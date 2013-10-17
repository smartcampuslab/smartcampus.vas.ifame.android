package eu.trentorise.smartcampus.ifame.utils;

import eu.trentorise.smartcampus.ifame.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectionUtils {
	public static boolean isConnectedToInternet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	public static void showToastNotConnectedToInternet(Context context) {
		Toast.makeText(context,
				context.getString(R.string.CHECK_YOUR_INTERNET_CONNECTION),
				Toast.LENGTH_SHORT).show();
	}

	public static void showToastErrorConnectingToWebService(Context context) {
		Toast.makeText(context,
				context.getString(R.string.ERROR_CONNECTING_WEB_SERVICE),
				Toast.LENGTH_SHORT).show();
	}
}
