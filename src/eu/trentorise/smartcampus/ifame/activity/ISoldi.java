package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;

public class ISoldi extends Activity {

	TextView centerText;
	TextView bottomText;
	ListView isoldi_listview; 
	ArrayList<String> acquisti_possibili; 
	private ArrayAdapter<String> adapter; 

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_isoldi);
		setContentView(R.layout.layout_isoldi);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		float card_value = (Float) extras.get("iSoldi");

		centerText = (TextView) findViewById(R.id.isoldi_center_text);
		bottomText = (TextView) findViewById(R.id.isoldi_bottom_text);
		isoldi_listview = (ListView) findViewById(R.id.isoldi_listview);
		
		acquisti_possibili = new ArrayList<String>(); 
		
		int resID = android.R.layout.simple_list_item_1;
		adapter = new ArrayAdapter<String>(this, resID, acquisti_possibili); 
		
		isoldi_listview.setAdapter(adapter);

		getAmount(card_value);
	}

	public void getAmount(float amount) {
		if (amount >= 4.90) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#228B22"));

			bottomText.setText("Puoi acquistare:  ");
			bottomText.setTextColor(Color.parseColor("#228B22")); 
		 
			acquisti_possibili.add("Intero"); 
			acquisti_possibili.add("Ridotto"); 
			acquisti_possibili.add("Snack"); 
			adapter.notifyDataSetChanged();
			
			
		} else if (amount >= 4.20 && amount < 4.90) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#D2691E"));

			bottomText.setText("Puoi acquistare:  ");
			bottomText.setTextColor(Color.parseColor("#D2691E")); 
			
			acquisti_possibili.add("Ridotto"); 
			acquisti_possibili.add("Snack"); 
			adapter.notifyDataSetChanged();
			
			
		} else if (amount >= 2.90 && amount < 4.20) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#D2691E"));

			bottomText
					.setText("Puoi acquistare:  ");
			bottomText.setTextColor(Color.parseColor("#D2691E")); 
			
			acquisti_possibili.add("Snack"); 
			adapter.notifyDataSetChanged();
		} else {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FF0000"));

			bottomText
					.setText("No puoi acquistare nessuna tipologia di menu");
			bottomText.setTextColor(Color.parseColor("#FF0000")); 
			
		}
	}
}
