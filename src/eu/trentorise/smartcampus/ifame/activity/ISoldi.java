package eu.trentorise.smartcampus.ifame.activity;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.apache.http.entity.StringEntity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.connector.ISoldiConnector;
import eu.trentorise.smartcampus.ifame.model.Saldo;
import eu.trentorise.smartcampus.ifame.model.Transaction;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class ISoldi extends SherlockActivity {

	public final static String GET_AMOUNT_MONEY = "get_money";
	Button stats_button;
	Saldo saldoReturn;
	TextView centerText;
	TextView bottomText;
	ListView isoldi_listview;
	ArrayList<String> acquisti_possibili;
	Long transaction_time;
	String transaction_value;
	public ArrayList<Long> t_list;
	public ArrayList<String> v_list;
	private ArrayAdapter<String> adapter;
	TextView isoldi_euro_txt; 
	ProgressDialog progressDialog;
	View isoldi_layout_view; 

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_isoldi);
		// setContentView(R.layout.layout_isoldi);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		centerText = (TextView) findViewById(R.id.isoldi_center_text);
		bottomText = (TextView) findViewById(R.id.isoldi_bottom_text);
		isoldi_listview = (ListView) findViewById(R.id.isoldi_listview);
		stats_button = (Button) findViewById(R.id.isoldi_statistics_button);
		isoldi_euro_txt = (TextView) findViewById(R.id.isoldi_euro_text);
		isoldi_layout_view = (RelativeLayout) findViewById(R.id.isoldi_layout);
		
		new ProgressDialog(ISoldi.this);

		if(ConnectionUtils.isOnline(this)){
			new ISoldiConnector(this).execute();
		}else{
			Toast.makeText(ISoldi.this, "Connection not available", Toast.LENGTH_LONG).show();
			finish();
		}
		
		acquisti_possibili = new ArrayList<String>();

		int resID = android.R.layout.simple_list_item_1;
		adapter = new ArrayAdapter<String>(this, resID, acquisti_possibili);

		isoldi_listview.setAdapter(adapter);

		isoldi_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// trova qual'è l'oggetto schiacciato
				String selected = (String) parent.getItemAtPosition(position);
				if (selected.equals("Snack"))
					selected = "Snack1234";
				if (selected.equals("Ridotto"))
					selected = "Ridotto1234";
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				i.putExtra(Fai_il_tuo_menu.SELECTED_MENU, selected);
				Toast.makeText(getApplicationContext(), selected,
						Toast.LENGTH_LONG).show();
				startActivity(i);

			}

		});


		stats_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showUserStats();

			}
		});
	}

	public void getAmount(float amount) {

		if (amount >= 4.90) {
	
			centerText.setText( String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#228B22"));

			bottomText.setText(" " + getString(R.string.iSoldi_puoi_acquistare));
			stats_button.setBackgroundColor(Color.parseColor("#228B22"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#228B22"));

			acquisti_possibili
					.add(getString(R.string.iDeciso_menu_types_intero));
			acquisti_possibili
					.add(getString(R.string.iDeciso_menu_types_ridotto));
			acquisti_possibili
					.add(getString(R.string.iDeciso_menu_types_snack));
			adapter.notifyDataSetChanged();

		} else if (amount >= 4.20 && amount < 4.90) {
			centerText.setText(" " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FFD700"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FFD700"));

			bottomText.setText(getString(R.string.iSoldi_puoi_acquistare));
			stats_button.setTextColor(Color.parseColor("#FFFFFF"));

			acquisti_possibili
					.add(getString(R.string.iDeciso_menu_types_ridotto));
			acquisti_possibili
					.add(getString(R.string.iDeciso_menu_types_snack));
			adapter.notifyDataSetChanged();

		} else if (amount >= 2.90 && amount < 4.20) {
			centerText.setText(String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FF8800"));

			bottomText.setText(" " + getString(R.string.iSoldi_puoi_acquistare));
			stats_button.setBackgroundColor(Color.parseColor("#FF8800"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FF8800"));

			acquisti_possibili
					.add(getString(R.string.iDeciso_menu_types_snack));
			adapter.notifyDataSetChanged();
		} else {
			centerText.setText(String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#CC0000"));

			bottomText.setText(" " + getString(R.string.iSoldi_devi_ricaricare));
			bottomText.setTextSize(25);
			bottomText.setTextColor(Color.parseColor("#CC0000"));

			stats_button.setBackgroundColor(Color.parseColor("#CC0000"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#CC0000"));

		}
	}

	public void showUserStats() {

		final View userStatsLayout = (View) findViewById(R.id.user_stats);

		ToggleButton showUserStats_button = (ToggleButton) findViewById(R.id.isoldi_statistics_button);

		if (showUserStats_button.isChecked()) {
			userStatsLayout.setVisibility(View.VISIBLE);

		} else {
			userStatsLayout.setVisibility(View.GONE);
		}

		showUserStats_button
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							userStatsLayout.setVisibility(View.VISIBLE);

						} else {
							userStatsLayout.setVisibility(View.GONE);
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.isoldi, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);

	}
	
	/**
	 * 
	 * CONNECTOR TO TO GET DATA
	 */
	
	public class ISoldiConnector extends AsyncTask<Saldo, Void, Saldo> {

		private ProtocolCarrier mProtocolCarrier;
		private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
		private static final String auth_token = "AUTH_TOKEN";
		private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public ISoldiConnector(Context applicationContext) {
			context = applicationContext;
		}

		private Saldo getSaldo() {
			// try {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it", "isoldi/getsoldi");
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, appToken, authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					return Utils.convertJSONToObject(body, Saldo.class);

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
		protected void onPreExecute(){
			super.onPreExecute();
			isoldi_layout_view.setVisibility(View.GONE);
			progressDialog = ProgressDialog.show(context, "iSoldi", "Loading..."); 
		}

		@Override
		protected Saldo doInBackground(Saldo... saldo) {
			
			return getSaldo();
		}
		
		 @Override
		 protected void onPostExecute(Saldo result){
			 getAmount(Float.parseFloat(result.getCredit()));
			 progressDialog.dismiss(); 
			 isoldi_layout_view.setVisibility(View.VISIBLE);
		 }
	}

}
