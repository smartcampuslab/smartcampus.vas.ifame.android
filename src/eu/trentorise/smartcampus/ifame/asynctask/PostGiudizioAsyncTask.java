package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IGraditoVisualizzaRecensioni;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class PostGiudizioAsyncTask extends
		AsyncTask<Long, Void, List<Giudizio>> {

	/** Logging tag */
	private static final String TAG = "PostGiudizioAsyncTask";

	private IGraditoVisualizzaRecensioni context;
	private ProgressDialog progressDialog;
	private GiudizioDataToPost data;
	private String token;

	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	public PostGiudizioAsyncTask(IGraditoVisualizzaRecensioni context,
			GiudizioDataToPost data) {
		this.context = context;
		this.data = data;

		CLIENT_ID = context.getString(R.string.CLIENT_ID);
		CLIENT_SECRET = context.getString(R.string.CLIENT_SECRET);
		URL_BASE_WEB_IFAME = context.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = context.getString(R.string.APP_TOKEN);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context,
				context.getString(R.string.iGradito_title_activity),
				"Loading...");
		SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
		try {
			token = accessProvider.readToken(context, CLIENT_ID, CLIENT_SECRET);

		} catch (AACException e) {
			Log.e(TAG, "Failed to get token: " + e.getMessage());
		}
	}

	@Override
	protected List<Giudizio> doInBackground(Long... params) {

		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				APP_TOKEN);

		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				"mensa/" + params[0] + "/piatto/" + params[1] + "/giudizio/add");

		request.setMethod(Method.POST);
		request.setBody(Utils.convertToJSON(data));

		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, token);

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
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Giudizio> result) {
		super.onPostExecute(result);
		if (result == null) {
			ConnectionUtils.showToastErrorConnectingToWebService(context);
		} else {
			context.createGiudiziList(result);
			progressDialog.dismiss();
			Toast.makeText(context, "Recensione aggiunta correttamente",
					Toast.LENGTH_LONG).show();
		}
	}
}
