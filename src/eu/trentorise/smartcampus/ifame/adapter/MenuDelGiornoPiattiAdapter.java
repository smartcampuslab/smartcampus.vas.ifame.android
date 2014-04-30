package eu.trentorise.smartcampus.ifame.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import it.smartcampuslab.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Piatto;

public class MenuDelGiornoPiattiAdapter extends ArrayAdapter<Piatto> {

	public MenuDelGiornoPiattiAdapter(Context context, List<Piatto> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
	}

	public MenuDelGiornoPiattiAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Piatto piattoDelGiorno = getItem(position);

		if (piattoDelGiorno.getPiatto_nome().matches("[0-9]+")) {

			convertView = inflater.inflate(
					R.layout.layout_row_header_menu_adapter, null);

			int num = Integer.parseInt(piattoDelGiorno.getPiatto_nome());

			TextView nome_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_day_header_adapter);
			if (num == 1) {
				nome_piatto_del_giorno.setText(getContext().getString(
						R.string.iDeciso_menu_del_giorno_primi));
			} else if (num == 2) {
				nome_piatto_del_giorno.setText(getContext().getString(
						R.string.iDeciso_menu_del_giorno_secondi));
			} else if (num == 3) {
				nome_piatto_del_giorno.setText(getContext().getString(
						R.string.iDeciso_menu_del_giorno_contorni));
			}

		} else {

			convertView = inflater.inflate(R.layout.layout_row_menu_adapter,
					null);

			TextView nome_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_name_adapter);
			TextView kcal_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_kcal_adapter);

			nome_piatto_del_giorno.setText(piattoDelGiorno.getPiatto_nome());
			kcal_piatto_del_giorno.setText(piattoDelGiorno.getPiatto_kcal());

		}
		return convertView;
	}

}