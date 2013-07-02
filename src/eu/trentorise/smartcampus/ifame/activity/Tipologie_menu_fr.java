package eu.trentorise.smartcampus.ifame.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.tabs.TabListener;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaInteroFragment;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaRidottoFragment;
import eu.trentorise.smartcampus.ifame.tabs.TipologiaSnackFragment;

public class Tipologie_menu_fr extends SherlockFragmentActivity {

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.tipologie_menu_fr, menu);
	    return true;
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

		boolean from_componi = getIntent().getBooleanExtra(
				Fai_il_tuo_menu.HAS_CALLED_TIPOLOGIE, false);
		String selected_menu = null;
		
		if (from_componi) {
			selected_menu = getIntent().getStringExtra(
					Fai_il_tuo_menu.SELECTED_MENU);
		}

		
		
		// getSupportActionBar().setDisplayShowHomeEnabled(false);
		// getSupportActionBar().setDisplayShowTitleEnabled(true);
		setContentView(R.layout.empty_layout);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab interoTab = getSupportActionBar().newTab();
		interoTab.setText(R.string.iDeciso_menu_types_intero);
		interoTab.setTabListener(new TabListener<TipologiaInteroFragment>(this,
				getString(R.string.iDeciso_tipologie_menu_fragment_intero), TipologiaInteroFragment.class,
				android.R.id.content));
		getSupportActionBar().addTab(interoTab);

		Tab ridottoTab = getSupportActionBar().newTab();
		ridottoTab.setTabListener(new TabListener<TipologiaRidottoFragment>(
				this, getString(R.string.iDeciso_tipologie_menu_fragment_ridotto),
				TipologiaRidottoFragment.class, android.R.id.content));
		ridottoTab.setText(R.string.iDeciso_menu_types_ridotto);
		getSupportActionBar().addTab(ridottoTab);

		Tab snackTab = getSupportActionBar().newTab();
		snackTab.setTabListener(new TabListener<TipologiaSnackFragment>(this,
				getString(R.string.iDeciso_tipologie_menu_fragment_snack), TipologiaSnackFragment.class,
				android.R.id.content));

		snackTab.setText(R.string.iDeciso_menu_types_snack);
		getSupportActionBar().addTab(snackTab);

		
		if (/*from_componi && */selected_menu != null){

			if (selected_menu.equals(getString(R.string.iDeciso_menu_types_intero)))
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
