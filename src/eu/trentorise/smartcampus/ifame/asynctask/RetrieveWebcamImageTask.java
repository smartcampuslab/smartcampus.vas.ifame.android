package eu.trentorise.smartcampus.ifame.asynctask;

import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.AspectRatioImageView;

public class RetrieveWebcamImageTask extends AsyncTask<String, Void, Bitmap> {

	private Context context;
	private AspectRatioImageView img_view;
	private ProgressDialog pd;

	public RetrieveWebcamImageTask(Context context,
			AspectRatioImageView img_view) {
		this.context = context;
		this.img_view = img_view;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = ProgressDialog.show(context,
				context.getString(R.string.iFretta_title_activity),
				context.getString(R.string.loading));
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
		img_view.setVisibility(View.VISIBLE);
		pd.dismiss();
	}

}