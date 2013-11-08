package eu.trentorise.smartcampus.ifame.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.asynctask.DeleteLikeTask;
import eu.trentorise.smartcampus.ifame.asynctask.PostLikeTask;
import eu.trentorise.smartcampus.ifame.model.Giudizio;
import eu.trentorise.smartcampus.ifame.model.Likes;

/**
 * ADAPTER FOR DISPLAYING REVIEWS FROM USERS
 */
public class ReviewListAdapter extends ArrayAdapter<Giudizio> {

	private SimpleDateFormat dateformat;
	private final String user_id;

	public ReviewListAdapter(Context context, String user_id) {
		super(context, android.R.layout.simple_list_item_1);
		this.user_id = user_id;

		dateformat = new SimpleDateFormat("dd/MM/yy  HH:mm");
	}

	public ReviewListAdapter(Context context, String user_id,
			List<Giudizio> reviews) {
		super(context, android.R.layout.simple_list_item_1, reviews);
		this.user_id = user_id;

		dateformat = new SimpleDateFormat("dd/MM/yy  HH:mm");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		final DataHandler handler;
		final Giudizio giudizio = getItem(position);
		if (view == null) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(
					R.layout.layout_igradito_recensioni_adapter, null);

			handler = new DataHandler();

			// this.username = (TextView) view
			// .findViewById(R.id.iGradito_recensioni_nome_utente);
			handler.review_date = (TextView) view
					.findViewById(R.id.iGradito_recensioni_data_inserimento);
			handler.review_comment = (TextView) view
					.findViewById(R.id.iGradito_recensioni_review_content);
			handler.like_count_view = (TextView) view
					.findViewById(R.id.like_count);
			handler.dislike_count_view = (TextView) view
					.findViewById(R.id.dislike_count);
			handler.like_image_button = (ImageButton) view
					.findViewById(R.id.like_button);
			handler.dislike_image_button = (ImageButton) view
					.findViewById(R.id.dislike_button);
			handler.voto = (TextView) view
					.findViewById(R.id.iGradito_recensioni_voto);

			for (Likes l : giudizio.getLikes()) {
				if (l.getIs_like()) {
					handler.like_count++;

					if (l.getUser_id() == Long.parseLong(user_id)) {
						handler.non_ha_ancora_fatto_like_o_dislike = false;
						handler.is_like = true;
						handler.like_image_button
								.setImageResource(R.drawable.icon_like_up);
					}

				} else {
					handler.dislike_count++;

					if (l.getUser_id() == Long.parseLong(user_id)) {
						handler.non_ha_ancora_fatto_like_o_dislike = false;
						handler.is_like = false;
						handler.dislike_image_button
								.setImageResource(R.drawable.icon_like_down);
					}
				}
			}
			view.setTag(handler);
		} else {
			handler = (DataHandler) view.getTag();
		}

		// ADD LISTENER TO THE LIKE BUTTON
		handler.like_image_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Likes likes = new Likes();

				likes.setGiudizio_id(giudizio.getGiudizio_id());
				likes.setUser_id(Long.parseLong(user_id));

				if (handler.non_ha_ancora_fatto_like_o_dislike) {
					// caso base faccio like
					handler.non_ha_ancora_fatto_like_o_dislike = false;
					handler.is_like = true;

					likes.setIs_like(true);
					handler.like_count++;
					handler.like_count_view.setText(handler.like_count + "");

					handler.like_image_button
							.setImageResource(R.drawable.icon_like_up);

					new PostLikeTask(getContext()).execute(likes);
				} else {
					// ho gia messo un like o dislike
					if (!handler.is_like) {
						// sto cambiando idea da dislike a like
						handler.is_like = true;

						likes.setIs_like(true);

						// aumento i dislike
						handler.dislike_count--;
						handler.dislike_count_view
								.setText(handler.dislike_count + "");

						// riduco i like
						handler.like_count++;
						handler.like_count_view
								.setText(handler.like_count + "");

						handler.like_image_button
								.setImageResource(R.drawable.icon_like_up);

						handler.dislike_image_button
								.setImageResource(R.drawable.icon_like_down_outline);

						new PostLikeTask(getContext()).execute(likes);

					} else {
						// ho fatto like ancora tolgo il like
						handler.non_ha_ancora_fatto_like_o_dislike = true;
						// lo metto null ma dovrebbe fottere cosa sia
						handler.is_like = null;

						likes.setIs_like(true);

						handler.like_count--;
						handler.like_count_view
								.setText(handler.like_count + "");

						handler.like_image_button
								.setImageResource(R.drawable.icon_like_up_outline);

						new DeleteLikeTask(getContext()).execute(likes);
					}
				}
			}
		});

		// ADD LISTENER TO THE LIKE BUTTON
		handler.dislike_image_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Likes likes = new Likes();

				likes.setGiudizio_id(giudizio.getGiudizio_id());
				likes.setUser_id(Long.parseLong(user_id));

				if (handler.non_ha_ancora_fatto_like_o_dislike) {
					// non ho ancora fatto like o dislike e clicco su
					// dislike
					handler.non_ha_ancora_fatto_like_o_dislike = false;
					handler.is_like = false;

					likes.setIs_like(false);

					handler.dislike_count++;
					handler.dislike_count_view.setText(handler.dislike_count
							+ "");

					handler.dislike_image_button
							.setImageResource(R.drawable.icon_like_down);

					new PostLikeTask(getContext()).execute(likes);

				} else {
					// ho gia messo un like o dislike
					if (handler.is_like) {
						// sto cambiando idea da like a dislike
						handler.is_like = false;

						likes.setIs_like(false);

						// aumento i dislike
						handler.dislike_count++;
						handler.dislike_count_view
								.setText(handler.dislike_count + "");

						// riduco i like
						handler.like_count--;
						handler.like_count_view
								.setText(handler.like_count + "");

						handler.dislike_image_button
								.setImageResource(R.drawable.icon_like_down);

						handler.like_image_button
								.setImageResource(R.drawable.icon_like_up_outline);

						new PostLikeTask(getContext()).execute(likes);

					} else {
						// ho fatto dislike ancora tolgo il dislike
						handler.non_ha_ancora_fatto_like_o_dislike = true;
						// lo metto null ma dovrebbe fottere cosa sia
						handler.is_like = null;

						likes.setIs_like(false);

						handler.dislike_count--;
						handler.dislike_count_view
								.setText(handler.dislike_count + "");

						handler.dislike_image_button
								.setImageResource(R.drawable.icon_like_down_outline);

						new DeleteLikeTask(getContext()).execute(likes);
					}
				}
			}
		});

		// String giudizioUserName = "";
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// (1) Show only user id
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// stringNameToShow = giudizio.getUser_id().toString();
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// (2) Show user name (comment one of the two following option)
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// (2.1) Format: Name Surname
		// stringNameToShow = giudizio.getUser_name();
		// stringNameToShow = stringNameToShow.replace('.', ' ');
		// -------------------------------------------------------
		// (2.2) Format: Name S.
		// giudizioUserName = giudizio.getUser_name();
		// giudizioUserName = giudizioUserName.replace('.', ' ');
		// giudizioUserName = giudizioUserName.substring(0,
		// giudizioUserName.indexOf(" ") + 2)
		// + ".";
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// handler.username.setText(giudizioUserName);
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		handler.review_date.setText(dateformat.format(
				giudizio.getUltimo_aggiornamento()).toString());
		handler.review_comment.setText(giudizio.getCommento());
		handler.like_count_view.setText(handler.like_count + "");
		handler.dislike_count_view.setText(handler.dislike_count + "");

		handler.voto.setText(" " + formatUserVoto(giudizio.getVoto()));

		return view;
	}

	private static class DataHandler {

		TextView voto;
		// TextView username;
		TextView review_date;
		TextView review_comment;
		TextView like_count_view;
		TextView dislike_count_view;
		ImageButton like_image_button;
		ImageButton dislike_image_button;
		Integer like_count;
		Integer dislike_count;
		Boolean non_ha_ancora_fatto_like_o_dislike;
		Boolean is_like;

		public DataHandler() {

			like_count = 0;
			dislike_count = 0;
			non_ha_ancora_fatto_like_o_dislike = true;
		}
	}

	/**
	 * Just for showing 'Voto: 5' instead of 'Voto: 5.0' because user voto is
	 * always an integer
	 */
	public static String formatUserVoto(float f) {
		if (f == (int) f) {
			return ((int) f) + "";
		} else {
			return f + "";
		}
	}

}