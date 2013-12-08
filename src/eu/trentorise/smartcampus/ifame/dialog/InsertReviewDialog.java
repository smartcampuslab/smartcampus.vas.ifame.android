package eu.trentorise.smartcampus.ifame.dialog;

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
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IGraditoVisualizzaRecensioni;
import eu.trentorise.smartcampus.ifame.activity.MenuDelGiornoActivity;
import eu.trentorise.smartcampus.ifame.activity.MenuDelMeseActivity;
import eu.trentorise.smartcampus.ifame.asynctask.PostGiudizioAsyncTask;
import eu.trentorise.smartcampus.ifame.model.GiudizioDataToPost;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.MensaUtils;
import eu.trentorise.smartcampus.ifame.utils.UserIdUtils;

/**
 * Custom dialog interface to add or edit own review
 */
public class InsertReviewDialog extends SherlockDialogFragment {

	/** These are the key of the bundle objects to pass when calling the dialog */
	public static final String MENSA = "mensa_extra";
	public static final String COMMENTO = "mio_commento";
	public static final String VOTO = "mio_voto";
	public static final String PIATTO = "piatto_extra";
	// public static final String USERID = "user_id";

	private EditText userReviewEditText;
	private TextView piattoNameTextView;
	private SeekBar barUserValutation;
	// private int voto;

	private Mensa mensa;
	private SherlockFragmentActivity activity;

	private MenuItem refreshButton;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				getSherlockActivity());
		// Inflate and set the layout for the dialog, pass null as the parent
		// view because its going in the dialog layout
		LayoutInflater inflator = getSherlockActivity().getLayoutInflater();
		View dialogView = inflator.inflate(
				R.layout.layout_igradito_dialog_insert_review, null);

		// get the arguments passed invoking the dialog
		Bundle argsBundle = getArguments();
		mensa = (Mensa) argsBundle.getSerializable(MENSA);
		final Piatto piatto = (Piatto) argsBundle.getSerializable(PIATTO);
		String mioCommento = argsBundle.getString(COMMENTO);
		Integer mioVoto = argsBundle.getInt(VOTO);

		if (mensa == null) {
			mensa = MensaUtils.getFavouriteMensa(getSherlockActivity());
		}

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
		} else {
			barUserValutation.setProgress(5);
		}
		// voto = barUserValutation.getProgress();

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

		builder.setView(dialogView);

		activity = getSherlockActivity();

		if (activity instanceof IGraditoVisualizzaRecensioni) {
			refreshButton = ((IGraditoVisualizzaRecensioni) activity)
					.getRefreshButton();
		} else if (activity instanceof MenuDelMeseActivity) {
			refreshButton = ((MenuDelMeseActivity) activity).getRefreshButton();
		} else if (activity instanceof MenuDelGiornoActivity) {
			refreshButton = ((MenuDelGiornoActivity) activity)
					.getRefreshButton();
		} else {
			refreshButton = null;
		}

		// Add action buttons
		builder.setPositiveButton(
				getString(R.string.iGradito_dialog_button_add_text),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						String userId = UserIdUtils
								.getUserId(getSherlockActivity());

						GiudizioDataToPost giudizioDataToPost = new GiudizioDataToPost();
						giudizioDataToPost.commento = userReviewEditText
								.getText().toString();
						giudizioDataToPost.userId = Long.parseLong(userId);
						giudizioDataToPost.voto = (float) barUserValutation
								.getProgress();

						new PostGiudizioAsyncTask(activity, giudizioDataToPost,
								refreshButton).execute(mensa.getMensa_id(),
								piatto.getPiatto_id());

						getDialog().cancel();
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