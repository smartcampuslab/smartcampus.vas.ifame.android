package eu.trentorise.smartcampus.ifame.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;


public class OrariAdapter extends ArrayAdapter<String> {

	private Context context;
	private int layoutResourceId;

	public OrariAdapter(Context context, List<String> arr) {
		super(context, R.layout.layout_row_orari, arr);
		this.context = context;
		this.layoutResourceId = R.layout.layout_row_orari;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		String item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
		}

		TextView gg = (TextView) row.findViewById(R.id.gg);
		TextView date = (TextView) row.findViewById(R.id.data);
		
		
		Date sToDate;
		try {
			sToDate= new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN).parse(item);
			SimpleDateFormat dateFormatgg = new SimpleDateFormat("EEEE");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateStringTitle = dateFormat.format(sToDate);
			String dateStringDay = dateFormatgg.format(sToDate);
			gg.setText(dateStringTitle);
			date.setText(dateStringDay);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row;
	}

}
