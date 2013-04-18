package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IDeciso extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ideciso);
		
		
		Button iDeciso_menu_giorno_btn = (Button)findViewById(R.id.daily_menu_btn);
		iDeciso_menu_giorno_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(IDeciso.this, Menu_giorno.class);
				startActivity(i);
				
			}});
		
		Button iDeciso_menu_types_btn = (Button)findViewById(R.id.menu_types_btn);
		iDeciso_menu_types_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IDeciso.this, Tipologie_menu.class);
				startActivity(i);
				
			}});
		Button iDeciso_compose_menu_btn = (Button)findViewById(R.id.compose_menu_btn);
		iDeciso_compose_menu_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IDeciso.this, Fai_il_tuo_menu.class);
				startActivity(i);
				
			}});
		Button iDeciso_monthly_menu_btn = (Button)findViewById(R.id.monthly_menu_btn);
		iDeciso_monthly_menu_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(IDeciso.this, Menu_mese.class);
				startActivity(i);
				
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ideciso, menu);
		return true;
	}

}
