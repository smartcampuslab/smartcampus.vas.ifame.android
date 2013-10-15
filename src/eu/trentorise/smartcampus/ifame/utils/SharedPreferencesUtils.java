package eu.trentorise.smartcampus.ifame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;

public class SharedPreferencesUtils {

	public final static String GET_FAVOURITE_CANTEEN = "get_favourite_canteen";
	private final static String NOT_FOUND = "not_found";

	/*
	 * set the name of default mensa in shared preferences
	 */
	public static void setDefaultMensa(Context context, String mensa_name) {

		SharedPreferences pref = context.getSharedPreferences(
				context.getString(R.string.iFretta_preference_file),
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = pref.edit();

		editor.putString(GET_FAVOURITE_CANTEEN, mensa_name);

		editor.commit();

		Toast.makeText(
				context,
				context.getString(R.string.iFretta_details_set_favourite_canteen)
						+ " " + mensa_name, Toast.LENGTH_SHORT).show();
	}

	/*
	 * return mensa name or null if not set
	 */
	public static String getDefaultMensa(Context context) {

		SharedPreferences pref = context.getSharedPreferences(
				context.getString(R.string.iGradito_preference_file),
				Context.MODE_PRIVATE);

		String mensa = pref.getString(GET_FAVOURITE_CANTEEN, NOT_FOUND);

		if (mensa.equalsIgnoreCase(NOT_FOUND)) {
			mensa = null;
		}

		return mensa;
	}

}