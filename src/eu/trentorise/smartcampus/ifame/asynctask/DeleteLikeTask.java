package eu.trentorise.smartcampus.ifame.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.model.Likes;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class DeleteLikeTask extends AsyncTask<Likes, Void, Boolean> {
	/** Logging tag */
	private static final String TAG = "DeleteLikeTask";

	private Context context;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	public DeleteLikeTask(Context context) {
		this.context = context;

		URL_BASE_WEB_IFAME = context.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = context.getString(R.string.APP_TOKEN);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Likes... like) {
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				APP_TOKEN);
		// esempio path /giudizio/43/user/67/like/delete
		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				"/giudizio/" + like[0].getGiudizio_id() + "/like/delete");

		request.setMethod(Method.POST);
		request.setBody(Utils.convertToJSON(like[0]));

		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, IFameMain.getAuthToken());
			if (response.getHttpStatus() == 200) {
				return true;
			}
		} catch (ConnectionException e) {
			Log.e(TAG, "ConnectionException: " + e.getMessage());
		} catch (ProtocolException e) {
			Log.e(TAG, "ProtocolException: " + e.getMessage());
		} catch (SecurityException e) {
			Log.e(TAG, "SecurityException: " + e.getMessage());
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean resultOk) {
		super.onPostExecute(resultOk);
		if (!resultOk) {
			Toast.makeText(context,
					context.getString(R.string.errorLikeNotDeleted),
					Toast.LENGTH_SHORT).show();
		}
	}
}
