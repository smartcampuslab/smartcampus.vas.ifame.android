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
import eu.trentorise.smartcampus.ifame.activity.MenuDelGiorno;
import eu.trentorise.smartcampus.ifame.adapter.AlternativePiattiAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetAlternativeListTask;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;

public class MenuGiornoAlternativeFragment extends SherlockFragment {

	private AlternativePiattiAdapter mPiattiAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// dialog = new WebSearchDialog();
		mPiattiAdapter = new AlternativePiattiAdapter(getSherlockActivity());
		if (IFameUtils.isUserConnectedToInternet(getSherlockActivity())) {
			new GetAlternativeListTask(getSherlockActivity(), mPiattiAdapter)
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
		return inflater.inflate(R.layout.layout_menu_giorno_alternative,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setup the listview
		ListView lista_piatti_view = (ListView) getSherlockActivity()
				.findViewById(R.id.lista_piatti_alternative);
		lista_piatti_view.setAdapter(mPiattiAdapter);
		lista_piatti_view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long id) {
				Piatto piatto = (Piatto) adapter.getItemAtPosition(pos);
				// check because there are some fake piatti as header
				if (!piatto.getPiatto_nome().matches("[0-9]+")) {
					// showWebSearchDialog(piattoName);
					((MenuDelGiorno) getSherlockActivity())
							.showOptionsDialog(piatto);
				}
			}
		});
	}
}
