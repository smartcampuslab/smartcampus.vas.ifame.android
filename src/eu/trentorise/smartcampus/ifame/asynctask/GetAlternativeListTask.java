package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import it.smartcampuslab.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.adapter.AlternativePiattiAdapter;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

public class GetAlternativeListTask extends AsyncTask<Void, Void, List<Piatto>> {

	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	private LinearLayout progressBarLayout;
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
		progressBarLayout = IFameUtils.setProgressBarLayout(activity);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Piatto> resultList) {
		super.onPostExecute(resultList);
		if (resultList == null) {
			Toast.makeText(activity,
					activity.getString(R.string.errorSomethingWentWrong),
					Toast.LENGTH_SHORT).show();
			activity.finish();
			return;

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
			// piattiAdapter.addAll(addHeadersAlternative(resultList));
			piattiAdapter.notifyDataSetChanged();
		}
		progressBarLayout.setVisibility(View.GONE);
	}
}