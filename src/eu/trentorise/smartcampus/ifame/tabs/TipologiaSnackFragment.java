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

public class TipologiaSnackFragment extends SherlockFragment {

	ViewGroup theContainer;
	Intent i;

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
		TextView primo1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_primo1);
		TextView contorno1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_contorno1);
		TextView dessert1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_dessert1);

		TextView pane1 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_pane1);

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

		// menu snack 2
		TextView secondo2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_secondo2);
		TextView contorno2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_contorno2);
		TextView dessert2 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_dessert2);
		TextView pane2 = (TextView) theContainer
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

		// menu snack 3
		TextView panino_o_pizza3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_panino_o_pizza3);
		TextView dessert3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_dessert3);
		TextView acqua_o_caffe3 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_acqua_o_caffe3);

		panino_o_pizza3.setText("- " + getString(R.string.iDeciso_panino)
				+ " o " + getString(R.string.iDeciso_pizza_trancio));
		panino_o_pizza3.setTypeface(null, Typeface.BOLD);
		dessert3.setText("+ "
				+ getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		acqua_o_caffe3
				.setText("+ " + getString(R.string.iDeciso_acqua_o_caffe));

		// menu snack 4
		TextView insalatona4 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_insalatona4);
		TextView pane4 = (TextView) theContainer
				.findViewById(R.id.tipologia_snack_pane4);

		insalatona4.setText("- "
				+ getString(R.string.iDeciso_compose_menu_checkbox_insalatona));
		insalatona4.setTypeface(null, Typeface.BOLD);
		pane4.setText("+ " + getString(R.string.iDeciso_pane));

		if (isCalled && selected_menu.equals("Snack12")) {

			pane1.setTextColor(Color.parseColor("#08D126"));
			pane2.setTextColor(Color.parseColor("#08D126"));

			if (isPrimoAvail)
				primo1.setTextColor(Color.parseColor("#08D126"));
			if (isSecondoAvail)
				secondo2.setTextColor(Color.parseColor("#08D126"));

			if (isC1Avail || isC2Avail) {
				contorno1.setTextColor(Color.parseColor("#08D126"));
				contorno2.setTextColor(Color.parseColor("#08D126"));
			}
			if (isDessertAvail) {
				dessert1.setTextColor(Color.parseColor("#08D126"));
				dessert2.setTextColor(Color.parseColor("#08D126"));

			}

			// metto in grigio 3 e 4
			panino_o_pizza3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			acqua_o_caffe3.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));

		}

		else if (isCalled && selected_menu.equals("Snack1")) {

			pane1.setTextColor(Color.parseColor("#08D126"));

			if (isPrimoAvail)
				primo1.setTextColor(Color.parseColor("#08D126"));

			if (isC1Avail || isC2Avail)
				contorno1.setTextColor(Color.parseColor("#08D126"));

			if (isDessertAvail)
				dessert1.setTextColor(Color.parseColor("#08D126"));

			// metto in grigio 2 3 4
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorno2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));

			TextView o2 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o2);
			o2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			panino_o_pizza3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			acqua_o_caffe3.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));

		}

		else if (isCalled && selected_menu.equals("Snack2")) {

			pane2.setTextColor(Color.parseColor("#08D126"));
			if (isSecondoAvail)
				secondo2.setTextColor(Color.parseColor("#08D126"));
			if (isC1Avail || isC2Avail)
				contorno2.setTextColor(Color.parseColor("#08D126"));
			if (isDessertAvail)
				dessert2.setTextColor(Color.parseColor("#08D126"));

			// grigio 1 3 4
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorno1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o1 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o1);
			o1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			panino_o_pizza3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			acqua_o_caffe3.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));
		}

		else if (isCalled && selected_menu.equals("Snack3")) {

			dessert3.setTextColor(Color.parseColor("#08D126"));
			acqua_o_caffe3.setTextColor(Color.parseColor("#08D126"));

			// grigio 1 2 4
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorno1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o1 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o1);
			o1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o2 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o2);
			o2.setTextColor(Color.parseColor("#C4C4C4"));
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorno2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));

		}

		else if (isCalled && selected_menu.equals("Snack4")) {
			pane4.setTextColor(Color.parseColor("#08D126"));
			// metto in grigio 1 2 3
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorno1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o1 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o1);
			o1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o2 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o2);
			o2.setTextColor(Color.parseColor("#C4C4C4"));
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorno2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			panino_o_pizza3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			acqua_o_caffe3.setTextColor(Color.parseColor("#C4C4C4"));

		} else if (isCalled
				&& (selected_menu.equals("Intero")
						|| selected_menu.equals("Ridotto1234")
						|| selected_menu.equals("Ridotto12")
						|| selected_menu.equals("Ridotto1")
						|| selected_menu.equals("Ridotto2")
						|| selected_menu.equals("Ridotto3") || selected_menu
							.equals("Ridotto4"))) {
			primo1.setTextColor(Color.parseColor("#C4C4C4"));
			contorno1.setTextColor(Color.parseColor("#C4C4C4"));
			dessert1.setTextColor(Color.parseColor("#C4C4C4"));
			pane1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o1 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o1);
			o1.setTextColor(Color.parseColor("#C4C4C4"));
			TextView o2 = (TextView) theContainer
					.findViewById(R.id.tipologia_snack_o2);
			o2.setTextColor(Color.parseColor("#C4C4C4"));
			secondo2.setTextColor(Color.parseColor("#C4C4C4"));
			contorno2.setTextColor(Color.parseColor("#C4C4C4"));
			dessert2.setTextColor(Color.parseColor("#C4C4C4"));
			pane2.setTextColor(Color.parseColor("#C4C4C4"));
			panino_o_pizza3.setTextColor(Color.parseColor("#C4C4C4"));
			dessert3.setTextColor(Color.parseColor("#C4C4C4"));
			acqua_o_caffe3.setTextColor(Color.parseColor("#C4C4C4"));
			insalatona4.setTextColor(Color.parseColor("#C4C4C4"));
			pane4.setTextColor(Color.parseColor("#C4C4C4"));

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
