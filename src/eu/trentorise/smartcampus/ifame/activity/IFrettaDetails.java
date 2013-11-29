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
import eu.trentorise.smartcampus.ifame.asynctask.RetrieveWebcamImageTask;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamAspectRatioImageView;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class IFrettaDetails extends SherlockActivity implements
		OnNavigationListener {

	private static final int START_HOUR = 12;
	private static final int END_HOUR = 14;

	// private Mensa mensa;
	private MenuItem refreshButton;
	private MensaSpinnerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta_details);

		adapter = new MensaSpinnerAdapter(this);
		adapter.add(MensaUtils.getFavouriteMensa(IFrettaDetails.this));

		for (Mensa mensa : MensaUtils.getMensaList(IFrettaDetails.this)) {
			adapter.add(mensa);
		}

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(adapter, this);
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
			loadWebcamImage(adapter.getItem(getSupportActionBar()
					.getSelectedNavigationIndex()));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadWebcamImage(Mensa mensa) {
		WebcamAspectRatioImageView webcamImage = (WebcamAspectRatioImageView) findViewById(R.id.imageViewId);

		if (ConnectionUtils.isUserConnectedToInternet(this)) {
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

		} else {
			webcamImage.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		loadWebcamImage(adapter.getItem(itemPosition));
		return false;
	}

}
