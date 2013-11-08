package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.adapter.IGraditoPiattoListAdapter;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.PiattoComparator;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

/**
 * THIS CONNECTOR GETS ALL THE DISHES DATA
 */
public class GetPiattiIGraditoTask extends AsyncTask<Void, Void, List<Piatto>> {

	private Activity activity;
	private IGraditoPiattoListAdapter adapter;
	private ProgressDialog progressDialog;
	private final String URL_BASE_WEB_IFAME;
	private final String APP_TOKEN;
	private final String PATH_IGRADITO_GETPIATTI;

	public GetPiattiIGraditoTask(Activity context,
			IGraditoPiattoListAdapter adapter) {
		this.activity = context;
		this.adapter = adapter;
		URL_BASE_WEB_IFAME = context.getString(R.string.URL_BASE_WEB_IFAME);
		APP_TOKEN = context.getString(R.string.APP_TOKEN);
		PATH_IGRADITO_GETPIATTI = context
				.getString(R.string.PATH_IGRADITO_GETPIATTI);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// DISPLAY A PROGRESSDIALOG AND GET THE USER TOKEN
		progressDialog = ProgressDialog.show(activity,
				activity.getString(R.string.iGradito_title_activity),
				activity.getString(R.string.loading));
		// progressDialog.setCancelable(true);
		// progressDialog.setCanceledOnTouchOutside(false);
	}

	@Override
	protected List<Piatto> doInBackground(Void... params) {

		ProtocolCarrier mProtocolCarrier = new ProtocolCarrier(activity,
				APP_TOKEN);
		MessageRequest request = new MessageRequest(URL_BASE_WEB_IFAME,
				PATH_IGRADITO_GETPIATTI);
		request.setMethod(Method.GET);

		try {
			MessageResponse response = mProtocolCarrier.invokeSync(request,
					APP_TOKEN, IFameMain.getAuthToken());

			if (response.getHttpStatus() == 200) {

				String body = response.getBody();

				List<Piatto> lista_piatti_temp = Utils.convertJSONToObjects(
						body, Piatto.class);

				Collections.sort(lista_piatti_temp, new PiattoComparator());

				// creo una nuova lista che oltre a contenere i piatti
				// contiene le lettere con cui essi iniziano : ("A",
				// "Anatre", "Ananas", "B", "Banane", "D", "Datteri"
				// ...)
				List<Piatto> lista_piatti_with_headers = new ArrayList<Piatto>();

				String letter = "A";

				lista_piatti_with_headers.add(new Piatto(letter, ""));

				for (int i = 0; i < lista_piatti_temp.size(); i++) {

					String nome_piatto = lista_piatti_temp.get(i)
							.getPiatto_nome();

					if (!nome_piatto.startsWith(letter)) {

						letter = Character.toString(nome_piatto.charAt(0));

						lista_piatti_with_headers.add(new Piatto(letter, ""));
					}

					lista_piatti_with_headers.add(lista_piatti_temp.get(i));
				}

				return lista_piatti_with_headers;
			} else {
				return null;
			}

		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(List<Piatto> result) {
		if (result == null) {
			Toast.makeText(activity,
					activity.getString(R.string.errorSomethingWentWrong),
					Toast.LENGTH_SHORT).show();
			activity.finish();
		} else {

			adapter.complete_list = result;
			adapter.clear();

			for (Piatto p : result) {
				adapter.add(p);
			}
		}
		progressDialog.dismiss();
	}
}