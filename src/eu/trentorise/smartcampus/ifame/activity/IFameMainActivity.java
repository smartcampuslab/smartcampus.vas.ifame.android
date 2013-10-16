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

public class IFameMainActivity extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "iFameMainActivity";

	/** added for getting userid for recensioni activity (igradito) */
	private String userID;

	private SCAccessProvider accessProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

		accessProvider = new EmbeddedSCAccessProvider();
		try {
			if (!accessProvider.login(this, getString(R.string.CLIENT_ID),
					getString(R.string.CLIENT_SECRET), null)) {

				new LoadUserDataFromACServiceTask().execute();
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// handle the failure, e.g., notify the user and close the app
		} catch (Exception e) {
			Log.e(TAG, "Failed to get the user id: " + e.getMessage());
		}

		/**
		 * Add the listeners to the buttons in the home of iFame
		 */
		Button iFretta_btn = (Button) findViewById(R.id.iFretta_button);
		iFretta_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMainActivity.this, IFretta.class);
				startActivity(i);
			}
		});

		Button iDeciso_btn = (Button) findViewById(R.id.iDeciso_button);
		iDeciso_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMainActivity.this, IDeciso.class);
				startActivity(i);
			}
		});
		Button iGradito_btn = (Button) findViewById(R.id.iGradito_button);
		iGradito_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMainActivity.this, IGradito.class);
				// is for getting userid for recensioni activity (igradito)
				i.putExtra("user_id", userID);
				startActivity(i);
			}
		});

		Button iSoldi_btn = (Button) findViewById(R.id.iSoldi_button);
		iSoldi_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMainActivity.this, ISoldi.class);
				startActivity(i);
			}
		});

	}

	/** Initialize userID variable */
	private class LoadUserDataFromACServiceTask extends
			AsyncTask<Void, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {

				String mToken = accessProvider.readToken(
						getApplicationContext(), getString(R.string.CLIENT_ID),
						getString(R.string.CLIENT_SECRET));

				Log.i(TAG, "TOKEN: " + mToken);

				BasicProfileService service = new BasicProfileService(
						getString(R.string.URL_BASIC_PROFILE_SERVICE));

				BasicProfile bp = service.getBasicProfile(mToken);

				if (bp != null) {
					userID = bp.getUserId();
					Log.i(TAG, "UserId: " + userID);
				}
			} catch (Exception e) {
				Log.e(TAG, "Error loading user data!" + e.getMessage());
			}
			return null;
		}

	}

}
