package eu.trentorise.smartcampus.ifame.activity;

import android.content.Intent;
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
import eu.trentorise.smartcampus.ifame.asynctask.LoadAndSaveUserDataFromACServiceTask;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;

public class IFameMain extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "IFameMain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

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

	// TODO add a smarter check if someone doesn't want to log in and
	// close the login view the app will close by itself

	@Override
	protected void onResume() {
		super.onResume();
		try {
			// check if the user is logged in otherwise open login view
			SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
			if (!accessProvider.login(this, getString(R.string.CLIENT_ID),
					getString(R.string.CLIENT_SECRET), null)) {
				// user is logged
				// OK a user is logged check internet connection
				if (ConnectionUtils.isConnectedToInternet(this)) {
					// needed because after some feature (in iGradito) need the
					// userId saved by this asynktask
					new LoadAndSaveUserDataFromACServiceTask(this).execute();
				} else {
					ConnectionUtils.showToastNotConnectedToInternet(this);
				}
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// TODO handle the failure, e.g., notify the user close the app
		} catch (Exception e) {
			Log.e(TAG, "Failed to get the user id: " + e.getMessage());
			// TODO handle the failure, e.g., notify the user close the app
		}
	}

}
