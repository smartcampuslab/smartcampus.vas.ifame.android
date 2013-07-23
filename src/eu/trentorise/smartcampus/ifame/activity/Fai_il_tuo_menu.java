package eu.trentorise.smartcampus.ifame.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;

public class Fai_il_tuo_menu extends SherlockActivity {

	//queste variabili salvano, alla pressione del bottone, lo stato delle checkboxes
	private boolean isPrimoPiatto;
	private boolean isSecondoPiatto;
	private boolean isContorno1;
	private boolean isContorno2;
	private boolean isInsalatona;
	private boolean isDessert;
	private boolean isPanino;
	private boolean isPizza;

	//stringhe statiche da usare come keys per gli intent extras
	public static final String SELECTED_MENU = "selected_menu";
	public static final String HAS_CALLED_TIPOLOGIE = "has_called_tipologie";
	public static final String PRIMO_AVAILABLE = "primoAvailable";
	public static final String SECONDO_AVAILABLE = "secondoAvailable";
	public static final String CONTORNO_1_AVAILABLE = "contorno1Available";
	public static final String CONTORNO_2_AVAILABLE = "contorno2Available";
	public static final String DESSERT_AVAILABLE = "dessertAvailable";
	public static final String INSALATONA_AVAILABLE = "insalatonaAvailable";
	public static final String PIZZA_AVAILABLE = "pizzaAvailable";
	public static final String IS_PRIMO = "isPrimo";
	public static final String IS_SECONDO = "isSecondo";
	public static final String IS_CONTORNO_1 = "isC1";
	public static final String IS_CONTORNO_2 = "isC2";
	public static final String IS_INSALATONA = "isInsalatona";
	public static final String IS_DESSERT = "isDessert";
	public static final String IS_PANINO = "isPanino";
	public static final String IS_PIZZA = "isPizza";

	public CheckBox primo;
	public CheckBox secondo;
	public CheckBox contorno1;
	public CheckBox contorno2;
	public CheckBox dessert;
	public CheckBox panino;
	public CheckBox insalatona;
	public CheckBox pizza;

	public ImageView primo_button;
	public ImageView secondo_button;
	public ImageView contorno1_button;
	public ImageView contorno2_button;
	public ImageView dessert_button;
	public ImageView pane_button;
	public ImageView insalatona_button;
	public ImageView pizza_button;

	public enum chosenMenu {
		Intero, Ridotto1, Ridotto2, Ridotto3, Ridotto4, Ridotto12, Ridotto1234, Snack1, Snack2, Snack3, Snack4, Snack12, Pizza, Panino
	};

	public static chosenMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fai_il_tuo_menu);

		//istanzia gli elementi dell'UI e assegna i vari listeners alle checkboxes
		componiMenu();

		Button confirm = (Button) findViewById(R.id.confirm_menu_btn);
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//alla pressione del pulsante, controllo quali checkboxes sono selezionate
				isPrimoPiatto = primo.isChecked();
				isSecondoPiatto = secondo.isChecked();
				isContorno1 = contorno1.isChecked();
				isContorno2 = contorno2.isChecked();
				isInsalatona = insalatona.isChecked();
				isDessert = dessert.isChecked();
				isPizza = pizza.isChecked();
				isPanino = panino.isChecked();

				//queste variabili servono a salvare se un piatto può essere ancora acquistato rimanendo comunque nel menu scelto, senza dunque aumentare il prezzo
				boolean primoAvail = false;
				boolean secondoAvail = false;
				boolean c1Avail = false;
				boolean c2Avail = false;
				boolean dessertAvail = false;
				boolean insalatonaAvail = false;
				boolean pizzaAvail = false;

				// no selezioni
				if (!isPrimoPiatto && !isSecondoPiatto && !isContorno1
						&& !isContorno2 && !isInsalatona && !isDessert
						&& !isPizza && !isPanino) {
					Toast.makeText(getApplicationContext(),
							R.string.iDeciso_compose_menu_no_items_selected,
							Toast.LENGTH_LONG).show();
					return;
				}

				// intero (ssse abbiamo il primo e il secondo (condizione necessaria e sufficiente)
				else if (primo.isChecked() && secondo.isChecked()) {
					Fai_il_tuo_menu.menu = chosenMenu.Intero;
					if (!isContorno1)
						c1Avail = true;
					if (!isContorno2)
						c2Avail = true;
					if (!isDessert)
						dessertAvail = true;
				}

				// snack1 ---> primo selezionato + solo uno tra i contorni o il
				// dessert
				else if (primo.isChecked()
						&& (!secondo.isChecked())
						&& ((contorno1.isChecked() && !contorno2.isChecked() && !dessert
								.isChecked())
								^ (!contorno1.isChecked()
										&& contorno2.isChecked() && !dessert
											.isChecked())
								^ (!contorno1.isChecked()
										&& !contorno2.isChecked() && dessert
											.isChecked()) ^ (!contorno1
								.isChecked() && !contorno2.isChecked() && !dessert
									.isChecked()))) {

					menu = chosenMenu.Snack1;

					// se ha preso solo ed esclusivamente il primo
					if (!contorno1.isChecked() && !contorno2.isChecked()
							&& !dessert.isChecked()) {
						c1Avail = true;
						dessertAvail = true;

					}
				}
				// snack2 ---> secondo selezionato + solo uno tra i contorni o
				// il dessert
				else if (!primo.isChecked()
						&& (secondo.isChecked())
						&& ((contorno1.isChecked() && !contorno2.isChecked() && !dessert
								.isChecked())
								^ (!contorno1.isChecked()
										&& contorno2.isChecked() && !dessert
											.isChecked())
								^ (!contorno1.isChecked()
										&& !contorno2.isChecked() && dessert
											.isChecked()) ^ (!contorno1
								.isChecked() && !contorno2.isChecked() && !dessert
									.isChecked()))) {
					menu = chosenMenu.Snack2;

					// se ha preso solo ed esclusivamente il secondo
					if (!contorno1.isChecked() && !contorno2.isChecked()
							&& !dessert.isChecked()) {
						c1Avail = true;
						c2Avail = true;
						dessertAvail = true;

					}
				}

				// snack insalatona
				else if (insalatona.isChecked() && !dessert.isChecked()
						&& !contorno1.isChecked() && !contorno2.isChecked())
					menu = chosenMenu.Snack4;

				// ridotto insalatona
				else if (insalatona.isChecked()
						&& (contorno1.isChecked() || contorno2.isChecked() || dessert
								.isChecked())) {
					menu = chosenMenu.Ridotto3;

					// se oltre all'insalata ha preso solo uno tra contorni e
					// dessert
					if (isContorno1 && !isContorno2 && !isDessert) {
						c2Avail = true;
						dessertAvail = true;
					}

					if (!isContorno1 && isContorno2 && !isDessert) {
						c1Avail = true;
						dessertAvail = true;
					}
					if (!isContorno1 && !isContorno2 && isDessert) {
						c2Avail = true;
						c1Avail = true;
					}

					// se oltre all'insalata ha preso 2 tra contorni e dessert
					// non fare niente, non puoi più prendere altro a parte il
					// pane

					/*
					 * if ((isContorno1 && isContorno2) || (isContorno1 &&
					 * isDessert) || (isContorno2 && isDessert)) {
					 * 
					 * 
					 * }
					 */

				}
				// snack panino
				else if (panino.isChecked())
					menu = chosenMenu.Snack3;

				// se ha preso il primo + almeno 2 tra contorni e dessert non
				// mettere niente come available
				else if (isPrimoPiatto
						&& ((isContorno1 && isContorno2)
								|| (isContorno1 && isDessert) || (isContorno2 && isDessert))) {

					menu = chosenMenu.Ridotto1;

				}

				// se ha preso il secondo + almeno 2 tra contorni e dessert
				else if (isSecondoPiatto
						&& ((isContorno1 && isContorno2)
								|| (isContorno1 && isDessert) || (isContorno2 && isDessert))) {

					menu = chosenMenu.Ridotto2;

				}

				else if (isPizza)
					menu = chosenMenu.Ridotto4;

				// se ha preso 2 contorni e 1 dessert a questo può scegliere da
				// R1 e R2 e quindi tra primo o secondo

				else if (isContorno1 && isContorno2 && isDessert) {
					menu = chosenMenu.Ridotto12;
					if (!isPrimoPiatto)
						primoAvail = true;
					if (!isSecondoPiatto)
						secondoAvail = true;
				}

				// se ha preso 2 TRA contorni e dessert allora puï¿½ scegliere
				// qualsiasi tipo di ridotto
				else if (isContorno1 && isContorno2 && !isDessert) {
					menu = chosenMenu.Ridotto1234;

					primoAvail = true;
					secondoAvail = true;
					insalatonaAvail = true;
					pizzaAvail = true;
					dessertAvail = true;
				} else if (isContorno1 && !isContorno2 && isDessert) {
					menu = chosenMenu.Ridotto1234;
					primoAvail = true;
					secondoAvail = true;
					insalatonaAvail = true;
					pizzaAvail = true;
					c2Avail = true;
				} else if (!isContorno1 && isContorno2 && isDessert) {
					menu = chosenMenu.Ridotto1234;

					primoAvail = true;
					secondoAvail = true;
					insalatonaAvail = true;
					pizzaAvail = true;
					c1Avail = true;
				}

				// se ho preso solo ed esclusivamente 1 tra contorni e dessert
				else if ((isContorno1 && !isContorno2 && !isDessert)
						^ (!isContorno1 && isContorno2 && !isDessert)
						^ (!isContorno1 && !isContorno2 && isDessert)) {
					menu = chosenMenu.Snack12;
					// available tutte le composizioni di snack 1 e 2
					primoAvail = true;
					secondoAvail = true;
				}

				Intent i = new Intent(Fai_il_tuo_menu.this,
						Tipologie_menu_fr.class);

				i.putExtra(IS_PRIMO, isPrimoPiatto);
				i.putExtra(IS_SECONDO, isSecondoPiatto);
				i.putExtra(IS_CONTORNO_1, isContorno1);
				i.putExtra(IS_CONTORNO_2, isContorno2);
				i.putExtra(IS_INSALATONA, isInsalatona);
				i.putExtra(IS_DESSERT, isDessert);
				i.putExtra(IS_PIZZA, isPizza);
				i.putExtra(IS_PANINO, isPanino);

				i.putExtra(CONTORNO_1_AVAILABLE, c1Avail);
				i.putExtra(CONTORNO_2_AVAILABLE, c2Avail);
				i.putExtra(PRIMO_AVAILABLE, primoAvail);
				i.putExtra(SECONDO_AVAILABLE, secondoAvail);
				i.putExtra(DESSERT_AVAILABLE, dessertAvail);
				i.putExtra(INSALATONA_AVAILABLE, insalatonaAvail);
				i.putExtra(PIZZA_AVAILABLE, pizzaAvail);

				String selected_menu = null;
				if (menu.equals(chosenMenu.Intero))
					selected_menu = "Intero";
				else if (menu.equals(chosenMenu.Ridotto1))
					selected_menu = "Ridotto1";
				else if (menu.equals(chosenMenu.Ridotto2))
					selected_menu = "Ridotto2";
				else if (menu.equals(chosenMenu.Ridotto3))
					selected_menu = "Ridotto3";
				else if (menu.equals(chosenMenu.Ridotto4))
					selected_menu = "Ridotto4";
				else if (menu.equals(chosenMenu.Ridotto12))
					selected_menu = "Ridotto12";
				else if (menu.equals(chosenMenu.Ridotto1234))
					selected_menu = "Ridotto1234";
				else if (menu.equals(chosenMenu.Snack1))
					selected_menu = "Snack1";
				else if (menu.equals(chosenMenu.Snack2))
					selected_menu = "Snack2";
				else if (menu.equals(chosenMenu.Snack3))
					selected_menu = "Snack3";
				else if (menu.equals(chosenMenu.Snack4))
					selected_menu = "Snack4";
				else if (menu.equals(chosenMenu.Snack12))
					selected_menu = "Snack12";

				i.putExtra(HAS_CALLED_TIPOLOGIE, true);
				i.putExtra(SELECTED_MENU, selected_menu);
				startActivity(i);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);

	}

	public void componiMenu() {

		primo = (CheckBox) findViewById(R.id.primo_piatto);
		secondo = (CheckBox) findViewById(R.id.secondo_piatto);
		contorno1 = (CheckBox) findViewById(R.id.contorno_caldo);
		contorno2 = (CheckBox) findViewById(R.id.contorno_freddo);
		dessert = (CheckBox) findViewById(R.id.dessert);
		panino = (CheckBox) findViewById(R.id.pane);
		insalatona = (CheckBox) findViewById(R.id.insalatona);
		pizza = (CheckBox) findViewById(R.id.pizza);

		primo_button = (ImageView) findViewById(R.id.primo_button);
		secondo_button = (ImageView) findViewById(R.id.secondo_button);
		contorno1_button = (ImageView) findViewById(R.id.contorno_caldo_button);
		contorno2_button = (ImageView) findViewById(R.id.contorno_freddo_button);
		dessert_button = (ImageView) findViewById(R.id.dessert_button);
		pane_button = (ImageView) findViewById(R.id.pane_button);
		insalatona_button = (ImageView) findViewById(R.id.insalatona_button);
		pizza_button = (ImageView) findViewById(R.id.pizza_button);

		final View primo_view = (View) findViewById(R.id.primo_include);
		final View secondo_view = (View) findViewById(R.id.secondo_include);
		final View contorno_caldo_view = (View) findViewById(R.id.contorno_caldo_include);
		final View contorno_freddo_view = (View) findViewById(R.id.contorno_freddo_include);
		final View insalatona_view = (View) findViewById(R.id.insalatona_include);
		final View dessert_view = (View) findViewById(R.id.dessert_include);
		final View pane_view = (View) findViewById(R.id.pane_include);
		final View pizza_view = (View) findViewById(R.id.pizza_include);

		// setto gli OnclickListener per le ImageView per espandere il menu in
		// modo dinamico; inverte il senso della freccia per il menu espandibile

	/*	primo_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (primo_view.getVisibility() == View.GONE) {
					primo_button.setImageResource(R.drawable.arrow_up);
					primo_view.setVisibility(View.VISIBLE);
				} else {
					primo_view.setVisibility(View.GONE);
					primo_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		secondo_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (secondo_view.getVisibility() == View.GONE) {
					secondo_button.setImageResource(R.drawable.arrow_up);
					secondo_view.setVisibility(View.VISIBLE);
				} else {
					secondo_view.setVisibility(View.GONE);
					secondo_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		contorno1_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (contorno_caldo_view.getVisibility() == View.GONE) {
					contorno1_button.setImageResource(R.drawable.arrow_up);
					contorno_caldo_view.setVisibility(View.VISIBLE);
				} else {
					contorno_caldo_view.setVisibility(View.GONE);
					contorno1_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		contorno2_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (contorno_freddo_view.getVisibility() == View.GONE) {
					//contorno2_button.setImageResource(R.drawable.arrow_up);
					contorno_freddo_view.setVisibility(View.VISIBLE);
				} else {
					contorno_freddo_view.setVisibility(View.GONE);
					contorno2_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		insalatona_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (insalatona_view.getVisibility() == View.GONE) {
					insalatona_button.setImageResource(R.drawable.arrow_up);
					insalatona_view.setVisibility(View.VISIBLE);
				} else {
					insalatona_view.setVisibility(View.GONE);
					insalatona_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		dessert_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dessert_view.getVisibility() == View.GONE) {
					dessert_button.setImageResource(R.drawable.arrow_up);
					dessert_view.setVisibility(View.VISIBLE);
				} else {
					dessert_view.setVisibility(View.GONE);
					dessert_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		pane_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pane_view.getVisibility() == View.GONE) {
					pane_button.setImageResource(R.drawable.arrow_up);
					pane_view.setVisibility(View.VISIBLE);
				} else {
					pane_view.setVisibility(View.GONE);
					pane_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});

		pizza_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pizza_view.getVisibility() == View.GONE) {
					pizza_button.setImageResource(R.drawable.arrow_up);
					pizza_view.setVisibility(View.VISIBLE);
				} else {
					pizza_view.setVisibility(View.GONE);
					pizza_button.setImageResource(R.drawable.arrow_down);
				}
			}

		});*/

		 
		/*
		 * 
		 * logica delle checkboxes, controlla cosa è selezionato ed esclude i piatti non consoni con la scelta
		 */
		
		
		
		primo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
				//	primo_button.setVisibility(View.VISIBLE);
					
					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);
					
				} else {
					primo_button.setVisibility(View.GONE);
					primo_view.setVisibility(View.GONE);
					primo_button.setImageResource(R.drawable.arrow_down);
					
					if (!secondo.isChecked()
							&& !panino.isChecked()
							&& !pizza.isChecked()
							&& !(contorno1.isChecked() && contorno2.isChecked() && dessert
									.isChecked()))
						insalatona.setEnabled(true);

					if (!pizza.isChecked() && !secondo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !contorno1.isChecked())
						insalatona.setEnabled(true);
					if (!insalatona.isChecked() && !secondo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !contorno1.isChecked())
						pizza.setEnabled(true);
					if (!pizza.isChecked() && !secondo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !insalatona.isChecked()
							&& !contorno1.isChecked())
						panino.setEnabled(true);
				}
			}
		});

		// quando rilascio, se c1 c2 e dessert allora non devo liberare
		// insalatona
		secondo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
				//	secondo_button.setVisibility(View.VISIBLE);
					
					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);
					
				} else {
					secondo_button.setVisibility(View.GONE);
					secondo_view.setVisibility(View.GONE);
					secondo_button.setImageResource(R.drawable.arrow_down);
					
					if (!primo.isChecked()
							&& !panino.isChecked()
							&& !pizza.isChecked()
							&& !(contorno1.isChecked() && contorno2.isChecked() && dessert
									.isChecked()))
						insalatona.setEnabled(true);
					if (!pizza.isChecked() && !primo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !contorno1.isChecked())
						insalatona.setEnabled(true);
					if (!insalatona.isChecked() && !primo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !contorno1.isChecked())
						pizza.setEnabled(true);
					if (!pizza.isChecked() && !primo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !insalatona.isChecked()
							&& !contorno1.isChecked())
						panino.setEnabled(true);

				}
			}
		});

		panino.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
			//		pane_button.setVisibility(View.VISIBLE);
					
					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);
					primo.setEnabled(false);
					primo.setChecked(false);
					secondo.setEnabled(false);
					secondo.setChecked(false);
					contorno1.setEnabled(false);
					contorno1.setChecked(false);
					contorno2.setEnabled(false);
					contorno2.setChecked(false);
					dessert.setEnabled(false);
					dessert.setChecked(false);
					
				} else {
					pane_button.setVisibility(View.GONE);
					pane_view.setVisibility(View.GONE);
					pane_button.setImageResource(R.drawable.arrow_down);

					insalatona.setEnabled(true);
					pizza.setEnabled(true);
					primo.setEnabled(true);
					secondo.setEnabled(true);
					contorno1.setEnabled(true);
					contorno2.setEnabled(true);
					dessert.setEnabled(true);
					
				}
			}
		});

		insalatona.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			/*
			 * non escludere i contorni e il dessert. la cosa importante ï¿½ che
			 * solo 2 su 3 siano selezionati
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
			//		insalatona_button.setVisibility(View.VISIBLE);
					
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);
					primo.setEnabled(false);
					primo.setChecked(false);
					secondo.setEnabled(false);
					secondo.setChecked(false);
					if // da ripetere in !isChecked
					(contorno1.isChecked() && contorno2.isChecked()) {
						dessert.setEnabled(false);
						dessert.setChecked(false);
					}

					if (contorno2.isChecked() && dessert.isChecked()) {
						contorno1.setEnabled(false);
						contorno1.setChecked(false);
					}
					if (contorno1.isChecked() && dessert.isChecked()) {
						contorno2.setEnabled(false);
						contorno2.setChecked(false);
					}

				} else {
			//		insalatona_button.setVisibility(View.GONE);
					insalatona_view.setVisibility(View.GONE);
					insalatona_button.setImageResource(R.drawable.arrow_down);

					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !contorno2.isChecked()
							&& !dessert.isChecked()) {
						panino.setEnabled(true);
						pizza.setEnabled(true);
					}
					primo.setEnabled(true);
					secondo.setEnabled(true);
					contorno1.setEnabled(true);
					contorno2.setEnabled(true);
					dessert.setEnabled(true);

				}
			}
		});

		pizza.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
		//			pizza_button.setVisibility(View.VISIBLE);
					
					panino.setEnabled(false);
					// panino.setChecked(false);
					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					primo.setEnabled(false);
					primo.setChecked(false);
					secondo.setEnabled(false);
					secondo.setChecked(false);
					contorno1.setEnabled(false);
					contorno1.setChecked(false);
					contorno2.setEnabled(false);
					contorno2.setChecked(false);
					dessert.setEnabled(false);
					dessert.setChecked(false);

				} else {
					pizza_button.setVisibility(View.GONE);
					pizza_view.setVisibility(View.GONE);
					pizza_button.setImageResource(R.drawable.arrow_down);
					
					panino.setEnabled(true);
					insalatona.setEnabled(true);
					primo.setEnabled(true);
					secondo.setEnabled(true);
					contorno1.setEnabled(true);
					contorno2.setEnabled(true);
					dessert.setEnabled(true);

				}
			}
		});

		contorno1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
		//			contorno1_button.setVisibility(View.VISIBLE);

					if (contorno2.isChecked() && dessert.isChecked()) {
						insalatona.setEnabled(false);
						insalatona.setChecked(false);
					}

					if (insalatona.isChecked() && contorno2.isChecked())
						dessert.setEnabled(false);
					if (insalatona.isChecked() && dessert.isChecked()) {
						contorno2.setEnabled(false);
					}
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setChecked(false);
					pizza.setEnabled(false);
				} else {
					contorno1_button.setVisibility(View.GONE);
					contorno_caldo_view.setVisibility(View.GONE);
					contorno1_button.setImageResource(R.drawable.arrow_down);

					contorno2.setEnabled(true);
					dessert.setEnabled(true);

					if (!primo.isChecked() && !secondo.isChecked()
							&& !panino.isChecked() && !pizza.isChecked()
							&& !contorno1.isChecked() && !dessert.isChecked()) {
						dessert.setEnabled(true);
						contorno2.setEnabled(true);
						insalatona.setEnabled(true);
					}
					if (!primo.isChecked() && !secondo.isChecked()
							&& !pizza.isChecked() && !panino.isChecked())
						insalatona.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !insalatona.isChecked() && !pizza.isChecked())
						panino.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !insalatona.isChecked())
						pizza.setEnabled(true);
				}
			}
		});

		contorno2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
		//			contorno2_button.setVisibility(View.VISIBLE);

					if (contorno1.isChecked() && dessert.isChecked()) {
						insalatona.setEnabled(false);
						insalatona.setChecked(false);
					}

					if (insalatona.isChecked() && contorno1.isChecked())
						dessert.setEnabled(false);
					if (insalatona.isChecked() && dessert.isChecked()) {
						contorno1.setEnabled(false);
					}
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setChecked(false);
					pizza.setEnabled(false);
				} else {
					contorno2_button.setVisibility(View.GONE);
					contorno_freddo_view.setVisibility(View.GONE);
					contorno2_button.setImageResource(R.drawable.arrow_down);
					
					contorno1.setEnabled(true);
					dessert.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()
							&& !panino.isChecked() && !pizza.isChecked()
							&& !contorno1.isChecked() && !dessert.isChecked()) {
						insalatona.setEnabled(true);
						dessert.setEnabled(true);
						contorno1.setEnabled(true);
					}
					if (!primo.isChecked() && !secondo.isChecked()
							&& !pizza.isChecked() && !panino.isChecked())
						insalatona.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !dessert.isChecked()
							&& !insalatona.isChecked() && !pizza.isChecked())
						panino.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !insalatona.isChecked())
						pizza.setEnabled(true);

				}
			}
		});

		dessert.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
		//			dessert_button.setVisibility(View.VISIBLE);
					
					if (contorno2.isChecked() && contorno1.isChecked()) {
						insalatona.setEnabled(false);
						insalatona.setChecked(false);
					}
					if (insalatona.isChecked() && contorno2.isChecked())
						contorno1.setEnabled(false);
					if (insalatona.isChecked() && contorno1.isChecked()) {
						contorno2.setEnabled(false);
					}
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);

				} else {
					dessert_button.setVisibility(View.GONE);
					dessert_view.setVisibility(View.GONE);
					dessert_button.setImageResource(R.drawable.arrow_down);
					
					contorno2.setEnabled(true);
					contorno1.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()

					&& !panino.isChecked() && !pizza.isChecked()) {
						contorno2.setEnabled(true);
						contorno1.setEnabled(true);
						insalatona.setEnabled(true);
					}
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !contorno2.isChecked()
							&& !insalatona.isChecked() && !pizza.isChecked())
						panino.setEnabled(true);
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !contorno2.isChecked()
							&& !panino.isChecked() && !insalatona.isChecked())
						pizza.setEnabled(true);
				}

			}
		});

	}
}