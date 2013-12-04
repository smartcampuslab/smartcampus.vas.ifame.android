package eu.trentorise.smartcampus.ifame.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
				MensaUtils.getAndSaveMensaList(ctx);
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

	public static ProgressDialog getCustomProgressDialog(
			final Activity context, String progressTitle,
			final MenuItem refreshMenuItem) {

		String message = context.getString(R.string.loading);

		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(progressTitle);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

				Toast.makeText(context,
						context.getString(R.string.iSoldi_press_again_to_exit),
						Toast.LENGTH_SHORT).show();

				if (refreshMenuItem != null) {
					refreshMenuItem
							.setActionView(R.layout.actionbar_progressbar_circle);
					refreshMenuItem.expandActionView();
				}
			}
		});
		return progressDialog;
	}

}
