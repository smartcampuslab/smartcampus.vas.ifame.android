package eu.trentorise.smartcampus.ifame.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
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

public class ISoldi extends SherlockActivity {

	public final static String GET_AMOUNT_MONEY = "get_money";

	private TextView centerText;
	private TextView bottomText;
	private TextView statsButton;
	private TextView interoText;
	private TextView ridottoText;
	private TextView snackText;
	private TextView isoldi_euro_txt;
	private TextView val_textview2;
	private TextView val_textview1;
	private TextView val_textview3;

	private LinearLayout isoldi_layout_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_isoldi);

		ridottoText = (TextView) findViewById(R.id.isoldi_ridotto_text);
		interoText = (TextView) findViewById(R.id.isoldi_intero_text);
		snackText = (TextView) findViewById(R.id.isoldi_snack_text);
		statsButton = (TextView) findViewById(R.id.isoldi_statistics_button);
		centerText = (TextView) findViewById(R.id.isoldi_center_text);
		bottomText = (TextView) findViewById(R.id.isoldi_bottom_text);
		isoldi_euro_txt = (TextView) findViewById(R.id.isoldi_euro_text);
		isoldi_layout_view = (LinearLayout) findViewById(R.id.isoldi_layout);

		if (ConnectionUtils.isUserConnectedToInternet(this)) {
			new ISoldiConnector(this).execute();
		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
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

		// actionBarSherlock is initialized in super.onCreate()
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

	private void getAmount(Saldo result) {

		float amount = 0;
		if (result != null) {
			amount = Float.parseFloat(result.getCredit());

			val_textview1 = (TextView) findViewById(R.id.val_textview1);
			val_textview2 = (TextView) findViewById(R.id.val_textview2);
			val_textview3 = (TextView) findViewById(R.id.val_textview3);

			if (result.getPayments() != null) {
				// user has some payments
				if (result.getPayments().size() >= 1
						&& result.getPayments().get(0) != null) {
					String text = result.getPayments().get(0).getPaymentDate()
							+ " "
							+ result.getPayments().get(0)
									.getProductDescription() + "  €"
							+ result.getPayments().get(0).getProductPrice();
					val_textview1.setText(text);
					val_textview1.setVisibility(1);
				}
				if (result.getPayments().size() >= 2
						&& result.getPayments().get(1) != null) {
					String text = result.getPayments().get(1).getPaymentDate()
							+ " "
							+ result.getPayments().get(1)
									.getProductDescription() + "  €"
							+ result.getPayments().get(1).getProductPrice();
					val_textview2.setText(text);
					val_textview2.setVisibility(1);
				}
				if (result.getPayments().size() >= 3
						&& result.getPayments().get(2) != null) {
					String text = result.getPayments().get(2).getPaymentDate()
							+ " "
							+ result.getPayments().get(2)
									.getProductDescription() + "  €"
							+ result.getPayments().get(2).getProductPrice();
					val_textview3.setText(text);
					val_textview3.setVisibility(1);
				}
			} else {
				// there are no payments
				statsButton.setVisibility(View.GONE);
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

		} else if (amount >= 0 && amount < 2.90) {
			centerText.setText(String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FF8800"));

			bottomText
					.setText(" " + getString(R.string.iSoldi_puoi_acquistare));
			statsButton.setBackgroundColor(Color.parseColor("#FF8800"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FF8800"));

			snackText.setVisibility(View.VISIBLE);

		} else {
			centerText.setText(R.string.credit_status);
			centerText.setTextSize(27);
			centerText.setTextColor(Color.parseColor("#CC0000"));

			bottomText.setPadding(0, 10, 0, 0);
			bottomText.setGravity(Gravity.CENTER | Gravity.BOTTOM);
			bottomText.setText(R.string.unitn_log);
			bottomText.setTextSize(25);
			bottomText.setTextColor(Color.parseColor("#CC0000"));

			statsButton.setVisibility(View.GONE);
			isoldi_euro_txt.setVisibility(View.INVISIBLE);
			isoldi_euro_txt.setText("");
		}
	}

	private void showUserStats() {

		final View userStatsLayout = (View) findViewById(R.id.user_stats);

		ToggleButton showUserStats_button = (ToggleButton) findViewById(R.id.isoldi_statistics_button);

		if (showUserStats_button.isChecked()) {
			userStatsLayout.setVisibility(View.VISIBLE);
		} else {
			userStatsLayout.setVisibility(View.GONE);
		}

		showUserStats_button
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					// private String token;

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

	/*
	 * 
	 * CONNECTOR TO TO GET DATA
	 */
	private class ISoldiConnector extends AsyncTask<Void, Void, Saldo> {

		private Context context;
		private ProgressDialog progressDialog;

		private final String URL_BASE_WEB_IFAME;
		private final String URL_ISOLDI_GETSOLDI;
		private final String APP_TOKEN;

		public ISoldiConnector(Context applicationContext) {
			context = applicationContext;

			URL_BASE_WEB_IFAME = getString(R.string.URL_BASE_WEB_IFAME);
			APP_TOKEN = getString(R.string.APP_TOKEN);
			URL_ISOLDI_GETSOLDI = getString(R.string.PATH_ISOLDI_GETSOLDI);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
			isoldi_layout_view.setVisibility(View.GONE);
			progressDialog = ProgressDialog.show(context,
					getString(R.string.iSoldi_title_activity), "Loading...");
		}

		@Override
		protected Saldo doInBackground(Void... saldo) {

			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
					APP_TOKEN);
			MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
					URL_ISOLDI_GETSOLDI);
			request.setMethod(Method.GET);

			try {
				MessageResponse response = mProtocolCarrier.invokeSync(request,
						APP_TOKEN, IFameMain.getAuthToken());

				if (response.getHttpStatus() == 200) {
					return Utils.convertJSONToObject(response.getBody(),
							Saldo.class);
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
				// gestine del login con google e mostrare errore
				Saldo saldoError = new Saldo();
				saldoError.setCredit("-1");
				return saldoError;

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Saldo result) {
			if (result == null) {
				Toast.makeText(context,
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
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
