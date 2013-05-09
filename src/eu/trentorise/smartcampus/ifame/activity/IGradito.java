package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.connector.IGraditoConnector;
import eu.trentorise.smartcampus.ifame.model.PiattiList;
import eu.trentorise.smartcampus.ifame.model.Piatto;

public class IGradito extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito);

		ListView list_view = (ListView) findViewById(R.id.list_view_igradito);

		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Piatto p = (Piatto) adapter.getItemAtPosition(position);

				String ingredients = "Ingredienti: ";

				for (int i = 0; i < p.getPiatto_ingredients().length; i++) {
					ingredients += p.getPiatto_ingredients()[i] + " ";
				}
				ingredients += "!";
				Toast.makeText(IGradito.this, ingredients, Toast.LENGTH_LONG)
						.show();

			}
		});

		try {
			PiattiList pl = (PiattiList) new IGraditoConnector(
					getApplicationContext()).execute().get();

			setPiattiList(pl.getPiatti(), list_view);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.igradito, menu);
		return true;
	}

	private void setPiattiList(List<Piatto> plist, ListView lw) {
		/*
		 * List<String> pname = new ArrayList<String>(); Iterator i =
		 * plist.iterator();
		 * 
		 * while (i.hasNext()) { Piatto p = (Piatto) i.next();
		 * pname.add(p.getPiatto_name()); }
		 */
		Adapter a = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
				plist);

		lw.setAdapter((ListAdapter) a);
	}

}

class MyArrayAdapter extends ArrayAdapter<String> {

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public MyArrayAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i), i);
		}
	}

	@Override
	public long getItemId(int position) {
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
