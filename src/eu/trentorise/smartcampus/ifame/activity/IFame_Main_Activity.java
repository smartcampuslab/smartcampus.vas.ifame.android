package eu.trentorise.smartcampus.ifame.activity;

import java.io.IOException;
import java.util.Random;

import eu.trentorise.smartcampus.ac.ACService;
import eu.trentorise.smartcampus.ac.AcServiceException;
import eu.trentorise.smartcampus.ac.Constants;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.authenticator.AMSCAccessProvider;
import eu.trentorise.smartcampus.ac.model.Attribute;
import eu.trentorise.smartcampus.ac.model.UserData;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.profileservice.ProfileService;
import eu.trentorise.smartcampus.profileservice.ProfileServiceException;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IFame_Main_Activity extends Activity {

	/** Logging tag */
	private static final String TAG = "Main";

	private static final String AUTH_URL = "https://vas-dev.smartcampuslab.it/accesstoken-provider/ac";

	private static final String AC_SERVICE_ADDR = "https://vas-dev.smartcampuslab.it/acService";
	private static final String PROFILE_SERVICE_ADDR = "https://vas-dev.smartcampuslab.it";

	/**
	 * Provides access to the authentication mechanism. Used to retrieve the
	 * token
	 */
	private SCAccessProvider mAccessProvider = null;
	
	/** Access token for the application user */
	private String mToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

		try {
			Constants.setAuthUrl(getApplicationContext(), AUTH_URL);
		} catch (NameNotFoundException e1) {
			Log.e(TAG, "problems with configuration.");
			finish();
		}

		// Initialize the access provider
		mAccessProvider = new AMSCAccessProvider();
		// if the authentication is necessary, use the provided operations to
		// retrieve the token: no restriction on the preferred account type
		try {
			mToken = mAccessProvider.getAuthToken(this, null);

		}

		catch (OperationCanceledException e) {
			Log.e(TAG, "Login cancelled.");
			finish();
		} catch (AuthenticatorException e) {
			Log.e(TAG, "Login failed: " + e.getMessage());
			finish();
		} catch (IOException e) {
			Log.e(TAG, "Login ended with error: " + e.getMessage());
			finish();
		}

	}

	protected void onResume() {

		super.onResume();
		setContentView(R.layout.layout_ifame_main);

		if (mToken != null) {
			// access the user data from the AC service remotely
			new LoadUserDataFromACServiceTask().execute(mToken);
			// access the basic user profile data remotely
			new LoadUserDataFromProfileServiceTask().execute(mToken);

			Button iFretta_btn = (Button) findViewById(R.id.iFretta_button);
			iFretta_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(IFame_Main_Activity.this,
							IFretta.class);
					startActivity(i);

				}

			});

			Button iDeciso_btn = (Button) findViewById(R.id.iDeciso_button);
			iDeciso_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(IFame_Main_Activity.this,
							IDeciso.class);
					startActivity(i);

				}

			});

			Button iGradito_btn = (Button) findViewById(R.id.iGradito_button);
			iGradito_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(IFame_Main_Activity.this,
							IGradito.class);
					startActivity(i);

				}

			});

			Button iSoldi_btn = (Button) findViewById(R.id.iSoldi_button);
			iSoldi_btn.setOnClickListener(new OnClickListener() {

				float card_val;

				@Override
				public void onClick(View v) {
					float[] values = new float[] { 4.10f, 4.30F, 2.7f, 1.8f,
							13.0f, 5.10f, 7.15f, 3.77f, 3.50f, 2.60f, 2.90f };
					for (int i = 0; i < values.length; i++) {
						card_val = values[new Random().nextInt(values.length) % 60];
					}
					Intent i = new Intent(IFame_Main_Activity.this,
							ISoldi.class);
					i.putExtra("iSoldi", card_val);
					startActivity(i);

				}

			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check the result of the authentication
		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			// authentication successful
			if (resultCode == RESULT_OK) {
				mToken = data.getExtras().getString(
						AccountManager.KEY_AUTHTOKEN);
				Log.i(TAG, "Authentication successfull");
				new LoadUserDataFromACServiceTask().execute(mToken);
				new LoadUserDataFromProfileServiceTask().execute(mToken);
				// authentication cancelled by user
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
				Log.i(TAG, "Authentication cancelled");
				// authentication failed
			} else {
				String error = data.getExtras().getString(
						AccountManager.KEY_AUTH_FAILED_MESSAGE);
				Toast.makeText(this, error, Toast.LENGTH_LONG).show();
				Log.i(TAG, "Authentication failed: " + error);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class LoadUserDataFromACServiceTask extends
			AsyncTask<String, Void, UserData> {

		@Override
		protected UserData doInBackground(String... params) {
			try {
				return new ACService(AC_SERVICE_ADDR).getUserByToken(params[0]);
			} catch (SecurityException e) {
				Log.e(TAG, "Security Exception: " + e.getMessage());
			} catch (AcServiceException e) {
				Log.e(TAG, "Service Exception: " + e.getMessage());
			}
			return null;
		}
	}

	public class LoadUserDataFromProfileServiceTask extends
			AsyncTask<String, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(String... params) {
			try {
				return new ProfileService(PROFILE_SERVICE_ADDR)
						.getBasicProfile(params[0]);
			} catch (SecurityException e) {
				Log.e(TAG, "Security Exception: " + e.getMessage());
			} catch (ProfileServiceException e) {
				Log.e(TAG, "Profile Service Exception: " + e.getMessage());
			}
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ifame__main_, menu);
		return true;
	}

}
