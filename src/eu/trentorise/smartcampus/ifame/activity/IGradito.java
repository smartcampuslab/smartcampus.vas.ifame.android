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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.PiattiList;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.model.PiattoKcal;
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

	private class IGraditoConnector extends AsyncTask<Void, Void, PiattiList> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public IGraditoConnector(Context applicationContext) {
			context = applicationContext;
		}

		private PiattiList getPiattiList() {

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

					PiattiList list = Utils.convertJSONToObject(body,
							PiattiList.class);

					return list;

				} else {

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
			return getPiattiList();
		}

		@Override
		protected void onPostExecute(PiattiList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			ListAdapter a = new ArrayAdapter<String>(IGradito.this,
					android.R.layout.simple_list_item_1, result.getPiatti());

			list_view.setAdapter(a);
			pd.dismiss();
		}

	}

	// private class MyArrayAdapter extends ArrayAdapter<PiattoKcal> {
	//
	// public MyArrayAdapter(Context context, int textViewResourceId,
	// List<PiattoKcal> objects) {
	// super(context, textViewResourceId, objects);
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	//
	// LayoutInflater inflater = (LayoutInflater) getContext()
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//
	// convertView = inflater
	// .inflate(layout.layout_row_menu_adapter, null);
	//
	// PiattoKcal p = getItem(position);
	//
	// TextView name = (TextView) convertView
	// .findViewById(R.id.menu_name_adapter);
	// TextView kcal = (TextView) convertView
	// .findViewById(R.id.menu_kcal_adapter);
	//
	// name.setText(p.getPiatto());
	// kcal.setText("");
	//
	// return convertView;
	// }
	// }
}
