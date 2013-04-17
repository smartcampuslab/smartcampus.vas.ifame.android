package eu.trentorise.smartcampus.ifame.activity;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class IGradito extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_igradito);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.igradito, menu);
		return true;
	}

}
