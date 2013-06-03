package eu.trentorise.smartcampus.ifame.activity;

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

	public CheckBox primo;
	public CheckBox secondo;
	public CheckBox contorno1;
	public CheckBox contorno2;
	public CheckBox dessert;
	public CheckBox panino;
	public CheckBox insalatona;
	public CheckBox pizza;

	public enum chosenMenu {
		Intero, Ridotto, Snack, Pizza, Panino
	};

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

				if (!isPrimoPiatto && !isSecondoPiatto && !isContorno1
						&& !isContorno2 && !isInsalatona && !isDessert
						&& !isPizza && !isPanino) {
					Toast toast = Toast.makeText(getApplicationContext(), R.string.iDeciso_compose_menu_no_items_selected, Toast.LENGTH_LONG);
					toast.show();
					

				} else {

					// intero
					if (!isPrimoPiatto && !isSecondoPiatto && !isContorno1
							&& !isContorno2 && !isInsalatona && !isDessert
							&& !isPizza && !isPanino)
						menu = null;
					else if (primo.isChecked() && secondo.isChecked())
						Fai_il_tuo_menu.menu = chosenMenu.Intero;

					// snack1
					else if (primo.isChecked()
							&& (!secondo.isChecked())
							&& ((contorno1.isChecked()
									&& !contorno2.isChecked() && !dessert
										.isChecked())
									^ (!contorno1.isChecked()
											&& contorno2.isChecked() && !dessert
												.isChecked())
									^ (!contorno1.isChecked()
											&& !contorno2.isChecked() && dessert
												.isChecked()) ^ (!contorno1
									.isChecked() && !contorno2.isChecked() && !dessert
										.isChecked()))) {
						menu = chosenMenu.Snack;
					}
					// snack2
					else if (!primo.isChecked()
							&& (secondo.isChecked())
							&& ((contorno1.isChecked()
									&& !contorno2.isChecked() && !dessert
										.isChecked())
									^ (!contorno1.isChecked()
											&& contorno2.isChecked() && !dessert
												.isChecked())
									^ (!contorno1.isChecked()
											&& !contorno2.isChecked() && dessert
												.isChecked()) ^ (!contorno1
									.isChecked() && !contorno2.isChecked() && !dessert
										.isChecked()))) {
						menu = chosenMenu.Snack;
					}

					// snack insalatona
					else if (insalatona.isChecked())
						menu = chosenMenu.Snack;
					// snack panino
					else if (panino.isChecked())
						menu = chosenMenu.Snack;
					else
						menu = chosenMenu.Ridotto;

					/*
					 * Intent i = new Intent(Fai_il_tuo_menu.this,
					 * MostraMenuComposto.class);
					 */

					// per ora
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

					String selected_menu = null;
					if (menu.equals(chosenMenu.Intero))
						selected_menu = "Intero";

					else if (menu.equals(chosenMenu.Ridotto))
						selected_menu = "Ridotto";
					else if (menu.equals(chosenMenu.Snack))
						selected_menu = "Snack";

					i.putExtra("selected_item", selected_menu);

					startActivity(i);

				}
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
					dessert.setEnabled(false);
					dessert.setChecked(false);
					contorno1.setEnabled(false);
					contorno1.setChecked(false);
					contorno2.setEnabled(false);
					contorno2.setChecked(false);

				} else {

					panino.setEnabled(true);
					pizza.setEnabled(true);
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

					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setChecked(false);
					pizza.setEnabled(false);
				} else {

					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno2.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !pizza.isChecked())
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

					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setChecked(false);
					pizza.setEnabled(false);
				} else {
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !dessert.isChecked()
							&& !panino.isChecked() && !pizza.isChecked())
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
					insalatona.setEnabled(false);
					insalatona.setChecked(false);
					panino.setEnabled(false);
					// panino.setChecked(false);
					pizza.setEnabled(false);
					pizza.setChecked(false);

				} else {
					if (!primo.isChecked() && !secondo.isChecked()
							&& !contorno1.isChecked() && !contorno2.isChecked()
							&& !panino.isChecked() && !pizza.isChecked())
						insalatona.setEnabled(true);
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

		// caterba di ifssss
		/*
		 * // intero if (primo.isChecked() && secondo.isChecked())
		 * Fai_il_tuo_menu.menu = chosenMenu.Intero;
		 * 
		 * // snack1 else if (primo.isChecked() && (!secondo.isChecked()) &&
		 * ((contorno1.isChecked() && !contorno2.isChecked() && !dessert
		 * .isChecked()) ^ (!contorno1.isChecked() && contorno2.isChecked() &&
		 * !dessert .isChecked()) ^ (!contorno1.isChecked() &&
		 * !contorno2.isChecked() && dessert .isChecked()) ^
		 * (!contorno1.isChecked() && !contorno2.isChecked() &&
		 * !dessert.isChecked()))) { menu = chosenMenu.Snack; } // snack2 else
		 * if (!primo.isChecked() && (secondo.isChecked()) &&
		 * ((contorno1.isChecked() && !contorno2.isChecked() && !dessert
		 * .isChecked()) ^ (!contorno1.isChecked() && contorno2.isChecked() &&
		 * !dessert .isChecked()) ^ (!contorno1.isChecked() &&
		 * !contorno2.isChecked() && dessert .isChecked()) ^
		 * (!contorno1.isChecked() && !contorno2.isChecked() &&
		 * !dessert.isChecked()))) { menu = chosenMenu.Snack; }
		 * 
		 * // snack insalatona else if (insalatona.isChecked()) menu =
		 * chosenMenu.Snack; //snack panino else if (panino.isChecked()) menu =
		 * chosenMenu.Snack; else menu = chosenMenu.Ridotto;
		 */

	}
}

/*
 * primo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 * 
 * @Override public void onCheckedChanged(CompoundButton buttonView, boolean
 * isChecked) { if (!isChecked) { //noprimo
 * 
 * if (secondo.isChecked()){//secondo if(contorno1.isChecked()){ //secondo
 * contorno1 if (contorno2.isChecked()){ //secondo c1 c2
 * 
 * } else{ //secondo c1 noc2 } } else{ //secondo e no contorno1 } } else{
 * //tutti i casi in cui il secondo no }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 * 
 * else { // primo selezionato insalatona.setEnabled(false);
 * insalatona.setChecked(false); pizza.setEnabled(false);
 * pizza.setChecked(false); panino.setEnabled(false); panino.setChecked(false);
 * 
 * if (secondo.isChecked()) { menu = chosenMenu.Intero;
 * contorno1.setEnabled(true); contorno2.setEnabled(true);
 * dessert.setEnabled(true); } else { // preso il primo ma non il secondo if
 * (contorno1.isChecked()) { // primo contorno 1 if (contorno2.isChecked()) { //
 * primo contorno1 // contorno 2 // --->ridotto menu = chosenMenu.Ridotto;
 * dessert.setEnabled(true); } else { // primo cortorno 1 if
 * (dessert.isChecked()) { // primo contorno1 // dessert --> // Ridotto
 * 
 * menu = chosenMenu.Ridotto;
 * 
 * } else { // primo contorno1 menu = chosenMenu.Snack; }
 * 
 * } } else {// primo if (contorno2.isChecked()) { // primo contorno2 if
 * (dessert.isChecked()) { // primo contorno2 dessert
 * 
 * menu = chosenMenu.Ridotto;
 * 
 * } else { // primo contorno2 menu = chosenMenu.Snack; }
 * 
 * } else {// primo //che abbia o no il dessert sono in snack menu =
 * chosenMenu.Snack;
 * 
 * } } } } //fine del primo selezionato con tutte le altre possibilità
 * 
 * } });
 * 
 * secondo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 * 
 * @Override public void onCheckedChanged(CompoundButton buttonView, boolean
 * isChecked) { // TODO Auto-generated method stub
 * 
 * } });
 */

