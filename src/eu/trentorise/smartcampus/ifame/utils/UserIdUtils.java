package eu.trentorise.smartcampus.ifame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import eu.trentorise.smartcampus.ifame.asynctask.LoadAndSaveUserIdFromACServiceTask;

public class UserIdUtils {

	private final static String USER_ID_SHARED_PREFERENCES_NAME = "user_id_preferences";
	private final static String USER_ID_KEY = "user_id";

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
	public static boolean setUserId(Context context, String userId) {

		SharedPreferences pref = context.getSharedPreferences(
				USER_ID_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(USER_ID_KEY, userId);

		return editor.commit();
	}

	/** Retrieve and save in the shared preferences the user id */
	public static void retrieveAndSaveUserId(Context context) {
		new LoadAndSaveUserIdFromACServiceTask(context).execute();
	}

}
