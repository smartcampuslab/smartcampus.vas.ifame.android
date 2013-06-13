package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import eu.trentorise.smartcampus.ifame.R;

public class Recensioni_Activity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_pagina_recensioni);
		Bundle extras = getIntent().getExtras();

		ListView user_list = (ListView) findViewById(R.id.recensioni_list);
		// if there are no available intents return
		if (extras == null) {
			return;
		}

		// Get intent from the igradito activity
		String nome_piatto = (String) extras.get("nome_piatto");
		setTitle(nome_piatto);

		String[] utenti = new String[] {
				"Francesco\nbuonissimo, da prendere sempre",
				"Desmond\nma cos'è sta storia? ",
				"Stefano\nche figata prendere l'intero", "Bonadiman\nbuono!",
				"Larva\ndavvero buono", "Leso\nnon mi è piaciuto" };
		List<String> lista_utenti = new ArrayList<String>();
		for (int i = 0; i < utenti.length; i++) {
			lista_utenti.add(utenti[i]);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Recensioni_Activity.this, android.R.layout.simple_list_item_1,
				lista_utenti);

		user_list.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.igradito_recensioni_menu_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_add_comments:
			
			final Dialog dialog = new Dialog(this);
			//hide the title bar
			//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			//set content view to the customized layout
			dialog.setContentView(R.layout.igradito_custom_dialogbox);
			dialog.setTitle("La tua recensione...");
  
			Button annullaButton = (Button) dialog.findViewById(R.id.annulla_button);
			// if button is clicked, close the custom dialog
			annullaButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
 
			dialog.show();
			
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}	

}
