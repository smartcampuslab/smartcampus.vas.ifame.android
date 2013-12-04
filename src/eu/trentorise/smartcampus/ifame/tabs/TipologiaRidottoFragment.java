package eu.trentorise.smartcampus.ifame.tabs;

//errore: se seleziono solo dessert non mi fa vedere paninomenu
//TODO: cambiare dinamicamente il "2 a scelta" per trasformarlo in "1 a scelta" quando necessario?

/*
 * 
 * fragment per la tipologia di menu ridotto
 */

//TODO: selezionando 2 contorni colora di verde il dessert, sbagliato 

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Intent;
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
import eu.trentorise.smartcampus.ifame.activity.Tipologie_menu_fr;
import eu.trentorise.smartcampus.ifame.activity.ComponiMenu.chosenMenu;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
		return inflater.inflate(R.layout.layout_tipologia_ridotto, container,
				false);
	}

	@Override
	public void onResume() {

		i = getSherlockActivity().getIntent();

		boolean isCalled = i.getBooleanExtra(
				ComponiMenu.HAS_CALLED_TIPOLOGIE, false);

		TextView buyable = (TextView) theContainer
				.findViewById(R.id.tipologia_ridotto_buyable);

		/*
		 * 
		 * se � stata chiamata da "componi menu" allora prendiamo l'importo dei
		 * soldi presenti nella tessera, e modifichiamo la textview di
		 * conseguenza
		 */

		if (isCalled) {

			// ****************************************
			// NON CI SONO PIU DA UN PO STE PREFERENCES
			// ****************************************
			// SharedPreferences pref = getSherlockActivity()
			// .getSharedPreferences(
			// getString(R.string.iFretta_preference_file),
			// Context.MODE_PRIVATE);
			// if (pref.contains(ISoldi.GET_AMOUNT_MONEY)) {
			// float cash = pref.getFloat(ISoldi.GET_AMOUNT_MONEY, 0);
			// if (cash >= 4.20) {
			// buyable.setVisibility(View.VISIBLE);
			// buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_sufficiente)
			// + cash);
			// buyable.setTextColor(Color.parseColor("#08D126"));
			// } else {
			// float difference = 4.20f - cash;
			// buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_insufficiente));
			// buyable.setTextColor(Color.parseColor("#CF323C"));
			// }
			//
			// } else

			buyable.setVisibility(View.GONE);
		} else {
			buyable.setVisibility(View.GONE);

			// ridotto1
			TextView ridotto1Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto1_titolo);
			ridotto1Title.setText(chosenMenu.Ridotto1.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto1Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto1Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			TextView primot = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_primo1);
			primot.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_first)
					+ ",");
			primot.setTypeface(null, Typeface.BOLD);

			TextView contornot = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_contorno21);
			contornot
					.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_contorno2)
							+ ",");
			contornot.setTypeface(null, Typeface.BOLD);

			TextView dessertt = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_dessert1);
			dessertt.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
					+ ",");
			dessertt.setTypeface(null, Typeface.BOLD);

			TextView primo = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_pane11);
			primo.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
					+ ",");
			primo.setTypeface(null, Typeface.BOLD);

			// ridotto 2
			TextView ridotto2Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto2_titolo);
			ridotto2Title.setText(chosenMenu.Ridotto2.toString());

			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto2Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto2Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			TextView secondot = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_secondo2);
			secondot.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_second)
					+ ",");
			secondot.setTypeface(null, Typeface.BOLD);

			TextView contorno12t = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_contorno12);
			contorno12t
					.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
							+ ",");
			contorno12t.setTypeface(null, Typeface.BOLD);

			TextView dessert2t = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_dessert2);
			dessert2t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
					+ ",");
			dessert2t.setTypeface(null, Typeface.BOLD);

			TextView pane1t = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_pane12);
			pane1t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
					+ ",");
			pane1t.setTypeface(null, Typeface.BOLD);

			// ridotto 3
			TextView ridotto3Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto3_titolo);
			ridotto3Title.setText(chosenMenu.Ridotto3.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto3Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto3Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			TextView main = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_piattofreddo_pastastation_insalatona3);

			main.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_piattofreddo)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pastastation)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona)
					+ ",");
			main.setTypeface(null, Typeface.BOLD);

			TextView contorno13t = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_contorno13);
			contorno13t
					.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
							+ ",");
			contorno13t.setTypeface(null, Typeface.BOLD);

			TextView dessert3t = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_dessert3);
			dessert3t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
					+ ",");
			dessert3t.setTypeface(null, Typeface.BOLD);

			TextView pane13t = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_pane13);
			pane13t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
					+ ",");
			pane13t.setTypeface(null, Typeface.BOLD);

			// ridotto 4

			TextView ridotto4Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto4_titolo);
			ridotto4Title.setText(chosenMenu.Ridotto4.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto4Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto4Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			TextView pizzat = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_pizza4);
			pizzat.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pizza)
					+ ",");
			pizzat.setTypeface(null, Typeface.BOLD);

			TextView contorno_dessert = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_contorno14_dessert4);
			contorno_dessert
					.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
							+ " / "
							+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
							+ ",");
			contorno_dessert.setTypeface(null, Typeface.BOLD);

			TextView caffe_acqua = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto_caffe4_acqua4);
			caffe_acqua.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
					+ ",");
			caffe_acqua.setTypeface(null, Typeface.BOLD);

			super.onResume();

			return;

		}

		ArrayList<String> selected_menu = i
				.getStringArrayListExtra(ComponiMenu.MENU_COMPATIBLES);
		HashMap<String, Boolean> mapCheckedItems = (HashMap<String, Boolean>) i
				.getSerializableExtra(ComponiMenu.MENU_CHECKED_TRUE);

		// RIDOTTO 1
		if (selected_menu.contains(chosenMenu.Ridotto1.toString())) {

			TextView ridotto1Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto1_titolo);
			ridotto1Title.setText(chosenMenu.Ridotto1.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto1Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto1Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(PRIMO_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_primo1);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_first)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_primo1);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_first)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CONTORNO2_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno21);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno2)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno21);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno2)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(DESSERT_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_dessert1);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_dessert1);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(PANE1_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pane11);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pane11);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

		} else {
			RelativeLayout containerRidotto = (RelativeLayout) theContainer
					.findViewById(R.id.container_ridotto1);
			containerRidotto.setVisibility(View.GONE);
		}

		// fine ridotto 1

		// RIDOTTO 2
		if (selected_menu.contains(chosenMenu.Ridotto2.toString())) {

			TextView ridotto2Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto2_titolo);
			ridotto2Title.setText(chosenMenu.Ridotto2.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto2Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto2Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(SECONDO_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_secondo2);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_second)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_secondo2);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_second)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CONTORNO1_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno12);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno12);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(DESSERT_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_dessert2);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_dessert2);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(PANE1_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pane12);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pane12);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

		} else {
			RelativeLayout containerRidotto = (RelativeLayout) theContainer
					.findViewById(R.id.container_ridotto2);
			containerRidotto.setVisibility(View.GONE);
		}
		// fine ridotto 2

		// RIDOTTO 3
		if (selected_menu.contains(chosenMenu.Ridotto3.toString())) {

			TextView ridotto3Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto3_titolo);
			ridotto3Title.setText(chosenMenu.Ridotto3.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto3Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto3Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(PIATTO_FREDDO_TEXT)
					|| mapCheckedItems.containsKey(PASTA_STATION_TEXT)
					|| mapCheckedItems.containsKey(INSALATONA_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_piattofreddo_pastastation_insalatona3);

				if ((mapCheckedItems.containsKey(PIATTO_FREDDO_TEXT))) {

					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_piattofreddo)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(PASTA_STATION_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_pastastation)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(INSALATONA_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_piattofreddo_pastastation_insalatona3);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_piattofreddo)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pastastation)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CONTORNO1_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno13);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno13);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(DESSERT_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_dessert3);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_dessert3);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(PANE1_TEXT)) {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pane13);
				primo.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
			} else {
				TextView primo = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pane13);
				primo.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				primo.setTypeface(null, Typeface.BOLD);
				primo.setTextColor(Color.parseColor("#08D126"));
			}

		} else {
			RelativeLayout containerRidotto = (RelativeLayout) theContainer
					.findViewById(R.id.container_ridotto3);
			containerRidotto.setVisibility(View.GONE);
		}
		// fine ridotto 3

		// RIDOTTO 4
		if (selected_menu.contains(chosenMenu.Ridotto4.toString())) {

			TextView ridotto4Title = (TextView) theContainer
					.findViewById(R.id.tipologia_ridotto4_titolo);
			ridotto4Title.setText(chosenMenu.Ridotto4.toString());
			if (android.os.Build.VERSION.SDK_INT >= 16)
				ridotto4Title.setBackground(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));
			else
				ridotto4Title.setBackgroundDrawable(theContainer.getResources()
						.getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(PIZZA_TEXT)) {
				TextView pizza = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pizza4);
				pizza.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pizza)
						+ ",");
				pizza.setTypeface(null, Typeface.BOLD);
			} else {
				TextView pizza = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_pizza4);
				pizza.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pizza)
						+ ",");
				pizza.setTypeface(null, Typeface.BOLD);
				pizza.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CONTORNO1_TEXT)
					|| mapCheckedItems.containsKey(DESSERT_TEXT)) {

				TextView contorno_dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno14_dessert4);

				if ((mapCheckedItems.containsKey(CONTORNO1_TEXT))) {

					contorno_dessert
							.setText("- "
									+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
									+ ",");
					contorno_dessert.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(DESSERT_TEXT))) {
					contorno_dessert
							.setText("- "
									+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
									+ ",");
					contorno_dessert.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView contorno_dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_contorno14_dessert4);
				contorno_dessert
						.setText("+ "
								+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
								+ " / "
								+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
								+ ",");
				contorno_dessert.setTypeface(null, Typeface.BOLD);
				contorno_dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CAFFE_TEXT)
					|| mapCheckedItems.containsKey(ACQUA_TEXT)) {

				TextView caffe_acqua = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_caffe4_acqua4);

				if ((mapCheckedItems.containsKey(CAFFE_TEXT))) {
					caffe_acqua
							.setText("- "
									+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
									+ ",");
					caffe_acqua.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(ACQUA_TEXT))) {
					caffe_acqua
							.setText("- "
									+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
									+ ",");
					caffe_acqua.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView caffe_acqua = (TextView) theContainer
						.findViewById(R.id.tipologia_ridotto_caffe4_acqua4);
				caffe_acqua
						.setText("+ "
								+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
								+ " / "
								+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
								+ ",");
				caffe_acqua.setTypeface(null, Typeface.BOLD);
				caffe_acqua.setTextColor(Color.parseColor("#08D126"));
			}

		} else {
			RelativeLayout containerRidotto = (RelativeLayout) theContainer
					.findViewById(R.id.container_ridotto4);
			containerRidotto.setVisibility(View.GONE);
		}
		// fine ridotto 4

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
