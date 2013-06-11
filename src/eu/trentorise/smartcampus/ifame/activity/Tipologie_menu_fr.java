package eu.trentorise.smartcampus.ifame.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.tabs.TabListener;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaInteroFragment;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaRidottoFragment;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaSnackFragment;

public class Tipologie_menu_fr extends SherlockFragmentActivity {

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean from_componi = getIntent().getBooleanExtra(
				Fai_il_tuo_menu.HAS_CALLED_TIPOLOGIE, false);
		String selected_menu = null;
		
		if (from_componi) {
			selected_menu = getIntent().getStringExtra(
					Fai_il_tuo_menu.SELECTED_MENU);
			getIntent().removeExtra(Fai_il_tuo_menu.SELECTED_MENU);
		}
		else {
			getIntent().removeExtra(Fai_il_tuo_menu.HAS_CALLED_TIPOLOGIE);
			getIntent().removeExtra(Fai_il_tuo_menu.SELECTED_MENU);
		}
		
		// getSupportActionBar().setDisplayShowHomeEnabled(false);
		// getSupportActionBar().setDisplayShowTitleEnabled(true);
		setContentView(R.layout.empty_layout);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab interoTab = getSupportActionBar().newTab();
		interoTab.setText(R.string.iFame_intero);
		interoTab.setTabListener(new TabListener<TipologiaInteroFragment>(this,
				"tipologiaInteroFragment", TipologiaInteroFragment.class,
				android.R.id.content));
		getSupportActionBar().addTab(interoTab);

		Tab ridottoTab = getSupportActionBar().newTab();
		ridottoTab.setTabListener(new TabListener<TipologiaRidottoFragment>(
				this, "tipologiaRidottoFragment",
				TipologiaRidottoFragment.class, android.R.id.content));
		ridottoTab.setText(R.string.iFame_ridotto);
		getSupportActionBar().addTab(ridottoTab);

		Tab snackTab = getSupportActionBar().newTab();
		snackTab.setTabListener(new TabListener<TipologiaSnackFragment>(this,
				"tipologiaSnackFragment", TipologiaSnackFragment.class,
				android.R.id.content));

		snackTab.setText(R.string.iFame_snack);
		getSupportActionBar().addTab(snackTab);

		
		if (from_componi && selected_menu != null){

			if (selected_menu.equals("Intero"))
				interoTab.select();

			else if (selected_menu.equals("Ridotto1")
					|| selected_menu.equals("Ridotto2")
					|| selected_menu.equals("Ridotto3")
					|| selected_menu.equals("Ridotto4")
					|| selected_menu.equals("Ridotto12")
					|| selected_menu.equals("Ridotto1234"))
				ridottoTab.select();

			else if (selected_menu.equals("Snack1")
					|| selected_menu.equals("Snack2")
					|| selected_menu.equals("Snack3")
					|| selected_menu.equals("Snack4")
					|| selected_menu.equals("Snack12"))

				snackTab.select();

		}
		if (getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_TABS);
		}

	}
}
