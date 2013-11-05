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
import eu.trentorise.smartcampus.ifame.adapter.MenuDelGiornoPiattiAdapter;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class GetMenuDelGiornoTask extends AsyncTask<Void, Void, MenuDelGiorno> {


	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;


	private ProgressDialog progressDialog;
	private Activity activity;
	private MenuDelGiornoPiattiAdapter mPiattiAdapter;

	public GetMenuDelGiornoTask(Activity activity,
			MenuDelGiornoPiattiAdapter piattiAdapter) {
		this.activity = activity;
		this.mPiattiAdapter = piattiAdapter;
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
	protected MenuDelGiorno doInBackground(Void... params) {
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
					APP_TOKEN);
			MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
					"getmenudelgiorno");
			request.setMethod(Method.GET);
			try {
				MessageResponse response = mProtocolCarrier.invokeSync(request,
						APP_TOKEN, IFameMain.getAuthToken());
				if (response.getHttpStatus() == 200) {
					return Utils.convertJSONToObject(response.getBody(),
							MenuDelGiorno.class);
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
	protected void onPostExecute(MenuDelGiorno result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		if (result == null) {
			ConnectionUtils.errorToastRetrievingDataFromWeb(activity
					.getApplicationContext());
			activity.finish();
		} else {
			mPiattiAdapter.clear();
			mPiattiAdapter.addAll(addHeadersToPiattiList(result
					.getPiattiDelGiorno()));
			mPiattiAdapter.notifyDataSetChanged();
		}
	}

	/** add some fake piatti as headers */
	private List<Piatto> addHeadersToPiattiList(List<Piatto> outputList) {
		List<Piatto> listaPiattiConSentinelle = new ArrayList<Piatto>();
		listaPiattiConSentinelle.add(new Piatto("1", ""));
		for (int i = 0; i < outputList.size(); i++) {
			listaPiattiConSentinelle.add(outputList.get(i));
			if (i == 2) {
				listaPiattiConSentinelle.add(new Piatto("2", ""));
			}
			if (i == 4)
				listaPiattiConSentinelle.add(new Piatto("3", ""));
		}
		return listaPiattiConSentinelle;
	}
}
