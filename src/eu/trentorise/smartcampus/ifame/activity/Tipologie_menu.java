package eu.trentorise.smartcampus.ifame.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import eu.trentorise.smartcampus.ifame.R;

public class Tipologie_menu extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tipologie_menu);
		ScrollView scroll = (ScrollView)findViewById(R.id.iDeciso_tipologie_main_scrollview);

		Intent i = getIntent();
		String selected_item = i.getStringExtra("selected_item");
		
		Intent intent;
		TabSpec spec;

		intent = new Intent(this, TipologiaIntero.class);
		TabHost menu = (TabHost) findViewById(android.R.id.tabhost);
		spec = menu.newTabSpec("Intero").setIndicator(createTabIndicator(getString(R.string.iDeciso_menu_types_intero))).setContent(intent);
		menu.addTab(spec);
		
		intent = new Intent(this, TipologiaRidotto.class);
		spec = menu.newTabSpec("Ridotto").setIndicator(createTabIndicator(getString(R.string.iDeciso_menu_types_ridotto))).setContent(intent);
		menu.addTab(spec);
		
		intent = new Intent(this, TipologiaSnack.class);
		spec = menu.newTabSpec("Snack").setIndicator(createTabIndicator(getString(R.string.iDeciso_menu_types_snack))).setContent(intent);
		menu.addTab(spec);

		if (selected_item != null) {

			if (selected_item.equals("Intero")) {
				menu.setCurrentTab(0);
			} else if (selected_item.equals("Ridotto")) {
				menu.setCurrentTab(1);
			} else {
				menu.setCurrentTab(2);
			}
		}
		
		scroll.scrollTo(0, 0);
		scroll.scrollBy(0, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipologie_menu, menu);
		return true;

	}
	
	//per eventuali modifiche di layout del tab agire qui
	private View createTabIndicator(String text) {
	    Button button = new Button(this);
	    button.setBackgroundResource(R.color.sc_dark_gray);
	    button.setTextSize(14);
	    button.setText(text);
	    button.setTextColor(Color.WHITE);
	    button.setGravity(Gravity.CENTER);
	    return button;
	}


}
