package eu.trentorise.smartcampus.ifame.activity;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
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

	private ProgressDialog pd;
	private View view;
	private String user_id;
	private Spinner mense_spinner;
	String actual_mensa;
	public final static String GET_FAVOURITE_CANTEEN = "GET_CANTEEN";
	//List<Mensa> listaMense = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_piattimensa);
		
		//Get user_id intent
		Bundle extras = getIntent().getExtras(); 
		if(extras == null){
			return; 
		}
		
		user_id = (String) extras.get("user_id");
		
		
		// don't show anything until the data is retrieved
		view = findViewById(R.id.igradito_piatti_mensa_view); // change to relative
														// layout view if it
														// doesn't work		
		view.setVisibility(View.GONE);

		new MensaConnector(IGradito.this).execute();
		new PiattiConnector(IGradito.this).execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.igradito, menu);

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
				//adapter.getFilter().filter(newText);
				return true;
			}

			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				//adapter.getFilter().filter(query);

				return true;
			}
		};

		searchView.setOnQueryTextListener(queryTextListener);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * case R.id.action_settings: menuItem = item; break;
		 */
		case android.R.id.home: onBackPressed();
		break;
		case R.id.iGradito_set_favourite_canteen:

			SharedPreferences pref = getSharedPreferences(
					getString(R.string.iGradito_preference_file),
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(GET_FAVOURITE_CANTEEN, actual_mensa);
			editor.commit();
			
			Toast.makeText(getApplicationContext(),
					"Hai settato come preferita: " + actual_mensa,
					Toast.LENGTH_LONG).show();
			break;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	
	/**
	 * 
	 * MODIFY THE SPINNER LIST ADAPTER
	 *
	 */
	public class MyCursorAdapter extends BaseAdapter implements SpinnerAdapter{
	    private Activity activity;
	    private List<Mensa> lista_mense; 

	    public MyCursorAdapter(Activity activity, List<Mensa> lista_mense){
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
	    if( convertView == null ){
	        LayoutInflater inflater = activity.getLayoutInflater();
	        spinView = inflater.inflate(R.layout.layout_listview_igradito, null);
	    } else {
	         spinView = convertView;
	    }
	    TextView nome_mensa = (TextView) spinView.findViewById(R.id.nome_mensa);
	    
	    actual_mensa = lista_mense.get(position).getMensa_nome();
	    nome_mensa.setText(actual_mensa);
	    
	    
	    return spinView;
	    
	    }

	}

	/*
	 * 
	 * THIS CONNECTOR CONNECTS TO THE WEB SERVICE AND COLLECTS THE MENSA DATA
	 */
	private class MensaConnector extends AsyncTask<Void, Void, List<Mensa>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public MensaConnector(Context applicationContext) {
			context = applicationContext;
		}

		private List<Mensa> getMense() {
			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getmense");

			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					List<Mensa> list_mense = Utils.convertJSONToObjects(body,
							Mensa.class);

					return list_mense;
				} else {
					return null;
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
		protected List<Mensa> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getMense();
		}

		@Override
		protected void onPostExecute(List<Mensa> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createMenseSpinner(result);
			// once the data is available dismiss the dialog
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

	}

	/*
	 * 
	 * THIS CONNECTOR CONNECTS TO THE WEB SERVICE AND COLLECTS THE DISH DATA
	 */

	private class PiattiConnector extends AsyncTask<Void, Void, List<Piatto>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public PiattiConnector(Context applicationContext) {
			context = applicationContext;
		}

		private List<Piatto> getPiatti() {
			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getpiatti");

			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					List<Piatto> lista_piatti = Utils.convertJSONToObjects(
							body, Piatto.class);

					return lista_piatti;
				} else {
					return null;
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
					IGradito.this, "IGradito",
					"Loading...");
			super.onPreExecute();
		}

		@Override
		protected List<Piatto> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getPiatti();
		}

		@Override
		protected void onPostExecute(List<Piatto> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createPiattiList(result);
			// once the data is available dismiss the dialog
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

	}


	/**
	 * 
	 * 
	 * CREATE A LIST THAT SHOWS THE VARIOUS PIATTI
	 * 
	 */

	private void createPiattiList(List<Piatto> piatti) {
		ListView piattilist_view = (ListView) findViewById(R.id.list_view_igradito);

		PiattiListAdapter adapter = new PiattiListAdapter(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				piatti);
		
		piattilist_view.setAdapter(adapter); 
		
		//add a listener to the list 
		piattilist_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Piatto piatto = (Piatto) adapter.getItemAtPosition(position);

				Mensa spinner_mensa_value = (Mensa) mense_spinner.getSelectedItem();
				
				//Pass values to next activity through an intent
				Intent intent = new Intent(IGradito.this, Recensioni_Activity.class);
				intent.putExtra("nome_piatto", piatto);
				intent.putExtra("user_id", user_id);
				intent.putExtra("igradito_spinner_mense", spinner_mensa_value);
				startActivity(intent);
			}
			
		});
	}
	
	private void createMenseSpinner(List<Mensa> listamense){
		mense_spinner = (Spinner) findViewById(R.id.spinner_portata);
		
		MyCursorAdapter adapter = new MyCursorAdapter(this, listamense);
		mense_spinner.setAdapter(adapter);
 
	}
	

	/*
	 * 
	 * CUSTOM ADAPTER FOR THE LIST OF CANTEENS
	 */

	private class PiattiListAdapter extends ArrayAdapter<Piatto> {

		public PiattiListAdapter(Context context, int textViewResourceId,
				List<Piatto> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.layout_row_menu_adapter,
					null);

			Piatto piattoDelGiorno = getItem(position);

			TextView nome_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_name_adapter);

			TextView kcal_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_kcal_adapter);

			nome_piatto_del_giorno.setText(piattoDelGiorno.getPiatto_nome());
			kcal_piatto_del_giorno.setText(piattoDelGiorno.getPiatto_kcal());

			return convertView;
		}

	}
}
