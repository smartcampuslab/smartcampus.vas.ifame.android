package eu.trentorise.smartcampus.ifame.activity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;

public class IFretta_Details extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.ifretta_details);

		//if there are no available intents  return
		if (extras == null) {
			return;
		}

		//Get mensa intent from activity:ifretta
	//	Mensa m = (Mensa) extras.get("mensa");
		TextView mensa_name = (TextView) findViewById(R.id.mensa_name_textview);
		final TextView date = (TextView) findViewById(R.id.date_text_view);

		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String date_s = s.format(new Date());

		//the onclick method is for a demonstraive purpose, time is updated when the button is clicked
		Button btn = (Button) findViewById(R.id.refresh_button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				String date_s = s.format(new Date());
				date.setText(date_s);
			}
		});

		//mensa_name.setText(String.valueOf(m));
		date.setText(date_s);

		String img_url = (String) extras.get("img_url"); //Get the mensa url  from activity:ifretta
		
		//if there is no webcam available for the given mensa, assign an image that says "not available"
		if(img_url.equals("")){
			findViewById(R.id.imageViewID).setBackgroundResource(R.drawable.image_not_available);
		}	else {
			//retrieve the image from unitn website
			ImageView img_view = (ImageView) findViewById(R.id.imageViewID);
			img_view.getLayoutParams().height = 600; //Set size of the retrieved image 
			img_view.getLayoutParams().width = 420; //set size of the retrieved image
			new RetrieveImage(img_view).execute(img_url); 
		}

		// mensa_name.setTextColor(Color.parseColor("#228B22"));
		// date.setTextColor(Color.parseColor("#228B22"));
		//
		// btn.setBackgroundColor(Color.parseColor("#228B22"));
	}
	
	//It is advisable to use async task to retrieve data or perform network task
	//Otherwise an exception will be thrown 
	//This class will be called in the onCreate method
	private class RetrieveImage extends AsyncTask<String, Void, Bitmap> {

		ImageView img_view;

		//Constructor..
		public RetrieveImage(ImageView img_view) {
			this.img_view = img_view;
		}

		//Perform retrieval in background
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
