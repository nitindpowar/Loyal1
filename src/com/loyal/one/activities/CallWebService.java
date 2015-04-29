package com.loyal.one.activities;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

/**
 * Used for calling the webservice
 * 
 * @author mohamadazhar.inamdar
 * 
 */
public class CallWebService {

	public static String TAG = "CallWebService";
	public static String SOAP_ACTION;
	private static int TIME_OUT = 60000; // Setting timeout to 1 min

	public static SoapObject getResponseObject(String[] params,
			String methodName, Context context) throws Exception {

		/*
		 * create connection and send the request and return response
		 */

		SoapObject mainSoapObject = null;

		/*
		 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		 * 
		 * @@@@@ CheckIsDSA Method
		 * 
		 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		 * 
		 * @@@@@
		 */
		if (methodName.equalsIgnoreCase("GetCodeTypes")) {

			SOAP_ACTION = "\"" +Holder.ACTION + methodName+ "\"";
			mainSoapObject = new SoapObject(Holder.loy_Namespace, methodName);
		}
		Log.i(TAG, "MyRequest @@@@@@@  : " + mainSoapObject);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(mainSoapObject);
		Log.i(TAG, "before call");

		HttpTransportSE androidHttpTransport = new HttpTransportSE(Holder.URL,
				TIME_OUT);
		androidHttpTransport.debug = true;
		// HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		// System.setProperty("http.keepAlive", "true");

		// androidHttpTransport.reset();
		// androidHttpTransport.setTimeout(30000);
		androidHttpTransport.call(SOAP_ACTION, envelope);
		Log.i(TAG, "requestDump : " + androidHttpTransport.requestDump);
		Log.i(TAG, "responseDump : " + androidHttpTransport.responseDump);

		Log.i(TAG, "after call");

		if (envelope.getResponse() instanceof SoapObject) {
			Log.i(TAG, "response object of soapobject");
			Log.i(TAG, envelope.getResponse().toString());
			return (SoapObject)envelope.bodyIn;
//			return (SoapObject) envelope.getResponse();
		} else {
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			Log.i(TAG, "response object of soapPrimitive");
			Log.i(TAG, result.toString());
		}

		return null;
	}

	// ServiceConnectionSE

	/*
	 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ HTTP Call to WebService
	 * 
	 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	 */
	/*
	 * public static String callWebService(String methodName, String envelope)
	 * throws Exception {
	 * 
	 * Log.i(TAG, "Calling GetSchemas Africa Webservice");
	 * 
	 * SOAP_ACTION = Holder.ACTION + methodName;
	 * 
	 * final DefaultHttpClient httpClient = new DefaultHttpClient();
	 * 
	 * // request parameters
	 * 
	 * HttpParams params = httpClient.getParams();
	 * 
	 * HttpConnectionParams.setConnectionTimeout(params, 10000);
	 * 
	 * HttpConnectionParams.setSoTimeout(params, 15000);
	 * 
	 * // set parameter
	 * 
	 * HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);
	 * 
	 * // POST the envelope
	 * 
	 * HttpPost httppost = new HttpPost(Holder.URL);
	 * 
	 * // add headers
	 * 
	 * httppost.setHeader("soapaction", SOAP_ACTION);
	 * 
	 * httppost.setHeader("Content-Type", "text/xml; charset=utf-8");
	 * 
	 * String responseString = "";
	 * 
	 * try {
	 * 
	 * // the entity holds the request
	 * 
	 * HttpEntity entity = new StringEntity(envelope);
	 * 
	 * httppost.setEntity(entity);
	 * 
	 * // Response handler
	 * 
	 * ResponseHandler<String> rh = new ResponseHandler<String>() {
	 * 
	 * // invoked when client receives response
	 * 
	 * public String handleResponse(HttpResponse response) throws
	 * ClientProtocolException, IOException {
	 * 
	 * HttpEntity entity = response.getEntity();
	 * 
	 * // read the response as byte array
	 * 
	 * StringBuffer out = new StringBuffer();
	 * 
	 * byte[] b = EntityUtils.toByteArray(entity);
	 * 
	 * // write the response byte array to a string buffer
	 * 
	 * out.append(new String(b, 0, b.length));
	 * 
	 * return out.toString();
	 * 
	 * } };
	 * 
	 * responseString = httpClient.execute(httppost, rh);
	 * 
	 * Log.i(TAG, "Response Xml : " + responseString);
	 * 
	 * } catch (ClientProtocolException e) {
	 * 
	 * // TODO Auto-generated catch block
	 * 
	 * e.printStackTrace();
	 * 
	 * throw new Exception(e.getMessage());
	 * 
	 * } catch (SocketException e) { e.printStackTrace(); Log.e(TAG,
	 * "SocketException : " + e.getMessage());
	 * 
	 * throw new Exception(e.getMessage()); } catch (SocketTimeoutException e) {
	 * e.printStackTrace(); // System.out.println(e.getClass().getName() + ": "
	 * // + e.getMessage()); Log.e(TAG, "SocketTimeoutException : " +
	 * e.getMessage()); throw new Exception(e.getMessage()); } catch
	 * (IOException e) { e.printStackTrace(); //
	 * System.out.println(e.getClass().getName() + ": " // + e.getMessage());
	 * Log.e(TAG, "IOException : " + e.getMessage()); throw new
	 * Exception(e.getMessage()); } catch (Exception e) { e.printStackTrace();
	 * Log.e(TAG, "Exception : " + e.getMessage()); throw new
	 * Exception(e.getMessage()); } finally {
	 * 
	 * // close the connection
	 * 
	 * httpClient.getConnectionManager().shutdown();
	 * 
	 * }
	 * 
	 * return responseString;
	 * 
	 * }
	 */

}
