package eu.trentorise.smartcampus.ifame.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;

public class Menu_giorno_alternative extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_giorno_alternative);

		// riempio la ListView per i primi piatti

		String[] primi_alternative = { "Pasta e fagioli", "Riso in bianco",
				"Gnocchi di patate" };

		final ArrayList<String> lista_primi_alternative = new ArrayList<String>();
		for (int i = 0; i < primi_alternative.length; ++i) {
			lista_primi_alternative.add(primi_alternative[i]);
		}
		ListView listaprimi_alternative = (ListView) findViewById(R.id.list_view_primi_alternative);

		ListAdapter adapter_primi_alternative = new ArrayAdapter<String>(this,
				layout.layout_list_view, lista_primi_alternative);
		listaprimi_alternative.setAdapter(adapter_primi_alternative);

		// riempio la ListView per i secondi piatti

		String[] secondi_alternative = { "Pizza", "Coniglio" };

		final ArrayList<String> lista_secondi_alternative = new ArrayList<String>();
		for (int i = 0; i < secondi_alternative.length; ++i) {
			lista_secondi_alternative.add(secondi_alternative[i]);
		}
		ListView listasecondi_alternative = (ListView) findViewById(R.id.list_view_secondi_alternative);

		ListAdapter adapter_secondi_alternative = new ArrayAdapter<String>(
				this, layout.layout_list_view, lista_secondi_alternative);
		listasecondi_alternative.setAdapter(adapter_secondi_alternative);

		// riempio la ListView per i contorni caldi

		String[] contorni_alternative = { "Piselli", "Crocchette", "Cavolfiori" };

		final ArrayList<String> lista_contorni_alternative = new ArrayList<String>();
		for (int i = 0; i < contorni_alternative.length; ++i) {
			lista_contorni_alternative.add(contorni_alternative[i]);
		}
		ListView listacontorni_alternative = (ListView) findViewById(R.id.list_view_contorni_alternative);

		ListAdapter adapter_contorni_alternative = new ArrayAdapter<String>(
				this, layout.layout_list_view, lista_contorni_alternative);
		listacontorni_alternative.setAdapter(adapter_contorni_alternative);

		// RISOLUZIONE PROBLEMA LISTVIEW IN SCROLLCONTAINER

		// listaprimi
		ListAdapter listAdapterPrimi_alternative = listaprimi_alternative
				.getAdapter();

		int rowsPrimi_alt = listAdapterPrimi_alternative.getCount();
		int heightPrimi_alt = 60 * rowsPrimi_alt;
		ViewGroup.LayoutParams paramsPrimi_alt = listaprimi_alternative
				.getLayoutParams();
		paramsPrimi_alt.height = heightPrimi_alt;
		listaprimi_alternative.setLayoutParams(paramsPrimi_alt);
		listaprimi_alternative.requestLayout();

		// listasecondi
		ListAdapter listAdapterSecondi_alternative = listasecondi_alternative
				.getAdapter();

		int rowsSecondi_alt = listAdapterSecondi_alternative.getCount();
		int heightSecondi_alt = 60 * rowsSecondi_alt;
		ViewGroup.LayoutParams paramsSecondi_alt = listasecondi_alternative
				.getLayoutParams();
		paramsSecondi_alt.height = heightSecondi_alt;
		listasecondi_alternative.setLayoutParams(paramsSecondi_alt);
		listasecondi_alternative.requestLayout();

		// listacontorni
		ListAdapter listAdapterContorni_alternative = listacontorni_alternative
				.getAdapter();

		int rowsContorni_alt = listAdapterContorni_alternative.getCount();
		int heightContorni_alt = 60 * rowsContorni_alt;

		ViewGroup.LayoutParams paramsContorni_alt = listacontorni_alternative
				.getLayoutParams();
		paramsContorni_alt.height = heightContorni_alt;
		listacontorni_alternative.setLayoutParams(paramsContorni_alt);
		listacontorni_alternative.requestLayout();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno_alternative, menu);
		return true;
	}

}
