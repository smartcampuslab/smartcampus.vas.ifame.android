package eu.trentorise.smartcampus.ifame.activity;


import com.actionbarsherlock.ActionBarSherlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;

public class Fai_il_tuo_menu extends Activity {

	private boolean isPrimoPiatto;
	private boolean isSecondoPiatto;
	private boolean isContorno1;
	private boolean isContorno2;
	private boolean isInsalatona;
	private boolean isDessert;
	private boolean isPanino;
	private boolean isPizza;

	public static final String SELECTED_MENU = "selected_menu";
	public static final String PRIMO_AVAILABLE = "primoAvailable";
	public static final String SECONDO_AVAILABLE = "secondoAvailable";
	public static final String CONTORNO_1_AVAILABLE = "contorno1Available";
	public static final String CONTORNO_2_AVAILABLE = "contorno2Available";
	public static final String DESSERT_AVAILABLE = "dessertAvailable";
	public static final String INSALATONA_AVAILABLE = "insalatonaAvailable";
	public static final String PIZZA_AVAILABLE = "pizzaAvailable";
	
	public CheckBox primo;
	public CheckBox secondo;
	public CheckBox contorno1;
	public CheckBox contorno2;
	public CheckBox dessert;
	public CheckBox panino;
	public CheckBox insalatona;
	public CheckBox pizza;

	public enum chosenMenu {
		Intero, Ridotto1, Ridotto2, Ridotto3, Ridotto4, Ridotto12, Ridotto1234, Snack1, Snack2, Snack3, Snack4, Pizza, Panino};

	public static chosenMenu menu;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fai_il_tuo_menu);

		componiMenu();

		
		Button confirm = (Button) findViewById(R.id.confirm_menu_btn);
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				isPrimoPiatto = primo.isChecked();
				isSecondoPiatto = secondo.isChecked();
				isContorno1 = contorno1.isChecked();
				isContorno2 = contorno2.isChecked();
				isInsalatona = insalatona.isChecked();
				isDessert = dessert.isChecked();
				isPizza = pizza.isChecked();
				isPanino = panino.isChecked();

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
					Toast toast = Toast.makeText(getApplicationContext(),
							R.string.iDeciso_compose_menu_no_items_selected,
							Toast.LENGTH_LONG);
					toast.show();
				}

				// intero
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
						c2Avail = true;
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
								.isChecked()))
					menu = chosenMenu.Ridotto3;

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

				// snack panino
				else if (panino.isChecked())
					menu = chosenMenu.Snack3;

				// se ha preso il primo + almeno 2 tra contorni e dessert
				else if (isPrimoPiatto
						&& ((isContorno1 && isContorno2)
								|| (isContorno1 && isDessert) || (isContorno2 && isDessert))) {

					menu = chosenMenu.Ridotto1;

					if (!isContorno1)
						c1Avail = true;
					if (!isContorno2)
						c2Avail = true;
					if (!isDessert)
						dessertAvail = true;
				}
				
				// se ha preso il secondo + almeno 2 tra contorni e dessert
				else if (isSecondoPiatto
						&& ((isContorno1 && isContorno2)
								|| (isContorno1 && isDessert) || (isContorno2 && isDessert))) {

					menu = chosenMenu.Ridotto2;

					if (!isContorno1)
						c1Avail = true;
					if (!isContorno2)
						c2Avail = true;
					if (!isDessert)
						dessertAvail = true;
				}
				
				else if (isPizza)
					menu = chosenMenu.Ridotto4;
				
				// se ha preso 2 contorni e il dessert a questo pu� scegliere da R1 e R2 e quindi tra primo o secondo
				
				else if (isContorno1 && isContorno2 && isDessert){
					menu = chosenMenu.Ridotto12;
					if (!isPrimoPiatto)
						primoAvail = true;
					if (!isSecondoPiatto)
						secondoAvail = true;
				}
				
				// se ha preso 2 tra contorni e dessert allora pu� scegliere qualsiasi tipo di ridotto 
				else if (isContorno1 && isContorno2 && !isDessert){
					menu = chosenMenu.Ridotto1234;
					
					primoAvail = true;
					secondoAvail = true;
					insalatonaAvail = true;
					pizzaAvail = true;
					dessertAvail = true;
				}
				else if (isContorno1 && !isContorno2 && isDessert){
					menu = chosenMenu.Ridotto1234;	
					
					primoAvail = true;
					secondoAvail = true;
					insalatonaAvail = true;
					pizzaAvail = true;
					c2Avail = true;
				}
				else if (!isContorno1 && isContorno2 && isDessert){
					menu = chosenMenu.Ridotto1234;	
					
					primoAvail = true;
					secondoAvail = true;
					insalatonaAvail = true;
					pizzaAvail = true;
					c1Avail = true;
				}
				
				
				
				
				
				
				
				

				Intent i = new Intent(Fai_il_tuo_menu.this,
						Tipologie_menu.class);

				i.putExtra("isPrimoPiatto", isPrimoPiatto);
				i.putExtra("isSecondoPiatto", isSecondoPiatto);
				i.putExtra("isContorno1", isContorno1);
				i.putExtra("isContorno2", isContorno2);
				i.putExtra("isInsalatona", isInsalatona);
				i.putExtra("isDessert", isDessert);
				i.putExtra("isPizza", isPizza);
				i.putExtra("isPanino", isPanino);

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
				

				i.putExtra(SELECTED_MENU, selected_menu);

				startActivity(i);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fai_il_tuo_menu, menu);
		return true;
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

		// quando rilascio, se c1 c2 e dessert allora non devo liberare
		// insalatona
		primo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {

					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);

				} else {
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
					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);

				} else {
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
			 * non escludere i contorni e il dessert. la cosa importante � che
			 * solo 2 su 3 siano selezionati
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
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