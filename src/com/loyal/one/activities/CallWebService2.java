package com.loyal.one.activities;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class CallWebService2 {

	private static String URL;
	private static String TAG="CallWebService";
	private static String SOAP_ACTION;
	private static int TIME_OUT=60000;//Time for 2 mins

	public static SoapObject getResponseObject(String[] params, String methodName,
			Object object) throws Exception {

		/*
		 * create connection and send the request and return response
		 */
		URL = Holder.URL;
//		URL = "http://197.85.182.132/loyal1service/Loyal1Service.svc";
		SoapObject request = null;
		
		if (methodName.equalsIgnoreCase("GetCodeTypes")) {
			Log.i(TAG, "Calling GetCodeTypeList Africa Webservice");
			SOAP_ACTION = "\"" + Holder.ACTION + methodName + "\"";
			request = new SoapObject(Holder.loy_Namespace, methodName);
			Log.i(TAG,"SOAP_ACTION @@@@@@@  : " + SOAP_ACTION);
//			Log.i(TAG,"request @@@@@@@  : " + request);
		}
		

		Log.i(TAG,"MyRequest @@@@@@@  : " + request);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		Log.i(TAG,"before call" );
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,
				TIME_OUT);
		// HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//		Log.i(TAG, "requestDump : " + androidHttpTransport.requestDump);
		// System.setProperty("http.keepAlive", "true");
		androidHttpTransport.debug = true;
		// androidHttpTransport.reset();
		// androidHttpTransport.setTimeout(30000);
		Log.i(TAG, "Envelope : " + envelope.toString());
		androidHttpTransport.call(SOAP_ACTION, envelope);
		
		Log.i(TAG, "requestDump : " + androidHttpTransport.requestDump);
		Log.i(TAG, "responseDump : " + androidHttpTransport.responseDump);
		Log.i(TAG, "after call");

		if (envelope.getResponse() instanceof SoapObject) {
			/*
			 * Log.i(TAG, "response object of soapobject"); Log.i(TAG,
			 * envelope.getResponse().toString());
			 */
			Log.i(TAG, "SoapObject");
			return (SoapObject) envelope.getResponse();
		} else {
			Log.i(TAG, "SoapPrimitive");
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			/*
			 * Log.i(TAG, "response object of soapPrimitive"); Log.i(TAG,
			 * result.toString());
			 */
		}

		return null;
	}



}
