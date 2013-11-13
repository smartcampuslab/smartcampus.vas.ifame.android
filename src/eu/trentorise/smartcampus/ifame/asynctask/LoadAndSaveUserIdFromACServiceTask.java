package eu.trentorise.smartcampus.ifame.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.GlobalConfig;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.utils.SharedPreferencesUtils;
import eu.trentorise.smartcampus.network.RemoteConnector;
import eu.trentorise.smartcampus.network.RemoteConnector.CLIENT_TYPE;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.ProfileServiceException;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;

/**
 * Get the userId from basic profile service and save it under the
 * SharedPreferences. The userId after is needed when user post a review.
 */
public class LoadAndSaveUserIdFromACServiceTask extends
		AsyncTask<Void, Void, Void> {
	/** Logging tag */
	private static final String TAG = "LoadAndSaveUserIdFromACServiceTask";

	private Context context;

	public LoadAndSaveUserIdFromACServiceTask(Context context) {
		this.context = context;
	}

	private String getAppUrl() {
		String returnAppUrl = "";
		try {
			returnAppUrl = GlobalConfig.getAppUrl(context);
			if (!returnAppUrl.endsWith("/"))
				returnAppUrl = returnAppUrl.concat("/");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		return returnAppUrl;
	}

	@Override
	protected Void doInBackground(Void... params) {
		RemoteConnector.setClientType(CLIENT_TYPE.CLIENT_ACCEPTALL);
		// retrieve the token and get the basic profile
		// SCAccessProvider accessProvider = IFameMain.getAccessProvider();
		String userToken = null;
		try {
			userToken = IFameMain.getAuthToken();
		} catch (AACException e) {
			Log.e(TAG, "Failed to get token: " + e.getMessage());
			// TODO handle the exception
		}
		Log.i(TAG, "Token: " + userToken);
		// check if correctly get the token
		if (userToken != null) {
			BasicProfileService service = new BasicProfileService(getAppUrl()+"aac");
			BasicProfile bp;
			try {
				bp = service.getBasicProfile(userToken);

				if (bp != null) {
					// save the userId in sharedpreferences. Will be used to
					// or edit reviews
					SharedPreferencesUtils.setUserID(context, bp.getUserId());
					Log.i(TAG, "UserId: " + bp.getUserId());
				}
				// else {
				// SharedPreferencesUtils.setUserID(context, "");
				// }
			} catch (SecurityException se) {
				Log.e(TAG, "Failed to get basic profile: " + se.getMessage());
				// TODO handle the exception
			} catch (ProfileServiceException pse) {
				Log.e(TAG, "Failed to get basic profile: " + pse.getMessage());
				// TODO handle the exception
			}
		}
		return null;
	}
}