package eu.trentorise.smartcampus.ifame.activity;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.asynctask.RetrieveWebcamImageTask;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamAspectRatioImageView;

public class IFrettaDetails extends SherlockActivity {

	/** Logging tag */
	@SuppressWarnings("unused")
	private static final String TAG = "iFrettaDetails";

	/** Is the key of the mensa object needed to be passed as extra */
	public static final String MENSA = "mensa_extra";

	private static final int START_HOUR = 12;
	private static final int END_HOUR = 14;

	private WebcamAspectRatioImageView webcamImage;
	private Mensa mensa;
	private MenuItem refreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta_details);
		webcamImage = (WebcamAspectRatioImageView) findViewById(R.id.imageViewId);

		// GET THE MENSA OBJECT FROM IFRETTA
		Bundle extras = getIntent().getExtras();
		mensa = (Mensa) extras.get(MENSA);

		setTitle(mensa.getMensa_nome());

		// get current time
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

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
			// simply redo the same check as in the oncreate before retrieve the
			// image
			int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

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
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
