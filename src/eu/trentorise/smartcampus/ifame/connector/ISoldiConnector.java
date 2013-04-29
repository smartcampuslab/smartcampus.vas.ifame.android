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
import eu.trentorise.smartcampus.ifame.model.Mensa;
import eu.trentorise.smartcampus.ifame.model.Saldo;
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

public class ISoldiConnector extends AsyncTask {

	private ProtocolCarrier mProtocolCarrier;
	private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
	private static final String auth_token = "AUTH_TOKEN";
	private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

	public ISoldiConnector(Context applicationContext) {
		context = applicationContext;
	}

	private Saldo getSaldo() {
		// try {

		mProtocolCarrier = new ProtocolCarrier(context, appToken);

		MessageRequest request = new MessageRequest(
				"http://smartcampuswebifame.app.smartcampuslab.it", "getsoldi");
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier
					.invokeSync(request, appToken, authToken);

			if (response.getHttpStatus() == 200) {

				String body = response.getBody();

				return Utils.convertJSONToObject(body, Saldo.class);

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
		return null;

		
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub

		return getSaldo();
	}
}
