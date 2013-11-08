package eu.trentorise.smartcampus.ifame.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;

public class ConnectionUtils {

	public static boolean isUserConnectedToInternet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	public static void errorToastTnternetConnectionNeeded(Context context) {
		Toast.makeText(context,
				context.getString(R.string.errorInternetConnectionRequired),
				Toast.LENGTH_SHORT).show();
	}
}
