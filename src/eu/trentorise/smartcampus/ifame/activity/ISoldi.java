package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.ToggleButton;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.connector.ISoldiConnector;
import eu.trentorise.smartcampus.ifame.model.Saldo;
import eu.trentorise.smartcampus.ifame.model.Transaction;

public class ISoldi extends Activity {

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

				Intent i = new Intent(ISoldi.this, Tipologie_menu.class);
				i.putExtra("selected_item", selected);
				startActivity(i);

			}

		});

		try {
			saldoReturn = (Saldo) new ISoldiConnector(getApplicationContext())
					.execute().get();
			if (saldoReturn == null) {
				getAmount(0);
			} else {
				getAmount(Float.parseFloat(saldoReturn.getCredit()));

				ListAdapter listAdapter = isoldi_listview.getAdapter();

				int rows = listAdapter.getCount();
				int height = 70 * rows;
				ViewGroup.LayoutParams params = isoldi_listview
						.getLayoutParams();
				params.height = height;
				isoldi_listview.setLayoutParams(params);
				isoldi_listview.requestLayout();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator<Transaction> i = saldoReturn.getTransactions().iterator();
		t_list = new ArrayList<Long>();
		v_list = new ArrayList<String>();

		while (i.hasNext()) {
			// Transaction t = (Transaction) i.next();
			Transaction t = new Transaction();
			t = (Transaction) i.next();
			// transaction_time = t.getTimemillis();
			// transaction_value = t.getValue();
			t_list.add(t.getTimemillis());
			v_list.add(t.getValue());
		}

		stats_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * ListView lt = (ListView) findViewById(R.id.test_listview);
				 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				 * ISoldi.this, android.R.layout.simple_list_item_1, v_list);
				 * lt.setAdapter(adapter);
				 */

//non ci serve più un'altra Activity per le statistiche!
				
				// Intent intent = new Intent(ISoldi.this,
				// Stats_Activity.class);
				// intent.putExtra("values", v_list.toArray());
				// intent.putExtra("time_value", t_list.toArray());
				// startActivity(intent);
				showUserStats();

			}
		});
	}

	public void getAmount(float amount) {
		if (amount >= 4.90) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#228B22"));

			bottomText.setText("Puoi acquistare:");
			stats_button.setBackgroundColor(Color.parseColor("#228B22"));

			acquisti_possibili.add("Intero");
			acquisti_possibili.add("Ridotto");
			acquisti_possibili.add("Snack");
			adapter.notifyDataSetChanged();

		} else if (amount >= 4.20 && amount < 4.90) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FFD700"));

			bottomText.setText("Puoi acquistare:");
			stats_button.setBackgroundColor(Color.parseColor("#FFD700"));

			acquisti_possibili.add("Ridotto");
			acquisti_possibili.add("Snack");
			adapter.notifyDataSetChanged();

		} else if (amount >= 2.90 && amount < 4.20) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FF8800"));

			bottomText.setText("Puoi acquistare:");
			stats_button.setBackgroundColor(Color.parseColor("#FF8800"));

			acquisti_possibili.add("Snack");
			adapter.notifyDataSetChanged();
		} else {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#CC0000"));

			bottomText.setText("Devi ricaricare!");
			bottomText.setTextSize(25);
			bottomText.setTextColor(Color.parseColor("#CC0000"));

			stats_button.setBackgroundColor(Color.parseColor("#CC0000"));

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

}
