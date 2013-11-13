package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.asynctask.LoadAndSaveUserIdFromACServiceTask;
import eu.trentorise.smartcampus.ifame.utils.SharedPreferencesUtils;

public class IFameMain extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "IFameMain";
	private static SCAccessProvider accessProvider = null;
	public static Context ctx;

	// private SCAccessProvider accessProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);
		ctx = getApplicationContext();
		// check if the user is logged otherwise open login window
		try {
			if (!getAccessProvider().login(IFameMain.this, null)) {
				// login
				new LoadAndSaveUserIdFromACServiceTask(ctx).execute();
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// TODO handle the failure, e.g., notify the user close the app
		}

		// Add the listeners to the 4 buttons in the home of iFame
		Button iFrettaButton = (Button) findViewById(R.id.iFretta_button);
		iFrettaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFameMain.this, IFretta.class);
				startActivity(i);
			}
		});

		Button iDecisoButton = (Button) findViewById(R.id.iDeciso_button);
		iDecisoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMain.this, IDeciso.class);
				startActivity(i);
			}
		});

		Button iGraditoButton = (Button) findViewById(R.id.iGradito_button);
		iGraditoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMain.this, IGradito.class);
				startActivity(i);
			}
		});

		Button iSoldiButton = (Button) findViewById(R.id.iSoldi_button);
		iSoldiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMain.this, ISoldi.class);
				startActivity(i);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// check the result of the authentication
		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				// user is logged
				// OK a user is logged check internet connection and get userid
				// if(ConnectionUtils.isUserConnectedToInternet(IFameMain.this))
				// {
				// needed because after some feature (in iGradito) need the
				// userId saved by this asynktask

				SharedPreferencesUtils.retrieveAndSaveUserId(IFameMain.this);
				// }
			} else {
				// if cancelled by user (resultCode == Activity.RESULT_CANCELED)
				// or any other case close the app
				Toast.makeText(IFameMain.this,
						getString(R.string.errorLoginRequired),
						Toast.LENGTH_SHORT).show();
				IFameMain.this.finish();
			}
		}
	}

	public static SCAccessProvider getAccessProvider() {
		if (accessProvider == null)
			accessProvider = SCAccessProvider.getInstance(ctx);// .getInstance(ctx);
		return accessProvider;
	}

	public static String getAuthToken() throws AACException {
		String mToken;
		mToken = getAccessProvider().readToken(ctx);
		return mToken;
	}
}
