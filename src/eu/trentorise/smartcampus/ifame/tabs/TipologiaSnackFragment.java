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

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.Fai_il_tuo_menu;
import eu.trentorise.smartcampus.ifame.activity.ISoldi;

/*
 * 
 * fragment che mostra la tipologia snack, si comporta diversamente a seconda che sia chiamato da "fai il tuo menu" rispetto che da "iDeciso"
 */
public class TipologiaSnackFragment extends SherlockFragment {

	ViewGroup theContainer;
	Intent i;
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

		i = getSherlockActivity().getIntent();
		boolean isCalled = i.getBooleanExtra(
				Fai_il_tuo_menu.HAS_CALLED_TIPOLOGIE, false);
		TextView buyable = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_buyable);

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
				if (cash >= 2.90) {
					buyable.setVisibility(View.VISIBLE);
					buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_sufficiente)
							+ cash);
					buyable.setTextColor(Color.parseColor("#08D126"));
				} else {
					float difference = 2.90f - cash;
					buyable.setText(getString(R.string.iDeciso_tipologie_menu_credito_insufficiente));
					buyable.setTextColor(Color.parseColor("#CF323C"));

					/* +"\nDevi ricaricare almeno: "+difference */
				}

			}
		} else
			buyable.setVisibility(View.GONE);

		String selected_menu = i.getStringExtra(Fai_il_tuo_menu.SELECTED_MENU);

		setEverythingUp();

		/*
		 * 
		 * se arriviamo da componi, allora in base al menu corrispondente alla
		 * scelta dell'utente, e hai piatti ancora acquistabili che recuperiamo
		 * dall'intent, coloriamo di conseguenza le scritte (verdi le
		 * acquistabili, nere le selezionabili, grigio le altre
		 */
		if (isCalled) {

			if (selected_menu.equals("Snack12")) {

				coloraVerde(1);
				coloraVerde(2);

				coloraGrigio(3);
				coloraGrigio(4);

			}

			else if (selected_menu.equals("Snack1")) {

				coloraVerde(1);

				coloraGrigio(2);
				coloraGrigio(3);
				coloraGrigio(4);

			}

			else if (selected_menu.equals("Snack2")) {

				coloraVerde(2);

				coloraGrigio(1);
				coloraGrigio(3);
				coloraGrigio(4);
			}

			else if (selected_menu.equals("Snack3")) {

				coloraVerde(3);

				coloraGrigio(2);
				coloraGrigio(1);
				coloraGrigio(4);

			}

			else if (selected_menu.equals("Snack4")) {
				coloraVerde(4);

				coloraGrigio(2);
				coloraGrigio(3);
				coloraGrigio(1);
			} else if ((selected_menu.equals("Intero")
					|| selected_menu.equals("Ridotto1234")
					|| selected_menu.equals("Ridotto12")
					|| selected_menu.equals("Ridotto1")
					|| selected_menu.equals("Ridotto2")
					|| selected_menu.equals("Ridotto3") || selected_menu
						.equals("Ridotto4"))) {
				coloraGrigio(1);
				coloraGrigio(2);
				coloraGrigio(3);
				coloraGrigio(4);
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

	private void coloraVerde(int menuNumber) {

		if (menuNumber == 1) {
			pane1.setTextColor(Color.parseColor("#08D126"));

			if (isPrimoAvail)
				primo1.setTextColor(Color.parseColor("#08D126"));

			if (isC1Avail || isC2Avail)
				contorno1.setTextColor(Color.parseColor("#08D126"));

			if (isDessertAvail)
				dessert1.setTextColor(Color.parseColor("#08D126"));

			caffe_salse1.setTextColor(Color.parseColor("#08D126"));

		} else if (menuNumber == 2) {
			pane2.setTextColor(Color.parseColor("#08D126"));
			if (isSecondoAvail)
				secondo2.setTextColor(Color.parseColor("#08D126"));
			if (isC1Avail || isC2Avail)
				contorno2.setTextColor(Color.parseColor("#08D126"));
			if (isDessertAvail)
				dessert2.setTextColor(Color.parseColor("#08D126"));
			caffe_salse2.setTextColor(Color.parseColor("#08D126"));
		} else if (menuNumber == 3) {
			dessert3.setTextColor(Color.parseColor("#08D126"));
			caffe_salse3.setTextColor(Color.parseColor("#08D126"));
			acqua3.setTextColor(Color.parseColor("#08D126"));
		} else if (menuNumber == 4) {
			pane4.setTextColor(Color.parseColor("#08D126"));
		}

	}

	private void coloraGrigio(int menuNumber) {

		if (menuNumber == 1) {
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorno1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			o1.setTextColor(Color.parseColor("#C4C4C4"));
			caffe_salse1.setTextColor(Color.parseColor("#C4C4C4"));
		} else if (menuNumber == 2) {
			o2.setTextColor(Color.parseColor("#C4C4C4"));
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorno2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			caffe_salse2.setTextColor(Color.parseColor("#C4C4C4"));
		} else if (menuNumber == 3) {
			panino_o_pizza3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			acqua3.setTextColor(Color.parseColor("#C4C4C4"));
			caffe_salse3.setTextColor(Color.parseColor("#C4C4C4"));
		} else if (menuNumber == 4) {
			insalatona4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));
		}
	}

	/*
	 * 
	 * instanzio tutte le textviews (il numero � relativo al menu
	 * corrispondente)
	 */
	public void setEverythingUp() {

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

		// menu snack 1
		primo1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_primo1);
		contorno1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_contorno1);
		dessert1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_dessert1);
		o1 = (TextView) theContainer.findViewById(R.id.tipologia_snack_o1);
		pane1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_pane1);
		caffe_salse1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_caffe_o_salse1);
		primo1.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_first)
				+ ", ");
		primo1.setTypeface(null, Typeface.BOLD);
		contorno1
				.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo)
						+ " ");
		dessert1.setText(" "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane1.setText("+ " + getString(R.string.iDeciso_pane));
		caffe_salse1
				.setText("+ " + getString(R.string.iDeciso_caffe_o_2_salse));
		// menu snack 2
		secondo2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_secondo2);
		caffe_salse2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_caffe_o_salse2);
		contorno2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_contorno2);
		dessert2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_dessert2);
		pane2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_pane2);

		secondo2.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_second));
		secondo2.setTypeface(null, Typeface.BOLD);
		contorno2
				.setText("+ "
						+ getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo)
						+ " ");
		dessert2.setText(" "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane2.setText("+ " + getString(R.string.iDeciso_pane));
		o2 = (TextView) theContainer.findViewById(R.id.tipologia_snack_o2);
		caffe_salse2
				.setText("+ " + getString(R.string.iDeciso_caffe_o_2_salse));
		// menu snack 3
		panino_o_pizza3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_panino_o_pizza3);
		dessert3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_dessert3);
		acqua3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_acqua3);
		caffe_salse3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_caffe_o_salse3);
		panino_o_pizza3.setText("- " + getString(R.string.iDeciso_panino)
				+ " o " + getString(R.string.iDeciso_pizza_trancio));
		panino_o_pizza3.setTypeface(null, Typeface.BOLD);
		dessert3.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		acqua3.setText("+ " + getString(R.string.iDeciso_acqua));
		caffe_salse3
				.setText("+ " + getString(R.string.iDeciso_caffe_o_2_salse));
		// menu snack 4
		insalatona4 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_insalatona4);
		pane4 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_pane4);

		insalatona4.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona));
		insalatona4.setTypeface(null, Typeface.BOLD);
		pane4.setText("+ " + getString(R.string.iDeciso_pane));

	}
}
