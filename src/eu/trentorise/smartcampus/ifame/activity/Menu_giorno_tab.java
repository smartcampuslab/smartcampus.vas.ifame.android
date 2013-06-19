package eu.trentorise.smartcampus.ifame.activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.tabs.MenuGiornoAlternativeFragment;
import eu.trentorise.smartcampus.ifame.tabs.MenuGiornoFragment;
import eu.trentorise.smartcampus.ifame.tabs.TabListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
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
		dailyMenuTab.setText(R.string.title_activity_menu_giorno);
		dailyMenuTab.setTabListener(new TabListener<MenuGiornoFragment>(this,
				getString(R.string.iDeciso_daily_menu_daily_fragment), MenuGiornoFragment.class,
				android.R.id.content));
		getSupportActionBar().addTab(dailyMenuTab);
		
		
		Tab alternativeMenuTab = getSupportActionBar().newTab();
		alternativeMenuTab.setText(R.string.title_activity_menu_giorno_alternative);
		alternativeMenuTab.setTabListener(new TabListener<MenuGiornoAlternativeFragment>(this,
				getString(R.string.iDeciso_daily_menu_alternatives_fragment), MenuGiornoAlternativeFragment.class, android.R.id.content));
		getSupportActionBar().addTab(alternativeMenuTab);
		
		if (getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_TABS);
		}
		
		
		
		
		
	}

}
