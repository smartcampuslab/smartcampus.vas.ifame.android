package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.ScrollView;
import android.widget.TextView;

public class TipologiaSnack extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tipologia_snack);
		setTitle(getString(R.string.iDeciso_menu_types_snack));
		
		
		//menu snack 1
		TextView primo1 = (TextView) findViewById(R.id.tipologia_snack_primo1);
		TextView contorno_o_dessert1 = (TextView) findViewById(R.id.tipologia_snack_contorno_o_dessert1);
		TextView pane1 = (TextView) findViewById(R.id.tipologia_snack_pane1); 
		
		primo1.setText("- "+getString(R.string.iDeciso_compose_menu_checkbox_first)+", ");
		primo1.setTypeface(null, Typeface.BOLD);
		contorno_o_dessert1.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_contorno_caldo)+ " o "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		pane1.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		//menu snack 2
		TextView secondo2 = (TextView) findViewById(R.id.tipologia_snack_secondo2); 
		TextView pane2 = (TextView) findViewById(R.id.tipologia_snack_pane2); 
		
		secondo2.setText("- "+ getString(R.string.iDeciso_compose_menu_checkbox_second));		
		secondo2.setTypeface(null, Typeface.BOLD);
		pane2.setText("+ "+getString(R.string.iDeciso_pane));
		
		
		//menu snack 3
		TextView trancio_pizza3 = (TextView) findViewById(R.id.tipologia_snack_trancio_pizza3);
		TextView dessert3 = (TextView)findViewById(R.id.tipologia_snack_dessert3);
		TextView acqua_o_caffe3 = (TextView)findViewById(R.id.tipologia_snack_acqua_o_caffe3);
		
		trancio_pizza3.setText("- "+ getString(R.string.iDeciso_pizza_trancio));
		trancio_pizza3.setTypeface(null, Typeface.BOLD);
		dessert3.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		acqua_o_caffe3.setText("+ "+getString(R.string.iDeciso_acqua_o_caffe));
		
		
		//menu snack 4
		TextView panino4 = (TextView) findViewById(R.id.tipologia_snack_panino4);
		TextView dessert4 = (TextView)findViewById(R.id.tipologia_snack_dessert4);
		TextView acqua_o_caffe4 = (TextView)findViewById(R.id.tipologia_snack_acqua_o_caffe4);
		
		panino4.setText("- "+ getString(R.string.iDeciso_panino));
		panino4.setTypeface(null, Typeface.BOLD);
		dessert4.setText("+ "+getString(R.string.iDeciso_compose_menu_checkbox_dessert));
		acqua_o_caffe4.setText("+ "+getString(R.string.iDeciso_acqua_o_caffe));
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipologia_snack, menu);
		return true;
	}

}