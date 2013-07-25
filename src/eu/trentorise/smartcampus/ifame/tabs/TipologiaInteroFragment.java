package eu.trentorise.smartcampus.ifame.tabs;
/*
 * 
 * fragment per il menu intero
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.Fai_il_tuo_menu;
import eu.trentorise.smartcampus.ifame.activity.ISoldi;

public class TipologiaInteroFragment extends SherlockFragment {

	ViewGroup theContainer;
	boolean isC1Avail;
	boolean isC2Avail;
	boolean isDessertAvail;

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

		String selected_menu = i.getStringExtra(Fai_il_tuo_menu.SELECTED_MENU);

		TextView primo = (TextView) theContainer
				.findViewById(R.id.tipologia_intero_primo);
		primo.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_first) + ",");
		primo.setTypeface(null, Typeface.BOLD);

		TextView secondo = (TextView) theContainer
				.findViewById(R.id.tipologia_intero_secondo);
		secondo.setText(" "
				+ getString(R.string.iDeciso_compose_menu_checkbox_second));
		secondo.setTypeface(null, Typeface.BOLD);

		TextView contornoA = (TextView) theContainer
				.findViewById(R.id.tipologia_intero_contorno_a);
		TextView contornoB = (TextView) theContainer.findViewById(R.id.tipologia_intero_contorno_b);
		
		contornoA.setText("+ " + getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo));
		contornoB.setText("+ " + getString(R.string.iDeciso_compose_menu_checkbox_contorno_freddo));

		TextView dessert = (TextView) theContainer
				.findViewById(R.id.tipologia_intero_dessert);
		dessert.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));

		TextView pane = (TextView) theContainer
				.findViewById(R.id.tipologia_intero_pane);
		pane.setText("+ " + getString(R.string.iDeciso_pane));

		if (isCalled && selected_menu.equals("Intero")) {
			isC1Avail = i.getBooleanExtra(Fai_il_tuo_menu.CONTORNO_1_AVAILABLE,
					false);
			isC2Avail = i.getBooleanExtra(Fai_il_tuo_menu.CONTORNO_2_AVAILABLE,
					false);
			isDessertAvail = i.getBooleanExtra(
					Fai_il_tuo_menu.DESSERT_AVAILABLE, false);
			pane.setTextColor(Color.parseColor("#08D126"));

		}

		boolean isPrimoSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_PRIMO,
				false);
		boolean isSecondoSelected = i.getBooleanExtra(
				Fai_il_tuo_menu.IS_SECONDO, false);
		boolean isC1Selected = i.getBooleanExtra(Fai_il_tuo_menu.IS_CONTORNO_1,
				false);
		boolean isC2Selected = i.getBooleanExtra(Fai_il_tuo_menu.IS_CONTORNO_2,
				false);
		boolean isDessertSelected = i.getBooleanExtra(
				Fai_il_tuo_menu.IS_DESSERT, false);
		boolean isInsalatonaSelected = i.getBooleanExtra(
				Fai_il_tuo_menu.IS_INSALATONA, false);
		boolean isPaninoSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_PANINO,
				false);
		boolean isPizzaSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_PIZZA,
				false);

		
		/*
		 * 
		 * mi assicuro di non trovarmi nei tre casi dove non � possibile acquistare l'intero
		 */
		if (isCalled && !isPaninoSelected
				&& !isInsalatonaSelected && !isPizzaSelected) {

			pane.setTextColor(Color.parseColor("#08D126"));

			if (!isPrimoSelected)
				primo.setTextColor(Color.parseColor("#08D126"));
			if (!isSecondoSelected)
				secondo.setTextColor(Color.parseColor("#08D126"));
			if (!isC1Selected)
				contornoA.setTextColor(Color.parseColor("#08D126"));
			if (!isC2Selected)
				contornoB.setTextColor(Color.parseColor("#08D126"));
			if (!isDessertSelected) {
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

		}

		/*
		 * 
		 * qui significa che arriviamo da un'altra tab e avamo selezionato uno dei tre piatti che non sono matchabili con il menu intero
		 */
		if (isCalled
				&& (isPaninoSelected || isInsalatonaSelected || isPizzaSelected)) {
			
			
			//coloro di grigio
			primo.setTextColor(Color.parseColor("#C4C4C4"));
			contornoA.setTextColor(Color.parseColor("#C4C4C4"));
			contornoB.setTextColor(Color.parseColor("#C4C4C4"));
			secondo.setTextColor(Color.parseColor("#C4C4C4"));
			dessert.setTextColor(Color.parseColor("#C4C4C4"));
			pane.setTextColor(Color.parseColor("#C4C4C4"));
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
