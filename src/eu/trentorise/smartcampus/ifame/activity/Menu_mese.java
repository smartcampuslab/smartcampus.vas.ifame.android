package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.model.MenuDellaSettimana;
import eu.trentorise.smartcampus.ifame.model.PiattoKcal;
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

	
	private Spinner weekSpinner;
	private ArrayAdapter<Settimana> weekAdapter;
	private Settimana selectedSettimana;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_mese);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_menu_mese, container, false);
	}
	
	/*
	public void createMenuOfTheWeek(MenuDellaSettimana menuDellaSettimana){
		List<List<String>> menu_settimana = new ArrayList<List<String>>();
		
		List<PiattoKcal> piatti_del_giorno = MenuDelGiorno.getPiattiDelGiorno();
		List<MenuDelGiorno> menu_giorno = menuDellaSettimana.getMenuDellaSettimana();
		Iterator<MenuDelGiorno> iter = menu_giorno.iterator(); 
		Iterator<PiattoKcal> iter1 = piatti_del_giorno.iterator();
		
		while(iter.hasNext()){
			
		}
		
		final TextView date = (TextView) findViewById(R.id.day_of_the_week);

		SimpleDateFormat sd = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String day_week = sd.format(new Date());
		date.setText(day_week);
		
		ListView listacibiday = (ListView) findViewById(R.id.menu_of_the_day);

		 ArrayAdapter<String> adapter_cibi_day = new ArrayAdapter<String>(this,
				layout.layout_list_view, menu_settimana);
		listacibiday.setAdapter(adapter_cibi_day);

		ListAdapter listAdapterCibi = listacibiday.getAdapter();

		int rowsCibi = listAdapterCibi.getCount();
		int heightCibi = 60 * rowsCibi;
		ViewGroup.LayoutParams paramsCibi = listacibiday.getLayoutParams();
		paramsCibi.height = heightCibi;
		listacibiday.setLayoutParams(paramsCibi);
		listacibiday.requestLayout();
	}
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_mese, menu);
		return true;
	}

//	@Override
//	public void onStart() {
//		super.onStart();
//
//		weekSpinner = (Spinner) findViewById(R.id.spinner_settimana);
//		weekAdapter = new ArrayAdapter<Settimana>(this,
//				android.R.layout.simple_spinner_dropdown_item, settimana);
//		weekSpinner.setAdapter(weekAdapter);
//
//	}

}
