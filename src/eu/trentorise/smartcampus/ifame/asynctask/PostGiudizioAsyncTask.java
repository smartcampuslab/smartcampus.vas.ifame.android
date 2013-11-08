package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.activity.IGraditoVisualizzaRecensioni;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class PostGiudizioAsyncTask extends
		AsyncTask<Long, Void, List<Giudizio>> {

	private IGraditoVisualizzaRecensioni visualizzaRecensioniActivity;
	private ProgressDialog progressDialog;
	private GiudizioDataToPost data;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	public PostGiudizioAsyncTask(
			IGraditoVisualizzaRecensioni visualizzaRecensioniActivity,
			GiudizioDataToPost data) {
		this.visualizzaRecensioniActivity = visualizzaRecensioniActivity;
		this.data = data;
		URL_BASE_WEB_IFAME = visualizzaRecensioniActivity
				.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = visualizzaRecensioniActivity.getString(R.string.APP_TOKEN);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(visualizzaRecensioniActivity,
				visualizzaRecensioniActivity
						.getString(R.string.iGradito_title_activity),
				"Loading...");
		// progressDialog.setCancelable(true);
		// progressDialog.setCanceledOnTouchOutside(false);
	}

	@Override
	protected List<Giudizio> doInBackground(Long... params) {
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(
				visualizzaRecensioniActivity, APP_TOKEN);

		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				"mensa/" + params[0] + "/piatto/" + params[1] + "/giudizio/add");

		request.setMethod(Method.POST);
		request.setBody(Utils.convertToJSON(data));

		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, IFameMain.getAuthToken());

			if (response.getHttpStatus() == 200) {
				return Utils.convertJSONToObjects(response.getBody(),
						Giudizio.class);
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Giudizio> result) {
		super.onPostExecute(result);
		if (result == null) {
			Toast.makeText(
					visualizzaRecensioniActivity,
					visualizzaRecensioniActivity
							.getString(R.string.errorSomethingWentWrong),
					Toast.LENGTH_SHORT).show();
		} else {
			visualizzaRecensioniActivity.createGiudiziList(result);
			progressDialog.dismiss();
			Toast.makeText(visualizzaRecensioniActivity,
					"Recensione aggiunta correttamente", Toast.LENGTH_LONG)
					.show();
		}
	}
}
