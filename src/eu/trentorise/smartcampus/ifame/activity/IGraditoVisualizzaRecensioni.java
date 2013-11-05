package eu.trentorise.smartcampus.ifame.activity;

import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
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
import eu.trentorise.smartcampus.ifame.utils.SharedPreferencesUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;


////////////////7
//per la compatibilità guarda tu!!!!!!!!!!!!!!!!!!
///////////////
@SuppressLint("NewApi")
public class IGraditoVisualizzaRecensioni extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "RecensioniActivity";

	public static final String MENSA = "mensa_extra";
	public static final String PIATTO = "piatto_extra";

	private MenuItem menuItem;
	private ReviewListAdapter adapter;
	private Piatto piatto;
	private Mensa mensa;
	private TextView giudizio_espresso_da;
	private TextView giudizio_medio_txt;
	private TextView no_data_to_display;
	private ListView giudiziListview;

	private Integer mioVoto;
	private String mioCommento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_pagina_recensioni);

		giudiziListview = (ListView) findViewById(R.id.recensioni_list);
		giudizio_espresso_da = (TextView) findViewById(R.id.espresso_da);
		giudizio_medio_txt = (TextView) findViewById(R.id.giudizio);
		no_data_to_display = (TextView) findViewById(R.id.giudizio_no_data_to_display);

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
			new GetGiudizioConnector(IGraditoVisualizzaRecensioni.this)
					.execute(mensa.getMensa_id(), piatto.getPiatto_id());
			// just to be sure that userId is saved in sharedpreferences
			SharedPreferencesUtils
					.retrieveAndSaveUserId(IGraditoVisualizzaRecensioni.this);
		} else {
			Toast.makeText(getApplicationContext(),
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

		FragmentManager fragmentManager = getFragmentManager();
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

	@SuppressLint("NewApi")
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
			new GetGiudizioConnector(IGraditoVisualizzaRecensioni.this)
					.execute(mensa.getMensa_id(), piatto.getPiatto_id());
		} else {
			Toast.makeText(getApplicationContext(),
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
		private ProtocolCarrier mProtocolCarrier;
		private ProgressDialog progressDialog;
		private Context context;
		private String appToken = "test smartcampus";


		public GetGiudizioConnector(Context applicationContext) {
			context = applicationContext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "iGradito",
					"Loading...");
		}

		@Override
		protected List<Giudizio> doInBackground(Long... params) {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"https://smartcampuswebifame.app.smartcampuslab.it/",
					"mensa/" + params[0] + "/piatto/" + params[1] + "/giudizio");

			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, appToken, IFameMain.getAuthToken());

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
					List<Giudizio> list = Utils.convertJSONToObjects(body,
							Giudizio.class);
					return list;
				} else {
					return null;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Giudizio> result) {
			super.onPostExecute(result);
			if (result == null) {
				progressDialog.dismiss();
				Toast.makeText(context,
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
			} else {
				createGiudiziList(result);
			}
			progressDialog.dismiss();
		}
	}

	/*
	 * 
	 * METHOD CALLED AFTER POST OR GET GIUDIZI FROM THE EWB
	 */
	public void createGiudiziList(List<Giudizio> reviews) {

		int review_size = reviews.size();
		float avg = 0;
		Long user_id = Long.parseLong(SharedPreferencesUtils
				.getUserID(IGraditoVisualizzaRecensioni.this));

		if (review_size > 0) {
			for (Giudizio g : reviews) {
				// calcolo la media
				avg += g.getVoto();
				if (g.getUser_id() == user_id) {
					mioCommento = g.getCommento();
					mioVoto = Math.round(g.getVoto());
				}
			}
			avg = avg / (float) review_size;

			// non mostro i commenti vuoti
			Iterator<Giudizio> i = reviews.iterator();
			while (i.hasNext()) {
				if (i.next().getCommento().equals("")) {
					i.remove();
				}
			}

			if (adapter == null) {
				adapter = new ReviewListAdapter(
						IGraditoVisualizzaRecensioni.this,
						SharedPreferencesUtils
								.getUserID(IGraditoVisualizzaRecensioni.this),
						reviews);
				giudiziListview.setAdapter(adapter);
			} else {
				adapter.clear();
				adapter.addAll(reviews);
			}
			giudizio_espresso_da.setText(review_size + " utent"
					+ (review_size == 1 ? "e" : "i"));
			giudizio_medio_txt.setText(avg + "");
			adapter.notifyDataSetChanged();
			// se ho solo recensioni senza commenti
			if (adapter.getCount() == 0) {
				giudiziListview.setVisibility(View.GONE);
				no_data_to_display
						.setText("Nessun utente lasciato un commento!");
				no_data_to_display.setVisibility(View.VISIBLE);
			} else {
				giudiziListview.setVisibility(View.VISIBLE);
				no_data_to_display.setVisibility(View.GONE);
			}
		} else {
			mioCommento = "";
			mioVoto = 5;
			giudiziListview.setVisibility(View.GONE);
			no_data_to_display.setVisibility(View.VISIBLE);
		}
		// PER IL CARICAMENTO NELL ACTION BAR
		if (menuItem != null) {
			menuItem.collapseActionView();
			menuItem.setActionView(null);
		}
	}
}