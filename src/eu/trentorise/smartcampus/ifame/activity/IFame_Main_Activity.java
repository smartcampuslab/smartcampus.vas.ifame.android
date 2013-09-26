package eu.trentorise.smartcampus.ifame.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;

public class IFame_Main_Activity extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "Main";

	/** added for getting userid for recensioni activity (igradito) */
	private String userID;

	/** Access token for the application user */
	private String mToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

		final SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
		final String CLIENT_ID = getString(R.string.CLIENT_ID);
		final String CLIENT_SECRET = getString(R.string.CLIENT_SECRET);

		try {
			if (!accessProvider.login(this, CLIENT_ID, CLIENT_SECRET, null)) {
				// user is already registered. Proceed requesting the token
				// and the related steps if needed

				// new thread is needed because will throw the
				// Networkonmainthreadexception running the read token
				new Thread(new Runnable() {

					@Override
					public void run() {
						String mToken;

						try {
							mToken = accessProvider.readToken(
									getApplicationContext(), CLIENT_ID,
									CLIENT_SECRET);

							new LoadUserDataFromACServiceTask().execute(mToken);

						} catch (AACException e) {
							Log.e(TAG,
									"Failed to read token: " + e.getMessage());
							mToken = null;
							// handle the failure, e.g., notify the user and
							// close
							// the app.
						}

					}
				}).start();

			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			mToken = null;
			// handle the failure, e.g., notify the user and close
			// the app.
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		setContentView(R.layout.layout_ifame_main);

		Button iFretta_btn = (Button) findViewById(R.id.iFretta_button);
		iFretta_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFame_Main_Activity.this, IFretta.class);
				startActivity(i);

			}

		});

		Button iDeciso_btn = (Button) findViewById(R.id.iDeciso_button);
		iDeciso_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFame_Main_Activity.this, IDeciso.class);
				startActivity(i);

			}

		});

		Button iGradito_btn = (Button) findViewById(R.id.iGradito_button);
		iGradito_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFame_Main_Activity.this, IGradito.class);
				// added for getting userid for recensioni activity
				// (igradito)
				i.putExtra("user_id", userID);
				startActivity(i);
			}

		});

		Button iSoldi_btn = (Button) findViewById(R.id.iSoldi_button);
		iSoldi_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// float card_val;
				// float[] values = new float[] { 4.10f, 4.30F, 2.7f, 1.8f,
				// 13.0f, 5.10f, 7.15f, 3.77f, 3.50f, 2.60f, 2.90f };
				// for (int i = 0; i < values.length; i++) {
				// card_val = values[new Random().nextInt(values.length) %
				// 60];
				// }
				Intent i = new Intent(IFame_Main_Activity.this, ISoldi.class);
				// i.putExtra("iSoldi", card_val);
				startActivity(i);

			}

		});

	}

	private class LoadUserDataFromACServiceTask extends
			AsyncTask<String, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(String... params) {
			try {
				BasicProfileService service = new BasicProfileService(
						"https://vas-dev.smartcampuslab.it/aac");
				BasicProfile bp = service.getBasicProfile(params[0]);
				if (bp != null) {
					userID = bp.getUserId();
				} else {
					Log.d(TAG, "Error loading user data!");
				}
			} catch (Exception e) {
				Log.d(TAG, e.getMessage());
			}
			return null;
		}

	}
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
	// if (resultCode == Activity.RESULT_OK) {
	// // A&A successful. Proceed requesting the token
	// // and the related steps if needed
	// } else if (resultCode == Activity.RESULT_CANCELED) {
	// // Cancelled by user
	// } else {
	// // Operation failed for some reason
	// }
	// }
	// // other code here
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // check the result of the authentication
	// if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
	// // authentication successful
	// if (resultCode == RESULT_OK) {
	// mToken = data.getExtras().getString(
	// AccountManager.KEY_AUTHTOKEN);
	// Log.i(TAG, "Authentication successfull");
	// new LoadUserDataFromACServiceTask().execute(mToken);
	// // new LoadUserDataFromProfileServiceTask().execute(mToken);
	// // authentication cancelled by user
	// } else if (resultCode == RESULT_CANCELED) {
	// Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
	// Log.i(TAG, "Authentication cancelled");
	// // authentication failed
	// } else {
	// String error = data.getExtras().getString(
	// AccountManager.KEY_AUTH_FAILED_MESSAGE);
	// Toast.makeText(this, error, Toast.LENGTH_LONG).show();
	// Log.i(TAG, "Authentication failed: " + error);
	// }
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	// public class LoadUserDataFromProfileServiceTask extends
	// AsyncTask<String, Void, BasicProfile> {
	//
	// @Override
	// protected BasicProfile doInBackground(String... params) {
	// try {
	// return new ProfileService(PROFILE_SERVICE_ADDR)
	// .getBasicProfile(params[0]);
	// } catch (SecurityException e) {
	// Log.e(TAG, "Security Exception: " + e.getMessage());
	// } catch (ProfileServiceException e) {
	// Log.e(TAG, "Profile Service Exception: " + e.getMessage());
	// }
	// return null;
	// }
	// }

}
