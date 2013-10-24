package eu.trentorise.smartcampus.ifame.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.model.Piatto;

public class PiattoKcalListAdapter extends ArrayAdapter<Piatto> {

	private Calendar calendar;
	private SimpleDateFormat simpleDateFormat;

	public PiattoKcalListAdapter(Context context, List<Piatto> list) {
		super(context, android.R.layout.simple_list_item_1, list);

		calendar = Calendar.getInstance();
		simpleDateFormat = new SimpleDateFormat("EEEEE dd MMMM yyyy");
	}

	public PiattoKcalListAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);

		calendar = Calendar.getInstance();
		simpleDateFormat = new SimpleDateFormat("EEEEE dd MMMM yyyy");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Piatto p = getItem(position);
		// check if the name is a number
		if (p.getPiatto_nome().matches("[0-9]+")) {
			// ho un piatto sentinella setto il testo come data
			convertView = inflater.inflate(
					layout.layout_row_header_menu_adapter, null);

			TextView dayHeader = (TextView) convertView
					.findViewById(R.id.menu_day_header_adapter);
			TextView kcalHeader = (TextView) convertView
					.findViewById(R.id.menu__kcal_header_adapter);

			int day = Integer.parseInt(p.getPiatto_nome());
			calendar.set(Calendar.DATE, day);
			String date_formatted = simpleDateFormat.format(calendar.getTime());

			dayHeader.setText(date_formatted);
			dayHeader.setTextColor(Color.WHITE);
			kcalHeader.setTextColor(Color.WHITE);

		} else {
			// ho un piatto vero setto i campi coi valori corrispondenti
			convertView = inflater
					.inflate(layout.layout_row_menu_adapter, null);
			TextView name = (TextView) convertView
					.findViewById(R.id.menu_name_adapter);
			TextView kcal = (TextView) convertView
					.findViewById(R.id.menu_kcal_adapter);

			name.setText(p.getPiatto_nome());
			kcal.setText(p.getPiatto_kcal());
		}
		return convertView;
	}
}