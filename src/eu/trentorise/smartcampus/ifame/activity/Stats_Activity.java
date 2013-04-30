package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;


public class Stats_Activity extends Activity {

	private TextView val_txt;
	private TextView time_txt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats_layout);	
		
		Bundle extras = getIntent().getExtras();
		if(extras == null){
			return; 
		}
		
		val_txt = (TextView) findViewById(R.id.val_textview); 
		time_txt = (TextView) findViewById(R.id.time_textview); 
		
		Object[] time_list = (Object[]) extras.get("time_value");
		Object[] val_list = (Object[]) extras.get("values");
		
		time_txt.setText("");
		for(int i = 0; i < time_list.length; i++){
			time_txt.append( String.valueOf(time_list[i]) + "\n");
		}
		 
		val_txt.setText(""); 
		for(int i = 0; i < val_list.length; i++){
			val_txt.append(val_list[i].toString() + "\n");
			
		}
	}

}
