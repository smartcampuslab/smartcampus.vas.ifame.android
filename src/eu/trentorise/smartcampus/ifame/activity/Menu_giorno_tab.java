package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import eu.trentorise.smartcampus.ifame.R;

public class Menu_giorno_tab extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_giorno_tab);

		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;

//		Calendar cal = Calendar.getInstance();
//		Date date = cal.getTime();
//		int year = date.getYear() + 1900;
//		setTitle(date.getDay() + "-" + date.getMonth() + "/" + year);
		
		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String daily_menu = s.format(new Date());
		//Set the title of the action bar to the current date
		setTitle(daily_menu);

		Intent intent = new Intent(this, Menu_giorno.class);
		spec = tabHost.newTabSpec("MENU DEL GIORNO").setIndicator(createTabIndicator("MENU DEL GIORNO")).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent(this, Menu_giorno_alternative.class);
		spec = tabHost.newTabSpec("ALTERNATIVE").setIndicator(createTabIndicator("ALTERNATIVE")).setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno_tab, menu);
		return true;
	}


	
	//per eventuali modifiche di layout del tab agire qui
	private View createTabIndicator(String text) {
	    Button button = new Button(this);
	    button.setBackgroundResource(R.color.sc_dark_gray);
	    button.setTypeface(null,Typeface.BOLD);
	    button.setTextSize(12);
	    button.setText(text);
	    button.setTextColor(Color.WHITE);
	    button.setGravity(Gravity.CENTER);
	    return button;
	}

	
}
