package eu.trentorise.smartcampus.ifame.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.IGraditoPiattoListAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetPiattiIGraditoTask;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;

/**
 * This Activity shows the list of dishes for a given mensa
 */

public class IGradito extends SherlockActivity {

	private IGraditoPiattoListAdapter piattiListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito_piattimensa);

		// create the adapter
		// mensaAdapter = new MensaAdapter(IGradito.this);
		piattiListAdapter = new IGraditoPiattoListAdapter(IGradito.this);

		
		
		if (IFameUtils.isUserConnectedToInternet(getApplicationContext())) {
			new GetPiattiIGraditoTask(IGradito.this, piattiListAdapter)
					.execute();
		} else {
			Toast.makeText(this,
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		// setup the listview
		ListView piattiListView = (ListView) findViewById(R.id.list_view_igradito);
		piattiListView.setAdapter(piattiListAdapter);
		piattiListView.setFastScrollEnabled(true);
		piattiListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// this check piatto name is because there are headers item in
				// the list that have one character piatto name so we don't have
				// to start next activity by clicking on them
				Piatto piatto = (Piatto) adapter.getItemAtPosition(position);
				if ((piatto.getPiatto_nome().length()) != 1) {
					Intent intent = new Intent(IGradito.this,
							IGraditoVisualizzaRecensioni.class);
					intent.putExtra(IGraditoVisualizzaRecensioni.PIATTO, piatto);
					startActivity(intent);
				}
			}
		});

		// setup supportActionBar (Sherlock) is initialized in super.onCreate()
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.igradito, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		final SearchView searchViewActionBar = (SearchView) menu.findItem(
				R.id.igradito_search).getActionView();

		if (null != searchManager) {
			searchViewActionBar.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchViewActionBar.setIconifiedByDefault(false);
		}
		
		getSupportActionBar().setIcon(R.drawable.ifame_logo); 

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				// this is your adapter that will be filtered
				piattiListAdapter.getFilter().filter(newText);
				return true;
			}

			@SuppressLint("NewApi")
			@Override
			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				piattiListAdapter.getFilter().filter(query);
				searchViewActionBar.clearFocus();
				return true;
			}
		};
		searchViewActionBar.setOnQueryTextListener(queryTextListener);

		return super.onCreateOptionsMenu(menu);
	}

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
}