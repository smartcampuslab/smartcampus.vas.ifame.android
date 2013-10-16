package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.asynctask.RetrieveImage;
import eu.trentorise.smartcampus.ifame.model.Mensa;

public class IFrettaDetails extends SherlockActivity {

	/** Logging tag */
	@SuppressWarnings("unused")
	private static final String TAG = "iFrettaDetails";

	/** Is the key of the mensa object passed as extra */
	public static final String MENSA_EXTRA = "mensa";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta_details);
		ImageView webcamImage = (ImageView) findViewById(R.id.imageViewID);

		// GET THE MENSA OBJECT FROM IFRETTA
		Bundle extras = getIntent().getExtras();
		Mensa mensa = (Mensa) extras.get(MENSA_EXTRA);

		setTitle(mensa.getMensa_nome());

		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String date_s = s.format(new Date());

		// GET CURRENT TIME
		Calendar c = Calendar.getInstance();
		int current_hour = c.get(Calendar.HOUR_OF_DAY);
		int start_hour = 12;
		int end_hour = 14;

		// if there is no webcam available for the given mensa, assign an image
		// that says "not available"
		if ((mensa.getMensa_link_online().equals(""))
				|| (mensa.getMensa_link_offline().equals(""))) {
			findViewById(R.id.imageViewID).setBackgroundResource(
					R.drawable.image_not_available);

		} else if (start_hour <= current_hour && current_hour < end_hour) {
			// retrieve the image from unitn website

			Rect rect = new Rect();

			Display screen = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			screen.getRectSize(rect);

			int y = rect.height();
			y = (int) ((int) y * 0.77);
			webcamImage.getLayoutParams().height = (y);

			webcamImage.getLayoutParams().width = rect.width() - 3;

			new RetrieveImage(webcamImage)
					.execute(mensa.getMensa_link_online());

		} else {

			Rect rect = new Rect();
			Display screen = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();

			screen.getRectSize(rect);

			int y = rect.height();
			y = (int) ((int) y * 0.77);
			webcamImage.getLayoutParams().height = (y);
			webcamImage.getLayoutParams().width = rect.width() - 3;

			new RetrieveImage(webcamImage).execute(mensa
					.getMensa_link_offline());
		}

		// actionBarSherlock is initialized in super.onCreate()
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// NOT YET IMPLEMENTED FAVOURITE MENSA FUNCTIONALITY
		// MenuInflater inflater = getSupportMenuInflater();
		// inflater.inflate(R.menu.ifretta_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
			// NOT YET IMPLEMENTED FAVOURITE MENSA FUNCTIONALITY
			// case R.id.iFretta_set_as_favourite_webcam:
			// SharedPreferencesUtils.setDefaultMensa(this, mensa_name);
			// break;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
