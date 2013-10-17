package eu.trentorise.smartcampus.ifame.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
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
	private String userToken;

	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;

	public DeleteLikeTask(Context context) {
		this.context = context;

		CLIENT_ID = context.getString(R.string.CLIENT_ID);
		CLIENT_SECRET = context.getString(R.string.CLIENT_SECRET);
		URL_BASE_WEB_IFAME = context.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = context.getString(R.string.APP_TOKEN);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
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
	protected Boolean doInBackground(Likes... like) {
		if (userToken != null) {
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
					APP_TOKEN);
			// esempio <urlbase>/giudizio/43/user/67/like/delete
			MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
					"/giudizio/" + like[0].getGiudizio_id() + "/like/delete");

			request.setMethod(Method.POST);

			request.setBody(Utils.convertToJSON(like[0]));

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, APP_TOKEN,
						userToken);
				if (response.getHttpStatus() == 200) {
					return true;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean resultOk) {
		super.onPostExecute(resultOk);
		if (!resultOk) {
			Toast.makeText(context, context.getString(R.string.likeNotDeleted),
					Toast.LENGTH_SHORT).show();
		}
	}
}
