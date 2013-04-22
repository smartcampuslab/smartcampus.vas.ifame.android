package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamMensa;

//import eu.trentorise.smartcampus.ifame.fragment.IFretta_Details;

public class IFretta extends Activity {
	
	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(IFretta.this, "HelloWorld", Toast.LENGTH_LONG).show();
	} */

	ArrayList<WebcamMensa> mensa_list; 
	ListView ifretta_listView; 
	private ArrayAdapter<WebcamMensa> adapter; 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta); 
		
		ifretta_listView = (ListView) findViewById(R.id.ifretta_page_list);
		mensa_list = new ArrayList<WebcamMensa>(); 
		
		int resID = android.R.layout.simple_list_item_1;
		adapter = new ArrayAdapter<WebcamMensa>(this, resID, mensa_list); 
		
		ifretta_listView.setAdapter(adapter);
		
		mensa_list.add(new WebcamMensa(Mensa.POVO_O, "LINK..."));
		mensa_list.add(new WebcamMensa(Mensa.POVO_1, "LINK..."));
		mensa_list.add(new WebcamMensa(Mensa.FBK, "LINK..."));
		mensa_list.add(new WebcamMensa(Mensa.TOMMASO_GAR, "LINK..."));
		mensa_list.add(new WebcamMensa(Mensa.XXIV_MAGGIO, "LINK..."));
		mensa_list.add(new WebcamMensa(Mensa.ZANELLA, "LINK..."));
		
		
		ifretta_listView.setOnItemClickListener(new OnItemClickListener() {

			   public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

				   Toast.makeText(IFretta.this, "Hello World", Toast.LENGTH_LONG).show(); 

			}
		});
	}	
}
