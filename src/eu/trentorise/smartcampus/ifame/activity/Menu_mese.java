package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.model.Settimana;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Menu_mese extends Activity {

	private List<Settimana> settimana = new ArrayList<Settimana>();
	private Spinner weekSpinner;
	private ArrayAdapter<Settimana> weekAdapter;
	private Settimana selectedSettimana;

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

		ListAdapter listAdapterCibi = listacibiday.getAdapter();

		int rowsCibi = listAdapterCibi.getCount();
		int heightCibi = 60 * rowsCibi;
		ViewGroup.LayoutParams paramsCibi = listacibiday.getLayoutParams();
		paramsCibi.height = heightCibi;
		listacibiday.setLayoutParams(paramsCibi);
		listacibiday.requestLayout();

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_menu_mese, container, false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_mese, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		weekSpinner = (Spinner) findViewById(R.id.spinner_settimana);
		weekAdapter = new ArrayAdapter<Settimana>(this,
				android.R.layout.simple_spinner_dropdown_item, settimana);
		weekSpinner.setAdapter(weekAdapter);

	}

}
