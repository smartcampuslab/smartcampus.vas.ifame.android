package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.activity.IGraditoVisualizzaRecensioni;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

public class PostGiudizioAsyncTask extends
		AsyncTask<Void, Void, List<Giudizio>> {

	private Activity activity;
	private GiudizioDataToPost data;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	private MenuItem refreshButton;
	private Long mensaId, piattoId;

	public PostGiudizioAsyncTask(Activity activity, GiudizioDataToPost data,
			MenuItem refreshButton, Long mensaId, Long piattoId) {
		this.activity = activity;
		this.data = data;
		this.refreshButton = refreshButton;
		this.mensaId = mensaId;
		this.piattoId = piattoId;

		URL_BASE_WEB_IFAME = activity.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = activity.getString(R.string.APP_TOKEN);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		IFameUtils.setActionBarLoading(refreshButton);
	}

	@Override
	protected List<Giudizio> doInBackground(Void... params) {
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
				APP_TOKEN);

		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				"mensa/" + mensaId + "/piatto/" + piattoId + "/giudizio/add");

		request.setMethod(Method.POST);
		request.setBody(Utils.convertToJSON(data));

		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, IFameMain.getAuthToken());

			if (response.getHttpStatus() == 200) {
				return Utils.convertJSONToObjects(response.getBody(),
						Giudizio.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(List<Giudizio> result) {
		super.onPostExecute(result);

		if (result == null) {
			Toast.makeText(
					activity,
					activity.getString(R.string.iGradito_recensione_non_aggiunta),
					Toast.LENGTH_SHORT).show();
		} else {

			// if review is posted in visualizza review show reviews
			if (activity instanceof IGraditoVisualizzaRecensioni) {
				((IGraditoVisualizzaRecensioni) activity)
						.createGiudiziList(result);
			}

			Toast.makeText(
					activity,
					activity.getString(R.string.iGradito_recensione_aggiunta_correttamente),
					Toast.LENGTH_LONG).show();
		}

		IFameUtils.removeActionBarLoading(refreshButton);
	}
}
