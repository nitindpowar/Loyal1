package com.loyal.one.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxingr.client.android.CaptureActivity;
import com.loyal.one.R;
import com.loyal.one.activities.POJO.CardItemPOJO;
import com.loyal.one.activities.POJO.StandardCardItemPOJO;
import com.loyal.one.activities.adapter.CardBaseAdapter;
import com.loyal.one.activities.adapter.StdCardBaseAdapter;

public class MainActivity extends Activity implements OnClickListener {
	
	protected static int choice = -2;
	protected static int customerId = -1;
	protected static int deviceId = -1;
	protected static int rsaId = -1;
	private TextView searchTextView;
	private static final int CAMERA_REQUEST = 1888; 
	 private ImageView imageView;
	

	private ListView cardListView;
	private ArrayList<CardItemPOJO> cardItems;

	private ListView stdCardListView;
	private ArrayList<StandardCardItemPOJO> stdCardItems;
	private StdCardBaseAdapter stdCardBaseAdapter;
	
	
	private CardBaseAdapter CardBaseAdapter;
	private String TAG="MainActivity"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		cardListView = (ListView) findViewById(R.id.list1);		
//		cardItems=new ArrayList<CardItem>() ;
		Holder.QR_DATA="";
		cardItems=getCardData();
		CardBaseAdapter = new CardBaseAdapter(this , cardItems);		
		cardListView.setAdapter(CardBaseAdapter);		
		
		cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CardItemPOJO cardItemPOJO=(CardItemPOJO) parent.getItemAtPosition(position);
				Toast.makeText(MainActivity.this ,"CardItemPOJO : "+ cardItemPOJO.toString(),Toast.LENGTH_LONG).show();
				

				
			}
			
		});
		
	}

	private ArrayList<CardItemPOJO> getCardData() {
		// TODO Auto-generated method stub
		ArrayList<CardItemPOJO> aa =new ArrayList<CardItemPOJO>();
		
		CardItemPOJO c1= new CardItemPOJO(null,"Bigbazr card");
		CardItemPOJO c2= new CardItemPOJO(null,"Star bazar card");
		CardItemPOJO c3= new CardItemPOJO(null,"food shop card");
		CardItemPOJO c4= new CardItemPOJO(null,"benifite card");
	
		aa.add(c1);
		aa.add(c2);
		aa.add(c3);
		aa.add(c4);
		
		return aa;	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
        case R.id.action_add:
            openAdd();
            return true; 
            
        default:
            return super.onOptionsItemSelected(item);
    }	
	}

	private void openAdd() {
		// TODO Auto-generated method stub
//		Intent intent = new Intent(this, StandardCardsActivity.class);
//		startActivity(intent);
		
//		Intent captureIntent= new Intent(this, CaptureActivity.class);
//		startActivityForResult(captureIntent,0);
		
		
		
//		showStdCardDialog("Select Card Type");
		Intent intentN= new Intent(MainActivity.this,CreateCardActivity.class);
    	startActivity(intentN);
		
		
	}
	
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
			// TODO Auto-generated method stub
			System.out.println(TAG + " @@@@@@@onActivityResult");
			System.out.println(TAG + " @@@@@@@@@@requestCode"+requestCode+", RESULT_OK "+resultCode);
			
			 if (requestCode == 0) {
			        if (resultCode == RESULT_OK) {
			        	System.out.println(TAG + " 1");
			            String contents = intent.getStringExtra("SCAN_RESULT");
			            System.out.println(TAG + " 2");
			            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
			            System.out.println(TAG + " 3");
//			            showMyDialog("QRCode", contents);			            
			            System.out.println(TAG+"contents : " + contents + "\n format : " + format);			        	
			        	String qr_data = intent.getExtras().getString("QR_DATA");
			        	System.out.println("QR_DATA : " + qr_data);
			        	showMyDialog("QRCode", qr_data,"mESSAGE");	
			        	Holder.CardHolder.setQR_DATA(qr_data);			        	
			        	Intent intentN= new Intent(this,CreateCardActivity.class);
			        	startActivity(intentN);
			            // Handle successful scan
			        } else if (resultCode == RESULT_CANCELED) {
			            // Handle cancel
			        }
			    }

		}
	 /*
		@Override
		public void onWindowFocusChanged(boolean hasFocus) {
			// TODO Auto-generated method stub
			super.onWindowFocusChanged(hasFocus);
			if(hasFocus){
				System.out.println("onWindowFocusChanged : " + TAG);
				
				if (!Holder.QR_DATA.trim().equals("")) {
					System.out.println("!!!!!QR_DATA6546 : " + Holder.QR_DATA);
				}
//				
				if(!Holder.QR_DATA.trim().equals("")){
//					
			    	System.out.println("!!!!!QR_DATA6546 : " + Holder.QR_DATA);
//			    	showMyDialog("QR- CODE", Holder.QR_DATA, "Message");		    	
				}
			}
		}
*/
		private void showMyDialog(final String code, String title, String message) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			LayoutInflater li = LayoutInflater.from(MainActivity.this);
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

		
		
		private void showStdCardDialog( String title) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			LayoutInflater li = LayoutInflater.from(MainActivity.this);
			View alertView = li.inflate(R.layout.standard_card_dialog, null);
			builder.setView(alertView);
			builder.setCancelable(true);

			final AlertDialog dialog = builder.create();
			dialog.show();

			// Remove padding from parent
			ViewGroup parent = (ViewGroup) alertView.getParent();
			parent.setPadding(0, 0, 0, 0);

			TextView titleText = (TextView) alertView.findViewById(R.id.titleMsg);
			titleText.setText(title);
			
			
			stdCardListView = (ListView)  alertView.findViewById(R.id.list_cards);		
//			cardItems=new ArrayList<CardItem>() ;
			
			stdCardItems =getStdCardData();
			stdCardBaseAdapter = new StdCardBaseAdapter(getApplicationContext() , stdCardItems);

			
			stdCardListView.setAdapter(stdCardBaseAdapter);		
			
			stdCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					StandardCardItemPOJO cardItemPOJO=(StandardCardItemPOJO) parent.getItemAtPosition(position);
					Toast.makeText(MainActivity.this ,"CardItemPOJO : "+ cardItemPOJO.toString(),Toast.LENGTH_LONG).show();
/*					Intent intent = new Intent(MainActivity.this, CreateCardActivity.class);
					intent.putExtra("cardName", cardItemPOJO.getCardName());
					intent.putExtra("imageName", cardItemPOJO.getImage());
					startActivity(intent);
					*/					
//					Intent captureIntent= new Intent(MainActivity.this, CaptureActivity.class);
//					startActivityForResult(captureIntent,0);
//					
					Intent intentN= new Intent(MainActivity.this,CreateCardActivity.class);
		        	startActivity(intentN);
					
				}
				
			});
			

			 
		}


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
		
		public void scan(View view) {
			System.out.println(TAG + " scan");

			/*try {
				Intent intent = new Intent("com.google.zxingr.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
			} catch (ActivityNotFoundException e) {
				// TODO: handle exception
				showMyDialog("QRCODE", "QRCODE scanner not found. Would you like to install.");
				
			}*/
			Intent captureIntent= new Intent(this, CaptureActivity.class);
			startActivityForResult(captureIntent,0);

		}
	 
		private ArrayList<StandardCardItemPOJO> getStdCardData() {
			// TODO Auto-generated method stub
			ArrayList<StandardCardItemPOJO> aa =new ArrayList<StandardCardItemPOJO>();
			
			StandardCardItemPOJO c1= new StandardCardItemPOJO("Bigbazr std card","bigbazar");
			StandardCardItemPOJO c2= new StandardCardItemPOJO("Star bazar std card","starbazar");
			StandardCardItemPOJO c3= new StandardCardItemPOJO("Payback std card","payback");
			StandardCardItemPOJO c4= new StandardCardItemPOJO("AirIndia std card","airindia");
		
			aa.add(c1);
			aa.add(c2);
			aa.add(c3);
			aa.add(c4);
			
			return aa;	
		}
		
	 
}
