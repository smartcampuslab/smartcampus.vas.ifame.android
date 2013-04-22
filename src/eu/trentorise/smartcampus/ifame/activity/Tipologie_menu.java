package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Tipologie_menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tipologie_menu);
		
		Button iDeciso_menu_intero_btn = (Button)findViewById(R.id.menu_intero_btn);
		iDeciso_menu_intero_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Tipologie_menu.this, Menu_Intero.class);
				startActivity(i);
				
			}});
		
		Button iDeciso_menu_ridotto_btn = (Button)findViewById(R.id.menu_ridotto_btn);
		iDeciso_menu_ridotto_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Tipologie_menu.this, Menu_Ridotto.class);
				startActivity(i);
				
			}});
		
		Button iDeciso_menu_snack_btn = (Button)findViewById(R.id.menu_snack_btn);
		iDeciso_menu_snack_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Tipologie_menu.this, Menu_snack.class);
				startActivity(i);
				
			}});	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipologie_menu, menu);
		return true;
	}

}
