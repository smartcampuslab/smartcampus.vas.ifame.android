package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;

//import eu.trentorise.smartcampus.ifame.fragment.IFretta_Details;

public class IFretta extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifretta);

		findViewById(R.id.povo_0).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", Mensa.POVO_O);
				startActivity(i);
			}

		});

		findViewById(R.id.povo_1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", Mensa.POVO_1);
				startActivity(i);
			}
		});

		findViewById(R.id.tommaso_gar).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(IFretta.this, IFretta_Details.class);
						i.putExtra("mensa", Mensa.TOMMASO_GAR);
						startActivity(i);
					}
				});

		findViewById(R.id.mesiano).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", Mensa.MESIANO);
				startActivity(i);
			}
		});

		findViewById(R.id.zanella).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", Mensa.ZANELLA);
				startActivity(i);
			}
		});

		findViewById(R.id.fbk).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(IFretta.this, IFretta_Details.class);
				i.putExtra("mensa", Mensa.FBK);
				startActivity(i);
			}
		});

		findViewById(R.id.xxiv_magggio).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(IFretta.this, IFretta_Details.class);
						i.putExtra("mensa", Mensa.XXIV_MAGGIO);
						startActivity(i);
					}
				});

	}
}
