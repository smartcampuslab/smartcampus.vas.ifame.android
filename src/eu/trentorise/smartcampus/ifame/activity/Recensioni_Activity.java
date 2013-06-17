package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Review;

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
				"Francesco",
				"Desmond",
				"Stefano", "Bonadiman",
				"Enrico" };
		
		ArrayList<Review> lista_reviews = new ArrayList<Review>();
		for (int i = 0; i < utenti.length; i++) {
			Review r = new Review(utenti[i], "questa è solo la prova di una recensione", "17/06/13");
			lista_reviews.add(r);
			
		}

		LazyAdapter adapter = new LazyAdapter(
				Recensioni_Activity.this, R.layout.list_igradito_recensioni,
				lista_reviews);

		user_list.setAdapter(adapter);

	}

	
	public class LazyAdapter extends ArrayAdapter<Review> {
		 
	    ArrayList<Review> reviews;
	    Activity activity;
	    int layoutId;
	 
	    public LazyAdapter(Activity activity, int layout_id, ArrayList<Review> reviews) {
	    	super(activity, layout_id, reviews);
	    	this.reviews=reviews;
	    	this.activity = activity;
	    	this.layoutId = layout_id;
	    }
	 
	    public int getCount() {
	        return reviews.size();
	    }
	 
	/*    public Review getItem(int position) {
	        return reviews[position];
	    }*/
	 
	    public long getItemId(int position) {
	        return position;
	    }
	 
	   
	    
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        
	        if(row == null){
	            LayoutInflater inflater = ((Activity)activity).getLayoutInflater();
	            row = inflater.inflate(layoutId, parent, false);
	            
	        }
	        
	        return row;
	    }
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
