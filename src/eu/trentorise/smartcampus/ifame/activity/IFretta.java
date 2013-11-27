package eu.trentorise.smartcampus.ifame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetMenseTask;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;

@Deprecated
public class IFretta extends SherlockActivity {

	/** Logging tag */
	@SuppressWarnings("unused")
	private static final String TAG = "iFretta";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		ListView mListViewMensa = (ListView) findViewById(R.id.ifretta_page_list);

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// initialize and setup the adapter with the listener
		MensaAdapter adapterMensaList = new MensaAdapter(this);
		mListViewMensa.setAdapter(adapterMensaList);
		mListViewMensa.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				Intent iFrettaDetails = new Intent(getApplicationContext(),
						IFrettaDetails.class);
				iFrettaDetails.putExtra(IFrettaDetails.MENSA,
						(Mensa) adapter.getItemAtPosition(position));
				startActivity(iFrettaDetails);
			}
		});

		// check for internet connection and than get the mense
		if (ConnectionUtils.isUserConnectedToInternet(getApplicationContext())) {
			// if this task has an error call finish() if this activity
			new GetMenseTask(this, adapterMensaList).execute();
		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			finish();
			return;
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
			// Mensa mensa = new Mensa(
			// "sasasaaaas",
			// "http://www.operauni.tn.it/upload/cms/456_x/mesiano-web-2.jpg",
			// "http://www.operauni.tn.it/upload/cms/456_x/mesiano-web-2.jpg");
			// Intent i = new Intent(this, IFrettaDetails.class);
			// i.putExtra(IFrettaDetails.MENSA, mensa);
			// startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
