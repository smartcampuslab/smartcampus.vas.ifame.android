package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MensaAdapter;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

public class IFretta extends SherlockActivity {

	// public final static String IS_THE_FIRST_TIME = "first_time_or_update";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		ListView mListViewMensa = (ListView) findViewById(R.id.ifretta_page_list);

		// setup actionbar (supportActionBar is initialized in super.onCreate())
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// initialize and setup the adapter with the listener
		final MensaAdapter adapterMensaList = new MensaAdapter(this);
		mListViewMensa.setAdapter(adapterMensaList);
		mListViewMensa.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {

				MensaUtils.setFavouriteMensa(IFretta.this,
						(Mensa) adapter.getItemAtPosition(position));
				adapterMensaList.notifyDataSetChanged();
			}
		});

		ArrayList<Mensa> lista = MensaUtils.getMensaList(this);
		for (Mensa mensa : lista) {
			adapterMensaList.add(mensa);
		}
		adapterMensaList.notifyDataSetChanged();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
