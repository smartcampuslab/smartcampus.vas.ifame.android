package eu.trentorise.smartcampus.ifame.asynctask;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.WebcamAspectRatioImageView;

public class RetrieveWebcamImageTask extends AsyncTask<String, Void, Bitmap> {

	// private Context context;
	private WebcamAspectRatioImageView img_view;
	// private ProgressDialog progressDialog;

	private MenuItem refreshButton;

	public RetrieveWebcamImageTask(Context context,
			WebcamAspectRatioImageView img_view, MenuItem refresh) {
		// this.context = context;
		this.img_view = img_view;
		this.refreshButton = refresh;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// progressDialog = ProgressDialog.show(context,
		// context.getString(R.string.iFretta_title_activity),
		// context.getString(R.string.loading));
		// progressDialog.setCancelable(true);
		// progressDialog.setCanceledOnTouchOutside(false);

		if (refreshButton != null) {
			refreshButton.setActionView(R.layout.actionbar_progressbar_circle);
			refreshButton.expandActionView();
		}
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap bmap_image = null;

		try {

			InputStream in = new java.net.URL(urldisplay).openStream();
			bmap_image = BitmapFactory.decodeStream(in);

		} catch (Exception e) {
			Log.e(getClass().getName(), e.getMessage());
			return null;
		}
		return bmap_image;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			img_view.setImageBitmap(result);
		}

		img_view.setVisibility(View.VISIBLE);

		if (refreshButton != null) {
			refreshButton.collapseActionView();
			refreshButton.setActionView(null);
		}

		// progressDialog.dismiss();
	}
}