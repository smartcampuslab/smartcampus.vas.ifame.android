package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog;
import eu.trentorise.smartcampus.ifame.dialog.OptionsMenuDialog;
import eu.trentorise.smartcampus.ifame.dialog.OptionsMenuDialog.OptionsMenuDialogListener;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.tabs.MenuGiornoAlternativeFragment;
import eu.trentorise.smartcampus.ifame.tabs.MenuGiornoFragment;
import eu.trentorise.smartcampus.ifame.tabs.TabListener;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;

public class MenuDelGiornoActivity extends SherlockFragmentActivity implements
		OptionsMenuDialogListener {

	private OptionsMenuDialog optionsDialog;
	private InsertReviewDialog insertReviewDialog;

	private MenuItem refreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.empty_layout);

		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String dateStringTitle = dateFormat.format(new Date());
		setTitle(dateStringTitle);

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
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// just to be sure to have it before add the review
		UserIdUtils.retrieveAndSaveUserId(MenuDelGiornoActivity.this);
	}

	public MenuItem getRefreshButton() {
		return refreshButton;
	}

	private void setRefreshButton(MenuItem refreshButton) {
		this.refreshButton = refreshButton;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_only_loading_progress,
				menu);
		setRefreshButton(menu.findItem(R.id.action_refresh));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);

	}

	/** display dialog options */
	public void showOptionsDialog(Piatto piatto) {

		if (optionsDialog == null) {
			optionsDialog = new OptionsMenuDialog();
		}

		Bundle args = new Bundle();
		args.putSerializable(OptionsMenuDialog.PIATTO, piatto);

		optionsDialog.setArguments(args);
		optionsDialog.show(getSupportFragmentManager(), "OptionsMenuDialog");
	}

	/**
	 * Show dialog for insert or edit a review
	 */
	private void showInsertReviewDialog(Piatto piatto) {

		if (insertReviewDialog == null) {
			insertReviewDialog = new InsertReviewDialog();
		}

		// put the data needed for showing the dialog in a bundle
		Bundle args = new Bundle();
		args.putSerializable(InsertReviewDialog.PIATTO, piatto);
		args.putInt(InsertReviewDialog.VOTO, 5);
		args.putString(InsertReviewDialog.COMMENTO, "");

		// pass the bundle to the dialog and show
		insertReviewDialog.setArguments(args);
		insertReviewDialog.show(getSupportFragmentManager(),
				"insertReviewDialog");
	}

	@Override
	public void onClickOptionsMenuDialog(DialogInterface dialog, int position,
			Piatto piatto) {
		switch (position) {

		case OptionsMenuDialog.VIEW_REVIEW:
			Intent viewReviews = new Intent(MenuDelGiornoActivity.this,
					IGraditoVisualizzaRecensioni.class);
			viewReviews.putExtra(IGraditoVisualizzaRecensioni.PIATTO, piatto);
			startActivity(viewReviews);
			break;

		case OptionsMenuDialog.RATE_OR_REVIEW:
			showInsertReviewDialog(piatto);
			break;

		case OptionsMenuDialog.SEARCH_GOOGLE:
			Intent searchGoogle = new Intent(Intent.ACTION_WEB_SEARCH);
			searchGoogle.putExtra(SearchManager.QUERY, piatto.getPiatto_nome());
			startActivity(searchGoogle);
			break;

		default:
			break;
		}

	}

}
