package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.PiattoKcalListAdapter;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog;
import eu.trentorise.smartcampus.ifame.dialog.OptionsMenuDialog;
import eu.trentorise.smartcampus.ifame.dialog.OptionsMenuDialog.OptionsMenuDialogListener;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.MenuDelMese;
import eu.trentorise.smartcampus.ifame.model.MenuDellaSettimana;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

public class MenuDelMeseActivity extends SherlockFragmentActivity implements
		OptionsMenuDialogListener {

	private Spinner mSpinner;
	private MenuDelMese menuDelMese;
	private PiattoKcalListAdapter mPiattiListAdapter;

	private ArrayAdapter<String> mSpinnerAdapter;
	private Calendar mCalendar;
	private SimpleDateFormat dateFormat;

	private MenuItem refreshButton;

	// private WebSearchDialog webSearchDialog;
	private OptionsMenuDialog optionsDialog;
	private InsertReviewDialog insertReviewDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_mese);

		mCalendar = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("dd/MM/yy");

		if (IFameUtils.isUserConnectedToInternet(MenuDelMeseActivity.this)) {
			new MenuDelMeseConnector().execute();

		} else {
			Toast.makeText(MenuDelMeseActivity.this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// setup title
		String[] months = { getString(R.string.iDeciso_monthly_menu_january),
				getString(R.string.iDeciso_monthly_menu_february),
				getString(R.string.iDeciso_monthly_menu_march),
				getString(R.string.iDeciso_monthly_menu_april),
				getString(R.string.iDeciso_monthly_menu_may),
				getString(R.string.iDeciso_monthly_menu_june),
				getString(R.string.iDeciso_monthly_menu_july),
				getString(R.string.iDeciso_monthly_menu_august),
				getString(R.string.iDeciso_monthly_menu_september),
				getString(R.string.iDeciso_monthly_menu_october),
				getString(R.string.iDeciso_monthly_menu_november),
				getString(R.string.iDeciso_monthly_menu_december) };

		setTitle(getString(R.string.iDeciso_monthly_menu_of) + " "
				+ months[mCalendar.get(Calendar.MONTH)]);

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// setup the listview
		ListView piattiListView = (ListView) findViewById(R.id.menu_of_the_day);
		mPiattiListAdapter = new PiattoKcalListAdapter(this);
		piattiListView.setAdapter(mPiattiListAdapter);
		piattiListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1,
					int position, long arg3) {
				Piatto piatto = (Piatto) adapter.getItemAtPosition(position);
				// check because there are some fake piatti as header
				if (!piatto.getPiatto_nome().matches("[0-9]+")) {
					// showWebSearchDialog(piattoName);
					showOptionsDialog(piatto);
				}
			}
		});

		// setup the spinner
		mSpinner = (Spinner) findViewById(R.id.spinner_settimana);
		mSpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int position, long id) {
				String customFormatDateString = (String) adapter
						.getItemAtPosition(position);
				refreshPiattiList(spinnerStringToStartDayOfTheWeek(customFormatDateString));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// DO NOTHING
			}
		});

		// just to be sure to have it before add the review
		UserIdUtils.retrieveAndSaveUserId(MenuDelMeseActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_only_loading_progress,
				menu);
		setRefreshButton(menu.findItem(R.id.action_refresh));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * 
	 * 
	 * CONNECTOR MENU DEL MESE
	 */
	private class MenuDelMeseConnector extends
			AsyncTask<Void, Void, MenuDelMese> {

		private LinearLayout progressBarLayout;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// DISPLAY A PROGRESSDIALOG
			progressBarLayout = IFameUtils
					.setProgressBarLayout(MenuDelMeseActivity.this);
		}

		@Override
		protected MenuDelMese doInBackground(Void... params) {
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(
					MenuDelMeseActivity.this, getString(R.string.APP_TOKEN));
			MessageRequest request = new MessageRequest(
					getString(R.string.URL_BASE_WEB_IFAME), "/getmenudelmese");
			request.setMethod(Method.GET);
			try {
				MessageResponse response = mProtocolCarrier
						.invokeSync(request, getString(R.string.APP_TOKEN),
								IFameMain.getAuthToken());
				if (response.getHttpStatus() == 200) {
					return Utils.convertJSONToObject(response.getBody(),
							MenuDelMese.class);
				}
			} catch (Exception e) {
				Log.e(MenuDelMeseConnector.class.getName(),
						"Failed to get the monthly menu: " + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(MenuDelMese mdm) {
			super.onPostExecute(mdm);

			if (mdm == null) {
				// c'è stato un errore nel ricevere il menu del mese
				Toast.makeText(MenuDelMeseActivity.this,
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
				finish();
				return;

			} else {
				// setto il menu del mese ricevuto come variabile di classe
				setMenuDelMese(mdm);
				// cerco la settimana corrente e la mostro
				int currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
				// ciclo sulle settimane e prendo tutti i piatti della settimana
				int weekCount = 0;
				int spinnerPosition = 0;
				for (MenuDellaSettimana m : mdm.getMenuDellaSettimana()) {

					int start_day = m.getStart_day();
					int end_day = m.getEnd_day();
					// setto l'item dello spinner
					mSpinnerAdapter.add(dayToMyFormat(start_day, end_day));
					// se il giorno corrente è tra il giorno iniziale e quelo
					// finale
					// della settimana sono nella settimana che mi interessa
					if (currentDay >= start_day && currentDay <= end_day) {
						spinnerPosition = weekCount;
					}
					weekCount++;
				}
				mSpinner.setSelection(spinnerPosition);
				mSpinner.setVisibility(View.VISIBLE);
			}

			progressBarLayout.setVisibility(View.GONE);
		}
	}

	public MenuDelMese getMenuDelMese() {
		return this.menuDelMese;
	}

	private void setMenuDelMese(MenuDelMese mdm) {
		this.menuDelMese = mdm;
	}

	public MenuItem getRefreshButton() {
		return refreshButton;
	}

	public void setRefreshButton(MenuItem refreshButton) {
		this.refreshButton = refreshButton;
	}

	/**
	 * Gets the start day of the week from the string shown on the spinner
	 * 
	 * @param customFormattedString
	 *            must be in the form -> [from] dd/mm/yy [to] dd/mm/yy
	 */
	private int spinnerStringToStartDayOfTheWeek(String customFormattedString) {
		return Integer.parseInt(customFormattedString.split("\\s")[1].split(
				"/", 2)[0]);
	}

	/**
	 * Format string in a custom format for showing in the spinner from dd/mm/yy
	 * to dd/mm/yy
	 */
	private String dayToMyFormat(int startDay, int endDay) {
		mCalendar.set(Calendar.DAY_OF_MONTH, startDay);
		String start_day_string = dateFormat.format(mCalendar.getTime());
		mCalendar.set(Calendar.DAY_OF_MONTH, endDay);
		String end_day_string = dateFormat.format(mCalendar.getTime());

		return getString(R.string.iDeciso_monthly_menu_from) + " "
				+ start_day_string + " "
				+ getString(R.string.iDeciso_monthly_menu_to) + " "
				+ end_day_string;
	}

	/**
	 * Update the list of dishes shown when a user select another week on the
	 * spinner
	 */
	private void refreshPiattiList(int weekStartDay) {
		mPiattiListAdapter.clear();
		// mPiattiListAdapter.notifyDataSetChanged();
		// prendo la lista di menu della settimana
		List<MenuDellaSettimana> menuOfTheWeekList = getMenuDelMese()
				.getMenuDellaSettimana();
		// per ogni giorno della settimana
		for (MenuDellaSettimana mds : menuOfTheWeekList) {
			if (weekStartDay == mds.getStart_day()) {
				// sono nella settimana interessata ciclo sui menu del
				// giorno poi esco dal ciclo
				ArrayList<MenuDelGiorno> menuOfTheDayList = (ArrayList<MenuDelGiorno>) mds
						.getMenuDelGiorno();
				for (MenuDelGiorno mdg : menuOfTheDayList) {
					// ATTENZIONE AL MAGHEGGIO
					Piatto piattoSentinella = new Piatto();
					// setto come nome del piatto il numero del giorno
					piattoSentinella.setPiatto_nome(mdg.getDay() + "");
					// del menu del giorno che sto iterando perche mi serve
					// come sentinella nell'adapter
					mPiattiListAdapter.add(piattoSentinella);
					// aggiungo tutti gli altri piatti
					// occhio alla compatibilità
					for (Piatto p : mdg.getPiattiDelGiorno()) {
						mPiattiListAdapter.add(p);
					}
				}
				// esco dal ciclo
				break;
			}
		}
		mPiattiListAdapter.notifyDataSetChanged();
	}

	/** display dialog options */
	private void showOptionsDialog(Piatto piatto) {

		if (optionsDialog == null) {
			optionsDialog = new OptionsMenuDialog();
		}

		Bundle args = new Bundle();
		args.putSerializable(OptionsMenuDialog.PIATTO, piatto);

		optionsDialog.setArguments(args);
		optionsDialog.show(getSupportFragmentManager(), "OptionsMenuDialog");
	}

	/**
	 * Show dialog for insert or edit a review
	 */
	private void showInsertReviewDialog(Piatto piatto) {

		if (insertReviewDialog == null) {
			insertReviewDialog = new InsertReviewDialog();
		}

		// put the data needed for showing the dialog in a bundle
		Bundle args = new Bundle();
		args.putSerializable(InsertReviewDialog.PIATTO, piatto);
		args.putInt(InsertReviewDialog.VOTO, 5);
		args.putString(InsertReviewDialog.COMMENTO, "");

		// pass the bundle to the dialog and show
		insertReviewDialog.setArguments(args);
		insertReviewDialog.show(getSupportFragmentManager(),
				"insertReviewDialog");
	}

	@Override
	public void onClickOptionsMenuDialog(DialogInterface dialog, int position,
			Piatto piatto) {
		switch (position) {

		case OptionsMenuDialog.VIEW_REVIEW:
			Intent viewReviews = new Intent(MenuDelMeseActivity.this,
					IGraditoVisualizzaRecensioni.class);
			viewReviews.putExtra(IGraditoVisualizzaRecensioni.PIATTO, piatto);
			startActivity(viewReviews);
			break;

		case OptionsMenuDialog.RATE_OR_REVIEW:
			showInsertReviewDialog(piatto);
			break;

		case OptionsMenuDialog.SEARCH_GOOGLE:
			Intent searchGoogle = new Intent(Intent.ACTION_WEB_SEARCH);
			searchGoogle.putExtra(SearchManager.QUERY, piatto.getPiatto_nome());
			startActivity(searchGoogle);
			break;

		default:
			break;
		}

	}

}
