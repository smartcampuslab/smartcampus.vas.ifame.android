package eu.trentorise.smartcampus.ifame.connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.FileRequestParam;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.custom.RequestParam;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

//import eu.trentorise.smartcampus.android.common.Utils;

public class IFrettaConnector extends AsyncTask {

	private ProtocolCarrier mProtocolCarrier;
	private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getmense";
	private static final String auth_token = "AUTH_TOKEN";
	private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public  Context context;
	public String appToken="test smartcampus";
	public String authToken= "aee58a92-d42d-42e8-b55e-12e4289586fc";

	public IFrettaConnector(Context applicationContext) {
		context=applicationContext;
	}

	private void connect() {
		//try {
			
			mProtocolCarrier = new ProtocolCarrier(context, appToken);
			
			MessageRequest request = new MessageRequest("http://smartcampuswebifame.app.smartcampuslab.it","getmense");
			request.setMethod(Method.GET);
			
		
			MessageResponse response;
			try {
				response = mProtocolCarrier.invokeSync(request,
						appToken, authToken);
				
				if (response.getHttpStatus() == 200) {
					
					List<Mensa> list = null;
				

					// List<Mensa> list =
					list = Utils.convertJSONToObjects(response.getBody(),Mensa.class);
					Iterator<Mensa> i = list.iterator();
					while (i.hasNext()) {
						System.out.println(((Mensa) i.next()).toString());
					}
				} else {
					
				}
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			/*

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
					list = Utils.convertJSONToObjects(EntityUtils.toString(entity),Mensa.class);
				

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
		}   */
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		connect();
		return null;
	}
}
