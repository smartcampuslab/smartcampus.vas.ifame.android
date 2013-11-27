package eu.trentorise.smartcampus.ifame.activity;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaSpinnerAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetMenseTaskActionBar;
import eu.trentorise.smartcampus.ifame.asynctask.RetrieveWebcamImageTask;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamAspectRatioImageView;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;

public class IFrettaDetails extends SherlockActivity implements
		OnNavigationListener {

	/** Logging tag */
	@SuppressWarnings("unused")
	private static final String TAG = "iFrettaDetails";

	/** Is the key of the mensa object needed to be passed as extra */
	@Deprecated
	public static final String MENSA = "mensa_extra";

	private static final int START_HOUR = 12;
	private static final int END_HOUR = 14;

	// private Mensa mensa;
	private MenuItem refreshButton;
	private MensaSpinnerAdapter adapter;

	private int currentMensa = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta_details);

		adapter = new MensaSpinnerAdapter(this);

		// check for internet connection and than get the mense
		if (ConnectionUtils.isUserConnectedToInternet(getApplicationContext())) {

			new GetMenseTaskActionBar(this, adapter).execute();

		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		ActionBar bar = getSupportActionBar();
		bar.setDisplayShowTitleEnabled(false);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setHomeButtonEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);
		// Set up the dropdown list navigation in the action bar.
		bar.setListNavigationCallbacks(adapter, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// NOT YET IMPLEMENTED FAVOURITE MENSA FUNCTIONALITY
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_only_loading, menu);

		refreshButton = menu.findItem(R.id.action_refresh);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;

		case R.id.action_refresh:
			loadWebcamImage(adapter.getItem(currentMensa));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadWebcamImage(Mensa mensa) {

		WebcamAspectRatioImageView webcamImage = (WebcamAspectRatioImageView) findViewById(R.id.imageViewId);
		// get current hour
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		// if there is no link for a specified mensa do nothing because the
		// default is shown no avaiable or loading image
		if (mensa.getMensa_link_online() == null
				|| mensa.getMensa_link_offline() == null
				|| mensa.getMensa_link_online().equals("")
				|| mensa.getMensa_link_offline().equals("")) {

			// show that image is not avaiable
			webcamImage.setVisibility(View.VISIBLE);

		} else if (START_HOUR <= currentHour && currentHour < END_HOUR) {
			// retrieve the online image website: only between 12 and 14
			new RetrieveWebcamImageTask(this, webcamImage, refreshButton)
					.execute(mensa.getMensa_link_online());
		} else {
			// otherwise retrieve the offline image
			new RetrieveWebcamImageTask(this, webcamImage, refreshButton)
					.execute(mensa.getMensa_link_offline());
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		currentMensa = itemPosition;
		loadWebcamImage(adapter.getItem(itemPosition));
		return false;
	}

}
