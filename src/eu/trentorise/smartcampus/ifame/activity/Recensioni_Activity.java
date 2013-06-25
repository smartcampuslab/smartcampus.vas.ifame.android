package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Review;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Recensioni_Activity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	List<Mensa> listaMense = null;
	
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
					"questa e' solo la prova di una recensione", "17/06/13");
			lista_reviews.add(r);

		}

		ReviewAdapter adapter = new ReviewAdapter(Recensioni_Activity.this,
				R.layout.list_igradito_recensioni, lista_reviews);

		user_list.setAdapter(adapter);

		
		new IGraditoConnector(Recensioni_Activity.this).execute();
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
			
			//set the title of the dialog box
			getDialog().setTitle("La tua recensione... ");

			Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
			
			//ADD LISTENER TO THE ANNULA BUTTON
			view.findViewById(R.id.annulla_button).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				dismiss();
				}
			});
			
			MyCursorAdapter adapter = new MyCursorAdapter(getActivity(), listaMense);
			spinner.setAdapter(adapter);

			return view;
		}
		
	}
	
	
	public class MyCursorAdapter extends BaseAdapter implements SpinnerAdapter{
	    private Activity activity;
	    private List<Mensa> lista_mense; 

	    public MyCursorAdapter(Activity activity, List<Mensa> lista_mense){
	        this.activity = activity;
	        this.lista_mense = lista_mense;
	    }

	    public int getCount() {
	        return lista_mense.size();
	    }

	    public Object getItem(int position) {
	        return lista_mense.get(position);
	    }

	    @Override
		public long getItemId(int position) {
			return lista_mense.get(position).getMensa_id();
		}
	    
	    public View getView(int position, View convertView, ViewGroup parent) {

	    View spinView;
	    if( convertView == null ){
	        LayoutInflater inflater = activity.getLayoutInflater();
	        spinView = inflater.inflate(R.layout.layout_listview_igradito, null);
	    } else {
	         spinView = convertView;
	    }
	    TextView nome_mensa = (TextView) spinView.findViewById(R.id.nome_mensa);
	    	    
	    nome_mensa.setText(lista_mense.get(position).getMensa_nome());
	    
	    return spinView;
	    
	    }

	}
	
	
	private class IGraditoConnector extends AsyncTask<Void, Void, List<Mensa>> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IGraditoConnector(Context applicationContext) {
			context = applicationContext;
		}

		private List<Mensa> getMense() {
			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getmense");
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
					List<Mensa> list = Utils.convertJSONToObjects(body,
							Mensa.class);
					return list;
				} else {
					return null;
				}

			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		

		@Override
		protected List<Mensa> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getMense();
		}

		@Override
		protected void onPostExecute(List<Mensa> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			listaMense = result;
			
		}
	}	
}
