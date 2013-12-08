package eu.trentorise.smartcampus.ifame.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.activity.MensaPreferita;
import eu.trentorise.smartcampus.ifame.comparator.MensaComparator;
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

public class MensaUtils {

	/**
	 * This is the time between two updates of the canteens available list and
	 * is set by default to one day
	 * 
	 * 24 h * 60 m * 60 s * 1000 ms
	 */
	private static final long TIME_IN_MILLIS = 24 * 60 * 60 * 1000;
	private final static String LAST_UPDATE = "get_last_update";

	private final static String FAVOURITE_MENSA_SHARED_PREFERENCES = "favourite_mensa_shared_preferences";

	private final static String MENSA_LIST = "mensa_list";
	private final static String FAVOURITE_MENSA = "favourite_mensa_object";
	private final static String FAVOURITE_MENSA_NAME = "favourite_mensa_name";

	private static boolean setLastUpdateNow(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		Long currentTimeInMillis = new Date().getTime();
		editor.putLong(LAST_UPDATE, currentTimeInMillis);

		return editor.commit();
	}

	/** returns true if an update is required false instead */
	private static boolean updateRequired(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		if (pref.contains(LAST_UPDATE)) {

			Long lastUpdate = pref.getLong(LAST_UPDATE, -1);
			Long now = new Date().getTime();

			return (now - lastUpdate) > TIME_IN_MILLIS;
		} else {
			return true;
		}
	}

	private static boolean setMensaList(Context context, List<Mensa> mensaList) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putString(MENSA_LIST, Utils.convertToJSON(mensaList));

		return editor.commit();
	}

	/**
	 * 
	 * When the app is initialized this will return always the canteens list
	 * otherwise get the list empty is a signal that application requires the
	 * initialization
	 * 
	 */
	public static ArrayList<Mensa> getMensaList(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		ArrayList<Mensa> listaMense = null;
		if (pref.contains(MENSA_LIST)) {
			String json = pref.getString(MENSA_LIST, null);
			listaMense = (ArrayList<Mensa>) Utils.convertJSONToObjects(json,
					Mensa.class);
		}
		// otherwise a check is required in the task or nullpointerexeption
		if (listaMense == null) {
			listaMense = new ArrayList<Mensa>();
		}
		return listaMense;
	}

	public static boolean setFavouriteMensa(Context context, Mensa mensa) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putString(FAVOURITE_MENSA, Utils.convertToJSON(mensa));
		editor.putString(FAVOURITE_MENSA_NAME, mensa.getMensa_nome());

		return editor.commit();
	}

	public static Mensa getFavouriteMensa(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		if (pref.contains(FAVOURITE_MENSA)) {
			return Utils.convertJSONToObject(
					pref.getString(FAVOURITE_MENSA, ""), Mensa.class);
		}
		return null;
	}

	public static String getFavouriteMensaName(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				FAVOURITE_MENSA_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		String name = "";
		if (pref.contains(FAVOURITE_MENSA_NAME)) {
			name = pref.getString(FAVOURITE_MENSA_NAME, name);
		}
		return name;
	}

	/** before call this method the mensa list must be in the sharedpreferences */
	public static void getAndSaveMensaList(Context context) {
		if (updateRequired(context)) {
			new GetMensaList(context).execute();
		}
	}

	private static class GetMensaList extends AsyncTask<Void, Void, Boolean> {

		private Context context;
		ProgressDialog progressdialog;

		public GetMensaList(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (getMensaList(context).isEmpty()) {
				progressdialog = ProgressDialog.show(context,
						context.getString(R.string.iFame_main_title_activity),
						context.getString(R.string.initializing));
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(context,
					context.getString(R.string.APP_TOKEN));
			MessageRequest request = new MessageRequest(
					context.getString(R.string.URL_BASE_WEB_IFAME),
					context.getString(R.string.PATH_IFRETTA_GETMENSE));
			request.setMethod(Method.GET);
			try {
				MessageResponse response = mProtocolCarrier.invokeSync(request,
						context.getString(R.string.APP_TOKEN),
						IFameMain.getAuthToken());

				if (response.getHttpStatus() == 200) {

					ArrayList<Mensa> listfromTheWeb = (ArrayList<Mensa>) Utils
							.convertJSONToObjects(response.getBody(),
									Mensa.class);
					ArrayList<Mensa> savedList = getMensaList(context);

					// set last update
					setLastUpdateNow(context);

					boolean savedListIsUpdated = true;
					for (Mensa mensa : listfromTheWeb) {
						if (!savedList.contains(mensa)) {
							savedListIsUpdated = false;
						}
					}

					if (!savedListIsUpdated) {

						Collections.sort(listfromTheWeb, new MensaComparator());

						setMensaList(context, listfromTheWeb);
						// set the first one as favourite because one is always
						// required
						setFavouriteMensa(context, listfromTheWeb.get(0));
						// return true only if the list of the webcam saved
						// requires an update otherwise false
						return true;
					} else {
						// la lista salvata è aggiornata
						return false;
					}
				} else {
					/** caso un po critico che non dovrebbe accadere */
					// response != 200
					return false;
				}
			} catch (Exception e) {
				Log.e(this.getClass().getName(), e.getMessage());
			}
			// some errors happens
			return false;
		}

		@Override
		protected void onPostExecute(Boolean showFavouriteMensaActivity) {
			super.onPostExecute(showFavouriteMensaActivity);

			if (progressdialog != null) {
				progressdialog.dismiss();
			}
			// solo se mi ritorna true devofare qualcosa perche
			// la lista è cambiata e devo far partire l' activity per settare
			// la nuova mensa preferita e mostrare la lista aggiornata
			if (showFavouriteMensaActivity) {
				Intent intent = new Intent(context, MensaPreferita.class);
				context.startActivity(intent);
			}
		}
	}

}
