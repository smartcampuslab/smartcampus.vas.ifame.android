package eu.trentorise.smartcampus.ifame.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;

public class IFameUtils {

	public static boolean isUserConnectedToInternet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	public static void checkInitBeforeLaunchActivity(Activity ctx,
			Class<?> activity) {
		// check if the user is connected
		if (IFameUtils.isUserConnectedToInternet(ctx)) {
			// check if the application is initialized
			if (MensaUtils.getMensaList(ctx).isEmpty()) {
				// initialize the webcam list
				MensaUtils.getAndSaveMensaList(ctx, false);
			} else {
				// start the activity
				Intent i = new Intent(ctx, activity);
				ctx.startActivity(i);
			}
		} else {
			Toast.makeText(ctx,
					ctx.getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
		}
	}

	public static LinearLayout setProgressBarLayout(Activity activity) {

		LinearLayout progressBarLayout = new LinearLayout(activity);
		progressBarLayout.setGravity(Gravity.CENTER);
		ProgressBar spinner = new ProgressBar(activity);
		progressBarLayout.addView(spinner);
		activity.addContentView(progressBarLayout, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		return progressBarLayout;
	}

	public static ProgressDialog getProgressDialog(Context context,
			String progressTitle) {

		String loading = context.getString(R.string.loading);
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(progressTitle);
		progressDialog.setMessage(loading);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);

		return progressDialog;
	}

	public static void setActionBarLoading(MenuItem refreshButton) {
		if (refreshButton != null) {
			refreshButton.setActionView(R.layout.actionbar_progressbar_circle);
			refreshButton.expandActionView();
		}
	}

	public static void removeActionBarLoading(MenuItem refreshButton) {
		if (refreshButton != null) {
			refreshButton.collapseActionView();
			refreshButton.setActionView(null);
		}
	}

}
