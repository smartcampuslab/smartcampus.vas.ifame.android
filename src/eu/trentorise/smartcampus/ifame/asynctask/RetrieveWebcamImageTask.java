package eu.trentorise.smartcampus.ifame.asynctask;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class RetrieveWebcamImageTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView img_view;

	public RetrieveWebcamImageTask(ImageView img_view) {
		this.img_view = img_view;
	}

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

	@Override
	protected void onPostExecute(Bitmap result) {
		img_view.setImageBitmap(result);
	}

}