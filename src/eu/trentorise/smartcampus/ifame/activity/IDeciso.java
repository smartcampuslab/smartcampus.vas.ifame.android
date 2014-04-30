package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import it.smartcampuslab.ifame.R;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;

public class IDeciso extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_ideciso);

		// popoliamo la listview
		String[] features = { getString(R.string.iDeciso_home_daily_menu),
				getString(R.string.iDeciso_home_menu_types),
				getString(R.string.iDeciso_home_compose_menu),
				getString(R.string.iDeciso_home_monthly_menu) };
		ArrayList<String> features_list = new ArrayList<String>();

		for (int i = 0; i < features.length; i++) {
			features_list.add(i, features[i]);
		}

		ListView list = (ListView) findViewById(R.id.ideciso_page_list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.layout_list_view_ideciso, features_list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i;
				switch (position) {
				case 0:
					IFameUtils.checkInitBeforeLaunchActivity(IDeciso.this,
							MenuDelGiornoActivity.class);
					break;
				case 1:
					i = new Intent(IDeciso.this, TipologieMenu.class);
					startActivity(i);
					break;
				case 2:
					i = new Intent(IDeciso.this, ComponiMenu.class);
					startActivity(i);
					break;
				case 3:
					IFameUtils.checkInitBeforeLaunchActivity(IDeciso.this,
							MenuDelMeseActivity.class);
					break;

				}
			}
		});

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
