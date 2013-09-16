package eu.trentorise.smartcampus.ifame.activity;

/*
 * 
 * sherlock activity con tabs (2)
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.tabs.MenuGiornoAlternativeFragment;
import eu.trentorise.smartcampus.ifame.tabs.MenuGiornoFragment;
import eu.trentorise.smartcampus.ifame.tabs.TabListener;

public class Menu_giorno_tab extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_layout);

		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String daily_menu = s.format(new Date());
		setTitle(daily_menu);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab dailyMenuTab = getSupportActionBar().newTab();
		dailyMenuTab.setText(R.string.iDeciso_home_daily_menu);
		dailyMenuTab.setTabListener(new TabListener<MenuGiornoFragment>(this,
				getString(R.string.iDeciso_daily_menu_daily_fragment),
				MenuGiornoFragment.class, android.R.id.content));
		getSupportActionBar().addTab(dailyMenuTab);

		Tab alternativeMenuTab = getSupportActionBar().newTab();
		alternativeMenuTab
				.setText(R.string.iDeciso_alternatives_title_activity);
		alternativeMenuTab
				.setTabListener(new TabListener<MenuGiornoAlternativeFragment>(
						this,
						getString(R.string.iDeciso_daily_menu_alternatives_fragment),
						MenuGiornoAlternativeFragment.class,
						android.R.id.content));
		getSupportActionBar().addTab(alternativeMenuTab);

		if (getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_TABS);
		}

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

}
