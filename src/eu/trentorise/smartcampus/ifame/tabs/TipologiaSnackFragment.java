package eu.trentorise.smartcampus.ifame.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;

public class TipologiaSnackFragment extends SherlockFragment {

	ViewGroup theContainer;
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		theContainer = container;
		return inflater.inflate(R.layout.layout_tipologia_snack, container, false);
	}

	
	
	@Override
	public void onResume() {
		
		//menu snack 1
		TextView primo1 = (TextView) theContainer.findViewById(R.id.tipologia_snack_primo1);
		TextView contorno_o_dessert1 = (TextView) theContainer.findViewById(R.id.tipologia_snack_contorno_o_dessert1);
		TextView pane1 = (TextView) theContainer.findViewById(R.id.tipologia_snack_pane1); 
		
		primo1.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_first)+", ");
		primo1.setTypeface(null, Typeface.BOLD);
		contorno_o_dessert1.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo)+ " o "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane1.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		//menu snack 2
		TextView secondo2 = (TextView) theContainer.findViewById(R.id.tipologia_snack_secondo2);
		TextView contorno_o_dessert2 = (TextView)theContainer.findViewById(R.id.tipologia_snack_contorno_o_dessert2);
		TextView pane2 = (TextView) theContainer.findViewById(R.id.tipologia_snack_pane2); 
		
		secondo2.setText("- "+ getString(R.string.iDeciso_compose_menu_checkbox_second));		
		secondo2.setTypeface(null, Typeface.BOLD);
		contorno_o_dessert2.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo)+ " o "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane2.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		//menu snack 3
		TextView panino_o_pizza3 = (TextView) theContainer.findViewById(R.id.tipologia_snack_panino_o_pizza3);
		TextView dessert3 = (TextView)theContainer.findViewById(R.id.tipologia_snack_dessert3);
		TextView acqua_o_caffe3 = (TextView)theContainer.findViewById(R.id.tipologia_snack_acqua_o_caffe3);
		
		panino_o_pizza3.setText("- "+ getString(R.string.iDeciso_panino) + " o "+ getString(R.string.iDeciso_pizza_trancio));
		panino_o_pizza3.setTypeface(null, Typeface.BOLD);
		dessert3.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		acqua_o_caffe3.setText("+ "+getString(R.string.iDeciso_acqua_o_caffe));
		
		
		//menu snack 4
		TextView insalatona4 = (TextView) theContainer.findViewById(R.id.tipologia_snack_insalatona4);
		TextView pane4 = (TextView)theContainer.findViewById(R.id.tipologia_snack_pane4);
		
		insalatona4.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_insalatona));
		insalatona4.setTypeface(null, Typeface.BOLD);
		pane4.setText("+ "+getString(R.string.iDeciso_pane));
		super.onResume();
		
		
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
