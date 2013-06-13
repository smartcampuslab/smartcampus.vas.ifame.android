package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.connector.ISoldiConnector;
import eu.trentorise.smartcampus.ifame.model.Saldo;

public class IDeciso extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// per ora da qui ottengo i soldi presenti nella card e salvo in
		// preferences
		float cash;
		Saldo saldoReturn;
		try {
			saldoReturn = (Saldo) new ISoldiConnector(getApplicationContext())
					.execute().get();

			if (saldoReturn == null) {
				Toast.makeText(getApplicationContext(), "Saldo in denaro mancante", Toast.LENGTH_LONG).show();
			} else {

				cash = Float.parseFloat(saldoReturn.getCredit());

				SharedPreferences pref = getSharedPreferences(
						getString(R.string.iFretta_preference_file),
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();

				Toast.makeText(getApplicationContext(), "Cash: "+cash, Toast.LENGTH_LONG).show();
				editor.remove(ISoldi.GET_AMOUNT_MONEY);
				editor.putFloat(ISoldi.GET_AMOUNT_MONEY, cash);
				editor.commit();
			}

		} catch (InterruptedException e) {
			Toast.makeText(getApplicationContext(), "InterruptedException", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (ExecutionException e) {
			Toast.makeText(getApplicationContext(), "ExecutionException", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		setContentView(R.layout.layout_ideciso);

		String[] features = { "Menu del giorno", "Tipologie di menu",
				"Fai il tuo menu", "Menu del mese" };
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
					i = new Intent(IDeciso.this, Menu_giorno_tab.class);
					startActivity(i);
					break;
				case 1:
					i = new Intent(IDeciso.this, Tipologie_menu_fr.class);
					startActivity(i);
					break;
				case 2:
					i = new Intent(IDeciso.this, Fai_il_tuo_menu.class);
					startActivity(i);
					break;
				case 3:
					i = new Intent(IDeciso.this, Menu_mese.class);
					startActivity(i);
					break;

				}

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ideciso, menu);
		return true;
	}

}

/*
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState); setContentView(R.layout.layout_ideciso);
 * 
 * 
 * Button iDeciso_menu_giorno_btn = (Button)findViewById(R.id.daily_menu_btn);
 * iDeciso_menu_giorno_btn.setOnClickListener(new OnClickListener(){
 * 
 * @Override public void onClick(View arg0) {
 * 
 * Intent i = new Intent(IDeciso.this, Menu_giorno.class); startActivity(i);
 * 
 * }});
 * 
 * Button iDeciso_menu_types_btn = (Button)findViewById(R.id.menu_types_btn);
 * iDeciso_menu_types_btn.setOnClickListener(new OnClickListener(){
 * 
 * @Override public void onClick(View v) { Intent i = new Intent(IDeciso.this,
 * Tipologie_menu.class); startActivity(i);
 * 
 * }}); Button iDeciso_compose_menu_btn =
 * (Button)findViewById(R.id.compose_menu_btn);
 * iDeciso_compose_menu_btn.setOnClickListener(new OnClickListener(){
 * 
 * @Override public void onClick(View v) { Intent i = new Intent(IDeciso.this,
 * Fai_il_tuo_menu.class); startActivity(i);
 * 
 * }}); Button iDeciso_monthly_menu_btn =
 * (Button)findViewById(R.id.monthly_menu_btn);
 * iDeciso_monthly_menu_btn.setOnClickListener(new OnClickListener(){
 * 
 * @Override public void onClick(View v) { Intent i = new Intent(IDeciso.this,
 * Menu_mese.class); startActivity(i);
 * 
 * }}); }
 */