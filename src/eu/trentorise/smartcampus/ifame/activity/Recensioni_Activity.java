package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
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
import android.widget.Spinner;
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

		String[] utenti = new String[] { "Francesco", "Desmond", "Stefano",
				"Bonadiman", "Enrico" };

		ArrayList<Review> lista_reviews = new ArrayList<Review>();
		for (int i = 0; i < utenti.length; i++) {
			Review r = new Review(utenti[i],
					"questa � solo la prova di una recensione", "17/06/13");
			lista_reviews.add(r);

		}

		ReviewAdapter adapter = new ReviewAdapter(Recensioni_Activity.this,
				R.layout.list_igradito_recensioni, lista_reviews);

		user_list.setAdapter(adapter);

	}

	public class ReviewAdapter extends ArrayAdapter<Review> {

		ArrayList<Review> reviews;
		Activity activity;
		int layoutId;

		public ReviewAdapter(Activity activity, int layout_id,
				ArrayList<Review> reviews) {
			super(activity, layout_id, reviews);
			this.reviews = reviews;
			this.activity = activity;
			this.layoutId = layout_id;
		}

		public int getCount() {
			return reviews.size();
		}

		/*
		 * public Review getItem(int position) { return reviews[position]; }
		 */

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if (row == null) {
				LayoutInflater inflater = ((Activity) activity)
						.getLayoutInflater();
				row = inflater.inflate(layoutId, parent, false);

				// TO BE USED WHEN REVIEW MODEL AND CONNECTOR ARE READY
				// TextView username = (TextView)
				// findViewById(R.id.iGradito_recensioni_nome_utente);
				// TextView review_date = (TextView)
				// findViewById(R.id.iGradito_recensioni_data_inserimento);
				// TextView review_content =
				// (TextView)findViewById(R.id.iGradito_recensioni_review_content);
				// TextView like_count = (TextView)
				// findViewById(R.id.like_count);
				// TextView dislike_count =
				// (TextView)findViewById(R.id.dislike_count);
				//
				// Review r = getItem(position);
				//
				// username.setText(r.getUser());
				// review_date.setText(r.getDate());
				// review_content.setText(r.getContent());

				// fields like and dislike should be added to the review model
				//

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

			showCustomizedDialog();

			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void showCustomizedDialog() {
		// hide the title bar
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		FragmentManager fm = getFragmentManager();
		CustomDialog cd = new CustomDialog();
		cd.show(fm, "fragment");

	}

	public class CustomDialog extends DialogFragment {

		public CustomDialog() {
			// NO ARG CONSTRUCTOR---REQUIRED
		}

		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.igradito_custom_dialogbox,
					container);

			String[] mensa_list = new String[] { "Povo Mensa", "Povo Mensa Veloce",
					"Tommaso Gar", "Zannela", "Mesiano 1", "Mesiano 2" };

			Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
			
			//ADD LISTENER TO THE ANNULA BUTTON
			view.findViewById(R.id.annulla_button).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				dismiss();
				}
			});

			final ArrayAdapter<String> splist = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item, mensa_list);

			spinner.setAdapter(splist);

			return view;
		}
	}

}
