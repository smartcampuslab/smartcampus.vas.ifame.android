package eu.trentorise.smartcampus.ifame.activity;
import java.util.List;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This Activity shows the list of dishes for a given mensa
 */


public class IGradito extends Activity {

	ProgressDialog pd; 
	View view; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_mensalist);
		//set the process dialog
		pd = ProgressDialog.show(this, "Loading... ",
				"please wait...");
		
		//don't show anything until the data is retrieved
		view = findViewById(R.id.ifretta_page_list); //change to relative layout view if it doesn't work
		view.setVisibility(View.GONE);
		
		new IGraditoConnector(IGradito.this).execute();
	}

	
	/*
	 * 
	 * THIS CONNECTOR CONNECTS TO THE WEB SERVICE AND COLLECTS THE MENSA DATA
	 */
	
	private class IGraditoConnector extends AsyncTask<Void, Void, List<Mensa>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IGraditoConnector(Context applicationContext) {
			context = applicationContext;
		}

		private List<Mensa> getWebCamMense() {
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
			return getWebCamMense();
		}

		@Override
		protected void onPostExecute(List<Mensa> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createWebcamList(result);
			// once the data is available dismiss the dialog
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

	}
	
	/*
	 * 
	 * CREATE A LIST THAT SHOWS THE VARIOUS CANTEENS WITH THE RESULTS RETRIEVED
	 */
	private void createWebcamList(List<Mensa> mense) {
		ListView ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);

		IGraditoArrayAdapter adapter = new IGraditoArrayAdapter(this,
				R.layout.layout_list_view_ifretta, mense);

		ifretta_listView.setAdapter(adapter);
		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				Mensa m = (Mensa) adapter.getItemAtPosition(position);
				Intent intent = new Intent(IGradito.this, IGradito_PiattiMensa_Activity.class);
								
				intent.putExtra("mensa", m);
				
				startActivity(intent);

			}
		});

	}
	
	/*
	 * 
	 * CUSTOM ADAPTER FOR THE LIST OF CANTEENS
	 */

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

	}
}
