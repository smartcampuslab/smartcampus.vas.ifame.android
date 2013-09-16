package eu.trentorise.smartcampus.ifame.activity;

import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;

public class IFame_Main_Activity extends SherlockActivity {

	/** Logging tag */
	private static final String TAG = "Main";

	//private static final String AUTH_URL = "https://vas-dev.smartcampuslab.it/accesstoken-provider/ac";
	private static final String CLIENT_ID = "9c7ccf0a-0937-4cc8-ae51-30d6646a4445";
	private static final String CLIENT_SECRET = "f6078203-1690-4a12-bf05-0aa1d1428875";
	
	private static final String AC_SERVICE_ADDR = "https://vas-dev.smartcampuslab.it/acService";
	private static final String PROFILE_SERVICE_ADDR = "https://vas-dev.smartcampuslab.it";

	private SCAccessProvider accessProvider = null;
	public static String userAuthToken;
	public static ProgressDialog pd;
	public static BasicProfile bp;

	// added for getting userid for recensioni activity (igradito)
	private String userID;

	/** Access token for the application user */
	private String mToken = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// your code here
		setContentView(R.layout.layout_ifame_main);
	 
	        SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
	        try {
			if (!accessProvider.login(this, CLIENT_ID, CLIENT_SECRET, null)) {
				// user is already registered. Proceed requesting the token 
	                        // and the related steps if needed
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: "+e.getMessage());
	                // handle the failure, e.g., notify the user and close the app. 
		}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		try {
//			Constants.setAuthUrl(getApplicationContext(), AUTH_URL);
//		} catch (NameNotFoundException e1) {
//			Log.e(TAG, "problems with configuration.");
//			finish();
//		}
//
//		// Initialize the access provider
//		mAccessProvider = new AMSCAccessProvider();
//		// if the authentication is necessary, use the provided operations to
//		// retrieve the token: no restriction on the preferred account type
//		try {
//			mToken = mAccessProvider.getAuthToken(this, null);
//			// added for getting userid for recensioni activity (igradito)
//			if (mToken != null) {
//				// read user Data
//				UserData data = mAccessProvider.readUserData(this, null);
//				userID = data.getUserId();
//
//			}
//			// added for getting userid for recensioni activity (igradito)
//
//		}
//
//		catch (OperationCanceledException e) {
//			Log.e(TAG, "Login cancelled.");
//			finish();
//		} catch (AuthenticatorException e) {
//			Log.e(TAG, "Login failed: " + e.getMessage());
//			finish();
//		} catch (IOException e) {
//			Log.e(TAG, "Login ended with error: " + e.getMessage());
//			finish();
//		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	protected void onResume() {

		super.onResume();
		setContentView(R.layout.layout_ifame_main);

		if (mToken != null) {
			// access the user data from the AC service remotely
			new LoadUserDataFromACServiceTask();
			// access the basic user profile data remotely
			// new LoadUserDataFromProfileServiceTask().execute(mToken);

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
					// added for getting userid for recensioni activity (igradito)
					i.putExtra("user_id", userID);
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
		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				// A&A successful. Proceed requesting the token 
	                        // and the related steps if needed
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// Cancelled by user
			} else {
				// Operation failed for some reason
			}
		}
	        // other code here 
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// check the result of the authentication
//		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
//			// authentication successful
//			if (resultCode == RESULT_OK) {
//				mToken = data.getExtras().getString(
//						AccountManager.KEY_AUTHTOKEN);
//				Log.i(TAG, "Authentication successfull");
//				new LoadUserDataFromACServiceTask().execute(mToken);
//				// new LoadUserDataFromProfileServiceTask().execute(mToken);
//				// authentication cancelled by user
//			} else if (resultCode == RESULT_CANCELED) {
//				Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
//				Log.i(TAG, "Authentication cancelled");
//				// authentication failed
//			} else {
//				String error = data.getExtras().getString(
//						AccountManager.KEY_AUTH_FAILED_MESSAGE);
//				Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//				Log.i(TAG, "Authentication failed: " + error);
//			}
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}

	public class LoadUserDataFromACServiceTask extends
	AsyncTask<Void, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {
				String token = accessProvider.readToken(IFame_Main_Activity.this, CLIENT_ID, CLIENT_SECRET);
				BasicProfileService service = new BasicProfileService("https://vas-dev.smartcampuslab.it/aac");
				BasicProfile bp = service.getBasicProfile(token);
				return bp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	 
		@Override
		protected void onPostExecute(BasicProfile result) {
			super.onPostExecute(result);
		}
		}

//	public class LoadUserDataFromProfileServiceTask extends
//			AsyncTask<String, Void, BasicProfile> {
//
//		@Override
//		protected BasicProfile doInBackground(String... params) {
//			try {
//				return new ProfileService(PROFILE_SERVICE_ADDR)
//						.getBasicProfile(params[0]);
//			} catch (SecurityException e) {
//				Log.e(TAG, "Security Exception: " + e.getMessage());
//			} catch (ProfileServiceException e) {
//				Log.e(TAG, "Profile Service Exception: " + e.getMessage());
//			}
//			return null;
//		}
//	}

}
