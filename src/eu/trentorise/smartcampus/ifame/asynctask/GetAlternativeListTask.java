package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.adapter.AlternativePiattiAdapter;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class GetAlternativeListTask extends AsyncTask<Void, Void, List<Piatto>> {

	/** Logging tag */
	private static final String TAG = "GetAlternativeListTask";

	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	private ProgressDialog progressDialog;
	private Activity activity;
	private AlternativePiattiAdapter piattiAdapter;

	public GetAlternativeListTask(Activity activity,
			AlternativePiattiAdapter piattiAdapter) {
		this.activity = activity;
		this.piattiAdapter = piattiAdapter;

		URL_BASE_WEB_IFAME = activity.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = activity.getString(R.string.APP_TOKEN);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
		progressDialog = ProgressDialog.show(activity,
				activity.getString(R.string.iDeciso_home_daily_menu),
				activity.getString(R.string.loading));
	}

	@Override
	protected List<Piatto> doInBackground(Void... params) {
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
				APP_TOKEN);
		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				"getalternative");
		request.setMethod(Method.GET);
		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, IFameMain.getAuthToken());
			if (response.getHttpStatus() == 200) {
				return Utils.convertJSONToObjects(response.getBody(),
						Piatto.class);
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
	protected void onPostExecute(List<Piatto> resultList) {
		super.onPostExecute(resultList);
		progressDialog.dismiss();
		if (resultList == null) {
			ConnectionUtils.errorToastRetrievingDataFromWeb(activity
					.getApplicationContext());
			activity.finish();
		} else {
			piattiAdapter.clear();
			int i = 0;
			for (Piatto piatto : resultList) {
				i++;
				if (i == 1) {
					// sentinel for seconds
					piattiAdapter.add(new Piatto("1", ""));
				}
				if (i == 4) {
					// sentinel for piatti freddi
					piattiAdapter.add(new Piatto("2", ""));
				}
				if (i == 7) {
					// sentinel for contorni
					piattiAdapter.add(new Piatto("3", ""));
				}
				if (i == 10) {
					piattiAdapter.add(new Piatto("4", ""));
				}
				piattiAdapter.add(piatto);
			}
			//piattiAdapter.addAll(addHeadersAlternative(resultList));
			piattiAdapter.notifyDataSetChanged();
		}
	}
}