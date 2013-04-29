package eu.trentorise.smartcampus.ifame.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import eu.trentorise.smartcampus.ifame.model.Mensa;

public class IFretta_Details extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.ifretta_details);

		if (extras == null) {
			return;
		}

		Mensa m = (Mensa) extras.get("mensa");
		TextView mensa_name = (TextView) findViewById(R.id.mensa_name_textview);
		final TextView date = (TextView) findViewById(R.id.date_text_view);

		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String date_s = s.format(new Date());

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

		mensa_name.setText(String.valueOf(m));
		date.setText(date_s);

		String img_url = (String) extras.get("img_url");
		
		if(img_url.equals("")){
			findViewById(R.id.imageViewID).setBackgroundResource(R.drawable.image_not_available);
		}	else {
			new RetrieveImage((ImageView) findViewById(R.id.imageViewID)).execute(img_url); 
		}

		// mensa_name.setTextColor(Color.parseColor("#228B22"));
		// date.setTextColor(Color.parseColor("#228B22"));
		//
		// btn.setBackgroundColor(Color.parseColor("#228B22"));
	}

	private class RetrieveImage extends AsyncTask<String, Void, Bitmap> {

		ImageView img_view;

		public RetrieveImage(ImageView img_view) {
			this.img_view = img_view;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			img_view.setImageBitmap(result);
		}

	}
}
