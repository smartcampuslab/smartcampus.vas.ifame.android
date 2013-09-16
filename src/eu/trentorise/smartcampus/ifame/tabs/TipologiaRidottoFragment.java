package eu.trentorise.smartcampus.ifame.tabs;

//errore: se seleziono solo dessert non mi fa vedere paninomenu
//TODO: cambiare dinamicamente il "2 a scelta" per trasformarlo in "1 a scelta" quando necessario?

/*
 * 
 * fragment per la tipologia di menu ridotto
 */

//TODO: selezionando 2 contorni colora di verde il dessert, sbagliato 

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

public class TipologiaRidottoFragment extends SherlockFragment {

	ViewGroup theContainer;

	Intent i;
	TextView primo1;
	TextView contornoA1;
	TextView contornoB1;
	TextView dessert1;
	TextView pane1;
	TextView secondo2;
	TextView contornoA2;
	TextView dessert2;
	TextView pane2;
	TextView insalatona3;
	TextView pane3;
	TextView contornoA3;
	TextView dessert3;
	TextView pizza4;
	TextView contorno_o_dessert4;
	TextView caffe_o_acqua4;

	boolean isPrimoAvail;
	boolean isSecondoAvail;
	boolean isC1Avail;
	boolean isC2Avail;
	boolean isDessertAvail;
	boolean isInsalatonaAvail;
	boolean isPizzaAvail;
	boolean isPrimoSelected;
	boolean isSecondoSelected;
	boolean isC1Selected;
	boolean isC2Selected;
	boolean isDessertSelected;
	boolean isInsalatonaSelected;
	boolean isPaninoSelected;
	boolean isPizzaSelected;

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

		/*
		 * 
		 * se � stata chiamata da "componi menu" allora prendiamo l'importo dei
		 * soldi presenti nella tessera, e modifichiamo la textview di
		 * conseguenza
		 */

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

		setEverythingUp();

		if (isCalled) {

			if (selected_menu.equals("Ridotto1234")) {

				coloraVerde(1);
				coloraVerde(2);
				coloraVerde(3);
				coloraVerde(4);

			}

			else if (selected_menu.equals("Ridotto12")) {
				coloraVerde(1);
				coloraVerde(2);
				// metti in grigio 3 e 4
				coloraGrigio(3);
				coloraGrigio(4);

			} else if (selected_menu.equals("Ridotto1")) {
				coloraVerde(1);
				// metti grigio 2 3 4
				coloraGrigio(2);
				coloraGrigio(3);
				coloraGrigio(4);

			} else if (selected_menu.equals("Ridotto2")) {

				coloraVerde(2);

				// metti grigio 1 3 4
				coloraGrigio(1);
				coloraGrigio(3);
				coloraGrigio(4);

			} else if (selected_menu.equals("Ridotto3")) {

				coloraVerde(3);

				// metti in grigio tutto il resto 124
				coloraGrigio(1);
				coloraGrigio(2);
				coloraGrigio(4);

			} else if (selected_menu.equals("Ridotto4")) {

				coloraVerde(4);

				// metti in grigio 1 2 3
				coloraGrigio(1);
				coloraGrigio(2);
				coloraGrigio(3);

			}

			/*
			 * se sono stato reindirizzato allo snack e poi seleziono il
			 * ridotto, allora i controlli sulla disponibilit� non vanno
			 * affrontati, perch� arrivando dallo snack sono sicuro che non
			 * siano stati selezionati
			 */
			else if ((selected_menu.equals("Snack1")
					|| selected_menu.equals("Snack12")
					|| selected_menu.equals("Snack2")
					|| selected_menu.equals("Snack3") || selected_menu
						.equals("Snack4"))) {

				// se aveva scelto lo snack con insalatona
				if (isInsalatonaSelected) {
					contornoA3.setTextColor(Color.parseColor("#08D126"));
					pane3.setTextColor(Color.parseColor("#08D126"));
					dessert3.setTextColor(Color.parseColor("#08D126"));
					// grigio gli altri
					coloraGrigio(1);
					coloraGrigio(2);
					coloraGrigio(4);
				} else if (isSecondoSelected) {
					if (!isDessertSelected)
						dessert2.setTextColor(Color.parseColor("#08D126"));
					if (!isC1Selected && !isC2Selected)
						contornoA2.setTextColor(Color.parseColor("#08D126"));
					pane2.setTextColor(Color.parseColor("#08D126"));

					// grigio gli altri
					coloraGrigio(1);
					coloraGrigio(3);
					coloraGrigio(4);
				} else if (isPrimoSelected) {
					if (!isDessertSelected)
						dessert1.setTextColor(Color.parseColor("#08D126"));
					if (!isC1Selected)
						contornoA1.setTextColor(Color.parseColor("#08D126"));
					if (!isC2Selected)
						contornoB1.setTextColor(Color.parseColor("#08D126"));
					pane1.setTextColor(Color.parseColor("#08D126"));
					// grigio gli altri
					coloraGrigio(3);
					coloraGrigio(2);
					coloraGrigio(4);

				} else if (isPaninoSelected) {
					coloraGrigio(1);
					coloraGrigio(2);
					coloraGrigio(3);
					coloraGrigio(4);
				}

				else if ((isC1Selected || isC2Selected || isDessertSelected)
						&& !isPrimoSelected && !isSecondoSelected
						&& !isInsalatonaSelected && !isPaninoSelected
						&& !isPizzaSelected) {
					primo1.setTextColor(Color.parseColor("#08D126"));
					if (!isC1Selected)
						contornoA1.setTextColor(Color.parseColor("#08D126"));
					if (!isC2Selected)
						contornoB1.setTextColor(Color.parseColor("#08D126"));

					if (!isDessertSelected) {
						dessert1.setTextColor(Color.parseColor("#08D126"));
						dessert2.setTextColor(Color.parseColor("#08D126"));
						dessert3.setTextColor(Color.parseColor("#08D126"));

					}
					pane1.setTextColor(Color.parseColor("#08D126"));
					secondo2.setTextColor(Color.parseColor("#08D126"));
					if (!isC1Selected && !isC2Selected)
						contornoA2.setTextColor(Color.parseColor("#08D126"));
					pane2.setTextColor(Color.parseColor("#08D126"));
					insalatona3.setTextColor(Color.parseColor("#08D126"));
					contornoA3.setTextColor(Color.parseColor("#08D126"));
					pane3.setTextColor(Color.parseColor("#08D126"));

				}
			}

		}

		/*
		 * se sono stato indirizzato all'intero, ma poi clicco su ridotto,
		 * allora metto tutto in grigio (primo + secondo = intero e nessuno dei
		 * ridotti li comprende)
		 */
		if (isCalled && selected_menu.equals("Intero")) {
			coloraGrigio(1);
			coloraGrigio(2);
			coloraGrigio(3);
			coloraGrigio(4);
		}

		super.onResume();

	}

	private void coloraGrigio(int menuNumber) {
		if (menuNumber == 1) {
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contornoA1.setTextColor(Color.parseColor("#C4C4C4"));
			contornoB1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
		} else if (menuNumber == 2) {
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contornoA2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
		} else if (menuNumber == 3) {
			insalatona3.setTextColor(Color.parseColor("#C4C4C4"));
			contornoA3.setTextColor(Color.parseColor("#C4C4C4"));
			pane3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
		} else if (menuNumber == 4) {
			pizza4.setTextColor(Color.parseColor("#C4C4C4"));
			contorno_o_dessert4.setTextColor(Color.parseColor("#C4C4C4"));
			caffe_o_acqua4.setTextColor(Color.parseColor("#C4C4C4"));
		}

	}

	private void coloraVerde(int menuNumber) {
		if (menuNumber == 1) {
			pane1.setTextColor(Color.parseColor("#08D126"));
			if (isPrimoAvail) {
				primo1.setTextColor(Color.parseColor("#08D126"));
			}
			if (!isC1Selected)
				contornoA1.setTextColor(Color.parseColor("#08D126"));
			if (!isC2Selected)
				contornoB1.setTextColor(Color.parseColor("#08D126"));

			if (!isDessertSelected) {
				dessert1.setTextColor(Color.parseColor("#08D126"));
			}

		} else if (menuNumber == 2) {
			pane2.setTextColor(Color.parseColor("#08D126"));

			if (isSecondoAvail) {
				secondo2.setTextColor(Color.parseColor("#08D126"));
			}

			if (!isC1Selected && !isC2Selected) {
				contornoA2.setTextColor(Color.parseColor("#08D126"));
			}
			if (!isDessertSelected) {
				dessert2.setTextColor(Color.parseColor("#08D126"));

			}
		} else if (menuNumber == 3) {

			if (!isC1Selected && !isC2Selected)
				contornoA3.setTextColor(Color.parseColor("#08D126"));
			if (!isDessertSelected)
				dessert3.setTextColor(Color.parseColor("#08D126"));

			if (!isInsalatonaSelected)
				insalatona3.setTextColor(Color.parseColor("#08D126"));

			pane3.setTextColor(Color.parseColor("#08D126"));
		} else if (menuNumber == 4) {
			contorno_o_dessert4.setTextColor(Color.parseColor("#08D126"));
			caffe_o_acqua4.setTextColor(Color.parseColor("#08D126"));

		}
	}

	// istanzia gli oggetti necessari dichiarati globalmente

	private void setEverythingUp() {
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

		isPrimoSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_PRIMO, false);
		isSecondoSelected = i
				.getBooleanExtra(Fai_il_tuo_menu.IS_SECONDO, false);
		isC1Selected = i.getBooleanExtra(Fai_il_tuo_menu.IS_CONTORNO_1, false);
		isC2Selected = i.getBooleanExtra(Fai_il_tuo_menu.IS_CONTORNO_2, false);
		isDessertSelected = i
				.getBooleanExtra(Fai_il_tuo_menu.IS_DESSERT, false);
		isInsalatonaSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_INSALATONA,
				false);
		isPaninoSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_PANINO, false);
		isPizzaSelected = i.getBooleanExtra(Fai_il_tuo_menu.IS_PIZZA, false);

		// menu ridotto 1
		primo1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_primo1);
		contornoA1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorno_a1);
		contornoB1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorno_b1);
		dessert1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert1);
		pane1 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane1);

		primo1.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_first) + ",");
		primo1.setTypeface(null, Typeface.BOLD);
		contornoA1
				.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo));
		contornoB1
				.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno_freddo));
		dessert1.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane1.setText("+ " + getString(R.string.iDeciso_pane));

		// menu ridotto2
		secondo2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_secondo2);
		contornoA2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorno_a2);
		dessert2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert2);
		pane2 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane2);

		secondo2.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_second)
				+ ",");
		secondo2.setTypeface(null, Typeface.BOLD);
		contornoA2
				.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo));
		dessert2.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane2.setText("+ " + getString(R.string.iDeciso_pane));

		// menu ridotto3 pasta station cambia dal primo??????????? sul sito
		// sembra di si....per ora non ne tengo conto perch� credo sia sbagliato
		insalatona3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_insalatona3);
		pane3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pane3);
		contornoA3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorno_a3);
		dessert3 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_dessert3);

		insalatona3.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona)
				+ ", ");
		insalatona3.setTypeface(null, Typeface.BOLD);
		pane3.setText("+ " + getString(R.string.iDeciso_pane));
		contornoA3
				.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo));
		dessert3.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));

		// menu ridotto4
		pizza4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_pizza4);
		contorno_o_dessert4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_contorno_o_dessert4);
		caffe_o_acqua4 = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_caffe_o_acqua4);

		pizza4.setText("- " + getString(R.string.iDeciso_pizza) + ", ");
		pizza4.setTypeface(null, Typeface.BOLD);
		contorno_o_dessert4.setText("+ "
				+ getString(R.string.iDeciso_contorno_o_dessert));
		caffe_o_acqua4
				.setText("+ " + getString(R.string.iDeciso_acqua_o_caffe));
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
