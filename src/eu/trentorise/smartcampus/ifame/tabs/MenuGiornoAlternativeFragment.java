package eu.trentorise.smartcampus.ifame.tabs;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
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
import eu.trentorise.smartcampus.ifame.activity.IGraditoVisualizzaRecensioni;
import eu.trentorise.smartcampus.ifame.adapter.AlternativePiattiAdapter;
import eu.trentorise.smartcampus.ifame.asynctask.GetAlternativeListTask;
import eu.trentorise.smartcampus.ifame.dialog.InsertReviewDialog;
import eu.trentorise.smartcampus.ifame.dialog.OptionsMenuDialog;
import eu.trentorise.smartcampus.ifame.dialog.OptionsMenuDialog.OptionsMenuDialogListener;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;

public class MenuGiornoAlternativeFragment extends SherlockFragment implements
		OptionsMenuDialogListener {

	// private WebSearchDialog webSearchDialog;
	private OptionsMenuDialog optionsDialog;
	private InsertReviewDialog insertReviewDialog;

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
					showOptionsDialog(piatto);
				}
			}
		});
	}

	/** display dialog options */
	private void showOptionsDialog(Piatto piatto) {

		if (optionsDialog == null) {
			optionsDialog = new OptionsMenuDialog();
		}

		Bundle args = new Bundle();
		args.putSerializable(OptionsMenuDialog.PIATTO, piatto);

		optionsDialog.setArguments(args);
		optionsDialog.show(getSherlockActivity().getSupportFragmentManager(), "OptionsMenuDialog");
	}

	/**
	 * Show dialog for insert or edit a review
	 */
	private void showInsertReviewDialog(Piatto piatto) {

		if (insertReviewDialog == null) {
			insertReviewDialog = new InsertReviewDialog();
		}

		// put the data needed for showing the dialog in a bundle
		Bundle args = new Bundle();
		args.putSerializable(InsertReviewDialog.PIATTO, piatto);
		args.putInt(InsertReviewDialog.VOTO, 5);
		args.putString(InsertReviewDialog.COMMENTO, "");

		// pass the bundle to the dialog and show
		insertReviewDialog.setArguments(args);
		insertReviewDialog.show(getSherlockActivity()
				.getSupportFragmentManager(), "insertReviewDialog");
	}

	@Override
	public void onClickOptionsMenuDialog(DialogInterface dialog, int position,
			Piatto piatto) {
		switch (position) {

		case OptionsMenuDialog.VIEW_REVIEW:
			Intent viewReviews = new Intent(getSherlockActivity(),
					IGraditoVisualizzaRecensioni.class);
			viewReviews.putExtra(IGraditoVisualizzaRecensioni.PIATTO, piatto);
			startActivity(viewReviews);
			break;

		case OptionsMenuDialog.RATE_OR_REVIEW:
			showInsertReviewDialog(piatto);
			break;

		case OptionsMenuDialog.SEARCH_GOOGLE:
			Intent searchGoogle = new Intent(Intent.ACTION_WEB_SEARCH);
			searchGoogle.putExtra(SearchManager.QUERY, piatto.getPiatto_nome());
			startActivity(searchGoogle);
			break;

		default:
			break;
		}

	}

}
