package eu.trentorise.smartcampus.ifame.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.model.Likes;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class PostLikeTask extends AsyncTask<Likes, Void, Boolean> {

	private ProtocolCarrier mProtocolCarrier;
	private Context context;
	private String appToken = "test smartcampus";

	private static final String CLIENT_ID = "9c7ccf0a-0937-4cc8-ae51-30d6646a4445";
	private static final String CLIENT_SECRET = "f6078203-1690-4a12-bf05-0aa1d1428875";
	private String token;

	public PostLikeTask(Context applicationContext) {
		context = applicationContext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
		try {
			token = accessProvider.readToken(context, CLIENT_ID, CLIENT_SECRET);
		} catch (AACException e) {
			Log.e("POST_LIKE", "Failed to get token: " + e.getMessage());
		}
	}

	@Override
	protected Boolean doInBackground(Likes... like) {

		mProtocolCarrier = new ProtocolCarrier(context, appToken);

		MessageRequest request = new MessageRequest(
				"http://smartcampuswebifame.app.smartcampuslab.it", "giudizio/"
						+ like[0].getGiudizio_id() + "/like");

		request.setMethod(Method.POST);

		request.setBody(Utils.convertToJSON(like[0]));

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request, appToken, token);

			if (response.getHttpStatus() == 200) {
				return true;
			} else {
				return false;
			}

		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {
		} else {
			Toast.makeText(context, "Oooops! Not Liked", Toast.LENGTH_SHORT)
					.show();
		}

	}
}
