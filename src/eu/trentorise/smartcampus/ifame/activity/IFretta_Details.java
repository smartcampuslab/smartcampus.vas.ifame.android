package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.WebcamMensa;

public class IFretta_Details extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.ifretta_details);

		if (extras == null) {
			return;
		}

		Mensa m = (Mensa) extras.get("mensa");
		TextView mensa_name = (TextView) findViewById(R.id.mensa_name_textview);
		final TextView date = (TextView) findViewById(R.id.date_text_view);

		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String date_s = s.format(new Date());

		Button btn = (Button) findViewById(R.id.refresh_button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				String date_s = s.format(new Date());
				date.setText(date_s);
			}			
		});
		
		mensa_name.setText(String.valueOf(m));
		date.setText(date_s);

		mensa_name.setTextColor(Color.parseColor("#228B22"));
		date.setTextColor(Color.parseColor("#228B22"));

		btn.setBackgroundColor(Color.parseColor("#228B22"));

		/*switch (m) {
		case POVO_O:
			mensa_name.setText("POVO 0");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#228B22"));
			date.setTextColor(Color.parseColor("#228B22"));

			btn.setBackgroundColor(Color.parseColor("#228B22"));
			break;
		case POVO_1:
			mensa_name.setText("POVO 1");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#d2691e"));
			date.setTextColor(Color.parseColor("#d2691e"));

			btn.setBackgroundColor(Color.parseColor("#d2691e"));
			break;
		case FBK:
			mensa_name.setText("FBK");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#87ceeb"));
			date.setTextColor(Color.parseColor("#87ceeb"));

			btn.setBackgroundColor(Color.parseColor("#87ceeb"));
			break;
		case MESIANO:
			mensa_name.setText("MESIANO");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#b22222"));
			date.setTextColor(Color.parseColor("#b22222"));

			btn.setBackgroundColor(Color.parseColor("#b22222"));
			break;
		case TOMMASO_GAR:
			mensa_name.setText("TOMMASO GAR");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#4169e1"));
			date.setTextColor(Color.parseColor("#4169e1"));

			btn.setBackgroundColor(Color.parseColor("#4169e1"));
			break;
		case XXIV_MAGGIO:
			mensa_name.setText("XXIV MAGGIO");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#483D8B"));
			date.setTextColor(Color.parseColor("#483D8B"));

			btn.setBackgroundColor(Color.parseColor("#483D8B"));
			break;
		case ZANELLA:
			mensa_name.setText("ZANELLA");
			date.setText(date_s);

			mensa_name.setTextColor(Color.parseColor("#ffff00"));
			date.setTextColor(Color.parseColor("#ffff00"));

			btn.setBackgroundColor(Color.parseColor("#ffff00"));
			break;
		default:
			break;

		}*/

	}
}
