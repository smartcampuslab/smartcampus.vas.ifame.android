package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.PiattoKcal;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Menu_giorno extends Activity {

	private String selectedDish;
	View view;
	ProgressDialog pd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_giorno);

		new ProgressDialog(Menu_giorno.this);
		// Dont show anything until the data is loaded
		pd = ProgressDialog.show(Menu_giorno.this, "Loading... ",
				"please wait...");
		view = findViewById(R.id.menu_del_giorno_view);
		view.setVisibility(View.GONE);

		new MenuDelGiornoConnector(Menu_giorno.this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno, menu);
		return true;
	}

	private class StartWebSearchAlertDialog extends DialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(
					getString(R.string.GoogleSearchAlertText) + " "
							+ selectedDish + "?")
					.setPositiveButton(
							getString(R.string.GoogleSearchAlertAccept),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									Intent intent = new Intent(
											Intent.ACTION_WEB_SEARCH);
									intent.putExtra(SearchManager.QUERY,
											selectedDish); // query contains
									// search string
									startActivity(intent);

								}
							})
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User cancelled the dialog
								}
							});
			// Create the AlertDialog object and return it
			return builder.create();

		}
	}

	public void createMenuDelGiorno(MenuDelGiorno menuDelGiorno) {

		ListView lista_piatti_view = (ListView) findViewById(R.id.lista_piatti);
		
		List<PiattoKcal> lista_piatti = new ArrayList<PiattoKcal>();

		List<PiattoKcal> piattiList = menuDelGiorno.getPiattiDelGiorno();
		lista_piatti.add(new PiattoKcal("1", ""));
		for (int i = 0; i < piattiList.size(); i++) {
			lista_piatti.add(piattiList.get(i));
			if (i == 2) {
				lista_piatti.add(new PiattoKcal("2", ""));
			}
			if (i == 4)
				lista_piatti.add(new PiattoKcal("3", ""));
		}

		
		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String daily_menu = s.format(new Date());
		//Set the title of the action bar to the current date
		setTitle(daily_menu);


		MenuGiornoAdapter adapter_piatti = new MenuGiornoAdapter(Menu_giorno.this, android.R.layout.simple_list_item_1,lista_piatti);
		lista_piatti_view.setAdapter(adapter_piatti);

		lista_piatti_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				selectedDish = ((PiattoKcal) parent.getItemAtPosition(position)).getPiatto();
				StartWebSearchAlertDialog dialog = new StartWebSearchAlertDialog();

				dialog.show(getFragmentManager(), null);

			}
		});
		

	}

	private class MenuDelGiornoConnector extends AsyncTask<Void, Void, MenuDelGiorno> {

		private ProtocolCarrier mProtocolCarrier;
		private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
		private static final String auth_token = "AUTH_TOKEN";
		private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public MenuDelGiornoConnector(Context applicationContext) {
			context = applicationContext;
		}

		private MenuDelGiorno getMenuDelGiorno() {
			// try {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getmenudelgiorno");
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					MenuDelGiorno mdg = Utils.convertJSONToObject(body,
							MenuDelGiorno.class);

					return mdg;

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
		protected MenuDelGiorno doInBackground(Void... params) {
			return getMenuDelGiorno();
		}

		@Override
		protected void onPostExecute(MenuDelGiorno result) {
			super.onPostExecute(result);
			createMenuDelGiorno(result);
			// Make data visible after it has been fetched and dismiss the
			// dialog loader
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

	}

	private class MenuGiornoAdapter extends ArrayAdapter<PiattoKcal> {

		public MenuGiornoAdapter(Context context, int textViewResourceId,
				List<PiattoKcal> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			

			PiattoKcal piattoDelGiorno = getItem(position);
			
			if (piattoDelGiorno.getPiatto().matches("[0-9]+")){
				
				convertView = inflater.inflate(R.layout.layout_row_header_menu_adapter,
						null);
				
				int num = Integer.parseInt(piattoDelGiorno.getPiatto());

				
				TextView nome_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_day_header_adapter);
				if(num == 1){
					nome_piatto_del_giorno.setText("Primi"); 
				} else if (num == 2){
					nome_piatto_del_giorno.setText("Secondi"); 
				} else if(num == 3){
					nome_piatto_del_giorno.setText("Contorni"); 
				}
				
				
			} else {
				
				convertView = inflater.inflate(R.layout.layout_row_menu_adapter,
						null);

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
