package eu.trentorise.smartcampus.ifame.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import eu.trentorise.smartcampus.ifame.R;

public class Tipologie_menu extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tipologie_menu);
		
		Intent i = getIntent();
		String selected_item = i.getStringExtra("selected_item");
		
		TabHost menu = (TabHost)findViewById(android.R.id.tabhost);
        TabSpec spec = menu.newTabSpec("tabcontent");
        spec.setContent(R.id.menu_intero_btn);
        spec.setIndicator("Intero");
        menu.addTab(spec);
        spec = menu.newTabSpec("menu_ridotto_btn");
        spec.setContent(R.id.menu_ridotto_btn);
        spec.setIndicator("Ridotto");
        menu.addTab(spec);
        spec = menu.newTabSpec("menu_snack_btn");
        spec.setContent(R.id.menu_snack_btn);
        spec.setIndicator("Snack");
        menu.addTab(spec);
		
        
        if (selected_item!=null){
        	
        	if (selected_item.equals("Intero")){
        		menu.setCurrentTab(0);}
        	else if (selected_item.equals("Ridotto")){menu.setCurrentTab(1);}
        	else {menu.setCurrentTab(2);}
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipologie_menu, menu);
		return true;
		
	}

}
