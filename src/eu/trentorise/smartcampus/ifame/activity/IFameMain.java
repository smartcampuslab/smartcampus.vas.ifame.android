package eu.trentorise.smartcampus.ifame.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;

public class IFameMain extends SherlockActivity {

	private static SCAccessProvider accessProvider = null;
	private static Context context;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// se l'app è stata inizializzata mostro la stellina
		if (!MensaUtils.getFavouriteMensaName(this).equalsIgnoreCase("")) {
			getSupportMenuInflater().inflate(R.menu.ifame_main, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// redraw the action bar because when is not initialized the app the
		// favourite button is not displayed
		supportInvalidateOptionsMenu();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.favourite_mensa_ifame_main) {
			Intent selezionaMensa = new Intent(this, IFretta.class);
			startActivity(selezionaMensa);
			return true;

		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

		// *********************************************************
		// sta roba qui serve ancora anche col login nel launcher???
		// *********************************************************
		// // check if the user is logged otherwise open login window
		// try {
		// if (!getAccessProvider().login(IFameMain.this, null)) {
		// }
		// } catch (AACException e) {
		// Log.e(TAG, "Failed to login: " + e.getMessage());
		// // TODO handle the failure, e.g., notify the user close the app
		// }
		// *********************************************************

		context = getApplicationContext();

		if (IFameUtils.isUserConnectedToInternet(this)) {
			// retrieve the mensa list and save it just to have always
			// the
			// updated link and datas if there is somehow an update
			MensaUtils.getAndSaveMensaList(IFameMain.this);
			// get user id and save
			UserIdUtils.retrieveAndSaveUserId(IFameMain.this);
		}

		// Add the listeners to the 4 buttons in the home of iFame
		// iDECISO
		Button iDecisoButton = (Button) findViewById(R.id.iDeciso_button);
		iDecisoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IFameMain.this, IDeciso.class);
				startActivity(i);
			}
		});
		// iFRETTA
		Button iFrettaButton = (Button) findViewById(R.id.iFretta_button);
		iFrettaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IFameUtils.checkInitBeforeLaunchActivity(IFameMain.this,
						IFrettaDetails.class);
			}
		});
		// iGRADITO
		Button iGraditoButton = (Button) findViewById(R.id.iGradito_button);
		iGraditoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IFameUtils.checkInitBeforeLaunchActivity(IFameMain.this,
						IGradito.class);
			}
		});
		// iSOLDI
		Button iSoldiButton = (Button) findViewById(R.id.iSoldi_button);
		iSoldiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IFameUtils.checkInitBeforeLaunchActivity(IFameMain.this,
						ISoldi.class);
			}
		});

	}

	// *******************************************************************************
	// still required also with the login in the launcher??????????????
	// *******************************************************************************
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// // check the result of the authentication
	// if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
	// if (resultCode == Activity.RESULT_OK) {
	// // retrieve the mensa list and save it just to have always the
	// // updated link and datas if there is somehow an update
	// MensaUtils.getAndSaveMensaList(IFameMain.this);
	//
	// // get user id and save
	// UserIdUtils.retrieveAndSaveUserId(IFameMain.this);
	// } else {
	// // or any other case close the app
	// Toast.makeText(IFameMain.this,
	// getString(R.string.errorLoginRequired),
	// Toast.LENGTH_SHORT).show();
	// finish();
	// return;
	// }
	// }
	// }
	// *******************************************************************************

	public static SCAccessProvider getAccessProvider() {
		if (accessProvider == null)
			accessProvider = SCAccessProvider.getInstance(context);
		return accessProvider;
	}

	public static String getAuthToken() throws AACException {
		String mToken;
		mToken = getAccessProvider().readToken(context);
		return mToken;
	}
}
