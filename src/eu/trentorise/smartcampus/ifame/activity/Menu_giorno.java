package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.TextView;

	public class Menu_giorno extends Activity {

		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_menu_giorno);

			final TextView date = (TextView) findViewById(R.id.date_daily_menu);

			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
			String daily_menu = s.format(new Date());
			date.setText(daily_menu); 
			
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno, menu);
		return true;
	}

}
