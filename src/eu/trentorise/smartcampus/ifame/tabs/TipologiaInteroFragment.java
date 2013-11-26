package eu.trentorise.smartcampus.ifame.tabs;

/*
 * 
 * fragment per il menu intero
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.ComponiMenu;
import eu.trentorise.smartcampus.ifame.activity.Fai_il_tuo_menu;
import eu.trentorise.smartcampus.ifame.activity.ISoldi;
import eu.trentorise.smartcampus.ifame.activity.ComponiMenu.chosenMenu;

public class TipologiaInteroFragment extends SherlockFragment {

	ViewGroup theContainer;
	boolean isC1Avail;
	boolean isC2Avail;
	boolean isDessertAvail;
	
	public static final String PRIMO_TEXT = "primo";
	public static final String PASTA_STATION_TEXT = "pasta_station";
	public static final String INSALATONA_TEXT = "insalatona";
	public static final String PANINO_TEXT = "panino";
	public static final String TRANCIO_PIZZA_TEXT = "trancio_pizza";
	public static final String PIZZA_TEXT = "pizza";
	public static final String PIATTO_FREDDO_TEXT = "piatto_freddo";
	public static final String SECONDO_TEXT = "secondo";
	public static final String CONTORNO1_TEXT = "contorno1";
	public static final String CONTORNO2_TEXT = "contorno2";
	public static final String DESSERT_TEXT = "dessert";
	public static final String CAFFE_TEXT = "caffe";
	public static final String ACQUA_TEXT = "acqua";
	public static final String SALSA2_TEXT = "salsa2";
	public static final String PANE1_TEXT = "pane1";
	public static final String PANE2_TEXT = "pane2";

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
		return inflater.inflate(R.layout.layout_tipologia_intero_fr, container,
				false);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onResume() {

		Intent i = getSherlockActivity().getIntent();
		boolean isCalled = i.getBooleanExtra(
				Fai_il_tuo_menu.HAS_CALLED_TIPOLOGIE, false);
		TextView buyable = (TextView) theContainer
				.findViewById(R.id.tipologia_intero_buyable);
	

		if (isCalled) {

			SharedPreferences pref = getSherlockActivity()
					.getSharedPreferences(
							getString(R.string.iFretta_preference_file),
							Context.MODE_PRIVATE);
			if (pref.contains(ISoldi.GET_AMOUNT_MONEY)) {
				float cash = pref.getFloat(ISoldi.GET_AMOUNT_MONEY, 0);
				if (cash >= 4.70) {
					buyable.setVisibility(View.VISIBLE);
					buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_sufficiente)
							+ cash);
					buyable.setTextColor(Color.parseColor("#08D126"));
				} else {
					float difference = 4.70f - cash;
					buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_insufficiente));
					buyable.setTextColor(Color.parseColor("#CF323C"));
				}

			}

		} else

			buyable.setVisibility(View.GONE);
		
		

		ArrayList<String> selected_menu = i.getStringArrayListExtra(ComponiMenu.MENU_COMPATIBLES);
		
		if(selected_menu.contains(chosenMenu.Intero.toString())){
			
			HashMap<String, Boolean> mapCheckedItems = (HashMap<String, Boolean>) i.getSerializableExtra(ComponiMenu.MENU_CHECKED_TRUE);
			
			
			TextView intero1Title = (TextView) theContainer
					.findViewById(R.id.tipologia_intero1_titolo);
			intero1Title.setText(chosenMenu.Intero.toString());
			intero1Title.setPadding(5, 2, 5, 2);
			if (android.os.Build.VERSION.SDK_INT >= 16)
				intero1Title.setBackground(theContainer.getResources().getDrawable(R.drawable.shape_title_componimenu));
				else
					intero1Title.setBackgroundDrawable(theContainer.getResources().getDrawable(R.drawable.shape_title_componimenu));
			/////////////////////////////////////
			////////////////////////////////////
			////////////////////////////////////
			///////////////////////////////////
			///////////////////////////
			if(mapCheckedItems.containsKey(PRIMO_TEXT)){
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_primo);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_first) + ",");
				primo.setTypeface(null, Typeface.BOLD);
			}else{
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_primo);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_first) + ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
				/////////////////////////////////////////////////////////////////////////////////////////////////////////
			}
			
			
			if(mapCheckedItems.containsKey(SECONDO_TEXT)){
				TextView secondo = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_secondo);
				secondo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_second) + ",");
				secondo.setTypeface(null, Typeface.BOLD);
			}else{
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_secondo);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_second) + ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}
			
			if(mapCheckedItems.containsKey(CONTORNO2_TEXT)){
				TextView contorno2 = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_contorno2);
				contorno2.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno2) + ",");
				contorno2.setTypeface(null, Typeface.BOLD);
			}else{
				TextView contorno2 = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_contorno2);
				contorno2.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno2) + ",");
				contorno2.setTypeface(null, Typeface.BOLD);
				contorno2.setTextColor(Color.parseColor("#08D126"));
			}
			
			if(mapCheckedItems.containsKey(DESSERT_TEXT)){
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_dessert);
				dessert.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert) + ",");
				dessert.setTypeface(null, Typeface.BOLD);
			}else{
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_dessert);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert) + ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}
			
			if(mapCheckedItems.containsKey(PANE1_TEXT)){
				TextView pane1 = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_pane1);
				pane1.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1));
				pane1.setTypeface(null, Typeface.BOLD);
			}else{
				TextView pane1 = (TextView) theContainer
						.findViewById(R.id.tipologia_intero_pane1);
				pane1.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1));
				pane1.setTypeface(null, Typeface.BOLD);
				pane1.setTextColor(Color.parseColor("#08D126"));
			}
			
		}else{
			RelativeLayout containerIntero = (RelativeLayout)theContainer.findViewById(R.id.container_intero1);
			containerIntero.setVisibility(View.GONE);
		}

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
