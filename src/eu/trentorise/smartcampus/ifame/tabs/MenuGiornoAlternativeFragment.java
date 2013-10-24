package eu.trentorise.smartcampus.ifame.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.AlternativePiattiAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetAlternativeListTask;
import eu.trentorise.smartcampus.ifame.dialog.WebSearchDialog;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.ConnectionUtils;

public class MenuGiornoAlternativeFragment extends SherlockFragment {

	private WebSearchDialog dialog;
	private AlternativePiattiAdapter mPiattiAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new WebSearchDialog();
		mPiattiAdapter = new AlternativePiattiAdapter(getActivity());
		if (ConnectionUtils.isUserConnectedToInternet(getActivity())) {
			new GetAlternativeListTask(getActivity(), mPiattiAdapter).execute();
		} else {
			ConnectionUtils.errorToastTnternetConnectionNeeded(getActivity()
					.getApplicationContext());
			getActivity().finish();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_menu_giorno_alternative,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setup the listview
		ListView lista_piatti_view = (ListView) getActivity().findViewById(
				R.id.lista_piatti_alternative);
		lista_piatti_view.setAdapter(mPiattiAdapter);
		lista_piatti_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long id) {

				String piatto_name = ((Piatto) adapter.getItemAtPosition(pos))
						.getPiatto_nome();
				// assure that search is not valid for numbers, since we use
				// numbers(1, 2, 3) as sentinels
				if (!piatto_name.matches("[0-9]+")) {
					showWebSearchDialog(piatto_name);
				}
			}
		});
	}

	private void showWebSearchDialog(String piatto_name) {
		Bundle args = new Bundle();
		args.putString(WebSearchDialog.PIATTO_NAME, piatto_name);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "StartWebSearchDialog");
	}

}
