package com.loyal.one.activities;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.loyal.one.R;

public class LoginActivity extends Activity  implements OnClickListener{

	private String TAG="LoginActivity";
	private HashMap<String, String> codeTypeMap;
	private Spinner codeTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		codeTypeMap = new HashMap<String, String>();
		codeTypeSpinner = (Spinner) this
				.findViewById(R.id.schema_spinner_login);
		codeTypeSpinner
				.setOnItemSelectedListener(new CountrySelectedListener());
		
		
		new GetCodeTypesTask().execute((String[])null);
		
		
	}
	
	public void register(View v) {
		Log.i(TAG ,"Register   old");
		Intent intent = new Intent (this,RegisterActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		

		default:
			break;
		}
	}
	
	class GetCodeTypesTask extends AsyncTask<String , Integer , SoapObject>{
		ProgressDialog pd = null;
		private String exception;
		private String message;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = ProgressDialog.
					show(LoginActivity.this, "", "Connecting...",
					true);
		}
		@Override
		protected SoapObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try{SoapObject soapObject = null;
			soapObject = CallWebService.getResponseObject(params,
					"GetCodeTypes", null);
			return soapObject;
			}
			 catch (SoapFault e) {
					e.printStackTrace();
					// System.out.println(e.getClass().getName() + ": "
					// + e.getMessage());
					Log.e(TAG, "SoapFault faultstring: " + e.faultstring);
					
					return null;
				} catch (SocketException e) {
					e.printStackTrace();
					// System.out.println(e.getClass().getName() + ": "
					// + e.getMessage());
					Log.e(TAG, "SocketException : " + e.getMessage());
					
					return null;
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					// System.out.println(e.getClass().getName() + ": "
					// + e.getMessage());
					Log.e(TAG, "SocketTimeoutException : " + e.getMessage());
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					// System.out.println(e.getClass().getName() + ": "
					// + e.getMessage());
					Log.e(TAG, "IOException : " + e.getMessage());
					return null;
				} catch (XmlPullParserException e) {
					// System.out.println(e.getClass().getName() + ": "
					// + e.getMessage());
					e.printStackTrace();
					Log.e(TAG, "XmlPullParserException : " + e.getMessage());
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, "Exception : " + e.getMessage());
					return null;
				}
		}
		
		@Override
		protected void onPostExecute(SoapObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			
			if(result!=null)
				{
				Log.i(TAG, "GetCodeTypeTask onPostExecute :   " + result);
				populateCodeTypeSpinner(populateCodeTypeLists(result));
				}else {
					if (exception.equalsIgnoreCase("SoapFault")) {
						showMyDialog("GetCodeType", getString(R.string.app_name),
								message);
					} else {
						showMyDialog("GetCodeType", getString(R.string.error),
								getString(R.string.couldNotConnectError));
					}

				}
		
			
		}
		
	}
	
	private void populateCodeTypeSpinner(ArrayList<String> arrayList) {

		ArrayAdapter<String> arrayAdapter = null;

		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrayList);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// int temp = arrayList.indexOf(codeTypeString);
		// System.out.println("Schema Position is : " + temp);
		codeTypeSpinner.setAdapter(arrayAdapter);
		// if (temp != -1) {
		// codeTypeSpinner.setSelection(temp);
		// }
	}

	private void showMyDialog(final String code, String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginActivity.this);
		LayoutInflater li = LayoutInflater.from(LoginActivity.this);
		View alertView = li.inflate(R.layout.errordialog, null);
		builder.setView(alertView);
		builder.setCancelable(false);

		final AlertDialog dialog = builder.create();
		dialog.show();

		// Remove padding from parent
		ViewGroup parent = (ViewGroup) alertView.getParent();
		parent.setPadding(0, 0, 0, 0);

		TextView titleText = (TextView) alertView.findViewById(R.id.titleError);
		titleText.setText(title);

		TextView dialogtv = (TextView) alertView.findViewById(R.id.dialogtv);
		LinearLayout.LayoutParams lp = (LayoutParams) dialogtv
				.getLayoutParams();
		lp.setMargins(0, 10, 0, 10);
		dialogtv.setLayoutParams(lp);

		dialogtv.setText(message);

		Button okButton = (Button) alertView.findViewById(R.id.yesBtn);
		okButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (code.equals("GetCodeTypes")) {
					dialog.dismiss();
					finish();
				} else {
					dialog.dismiss();
				}
			}
		});
	}

	public ArrayList<String> populateCodeTypeLists(SoapObject soapObject) {

		Log.i(TAG, "populateCodeTypeLists - soapObject : " + soapObject.toString());
		ArrayList<String> codeTypeArrayList = new ArrayList<String>();
		soapObject = (SoapObject) soapObject.getProperty(0);
		int count = soapObject.getPropertyCount();
		Log.i(TAG, "count : " + count);

		for (int i = 0; i < count; i++) {
			Log.i(TAG, "in For Loop"+i);
			if(soapObject.getProperty(i) instanceof SoapPrimitive)
			{
				Log.i(TAG, " instanceof SoapPrimitive");
				SoapPrimitive sp = (SoapPrimitive) soapObject.getProperty(i);
			}
			
			
			SoapObject codeTypeObject = (SoapObject) soapObject.getProperty(i);
			Log.i(TAG, "in For Loop"+codeTypeObject.getProperty("CodeTypeId").toString());
			codeTypeMap.put(codeTypeObject.getProperty("CodeTypeId").toString(),
					codeTypeObject.getProperty("CodeTypeName").toString());
			codeTypeArrayList.add(codeTypeObject.getProperty("CodeTypeName").toString());
		}
		Log.i(TAG, "schemasArrayList" + codeTypeArrayList.size());
		return codeTypeArrayList;

	}
	
	public class CountrySelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> schemaAdapter, View view,
				int pos, long id) {
			/*countryString = schemaAdapter.getItemAtPosition(pos).toString();
			Log.i(tag, "CountryString : " + countryString);
			if (countryMap.containsKey(countryString))
				Log.i(tag, "SchemaString :" + countryMap.get(countryString));*/
			/*
			 * if(schemaString.contains("South_Africa")){ Holder.isSchemaAfrican
			 * = false; }else{ Holder.isSchemaAfrican = true; }
			 */
		}

		public void onNothingSelected(AdapterView<?> schemaAdapter) {
			/*countryString = schemaAdapter.getItemAtPosition(0).toString();
			Log.i(tag, "CountryString : " + countryString);
			if (countryMap.containsKey(countryString))
				Log.i(tag, "SchemaString :" + countryMap.get(countryString));*/

		}
	}
	
}
