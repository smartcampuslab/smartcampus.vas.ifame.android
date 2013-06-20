package eu.trentorise.smartcampus.ifame.activity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;

public class IFretta_Details extends Activity {

	private MenuItem menuItem;
	String mensa_name;

	public final static String GET_FAVOURITE_CANTEEN = "GET_CANTEEN";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();

		setContentView(R.layout.ifretta_details);

		// if there are no available intents return
		if (extras == null) {
			return;
		}

		// Get mensa intent from activity:ifretta
		mensa_name = (String) extras.get("mensa");
		setTitle(mensa_name);
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String date_s = s.format(new Date());

		String img_url = (String) extras.get("img_url"); // Get the mensa url
															// from
															// activity:ifretta

		// if there is no webcam available for the given mensa, assign an image
		// that says "not available"
		if (img_url.equals("")) {
			findViewById(R.id.imageViewID).setBackgroundResource(
					R.drawable.image_not_available);
		} else {
			// retrieve the image from unitn website
			ImageView img_view = (ImageView) findViewById(R.id.imageViewID);

			Display myDisplay = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			Rect rect = new Rect();
			Display screen = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			screen.getRectSize(rect);

			int y = rect.height();
			y = (int) ((int) y*0.77);
			img_view.getLayoutParams().height = (y);

			img_view.getLayoutParams().width = rect.width()-3;

			new RetrieveImage(img_view).execute(img_url);
		}

		// mensa_name.setTextColor(Color.parseColor("#228B22"));
		// date.setTextColor(Color.parseColor("#228B22"));
		//
		// btn.setBackgroundColor(Color.parseColor("#228B22"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ifretta_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * case R.id.action_settings: menuItem = item; break;
		 */
		case R.id.iFretta_set_as_favourite_webcam:

			SharedPreferences pref = getSharedPreferences(
					getString(R.string.iFretta_preference_file),
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(GET_FAVOURITE_CANTEEN, mensa_name);
			editor.commit();
			Toast.makeText(getApplicationContext(),
					"Hai settato la tua mensa preferita: " + mensa_name,
					Toast.LENGTH_LONG).show();
			break;

		case R.id.iFretta_search_in_ViviTrento:
			Toast.makeText(getApplicationContext(),
					"implementare collegamento a vivitrento", Toast.LENGTH_LONG)
					.show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	// It is advisable to use async task to retrieve data or perform network
	// task
	// Otherwise an exception will be thrown
	// This class will be called in the onCreate method
	private class RetrieveImage extends AsyncTask<String, Void, Bitmap> {

		ImageView img_view;

		// Constructor..
		public RetrieveImage(ImageView img_view) {
			this.img_view = img_view;
		}

		// Perform retrieval in background
		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap bmap_image = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				bmap_image = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return bmap_image;
		}

		protected void onPostExecute(Bitmap result) {
			img_view.setImageBitmap(result);
		}

	}
}
