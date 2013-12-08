package eu.trentorise.smartcampus.ifame.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;

/**
 * Custom dialog interface to add or edit own review
 */
public class InsertReviewDialog extends SherlockDialogFragment {

	/** These are the key of the bundle objects to pass when calling the dialog */
	public static final String MENSA = "mensa_extra";
	public static final String COMMENTO = "mio_commento";
	public static final String VOTO = "mio_voto";
	public static final String PIATTO = "piatto_extra";

	private EditText userReviewEditText;
	private TextView piattoNameTextView;
	private SeekBar barUserValutation;

	private Mensa mensa;
	private Piatto piatto;

	private InsertReviewDialogListener mListener;

	public interface InsertReviewDialogListener {

		public void postReview(DialogInterface dialog, String commento,
				int voto, Long mensaId, Long piattoId);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (InsertReviewDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement InsertReviewDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				getSherlockActivity());
		// Inflate and set the layout for the dialog, pass null as the parent
		// view because its going in the dialog layout
		LayoutInflater inflator = getSherlockActivity().getLayoutInflater();
		View dialogView = inflator.inflate(
				R.layout.layout_igradito_dialog_insert_review, null);
		// Get Header TextView-> Mensa name
		piattoNameTextView = (TextView) dialogView
				.findViewById(R.id.custom_dialog_header);
		// Get editText associated with this view
		userReviewEditText = (EditText) dialogView
				.findViewById(R.id.custom_dialog_etext);
		// Get the seekbar asscociated with this view
		barUserValutation = (SeekBar) dialogView
				.findViewById(R.id.recensioni_seekbar);

		// get the arguments passed invoking the dialog
		Bundle argsBundle = getArguments();
		// get mensa and piatto and setup titles
		mensa = (Mensa) argsBundle.getSerializable(MENSA);
		piatto = (Piatto) argsBundle.getSerializable(PIATTO);
		// get favourite mensa if not passed
		if (mensa == null) {
			mensa = MensaUtils.getFavouriteMensa(getSherlockActivity());
		}
		// Add a title to the dialog
		builder.setTitle(mensa.getMensa_nome());
		// set text to the name of the mensa
		piattoNameTextView.setText(piatto.getPiatto_nome());
		// get my comment and vote if available
		String mioCommento = argsBundle.getString(COMMENTO);
		Integer mioVoto = argsBundle.getInt(VOTO);
		// fill the fields
		if (mioCommento != null) {
			userReviewEditText.setText(mioCommento);
		}
		if (mioVoto != null) {
			barUserValutation.setProgress(mioVoto);
		} else {
			barUserValutation.setProgress(5);
		}

		// SHOW KEYBOARD
		userReviewEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						userReviewEditText.post(new Runnable() {
							@Override
							public void run() {
								InputMethodManager imm = (InputMethodManager) getSherlockActivity()
										.getSystemService(
												Context.INPUT_METHOD_SERVICE);
								imm.showSoftInput(userReviewEditText,
										InputMethodManager.SHOW_IMPLICIT);
							}
						});
					}
				});
		userReviewEditText.requestFocus();
		// set the view
		builder.setView(dialogView);

		// Add action buttons
		builder.setPositiveButton(
				getString(R.string.iGradito_dialog_button_add_text),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						String commento = userReviewEditText.getText()
								.toString();
						int voto = barUserValutation.getProgress();

						mListener.postReview(dialog, commento, voto,
								mensa.getMensa_id(), piatto.getPiatto_id());

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