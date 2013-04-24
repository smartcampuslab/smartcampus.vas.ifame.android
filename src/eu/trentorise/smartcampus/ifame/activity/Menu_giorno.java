package eu.trentorise.smartcampus.ifame.activity;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.widget.ListView;
import android.widget.TextView;

	public class Menu_giorno extends Activity {

		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_menu_giorno);

			final TextView date = (TextView) findViewById(R.id.date_daily_menu);

			SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
			String daily_menu = s.format(new Date());
			date.setText(daily_menu); 
			
			//riempio la ListView per i primi piatti
			
			String[] primi = { "Pasta Aglio olio e peperoncino", "Pasta al ragu",
					"Risotto ai funghi"};
			
			final ArrayList<String> lista_primi = new ArrayList<String>();
			for (int i = 0; i < primi.length; ++i) {
				lista_primi.add(primi[i]);
			}
			ListView listaprimi = (ListView) findViewById(R.id.list_view_primi);
			
			final MyArrayAdapter adapter_primi = new MyArrayAdapter(this,
					layout.layout_list_view, lista_primi);
			listaprimi.setAdapter(adapter_primi);
			
			//riempio la ListView per i secondi piatti
			
			String[] secondi = { "Scaloppine al limone",
					"Ossobuco alla romana", "Stinco con Patate" };
			
			final ArrayList<String> lista_secondi = new ArrayList<String>();
			for (int i = 0; i < secondi.length; ++i) {
				lista_secondi.add(secondi[i]);
			}
			ListView listasecondi = (ListView) findViewById(R.id.list_view_secondi);
			
			final MyArrayAdapter adapter_secondi = new MyArrayAdapter(this,
					layout.layout_list_view, lista_secondi);
			listasecondi.setAdapter(adapter_secondi);
			
			//riempio la ListView per i contorni caldi
			
			String[] contorni = {"Fagioli", "Patatine fritte", "Carote" };
			
			final ArrayList<String> lista_contorni = new ArrayList<String>();
			for (int i = 0; i < contorni.length; ++i) {
				lista_contorni.add(contorni[i]);
			}
			ListView listacontorni = (ListView) findViewById(R.id.list_view_contorni);
			
			final MyArrayAdapter adapter_contorni = new MyArrayAdapter(this,
					layout.layout_list_view, lista_contorni);
			listacontorni.setAdapter(adapter_contorni);
			
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno, menu);
		return true;
	}

}
