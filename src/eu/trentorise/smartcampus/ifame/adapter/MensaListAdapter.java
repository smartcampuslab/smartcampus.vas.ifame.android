package eu.trentorise.smartcampus.ifame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;

public class MensaListAdapter extends ArrayAdapter<Mensa> {

	public MensaListAdapter(Context context) {
		super(context, R.layout.layout_list_view_ifretta);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = inflater.inflate(R.layout.layout_list_view_ifretta, null);

		TextView text_view_nome_mensa = (TextView) convertView
				.findViewById(R.id.list_ifretta);

		Mensa mensa = getItem(position);

		text_view_nome_mensa.setText(mensa.getMensa_nome());

		// NOT YET IMPLEMENTED FAVOURITE MENSA FUNCTIONALITY
		// String favourite_mensa_name = SharedPreferencesUtils
		// .getDefaultMensa(context);
		// // se la mensa preferita salvata, � quella che stiamo esaminando,
		// // allora la sottolineamo e ci aggiungiamo l'icona star
		// if (favourite_mensa_name != null
		// && m.getMensa_nome().equals(favourite_mensa_name)) {
		// Drawable icon_to_right = convertView.getResources().getDrawable(
		// android.R.drawable.star_off);
		// text_view_nome_mensa.setCompoundDrawablesWithIntrinsicBounds(null,
		// null, icon_to_right, null);
		// text_view_nome_mensa.setTypeface(null, Typeface.BOLD);
		// }

		return convertView;
	}

}
