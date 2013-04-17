package eu.trentorise.smartcampus.ifame.activity;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class IDeciso extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ideciso);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ideciso, menu);
		return true;
	}

}
