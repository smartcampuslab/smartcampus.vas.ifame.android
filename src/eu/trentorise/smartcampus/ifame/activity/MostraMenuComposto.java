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
	private boolean isContorno1;
	private boolean isContorno2;
	private boolean isDessert;
	private boolean isPanino;
	private boolean isInsalatona;
	private boolean isPizza;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mostra_menu_composto);

		Bundle datas  = getIntent().getExtras();

/*
		isPrimoPiatto = datas.getBoolean("isPrimoPiatto");
		isSecondoPiatto = datas.getBoolean("isSecondoPiatto");
		isContorno1 = datas.getBoolean("isContornoCaldo");
		isContorno2 = datas.getBoolean("isContornoFreddo");
		isDessert = datas.getBoolean("isDessert");
		isPanino = datas.getBoolean("isPanino");
		isInsalatona = datas.getBoolean("isInsalatona");
		isPizza = datas.getBoolean("isPizza");
	
*/
		isPrimoPiatto = datas.getBoolean("isPrimoPiatto");
		isSecondoPiatto = datas.getBoolean("isSecondoPiatto");
		isContorno1 = datas.getBoolean("isContorno1");
		isContorno2 = datas.getBoolean("isContorno2");
		isDessert = datas.getBoolean("isDessert");
		isPanino = datas.getBoolean("isPanino");
		isInsalatona = datas.getBoolean("isInsalatona");
		isPizza = datas.getBoolean("isPizza");
		
		
		
		TextView tv = (TextView) findViewById(R.id.textview);
		tv.setText("Il menu selezionato è: " + Fai_il_tuo_menu.menu + "" +
				"\n Primo: " + isPrimoPiatto+
				"\n Secondo:" +isSecondoPiatto +
				"\n Contorno1: " +isContorno1 +
				"\n Contorno2: " + isContorno2+
				"\n Dessert:" + isDessert+
				"\n Panino: " + isPanino+
				"\n Insalatona: " +isInsalatona +
				"\n Pizza: "+isPizza);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostra_menu_composto, menu);
		return true;
	}

}
