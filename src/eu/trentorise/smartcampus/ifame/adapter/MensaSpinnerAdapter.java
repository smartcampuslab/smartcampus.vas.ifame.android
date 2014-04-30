package eu.trentorise.smartcampus.ifame.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import it.smartcampuslab.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class MensaSpinnerAdapter extends ArrayAdapter<Mensa> {

	private LayoutInflater inflater;
	private final String favouriteMensaName;

	public MensaSpinnerAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);

		favouriteMensaName = MensaUtils.getFavouriteMensaName(context);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = inflater.inflate(R.layout.layout_spinner_mense_textview,
				null);

		TextView text_view_nome_mensa = (TextView) convertView
				.findViewById(R.id.spinner_mense_textview);
		text_view_nome_mensa.setTextSize(20);

		Mensa mensa = getItem(position);
		text_view_nome_mensa.setText(mensa.getMensa_nome());

		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		convertView = inflater.inflate(
				R.layout.layout_spinner_mense_textview_dropdown, null);
		TextView nome_mensa = (TextView) convertView
				.findViewById(R.id.spinner_mense_textview);

		Mensa mensa = (Mensa) getItem(position);

		if (mensa.getMensa_nome().equalsIgnoreCase(favouriteMensaName)) {

			convertView.setBackgroundColor(Color.parseColor("#CC0000"));
			nome_mensa.setPadding(18, 12, 5, 12);

		} else {
			nome_mensa.setCompoundDrawables(null, null, null, null);
			nome_mensa.setPadding(18, 15, 5, 15);
		}

		nome_mensa.setText(mensa.getMensa_nome());

		return convertView;
	}
}
