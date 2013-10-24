package eu.trentorise.smartcampus.ifame.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Piatto;

public class IGraditoPiattoListAdapter extends ArrayAdapter<Piatto> implements
		Filterable {

	public List<Piatto> complete_list;

	public IGraditoPiattoListAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);

		complete_list = new ArrayList<Piatto>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Piatto piattoDelGiorno = getItem(position);

		TextView nome_piatto_del_giorno;
		TextView kcal_piatto_del_giorno;

		if ((piattoDelGiorno.getPiatto_nome().length()) == 1) {
			convertView = inflater.inflate(
					R.layout.layout_row_header_menu_adapter, null);

			nome_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_day_header_adapter);
			kcal_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu__kcal_header_adapter);
		} else {
			convertView = inflater.inflate(R.layout.layout_row_menu_adapter,
					null);

			nome_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_name_adapter);

			kcal_piatto_del_giorno = (TextView) convertView
					.findViewById(R.id.menu_kcal_adapter);

		}

		nome_piatto_del_giorno.setText(piattoDelGiorno.getPiatto_nome());
		kcal_piatto_del_giorno.setText("");
		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();

				// We implement here the filter logic

				if (constraint == null || constraint.length() == 0) {
					// No filter implemented we return all the list
					results.values = complete_list;
					results.count = complete_list.size();
				} else {
					// We perform filtering operation

					List<Piatto> piatti = new ArrayList<Piatto>();

					for (Piatto p : complete_list) {

						if (p.getPiatto_nome().toUpperCase()
								.contains(constraint.toString().toUpperCase())) {
							piatti.add(p);
							// System.out.println("Il piatto è: "+
							// p.getPiatto().getPiatto_nome());

						}
					}

					results.values = piatti;
					// System.out.println("I piatti sono: " +
					// results.values);
					results.count = piatti.size();
					// System.out.println("Numero: " + results.count);

				}
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				// Now we have to inform the adapter about the new list
				// filtered

				IGraditoPiattoListAdapter.this.clear();

				if (results.count > 0) {

					List<Piatto> values = (List<Piatto>) results.values;
					for (Piatto p : values) {

						IGraditoPiattoListAdapter.this.add(p);

					}

					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;

	}

}