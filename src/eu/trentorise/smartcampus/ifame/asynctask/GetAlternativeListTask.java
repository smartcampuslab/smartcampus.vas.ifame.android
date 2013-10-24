package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.ArrayList;
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

	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	private String userToken;
	private ProgressDialog progressDialog;
	private Activity activity;
	private AlternativePiattiAdapter piattiAdapter;

	public GetAlternativeListTask(Activity activity,
			AlternativePiattiAdapter piattiAdapter) {
		this.activity = activity;
		this.piattiAdapter = piattiAdapter;

		CLIENT_ID = activity.getString(R.string.CLIENT_ID);
		CLIENT_SECRET = activity.getString(R.string.CLIENT_SECRET);
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
	protected List<Piatto> doInBackground(Void... params) {
		if (userToken != null) {
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
					APP_TOKEN);
			MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
					"getalternative");
			request.setMethod(Method.GET);
			try {
				MessageResponse response = mProtocolCarrier.invokeSync(request,
						APP_TOKEN, userToken);
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
			}
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
			piattiAdapter.addAll(addHeadersAlternative(resultList));
			piattiAdapter.notifyDataSetChanged();
		}
	}

	/** add some fake piatti as headers */
	private List<Piatto> addHeadersAlternative(List<Piatto> listaPiatti) {
		// Create a list in which the alternative menu will be saved
		List<Piatto> listaConSentinelle = new ArrayList<Piatto>();
		// add an item into the list, this item will be used as a sentinel that
		// will determine the type of dish(primo, secondo, etc..)
		listaConSentinelle.add(new Piatto("1", ""));
		for (int i = 0; i < listaPiatti.size(); i++) {
			listaConSentinelle.add(listaPiatti.get(i));
			if (i == 2) {
				// sentinel for seconds
				listaConSentinelle.add(new Piatto("2", ""));
			}
			if (i == 5) {
				// sentinel for piatti freddi
				listaConSentinelle.add(new Piatto("3", ""));
			}
			if (i == 8) {
				// sentinel for contorni
				listaConSentinelle.add(new Piatto("4", ""));
			}
		}
		return listaConSentinelle;
	}
}