package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.adapter.MensaAdapter;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

/**
 * ASYNCTASK PER COLLEGARSI AL WEB SERVICES E PRENDERE LE MENSE DISPONIBILI
 */
public class GetMenseTask extends AsyncTask<Void, Void, List<Mensa>> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private MensaAdapter mensaAdapter;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;
	private final String PATH_IFRETTA_GETMENSE;

	/**
	 * Get the list of the canteens from the web service and put it in the given
	 * adapter
	 */
	public GetMenseTask(Activity activity, MensaAdapter adapter) {
		this.activity = activity;
		this.mensaAdapter = adapter;
		URL_BASE_WEB_IFAME = activity.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = activity.getString(R.string.APP_TOKEN);
		PATH_IFRETTA_GETMENSE = activity
				.getString(R.string.PATH_IFRETTA_GETMENSE);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
		progressDialog = ProgressDialog.show(activity,
				activity.getString(R.string.iFretta_title_activity),
				activity.getString(R.string.loading));
		// progressDialog.setCancelable(true);
		//	progressDialog.setCanceledOnTouchOutside(false);
	}

	@Override
	protected List<Mensa> doInBackground(Void... params) {

		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
				APP_TOKEN);
		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				PATH_IFRETTA_GETMENSE);
		request.setMethod(Method.GET);
		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, IFameMain.getAuthToken());
			if (response.getHttpStatus() == 200) {
				return Utils.convertJSONToObjects(response.getBody(),
						Mensa.class);
			}
		} catch (Exception e) {
			Log.e(GetMenseTask.class.getName(), "Failed to get the canteens: "
					+ e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(List<Mensa> result) {
		super.onPostExecute(result);
		progressDialog.dismiss();

		if (result == null) {
			Toast.makeText(activity,
					activity.getString(R.string.errorSomethingWentWrong),
					Toast.LENGTH_SHORT).show();
			activity.finish();
		} else {
			for (Mensa mensa : result) {
				mensaAdapter.add(mensa);

			}
			mensaAdapter.notifyDataSetChanged();
		}
	}
}