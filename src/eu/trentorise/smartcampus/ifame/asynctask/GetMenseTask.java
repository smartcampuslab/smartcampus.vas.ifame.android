package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaListAdapter;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

/**
 * ASYNCTASK PER COLLEGARSI AL WEB SERVICES E PRENDERE LE MENSE DISPONIBILI
 */
public class GetMenseTask extends AsyncTask<Void, Void, List<Mensa>> {
	/** Logging tag */
	private static final String TAG = "GetMenseTask";

	private Activity activity;
	private ProgressDialog progressDialog;
	private MensaListAdapter mensaListAdapter;

	private String userToken;

	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;
	private final String PATH_IFRETTA_GET_MENSE;

	/**
	 * Get the list of the canteens from the web service and put it in the given
	 * adapter
	 */
	public GetMenseTask(Activity activity, MensaListAdapter adapter) {
		this.activity = activity;
		this.mensaListAdapter = adapter;

		CLIENT_ID = activity.getString(R.string.CLIENT_ID);
		CLIENT_SECRET = activity.getString(R.string.CLIENT_SECRET);
		URL_BASE_WEB_IFAME = activity.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = activity.getString(R.string.APP_TOKEN);
		PATH_IFRETTA_GET_MENSE = activity
				.getString(R.string.PATH_IFRETTA_GETMENSE);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
		progressDialog = ProgressDialog.show(activity,
				activity.getString(R.string.iFretta_title_activity),
				activity.getString(R.string.loading));
		SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
		try {
			userToken = accessProvider.readToken(activity, CLIENT_ID,
					CLIENT_SECRET);
		} catch (AACException e) {
			Log.e(TAG, "Failed to get token: " + e.getMessage());
			// TODO handle the exception
			userToken = null;
		}
	}

	@Override
	protected List<Mensa> doInBackground(Void... params) {
		if (userToken != null) {
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
					APP_TOKEN);
			MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
					PATH_IFRETTA_GET_MENSE);
			request.setMethod(Method.GET);
			try {
				MessageResponse response = mProtocolCarrier.invokeSync(request,
						APP_TOKEN, userToken);
				if (response.getHttpStatus() == 200) {
					return Utils.convertJSONToObjects(response.getBody(),
							Mensa.class);
				}
			} catch (Exception e) {
				Log.e(TAG, "Failed to get the canteens: " + e.getMessage());
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Mensa> result) {
		super.onPostExecute(result);
		progressDialog.dismiss();

		if (result == null) {
			ConnectionUtils.showToastErrorConnectingToWebService(activity);
			activity.finish();
		} else {
			mensaListAdapter.addAll(result);
			mensaListAdapter.notifyDataSetChanged();
		}
	}
}