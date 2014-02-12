package eu.trentorise.smartcampus.ifame.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.OperaPayment;


public class HistoryAdapter extends ArrayAdapter<OperaPayment> {

	private Context context;
	private int layoutResourceId;

	public HistoryAdapter(Context context, List<OperaPayment> arr) {
		super(context, R.layout.layout_row_history, arr);
		this.context = context;
		this.layoutResourceId = R.layout.layout_row_history;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		OperaPayment item = getItem(position);

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(layoutResourceId, parent, false);
		}

		TextView date = (TextView) row.findViewById(R.id.history_status);
		TextView content = (TextView) row.findViewById(R.id.history_amount);
		

		date.setText(item.getPaymentDate().substring(0, 10));
		
		String type =null;
		if(item.getProductPrice().contains("3.10")){
			type = "(Snack)";
		}
		if(item.getProductPrice().contains("4.40")){
			type = "(Ridotto)";
		}
		if(item.getProductPrice().contains("4.90")){
			type = "(Completo)";
		}
		content.setText(item.getProductPrice() + " € " + type  );
//		HistoryItem prev = null;
//		if (position > 0)
//			prev = getItem(position - 1);
//
//		if (prev == null || !(prev.getTitle().equals(item.getTitle()))) {
//			title.setVisibility(View.VISIBLE);
//		} else {
//			title.setVisibility(View.GONE);
//		}
		return row;
	}

}
