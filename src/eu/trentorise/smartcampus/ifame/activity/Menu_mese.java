package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class Menu_mese extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_mese);

		// Menu del giorno corrente

		final TextView date = (TextView) findViewById(R.id.day_of_the_week);

		SimpleDateFormat sd = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String day_week = sd.format(new Date());
		date.setText(day_week);

		// riempio la ListView con i cibi

		String[] cibi_day = { "Pasta Aglio olio e peperoncino",
				"Pasta al ragu", "Risotto ai funghi", "Scaloppine al limone",
				"Stinco con Patate", "Anatre all'arancia",
				"Ossobuco alla romana", "Fagioli", "Patatine fritte", "Carote" };

		final ArrayList<String> lista_cibi_day = new ArrayList<String>();
		for (int i = 0; i < cibi_day.length; ++i) {
			lista_cibi_day.add(cibi_day[i]);
		}
		ListView listacibiday = (ListView) findViewById(R.id.menu_of_the_day);

		final MyArrayAdapter adapter_cibi_day = new MyArrayAdapter(this,
				layout.layout_list_view, lista_cibi_day);
		listacibiday.setAdapter(adapter_cibi_day);

		// Menu del giorno seguente

		final TextView dateplus = (TextView) findViewById(R.id.next_day_of_the_week);

		SimpleDateFormat sdn = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String next_day_week = sdn.format(new Date());
		dateplus.setText(next_day_week);

		// riempio la ListView con i cibi

		String[] cibi_dayplus = { "Pasta Bella Napoli", "Risotto agli aromi",
				"Chicche di patate", "Bistecca di vitello",
				"Branzino ai ferri", "Pizza", "Bastoncini di pesce",
				"Finocchi", "Spinaci", "Cavolfiori" };

		final ArrayList<String> lista_cibi_dayplus = new ArrayList<String>();
		for (int i = 0; i < cibi_dayplus.length; ++i) {
			lista_cibi_dayplus.add(cibi_dayplus[i]);
		}
		ListView listacibidayplus = (ListView) findViewById(R.id.menu_of_the_next_day);

		final MyArrayAdapter adapter_cibi_dayplus = new MyArrayAdapter(this,
				layout.layout_list_view, lista_cibi_dayplus);
		listacibidayplus.setAdapter(adapter_cibi_dayplus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_mese, menu);
		return true;
	}

}
