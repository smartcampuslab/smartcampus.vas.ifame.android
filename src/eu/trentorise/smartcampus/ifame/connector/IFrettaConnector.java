package eu.trentorise.smartcampus.ifame.connector;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import android.os.AsyncTask;
import android.util.Log;

//import eu.trentorise.smartcampus.android.common.Utils;

public class IFrettaConnector extends AsyncTask {

	private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getmense";
	private static final String auth_token = "AUTH_TOKEN";
	private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";

	private void connect() {
		try {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(URL);
			request.setHeader(auth_token, token_value);

			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				if (entity != null) {

					List<Mensa> list = null;
					System.out.println(EntityUtils.toString(entity));

					// List<Mensa> list =
					// Utils.convertJSONToObjects(EntityUtils.toString(entity),Mensa.class);
					/*
					 * List<Mensa> list = Utils.convertJSON(
					 * EntityUtils.toString(entity), new
					 * TypeReference<List<Mensa>>() { });
					 */

					Iterator<Mensa> i = list.iterator();
					while (i.hasNext()) {
						System.out.println(((Mensa) i.next()).toString());
					}
				}
			}
		} catch (ClientProtocolException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		} catch (IOException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		connect();
		return null;
	}
}
