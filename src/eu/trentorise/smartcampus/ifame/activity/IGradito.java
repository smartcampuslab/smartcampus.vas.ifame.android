package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.PiattiList;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class IGradito extends Activity {

	ListView list_view;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito);

		pd = new ProgressDialog(IGradito.this).show(IGradito.this, "iGradito",
				"Loading...");

		list_view = (ListView) findViewById(R.id.list_view_igradito);

		/*
		 * 
		 * 
		 * 
		 * SOLO PER VEDERE GLI INGREDIENTI FAKE IN UN TOAST
		 */
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

		new IGraditoConnector(IGradito.this).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.igradito, menu);
		return true;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * CONNECTOR TO GETALLPIATTI WEB SERVICE
	 */

	public class IGraditoConnector extends AsyncTask<Void, Void, PiattiList> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IGraditoConnector(Context applicationContext) {
			context = applicationContext;
		}

		private PiattiList getPiatti() {
			mProtocolCarrier = new ProtocolCarrier(context, appToken);
			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getallpiatti");
			request.setMethod(Method.GET);
			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);
				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
					PiattiList pl = Utils.convertJSONToObject(body,
							PiattiList.class);
					return pl;
				}
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected PiattiList doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return getPiatti();
		}

		@Override
		protected void onPostExecute(PiattiList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Adapter a = new ArrayAdapter(IGradito.this,
					android.R.layout.simple_list_item_1, result.getPiatti());

			list_view.setAdapter((ListAdapter) a);
			pd.dismiss();
		}

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
