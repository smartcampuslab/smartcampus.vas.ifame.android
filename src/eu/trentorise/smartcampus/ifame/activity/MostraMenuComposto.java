package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;

public class MostraMenuComposto extends Activity {

	private boolean isPrimoPiatto;
	private boolean isSecondoPiatto;
	private boolean isContornoCaldo;
	private boolean isContornoFreddo;
	private boolean isDessert;
	private boolean isPane;
	private boolean isInsalatona;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mostra_menu_composto);
		
		Bundle datas = getIntent().getExtras();
		
		isPrimoPiatto = datas.getBoolean("isPrimoPiatto");
		isSecondoPiatto = datas.getBoolean("isSecondoPiatto");
		isContornoCaldo = datas.getBoolean("isContornoCaldo");
		isContornoFreddo = datas.getBoolean("isContornoFreddo");
		isDessert = datas.getBoolean("isDessert");
		isPane = datas.getBoolean("isPane");
		isInsalatona = datas.getBoolean("isInsalatona");
		
		TextView tv = (TextView)findViewById(R.id.textview);
		tv.setText("L'utente ha selezionato: \n" +
				"Primo piatto: " + isPrimoPiatto + "\n" +
				"Secondo piatto: " + isSecondoPiatto + "\n" +
				"Insalatona: " + isInsalatona + "\n" +
				"Contorno Caldo: " + isContornoCaldo + "\n" +
				"Contorno Freddo: " + isContornoFreddo + "\n" +
				"Dessert: " + isDessert + "\n" +
				"Pane: "+ isPane);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_menu_composto, menu);
		return true;
	}

}
