package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
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
		setContentView(R.layout.empty_layout);

		// setup the sctionbar
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_TABS);
		}

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

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		// gestione chiamata da componi il tuo menu
		ArrayList<String> menu_compatibles = extras
				.getStringArrayList(ComponiMenu.MENU_COMPATIBLES);

		HashMap<String, Boolean> mapCheckedItems = (HashMap<String, Boolean>) extras
				.getSerializable(ComponiMenu.MENU_CHECKED_TRUE);

		// gestione chiamata da isoldi
		String callFromISoldi = extras.getString(ISoldi.SELECTED_MENU);

		if (menu_compatibles != null) {

			String lastMenuCompatible = menu_compatibles.get(menu_compatibles
					.size() - 1);

			if (lastMenuCompatible.equals(chosenMenu.Snack1.toString())
					|| lastMenuCompatible.equals(chosenMenu.Snack2.toString())
					|| lastMenuCompatible.equals(chosenMenu.Snack3.toString())
					|| lastMenuCompatible.equals(chosenMenu.Snack4.toString())) {

				snackTab.select();

			} else if (lastMenuCompatible
					.equals(chosenMenu.Ridotto1.toString())
					|| lastMenuCompatible
							.equals(chosenMenu.Ridotto2.toString())
					|| lastMenuCompatible
							.equals(chosenMenu.Ridotto3.toString())
					|| lastMenuCompatible
							.equals(chosenMenu.Ridotto4.toString())) {

				ridottoTab.select();

			} else if (lastMenuCompatible.equals(chosenMenu.Intero.toString())) {

				interoTab.select();
			}
			// end if (menu_compatibles != null)
		} else if (callFromISoldi != null) {

			if (callFromISoldi.equals(ISoldi.RIDOTTO)) {
				ridottoTab.select();

			} else if (callFromISoldi.equals(ISoldi.SNACK)) {
				snackTab.select();

			} else if (callFromISoldi.equals(ISoldi.INTERO)) {
				interoTab.select();
			}

		}
	}
}
