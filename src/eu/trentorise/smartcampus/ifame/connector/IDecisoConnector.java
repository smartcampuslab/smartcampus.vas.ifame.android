package eu.trentorise.smartcampus.ifame.connector;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.ifame.model.MenuDelGiorno;
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

public class IDecisoConnector extends AsyncTask {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";

	public IDecisoConnector(Context applicationContext) {
		context = applicationContext;
	}

	private MenuDelGiorno getMenuDelGiorno() {
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

				MenuDelGiorno mdg = Utils.convertJSONToObject(body,
						MenuDelGiorno.class);

				return mdg;

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

		return getMenuDelGiorno();
	}
}
