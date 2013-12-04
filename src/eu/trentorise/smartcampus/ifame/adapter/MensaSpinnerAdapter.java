package eu.trentorise.smartcampus.ifame.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class MensaSpinnerAdapter extends ArrayAdapter<Mensa> {

	private LayoutInflater inflater;

	public MensaSpinnerAdapter(Context context, List<Mensa> mense) {
		super(context, android.R.layout.simple_list_item_1, mense);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public MensaSpinnerAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);

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

		convertView = inflater.inflate(R.layout.layout_spinner_mense_textview,
				null);
		TextView nome_mensa = (TextView) convertView
				.findViewById(R.id.spinner_mense_textview);

		Mensa mensa = (Mensa) getItem(position);

		// se è la preferita metto il testo rosso
		if (mensa.getMensa_nome().equalsIgnoreCase(
				MensaUtils.getFavouriteMensaName(getContext()))) {
			convertView.setBackgroundColor(Color.parseColor("#CC0000"));
			// nome_mensa.setTextColor();
		}

		nome_mensa.setText(mensa.getMensa_nome());
		// left, top, right, bottom
		nome_mensa.setPadding(18, 15, 8, 15);

		return convertView;
	}

}
