package eu.trentorise.smartcampus.ifame.activity;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.connector.IFrettaConnector;
import eu.trentorise.smartcampus.ifame.model.ListaMense;
import eu.trentorise.smartcampus.ifame.model.Mensa;

//import eu.trentorise.smartcampus.ifame.fragment.IFretta_Details;

public class IFretta extends Activity {

	/*
	 * public final String url_povo_0 =
	 * "http://www.operauni.tn.it/upload/Webcam/Povo01.jpg"; public final String
	 * url_povo_1 = "http://www.operauni.tn.it/upload/Webcam/Povo02.jpg"; public
	 * final String url_tom_gar =
	 * "http://www.operauni.tn.it/upload/Webcam/MensaUni.jpg"; public final
	 * String url_zanella =
	 * "http://www.operauni.tn.it/upload/Webcam/mensa_zanella.jpg"; public final
	 * String url_mesiano =
	 * "http://www.operauni.tn.it/upload/Webcam/MensaMes01.jpg";
	 * 
	 * // ArrayList<WebcamMensa> mensa_list; ListView ifretta_listView;
	 */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		try {
			ListaMense list = (ListaMense) new IFrettaConnector(
					getApplicationContext()).execute().get();

			if (list != null) {
				createWebcamList(list);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createWebcamList(ListaMense list) {
		ListView ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);

		List<Mensa> mense = list.getList();

		MyArrayAdapter adapter = new MyArrayAdapter(this,
				R.layout.layout_list_view_ideciso, mense);

		// MyArrayAdapter adapter = new MyArrayAdapter(this,
		// R.layout.mensa_text, list.getList());

		ifretta_listView.setAdapter(adapter);
		
		

		int rows = adapter.getCount();
		int height = 60 * rows;
		ViewGroup.LayoutParams params = ifretta_listView.getLayoutParams();
		params.height = height;
		ifretta_listView.setLayoutParams(params);
		ifretta_listView.requestLayout();

		/*
		 * mensa_list = new ArrayList<WebcamMensa>();
		 * 
		 * ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);
		 * 
		 * MyArrayAdapter adapter = new MyArrayAdapter(this,
		 * R.layout.mensa_text, mensa_list);
		 * ifretta_listView.setAdapter(adapter);
		 * 
		 * mensa_list.add(0, new WebcamMensa(Mensa.POVO_O, url_povo_0));
		 * mensa_list.add(1, new WebcamMensa(Mensa.POVO_1, url_povo_1));
		 * mensa_list.add(2, new WebcamMensa(Mensa.FBK, "")); mensa_list.add(3,
		 * new WebcamMensa(Mensa.TOMMASO_GAR, url_tom_gar)); mensa_list.add(4,
		 * new WebcamMensa(Mensa.XXIV_MAGGIO, "")); mensa_list.add(5, new
		 * WebcamMensa(Mensa.ZANELLA, url_zanella)); mensa_list.add(6, new
		 * WebcamMensa(Mensa.MESIANO, url_mesiano));
		 */

		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				Mensa m = (Mensa) adapter.getItemAtPosition(position);

				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", m.getMensa_name());
				i.putExtra("img_url", m.getMensa_link());
				startActivity(i);

			}
		});

	}

	private class MyArrayAdapter extends ArrayAdapter<Mensa> {

		public MyArrayAdapter(Context context, int textViewResourceId,
				List<Mensa> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.mensa_text, null);

			TextView nome_mensa = (TextView) convertView.findViewById(R.id.mensa_nameView);
			Mensa m = getItem(position); 
			
			nome_mensa.setText(m.getMensa_name()); 

			return convertView;
		}

	}
}
