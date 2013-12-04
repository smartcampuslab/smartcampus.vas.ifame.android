package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.utils.BuildMenuController;

public class ComponiMenu extends SherlockActivity {

	public CheckBox primo;
	public CheckBox pasta_station;
	public CheckBox insalatona;
	public CheckBox panino;
	public CheckBox trancio_pizza;
	public CheckBox pizza;
	public CheckBox piatto_freddo;
	public CheckBox secondo;
	public CheckBox contorno1;
	public CheckBox contorno2;
	public CheckBox dessert;
	public CheckBox caffe;
	public CheckBox acqua;
	public CheckBox salsa2;
	public CheckBox pane1;
	public CheckBox pane2;

	public ImageView primo_button;
	public ImageView pasta_station_button;
	public ImageView insalatona_button;
	public ImageView panino_button;
	public ImageView trancio_pizza_button;
	public ImageView pizza_button;
	public ImageView piatto_freddo_button;
	public ImageView secondo_button;
	public ImageView contorno1_button;
	public ImageView contorno2_button;
	public ImageView dessert_button;
	public ImageView pane1_button;
	public ImageView pane2_button;
	public ImageView acqua_button;
	public ImageView salsa2_button;

	public enum chosenMenu {
		Intero, Ridotto1, Ridotto2, Ridotto3, Ridotto4, Snack1, Snack2, Snack3, Snack4
	};

	public static chosenMenu menu;
	public static final String MENU_COMPATIBLES = "menu_compatibles";
	public static final String MENU_CHECKED_TRUE = "menu_checked_true";

	public ArrayList<String> menuCompatibles;

	public BuildMenuController checkMenu;

	public static final String HAS_CALLED_TIPOLOGIE = "has_called_tipologie";

	public static HashMap<String, Boolean> mapCheckedMenuTrue;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_fai_il_tuo_menu);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		componiMenu();

		Button confirm = (Button) findViewById(R.id.confirm_menu_btn);
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (checkMenu == null) {
					Toast.makeText(
							getApplicationContext(),
							getResources().getString(
									R.string.error_compose_menu),
							Toast.LENGTH_LONG).show();

					return;
				}

				menuCompatibles = checkMenu.getCompatiblesMenu();
				mapCheckedMenuTrue = checkMenu.getCheckedItems();

				// TODO Auto-generated method stub
				Intent i = new Intent(ComponiMenu.this, Tipologie_menu_fr.class);

				i.putStringArrayListExtra(MENU_COMPATIBLES, menuCompatibles);
				i.putExtra(MENU_CHECKED_TRUE, mapCheckedMenuTrue);
				i.putExtra(HAS_CALLED_TIPOLOGIE, true);
				startActivity(i);

			}

		});

	}

	public void onMenuCheckboxClicked(View view) {
		// Is the view now checked?
		boolean checked = ((CheckBox) view).isChecked();

		List<CheckBox> checkClickableUpdated = new ArrayList<CheckBox>();

		checkMenu = new BuildMenuController(primo, pasta_station, insalatona,
				panino, trancio_pizza, pizza, piatto_freddo, secondo,
				contorno1, contorno2, dessert, caffe, acqua, salsa2, pane1,
				pane2);

		checkClickableUpdated = checkMenu.updatePossibilitiesMenu();

	}

	public void componiMenu() {

		primo = (CheckBox) findViewById(R.id.primo_piatto);
		pasta_station = (CheckBox) findViewById(R.id.pasta_station);
		insalatona = (CheckBox) findViewById(R.id.insalatona);
		panino = (CheckBox) findViewById(R.id.panino);
		trancio_pizza = (CheckBox) findViewById(R.id.tranciopizza);
		pizza = (CheckBox) findViewById(R.id.pizza);
		piatto_freddo = (CheckBox) findViewById(R.id.piattofreddo);
		secondo = (CheckBox) findViewById(R.id.secondo);
		contorno1 = (CheckBox) findViewById(R.id.contorno1);
		contorno2 = (CheckBox) findViewById(R.id.contorno2);
		dessert = (CheckBox) findViewById(R.id.dessert);
		caffe = (CheckBox) findViewById(R.id.caffe);
		acqua = (CheckBox) findViewById(R.id.acqua);
		salsa2 = (CheckBox) findViewById(R.id.salsa2);
		pane1 = (CheckBox) findViewById(R.id.pane1);
		pane2 = (CheckBox) findViewById(R.id.pane2);

		primo_button = (ImageView) findViewById(R.id.primo_button);
		pasta_station_button = (ImageView) findViewById(R.id.pastastation_button);
		insalatona_button = (ImageView) findViewById(R.id.insalatona_button);
		panino_button = (ImageView) findViewById(R.id.panino_button);
		trancio_pizza_button = (ImageView) findViewById(R.id.tranciopizza_button);
		pizza_button = (ImageView) findViewById(R.id.pizza_button);
		piatto_freddo_button = (ImageView) findViewById(R.id.piattofreddo_button);
		secondo_button = (ImageView) findViewById(R.id.secondo_button);
		contorno1_button = (ImageView) findViewById(R.id.contorno1_button);
		contorno2_button = (ImageView) findViewById(R.id.contorno2_button);
		dessert_button = (ImageView) findViewById(R.id.dessert_button);
		pane1_button = (ImageView) findViewById(R.id.pane1_button);
		pane2_button = (ImageView) findViewById(R.id.pane2_button);
		acqua_button = (ImageView) findViewById(R.id.acqua_button);
		salsa2_button = (ImageView) findViewById(R.id.salsa2_button);

		final View primo_view = (View) findViewById(R.id.primo_include);
		final View pasta_station_view = (View) findViewById(R.id.pastastation_include);
		final View insalatona_view = (View) findViewById(R.id.insalatona_include);
		final View panino_view = (View) findViewById(R.id.panino_include);
		final View trancio_pizza_view = (View) findViewById(R.id.tranciopizza_include);
		final View pizza_view = (View) findViewById(R.id.pizza_include);
		final View piatto_freddo_view = (View) findViewById(R.id.piattofreddo_include);
		final View secondo_view = (View) findViewById(R.id.secondo_include);
		final View contorno1_view = (View) findViewById(R.id.contorno1_include);
		final View contorno2_view = (View) findViewById(R.id.contorno2_include);
		final View dessert_view = (View) findViewById(R.id.dessert_include);
		final View caffe_view = (View) findViewById(R.id.caffe_include);
		final View acqua_view = (View) findViewById(R.id.acqua_include);
		final View salsa2_view = (View) findViewById(R.id.salsa2_include);
		final View pane1_view = (View) findViewById(R.id.pane1_include);
		final View pane2_view = (View) findViewById(R.id.pane2_include);

	}
}
