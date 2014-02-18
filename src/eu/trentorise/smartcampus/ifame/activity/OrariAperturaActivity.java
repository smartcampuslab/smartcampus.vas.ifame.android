package eu.trentorise.smartcampus.ifame.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.OrariAdapter;

public class OrariAperturaActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta_orari);
		Intent data = getIntent();
		ArrayList<String> aperture = data.getStringArrayListExtra("aperture");
		ArrayList<String> openingFiltered = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		
		
		for (String op : aperture) {
			try {
				Date stringToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(op);
				if(stringToDate.after(date)){
					openingFiltered.add(op);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		}
		
		ListView lvorari = (ListView) findViewById(R.id.list_view_orari);
		OrariAdapter adapter = new OrariAdapter(getApplicationContext(), openingFiltered);
		lvorari.setAdapter(adapter);
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