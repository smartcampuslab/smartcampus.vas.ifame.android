package eu.trentorise.smartcampus.ifame.activity;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
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

		Adapter adapter = new WebArrayAdapter(this,
				android.R.layout.simple_list_item_1, list.getList());

		ifretta_listView.setAdapter((ListAdapter) adapter);

		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				Mensa m = (Mensa) adapter.getItemAtPosition(position);
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", m.getMensa_name());
				i.putExtra("img_url", m.getMensa_link());
				startActivity(i);
			}
		});
	}
}

class WebArrayAdapter extends ArrayAdapter<Mensa> {

	// HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
	Context context;
	List<Mensa> mlist;

	public WebArrayAdapter(Context context, int textViewResourceId,
			List<Mensa> mlist) {
		super(context, textViewResourceId, mlist);
		this.context = context;
		this.mlist = mlist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView tv = new TextView(context);
		// setPadding(top, left, right, bottom)
		tv.setPadding(10, 20, 10, 20);
		tv.setTextSize(15);
		tv.setText(mlist.get(position).getMensa_name());

		return tv;
	}
}
