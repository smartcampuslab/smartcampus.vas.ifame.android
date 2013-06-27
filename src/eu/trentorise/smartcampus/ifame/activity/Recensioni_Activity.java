package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.Date;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.model.GiudizioNew;
import eu.trentorise.smartcampus.ifame.model.Likes;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.model.Review;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Recensioni_Activity extends Activity {

	List<Mensa> listaMense = null;
	Piatto piatto;
	String user_id;
	Mensa mensa;
	GiudizioDataToPost giudizioDataToPost = null;
	String add = "http://192.168.33.106:8080/web-ifame";

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

		// Get intents from the igradito activity
		piatto = (Piatto) extras.get("nome_piatto");
		setTitle(piatto.getPiatto_nome());

		user_id = (String) extras.get("user_id");
		mensa = (Mensa) extras.get("igradito_spinner_mense");

		//get list of comments
		new GetGiudizioConnector(Recensioni_Activity.this).execute(
				mensa.getMensa_id(), piatto.getPiatto_id());

	}

	public class ReviewAdapter extends ArrayAdapter<GiudizioNew> {

		public ReviewAdapter(Activity activity, int layout_id,
				List<GiudizioNew> reviews) {
			super(activity, layout_id, reviews);
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_igradito_recensioni,
					null);

			// TO BE USED WHEN REVIEW MODEL AND CONNECTOR ARE READY
			TextView username = (TextView) convertView
					.findViewById(R.id.iGradito_recensioni_nome_utente);
			TextView review_date = (TextView) convertView
					.findViewById(R.id.iGradito_recensioni_data_inserimento);
			TextView review_content = (TextView) convertView
					.findViewById(R.id.iGradito_recensioni_review_content);
			TextView like_count = (TextView) convertView
					.findViewById(R.id.like_count);
			TextView dislike_count = (TextView) convertView
					.findViewById(R.id.dislike_count);

			GiudizioNew giudizio = getItem(position);

			username.setText(giudizio.getUser_id().toString());
			review_date.setText(giudizio.getUltimo_aggiornamento().toString());
			review_content.setText(giudizio.getCommento());

			List<Likes> list_likes = giudizio.getLikes();
			Integer likes = 0;
			Integer dislikes = 0;

			for (Likes l : list_likes) {
				if (l.getIs_like()) {
					likes++;
				} else {
					dislikes++;
				}
			}

			like_count.setText(likes.toString());
			dislike_count.setText(dislikes.toString());

			return convertView;
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

		EditText cd_editText;
		String commento;

		public CustomDialog() {
			// NO ARG CONSTRUCTOR---REQUIRED
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.igradito_custom_dialogbox,
					container);

			// set the title of the dialog box
			getDialog().setTitle("La tua recensione... ");

			cd_editText = (EditText) view
					.findViewById(R.id.custom_dialog_etext);

			// ADD LISTENER TO THE ANNULA BUTTON
			view.findViewById(R.id.annulla_button).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							dismiss();
						}
					});

			// ADD LISTENER TO THE OK BUTTON
			view.findViewById(R.id.OK_button).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							giudizioDataToPost = new GiudizioDataToPost();
							commento = cd_editText.getText().toString();
							giudizioDataToPost.commento = commento;
							giudizioDataToPost.userId = Long.parseLong(user_id);
							giudizioDataToPost.voto = (float) 7;

							// Toast.makeText(getActivity(), commento,
							// Toast.LENGTH_LONG).show();

							new PostGiudizioConnector(getActivity(),
									giudizioDataToPost).execute(
									mensa.getMensa_id(), piatto.getPiatto_id());
							dismiss();
						}
					});

			return view;
		}
	}

	/**
	 * CONNECTOR TO GET THE LIST OF COMMENTS
	 * 
	 */

	private class GetGiudizioConnector extends
			AsyncTask<Long, Void, List<GiudizioNew>> {
		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		
		public GetGiudizioConnector(Context applicationContext) {
			context = applicationContext;
		}

		@Override
		protected List<GiudizioNew> doInBackground(Long... params) {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"mensa/" + params[0] + "/piatto/" + params[1] + "/giudizio");

			// MessageRequest request = new MessageRequest(add, "mensa/"
			// + params[0] + "/piatto/" + params[1] + "/giudizio/add");

			request.setMethod(Method.GET);

			// request.setBody(Utils.convertToJSON(data));

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
					List<GiudizioNew> list = Utils.convertJSONToObjects(body,
							GiudizioNew.class);
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
		protected void onPostExecute(List<GiudizioNew> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createGiudiziList(result);

		}
	}

	/**
	 * 
	 * CONNECTOR TO POST COMMENTS
	 * 
	 */

	private class PostGiudizioConnector extends
			AsyncTask<Long, Void, List<GiudizioNew>> {
		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		GiudizioDataToPost data = null;

		public PostGiudizioConnector(Context applicationContext,
				GiudizioDataToPost data) {
			context = applicationContext;
			this.data = data;
		}

		@Override
		protected List<GiudizioNew> doInBackground(Long... params) {
			// TODO Auto-generated method stu

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"mensa/" + params[0] + "/piatto/" + params[1]
							+ "/giudizio/add");

			// MessageRequest request = new MessageRequest(add, "mensa/"
			// + params[0] + "/piatto/" + params[1] + "/giudizio/add");

			request.setMethod(Method.POST);

			request.setBody(Utils.convertToJSON(data));

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
					List<GiudizioNew> list = Utils.convertJSONToObjects(body,
							GiudizioNew.class);
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
		protected void onPostExecute(List<GiudizioNew> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createGiudiziList(result);
			Toast.makeText(getApplicationContext(), "Recensione Aggiunta", Toast.LENGTH_LONG).show(); 
		}
	}

	private void createGiudiziList(List<GiudizioNew> reviews) {
		ListView giudiziListview = (ListView) findViewById(R.id.recensioni_list);

		ReviewAdapter adapter = new ReviewAdapter(Recensioni_Activity.this,
				android.R.layout.simple_list_item_1, reviews);

		giudiziListview.setAdapter(adapter);
	}

}
