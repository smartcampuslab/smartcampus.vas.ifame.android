package eu.trentorise.smartcampus.ifame.asynctask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.ifame.activity.IFameMain;
import eu.trentorise.smartcampus.ifame.adapter.IGraditoPiattoListAdapter;
import eu.trentorise.smartcampus.ifame.comparator.PiattoComparator;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.utils.IFameUtils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;

/**
 * THIS CONNECTOR GETS ALL THE DISHES DATA
 */
public class GetPiattiIGraditoTask extends AsyncTask<Void, Void, List<Piatto>> {

	private Activity activity;
	private IGraditoPiattoListAdapter adapter;
	private LinearLayout progressBarLayout;

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
		// DISPLAY A PROGRESSBAR
		progressBarLayout = IFameUtils.setProgressBarLayout(activity);
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Piatto> result) {
		if (result == null) {
			// something went wrong
			Toast.makeText(activity,
					activity.getString(R.string.errorSomethingWentWrong),
					Toast.LENGTH_SHORT).show();
			activity.finish();
			return;

		} else {
			adapter.complete_list = result;
			adapter.clear();
			for (Piatto p : result) {
				if(!p.getPiatto_nome().equals(
						"Il servizio mensa e' sospeso"))
				adapter.add(p);
			}
			adapter.notifyDataSetChanged();
		}

		progressBarLayout.setVisibility(View.GONE);
	}
}