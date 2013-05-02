package eu.trentorise.smartcampus.ifame.connector;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.model.PiattiList;
import eu.trentorise.smartcampus.ifame.model.Piatto;
import eu.trentorise.smartcampus.ifame.model.Saldo;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

//import eu.trentorise.smartcampus.android.common.Utils;

public class IGraditoConnector extends AsyncTask {

	private ProtocolCarrier mProtocolCarrier;
	private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
	private static final String auth_token = "AUTH_TOKEN";
	private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

	public IGraditoConnector(Context applicationContext) {
		context = applicationContext;
	}

	private PiattiList getPiatti() {
		// try {

		mProtocolCarrier = new ProtocolCarrier(context, appToken);

		MessageRequest request = new MessageRequest(
				"http://smartcampuswebifame.app.smartcampuslab.it",
				"getallpiatti");
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier
					.invokeSync(request, appToken, authToken);

			if (response.getHttpStatus() == 200) {

				String body = response.getBody();

				PiattiList pl = Utils.convertJSONToObject(body,
						PiattiList.class);

				if (pl != null) {
					System.out.println("ce l'ho fatta");
				}
				return pl;

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

		return getPiatti();
	}
}
