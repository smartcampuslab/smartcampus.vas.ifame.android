package eu.trentorise.smartcampus.ifame.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class MensaAdapter extends ArrayAdapter<Mensa> {

	private LayoutInflater inflater;
	private Drawable icon_selected;

	public MensaAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
		icon_selected = context.getResources().getDrawable(
				R.drawable.ic_action_favourite_selected);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = inflater.inflate(R.layout.layout_list_view_ifretta, null);

		TextView text_view_nome_mensa = (TextView) convertView
				.findViewById(R.id.list_ifretta);

		String mensa_nome = getItem(position).getMensa_nome();
		text_view_nome_mensa.setText(mensa_nome);

		if (mensa_nome.equalsIgnoreCase(MensaUtils
				.getFavouriteMensaName(getContext()))) {
			text_view_nome_mensa.setTextColor(Color.parseColor("#CC0000"));
			text_view_nome_mensa.setCompoundDrawablesWithIntrinsicBounds(null,
					null, icon_selected, null);
			// text_view_nome_mensa.setTypeface(null, Typeface.BOLD);
		} else {
			// text_view_nome_mensa.setCompoundDrawablesWithIntrinsicBounds(null,
			// null, null, null);
			text_view_nome_mensa.setTextColor(Color.BLACK);
		}

		return convertView;
	}

	// @Override
	// public View getDropDownView(int position, View convertView, ViewGroup
	// parent) {
	// View spinView;
	//
	// spinView = inflater.inflate(
	// R.layout.layout_igradito_spinner_mensa_dropdown, null);
	//
	// Mensa mensa = (Mensa) getItem(position);
	// TextView nome_mensa = (TextView) spinView
	// .findViewById(R.id.textview_nome_mensa);
	// nome_mensa.setText(mensa.getMensa_nome());
	//
	// return spinView;
	// }
}
