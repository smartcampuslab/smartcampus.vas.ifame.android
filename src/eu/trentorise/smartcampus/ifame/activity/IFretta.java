package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamMensa;

//import eu.trentorise.smartcampus.ifame.fragment.IFretta_Details;

public class IFretta extends Activity {

	ArrayList<WebcamMensa> mensa_list;
	ListView ifretta_listView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		mensa_list = new ArrayList<WebcamMensa>();

		ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);

		MyArrayAdapter adapter = new MyArrayAdapter(this, R.layout.mensa_text,
				mensa_list);
		ifretta_listView.setAdapter(adapter);

		String url_povo_0 = "http://www.operauni.tn.it/cms-01.00/articolo.asp?IDcms=13737&s=279&l=IT";
		String url_povo_1 = "http://www.operauni.tn.it/upload/cms/456_x/mensa-povo2.jpg";
		String url_tom_gar = "http://www.operauni.tn.it/upload/cms/456_x/gar-offline.jpg";
		String url_zanella = "http://www.operauni.tn.it/upload/cms/456_x/mensa-zanella.jpg";
		String url_mesiano = "http://www.operauni.tn.it/upload/cms/456_x/mesiano-offline.jpg";

		mensa_list.add(0, new WebcamMensa(Mensa.POVO_O, url_povo_0));
		mensa_list.add(1, new WebcamMensa(Mensa.POVO_1, url_povo_1));
		mensa_list.add(2, new WebcamMensa(Mensa.FBK, "Webcam not available"));
		mensa_list.add(3, new WebcamMensa(Mensa.TOMMASO_GAR, url_tom_gar));
		mensa_list.add(4, new WebcamMensa(Mensa.XXIV_MAGGIO,
				"Webcam not available"));
		mensa_list.add(5, new WebcamMensa(Mensa.ZANELLA, url_zanella));
		mensa_list.add(6, new WebcamMensa(Mensa.MESIANO, url_mesiano));

		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {

				if (position == 0) {
					WebcamMensa val_at = mensa_list.get(0);

					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);
					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);
				}

				if (position == 1) {
					WebcamMensa val_at = mensa_list.get(1);

					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);
					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);

				}

				if (position == 2) {
					WebcamMensa val_at = mensa_list.get(2);

					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);
					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);

				}

				if (position == 3) {
					WebcamMensa val_at = mensa_list.get(3);

					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);

					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);

				}

				if (position == 4) {
					WebcamMensa val_at = mensa_list.get(4);

					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);
					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);

				}

				if (position == 5) {
					WebcamMensa val_at = mensa_list.get(5);
					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);
					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);

				}

				if (position == 6) {
					WebcamMensa val_at = mensa_list.get(6);

					Intent i = new Intent(IFretta.this, IFretta_Details.class);
					//String mensa = String.valueOf(val_at);
					i.putExtra("mensa", val_at.getTipo_mensa());
					startActivity(i);
				}
			}
		});

	}

	class MyArrayAdapter extends ArrayAdapter<WebcamMensa> {

		// HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public MyArrayAdapter(Context context, int textViewResourceId,
				List<WebcamMensa> objects) {
			super(context, textViewResourceId, objects);

			// for (int i = 0; i < objects.size(); ++i) {
			// mIdMap.put(objects.get(i), i);
			// }
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.mensa_text, null);

			TextView nome_mensa = (TextView) convertView
					.findViewById(R.id.mensa_nameView);
			//TextView url_mensa = (TextView) convertView
				//	.findViewById(R.id.mensa_urlView);

			WebcamMensa w_mensa = getItem(position);
			nome_mensa.setText(w_mensa.getTipo_mensa() + " ");
			//url_mensa.setText(w_mensa.getLink_webcam() + " ");
			return convertView;
		}
	}
}
