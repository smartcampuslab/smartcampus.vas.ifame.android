package eu.trentorise.smartcampus.ifame.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
						try {
							mToken = accessProvider.readToken(
									getApplicationContext(), CLIENT_ID,
									CLIENT_SECRET);
							System.out.println("TOKEN ->" + mToken);
							new LoadUserDataFromACServiceTask().execute(mToken);
						} catch (AACException e) {
							Log.e(TAG,
									"Failed to read token: " + e.getMessage());
							// handle the failure, e.g., notify the user and
							// close the app.
						}
					}
				}).start();
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// mToken = null;
			// handle the failure, e.g., notify the user and close
			// the app.
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
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
				Intent i = new Intent(IFame_Main_Activity.this, ISoldi.class);
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

}
