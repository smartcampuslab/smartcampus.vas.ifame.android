package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.template.MainActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IFame_Main_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);
		
		Button iFretta_btn = (Button)findViewById(R.id.iFretta_button);
		iFretta_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			Intent i = new Intent (IFame_Main_Activity.this, IFretta.class);
			startActivity(i);
				
			}

		});
		
		Button iDeciso_btn = (Button)findViewById(R.id.iDeciso_button);
		iDeciso_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			Intent i = new Intent (IFame_Main_Activity.this, IDeciso.class);
			startActivity(i);
				
			}

		});
		
		Button iGradito_btn = (Button)findViewById(R.id.iGradito_button);
		iGradito_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			Intent i = new Intent (IFame_Main_Activity.this, IGradito.class);
			startActivity(i);
				
			}

		});
		
		Button iSoldi_btn = (Button)findViewById(R.id.iSoldi_button);
		iSoldi_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			Intent i = new Intent (IFame_Main_Activity.this, ISoldi.class);
			startActivity(i);
				
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ifame__main_, menu);
		return true;
	}
	
}
