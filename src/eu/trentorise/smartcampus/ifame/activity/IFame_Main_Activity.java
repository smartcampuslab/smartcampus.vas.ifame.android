package eu.trentorise.smartcampus.ifame.activity;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.connector.IFrettaConnector;

public class IFame_Main_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_ifame_main);
		
		new IFrettaConnector(getApplicationContext()).execute();

		Button iFretta_btn = (Button) findViewById(R.id.iFretta_button);
		iFretta_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFame_Main_Activity.this, IFretta.class);
				startActivity(i);

			}

		});

		Button iDeciso_btn = (Button) findViewById(R.id.iDeciso_button);
		iDeciso_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFame_Main_Activity.this, IDeciso.class);
				startActivity(i);

			}

		});

		Button iGradito_btn = (Button) findViewById(R.id.iGradito_button);
		iGradito_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(IFame_Main_Activity.this, IGradito.class);
				startActivity(i);

			}

		});

		Button iSoldi_btn = (Button) findViewById(R.id.iSoldi_button);
		iSoldi_btn.setOnClickListener(new OnClickListener() {

			float card_val;

			@Override
			public void onClick(View v) {
				float[] values = new float[] { 4.10f, 4.30F, 2.7f, 1.8f, 13.0f, 5.10f,
						7.15f, 3.77f, 3.50f, 2.60f, 2.90f };
				for (int i = 0; i < values.length; i++) {
					card_val = values[new Random().nextInt(values.length) % 60];
				}
				Intent i = new Intent(IFame_Main_Activity.this, ISoldi.class);
				i.putExtra("iSoldi", card_val);
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
