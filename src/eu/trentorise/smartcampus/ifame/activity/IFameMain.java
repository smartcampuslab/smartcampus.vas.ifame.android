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
import eu.trentorise.smartcampus.ifame.utils.SharedPreferencesUtils;

public class IFameMain extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "IFameMain";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

		try {
			// check the user logged in
			SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
			if (!accessProvider.login(IFameMain.this,
					getString(R.string.CLIENT_ID),
					getString(R.string.CLIENT_SECRET), null)) {

				// check if a userId is already saved otherwise launch a task
				// that retrieves it and save it in sharedpreferences
				if (SharedPreferencesUtils.getUserID(IFameMain.this).length() == 0) {
					new LoadAndSaveUserDataFromACServiceTask(IFameMain.this)
							.execute();
				}
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// TODO handle the failure, e.g., notify the user and close the app
		} catch (Exception e) {
			Log.e(TAG, "Failed to get the user id: " + e.getMessage());
			// TODO handle the failure, e.g., notify the user and close the app
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

}
