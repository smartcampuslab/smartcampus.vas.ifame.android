package eu.trentorise.smartcampus.ifame.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;

public class TipologiaInteroFragment extends SherlockFragment {

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
		return inflater.inflate(R.layout.layout_tipologia_intero_fr, container, false);
	}

	
	
	@Override
	public void onResume() {
		
		TextView bigLabel = (TextView)theContainer.findViewById(R.id.tipologia_intero_biglabel);
		bigLabel.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_first)+", "+getString(R.string.iDeciso_compose_menu_checkbox_second));
		bigLabel.setTypeface(null, Typeface.BOLD);
		
		TextView contorni = (TextView)theContainer.findViewById(R.id.tipologia_intero_2contorni);
		contorni.setText("+ "+getString(R.string.iDeciso_2contorni));
		
		TextView dessert = (TextView)theContainer.findViewById(R.id.tipologia_intero_dessert);
		dessert.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));

		
		TextView pane = (TextView)theContainer.findViewById(R.id.tipologia_intero_pane);
		pane.setText("+ "+getString(R.string.iDeciso_pane));
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
