package eu.trentorise.smartcampus.ifame.utils;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.GlobalConfig;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.network.RemoteConnector;
import eu.trentorise.smartcampus.network.RemoteConnector.CLIENT_TYPE;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.ProfileServiceException;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;

public class UserIdUtils {

	/**
	 * This is the time between two updates of the userid and is set by default
	 * to one hour
	 */
	private static final long TIME_IN_MILLIS = 1 * 60 * 60 * 1000;
	private final static String LAST_UPDATE = "get_last_update";

	private final static String USER_ID_SHARED_PREFERENCES_NAME = "user_id_preferences";
	private final static String USER_ID_KEY = "user_id";

	private static boolean setLastUpdateNow(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				USER_ID_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		Long currentTimeInMillis = new Date().getTime();
		editor.putLong(LAST_UPDATE, currentTimeInMillis);

		return editor.commit();
	}

	/** returns true if an update is required false instead */
	private static boolean updateRequired(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				USER_ID_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

		if (pref.contains(LAST_UPDATE)) {

			Long lastUpdate = pref.getLong(LAST_UPDATE, -1);
			Long now = new Date().getTime();

			return (now - lastUpdate) > TIME_IN_MILLIS;
		} else {
			return true;
		}
	}

	/** returns the user id or the empty string */
	public static String getUserId(Context context) {
		String userId = "";

		SharedPreferences pref = context.getSharedPreferences(
				USER_ID_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		if (pref.contains(USER_ID_KEY)) {
			userId = pref.getString(USER_ID_KEY, userId);
		}
		return userId;
	}

	/** save the user id in the shared preferences */
	private static boolean setUserId(Context context, String userId) {
		setLastUpdateNow(context);

		SharedPreferences pref = context.getSharedPreferences(
				USER_ID_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(USER_ID_KEY, userId);

		return editor.commit();
	}

	/** Retrieve and save in the shared preferences the user id */
	public static void retrieveAndSaveUserId(Context context) {
		// if (updateRequired(context)) {
		new LoadAndSaveUserIdFromACServiceTask(context).execute();
		// }
	}

	/**
	 * Get the userId from basic profile service and save it under the
	 * SharedPreferences. The userId after is needed when user post a review.
	 */
	private static class LoadAndSaveUserIdFromACServiceTask extends
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
				try {
					BasicProfileService service = new BasicProfileService(
							getAppUrl() + "aac");
					BasicProfile bp = service.getBasicProfile(userToken);

					if (bp != null) {
						// save the userId in sharedpreferences. Will be used to
						// or edit reviews
						setUserId(context, bp.getUserId());
						Log.i(TAG, "UserId: " + bp.getUserId());
					}
				} catch (SecurityException se) {
					Log.e(TAG, "SecurityException: " + se.getMessage());
					// TODO handle the exception
				} catch (ProfileServiceException pse) {
					Log.e(TAG, "ProfileServiceException: " + pse.getMessage());
					// TODO handle the exception
				}
			}
			return null;
		}
	}

}
