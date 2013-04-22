package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import eu.trentorise.smartcampus.ifame.R;

public class Fai_il_tuo_menu extends Activity {

	boolean isPrimoPiatto;
	boolean isSecondoPiatto;
	boolean isContornoCaldo;
	boolean isContornoFreddo;
	boolean isDessert;
	boolean isPane;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fai_il_tuo_menu);
		
		Button confirm = (Button) findViewById(R.id.confirm_menu_btn);
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//control checkboxes status
				CheckBox primo = (CheckBox)findViewById(R.id.primo_piatto);
				CheckBox secondo = (CheckBox)findViewById(R.id.secondo_piatto);
				CheckBox contorno_caldo = (CheckBox)findViewById(R.id.contorno_caldo);
				CheckBox contorno_freddo = (CheckBox)findViewById(R.id.contorno_freddo);
				CheckBox dessert = (CheckBox)findViewById(R.id.dessert);
				CheckBox pane = (CheckBox)findViewById(R.id.pane);
				
				isPrimoPiatto = primo.isChecked();
				isSecondoPiatto = secondo.isChecked();
				isContornoCaldo = contorno_caldo.isChecked();
				isContornoFreddo = contorno_freddo.isChecked();
				isDessert = dessert.isChecked();
				isPane = pane.isChecked();
				
				Intent i = new Intent(Fai_il_tuo_menu.this, MostraComposizioneMenu.class);
				
				i.putExtra("isPrimoPiatto", isPrimoPiatto);
				i.putExtra("isSecondoPiatto", isSecondoPiatto);
				i.putExtra("isContornoCaldo", isContornoCaldo);
				i.putExtra("isContornoFreddo", isContornoFreddo);
				i.putExtra("isDessert", isDessert);
				i.putExtra("isPane", isPane);
				
				startActivity(i);		
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fai_il_tuo_menu, menu);
		return true;
	}

}
