package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.MenuDellaSettimana;
import eu.trentorise.smartcampus.ifame.model.PiattiList;
import eu.trentorise.smartcampus.ifame.model.PiattoKcal;
import eu.trentorise.smartcampus.ifame.model.Settimana;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Menu_mese extends Activity {

	private Spinner weekSpinner;
	private ListView listacibi_view;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_mese);

		listacibi_view = (ListView) findViewById(R.id.menu_of_the_day);

		pd = new ProgressDialog(Menu_mese.this).show(Menu_mese.this, "iDeciso",
				"Loading...");

		new IDecisoConnector(Menu_mese.this).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_mese, menu);
		return true;
	}

	public class IDecisoConnector extends
			AsyncTask<Void, Void, MenuDellaSettimana> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IDecisoConnector(Context applicationContext) {
			context = applicationContext;
		}

		private MenuDellaSettimana getMenuDellaSettimana() {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getmenudellasettimana");
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					MenuDellaSettimana mds = Utils.convertJSONToObject(body,
							MenuDellaSettimana.class);

					return mds;

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
		protected MenuDellaSettimana doInBackground(Void... params) {
			// TODO Auto-generated method stub

			return getMenuDellaSettimana();
		}

		@Override
		protected void onPostExecute(MenuDellaSettimana mds) {
			// TODO Auto-generated method stub
			super.onPostExecute(mds);

			List<MenuDelGiorno> mdglist = mds.getMenuDellaSettimana();
			List<PiattoKcal> entireweek = new ArrayList<PiattoKcal>();

			ArrayAdapter<PiattoKcal> adapter = new ListHeaderAdapter(
					Menu_mese.this, entireweek);

			for (MenuDelGiorno m : mdglist) {
				// ATTENZIONE AL MAGHEGGIO
				// setto come nome del piatto il numero del giorno
				// del menu del giorno che sto iterando perche mi serve come
				// sentinella nell'adapter
				PiattoKcal p = new PiattoKcal();
				p.setPiatto("" + m.getDay());
				adapter.add(p);
				adapter.addAll(m.getPiattiDelGiorno());
			}

			// TextView tw = new TextView(Menu_mese.this);
			// tw.setText("PROVA HEADER");
			// tw.setBackgroundColor(Color.BLUE);
			// listacibi_view.addHeaderView(tw);

			listacibi_view.setAdapter(adapter);

			// chiudo il loading...
			pd.dismiss();
		}
	}

	private class ListHeaderAdapter extends ArrayAdapter<PiattoKcal> {

		public ListHeaderAdapter(Context context, List<PiattoKcal> list) {
			super(Menu_mese.this, android.R.layout.simple_list_item_1, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater
					.inflate(layout.layout_row_menu_adapter, null);

			PiattoKcal p = getItem(position);

			TextView name = (TextView) convertView
					.findViewById(R.id.menu_name_adapter);
			TextView kcal = (TextView) convertView
					.findViewById(R.id.menu_kcal_adapter);

			if (p.getPiatto().matches("[0-9]+")) {
				// ho un piatto sentinella setto il testo come data
				// e niente kcal
				int day = Integer.parseInt(p.getPiatto());

				Calendar c = Calendar.getInstance();
				c.set(Calendar.DATE, day);
				SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
				String date_formatted = s.format(c.getTime());

				convertView.setBackgroundColor(Color.RED);
				name.setText(date_formatted);
				name.setTextSize((float) 20);
				// name.setBackgroundColor(Color.RED);
				name.setTextColor(Color.WHITE);
				// perche nel layout c'è che esce 'kcal'
				kcal.setText("");

			} else {
				// ho un piatto vero setto i campi coi valori corrispondenti
				name.setText(p.getPiatto());
				kcal.setText(p.getKcal());
			}

			return convertView;
		}
	}
}
