package eu.trentorise.smartcampus.ifame.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.adapter.MenuDelGiornoPiattiAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetMenuDelGiornoTask;
import eu.trentorise.smartcampus.ifame.dialog.WebSearchDialog;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;

public class MenuGiornoFragment extends SherlockFragment {

	private WebSearchDialog dialog;
	private MenuDelGiornoPiattiAdapter mPiattiAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new WebSearchDialog();
		mPiattiAdapter = new MenuDelGiornoPiattiAdapter(getSherlockActivity());
		if (IFameUtils.isUserConnectedToInternet(getSherlockActivity())) {
			new GetMenuDelGiornoTask(getSherlockActivity(), mPiattiAdapter)
					.execute();
		} else {
			Toast.makeText(getSherlockActivity(),
					getString(R.string.errorInternetConnectionRequired),
					Toast.LENGTH_SHORT).show();
			getSherlockActivity().finish();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_menu_giorno, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setup the listview
		ListView lista_piatti_view = (ListView) getSherlockActivity()
				.findViewById(R.id.lista_piatti);
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
