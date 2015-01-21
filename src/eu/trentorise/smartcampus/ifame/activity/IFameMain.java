package eu.trentorise.smartcampus.ifame.activity;

import it.smartcampuslab.ifame.R;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;
import eu.trentorise.smartcampus.ifame.utils.TutorialUtils;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;

public class IFameMain extends SherlockActivity {

	private static SCAccessProvider accessProvider = null;
	private static Context context;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.ifame_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.favourite_mensa_ifame_main) {
			IFameUtils.checkInitBeforeLaunchActivity(IFameMain.this,
					MensaPreferita.class);
			return true;

		} else if (item.getItemId() == R.id.show_tutorial) {
			TutorialUtils.enableTutorial(this);
			showTutorials();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);

		context = getApplicationContext();

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
						IFretta.class);
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
				Toast.makeText(IFameMain.this, R.string.dialog_coming_soon, Toast.LENGTH_SHORT).show();;
//				IFameUtils.checkInitBeforeLaunchActivity(IFameMain.this,
//						ISoldi.class);
			}
		});
		
		initDataOnFirstLaunch();

	}

	/**
	 * 
	 */
	public void initDataOnFirstLaunch() {
		if (!IFameUtils.checkDisclaimer(this)) {
			IFameUtils.disableDisclaimer(this);
			showDisclaimer();
			return;
		}
		try {
			if (!getAccessProvider().isLoggedIn(this)) {
				getAccessProvider().login(this, null);
				return;
			}
		} catch (AACException e) {
			Toast.makeText(this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
			finish();
		}
		
		if (IFameUtils.isUserConnectedToInternet(IFameMain.this)) {
			// retrieve the mensa list and save it just to keep always
			// the updated link and datas if there is somehow an update
			// to the webcam objects
			MensaUtils.getAndSaveMensaList(IFameMain.this, false);
			// get user id and save
			UserIdUtils.retrieveAndSaveUserId(IFameMain.this);
		}
	}

	private void showDisclaimer() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		WebView wv = new WebView(this);
		wv.loadData(getString(R.string.welcome_msg), "text/html; charset=UTF-8", "utf-8");

		builder.setTitle(R.string.welcome_title)
				.setView(wv)
//				.setMessage(R.string.welcome_msg)
				.setOnCancelListener(
						new DialogInterface.OnCancelListener() {

							@Override
							public void onCancel(DialogInterface arg0) {
								initDataOnFirstLaunch();
								arg0.dismiss();
							}
						})
				.setPositiveButton(getString(android.R.string.ok),
								new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								initDataOnFirstLaunch();
								dialog.dismiss();
							}
						});
		builder.create().show();
	}

	
	public void showTutorials() {
		TutorialUtils.getTutorial(this).showTutorials();
	}

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			try {
				if (resultCode == RESULT_OK) {
					String mToken = data.getExtras().getString(AccountManager.KEY_AUTHTOKEN);
					if (mToken == null) {
						Toast.makeText(this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
						finish();
					} else {
						initDataOnFirstLaunch(); 
					}

				} else if (resultCode == RESULT_CANCELED) {
					Toast.makeText(this, getString(R.string.token_required), Toast.LENGTH_LONG).show();
					finish();

				} else {
					Toast.makeText(this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
					// clean shared preferences
					finish();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		TutorialUtils.getTutorial(this).onTutorialActivityResult(requestCode,
				resultCode, data);
	}
}
