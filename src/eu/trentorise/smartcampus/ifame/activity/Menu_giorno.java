package eu.trentorise.smartcampus.ifame.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.R.layout;

public class Menu_giorno extends Activity {
	
	private String selectedDish;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu_giorno);

		final TextView date = (TextView) findViewById(R.id.date_daily_menu);

		SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
		String daily_menu = s.format(new Date());
		date.setText(daily_menu);

		Button alternative_btn = (Button) findViewById(R.id.alternative);
		alternative_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu_giorno.this,
						Menu_giorno_alternative.class);
				startActivity(intent);
			}

		});

		// riempio la ListView per i primi piatti

		String[] primi = { "Pasta A.O.P.", "Pasta al ragu", "Risotto ai funghi" };

		final ArrayList<String> lista_primi = new ArrayList<String>();
		for (int i = 0; i < primi.length; ++i) {
			lista_primi.add(primi[i]);
		}
		ListView listaprimi = (ListView) findViewById(R.id.list_view_primi);
		listaprimi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				selectedDish = (String) parent.getItemAtPosition(position);
				StartWebSearchAlertDialog dialog = new StartWebSearchAlertDialog();
				

				dialog.show(getFragmentManager(), null);

			}
		});

		final MyArrayAdapter adapter_primi = new MyArrayAdapter(this,
				layout.layout_list_view, lista_primi);
		listaprimi.setAdapter(adapter_primi);

		// riempio la ListView per i secondi piatti

		String[] secondi = { "Scaloppine al limone", "Ossobuco alla romana" };

		final ArrayList<String> lista_secondi = new ArrayList<String>();
		for (int i = 0; i < secondi.length; ++i) {
			lista_secondi.add(secondi[i]);
		}
		ListView listasecondi = (ListView) findViewById(R.id.list_view_secondi);

		final MyArrayAdapter adapter_secondi = new MyArrayAdapter(this,
				layout.layout_list_view, lista_secondi);
		listasecondi.setAdapter(adapter_secondi);

		// riempio la ListView per i contorni caldi

		String[] contorni = { "Fagioli", "Patatine fritte", "Carote" };

		final ArrayList<String> lista_contorni = new ArrayList<String>();
		for (int i = 0; i < contorni.length; ++i) {
			lista_contorni.add(contorni[i]);
		}
		ListView listacontorni = (ListView) findViewById(R.id.list_view_contorni);

		final MyArrayAdapter adapter_contorni = new MyArrayAdapter(this,
				layout.layout_list_view, lista_contorni);
		listacontorni.setAdapter(adapter_contorni);
		listasecondi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				selectedDish = (String) parent.getItemAtPosition(position);
				StartWebSearchAlertDialog dialog = new StartWebSearchAlertDialog();
				

				dialog.show(getFragmentManager(), null);

			}
		});


		// RISOLUZIONE PROBLEMA LISTVIEW IN SCROLLCONTAINER

		// listaprimi
		ListAdapter listAdapterPrimi = listaprimi.getAdapter();

		int rowsPrimi = listAdapterPrimi.getCount();
		int heightPrimi = 60 * rowsPrimi;
		ViewGroup.LayoutParams paramsPrimi = listaprimi.getLayoutParams();
		paramsPrimi.height = heightPrimi;
		listaprimi.setLayoutParams(paramsPrimi);
		listaprimi.requestLayout();

		// listasecondi
		ListAdapter listAdapterSecondi = listasecondi.getAdapter();

		int rowsSecondi = listAdapterSecondi.getCount();
		int heightSecondi = 60 * rowsSecondi;
		ViewGroup.LayoutParams paramsSecondi = listasecondi.getLayoutParams();
		paramsSecondi.height = heightSecondi;
		listasecondi.setLayoutParams(paramsSecondi);
		listasecondi.requestLayout();

		// listacontorni
		ListAdapter listAdapterContorni = listacontorni.getAdapter();

		int rowsContorni = listAdapterContorni.getCount();
		int heightContorni = 60 * rowsContorni;

		ViewGroup.LayoutParams paramsContorni = listacontorni.getLayoutParams();
		paramsContorni.height = heightContorni;
		listacontorni.setLayoutParams(paramsContorni);
		listacontorni.requestLayout();
		listacontorni.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				selectedDish = (String) parent.getItemAtPosition(position);
				StartWebSearchAlertDialog dialog = new StartWebSearchAlertDialog();
				

				dialog.show(getFragmentManager(), null);

			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_giorno, menu);
		return true;
	}

	public class StartWebSearchAlertDialog extends DialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			
			
			builder.setMessage(getString(R.string.GoogleSearchAlertText) +" "+ selectedDish +"?")
            .setPositiveButton(getString(R.string.GoogleSearchAlertAccept), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
    				
                	Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
    				intent.putExtra(SearchManager.QUERY, selectedDish); // query contains
    																// search string
    				startActivity(intent);
                    
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
     // Create the AlertDialog object and return it
     return builder.create();

			
		}
	}

}
