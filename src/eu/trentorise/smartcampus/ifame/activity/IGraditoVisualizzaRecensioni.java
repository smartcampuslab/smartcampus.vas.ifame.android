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

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaSpinnerAdapter;
import eu.trentorise.smartcampus.ifame.adapter.ReviewListAdapter;
import eu.trentorise.smartcampus.ifame.comparator.GiudizioComparator;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class IGraditoVisualizzaRecensioni extends SherlockFragmentActivity
		implements OnNavigationListener {

	public static final String PIATTO = "piatto_extra";

	private ReviewListAdapter reviewListAdapter;
	private Piatto piatto;

	private InsertReviewDialog insertReviewDialog;

	private TextView giudizio_espresso_da_textview;
	private TextView giudizio_medio_textview;
	private TextView no_data_to_display_textview;
	private ListView giudiziListview;

	private Integer mioVoto;
	private String mioCommento;

	private MensaSpinnerAdapter mensaSpinnerAdapter;
	private TextView nome_piatto_label;

	private int currentTabSelected;

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
		piatto = (Piatto) extras.get(PIATTO);

		mioVoto = 5;
		mioCommento = "";

		nome_piatto_label = (TextView) findViewById(R.id.nome_piatto_label);
		nome_piatto_label.setText(piatto.getPiatto_nome());

		mensaSpinnerAdapter = new MensaSpinnerAdapter(this);
		List<Mensa> listaMense = MensaUtils.getMensaList(this);
		for (Mensa mensa : listaMense) {
			mensaSpinnerAdapter.add(mensa);
		}

		// just to be sure that userId is saved in sharedpreferences
		UserIdUtils.retrieveAndSaveUserId(this);

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(mensaSpinnerAdapter, this);

		// select favourite mensa by default
		actionBar.setSelectedNavigationItem(mensaSpinnerAdapter
				.getPosition(MensaUtils.getFavouriteMensa(this)));
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

		if (insertReviewDialog == null) {
			insertReviewDialog = new InsertReviewDialog();
		}

		// put the data needed for showing the dialog in a bundle
		Bundle dataForTheDialog = new Bundle();
		dataForTheDialog.putSerializable(InsertReviewDialog.MENSA,
				mensaSpinnerAdapter.getItem(getSupportActionBar()
						.getSelectedNavigationIndex()));
		dataForTheDialog.putSerializable(InsertReviewDialog.PIATTO, piatto);
		dataForTheDialog.putInt(InsertReviewDialog.VOTO, mioVoto);
		dataForTheDialog.putString(InsertReviewDialog.COMMENTO, mioCommento);

		// pass the bundle to the dialog and show
		insertReviewDialog.setArguments(dataForTheDialog);
		insertReviewDialog.show(fragmentManager, "insertReviewDialog");
	}

	/**
	 * CONNECTOR TO GET THE LIST OF COMMENTS
	 */

	private class RetrieveGiudiziTask extends
			AsyncTask<Long, Void, List<Giudizio>> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(
					IGraditoVisualizzaRecensioni.this,
					getString(R.string.iGradito_title_activity),
					getString(R.string.loading));
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
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
						getString(R.string.errorLoadingReviews),
						Toast.LENGTH_SHORT).show();
				// finish();
			} else {
				createGiudiziList(result);
				progressDialog.dismiss();
			}
		}
	}

	/**
	 * 
	 * METHOD CALLED AFTER POST OR GET GIUDIZI FROM THE EWB
	 */
	public void createGiudiziList(List<Giudizio> reviews) {

		// sort the reviews by date
		Collections.sort(reviews, new GiudizioComparator());

		int review_size = reviews.size();
		float avg = 0;
		Long user_id = Long.parseLong(UserIdUtils
				.getUserId(IGraditoVisualizzaRecensioni.this));

		if (review_size > 0) {
			// count the average of this dish voto reviews
			for (Giudizio g : reviews) {
				// calcolo la media
				avg += g.getVoto();
				if (g.getUser_id() == user_id) {
					mioCommento = g.getTesto();
					mioVoto = Math.round(g.getVoto());
				}
			}
			avg = avg / (float) review_size;
			giudizio_medio_textview.setText(ReviewListAdapter
					.formatUserVoto(avg));

			giudizio_espresso_da_textview.setText(review_size
					+ " "
					+ (review_size == 1 ? getString(R.string.iGradito_user)
							: getString(R.string.iGradito_users)));

			// clean results list -> don't show reviews without the comment
			Iterator<Giudizio> i = reviews.iterator();
			while (i.hasNext()) {
				if (i.next().getTesto().equals("")) {
					i.remove();
				}
			}

			// init or update the adapter
			if (reviewListAdapter == null) {
				// intialize the adapter
				reviewListAdapter = new ReviewListAdapter(this,
						UserIdUtils.getUserId(this), reviews);
				giudiziListview.setAdapter(reviewListAdapter);
			} else {
				// clear and add the reviews at the adapter
				reviewListAdapter.clear();
				for (Giudizio giudizio : reviews) {
					reviewListAdapter.add(giudizio);
				}
				reviewListAdapter.notifyDataSetChanged();
			}

			// if there are only reviews without comments show message
			if (reviewListAdapter.getCount() == 0) {
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

			String notAvailable = getString(R.string.iGradito_not_available);

			giudizio_medio_textview.setText(notAvailable);
			giudizio_espresso_da_textview.setText(notAvailable);
		}

	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// se sono connesso richiedo i giudizi
		if (IFameUtils.isUserConnectedToInternet(this)) {
			currentTabSelected = itemPosition;
			new RetrieveGiudiziTask().execute(
					mensaSpinnerAdapter.getItem(itemPosition).getMensa_id(),
					piatto.getPiatto_id());
		} else {
			// non sono connesso mostro il toast e torno alla tab precedente
			// controllo l'item per evitare la ricorsione di chiamate
			if (currentTabSelected != itemPosition) {

				getSupportActionBar().setSelectedNavigationItem(
						currentTabSelected);

				Toast.makeText(IGraditoVisualizzaRecensioni.this,
						getString(R.string.errorInternetConnectionRequired),
						Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}
}