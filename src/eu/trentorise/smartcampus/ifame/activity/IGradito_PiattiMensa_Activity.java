package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto_Mensa;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class IGradito_PiattiMensa_Activity extends Activity {

	ProgressDialog pd;
	private Spinner portataSpinner;
	private Mensa mensa;
	String mensa_name;
	IGraditoAdapter adapter;
	List<Piatto_Mensa> lista_piatti;
	public final static String GET_FAVOURITE_CANTEEN = "GET_CANTEEN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_piattimensa);

		// Aggiungo lo spinner
		portataSpinner = (Spinner) findViewById(R.id.spinner_portata);
		portataSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapter,
							View view, int position, long id) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}

				});

		Bundle extras = getIntent().getExtras();

		// if there are no available intents return
		if (extras == null) {
			return;
		}

		// Get mensa intent from activity:ifretta
		mensa = (Mensa) extras.get("mensa");
		mensa_name = mensa.getMensa_nome();
		setTitle(mensa.getMensa_nome());

		ListView list_view = (ListView) findViewById(R.id.list_view_igradito);

		adapter = new IGraditoAdapter(IGradito_PiattiMensa_Activity.this,
				android.R.layout.simple_list_item_1);

		list_view.setAdapter(adapter);

		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {

				Piatto_Mensa piatto = (Piatto_Mensa) adapter.getItemAtPosition(position);
				Intent i = new Intent(IGradito_PiattiMensa_Activity.this,
						Recensioni_Activity.class);
				i.putExtra("nome_piatto", piatto.getPiatto().getPiatto_nome());
				startActivity(i);
			}
		});

		new IGraditoConnector(getApplicationContext(), adapter).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.igradito, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu
				.findItem(R.id.igradito_search).getActionView();
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
		case R.id.iGradito_set_favourite_canteen:

			SharedPreferences pref = getSharedPreferences(
					getString(R.string.iGradito_preference_file),
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(GET_FAVOURITE_CANTEEN, mensa_name);
			editor.commit();
			
			Toast.makeText(getApplicationContext(),
					"Hai settato come preferita: " + mensa_name,
					Toast.LENGTH_LONG).show();
			break;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	/*
	 * 
	 * 
	 * 
	 * ADAPTER PER IGRADITO
	 */

	private class IGraditoAdapter extends ArrayAdapter<Piatto_Mensa> implements
			Filterable {

		List<Piatto_Mensa> complete_list = new ArrayList<Piatto_Mensa>();

		public IGraditoAdapter(Context context, int textViewResourceId) {
			super(IGradito_PiattiMensa_Activity.this, textViewResourceId);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			Piatto_Mensa p = getItem(position);

			convertView = inflater.inflate(layout.igradito_layout_row_adapter,
					null);

			TextView piatto_name = (TextView) convertView
					.findViewById(R.id.piatto_name_adapter);
			TextView piatto_avg = (TextView) convertView
					.findViewById(R.id.piatto_avgvote_adapter);

			piatto_name.setText(p.getPiatto().getPiatto_nome());
			piatto_avg.setText(p.getVoto_medio().toString());

			return convertView;
			
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {

				@SuppressWarnings("unchecked")
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults results = new FilterResults();

					// We implement here the filter logic

					if (constraint == null || constraint.length() == 0) {
						// No filter implemented we return all the list
						results.values = complete_list;
						results.count = complete_list.size();
					} else {
						// We perform filtering operation

						List<Piatto_Mensa> piatti = new ArrayList<Piatto_Mensa>();

						for (Piatto_Mensa p : complete_list) {

							if (p.getPiatto()
									.getPiatto_nome()
									.toUpperCase()
									.contains(
											constraint.toString().toUpperCase())) {
								piatti.add(p);
								// System.out.println("Il piatto è: "+
								// p.getPiatto().getPiatto_nome());

							}
						}

						results.values = piatti;
						// System.out.println("I piatti sono: " +
						// results.values);
						results.count = piatti.size();
						// System.out.println("Numero: " + results.count);

					}
					return results;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {

					// Now we have to inform the adapter about the new list
					// filtered

					adapter.clear();

					if (results.count > 0) {

						for (Piatto_Mensa p : (List<Piatto_Mensa>) results.values) {

							adapter.add(p);

						}

						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			};
			return filter;

		}

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * CONNECTOR TO GETALLPIATTI WEB SERVICE
	 */

	private class IGraditoConnector extends
			AsyncTask<Void, Void, List<Piatto_Mensa>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		IGraditoAdapter adapter;

		public IGraditoConnector(Context applicationContext,
				IGraditoAdapter adapter) {
			context = applicationContext;
			this.adapter = adapter;
		}

		private List<Piatto_Mensa> getPiattiList() {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"iGradito/" + mensa.getMensa_id());
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					List<Piatto_Mensa> lista_piatti = Utils
							.convertJSONToObjects(body, Piatto_Mensa.class);

					return lista_piatti;

				} else {

				}
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(getApplicationContext()).show(
					IGradito_PiattiMensa_Activity.this, "IGradito",
					"Loading...");
			super.onPreExecute();
		}

		@Override
		protected List<Piatto_Mensa> doInBackground(Void... params) {
			return getPiattiList();
		}

		@Override
		protected void onPostExecute(List<Piatto_Mensa> result) {
			// TODO Auto-generated method stub

			if (result != null) {

				adapter.complete_list = result;

				adapter.clear();
				for (Piatto_Mensa p : result) {

					adapter.add(p);

				}
				System.out.println("Sono nel onpostexecute");
				lista_piatti = result;
			}

			pd.dismiss();
		}

	}

}
