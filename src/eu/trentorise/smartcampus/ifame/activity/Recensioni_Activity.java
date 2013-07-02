package eu.trentorise.smartcampus.ifame.activity;

import java.util.List;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.model.GiudizioNew;
import eu.trentorise.smartcampus.ifame.model.Likes;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
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
	// PostLikeConnector postLike;
	GiudizioDataToPost giudizioDataToPost = null;

	String add = "http://192.168.33.106:8080/web-ifame";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_pagina_recensioni);
		Bundle extras = getIntent().getExtras();

		if (extras == null) {
			return;
		}
		// Get intents from the igradito activity
		piatto = (Piatto) extras.get("nome_piatto");
		setTitle(piatto.getPiatto_nome());

		user_id = (String) extras.get("user_id");
		mensa = (Mensa) extras.get("igradito_spinner_mense");

		// get list of comments
		new GetGiudizioConnector(Recensioni_Activity.this).execute(
				mensa.getMensa_id(), piatto.getPiatto_id());

	}

	/**
	 * DISPLAY THE CUSTOMIZED DIALOG
	 * 
	 */
	private void showCustomizedDialog() {
		// hide the title bar
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		FragmentManager fm = getFragmentManager();
		CustomDialog cd = new CustomDialog();
		cd.show(fm, "fragment");

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

	/**
	 * 
	 * 
	 * CLASS TO CREATE A CUSTOM DIALOG BOX FOR THE REVIEWS
	 * 
	 */

	public class CustomDialog extends DialogFragment {

		EditText cd_editText;
		String commento;
		SeekBar cd_seekbar;

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
			
			//Get the seekbar asscociated with this view
			cd_seekbar = (SeekBar) view.findViewById(R.id.recensioni_seekbar);
			
			//ADD LISTENER TO THE SEEKBAR
			cd_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				int progressChanged = 0; 
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), progressChanged + "", Toast.LENGTH_LONG).show(); 
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					progressChanged = progress; 
					//seekBar.incrementProgressBy(1);
					
					
				}
			});

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
	 * 
	 * 
	 * ADAPTER FOR DISPLAYING REVIEWS
	 * PROBLEM : LIKE IS NOT UPDATED INSTANTANEOUSLY
	 * 
	 */
	public class ReviewAdapter extends ArrayAdapter<GiudizioNew> {

		GiudizioNew giudizio;
		TextView username;
		TextView review_date;
		TextView review_content;
		TextView like_count;
		TextView dislike_count;
		ImageButton like_button;
		ImageButton dislike_button;
		Integer count = 0;

		public ReviewAdapter(Activity activity, int layout_id,
				List<GiudizioNew> reviews) {
			super(activity, layout_id, reviews);
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			giudizio = getItem(position);

			convertView = inflater.inflate(R.layout.list_igradito_recensioni,
					null);

			username = (TextView) convertView
					.findViewById(R.id.iGradito_recensioni_nome_utente);
			review_date = (TextView) convertView
					.findViewById(R.id.iGradito_recensioni_data_inserimento);
			review_content = (TextView) convertView
					.findViewById(R.id.iGradito_recensioni_review_content);
			like_count = (TextView) convertView.findViewById(R.id.like_count);
			dislike_count = (TextView) convertView
					.findViewById(R.id.dislike_count);
			like_button = (ImageButton) convertView
					.findViewById(R.id.like_button);
			dislike_button = (ImageButton) convertView
					.findViewById(R.id.dislike_button);

			List<Likes> list_likes = giudizio.getLikes();
			Integer likes_count = 0;
			Integer dislikes = 0;

			for (Likes l : list_likes) {
				if (l.getIs_like()) {
					likes_count++;
				} else {
					dislikes++;
				}
			}

			count = likes_count;
			// ADD LISTENER TO THE LIKE BUTTON
			like_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Likes likes = new Likes();
					likes.setGiudizio_id(giudizio.getGiudizio_id());
					likes.setIs_like(true);
					likes.setUser_id(Long.parseLong(user_id));

					count++; 
					new PostLikeConnector(Recensioni_Activity.this, count)
					.execute(likes);

//					like_count.setText((count++) + "");
				}
			});

			// ADD LISTENER TO THE DISLIKE BUTTON
			dislike_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			username.setText(giudizio.getUser_id().toString());
			review_date.setText(giudizio.getUltimo_aggiornamento().toString());
			review_content.setText(giudizio.getCommento());

			like_count.setText(likes_count.toString());
			dislike_count.setText(dislikes.toString());
			
			return convertView;
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
			Toast.makeText(getApplicationContext(), "Recensione Aggiunta",
					Toast.LENGTH_LONG).show();
		}
	}

	private void createGiudiziList(List<GiudizioNew> reviews) {
		ListView giudiziListview = (ListView) findViewById(R.id.recensioni_list);

		ReviewAdapter adapter = new ReviewAdapter(Recensioni_Activity.this,
				android.R.layout.simple_list_item_1, reviews);
		giudiziListview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * CONNECTOR TO POST LIKES
	 * 
	 */

	private class PostLikeConnector extends AsyncTask<Likes, Void, Void> {
		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		int counter = 0; 
		TextView txtView;
		
		public PostLikeConnector(Context applicationContext,
				int counter) {
			context = applicationContext;
			this.counter = counter;
		}
		
		@Override
		protected void onPreExecute() {
			txtView = (TextView) findViewById(R.id.like_count); //tell asyntask to find the view before perfoming other tasks
			txtView.setText((counter++) + ""); //in case this line becomes problematic, move it to the onPostExecute method
		}

//		public PostLikeConnector(Context applicationContext) {
//			context = applicationContext;
//
//		}

		@Override
		protected Void doInBackground(Likes... like) {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"giudizio/" + like[0].getGiudizio_id() + "/like");

			request.setMethod(Method.POST);

			request.setBody(Utils.convertToJSON(like[0]));

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {
					//system out success 
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
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// Toast.makeText(getApplicationContext(), "Like assegnato",
			// Toast.LENGTH_LONG).show();
			
		}
	}

}
