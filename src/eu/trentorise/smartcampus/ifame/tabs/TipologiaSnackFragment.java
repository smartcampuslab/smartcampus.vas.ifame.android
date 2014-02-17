package eu.trentorise.smartcampus.ifame.tabs;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.ComponiMenu;
import eu.trentorise.smartcampus.ifame.activity.ComponiMenu.chosenMenu;

/*
 * 
 * fragment che mostra la tipologia snack, si comporta diversamente a seconda che sia chiamato da "fai il tuo menu" rispetto che da "iDeciso"
 */
@SuppressLint("NewApi")
public class TipologiaSnackFragment extends SherlockFragment {

	ViewGroup theContainer;
	TextView primo1;
	TextView contorno1;
	TextView dessert1;
	TextView pane1;
	TextView secondo2;
	TextView contorno2;
	TextView dessert2;
	TextView pane2;
	TextView panino_o_pizza3;
	TextView dessert3;
	TextView acqua3;
	TextView caffe_salse3;
	TextView insalatona4;
	TextView pane4;
	TextView o1;
	TextView o2;
	TextView caffe_salse2;
	TextView caffe_salse1;
	boolean isPrimoAvail;
	boolean isSecondoAvail;
	boolean isC1Avail;
	boolean isC2Avail;
	boolean isDessertAvail;
	boolean isInsalatonaAvail;
	boolean isPizzaAvail;
	boolean isPaninoAvail;

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
		return inflater.inflate(R.layout.layout_tipologia_snack, container,
				false);
	}

	@Override
	public void onResume() {

		Intent i = getSherlockActivity().getIntent();

		boolean isCalled = i.getBooleanExtra(ComponiMenu.HAS_CALLED_TIPOLOGIE,
				false);

		// TextView buyable = (TextView) theContainer
		// .findViewById(R.id.tipologia_snack_buyable);

		/*
		 * 
		 * se � stata chiamata da "componi menu" allora prendiamo l'importo dei
		 * soldi presenti nella tessera, e modifichiamo la textview di
		 * conseguenza
		 */
		if (isCalled) {

			// SharedPreferences pref = getSherlockActivity()
			// .getSharedPreferences(
			// getString(R.string.iFretta_preference_file),
			// Context.MODE_PRIVATE);
			// if (pref.contains(ISoldi.GET_AMOUNT_MONEY)) {
			// float cash = pref.getFloat(ISoldi.GET_AMOUNT_MONEY, 0);
			// if (cash >= 2.90) {
			// buyable.setVisibility(View.VISIBLE);
			// buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_sufficiente)
			// + cash);
			// buyable.setTextColor(Color.parseColor("#08D126"));
			// } else {
			// float difference = 2.90f - cash;
			// buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_insufficiente));
			// buyable.setTextColor(Color.parseColor("#CF323C"));
			//
			// /* +"\nDevi ricaricare almeno: "+difference */
			// }
			//
			// } elses

			// buyable.setVisibility(View.GONE);
		} else {
			// buyable.setVisibility(View.GONE);

			// snack 1

			TextView snack1Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack1_titolo);
			snack1Title.setText(chosenMenu.Snack1.toString());
			snack1Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack1Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack1Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			TextView paninot = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_panino);
			paninot.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_panino)
					+ ",");
			paninot.setTypeface(null, Typeface.BOLD);

			TextView dessert1t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_dessert1);
			dessert1t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
					+ ",");
			dessert1t.setTypeface(null, Typeface.BOLD);

			TextView acqua1t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_acqua1);
			acqua1t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
					+ ",");
			acqua1t.setTypeface(null, Typeface.BOLD);

			TextView main = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_caffe1_salsa21);
			main.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
					+ ",");
			main.setTypeface(null, Typeface.BOLD);

			// snack 2

			TextView snack2Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack2_titolo);
			snack2Title.setText(chosenMenu.Snack2.toString());
			snack2Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack2Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack2Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			TextView maint = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_primo1_pastastation1);
			maint.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_first)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pastastation)
					+ ",");
			maint.setTypeface(null, Typeface.BOLD);

			TextView maint2 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_contorno11_dessert2);
			maint2.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
					+ ",");
			maint2.setTypeface(null, Typeface.BOLD);

			TextView pane21t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_pane21);
			pane21t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pane2)
					+ ",");
			pane21t.setTypeface(null, Typeface.BOLD);

			TextView maint3 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_caffe2_salsa22);
			maint3.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
					+ ",");
			maint3.setTypeface(null, Typeface.BOLD);

			// snack 3

			TextView snack3Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack3_titolo);
			snack3Title.setText(chosenMenu.Snack3.toString());
			snack3Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack3Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack3Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			TextView maint4 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_secondo1_piattofreddo1);
			maint4.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_second)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_piattofreddo)
					+ ",");
			maint4.setTypeface(null, Typeface.BOLD);

			TextView contorno12t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_contorno12);
			contorno12t
					.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
							+ ",");
			contorno12t.setTypeface(null, Typeface.BOLD);

			TextView pane11t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_pane11);
			pane11t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
					+ ",");
			pane11t.setTypeface(null, Typeface.BOLD);

			TextView maint5 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_caffe3_salsa23);
			maint5.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
					+ ",");
			maint5.setTypeface(null, Typeface.BOLD);

			// snack 4
			TextView snack4Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack4_titolo);
			snack4Title.setText(chosenMenu.Snack4.toString());
			snack4Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack4Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack4Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			TextView tranciot = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_tranciopizza1);
			tranciot.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_tranciopizza)
					+ ",");
			tranciot.setTypeface(null, Typeface.BOLD);

			TextView dessertt = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_dessert3);
			dessertt.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
					+ ",");
			dessertt.setTypeface(null, Typeface.BOLD);

			TextView acqua2t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_acqua2);
			acqua2t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
					+ ",");
			acqua2t.setTypeface(null, Typeface.BOLD);

			TextView main6t = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_caffe4_salsa24);
			main6t.setText("- "
					+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
					+ " / "
					+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
					+ ",");
			main6t.setTypeface(null, Typeface.BOLD);

			super.onResume();

			return;
		}

		ArrayList<String> selected_menu = i
				.getStringArrayListExtra(ComponiMenu.MENU_COMPATIBLES);
		HashMap<String, Boolean> mapCheckedItems = (HashMap<String, Boolean>) i
				.getSerializableExtra(ComponiMenu.MENU_CHECKED_TRUE);

		// SNACK 1
		if (selected_menu.contains(chosenMenu.Snack1.toString())) {

			TextView snack1Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack1_titolo);
			snack1Title.setText(chosenMenu.Snack1.toString());
			snack1Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack1Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack1Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(PANINO_TEXT)) {
				TextView panino = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_panino);
				panino.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_panino)
						+ ",");
				panino.setTypeface(null, Typeface.BOLD);
			} else {
				TextView panino = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_panino);
				panino.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_panino)
						+ ",");
				panino.setTypeface(null, Typeface.BOLD);
				panino.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(DESSERT_TEXT)) {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_dessert1);
				dessert.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
			} else {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_dessert1);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(ACQUA_TEXT)) {
				TextView acqua = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_acqua1);
				acqua.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
						+ ",");
				acqua.setTypeface(null, Typeface.BOLD);
			} else {
				TextView acqua = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_acqua1);
				acqua.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
						+ ",");
				acqua.setTypeface(null, Typeface.BOLD);
				acqua.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CAFFE_TEXT)
					|| mapCheckedItems.containsKey(SALSA2_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe1_salsa21);

				if ((mapCheckedItems.containsKey(CAFFE_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(SALSA2_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe1_salsa21);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}
		} else {
			RelativeLayout containerSnack1 = (RelativeLayout) theContainer
					.findViewById(R.id.container_snack1);
			containerSnack1.setVisibility(View.GONE);
		}

		// SNACK 2
		if (selected_menu.contains(chosenMenu.Snack2.toString())) {

			TextView snack2Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack2_titolo);
			snack2Title.setText(chosenMenu.Snack2.toString());
			snack2Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack2Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack2Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(PRIMO_TEXT)
					|| mapCheckedItems.containsKey(PASTA_STATION_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_primo1_pastastation1);

				if ((mapCheckedItems.containsKey(PRIMO_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_first)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(PASTA_STATION_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_pastastation)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_primo1_pastastation1);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_first)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pastastation)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CONTORNO1_TEXT)
					|| mapCheckedItems.containsKey(DESSERT_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_contorno11_dessert2);

				if ((mapCheckedItems.containsKey(CONTORNO1_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(DESSERT_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_contorno11_dessert2);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(PANE2_TEXT)) {
				TextView pane2 = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_pane21);
				pane2.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane2)
						+ ",");
				pane2.setTypeface(null, Typeface.BOLD);
			} else {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_pane21);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane2)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CAFFE_TEXT)
					|| mapCheckedItems.containsKey(SALSA2_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe2_salsa22);

				if ((mapCheckedItems.containsKey(CAFFE_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(SALSA2_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe2_salsa22);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}
		} else {
			RelativeLayout containerSnack2 = (RelativeLayout) theContainer
					.findViewById(R.id.container_snack2);
			containerSnack2.setVisibility(View.GONE);
		}

		// SNACK 3
		if (selected_menu.contains(chosenMenu.Snack3.toString())) {

			TextView snack3Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack3_titolo);
			snack3Title.setText(chosenMenu.Snack3.toString());
			snack3Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack3Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack3Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(SECONDO_TEXT)
					|| mapCheckedItems.containsKey(PIATTO_FREDDO_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_secondo1_piattofreddo1);

				if ((mapCheckedItems.containsKey(SECONDO_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_second)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(PIATTO_FREDDO_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_piattofreddo)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_secondo1_piattofreddo1);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_second)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_piattofreddo)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CONTORNO1_TEXT)) {
				TextView pane2 = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_contorno12);
				pane2.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ ",");
				pane2.setTypeface(null, Typeface.BOLD);
			} else {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_contorno12);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno1)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(PANE1_TEXT)) {
				TextView pane2 = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_pane11);
				pane2.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				pane2.setTypeface(null, Typeface.BOLD);
			} else {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_pane11);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_pane1)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CAFFE_TEXT)
					|| mapCheckedItems.containsKey(SALSA2_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe3_salsa23);

				if ((mapCheckedItems.containsKey(CAFFE_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(SALSA2_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe3_salsa23);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}
		} else {
			RelativeLayout containerSnack3 = (RelativeLayout) theContainer
					.findViewById(R.id.container_snack3);
			containerSnack3.setVisibility(View.GONE);
		}

		// SNACK 4
		if (selected_menu.contains(chosenMenu.Snack4.toString())) {

			TextView snack4Title = (TextView) theContainer
					.findViewById(R.id.tipologia_snack4_titolo);
			snack4Title.setText(chosenMenu.Snack4.toString());
			snack4Title.setVisibility(View.VISIBLE);
			// if (android.os.Build.VERSION.SDK_INT >= 16)
			// snack4Title.setBackground(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));
			// else
			// snack4Title.setBackgroundDrawable(theContainer.getResources()
			// .getDrawable(R.drawable.shape_title_componimenu));

			if (mapCheckedItems.containsKey(TRANCIO_PIZZA_TEXT)) {
				TextView trancio = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_tranciopizza1);
				trancio.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_tranciopizza)
						+ ",");
				trancio.setTypeface(null, Typeface.BOLD);
			} else {
				TextView trancio = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_tranciopizza1);
				trancio.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_tranciopizza)
						+ ",");
				trancio.setTypeface(null, Typeface.BOLD);
				trancio.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(DESSERT_TEXT)) {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_dessert3);
				dessert.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
			} else {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_dessert3);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_dessert)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(ACQUA_TEXT)) {
				TextView pane2 = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_acqua2);
				pane2.setText("- "
						+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
						+ ",");
				pane2.setTypeface(null, Typeface.BOLD);
			} else {
				TextView dessert = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_acqua2);
				dessert.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_acqua)
						+ ",");
				dessert.setTypeface(null, Typeface.BOLD);
				dessert.setTextColor(Color.parseColor("#08D126"));
			}

			if (mapCheckedItems.containsKey(CAFFE_TEXT)
					|| mapCheckedItems.containsKey(SALSA2_TEXT)) {

				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe4_salsa24);

				if ((mapCheckedItems.containsKey(CAFFE_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				} else if ((mapCheckedItems.containsKey(SALSA2_TEXT))) {
					main.setText("- "
							+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
							+ ",");
					main.setTypeface(null, Typeface.BOLD);
				}

			} else {
				TextView main = (TextView) theContainer
						.findViewById(R.id.tipologia_snack_caffe4_salsa24);
				main.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_caffe)
						+ " / "
						+ getString(R.string.iDeciso_compose_menu_checkbox_salsa2)
						+ ",");
				main.setTypeface(null, Typeface.BOLD);
				main.setTextColor(Color.parseColor("#08D126"));
			}
		} else {
			RelativeLayout containerSnack4 = (RelativeLayout) theContainer
					.findViewById(R.id.container_snack4);
			containerSnack4.setVisibility(View.GONE);
		}

		super.onResume();

	}
}
