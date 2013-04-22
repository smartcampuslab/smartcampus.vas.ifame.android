package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import eu.trentorise.smartcampus.ifame.R;

public class IGradito extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito);

		String[] values = { "Pasta Aglio olio e peperoncino", "Pasta al ragu",
				"Risotto ai funghi", "Scaloppine al limone",
				"Ossobuco alla romana", "Fagioli", "Patatine fritte",
				"Stinco con Patate", "Anatre all'arancia",
				"Insalata di stagione" };
		
		final ArrayList<String> food_list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			food_list.add(values[i]);
		}
		ListView list_view = (ListView) findViewById(R.id.list_view_igradito);
		
		final MyArrayAdapter adapter = new MyArrayAdapter(this,
				android.R.layout.simple_list_item_1, food_list);
		list_view.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.igradito, menu);
		return true;
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
