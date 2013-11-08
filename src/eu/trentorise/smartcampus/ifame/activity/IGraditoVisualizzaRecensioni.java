package eu.trentorise.smartcampus.ifame.activity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.ReviewListAdapter;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.ifame.utils.GiudizioComparator;
import eu.trentorise.smartcampus.ifame.utils.SharedPreferencesUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class IGraditoVisualizzaRecensioni extends SherlockFragmentActivity {

	public static final String MENSA = "mensa_extra";
	public static final String PIATTO = "piatto_extra";

	private MenuItem menuItem;
	private ReviewListAdapter adapter;
	private Piatto piatto;
	private Mensa mensa;
	private TextView giudizio_espresso_da_textview;
	private TextView giudizio_medio_textview;
	private TextView no_data_to_display_textview;
	private ListView giudiziListview;

	private Integer mioVoto;
	private String mioCommento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_pagina_recensioni);

		giudiziListview = (ListView) findViewById(R.id.recensioni_list);

		giudizio_espresso_da_textview = (TextView) findViewById(R.id.giudizio_espresso_da_value);
		giudizio_medio_textview = (TextView) findViewById(R.id.giudizio_voto_medio_value);
		no_data_to_display_textview = (TextView) findViewById(R.id.giudizio_no_data_to_display);

		// Get extras parameters from the igradito activity
		Bundle extras = getIntent().getExtras();
		mensa = (Mensa) extras.get(MENSA);
		piatto = (Piatto) extras.get(PIATTO);

		mioVoto = 5;
		mioCommento = "";

		// display piatto name as title
		setTitle(piatto.getPiatto_nome());

		// setup action bar
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// get list of comments
		if (ConnectionUtils.isUserConnectedToInternet(this)) {
			new GetGiudizioConnector().execute(mensa.getMensa_id(),
					piatto.getPiatto_id());
			// just to be sure that userId is saved in sharedpreferences
			SharedPreferencesUtils
					.retrieveAndSaveUserId(IGraditoVisualizzaRecensioni.this);
		} else {
			Toast.makeText(IGraditoVisualizzaRecensioni.this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.igradito_recensioni_menu_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			onClickActionRefresh(item);
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_add_comments:
			showInsertReviewDialog();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	/**
	 * Show dialog for insert or edit a review
	 */
	private void showInsertReviewDialog() {

		FragmentManager fragmentManager = getSupportFragmentManager();
		InsertReviewDialog insertReviewDialog = new InsertReviewDialog();

		// put the data needed for showing the dialog in a bundle
		Bundle dataForTheDialog = new Bundle();
		dataForTheDialog.putSerializable(InsertReviewDialog.MENSA, mensa);
		dataForTheDialog.putSerializable(InsertReviewDialog.PIATTO, piatto);
		dataForTheDialog.putInt(InsertReviewDialog.VOTO, mioVoto);
		dataForTheDialog.putString(InsertReviewDialog.COMMENTO, mioCommento);
		dataForTheDialog.putString(InsertReviewDialog.USERID,
				SharedPreferencesUtils
						.getUserID(IGraditoVisualizzaRecensioni.this));

		// pass the bundle to the dialog and show
		insertReviewDialog.setArguments(dataForTheDialog);
		insertReviewDialog.show(fragmentManager, "insertReviewDialog");
	}

	private void onClickActionRefresh(MenuItem item) {
		menuItem = item;
		menuItem.setActionView(R.layout.actionbar_progressbar_circle);
		menuItem.expandActionView();

		// check for connection and get the reviews
		if (ConnectionUtils.isUserConnectedToInternet(this)) {
			// clear the adapter
			if (adapter != null) {
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
			// get the reviews
			new GetGiudizioConnector().execute(mensa.getMensa_id(),
					piatto.getPiatto_id());
		} else {
			Toast.makeText(IGraditoVisualizzaRecensioni.this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			menuItem.collapseActionView();
			menuItem.setActionView(null);
		}
	}

	/**
	 * CONNECTOR TO GET THE LIST OF COMMENTS
	 */

	private class GetGiudizioConnector extends
			AsyncTask<Long, Void, List<Giudizio>> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog
					.show(IGraditoVisualizzaRecensioni.this, "iGradito",
							"Loading...");
			// progressDialog.setCancelable(true);
			// progressDialog.setCanceledOnTouchOutside(false);
		}

		@Override
		protected List<Giudizio> doInBackground(Long... params) {

			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(
					IGraditoVisualizzaRecensioni.this,
					getString(R.string.APP_TOKEN));

			MessageRequest request = new MessageRequest(
					getString(R.string.URL_BASE_WEB_IFAME), "/mensa/"
							+ params[0] + "/piatto/" + params[1] + "/giudizio");
			request.setMethod(Method.GET);

			try {
				MessageResponse response = mProtocolCarrier
						.invokeSync(request, getString(R.string.APP_TOKEN),
								IFameMain.getAuthToken());

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
					List<Giudizio> list = Utils.convertJSONToObjects(body,
							Giudizio.class);
					return list;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Giudizio> result) {
			super.onPostExecute(result);
			if (result == null) {
				progressDialog.dismiss();
				Toast.makeText(IGraditoVisualizzaRecensioni.this,
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				createGiudiziList(result);
				progressDialog.dismiss();
			}
		}
	}

	/*
	 * 
	 * METHOD CALLED AFTER POST OR GET GIUDIZI FROM THE EWB
	 */
	public void createGiudiziList(List<Giudizio> reviews) {

		// sort the reviews by date
		Collections.sort(reviews, new GiudizioComparator());

		int review_size = reviews.size();
		float avg = 0;
		Long user_id = Long.parseLong(SharedPreferencesUtils
				.getUserID(IGraditoVisualizzaRecensioni.this));

		if (review_size > 0) {
			// count the average of this dish voto reviews
			for (Giudizio g : reviews) {
				// calcolo la media
				avg += g.getVoto();
				if (g.getUser_id() == user_id) {
					mioCommento = g.getCommento();
					mioVoto = Math.round(g.getVoto());
				}
			}
			avg = avg / (float) review_size;
			giudizio_medio_textview.setText(ReviewListAdapter
					.formatUserVoto(avg));

			// check per scrivere 4 users or 4 utenti
			// se è inglese (en, en_US, en_UK, en_CA) allora inglese altrimenti
			// italiano

			giudizio_espresso_da_textview.setText(review_size
					+ " "
					+ (review_size == 1 ? getString(R.string.iGradito_user)
							: getString(R.string.iGradito_users)));

			// clean results list -> don't show reviews without the comment
			Iterator<Giudizio> i = reviews.iterator();
			while (i.hasNext()) {
				if (i.next().getCommento().equals("")) {
					i.remove();
				}
			}

			// init or update the adapter
			if (adapter == null) {
				// intialize the adapter
				adapter = new ReviewListAdapter(this,
						SharedPreferencesUtils.getUserID(this), reviews);
				giudiziListview.setAdapter(adapter);
			} else {
				// clear and add the reviews at the adapter
				adapter.clear();
				for (Giudizio giudizio : reviews) {
					adapter.add(giudizio);
				}
				adapter.notifyDataSetChanged();
			}

			// if there are only reviews without comments show message
			if (adapter.getCount() == 0) {
				giudiziListview.setVisibility(View.GONE);
				no_data_to_display_textview
						.setText(getString(R.string.iGradito_no_one_left_comment));
				no_data_to_display_textview.setVisibility(View.VISIBLE);
			} else {
				giudiziListview.setVisibility(View.VISIBLE);
				no_data_to_display_textview.setVisibility(View.GONE);
			}

		} else {
			// no review are avaiable
			mioCommento = "";
			mioVoto = 5;
			giudiziListview.setVisibility(View.GONE);
			no_data_to_display_textview
					.setText(getString(R.string.iGradito_no_reviews_available));
			no_data_to_display_textview.setVisibility(View.VISIBLE);
		}

		// PER IL CARICAMENTO NELL ACTION BAR
		if (menuItem != null) {
			menuItem.collapseActionView();
			menuItem.setActionView(null);
		}
	}
}