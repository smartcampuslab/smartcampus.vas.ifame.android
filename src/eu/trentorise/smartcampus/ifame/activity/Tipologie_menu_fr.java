package eu.trentorise.smartcampus.ifame.activity;

import android.os.Bundle;
import android.view.MenuItem;

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

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.empty_layout);

		Tab interoTab = getSupportActionBar().newTab();
		interoTab.setText(R.string.iDeciso_menu_types_intero);
		interoTab.setTabListener(new TabListener<TipologiaInteroFragment>(this,
				"tipologiaInteroFragment", TipologiaInteroFragment.class,
				android.R.id.content));
		getSupportActionBar().addTab(interoTab);

		
		
		
		
		Tab ridottoTab = getSupportActionBar().newTab();
		ridottoTab.setTabListener(new TabListener<TipologiaRidottoFragment>(this,
				"tipologiaRidottoFragment", TipologiaRidottoFragment.class,
				android.R.id.content));
		ridottoTab.setText(R.string.iDeciso_menu_types_ridotto);
		getSupportActionBar().addTab(ridottoTab);

		Tab snackTab = getSupportActionBar().newTab();

		snackTab.setTabListener(new TabListener<TipologiaSnackFragment>(this,
				"tipologiaSnackFragment", TipologiaSnackFragment.class,
				android.R.id.content));

		snackTab.setText(R.string.iDeciso_menu_types_snack);
		getSupportActionBar().addTab(snackTab);

		if (getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_TABS);
		}

	}

}
