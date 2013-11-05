package eu.trentorise.smartcampus.ifame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IGraditoVisualizzaRecensioni;
import eu.trentorise.smartcampus.ifame.asynctask.PostGiudizioAsyncTask;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;

/**
 * Custom dialog interface to add or edit own review
 */
public class InsertReviewDialog extends DialogFragment {

	/** These are the key of the bundle objects to pass when calling the dialog */
	public static final String MENSA = "mensa_extra";
	public static final String COMMENTO = "mio_commento";
	public static final String VOTO = "mio_voto";
	public static final String PIATTO = "piatto_extra";
	public static final String USERID = "user_id";

	private EditText userReviewEditText;
	private TextView piattoNameTextView;
	private SeekBar barUserValutation;
	private int voto = 0;
	private GiudizioDataToPost giudizioDataToPost;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Inflate and set the layout for the dialog, pass null as the parent
		// view because its going in the dialog layout
		LayoutInflater inflator = getActivity().getLayoutInflater();
		View dialogView = inflator.inflate(R.layout.igradito_custom_dialogbox,
				null);

		// get the arguments passed invoking the dialog
		Bundle argsBundle = getArguments();
		final Mensa mensa = (Mensa) argsBundle.get(MENSA);
		final Piatto piatto = (Piatto) argsBundle.get(PIATTO);
		final String userId = argsBundle.getString(USERID);
		String mioCommento = argsBundle.getString(COMMENTO);
		Integer mioVoto = argsBundle.getInt(VOTO);

		// Add a title to the dialog
		builder.setTitle(mensa.getMensa_nome());
		// Get Header TextView-> Mensa name
		piattoNameTextView = (TextView) dialogView
				.findViewById(R.id.custom_dialog_header);
		// set text to the name of the mensa
		piattoNameTextView.setText(piatto.getPiatto_nome());
		// Get editText associated with this view
		userReviewEditText = (EditText) dialogView
				.findViewById(R.id.custom_dialog_etext);

		// Get the seekbar asscociated with this view
		barUserValutation = (SeekBar) dialogView
				.findViewById(R.id.recensioni_seekbar);
		if (mioCommento != null) {
			userReviewEditText.setText(mioCommento);
		}
		if (mioVoto != null) {
			barUserValutation.setProgress(mioVoto);
		}
		voto = barUserValutation.getProgress();
		// SHOW KEYBOARD
		userReviewEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						userReviewEditText.post(new Runnable() {
							@Override
							public void run() {
								InputMethodManager imm = (InputMethodManager) getActivity()
										.getSystemService(
												Context.INPUT_METHOD_SERVICE);
								imm.showSoftInput(userReviewEditText,
										InputMethodManager.SHOW_IMPLICIT);
							}
						});
					}
				});
		userReviewEditText.requestFocus();

		// Add Listener to valutation bar
		barUserValutation
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						voto = progress;
					}
				});

		builder.setView(dialogView);
		// Add action buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				giudizioDataToPost = new GiudizioDataToPost();
				giudizioDataToPost.commento = userReviewEditText.getText()
						.toString();
				giudizioDataToPost.userId = Long.parseLong(userId);
				giudizioDataToPost.voto = (float) voto;

				// if (adapter != null) {
				// adapter.clear();
				// adapter.notifyDataSetChanged();
				// }
				new PostGiudizioAsyncTask(
						(IGraditoVisualizzaRecensioni) getActivity(),
						giudizioDataToPost).execute(mensa.getMensa_id(),
						piatto.getPiatto_id());

				getDialog().cancel();
			}
		});
		builder.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						getDialog().cancel();
					}
				});
		return builder.create();
	}
}