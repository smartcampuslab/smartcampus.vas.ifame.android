package eu.trentorise.smartcampus.ifame.activity;

import java.util.Calendar;

import android.os.Bundle;
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
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class IFrettaDetails extends SherlockActivity implements
		OnNavigationListener {

	private static final int START_HOUR = 12;
	private static final int END_HOUR = 14;

	// private Mensa mensa;
	private MenuItem refreshButton;
	private MensaSpinnerAdapter adapter;
	private int currentTabSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta_details);

		adapter = new MensaSpinnerAdapter(IFrettaDetails.this);
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
		actionBar.setListNavigationCallbacks(adapter, IFrettaDetails.this);

		// select current mensa
		int selected = adapter.getPosition(MensaUtils
				.getFavouriteMensa(IFrettaDetails.this));
		actionBar.setSelectedNavigationItem(selected);
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
			// a check if happens that favourite canteens are not set
			int index = getSupportActionBar().getSelectedNavigationIndex();
			if (index >= 0) {
				loadWebcamImage(adapter.getItem(index));
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadWebcamImage(Mensa mensa) {
		WebcamAspectRatioImageView webcamImage = (WebcamAspectRatioImageView) findViewById(R.id.imageViewId);

		if (IFameUtils.isUserConnectedToInternet(IFrettaDetails.this)) {
			// get current hour
			int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

			if (START_HOUR <= currentHour && currentHour < END_HOUR) {
				// retrieve the online image website: only between 12 and 14
				new RetrieveWebcamImageTask(IFrettaDetails.this, webcamImage,
						refreshButton).execute(mensa.getMensa_link_online());
			} else {
				// otherwise retrieve the offline image
				new RetrieveWebcamImageTask(IFrettaDetails.this, webcamImage,
						refreshButton).execute(mensa.getMensa_link_offline());
			}
		} else {
			// show image image not avaiable
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// se sono connesso richiedo i giudizi
		if (IFameUtils.isUserConnectedToInternet(IFrettaDetails.this)) {
			currentTabSelected = itemPosition;
			loadWebcamImage(adapter.getItem(itemPosition));
		} else {
			// non sono connesso mostro il toast e torno alla tab precedente
			// controllo l'item per evitare la ricorsione di chiamate
			if (currentTabSelected != itemPosition) {

				getSupportActionBar().setSelectedNavigationItem(
						currentTabSelected);

				Toast.makeText(IFrettaDetails.this,
						getString(R.string.errorInternetConnectionRequired),
						Toast.LENGTH_SHORT).show();
			}
		}

		return false;
	}

}
