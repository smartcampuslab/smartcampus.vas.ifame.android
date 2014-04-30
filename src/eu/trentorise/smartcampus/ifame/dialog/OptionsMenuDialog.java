package eu.trentorise.smartcampus.ifame.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

import it.smartcampuslab.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Piatto;

public class OptionsMenuDialog extends SherlockDialogFragment {

	private static final int MENU_ITEMS = 3;

	public static final int VIEW_REVIEW = 0;
	public static final int RATE_OR_REVIEW = 1;
	public static final int SEARCH_GOOGLE = 2;

	/** this is the key of the serializable piatto object required */
	public static final String PIATTO = "get_piatto_selected";

	private OptionsMenuDialogListener mListener;

	public interface OptionsMenuDialogListener {

		public void onClickOptionsMenuDialog(DialogInterface dialog,
				int position, Piatto piatto);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OptionsMenuDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OptionsMenuDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				getSherlockActivity());

		final Piatto piatto = (Piatto) getArguments().getSerializable(PIATTO);

		builder.setTitle(piatto.getPiatto_nome());

		String[] items = new String[MENU_ITEMS];
		items[VIEW_REVIEW] = getString(R.string.view_review);
		items[RATE_OR_REVIEW] = getString(R.string.rate_dish);
		items[SEARCH_GOOGLE] = getString(R.string.search_on_google);

		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {

				mListener.onClickOptionsMenuDialog(dialog, position, piatto);
			}
		});

		builder.setNegativeButton(
				getString(R.string.iGradito_dialog_button_cancel_text),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		return builder.create();
	}
}
