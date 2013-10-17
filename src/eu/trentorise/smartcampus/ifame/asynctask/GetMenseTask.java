package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
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

	private Context context;
	private ProgressDialog progressDialog;
	private MensaListAdapter mensaListAdapter;

	private String userToken;
	private String titleProgressDialog;

	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;
	private final String PATH_IFRETTA_GET_MENSE;

	/**
	 * Get the list of the canteens from the web service and put it in the given
	 * adapter
	 */
	public GetMenseTask(Context context, MensaListAdapter adapter) {
		this.context = context;
		this.mensaListAdapter = adapter;

		CLIENT_ID = context.getString(R.string.CLIENT_ID);
		CLIENT_SECRET = context.getString(R.string.CLIENT_SECRET);
		URL_BASE_WEB_IFAME = context.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = context.getString(R.string.APP_TOKEN);
		PATH_IFRETTA_GET_MENSE = context
				.getString(R.string.PATH_IFRETTA_GETMENSE);
		titleProgressDialog = context
				.getString(R.string.iFretta_title_activity);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
		new ProgressDialog(context);
		progressDialog = ProgressDialog.show(context, titleProgressDialog,
				"Loading...");
		SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
		try {
			userToken = accessProvider.readToken(context, CLIENT_ID,
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
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
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
		if (result == null) {
			ConnectionUtils.showToastErrorConnectingToWebService(context);
			// TODO finish activity or notify an error
			// finish();
		} else {
			mensaListAdapter.addAll(result);
			mensaListAdapter.notifyDataSetChanged();
		}
		progressDialog.dismiss();
	}

}