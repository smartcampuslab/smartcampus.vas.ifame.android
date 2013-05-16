package eu.trentorise.smartcampus.ifame.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.ListaMense;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class IFretta extends Activity {

	ProgressDialog pd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		pd = new ProgressDialog(IFretta.this).show(IFretta.this, "Mense",
				"Loading...");

		new IFrettaConnector(IFretta.this).execute();
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ADAPTER PER IMPOSTARE IL LAYOUT DELLA LISTA DI WEBCAM
	 */

	private class MyArrayAdapter extends ArrayAdapter<Mensa> {

		public MyArrayAdapter(Context context, int textViewResourceId,
				List<Mensa> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.mensa_text, null);

			TextView nome_mensa = (TextView) convertView
					.findViewById(R.id.mensa_nameView);
			Mensa m = getItem(position);

			nome_mensa.setText(m.getMensa_name());

			return convertView;
		}

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ASYNCTASK PER COLLEGARSI AL WEB SERVICES E PRENDERE LE WEBCAM DISPONIBILI
	 */

	private class IFrettaConnector extends AsyncTask<Void, Void, ListaMense> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IFrettaConnector(Context applicationContext) {
			context = applicationContext;
		}

		private ListaMense getWebCamMense() {
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
					ListaMense list = Utils.convertJSONToObject(body,
							ListaMense.class);
					return list;
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
		protected ListaMense doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getWebCamMense();
		}

		@Override
		protected void onPostExecute(ListaMense result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createWebcamList(result);
			// quando ho i risultati del web service stoppo il caricamento
			pd.dismiss();
		}

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * CON I RISULTATI DA SERVER CREA LA LISTA DI WEBCAM
	 */
	private void createWebcamList(ListaMense list) {
		ListView ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);

		List<Mensa> mense = list.getList();

		MyArrayAdapter adapter = new MyArrayAdapter(this,
				R.layout.layout_list_view_ideciso, mense);

		ifretta_listView.setAdapter(adapter);
		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				Mensa m = (Mensa) adapter.getItemAtPosition(position);
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", m.getMensa_name());
				i.putExtra("img_url", m.getMensa_link());
				startActivity(i);

			}
		});

	}

}
