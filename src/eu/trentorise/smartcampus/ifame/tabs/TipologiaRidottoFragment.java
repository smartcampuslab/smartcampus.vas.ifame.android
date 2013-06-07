package eu.trentorise.smartcampus.ifame.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;

public class TipologiaRidottoFragment extends SherlockFragment {

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
		return inflater.inflate(R.layout.layout_tipologia_ridotto, container, false);
	}

	
	
	@Override
	public void onResume() {

		//menu ridotto 1
		TextView primo_e_2contorni1 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_primo_e_2contorni1);
		TextView dessert1 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_dessert1);
		TextView pane1 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_pane1); 
				
		primo_e_2contorni1.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_first)+", "+ getString(R.string.iDeciso_2contorni));
		primo_e_2contorni1.setTypeface(null, Typeface.BOLD);
		dessert1.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane1.setText("+ "+getString(R.string.iDeciso_pane));
		
		//menu ridotto2
		TextView secondo_e_2contorni2 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_secondo_e_2contorni2);
		TextView dessert2 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_dessert2);
		TextView pane2 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_pane2); 
				
		secondo_e_2contorni2.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_second)+", "+ getString(R.string.iDeciso_2contorni));
		secondo_e_2contorni2.setTypeface(null, Typeface.BOLD);
		dessert2.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane2.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		//menu ridotto3   pasta station cambia dal primo??????????? sul sito sembra di si....per ora non ne tengo conto perchè credo sia sbagliato 
		TextView insalatona3 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_insalatona3);
		TextView due_a_scelta_tra3 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_2a_scelta_tra3);
		TextView pane3 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_pane3); 
				
		insalatona3.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_insalatona)+", ");
		insalatona3.setTypeface(null, Typeface.BOLD);
		due_a_scelta_tra3.setText("+ "+getString(R.string.iDeciso_2a_scelta_tra)+ ": "+getString(R.string.iDeciso_contorni)+ " e "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane3.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		//menu ridotto4
		TextView pizza4 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_pizza4);
		TextView due_a_scelta_tra4 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_2a_scelta_tra4);
		TextView pane4 = (TextView) theContainer.findViewById(R.id.tipologia_ridotto_pane4); 
				
		pizza4.setText("- "+getString(R.string.iDeciso_pizza)+", ");
		pizza4.setTypeface(null, Typeface.BOLD);
		due_a_scelta_tra4.setText("+ "+getString(R.string.iDeciso_2a_scelta_tra)+ ": "+getString(R.string.iDeciso_contorni)+ " e "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
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
