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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

			Dialog dialog = new Dialog(this);
			showCustomizedDialog(dialog);

			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void showCustomizedDialog(final Dialog dialog) {
		// hide the title bar
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// set content view to the customized layout
		dialog.setContentView(R.layout.igradito_custom_dialogbox);
		dialog.setTitle("La tua recensione...");

		// the values of the spinner are taken from the string xml file for the
		// moment,
		// null pointer exception shown when getting the context of the array
		// adapter

		// add elements to the spinner
		// Spinner mensa_spinner = (Spinner) findViewById(R.id.spinner);
		// ArrayList<String> spinner_list = new ArrayList<String>();
		// spinner_list.add("Povo Mensa");
		// spinner_list.add("Povo Mensa Veloce");
		// spinner_list.add("Tommaso Gar");
		// spinner_list.add("Zannela");
		// spinner_list.add("Mesiano 1");
		// spinner_list.add("Mesiano 2");
		//
		// ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
		// , android.R.layout.simple_spinner_dropdown_item,
		// spinner_list);

		// mensa_spinner.setAdapter(spinner_adapter);

		// Add a listener to the button that closes the dialog
		Button annullaButton = (Button) dialog
				.findViewById(R.id.annulla_button);
		// if button is clicked, close the custom dialog
		annullaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

}
