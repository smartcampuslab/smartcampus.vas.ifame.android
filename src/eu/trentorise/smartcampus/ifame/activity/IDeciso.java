package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;

public class IDeciso extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// per ora da qui ottengo i soldi presenti nella card e salvo in
		// preferences, salveremo nel bundle spero e quindi prenderemo
		// semplicemente il valore da li
		// float cash;
		// Saldo saldoReturn;
		/*
		 * try { saldoReturn = (Saldo) new
		 * ISoldiConnector(getApplicationContext()) .execute().get();
		 * 
		 * if (saldoReturn == null) { //Toast.makeText(getApplicationContext(),
		 * "Saldo in denaro mancante", Toast.LENGTH_LONG).show(); } else {
		 * 
		 * cash = Float.parseFloat(saldoReturn.getCredit());
		 * 
		 * SharedPreferences pref = getSharedPreferences(
		 * getString(R.string.iFretta_preference_file), Context.MODE_PRIVATE);
		 * SharedPreferences.Editor editor = pref.edit();
		 * 
		 * // Toast.makeText(getApplicationContext(), "Cash: "+cash,
		 * Toast.LENGTH_LONG).show(); editor.remove(ISoldi.GET_AMOUNT_MONEY);
		 * editor.putFloat(ISoldi.GET_AMOUNT_MONEY, cash); editor.commit(); }
		 * 
		 * } catch (InterruptedException e) {
		 * Toast.makeText(getApplicationContext(), "InterruptedException",
		 * Toast.LENGTH_LONG).show(); e.printStackTrace(); } catch
		 * (ExecutionException e) { Toast.makeText(getApplicationContext(),
		 * "ExecutionException", Toast.LENGTH_LONG).show(); e.printStackTrace();
		 * }
		 */
		setContentView(R.layout.layout_ideciso);

		// popoliamo la listview
		String[] features = { getString(R.string.iDeciso_home_daily_menu),
				getString(R.string.iDeciso_home_menu_types),
				getString(R.string.iDeciso_home_compose_menu),
				getString(R.string.iDeciso_home_monthly_menu) };
		ArrayList<String> features_list = new ArrayList<String>();

		for (int i = 0; i < features.length; i++) {
			features_list.add(i, features[i]);
		}

		ListView list = (ListView) findViewById(R.id.ideciso_page_list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.layout_list_view_ideciso, features_list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i;
				switch (position) {
				case 0:
					i = new Intent(IDeciso.this, MenuDelGiorno.class);
					startActivity(i);
					break;
				case 1:
					i = new Intent(IDeciso.this, Tipologie_menu_fr.class);
					startActivity(i);
					break;
				case 2:
					i = new Intent(IDeciso.this, ComponiMenu.class);
					startActivity(i);
					break;
				case 3:
					i = new Intent(IDeciso.this, MenuDelMeseActivity.class);
					startActivity(i);
					break;

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

}
