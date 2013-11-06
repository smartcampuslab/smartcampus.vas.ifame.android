package eu.trentorise.smartcampus.ifame.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
	private Context context;
	private final String user_id;

	public ReviewListAdapter(Context context, String user_id) {
		super(context, android.R.layout.simple_list_item_1);
		this.context = context;
		this.user_id = user_id;

		dateformat = new SimpleDateFormat("HH:mm  dd/MM/yy");
	}

	public ReviewListAdapter(Context context, String user_id,
			List<Giudizio> reviews) {
		super(context, android.R.layout.simple_list_item_1, reviews);
		this.context = context;
		this.user_id = user_id;

		dateformat = new SimpleDateFormat("HH:mm  dd/MM/yy");
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		final DataHandler handler;
		final Giudizio giudizio = getItem(position);
		if (view == null) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_igradito_recensioni, null);

			handler = new DataHandler();

			handler.username = (TextView) view
					.findViewById(R.id.iGradito_recensioni_nome_utente);
			handler.review_date = (TextView) view
					.findViewById(R.id.iGradito_recensioni_data_inserimento);
			handler.review_content = (TextView) view
					.findViewById(R.id.iGradito_recensioni_review_content);
			handler.like_count_view = (TextView) view
					.findViewById(R.id.like_count);
			handler.dislike_count_view = (TextView) view
					.findViewById(R.id.dislike_count);
			handler.like_button = (ImageButton) view
					.findViewById(R.id.like_button);
			handler.dislike_button = (ImageButton) view
					.findViewById(R.id.dislike_button);

			handler.voto = (TextView) view
					.findViewById(R.id.iGradito_recensioni_voto);

			List<Likes> list_likes = giudizio.getLikes();
			Integer likes_count = 0;
			Integer dislikes_count = 0;

			for (Likes l : list_likes) {
				if (l.getIs_like()) {
					likes_count++;

					if (l.getUser_id() == Long.parseLong(user_id)) {
						handler.non_ha_ancora_fatto_like_o_dislike = false;
						handler.is_like = true;
						handler.like_button
								.setImageResource(R.drawable.icon_like_up);
					}

				} else {
					dislikes_count++;

					if (l.getUser_id() == Long.parseLong(user_id)) {
						handler.non_ha_ancora_fatto_like_o_dislike = false;
						handler.is_like = false;
						handler.dislike_button
								.setImageResource(R.drawable.icon_like_down);
					}
				}
			}

			handler.like_count = likes_count;
			handler.dislike_count = dislikes_count;

			view.setTag(handler);
		} else {
			handler = (DataHandler) view.getTag();
		}

		handler.username.setText(giudizio.getUser_id().toString());
		handler.review_date.setText(dateformat.format(
				giudizio.getUltimo_aggiornamento()).toString());
		handler.review_content.setText(giudizio.getCommento());
		handler.like_count_view.setText(handler.like_count + "");
		handler.dislike_count_view.setText(handler.dislike_count + "");
		handler.voto.setText(giudizio.getVoto() + "");

		// ADD LISTENER TO THE LIKE BUTTON
		handler.like_button.setOnClickListener(new OnClickListener() {

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

					handler.like_button
							.setImageResource(R.drawable.icon_like_up);

					new PostLikeTask(context).execute(likes);
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

						handler.like_button
								.setImageResource(R.drawable.icon_like_up);

						handler.dislike_button
								.setImageResource(R.drawable.icon_like_down_outline);

						new PostLikeTask(context).execute(likes);

					} else {
						// ho fatto like ancora tolgo il like
						handler.non_ha_ancora_fatto_like_o_dislike = true;
						// lo metto null ma dovrebbe fottere cosa sia
						handler.is_like = null;

						likes.setIs_like(true);

						handler.like_count--;
						handler.like_count_view
								.setText(handler.like_count + "");

						handler.like_button
								.setImageResource(R.drawable.icon_like_up_outline);

						new DeleteLikeTask(context).execute(likes);
					}
				}
			}
		});

		// ADD LISTENER TO THE LIKE BUTTON
		handler.dislike_button.setOnClickListener(new OnClickListener() {

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

					handler.dislike_button
							.setImageResource(R.drawable.icon_like_down);

					new PostLikeTask(context).execute(likes);

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

						handler.dislike_button
								.setImageResource(R.drawable.icon_like_down);

						handler.like_button
								.setImageResource(R.drawable.icon_like_up_outline);

						new PostLikeTask(context).execute(likes);

					} else {
						// ho fatto dislike ancora tolgo il dislike
						handler.non_ha_ancora_fatto_like_o_dislike = true;
						// lo metto null ma dovrebbe fottere cosa sia
						handler.is_like = null;

						likes.setIs_like(false);

						handler.dislike_count--;
						handler.dislike_count_view
								.setText(handler.dislike_count + "");

						handler.dislike_button
								.setImageResource(R.drawable.icon_like_down_outline);

						new DeleteLikeTask(context).execute(likes);
					}
				}
			}
		});
		return view;
	}

	private static class DataHandler {

		TextView voto;
		TextView username;
		TextView review_date;
		TextView review_content;
		TextView like_count_view;
		TextView dislike_count_view;
		ImageButton like_button;
		ImageButton dislike_button;
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
}