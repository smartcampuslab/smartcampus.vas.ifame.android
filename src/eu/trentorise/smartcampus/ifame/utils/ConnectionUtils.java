package eu.trentorise.smartcampus.ifame.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectionUtils {
	public static boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static void showToastNotConnected(Context context) {
		Toast.makeText(context, "Controlla la tua connessione ad internet!",
				Toast.LENGTH_SHORT).show();
	}

	public static void showToastErrorToConnectToWebService(Context context) {
		Toast.makeText(context, "Ooops! Qualcosa e' andato storto!",
				Toast.LENGTH_SHORT).show();
	}
}
