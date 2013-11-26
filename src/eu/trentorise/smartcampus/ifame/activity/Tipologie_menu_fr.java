package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.tabs.TabListener;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaInteroFragment;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaRidottoFragment;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaSnackFragment;

public class Tipologie_menu_fr extends SherlockFragmentActivity {

	public enum chosenMenu {
		Intero, Ridotto1, Ridotto2, Ridotto3, Ridotto4, Snack1, Snack2, Snack3, Snack4
	};

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String selected_menu = getIntent().getStringExtra(
				Fai_il_tuo_menu.SELECTED_MENU);

		ArrayList<String> menu_compatibles = getIntent()
				.getStringArrayListExtra(ComponiMenu.MENU_COMPATIBLES);
		
		HashMap<String, Boolean> mapCheckedItems = (HashMap<String, Boolean>) getIntent().getSerializableExtra(ComponiMenu.MENU_CHECKED_TRUE);

		// getSupportActionBar().setDisplayShowHomeEnabled(false);
		// getSupportActionBar().setDisplayShowTitleEnabled(true);
		setContentView(R.layout.empty_layout);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// intero tab
		Tab interoTab = getSupportActionBar().newTab();
		interoTab.setText(R.string.iDeciso_menu_types_intero);
		interoTab.setTabListener(new TabListener<TipologiaInteroFragment>(this,
				getString(R.string.iDeciso_tipologie_menu_fragment_intero),
				TipologiaInteroFragment.class, android.R.id.content));
		getSupportActionBar().addTab(interoTab);

		// ridotto tab
		Tab ridottoTab = getSupportActionBar().newTab();
		ridottoTab.setTabListener(new TabListener<TipologiaRidottoFragment>(
				this,
				getString(R.string.iDeciso_tipologie_menu_fragment_ridotto),
				TipologiaRidottoFragment.class, android.R.id.content));
		ridottoTab.setText(R.string.iDeciso_menu_types_ridotto);
		getSupportActionBar().addTab(ridottoTab);

		// snack tab
		Tab snackTab = getSupportActionBar().newTab();
		snackTab.setTabListener(new TabListener<TipologiaSnackFragment>(this,
				getString(R.string.iDeciso_tipologie_menu_fragment_snack),
				TipologiaSnackFragment.class, android.R.id.content));

		snackTab.setText(R.string.iDeciso_menu_types_snack);
		getSupportActionBar().addTab(snackTab);
		
		if (getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_TABS);
		}

		if (menu_compatibles != null) {

				for (String string : menu_compatibles) {
				if (string.equals(chosenMenu.Intero.toString())){
					interoTab.select();
					return;
				}else if (string.equals(chosenMenu.Ridotto1.toString())
						|| string.equals(chosenMenu.Ridotto2.toString())
						|| string.equals(chosenMenu.Ridotto3.toString())
						|| string.equals(chosenMenu.Ridotto4.toString())){
					ridottoTab.select();
					return;
				}else if (string.equals(chosenMenu.Snack1.toString())
						|| string.equals(chosenMenu.Snack2.toString())
						|| string.equals(chosenMenu.Snack3.toString())
						|| string.equals(chosenMenu.Snack4.toString())){

					snackTab.select();
					return;
				}
			}
		}



	}
}
