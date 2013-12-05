package eu.trentorise.smartcampus.ifame.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Saldo;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class ISoldi extends SherlockActivity {

	/** per gestire la chiamata da isoldi */
	public static final String SELECTED_MENU = "menu_selezionato";

	public static final String INTERO = "menu_intero";
	public static final String RIDOTTO = "menu_ridotto";
	public static final String SNACK = "menu_snack";

	private TextView centerText;
	private TextView bottomText;
	// private TextView statsButton;
	private TextView interoText;
	private TextView ridottoText;
	private TextView snackText;
	private TextView isoldi_euro_txt;
	private TextView credit_message_label;
	// private TextView val_textview2;
	// private TextView val_textview1;
	// private TextView val_textview3;

	private MenuItem refreshButton;

	private LinearLayout isoldi_layout_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_isoldi);

		ridottoText = (TextView) findViewById(R.id.isoldi_ridotto_text);
		interoText = (TextView) findViewById(R.id.isoldi_intero_text);
		snackText = (TextView) findViewById(R.id.isoldi_snack_text);
		// statsButton = (TextView) findViewById(R.id.isoldi_statistics_button);
		centerText = (TextView) findViewById(R.id.isoldi_center_text);
		bottomText = (TextView) findViewById(R.id.isoldi_bottom_text);
		isoldi_euro_txt = (TextView) findViewById(R.id.isoldi_euro_text);
		credit_message_label = (TextView) findViewById(R.id.isoldi_credit_message_text);

		isoldi_layout_view = (LinearLayout) findViewById(R.id.isoldi_layout);
		isoldi_layout_view.setVisibility(View.GONE);

		if (IFameUtils.isUserConnectedToInternet(getApplicationContext())) {
			new ISoldiConnector().execute();
		} else {
			Toast.makeText(ISoldi.this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		interoText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				i.putExtra(SELECTED_MENU, INTERO);
				startActivity(i);
			}
		});
		ridottoText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				i.putExtra(SELECTED_MENU, RIDOTTO);
				startActivity(i);

			}
		});
		snackText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ISoldi.this, Tipologie_menu_fr.class);
				i.putExtra(SELECTED_MENU, SNACK);
				startActivity(i);

			}
		});
		// statsButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// showUserStats();
		// }
		// });

		// actionBarSherlock is initialized in super.onCreate()
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void getAmount(Saldo result) {

		// if (result != null) {
		// amount = Float.parseFloat(result.getCredit());
		//
		// val_textview1 = (TextView) findViewById(R.id.val_textview1);
		// val_textview2 = (TextView) findViewById(R.id.val_textview2);
		// val_textview3 = (TextView) findViewById(R.id.val_textview3);
		//
		// if (result.getPayments() != null) {
		// // user has some payments
		// if (result.getPayments().size() >= 1
		// && result.getPayments().get(0) != null) {
		// String text = result.getPayments().get(0).getPaymentDate()
		// + " "
		// + result.getPayments().get(0)
		// .getProductDescription() + "  €"
		// + result.getPayments().get(0).getProductPrice();
		// val_textview1.setText(text);
		// val_textview1.setVisibility(1);
		// }
		// if (result.getPayments().size() >= 2
		// && result.getPayments().get(1) != null) {
		// String text = result.getPayments().get(1).getPaymentDate()
		// + " "
		// + result.getPayments().get(1)
		// .getProductDescription() + "  €"
		// + result.getPayments().get(1).getProductPrice();
		// val_textview2.setText(text);
		// val_textview2.setVisibility(1);
		// }
		// if (result.getPayments().size() >= 3
		// && result.getPayments().get(2) != null) {
		// String text = result.getPayments().get(2).getPaymentDate()
		// + " "
		// + result.getPayments().get(2)
		// .getProductDescription() + "  €"
		// + result.getPayments().get(2).getProductPrice();
		// val_textview3.setText(text);
		// val_textview3.setVisibility(1);
		// }
		// } else {
		// // there are no payments
		// statsButton.setVisibility(View.GONE);
		// }
		// }

		float amount = 0f;
		boolean creditoInvalido = false;

		if (result != null) {
			try {
				amount = Float.parseFloat(result.getCredit());
			} catch (NumberFormatException e) {
				// alcuni studenti che sono disabilitati o vecchi e hanno
				// l'account unitn ma la tessera dell opera disabilitata hanno
				// credito -> "" percio da errore qui
				creditoInvalido = true;
			}
		}

		if (creditoInvalido) {

			centerText.setText("0");
			centerText.setTextColor(Color.parseColor("#CC0000"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#CC0000"));

			bottomText.setTextColor(Color.parseColor("#CC0000"));
			bottomText.setText(getString(R.string.iSoldi_devi_ricaricare));

			interoText.setPaintFlags(interoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			ridottoText.setPaintFlags(ridottoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			snackText.setPaintFlags(snackText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			// interoText.setVisibility(View.GONE);
			// ridottoText.setVisibility(View.GONE);
			// snackText.setVisibility(View.GONE);

		} else if (amount >= 4.90f) {

			centerText.setText(result.getCredit());
			centerText.setTextColor(Color.parseColor("#228B22"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#228B22"));

			bottomText.setTextColor(Color.BLACK);
			bottomText
					.setText(" " + getString(R.string.iSoldi_puoi_acquistare));

			// statsButton.setBackgroundColor(Color.parseColor("#228B22"));

			interoText.setVisibility(View.VISIBLE);
			ridottoText.setVisibility(View.VISIBLE);
			snackText.setVisibility(View.VISIBLE);

		} else if (amount >= 4.40f && amount < 4.90f) {

			centerText.setText(result.getCredit());
			centerText.setTextColor(Color.parseColor("#FFCD00"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FFCD00"));

			bottomText.setTextColor(Color.BLACK);
			bottomText.setText(getString(R.string.iSoldi_puoi_acquistare));

			// statsButton.setTextColor(Color.parseColor("#FFFFFF"));

			interoText.setPaintFlags(interoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			// interoText.setVisibility(View.GONE);
			ridottoText.setVisibility(View.VISIBLE);
			snackText.setVisibility(View.VISIBLE);

		} else if (amount >= 3.10f && amount < 4.40f) {

			centerText.setText(result.getCredit());
			centerText.setTextColor(Color.parseColor("#FF8800"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#FF8800"));

			bottomText.setTextColor(Color.BLACK);
			bottomText.setText(getString(R.string.iSoldi_puoi_acquistare));

			// statsButton.setBackgroundColor(Color.parseColor("#FF8800"));

			interoText.setPaintFlags(interoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			ridottoText.setPaintFlags(ridottoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			// interoText.setVisibility(View.GONE);
			// ridottoText.setVisibility(View.GONE);
			snackText.setVisibility(View.VISIBLE);

		} else if (amount >= 0f && amount < 3.10f) {

			centerText.setText(result.getCredit());
			centerText.setTextColor(Color.parseColor("#CC0000"));
			isoldi_euro_txt.setTextColor(Color.parseColor("#CC0000"));

			bottomText.setTextColor(Color.parseColor("#CC0000"));
			bottomText.setText(getString(R.string.iSoldi_devi_ricaricare));

			// statsButton.setBackgroundColor(Color.parseColor("#FF8800"));

			interoText.setPaintFlags(interoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			ridottoText.setPaintFlags(ridottoText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			snackText.setPaintFlags(snackText.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			// interoText.setVisibility(View.GONE);
			// ridottoText.setVisibility(View.GONE);
			// snackText.setVisibility(View.GONE);

		} else {
			// amount < 0 errore mostro -> login con google non va bene

			interoText.setVisibility(View.GONE);
			ridottoText.setVisibility(View.GONE);
			snackText.setVisibility(View.GONE);
			credit_message_label.setVisibility(View.GONE);

			centerText.setText(R.string.iSoldi_credit_status_not_available);
			centerText.setTextSize(27);
			centerText.setGravity(Gravity.CENTER);
			centerText.setTextColor(Color.parseColor("#CC0000"));

			bottomText.setText(R.string.iSoldi_unitn_login_required);
			bottomText.setPadding(0, 18, 0, 0);
			bottomText.setGravity(Gravity.CENTER | Gravity.BOTTOM);

			bottomText.setTextColor(Color.parseColor("#CC0000"));

			// statsButton.setVisibility(View.GONE);
			isoldi_euro_txt.setVisibility(View.GONE);
		}
	}

	/*
	 * 
	 * CONNECTOR TO TO GET DATA
	 */
	private class ISoldiConnector extends AsyncTask<Void, Void, Saldo> {

		private LinearLayout progressBarLayout;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
			progressBarLayout = IFameUtils.setProgressBarLayout(ISoldi.this);

			IFameUtils.setActionBarLoading(refreshButton);
		}

		@Override
		protected Saldo doInBackground(Void... saldo) {

			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(ISoldi.this,
					getString(R.string.APP_TOKEN));
			MessageRequest request = new MessageRequest(
					getString(R.string.URL_BASE_WEB_IFAME),
					getString(R.string.PATH_ISOLDI_GETSOLDI));
			request.setMethod(Method.GET);

			try {

				MessageResponse response = mProtocolCarrier
						.invokeSync(request, getString(R.string.APP_TOKEN),
								IFameMain.getAuthToken());

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
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Saldo result) {
			super.onPostExecute(result);

			if (result == null) {
				Toast.makeText(ISoldi.this,
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
				finish();
				return;

			} else {
				getAmount(result);
			}

			// setup the view
			if (isoldi_layout_view != null) {
				isoldi_layout_view.setVisibility(View.VISIBLE);
			}
			progressBarLayout.setVisibility(View.GONE);
			IFameUtils.removeActionBarLoading(refreshButton);
		}
	}

	// private void showUserStats() {
	//
	// final View userStatsLayout = (View) findViewById(R.id.user_stats);
	//
	// ToggleButton showUserStats_button = (ToggleButton)
	// findViewById(R.id.isoldi_statistics_button);
	//
	// if (showUserStats_button.isChecked()) {
	// userStatsLayout.setVisibility(View.VISIBLE);
	// } else {
	// userStatsLayout.setVisibility(View.GONE);
	// }
	//
	// showUserStats_button
	// .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
	// {
	// // private String token;
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView,
	// boolean isChecked) {
	// if (isChecked) {
	// userStatsLayout.setVisibility(View.VISIBLE);
	//
	// } else {
	// userStatsLayout.setVisibility(View.GONE);
	// }
	// }
	// });
	// }

}