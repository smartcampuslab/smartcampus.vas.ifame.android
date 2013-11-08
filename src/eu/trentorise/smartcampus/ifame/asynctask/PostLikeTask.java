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

public class PostLikeTask extends AsyncTask<Likes, Void, Boolean> {
	/** Logging tag */
	private static final String TAG = "PostLikeTask";

	private Context context;

	public PostLikeTask(Context context) {
		this.context = context;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Likes... like) {
		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
				context.getString(R.string.APP_TOKEN));
		// esempio path /giudizio/43/like
		MessageRequest request = new MessageRequest(
				context.getString(R.string.URL_BASE_WEB_IFAME), "/giudizio/"
						+ like[0].getGiudizio_id() + "/like");

		request.setMethod(Method.POST);
		request.setBody(Utils.convertToJSON(like[0]));

		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					context.getString(R.string.APP_TOKEN),
					IFameMain.getAuthToken());
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
			Log.e(TAG, "AACException: " + e.getMessage());
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean resultOk) {
		super.onPostExecute(resultOk);
		if (!resultOk) {
			Toast.makeText(context, context.getString(R.string.errorNotLiked),
					Toast.LENGTH_SHORT).show();
		}
	}
}
