package eu.trentorise.smartcampus.ifame.tabs;

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
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.Fai_il_tuo_menu;
import eu.trentorise.smartcampus.ifame.activity.ISoldi;

public class TipologiaRidottoFragment extends SherlockFragment {

	ViewGroup theContainer;
	Intent i;

	boolean isPrimoAvail;
	boolean isSecondoAvail;
	boolean isC1Avail;
	boolean isC2Avail;
	boolean isDessertAvail;
	boolean isInsalatonaAvail;
	boolean isPizzaAvail;
	private boolean isContorno1;
	private boolean isContorno2;
	private boolean isDessert;

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
		return inflater.inflate(R.layout.layout_tipologia_ridotto, container,
				false);
	}

	@Override
	public void onResume() {

		i = getSherlockActivity().getIntent();
		boolean isCalled = i.getBooleanExtra(
				Fai_il_tuo_menu.HAS_CALLED_TIPOLOGIE, false);
		TextView buyable = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_buyable);
		if (isCalled) {

			SharedPreferences pref = getSherlockActivity()
					.getSharedPreferences(
							getString(R.string.iFretta_preference_file),
							Context.MODE_PRIVATE);
			if (pref.contains(ISoldi.GET_AMOUNT_MONEY)) {
				float cash = pref.getFloat(ISoldi.GET_AMOUNT_MONEY, 0);
				if (cash >= 4.20) {
					buyable.setVisibility(View.VISIBLE);
					buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_sufficiente)
							+ cash);
					buyable.setTextColor(Color.parseColor("#08D126"));
				} else {
					float difference = 4.20f - cash;
					buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_insufficiente));
					buyable.setTextColor(Color.parseColor("#CF323C"));
				}

			}
		} else
			buyable.setVisibility(View.GONE);
		
		String selected_menu = i.getStringExtra(Fai_il_tuo_menu.SELECTED_MENU);

		Toast.makeText(getSherlockActivity(), "isCalled: "+isCalled+"\nSelected: "+selected_menu, Toast.LENGTH_LONG).show();
		isPrimoAvail = i
				.getBooleanExtra(Fai_il_tuo_menu.PRIMO_AVAILABLE, false);
		isSecondoAvail = i.getBooleanExtra(Fai_il_tuo_menu.SECONDO_AVAILABLE,
				false);
		isC1Avail = i.getBooleanExtra(Fai_il_tuo_menu.CONTORNO_1_AVAILABLE,
				false);
		isC2Avail = i.getBooleanExtra(Fai_il_tuo_menu.CONTORNO_2_AVAILABLE,
				false);
		isDessertAvail = i.getBooleanExtra(Fai_il_tuo_menu.DESSERT_AVAILABLE,
				false);
		isInsalatonaAvail = i.getBooleanExtra(
				Fai_il_tuo_menu.INSALATONA_AVAILABLE, false);
		isPizzaAvail = i
				.getBooleanExtra(Fai_il_tuo_menu.PIZZA_AVAILABLE, false);

		isContorno1 = i.getBooleanExtra(Fai_il_tuo_menu.IS_CONTORNO_1, false);
		isContorno2 = i.getBooleanExtra(Fai_il_tuo_menu.IS_CONTORNO_2, false);
		isDessert = i.getBooleanExtra(Fai_il_tuo_menu.IS_DESSERT, false);
		// menu ridotto 1
		TextView primo1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_primo1);
		TextView contorni1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorni1);
		TextView dessert1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert1);
		TextView pane1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane1);

		primo1.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_first) + ",");
		primo1.setTypeface(null, Typeface.BOLD);
		contorni1.setText(" " + getString(R.string.iDeciso_2contorni));
		contorni1.setTypeface(null, Typeface.BOLD);
		dessert1.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane1.setText("+ " + getString(R.string.iDeciso_pane));

		// menu ridotto2
		TextView secondo2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_secondo2);
		TextView contorni2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorni2);
		TextView dessert2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert2);
		TextView pane2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane2);

		secondo2.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_second)
				+ ",");
		secondo2.setTypeface(null, Typeface.BOLD);
		contorni2.setText(" " + getString(R.string.iDeciso_2contorni));
		contorni2.setTypeface(null, Typeface.BOLD);
		dessert2.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane2.setText("+ " + getString(R.string.iDeciso_pane));

		// menu ridotto3 pasta station cambia dal primo??????????? sul sito
		// sembra di si....per ora non ne tengo conto perchè credo sia sbagliato
		TextView insalatona3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_insalatona3);
		TextView due_a_scelta_tra3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_2a_scelta_tra3);
		TextView pane3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane3);
		TextView contorni3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorni3);
		TextView e3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_e3);
		TextView dessert3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert3);

		insalatona3.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona)
				+ ", ");
		insalatona3.setTypeface(null, Typeface.BOLD);
		due_a_scelta_tra3.setText("+ "
				+ getString(R.string.iDeciso_2a_scelta_tra) + ":");
		pane3.setText("+ " + getString(R.string.iDeciso_pane));
		contorni3.setText(" " + getString(R.string.iDeciso_contorni) + " ");
		dessert3.setText(" "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));

		// menu ridotto4
		TextView pizza4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pizza4);
		TextView due_a_scelta_tra4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_2a_scelta_tra4);
		TextView contorni4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorni4);
		TextView dessert4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert4);
		TextView e4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_e4);
		TextView pane4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane4);

		pizza4.setText("- " + getString(R.string.iDeciso_pizza) + ", ");
		pizza4.setTypeface(null, Typeface.BOLD);
		due_a_scelta_tra4.setText("+ "
				+ getString(R.string.iDeciso_2a_scelta_tra) + ":");
		contorni4.setText(" " + getString(R.string.iDeciso_contorni) + " ");
		dessert4.setText(" "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane4.setText("+ " + getString(R.string.iDeciso_pane));

		if (isCalled && selected_menu.equals("Ridotto1234")) {

			if (isPrimoAvail) {
				primo1.setTextColor(Color.parseColor("#08D126"));
			}
			if (isSecondoAvail) {
				secondo2.setTextColor(Color.parseColor("#08D126"));
			}
			if (isC1Avail || isC2Avail) {
				contorni1.setTextColor(Color.parseColor("#08D126"));
				contorni2.setTextColor(Color.parseColor("#08D126"));
				if (!(isContorno1 && isContorno2 && !isDessert)
						&& !(isContorno1 && !isContorno2 && isDessert)
						&& !(!isContorno1 && isContorno2 && isDessert)) {
					contorni3.setTextColor(Color.parseColor("#08D126"));
					contorni4.setTextColor(Color.parseColor("#08D126"));
				}
			}
			if (isDessertAvail) {
				dessert1.setTextColor(Color.parseColor("#08D126"));
				dessert2.setTextColor(Color.parseColor("#08D126"));
				if (!(isContorno1 && isContorno2 && !isDessert)
						&& !(isContorno1 && !isContorno2 && isDessert)
						&& !(!isContorno1 && isContorno2 && isDessert)) {
					dessert3.setTextColor(Color.parseColor("#08D126"));
					dessert4.setTextColor(Color.parseColor("#08D126"));
				}
			}
			// panino non l'ho messo
			if (isInsalatonaAvail)
				insalatona3.setTextColor(Color.parseColor("#08D126"));

			if (isPizzaAvail)
				pizza4.setTextColor(Color.parseColor("#08D126"));

			pane1.setTextColor(Color.parseColor("#08D126"));
			pane2.setTextColor(Color.parseColor("#08D126"));
			pane3.setTextColor(Color.parseColor("#08D126"));
			pane4.setTextColor(Color.parseColor("#08D126"));

		}

		else if (isCalled && selected_menu.equals("Ridotto12")) {
			pane1.setTextColor(Color.parseColor("#08D126"));
			pane2.setTextColor(Color.parseColor("#08D126"));

			if (isPrimoAvail) {
				primo1.setTextColor(Color.parseColor("#08D126"));
			}
			if (isSecondoAvail) {
				secondo2.setTextColor(Color.parseColor("#08D126"));
			}
			if (isC1Avail || isC2Avail) {
				contorni1.setTextColor(Color.parseColor("#08D126"));
				contorni2.setTextColor(Color.parseColor("#08D126"));
			}
			if (isDessertAvail) {
				dessert1.setTextColor(Color.parseColor("#08D126"));
				dessert2.setTextColor(Color.parseColor("#08D126"));
			}

			// metti in grigio 3 e 4
			insalatona3.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra3.setTextColor(Color.parseColor("#C4C4C4"));
			pane3.setTextColor(Color.parseColor("#C4C4C4"));
			contorni3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			e3.setTextColor(Color.parseColor("#C4C4C4"));
			e4.setTextColor(Color.parseColor("#C4C4C4"));
			pizza4.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));
			contorni4.setTextColor(Color.parseColor("#C4C4C4"));
			dessert4.setTextColor(Color.parseColor("#C4C4C4"));

		} else if (isCalled && selected_menu.equals("Ridotto1")) {
			pane1.setTextColor(Color.parseColor("#08D126"));
			if (isPrimoAvail) {
				primo1.setTextColor(Color.parseColor("#08D126"));
			}

			if (isC1Avail || isC2Avail) {
				contorni1.setTextColor(Color.parseColor("#08D126"));
			}
			if (isDessertAvail) {
				dessert1.setTextColor(Color.parseColor("#08D126"));
			}

			// metti grigio 2 3 4
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorni2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona3.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra3.setTextColor(Color.parseColor("#C4C4C4"));
			pane3.setTextColor(Color.parseColor("#C4C4C4"));
			contorni3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			e3.setTextColor(Color.parseColor("#C4C4C4"));
			e4.setTextColor(Color.parseColor("#C4C4C4"));
			pizza4.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));
			contorni4.setTextColor(Color.parseColor("#C4C4C4"));
			dessert4.setTextColor(Color.parseColor("#C4C4C4"));

		} else if (isCalled && selected_menu.equals("Ridotto2")) {

			pane2.setTextColor(Color.parseColor("#08D126"));

			if (isSecondoAvail) {
				secondo2.setTextColor(Color.parseColor("#08D126"));
			}
			if (isC1Avail || isC2Avail) {
				contorni2.setTextColor(Color.parseColor("#08D126"));
			}
			if (isDessertAvail) {
				dessert2.setTextColor(Color.parseColor("#08D126"));
			}

			// metti grigio 1 3 4
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorni1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona3.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra3.setTextColor(Color.parseColor("#C4C4C4"));
			pane3.setTextColor(Color.parseColor("#C4C4C4"));
			contorni3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			e3.setTextColor(Color.parseColor("#C4C4C4"));
			e4.setTextColor(Color.parseColor("#C4C4C4"));
			pizza4.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));
			contorni4.setTextColor(Color.parseColor("#C4C4C4"));
			dessert4.setTextColor(Color.parseColor("#C4C4C4"));

		} else if (isCalled && selected_menu.equals("Ridotto3")) {

			if (isC1Avail || isC2Avail) {
				contorni3.setTextColor(Color.parseColor("#08D126"));
			}
			if (isDessertAvail) {
				dessert3.setTextColor(Color.parseColor("#08D126"));
			}
			// panino non l'ho messo
			if (isInsalatonaAvail)
				insalatona3.setTextColor(Color.parseColor("#08D126"));

			pane3.setTextColor(Color.parseColor("#08D126"));

			// metti in grigio tutto il resto 124
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorni1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorni2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			e4.setTextColor(Color.parseColor("#C4C4C4"));
			pizza4.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));
			contorni4.setTextColor(Color.parseColor("#C4C4C4"));
			dessert4.setTextColor(Color.parseColor("#C4C4C4"));

		} else if (isCalled && selected_menu.equals("Ridotto4")) {

			if (isC1Avail || isC2Avail) {
				contorni4.setTextColor(Color.parseColor("#08D126"));
			}
			if (isDessertAvail) {
				dessert4.setTextColor(Color.parseColor("#08D126"));
			}

			if (isPizzaAvail)
				pizza4.setTextColor(Color.parseColor("#08D126"));

			pane4.setTextColor(Color.parseColor("#08D126"));

			// metti in grigio 1 2 3
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorni1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorni2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona3.setTextColor(Color.parseColor("#C4C4C4"));
			due_a_scelta_tra3.setTextColor(Color.parseColor("#C4C4C4"));
			e3.setTextColor(Color.parseColor("#C4C4C4"));
			pane3.setTextColor(Color.parseColor("#C4C4C4"));
			contorni3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));

		}
		// se in realtà sono stato reindirizzato allo snack e poi seleziono il
		// ridotto
		else if (isCalled
				&& (selected_menu.equals("Snack1")
						|| selected_menu.equals("Snack12")
						|| selected_menu.equals("Snack2")
						|| selected_menu.equals("Snack3") || selected_menu
							.equals("Snack4"))) {
			boolean isPrimoSelected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_PRIMO, false);
			boolean isSecondoSelected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_SECONDO, false);
			boolean isC1Selected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_CONTORNO_1, false);
			boolean isC2Selected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_CONTORNO_2, false);
			boolean isDessertSelected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_DESSERT, false);
			boolean isInsalatonaSelected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_INSALATONA, false);
			boolean isPaninoSelected = i.getBooleanExtra(
					Fai_il_tuo_menu.IS_PANINO, false);

			pizza4.setTextColor(Color.parseColor("#08D126"));
			pane1.setTextColor(Color.parseColor("#08D126"));
			pane2.setTextColor(Color.parseColor("#08D126"));
			pane3.setTextColor(Color.parseColor("#08D126"));
			pane4.setTextColor(Color.parseColor("#08D126"));

			if (!isPrimoSelected)
				primo1.setTextColor(Color.parseColor("#08D126"));
			if (!isSecondoSelected)
				secondo2.setTextColor(Color.parseColor("#08D126"));
			if (!isC2Selected || !isC1Selected) {
				contorni1.setTextColor(Color.parseColor("#08D126"));
				contorni2.setTextColor(Color.parseColor("#08D126"));
			}
			if (!isInsalatonaSelected)
				insalatona3.setTextColor(Color.parseColor("#08D126"));

			if (!isDessertSelected) {
				dessert1.setTextColor(Color.parseColor("#08D126"));
				dessert2.setTextColor(Color.parseColor("#08D126"));
			}
			
			if (!isDessertSelected && !isC1Selected && !isC2Selected){
				due_a_scelta_tra3.setTextColor(Color.parseColor("#08D126"));
				due_a_scelta_tra4.setTextColor(Color.parseColor("#08D126"));
				contorni3.setTextColor(Color.parseColor("#08D126"));
				contorni4.setTextColor(Color.parseColor("#08D126"));
				dessert3.setTextColor(Color.parseColor("#08D126"));
				dessert4.setTextColor(Color.parseColor("#08D126"));
			}
			
			if (isDessertSelected && !isC1Selected && !isC2Selected){
				contorni3.setTextColor(Color.parseColor("#08D126"));
				contorni4.setTextColor(Color.parseColor("#08D126"));
			}
			if ((!isDessertSelected && isC1Selected && !isC2Selected)||(!isDessertSelected && !isC1Selected && isC2Selected)){
				dessert3.setTextColor(Color.parseColor("#08D126"));
				dessert4.setTextColor(Color.parseColor("#08D126"));
				contorni3.setTextColor(Color.parseColor("#08D126"));
				contorni4.setTextColor(Color.parseColor("#08D126"));
				
			}

			

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
