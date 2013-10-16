package eu.trentorise.smartcampus.ifame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaListAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.IFrettaConnector;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;

public class IFretta extends SherlockActivity {

	/** Logging tag */
	@SuppressWarnings("unused")
	private static final String TAG = "iFretta";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		ListView mListViewMensa = (ListView) findViewById(R.id.ifretta_page_list);

		MensaListAdapter mAdapterMensaList = new MensaListAdapter(this);

		mListViewMensa.setAdapter(mAdapterMensaList);
		mListViewMensa.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {

				Intent iFrettaDetailsIntent = new Intent(
						getApplicationContext(), IFrettaDetails.class);
				iFrettaDetailsIntent.putExtra(IFrettaDetails.MENSA_EXTRA,
						(Mensa) adapter.getItemAtPosition(position));
				startActivity(iFrettaDetailsIntent);
			}
		});

		// supportActionBar (Sherlock) is initialized in super.onCreate()
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// check for internet connection and get the mense
		if (ConnectionUtils.isOnline(getApplicationContext())) {
			new IFrettaConnector(IFretta.this, mAdapterMensaList).execute();
		} else {
			ConnectionUtils.showToastNotConnected(this);
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// NOT YET IMPLEMENTED FAVOURITE MENSA FUNCTIONALITY
		// if (adapter != null) {
		// // nel caso in cui si cambi la mensa preferita in "ifretta details"
		// // questo comando assicura che si apportino le opportune modifiche
		// adapter.notifyDataSetChanged();
		// }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

}
