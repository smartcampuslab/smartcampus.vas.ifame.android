package eu.trentorise.smartcampus.ifame.activity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaSpinnerAdapter;
import eu.trentorise.smartcampus.ifame.adapter.ReviewListAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.PostGiudizioAsyncTask;
import eu.trentorise.smartcampus.ifame.comparator.GiudizioComparator;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog.InsertReviewDialogListener;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

public class IGraditoVisualizzaRecensioni extends SherlockFragmentActivity
		implements OnNavigationListener, InsertReviewDialogListener {

	public static final String PIATTO = "piatto_extra";

	private ReviewListAdapter reviewListAdapter;
	private Piatto piatto;

	private InsertReviewDialog insertReviewDialog;

	private TextView giudizio_espresso_da_textview;
	private TextView giudizio_medio_textview;
	private TextView no_data_to_display_textview;

	private ListView giudiziListview;

	private LinearLayout no_data_to_display_wrapper;

	private Integer mioVoto;
	private String mioCommento;

	private MenuItem refreshButton;

	private MensaSpinnerAdapter mensaSpinnerAdapter;
	private TextView nome_piatto_label;

	private int currentTabSelected;

	public MenuItem getRefreshButton() {
		return refreshButton;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.igradito_recensioni_menu_item, menu);
		refreshButton = menu.findItem(R.id.action_refresh);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;

		case R.id.action_refresh:
			return true;

		case R.id.action_add_comments:
			showInsertReviewDialog();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_pagina_recensioni);

		giudiziListview = (ListView) findViewById(R.id.recensioni_list);

		giudizio_espresso_da_textview = (TextView) findViewById(R.id.giudizio_espresso_da_value);
		giudizio_medio_textview = (TextView) findViewById(R.id.giudizio_voto_medio_value);

		no_data_to_display_textview = (TextView) findViewById(R.id.giudizio_no_data_to_display);
		no_data_to_display_wrapper = (LinearLayout) findViewById(R.id.giudizio_no_data_to_display_wrapper_layout);

		// Get extras parameters from the igradito activity
		Bundle extras = getIntent().getExtras();
		piatto = (Piatto) extras.getSerializable(PIATTO);

		// just to be sure to have it before add the review
		UserIdUtils.retrieveAndSaveUserId(this);

		mioVoto = 5;
		mioCommento = "";

		nome_piatto_label = (TextView) findViewById(R.id.nome_piatto_label);
		nome_piatto_label.setText(piatto.getPiatto_nome());

		mensaSpinnerAdapter = new MensaSpinnerAdapter(this);
		List<Mensa> listaMense = MensaUtils.getMensaList(this);
		for (Mensa mensa : listaMense) {
			mensaSpinnerAdapter.add(mensa);
		}

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

	/**
	 * Show dialog for insert or edit a review
	 */
	private void showInsertReviewDialog() {

		if (insertReviewDialog == null) {
			insertReviewDialog = new InsertReviewDialog();
		}

		// put the data needed for showing the dialog in a bundle
		Bundle args = new Bundle();
		args.putSerializable(InsertReviewDialog.MENSA, mensaSpinnerAdapter
				.getItem(getSupportActionBar().getSelectedNavigationIndex()));
		args.putSerializable(InsertReviewDialog.PIATTO, piatto);
		args.putInt(InsertReviewDialog.VOTO, mioVoto);
		args.putString(InsertReviewDialog.COMMENTO, mioCommento);

		// pass the bundle to the dialog and show
		insertReviewDialog.setArguments(args);
		insertReviewDialog.show(getSupportFragmentManager(),
				"insertReviewDialog");
	}

	/**
	 * CONNECTOR TO GET THE LIST OF COMMENTS
	 */

	private class RetrieveGiudiziTask extends
			AsyncTask<Long, Void, List<Giudizio>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			IFameUtils.setActionBarLoading(refreshButton);
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
					return Utils.convertJSONToObjects(response.getBody(),
							Giudizio.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Giudizio> result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(IGraditoVisualizzaRecensioni.this,
						getString(R.string.errorLoadingReviews),
						Toast.LENGTH_SHORT).show();
			} else {
				createGiudiziList(result);
			}
			IFameUtils.removeActionBarLoading(refreshButton);
			refreshButton.setEnabled(false);
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
				no_data_to_display_wrapper.setVisibility(View.VISIBLE);
			} else {
				giudiziListview.setVisibility(View.VISIBLE);

				no_data_to_display_textview.setVisibility(View.GONE);
				no_data_to_display_wrapper.setVisibility(View.GONE);
			}

		} else {
			// no review are avaiable
			mioCommento = "";
			mioVoto = 5;

			giudiziListview.setVisibility(View.GONE);

			no_data_to_display_textview
					.setText(getString(R.string.iGradito_no_reviews_available));
			no_data_to_display_textview.setVisibility(View.VISIBLE);
			no_data_to_display_wrapper.setVisibility(View.VISIBLE);

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

	@Override
	public void postReview(DialogInterface dialog, String commento, int voto,
			Long mensa, Long piatto) {

		Long userId = Long.parseLong(UserIdUtils.getUserId(this));
		GiudizioDataToPost data = new GiudizioDataToPost(commento,
				(float) voto, userId);

		new PostGiudizioAsyncTask(IGraditoVisualizzaRecensioni.this, data,
				refreshButton, mensa, piatto).execute();
	}

}