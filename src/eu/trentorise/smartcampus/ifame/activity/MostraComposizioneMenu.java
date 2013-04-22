package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;

public class MostraComposizioneMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mostra_composizione_menu);
		
		TextView tv = (TextView)findViewById(R.id.mostra_scelte);
		
		Bundle i = getIntent().getExtras();
		
		
		tv.setText("L'utente ha selezionato: \n" +
				"Primo piatto: " + i.getBoolean("isPrimoPiatto", false) + "\n" +
				"Secondo piatto: " + i.getBoolean("isSecondoPiatto", false) + "\n" +
				"Contorno Caldo: " + i.getBoolean("isContornoCaldo", false) + "\n" +
				"Contorno Freddo: " + i.getBoolean("isContornoFreddo", false) + "\n" +
				"Dessert: " + i.getBoolean("isDessert", false) + "\n" +
				"Pane: " + i.getBoolean("isPane", false) + "\n");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_composizione_menu, menu);
		return true;
	}

}
