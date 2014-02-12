package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.HistoryAdapter;
import eu.trentorise.smartcampus.ifame.model.OperaPayment;
import eu.trentorise.smartcampus.ifame.model.Saldo;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class HistoryActivity extends SherlockActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.isoldi_history);


		if (IFameUtils.isUserConnectedToInternet(getApplicationContext())) {
			new ISoldiConnector().execute();
		} else {
			Toast.makeText(HistoryActivity.this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
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
			progressBarLayout = IFameUtils.setProgressBarLayout(HistoryActivity.this);
		}

		@Override
		protected Saldo doInBackground(Void... saldo) {

			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(HistoryActivity.this,
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
				Toast.makeText(HistoryActivity.this,
						getString(R.string.errorSomethingWentWrong),
						Toast.LENGTH_SHORT).show();
				finish();
				return;

			} else {
				ListView lvHistory = (ListView) findViewById(R.id.list_view_history);
				List<OperaPayment> opList =  new ArrayList<OperaPayment>();
				for (OperaPayment op : result.getPayments()) {
					opList.add(op);					
				}
				Collections.reverse(opList);
				HistoryAdapter adp = new HistoryAdapter(getApplicationContext(), opList);
				lvHistory.setAdapter(adp);
			}
			progressBarLayout.setVisibility(View.GONE);
			
		}
	}

}