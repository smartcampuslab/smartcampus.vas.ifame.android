package eu.trentorise.smartcampus.ifame.activity;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.ifame.utils.SharedPreferencesUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class IFretta extends SherlockActivity {

	ProgressDialog progressDialog;
	ListView ifretta_listView;
	MyArrayAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);

		adapter = new MyArrayAdapter(this, R.layout.layout_list_view_ifretta);

		new ProgressDialog(this);

		if (ConnectionUtils.isOnline(getApplicationContext())) {
			new IFrettaConnector(IFretta.this).execute();
		} else {
			ConnectionUtils.showToastNotConnected(this);
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// nel caso in cui si cambi la mensa preferita in "ifretta details"
		// questo comando assicura che si apportino le opportune modifiche
		adapter.notifyDataSetChanged();
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
	 * 
	 * 
	 * 
	 * 
	 * ADAPTER PER IMPOSTARE IL LAYOUT DELLA LISTA DI WEBCAM
	 */

	private class MyArrayAdapter extends ArrayAdapter<Mensa> {

		public MyArrayAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.layout_list_view_ifretta,
					null);

			TextView text_view_nome_mensa = (TextView) convertView
					.findViewById(R.id.list_ifretta);
			Mensa m = getItem(position);

			text_view_nome_mensa.setText(m.getMensa_nome());

			String favourite_mensa_name = SharedPreferencesUtils
					.getDefaultMensa(IFretta.this);

			// se la mensa preferita salvata, è quella che stiamo esaminando,
			// allora la sottolineamo e ci aggiungiamo l'icona star
			if (favourite_mensa_name != null
					&& m.getMensa_nome().equals(favourite_mensa_name)) {

				Drawable icon_to_right = convertView.getResources()
						.getDrawable(android.R.drawable.star_off);
				text_view_nome_mensa.setCompoundDrawablesWithIntrinsicBounds(
						null, null, icon_to_right, null);
				text_view_nome_mensa.setTypeface(null, Typeface.BOLD);
				// SpannableString content = new
				// SpannableString(m.getMensa_nome());
				// content.setSpan(new UnderlineSpan(), 0, m.getMensa_nome()
				// .length(), 0);
				// nome_mensa.setText(m.getMensa_nome());
			}
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

	private class IFrettaConnector extends AsyncTask<Void, Void, List<Mensa>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IFrettaConnector(Context applicationContext) {
			context = applicationContext;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "iFretta",
					"Loading...");
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
		protected void onPostExecute(List<Mensa> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				ConnectionUtils
						.showToastErrorToConnectToWebService(IFretta.this);
				finish();
			} else {
				createWebcamList(result);
				progressDialog.dismiss();
			}
		}

	}

	/*
	 * 
	 * CON I RISULTATI DA SERVER CREA LA LISTA DI WEBCAM
	 */
	private void createWebcamList(List<Mensa> mense) {

		adapter.addAll(mense);

		ifretta_listView.setAdapter(adapter);

		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				Mensa m = (Mensa) adapter.getItemAtPosition(position);
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", m.getMensa_nome());
				i.putExtra("online_img_url", m.getMensa_link_online());
				i.putExtra("offline_img_url", m.getMensa_link_offline());
				startActivity(i);

			}
		});

		adapter.notifyDataSetChanged();
	}

}
