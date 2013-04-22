package eu.trentorise.smartcampus.ifame.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;

public class ISoldi extends Activity {
	
	TextView centerText; 
	TextView bottomText; 

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_isoldi);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		float card_value = (Float) extras.get("iSoldi");

		centerText = (TextView) findViewById(R.id.isoldi_center_text);
		bottomText = (TextView) findViewById(R.id.isoldi_bottom_text);

		getAmount(card_value);
	}

	public void getAmount(float amount) {
		if (amount >= 4.90) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#228B22"));

			bottomText.setText("Puoi acquistare tutte le tipologie di menu!");
			bottomText.setTextColor(Color.parseColor("#228B22"));
		} else if (amount >= 2.90 && amount < 4.90) {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#D2691E"));

			bottomText
					.setText("Non puoi acquistare tutte le tipologie di menu!");
			bottomText.setTextColor(Color.parseColor("#D2691E"));
		} else {
			centerText.setText("€ " + String.valueOf(amount));
			centerText.setTextColor(Color.parseColor("#FF0000"));

			bottomText
					.setText("Non puoi acquistare nessuna tipologia di menu!");
			bottomText.setTextColor(Color.parseColor("#FF0000"));
		}
	}
}
