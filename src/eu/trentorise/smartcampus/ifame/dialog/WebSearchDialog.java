package eu.trentorise.smartcampus.ifame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

import eu.trentorise.smartcampus.ifame.R;

public class WebSearchDialog extends SherlockDialogFragment {

	/** key for the required String parameter piatto name */
	public static final String PIATTO_NAME = "piattoname";

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final String selectedDish = getArguments().getString(PIATTO_NAME);

		builder.setMessage(
				getString(R.string.iDeciso_GoogleSearchAlertText) + " "
						+ selectedDish + "?")
				.setPositiveButton(
						getString(R.string.iDeciso_GoogleSearchAlertAccept),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

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
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();

	}

}