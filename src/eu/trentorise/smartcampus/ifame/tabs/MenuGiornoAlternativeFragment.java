package eu.trentorise.smartcampus.ifame.tabs;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import com.actionbarsherlock.app.SherlockDialogFragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;

import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class MenuGiornoAlternativeFragment extends SherlockFragment {

	ViewGroup theContainer;
	private View view;
	private ProgressDialog pd;
	private String selectedDish;

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		theContainer = container;
		return inflater.inflate(R.layout.layout_menu_giorno_alternative,
				container, false);
	}

	@Override
	public void onResume() {

		// logic
		new ProgressDialog(getActivity());
		// Dont show anything until the data is loaded
		pd = ProgressDialog
				.show(getActivity(), "Loading... ", "please wait...");
		view = theContainer.findViewById(R.id.menu_alternative_view);
		// set the visibility of gthe layout to gone so that nothing will be
		// visible
		view.setVisibility(View.GONE);

		new AlternativeConnector(getActivity()).execute();
		super.onResume();

	}

	public void createMenuAlternative(List<Piatto> alternative) {
		
		if(alternative == null){
			System.out.println("La lista è vuota....");
		} else {
			System.out.println("La lista non è vuota....");
		}

		// Get the listview
		ListView lista_alternative = (ListView) theContainer
				.findViewById(R.id.lista_piatti_alternative);

		// Create a list in which the alternative menu will be saved
		List<Piatto> piatti_alternativi = new ArrayList<Piatto>();

		// add an item into the list, this item will be used as a sentinel that
		// will determine the type of dish(primo, secondo, etc..)
		piatti_alternativi.add(new Piatto("1", ""));

		for (int i = 0; i < alternative.size(); i++) {
			piatti_alternativi.add(alternative.get(i));
			if (i == 2) {
				piatti_alternativi.add(new Piatto("2", "")); // sentinel for 
																// secondi
			}
			if (i == 5) {
				piatti_alternativi.add(new Piatto("3", "")); // sentinel for
																// piatti freddi
			}
			if (i == 8) {
				piatti_alternativi.add(new Piatto("4", "")); // sentinel for
																// contorni
			}
		}

		AlternativeAdapter adapter_alternative = new AlternativeAdapter(
				getActivity(), android.R.layout.simple_expandable_list_item_1,
				piatti_alternativi);
		lista_alternative.setAdapter(adapter_alternative);

		// add a listener to the listview which will allow a google search when
		// an item is clicked
		lista_alternative.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				// Get the dish on a selected position of the list
				selectedDish = ((Piatto) parent.getItemAtPosition(position))
						.getPiatto_nome();
				StartWebSearchAlertDialog dialog = new StartWebSearchAlertDialog();

				dialog.show(getFragmentManager(), null); // Show the dialog

			}
		});

	}

	/*
	 * 
	 * Connector for the Alternative menu
	 */
	private class AlternativeConnector extends
			AsyncTask<Void, Void, List<Piatto>> {

		private ProtocolCarrier mProtocolCarrier;
		private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
		private static final String auth_token = "AUTH_TOKEN";
		private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
		public Context context;
		public String appToken = "test smartcampus";
		public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

		public AlternativeConnector(Context applicationContext) {
			context = applicationContext;
		}

		private List<Piatto> getAlternative() {

			mProtocolCarrier = new ProtocolCarrier(context, appToken);

			MessageRequest request = new MessageRequest(
					"http://smartcampuswebifame.app.smartcampuslab.it",
					"getalternative");
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request, appToken,
						authToken);

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();

					List<Piatto> alternative = Utils.convertJSONToObjects(body,
							Piatto.class);
					
				

					return alternative;

				} else {

				}
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected List<Piatto> doInBackground(Void... params) {
			return getAlternative();
		}

		@Override
		protected void onPostExecute(List<Piatto> result) {
			super.onPostExecute(result);
			createMenuAlternative(result);
			// Make data visible after it has been fetched and dismiss the
			// dialog loader
			view.setVisibility(View.VISIBLE);
			pd.dismiss();
		}

	}

	private class StartWebSearchAlertDialog extends SherlockDialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(
					getString(R.string.GoogleSearchAlertText) + " "
							+ selectedDish + "?")
					.setPositiveButton(
							getString(R.string.GoogleSearchAlertAccept),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									Intent intent = new Intent(
											Intent.ACTION_WEB_SEARCH);
									intent.putExtra(SearchManager.QUERY,
											selectedDish); // query contains
									// search string
									startActivity(intent);

								}
							})
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User cancelled the dialog
								}
							});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}

	private class AlternativeAdapter extends ArrayAdapter<Piatto> {

		public AlternativeAdapter(Context context, int textViewResourceId,
				List<Piatto> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			Piatto piattoDelGiorno = getItem(position);

			if (piattoDelGiorno.getPiatto_nome().matches("[0-9]+")) {

				convertView = inflater.inflate(
						R.layout.layout_row_header_menu_adapter, null);

				int num = Integer.parseInt(piattoDelGiorno.getPiatto_nome());

				TextView nome_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_day_header_adapter);
				if (num == 1) {
					nome_piatto_del_giorno.setText(getString(R.string.iDeciso_menu_del_giorno_primi));
				} else if (num == 2) {
					nome_piatto_del_giorno.setText(getString(R.string.iDeciso_menu_del_giorno_secondi));
				} else if (num == 3) {
					nome_piatto_del_giorno.setText(getString(R.string.iDeciso_menu_del_giorno_piatti_freddi));
				} else if (num == 4) {
					nome_piatto_del_giorno.setText(getString(R.string.iDeciso_menu_del_giorno_contorni));
				}

			} else {

				convertView = inflater.inflate(
						R.layout.layout_row_menu_adapter, null);

				TextView nome_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_name_adapter);
				TextView kcal_piatto_del_giorno = (TextView) convertView
						.findViewById(R.id.menu_kcal_adapter);

				nome_piatto_del_giorno
						.setText(piattoDelGiorno.getPiatto_nome());
				kcal_piatto_del_giorno
						.setText(piattoDelGiorno.getPiatto_kcal());

			}
			return convertView;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
