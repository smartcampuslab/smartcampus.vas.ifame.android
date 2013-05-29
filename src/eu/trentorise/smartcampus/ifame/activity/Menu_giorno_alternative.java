package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Alternative;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.PiattoKcal;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Menu_giorno_alternative extends Activity {

	View view;
	ProgressDialog pd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_giorno_alternative);
		
		new ProgressDialog(Menu_giorno_alternative.this);
		// Dont show anything until the data is loaded
		pd = ProgressDialog.show(Menu_giorno_alternative.this, "Loading... ",
				"please wait...");
		view = findViewById(R.id.menu_alternative_view);
		view.setVisibility(View.GONE);

		new AlternativeConnector(Menu_giorno_alternative.this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno_alternative, menu);
		return true;
	}

	public void createMenuAlternative(Alternative alternative) {

		// Get the listview
		ListView lista_alternative = (ListView) findViewById(R.id.lista_piatti_alternative);
		List<PiattoKcal> piatti_alternativi = new ArrayList<PiattoKcal>();

		List<PiattoKcal> alternativeList = alternative.getAlternative();
		piatti_alternativi.add(new PiattoKcal("1", ""));
		for (int i = 0; i < alternativeList.size(); i++) {
			piatti_alternativi.add(alternativeList.get(i));
			if (i == 2) {
				piatti_alternativi.add(new PiattoKcal("2", ""));
			}
			if (i == 5) {
				piatti_alternativi.add(new PiattoKcal("3", ""));
			}
			if (i == 8) {
				piatti_alternativi.add(new PiattoKcal("4", ""));
			}
		}

		AlternativeAdapter adapter_alternative = new AlternativeAdapter(
				Menu_giorno_alternative.this,
				android.R.layout.simple_expandable_list_item_1,
				piatti_alternativi);
		lista_alternative.setAdapter(adapter_alternative); 
		
	}

	private class AlternativeConnector extends
			AsyncTask<Void, Void, Alternative> {

		private ProtocolCarrier mProtocolCarrier;
		private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
		private static final String auth_token = "AUTH_TOKEN";
		private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public AlternativeConnector(Context applicationContext) {
			context = applicationContext;
		}

		private Alternative getAlternative() {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getalternative");
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					Alternative alt = Utils.convertJSONToObject(body,
							Alternative.class);

					return alt;

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
		protected Alternative doInBackground(Void... params) {
			return getAlternative();
		}

		@Override
		protected void onPostExecute(Alternative result) {
			super.onPostExecute(result);
			createMenuAlternative(result);
			// Make data visible after it has been fetched and dismiss the
			// dialog loader
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

	}

	private class AlternativeAdapter extends ArrayAdapter<PiattoKcal> {

		public AlternativeAdapter(Context context, int textViewResourceId,
				List<PiattoKcal> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			PiattoKcal piattoDelGiorno = getItem(position);

			if (piattoDelGiorno.getPiatto().matches("[0-9]+")) {

				convertView = inflater.inflate(
						R.layout.layout_row_header_menu_adapter, null);

				int num = Integer.parseInt(piattoDelGiorno.getPiatto());

				TextView nome_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_day_header_adapter);
				if (num == 1) {
					nome_piatto_del_giorno.setText("Primi");
				} else if (num == 2) {
					nome_piatto_del_giorno.setText("Secondi");
				} else if (num == 3) {
					nome_piatto_del_giorno.setText("Piatti Freddi");
				} else if (num == 4) {
					nome_piatto_del_giorno.setText("Contorni");
				}

			} else {

				convertView = inflater.inflate(
						R.layout.layout_row_menu_adapter, null);

				TextView nome_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_name_adapter);
				TextView kcal_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_kcal_adapter);

				nome_piatto_del_giorno.setText(piattoDelGiorno.getPiatto());
				kcal_piatto_del_giorno.setText(piattoDelGiorno.getKcal());

			}
			return convertView;
		}
	}

}
