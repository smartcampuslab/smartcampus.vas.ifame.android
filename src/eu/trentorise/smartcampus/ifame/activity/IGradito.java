package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.IGraditoPiattoListAdapter;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

/**
 * This Activity shows the list of dishes for a given mensa
 */

public class IGradito extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "iGradito";

	private View view;
	private Spinner menseSpinner;
	private String actual_mensa;

	public final static String GET_FAVOURITE_CANTEEN = "GET_CANTEEN";

	List<Piatto> lista_piatti;
	IGraditoPiattoListAdapter adapter;
	SearchView searchView;
	ListView piattilist_view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_piattimensa);

		// don't show anything until the data is retrieved
		view = findViewById(R.id.igradito_piatti_mensa_view); // change to
																// relative
		// layout view if it
		// doesn't work
		view.setVisibility(View.GONE);

		piattilist_view = (ListView) findViewById(R.id.list_view_igradito);

		adapter = new IGraditoPiattoListAdapter(IGradito.this);

		piattilist_view.setFastScrollEnabled(true);
		piattilist_view.setAdapter(adapter);

		// add a listener to the list
		piattilist_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				Piatto piatto = (Piatto) adapter.getItemAtPosition(position);
				// this check piatto name is because there are headers item in
				// the list that have one character piatto name so we don't have
				// to start an activity by clicking on them
				if ((piatto.getPiatto_nome().length()) != 1) {
					Intent intent = new Intent(IGradito.this,
							IGraditoVisualizzaRecensioni.class);
					// Pass values to next activity through an intent
					intent.putExtra(IGraditoVisualizzaRecensioni.PIATTO, piatto);
					intent.putExtra(IGraditoVisualizzaRecensioni.MENSA,
							(Mensa) menseSpinner.getSelectedItem());
					startActivity(intent);
				} else {
				}
			}

		});

		// supportActionBar (Sherlock) is initialized in super.onCreate()
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (ConnectionUtils.isConnectedToInternet(getApplicationContext())) {
			new ProgressDialog(this);
			new MensaConnector(this).execute();
			new PiattiConnector(this, adapter).execute();
		} else {
			ConnectionUtils.showToastNotConnectedToInternet(this);
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.igradito, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.igradito_search)
				.getActionView();
		if (null != searchManager) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
		}

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			public boolean onQueryTextChange(String newText) {
				// this is your adapter that will be filtered
				adapter.getFilter().filter(newText);
				return true;
			}

			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				adapter.getFilter().filter(query);
				searchView.clearFocus();
				return true;
			}
		};

		searchView.setOnQueryTextListener(queryTextListener);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * case R.id.action_settings: menuItem = item; break;
		 */
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	/*
	 * 
	 * MODIFY THE SPINNER LIST ADAPTER
	 */
	public class MyCursorAdapter extends BaseAdapter implements SpinnerAdapter {
		private Activity activity;
		private List<Mensa> lista_mense;

		public MyCursorAdapter(Activity activity, List<Mensa> lista_mense) {
			this.activity = activity;
			this.lista_mense = lista_mense;
		}

		public int getCount() {
			return lista_mense.size();
		}

		public Object getItem(int position) {
			return lista_mense.get(position);
		}

		@Override
		public long getItemId(int position) {
			return lista_mense.get(position).getMensa_id();
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View spinView;
			if (convertView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				spinView = inflater.inflate(R.layout.layout_listview_igradito,
						null);
			} else {
				spinView = convertView;
			}
			TextView nome_mensa = (TextView) spinView
					.findViewById(R.id.nome_mensa);

			actual_mensa = lista_mense.get(position).getMensa_nome();
			nome_mensa.setText(actual_mensa);

			return spinView;

		}

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * THIS CONNECTOR CONNECTS TO THE WEB SERVICE AND COLLECTS THE MENSA DATA
	 */
	private class MensaConnector extends AsyncTask<Void, Void, List<Mensa>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		// public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		private ProgressDialog progressDialog;

		private static final String CLIENT_ID = "9c7ccf0a-0937-4cc8-ae51-30d6646a4445";
		private static final String CLIENT_SECRET = "f6078203-1690-4a12-bf05-0aa1d1428875";
		private String token;

		public MensaConnector(Context applicationContext) {
			context = applicationContext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "iGradito",
					"Loading...");
			SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
			try {
				token = accessProvider.readToken(IGradito.this, CLIENT_ID,
						CLIENT_SECRET);
			} catch (AACException e) {
				Log.e(TAG, "Failed to get token: " + e.getMessage());
			}
		}

		@Override
		protected List<Mensa> doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getmense");

			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, appToken, token);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					List<Mensa> list_mense = Utils.convertJSONToObjects(body,
							Mensa.class);

					return list_mense;
				} else {
					return null;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Mensa> result) {
			super.onPostExecute(result);
			if (result == null) {
				ConnectionUtils
						.showToastErrorConnectingToWebService(IGradito.this);

				finish();
			} else {
				createMenseSpinner(result);
				view.setVisibility(View.VISIBLE);
				progressDialog.dismiss();
			}
		}
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * THIS CONNECTOR CONNECTS TO THE WEB SERVICE AND COLLECTS THE DISH DATA
	 */

	private class PiattiConnector extends AsyncTask<Void, Void, List<Piatto>> {

		private ProtocolCarrier mProtocolCarrier;
		private Context context;
		private String appToken = "test smartcampus";
		private IGraditoPiattoListAdapter adapter;
		private ProgressDialog progressDialog;

		private static final String CLIENT_ID = "9c7ccf0a-0937-4cc8-ae51-30d6646a4445";
		private static final String CLIENT_SECRET = "f6078203-1690-4a12-bf05-0aa1d1428875";
		private String token;

		public PiattiConnector(Context context,
				IGraditoPiattoListAdapter adapter) {
			this.context = context;
			this.adapter = adapter;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "iGradito",
					"Loading...");

			/*
			 * get the token
			 */
			SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
			try {
				token = accessProvider.readToken(IGradito.this, CLIENT_ID,
						CLIENT_SECRET);

			} catch (AACException e) {
				Log.e(TAG, "Failed to get token: " + e.getMessage());
			}
		}

		@Override
		protected List<Piatto> doInBackground(Void... params) {
			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getpiatti");

			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, appToken, token);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					List<Piatto> lista_piatti_temp = Utils
							.convertJSONToObjects(body, Piatto.class);

					Collections.sort(lista_piatti_temp, new Comparatore());

					// creo una nuova lista che oltre a contenere i piatti
					// contiene le lettere con cui essi iniziano : ("A",
					// "Anatre", "Ananas", "B", "Banane", "D", "Datteri" ...)
					List<Piatto> lista_piatti_with_headers = new ArrayList<Piatto>();

					String letter = "A";

					// List <String> startingLetters = new ArrayList<String>();
					// startingLetters.add("A");

					lista_piatti_with_headers.add(new Piatto(letter, ""));

					for (int i = 0; i < lista_piatti_temp.size(); i++) {

						String nome_piatto = lista_piatti_temp.get(i)
								.getPiatto_nome();

						if (!nome_piatto.startsWith(letter)) {

							letter = Character.toString(nome_piatto.charAt(0));
							// startingLetters.add(letter);
							lista_piatti_with_headers
									.add(new Piatto(letter, ""));
						}

						lista_piatti_with_headers.add(lista_piatti_temp.get(i));
					}

					return lista_piatti_with_headers;
				} else {
					return null;
				}

			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Piatto> result) {
			// TODO Auto-generated method stub
			if (result == null) {
				ConnectionUtils
						.showToastErrorConnectingToWebService(IGradito.this);
				finish();
			} else {

				adapter.complete_list = result;
				adapter.clear();

				for (Piatto p : result) {
					adapter.add(p);
				}

				lista_piatti = result;

				view.setVisibility(View.VISIBLE);
				progressDialog.dismiss();
			}

		}
	}

	/*
	 * mostra le mense disponibili nello spinner
	 */
	private void createMenseSpinner(List<Mensa> listamense) {

		menseSpinner = (Spinner) findViewById(R.id.spinner_portata);
		MyCursorAdapter adapter = new MyCursorAdapter(IGradito.this, listamense);
		menseSpinner.setAdapter(adapter);

		// NOT YET IMPLEMENTED FAVOURITE MENSA FUNCTIONALITY
		// String mensa = SharedPreferencesUtils.getDefaultMensa(IGradito.this);
		// if (mensa != null) {
		// // ok avevo settato mensa preferita
		// int pos = 0;
		// for (Mensa m : listamense) {
		// if (mensa.equalsIgnoreCase(m.getMensa_nome())) {
		// mense_spinner.setSelection(pos);
		// }
		// pos++;
		// }
		// }
	}

	/*
	 * 
	 * CUSTOM ADAPTER FOR THE LIST OF CANTEENS
	 */
	private class Comparatore implements Comparator<Piatto> {

		public Comparatore() {
		}

		@Override
		public int compare(Piatto lhs, Piatto rhs) {
			final String s1 = (String) lhs.getPiatto_nome();
			final String s2 = (String) rhs.getPiatto_nome();
			return s1.compareToIgnoreCase(s2);
		}

	}
}
