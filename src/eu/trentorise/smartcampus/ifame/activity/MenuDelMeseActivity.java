package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.PiattoKcalListAdapter;
import eu.trentorise.smartcampus.ifame.dialog.WebSearchDialog;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.MenuDelMese;
import eu.trentorise.smartcampus.ifame.model.MenuDellaSettimana;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

public class MenuDelMeseActivity extends SherlockFragmentActivity {

	private Spinner mSpinner;
	private MenuDelMese menuDelMese;
	private PiattoKcalListAdapter mPiattiListAdapter;
	private WebSearchDialog webSearchDialog;
	private ArrayAdapter<String> mSpinnerAdapter;
	private Calendar mCalendar;
	private SimpleDateFormat dateFormat;

	//private MenuItem refresh;

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getSupportMenuInflater();
	// inflater.inflate(R.menu.menu_only_loading, menu);
	// refresh = menu.findItem(R.id.action_refresh);
	// return true;
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_mese);

		webSearchDialog = new WebSearchDialog();
		mCalendar = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("dd/MM/yy");

		if (ConnectionUtils.isUserConnectedToInternet(this)) {
			new MenuDelMeseConnector().execute();
		} else {
			ConnectionUtils
					.errorToastTnternetConnectionNeeded(getApplicationContext());
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
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				String piattoName = ((Piatto) parent
						.getItemAtPosition(position)).getPiatto_nome();
				// check because there are some fake piatti as header
				if (!piattoName.matches("[0-9]+")) {
					showWebSearchDialog(piattoName);
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);

	}

	/*
	 * 
	 * 
	 * CONNECTOR MENU DEL MESE
	 */
	private class MenuDelMeseConnector extends
			AsyncTask<Void, Void, MenuDelMese> {

		private ProgressDialog progressDialog;

		private final String URL_BASE_WEB_IFAME;
		private final String APP_TOKEN;

		public MenuDelMeseConnector() {
			URL_BASE_WEB_IFAME = getString(R.string.URL_BASE_WEB_IFAME);
			APP_TOKEN = getString(R.string.APP_TOKEN);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// DISPLAY A PROGRESSDIALOG
			progressDialog = ProgressDialog.show(MenuDelMeseActivity.this,
					getString(R.string.iDeciso_home_monthly_menu),
					getString(R.string.loading));
			// progressDialog.setCancelable(true);
			// progressDialog.setCanceledOnTouchOutside(false);
			// progressDialog.setOnCancelListener(new OnCancelListener() {
			// @Override
			// public void onCancel(DialogInterface dialog) {
			// onBackPressed();
			// }
			// });
			// if (refresh != null) {
			// refresh.setActionView(R.layout.actionbar_progressbar_circle);
			// refresh.expandActionView();
			// }
		}

		@Override
		protected MenuDelMese doInBackground(Void... params) {
			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(
					getApplicationContext(), APP_TOKEN);
			MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
					"getmenudelmese");
			request.setMethod(Method.GET);
			try {
				MessageResponse response = mProtocolCarrier.invokeSync(request,
						APP_TOKEN, IFameMain.getAuthToken());
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
			// if (refresh != null) {
			// refresh.setActionView(null);
			// refresh.collapseActionView();
			// }
			if (mdm == null) {
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				// setto il menu del mese ricevuto come variabile di classe
				menuDelMese = mdm;
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
				progressDialog.dismiss();
			}
		}
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
		List<MenuDellaSettimana> menuOfTheWeekList = menuDelMese
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

	/** display dialog search on Google the selected dish name */
	private void showWebSearchDialog(String piatto_name) {
		Bundle args = new Bundle();
		args.putString(WebSearchDialog.PIATTO_NAME, piatto_name);
		webSearchDialog.setArguments(args);
		webSearchDialog.show(getSupportFragmentManager(),
				"StartWebSearchDialog");
	}
}
