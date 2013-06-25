package eu.trentorise.smartcampus.ifame.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
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

public class IGradito extends Activity {

	ProgressDialog pd;
	View view;
	String address = "http://192.168.33.106:8080/web-ifame";
	//List<Mensa> listaMense = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_piattimensa);
		// set the process dialog
		pd = ProgressDialog.show(this, "Loading... ", "please wait...");

		// don't show anything until the data is retrieved
		view = findViewById(R.id.igradito_piatti_mensa_view); // change to relative
														// layout view if it
														// doesn't work		
		view.setVisibility(View.GONE);

		new MensaConnector(IGradito.this).execute();
		new PiattiConnector(IGradito.this).execute();
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
	    	    
	    nome_mensa.setText(lista_mense.get(position).getMensa_nome());
	    
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

	/*
	 * 
	 * CREATE A LIST THAT SHOWS THE VARIOUS CANTEENS WITH THE RESULTS RETRIEVED
	 */
	/*
	private void createWebcamList(List<Mensa> mense) {
		ListView ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);

		IGraditoArrayAdapter adapter = new IGraditoArrayAdapter(this,
				R.layout.layout_list_view_ifretta, mense);

		ifretta_listView.setAdapter(adapter);
		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				Mensa m = (Mensa) adapter.getItemAtPosition(position);
				Intent intent = new Intent(IGradito.this,
						IGradito_PiattiMensa_Activity.class);

				intent.putExtra("mensa", m);

				startActivity(intent);

			}
		});

	}
	*/

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
	}
	
	private void createMenseSpinner(List<Mensa> listamense){
		Spinner spinner = (Spinner) findViewById(R.id.spinner_portata);
		
		MyCursorAdapter adapter = new MyCursorAdapter(this, listamense);
		spinner.setAdapter(adapter);
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
	
	/*
	 * 
	 * CUSTOM ADAPTER FOR THE LIST OF CANTEENS
	 */

	/*
	private class IGraditoArrayAdapter extends ArrayAdapter<Mensa> {

		public IGraditoArrayAdapter(Context context, int textViewResourceId,
				List<Mensa> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.layout_list_view_ifretta,
					null);

			TextView nome_mensa = (TextView) convertView
					.findViewById(R.id.list_ifretta);
			Mensa m = getItem(position);

			nome_mensa.setText(m.getMensa_nome());

			SharedPreferences pref = getSharedPreferences(
					getString(R.string.iGradito_preference_file),
					Context.MODE_PRIVATE);
			String mensa_name = pref.getString(
					IGradito_PiattiMensa_Activity.GET_FAVOURITE_CANTEEN, "No String");

			if (m.getMensa_nome().equals(mensa_name)) {
				nome_mensa.setTypeface(null, Typeface.BOLD);
				SpannableString content = new SpannableString(m.getMensa_nome());
				content.setSpan(new UnderlineSpan(), 0, m.getMensa_nome()
						.length(), 0);
				nome_mensa.setText(content);
			} 

			return convertView;
		}

	} */
}
