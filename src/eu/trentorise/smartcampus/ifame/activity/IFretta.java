package eu.trentorise.smartcampus.ifame.activity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaSpinnerAdapter;
import eu.trentorise.smartcampus.ifame.model.CanteenOpeningTimes;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamAspectRatioImageView;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class IFretta extends SherlockActivity implements OnNavigationListener {

	private MenuItem refreshButton;
	private MensaSpinnerAdapter adapter;
	private int currentTabSelected;
	private int selectedMensa;
	private ProgressBar progress;
	private Button oraribtn;
	private WebcamAspectRatioImageView webcamImage;

	private List<String> aperture;
	private TextView textViewOrari;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifretta_details);

		webcamImage = (WebcamAspectRatioImageView) findViewById(R.id.imageViewId);
		progress = (ProgressBar) findViewById(R.id.progressBar1);

		adapter = new MensaSpinnerAdapter(IFretta.this);
		for (Mensa mensa : MensaUtils.getMensaList(IFretta.this)) {
			adapter.add(mensa);
		}
		// setup actionbar (supportActionBar is initialized in super.onCreate())
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(adapter, IFretta.this);

		// select current mensa
		selectedMensa = adapter.getPosition(MensaUtils
				.getFavouriteMensa(IFretta.this));
		actionBar.setSelectedNavigationItem(selectedMensa);
		aperture = new ArrayList<String>();
		oraribtn = (Button) findViewById(R.id.buttonOrari);
		// check opening
		isCanteenOpen();
		

	}

	private void isCanteenOpen() {
		// orari
		Date datan = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String data = sdf.format(datan);
		Boolean openMes1 = false;
		Boolean openMes2 = false;
		Boolean openPovo1 = false;
		Boolean openPovoV = false;
		Boolean openTG = false;
		Boolean openZan = false;
		
		final List<String> apertureMes1 = new ArrayList<String>();;
		final List<String> apertureMes2 = new ArrayList<String>();;
		final List<String> aperturePovo1 = new ArrayList<String>();;
		final List<String> aperturePovoV = new ArrayList<String>();;
		final List<String> apertureTG = new ArrayList<String>();;
		final List<String> apertureZan = new ArrayList<String>();;

		textViewOrari = (TextView) findViewById(R.id.textViewOrari);
		// int index = getSupportActionBar().getSelectedNavigationIndex();

		// per ogni mensa
		for (Mensa mensa : MensaUtils.getMensaList(IFretta.this)) {
			// if
			// (mensa.getMensa_nome().compareTo(MensaUtils.getMensaList(IFretta.this).get(index).getMensa_nome())==0){
			for (CanteenOpeningTimes ot : mensa.getTimes()) {
				if ((mensa.getMensa_nome().compareTo("Mesiano 1") == 0)
						&& (ot.getType().compareTo("Pranzo") == 0)) {
					for (String orari : ot.getDates()) {
						apertureMes1.add(orari);						
						if (orari.compareTo(data.toString()) == 0)
							openMes1 = true;
					}
				}else
				if ((mensa.getMensa_nome().compareTo("Mesiano 1") == 0)
						&& (ot.getType().compareTo("Bar (orario 7:30-18)") == 0)) {
					for (String orari : ot.getDates()) {
						apertureMes2.add(orari);
						if (orari.compareTo(data.toString()) == 0)
							openMes2 = true;
					}
				}
				else
				if ((mensa.getMensa_nome().compareTo("Povo Mensa Veloce") == 0)
						&& (ot.getType().compareTo("Pranzo (linea standard)") == 0)) {
					for (String orari : ot.getDates()) {
						aperturePovo1.add(orari);
						if (orari.compareTo(data.toString()) == 0)
							openPovo1 = true;
					}
				}else
				if ((mensa.getMensa_nome().compareTo("Povo Mensa Veloce") == 0)
						&& (ot.getType().compareTo("Pranzo (linea veloce)") == 0)) {
					for (String orari : ot.getDates()) {
						aperturePovoV.add(orari);
						if (orari.compareTo(data.toString()) == 0)
							openPovoV = true;
					}

				}else
				if ((mensa.getMensa_nome().compareTo("Tommaso Gar.") == 0)
						&& (ot.getType().compareTo("Pranzo (linea standard)") == 0)) {
					for (String orari : ot.getDates()) {
						apertureTG.add(orari);
						if (orari.compareTo(data.toString()) == 0)
							openTG = true;
					}

				}else
				if ((mensa.getMensa_nome().compareTo("Zanella") == 0)
						&& (ot.getType().compareTo("Pranzo") == 0)) {
					for (String orari : ot.getDates()) {
						apertureZan.add(orari);
						if (orari.compareTo(data.toString()) == 0)
							openZan = true;
					}
				}
				
				
			}
		}

		switch (selectedMensa) {

		case 0:
			oraribtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IFretta.this, OrariAperturaActivity.class);
					intent.putStringArrayListExtra("aperture", (ArrayList<String>) apertureMes1);
					IFretta.this.startActivity(intent);
				}
			});
			
			if (openMes1) {
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_open_mensa));
			} else {
				textViewOrari.setTextColor(getResources().getColor(
						R.color.pressed_ifamestyle));
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_close_mensa));
			}
		case 1:
			oraribtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IFretta.this, OrariAperturaActivity.class);
					intent.putStringArrayListExtra("aperture", (ArrayList<String>) apertureMes2);
					IFretta.this.startActivity(intent);
				}
			});
			if (openMes2) {
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_open_mensa));
			} else {
				textViewOrari.setTextColor(getResources().getColor(
						R.color.pressed_ifamestyle));
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_close_mensa));
			}
		case 2:
			oraribtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IFretta.this, OrariAperturaActivity.class);
					intent.putStringArrayListExtra("aperture", (ArrayList<String>) aperturePovo1);
					IFretta.this.startActivity(intent);
				}
			});
			if (openPovo1) {
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_open_mensa));
			} else {
				textViewOrari.setTextColor(getResources().getColor(
						R.color.pressed_ifamestyle));
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_close_mensa));
			}
		case 3:
			oraribtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IFretta.this, OrariAperturaActivity.class);
					intent.putStringArrayListExtra("aperture", (ArrayList<String>) aperturePovoV);
					IFretta.this.startActivity(intent);
				}
			});
			if (openPovoV) {
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_open_mensa));
			} else {
				textViewOrari.setTextColor(getResources().getColor(
						R.color.pressed_ifamestyle));
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_close_mensa));
			}
		case 4:
			oraribtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IFretta.this, OrariAperturaActivity.class);
					intent.putStringArrayListExtra("aperture", (ArrayList<String>) apertureTG);
					IFretta.this.startActivity(intent);
				}
			});
			if (openTG) {
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_open_mensa));
			} else {
				textViewOrari.setTextColor(getResources().getColor(
						R.color.pressed_ifamestyle));
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_close_mensa));
			}
		case 5:
			oraribtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(IFretta.this, OrariAperturaActivity.class);
					intent.putStringArrayListExtra("aperture", (ArrayList<String>) apertureZan);
					IFretta.this.startActivity(intent);
				}
			});
			if (openZan) {
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_open_mensa));
			} else {
				textViewOrari.setTextColor(getResources().getColor(
						R.color.pressed_ifamestyle));
				textViewOrari.setText(getResources().getText(
						R.string.iFretta_close_mensa));
			}

		}
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
				selectedMensa = adapter.getPosition(MensaUtils
						.getFavouriteMensa(IFretta.this));
				isCanteenOpen();
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadWebcamImage(Mensa mensa) {

		if (IFameUtils.isUserConnectedToInternet(IFretta.this)) {
			if ((mensa.getMensa_link_offline() == null)
					|| (mensa.getMensa_link_online() == null)) {
				Toast.makeText(IFretta.this, getString(R.string.noCamera),
						Toast.LENGTH_SHORT).show();
			} else {
				Date now = new Date();
				if (now.after(getStart()) && now.before(getEnd())) {
					// retrieve the online image
					new RetrieveWebcamImageTask().execute(mensa
							.getMensa_link_online());
				} else {
					// otherwise retrieve the offline image
					new RetrieveWebcamImageTask().execute(mensa
							.getMensa_link_offline());
				}
			}
		} else {
			// show image image not avaiable
			Toast.makeText(IFretta.this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
		}

	}

	private static Date getStart() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 11);
		c.set(Calendar.MINUTE, 55);
		return c.getTime();
	}

	private static Date getEnd() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 14);
		c.set(Calendar.MINUTE, 30);
		return c.getTime();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// se sono connesso richiedo i giudizi
		if (IFameUtils.isUserConnectedToInternet(IFretta.this)) {
			currentTabSelected = itemPosition;
			loadWebcamImage(adapter.getItem(itemPosition));
			isCanteenOpen();
		} else {
			// non sono connesso mostro il toast e torno alla tab precedente
			// controllo l'item per evitare la ricorsione di chiamate
			if (currentTabSelected != itemPosition) {

				getSupportActionBar().setSelectedNavigationItem(
						currentTabSelected);

				Toast.makeText(IFretta.this,
						getString(R.string.errorInternetConnectionRequired),
						Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	private class RetrieveWebcamImageTask extends
			AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			IFameUtils.setActionBarLoading(refreshButton);
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap bmap_image = null;

			try {

				InputStream in = new java.net.URL(urldisplay).openStream();
				bmap_image = BitmapFactory.decodeStream(in);

			} catch (Exception e) {
				// Log.e(getClass().getName(), e.getMessage());
				return null;
			}
			return bmap_image;
		}

		@Override
		protected void onPostExecute(Bitmap result) {

			if (result != null) {
				webcamImage.setImageBitmap(result);
				webcamImage.setVisibility(View.VISIBLE);

			} else {
				Toast.makeText(IFretta.this,
						getString(R.string.errorLoadingWebcamImage),
						Toast.LENGTH_SHORT).show();
			}
			progress.setVisibility(View.GONE);
			IFameUtils.removeActionBarLoading(refreshButton);
		}
	}

}
