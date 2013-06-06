package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ScrollView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;

public class TipologiaIntero extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tipologia_intero);
		setTitle(getString(R.string.iDeciso_menu_types_intero));
		
		TextView bigLabel = (TextView)findViewById(R.id.tipologia_intero_biglabel);
		bigLabel.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_first)+", "+getString(R.string.iDeciso_compose_menu_checkbox_second));
		bigLabel.setTypeface(null, Typeface.BOLD);
		
		TextView contorni = (TextView)findViewById(R.id.tipologia_intero_2contorni);
		contorni.setText("+ "+getString(R.string.iDeciso_2contorni));
		
		TextView dessert = (TextView)findViewById(R.id.tipologia_intero_dessert);
		dessert.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		
		TextView pane = (TextView)findViewById(R.id.tipologia_intero_pane);
		pane.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipologia_intero, menu);
		return true;
	}

}
