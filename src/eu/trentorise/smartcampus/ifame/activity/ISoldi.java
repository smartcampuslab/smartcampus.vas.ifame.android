package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Saldo;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

/*
 * 
 * per ora � tutto basato sul recupero dell'importo nella tessera FASULLO (cambia di volta in volta)
 * in base all'importo vengono mostrati i tipi di menu che sono acquistabili, o nessuno altrimenti
 * 
 * le statistiche non sono ancora disponibili per mancanza di dati forniteci da OU
 */

public class ISoldi extends SherlockActivity {
	/** Logging tag */
	private static final String TAG = "iSoldi";

	public final static String GET_AMOUNT_MONEY = "get_money";
	Saldo saldoReturn;
	TextView centerText;
	TextView bottomText;
	TextView statsButton;
	TextView interoText;
	TextView ridottoText;
	TextView snackText;

	ArrayList<String> acquisti_possibili;
	Long transaction_time;
	String transaction_value;
	ArrayList<Long> t_list;
	ArrayList<String> v_list;
	ArrayAdapter<String> adapter;
	TextView isoldi_euro_txt;

	LinearLayout isoldi_layout_view;

	private TextView val_textview2;

	private TextView val_textview1;

	private TextView val_textview3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_isoldi);
		// // setContentView(R.layout.layout_isoldi);
		// Bundle extras = getIntent().getExtras();
		// if (extras == null) {
		// return;
		// }

		ridottoText = (TextView) findViewById(R.id.isoldi_ridotto_text);
		interoText = (TextView) findViewById(R.id.isoldi_intero_text);
		snackText = (TextView) findViewById(R.id.isoldi_snack_text);
		statsButton = (TextView) findViewById(R.id.isoldi_statistics_button);
		centerText = (TextView) findViewById(R.id.isoldi_center_text);
		bottomText = (TextView) findViewById(R.id.isoldi_bottom_text);
		isoldi_euro_txt = (TextView) findViewById(R.id.isoldi_euro_text);
		isoldi_layout_view = (LinearLayout) findViewById(R.id.isoldi_layout);

		if (ConnectionUtils.isOnline(this)) {
			new ProgressDialog(ISoldi.this);
			new ISoldiConnector(this).execute();
		} else {
			ConnectionUtils.showToastNotConnected(this);
			finish();
		}

		interoText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				startActivity(i);
			}
		});
		ridottoText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				i.putExtra(Fai_il_tuo_menu.SELECTED_MENU, "Ridotto1234");
				startActivity(i);

			}
		});
		snackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				i.putExtra(Fai_il_tuo_menu.SELECTED_MENU, "Snack1");
				startActivity(i);

			}
		});
		statsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showUserStats();
			}
		});
	}

	public void getAmount(Saldo result) {

		float amount = 0;
		if (result != null) {
			amount = Float.parseFloat(result.getCredit());
			val_textview1 = (TextView) findViewById(R.id.val_textview1);
			val_textview2 = (TextView) findViewById(R.id.val_textview2);
			val_textview3 = (TextView) findViewById(R.id.val_textview3);
			if (result.getPayments() != null) {
				if (result.getPayments().size() >=1 && result.getPayments().get(0) != null) {
					String text=result.getPayments().get(0).getPaymentDate()+" "+result.getPayments().get(0).getProductDescription()+ "  €"+result.getPayments().get(0)
							.getProductPrice();
					val_textview1.setText(text);
					val_textview1.setVisibility(1);
				}
				if (result.getPayments().size() >=2 && result.getPayments().get(1) != null) {
					String text=result.getPayments().get(1).getPaymentDate()+" "+result.getPayments().get(1).getProductDescription()+ "  €"+result.getPayments().get(1)
							.getProductPrice();
					val_textview2.setText(text);
					val_textview2.setVisibility(1);
				}
				if (result.getPayments().size() >= 3 && result.getPayments().get(2) != null) {
					String text=result.getPayments().get(2).getPaymentDate()+" "+result.getPayments().get(2).getProductDescription()+ "  €"+result.getPayments().get(2)
							.getProductPrice();
					val_textview3.setText(text);
					val_textview3.setVisibility(1);
				}
			}
		}

		if (amount >= 4.70) {

			centerText.setText(String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#228B22"));

			bottomText
					.setText(" " + getString(R.string.iSoldi_puoi_acquistare));
			statsButton.setBackgroundColor(Color.parseColor("#228B22"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#228B22"));

			interoText.setVisibility(View.VISIBLE);
			ridottoText.setVisibility(View.VISIBLE);
			snackText.setVisibility(View.VISIBLE);

		} else if (amount >= 4.20 && amount < 4.70) {
			centerText.setText(" " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FFD700"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FFD700"));

			bottomText.setText(getString(R.string.iSoldi_puoi_acquistare));
			statsButton.setTextColor(Color.parseColor("#FFFFFF"));

			ridottoText.setVisibility(View.VISIBLE);
			snackText.setVisibility(View.VISIBLE);

		} else if (amount >= 2.90 && amount < 4.20) {
			centerText.setText(String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FF8800"));

			bottomText
					.setText(" " + getString(R.string.iSoldi_puoi_acquistare));
			statsButton.setBackgroundColor(Color.parseColor("#FF8800"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FF8800"));

			snackText.setVisibility(View.VISIBLE);

		} else {
			centerText.setText(String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#CC0000"));

			bottomText
					.setText(" " + getString(R.string.iSoldi_devi_ricaricare));
			bottomText.setTextSize(25);
			bottomText.setTextColor(Color.parseColor("#CC0000"));

			statsButton.setBackgroundColor(Color.parseColor("#CC0000"));
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
					private String token;

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
		return false;
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

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * CONNECTOR TO TO GET DATA
	 */

	public class ISoldiConnector extends AsyncTask<Saldo, Void, Saldo> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		// public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		ProgressDialog progressDialog;

		private final String CLIENT_ID;
		private final String CLIENT_SECRET;
		private final String URL_WEB_IFAME;
		// private final String AUTHORIZATION_TOKEN = "Authorization";
		private String token;
		private Saldo saldo;

		public ISoldiConnector(Context applicationContext) {
			context = applicationContext;
			CLIENT_ID = getString(R.string.CLIENT_ID);
			CLIENT_SECRET = getString(R.string.CLIENT_SECRET);
			URL_WEB_IFAME = getString(R.string.URL_WEB_IFAME);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isoldi_layout_view.setVisibility(View.GONE);
			progressDialog = ProgressDialog.show(context, "iSoldi",
					"Loading...");

			/*
			 * get the token
			 */
			SCAccessProvider accessProvider = new EmbeddedSCAccessProvider();
			try {
				token = accessProvider.readToken(ISoldi.this, CLIENT_ID,
						CLIENT_SECRET);

			} catch (AACException e) {
				Log.e(TAG, "Failed to get token: " + e.getMessage());
			}
		}

		@Override
		protected Saldo doInBackground(Saldo... saldo) {

			if (token != null) {

				mProtocolCarrier = new ProtocolCarrier(context, appToken);

				MessageRequest request = new MessageRequest(URL_WEB_IFAME,
						"isoldi/getsoldi");

				request.setMethod(Method.GET);

				MessageResponse response;
				try {
					response = mProtocolCarrier.invokeSync(request, appToken,
							token);

					if (response.getHttpStatus() == 200) {

						return Utils.convertJSONToObject(response.getBody(),
								Saldo.class);
					}
				} catch (ConnectionException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Saldo result) {
			if (result == null) {
				ConnectionUtils
						.showToastErrorToConnectToWebService(ISoldi.this);
				finish();
			} else {
				this.saldo = result;
				if (result.getCredit().compareTo("") != 0)
					getAmount(result);
				else
					getAmount(null);
				progressDialog.dismiss();
				isoldi_layout_view.setVisibility(View.VISIBLE);
			}
		}
	}

}
