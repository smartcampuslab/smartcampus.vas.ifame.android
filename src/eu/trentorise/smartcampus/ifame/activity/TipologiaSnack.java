package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TipologiaSnack extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tipologia_snack);
		setTitle(getString(R.string.iDeciso_menu_types_snack));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipologia_snack, menu);
		return true;
	}

}
