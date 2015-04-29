package com.loyal.one.activities;

import java.util.EnumMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxingr.client.android.CaptureActivity;
import com.loyal.one.R;

/***
 * Activity used the Search functionality.
 * 
 * @author mohamadazhar.inamdar
 */
public class CreateCardActivity extends Activity implements OnClickListener {

	protected static int choice = -2;
	protected static int customerId = -1;
	protected static int deviceId = -1;
	protected static int rsaId = -1;
	private TextView searchTextView;
	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;
	private EditText barcodeedittext;
	private ImageView imagebarcode;

	private static String TAG = "SearchActivity";

	/**
	 * Method called when activity is first created and used to generate the
	 * user interface
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_card);

		// Intent captureIntent= new Intent(this, CaptureActivity.class);
		// startActivityForResult(captureIntent,0);
		
		TextView textView = (TextView) findViewById(R.id.textView1);

		textView.setText(Holder.CardHolder.getQR_DATA().toString());
		imageView = (ImageView) findViewById(R.id.imageView1);
		imagebarcode = (ImageView) findViewById(R.id.cardBarcode);

		Button photoButton = (Button) findViewById(R.id.button1);
		photoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});

		imageView = (ImageView) findViewById(R.id.imageViewbarcode1);
		Button barcodeButton = (Button) findViewById(R.id.buttonbarcode1);
		barcodeedittext = (EditText) findViewById(R.id.edittextbarcode1);

		barcodeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new
				 * Intent("com.google.zxing.client.android.ENCODE");
				 * intent.setPackage("com.google.zxing.client.android");
				 * intent.putExtra("ENCODE_FORMAT", "QR_CODE");
				 * intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
				 * intent.putExtra("ENCODE_DATA",
				 * barcodeedittext.getText().toString()); try {
				 * startActivityForResult(intent,0); } catch
				 * (ActivityNotFoundException e) { startActivity(new
				 * Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +
				 * "com.google.zxing.client.android"))); }
				 */
				Log.i(TAG, "barcodeedittext : "
						+ barcodeedittext.getText().toString());
				Intent intent = new Intent(CreateCardActivity.this,
						GenerateBarcodeActivity.class);
				intent.putExtra("ENCODE_DATA", barcodeedittext.getText()
						.toString());
				startActivity(intent);

			}
		});
		//

		int resID = getResources().getIdentifier(
				"@drawable/" + Holder.CardHolder.getCardImageName(),
				"drawable", getPackageName());
		// prodImg.setImageResource(resID);

		ImageView frontImageTextView = (ImageView) findViewById(R.id.cardLogo);
		frontImageTextView.setImageResource(resID);

		Bitmap bitmap = null;
		try {
			bitmap = encodeAsBitmap(Holder.CardHolder.getQR_DATA().toString(), BarcodeFormat.CODE_128,
					600, 300);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imagebarcode.setImageBitmap(bitmap);

	}

	/**
	 * Method called when back Button(hardware key) is pressed
	 */

	/**
	 * Method called when a activity running gains focus
	 */

	/**
	 * Called when Activity's focus changes
	 */

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			System.out.println("onWindowFocusChanged : " + TAG);

			if (!Holder.QR_DATA.trim().equals("")) {

				System.out.println("!!!!!QR_DATA6546 : " + Holder.QR_DATA);
				// showMyDialog(getString(R.string.search), Holder.QR_DATA);
			}
		}
	}

	/**
	 * Called when searchBy parameter(Customer ID, Device Id...) changed
	 */
	/**
	 * Method called when any view on User Interface is clicked, provided it has
	 * a OnClickListener set.
	 */

	public void scan(View view) {
		System.out.println(TAG + " scan");

		/*
		 * try { Intent intent = new
		 * Intent("com.google.zxingr.client.android.SCAN");
		 * intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		 * startActivityForResult(intent, 0); } catch (ActivityNotFoundException
		 * e) { // TODO: handle exception showMyDialog("QRCODE",
		 * "QRCODE scanner not found. Would you like to install.");
		 * 
		 * }
		 */
		Intent captureIntent = new Intent(this, CaptureActivity.class);
		startActivityForResult(captureIntent, 0);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Called when Search button ic clicked
	 * 
	 * @param view
	 */

	/**
	 * Method which pops up a dialog to display the user some message or error.
	 * 
	 * @param title
	 *            Used to display the title of Pop Up
	 * @param message
	 *            Used to display the actual message
	 */

	/**
	 * Method used to show the user simple messages during validation
	 * 
	 * @param title
	 *            Used to display the title of Pop Up
	 * @param message
	 *            Used to display the actual message
	 */

	/**
	 * Switch from Currenct Activity to CustomerProfile Activity
	 * 
	 * @param customerPOJO
	 */

	/**
	 * A thread, which is used to fetch the Search results from webservice (Used
	 * in Search Tab).
	 * 
	 */
	/*
	 * protected void onActivityResult(int requestCode, int resultCode, Intent
	 * data) { if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
	 * Bitmap photo = (Bitmap) data.getExtras().get("data");
	 * imageView.setImageBitmap(photo); } }
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		System.out.println(TAG + " onActivityResult");
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// showMyDialog("QRCode", contents);

				System.out.println("contents : " + contents + "\n format : "
						+ format);

				String qr_data = intent.getExtras().getString("QR_DATA");
				System.out.println("QR_DATA : " + qr_data);
				// showMyDialog("QRCode", qr_data);

				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}

	}

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
			int img_height) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}
		Map<EncodeHintType, Object> hints = null;
		String encoding = guessAppropriateEncoding(contentsToEncode);
		if (encoding != null) {
			hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result;
		try {
			result = writer.encode(contentsToEncode, format, img_width,
					img_height, hints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	private static String guessAppropriateEncoding(CharSequence contents) {
		// Very crude at the moment
		for (int i = 0; i < contents.length(); i++) {
			if (contents.charAt(i) > 0xFF) {
				return "UTF-8";
			}
		}
		return null;
	}

}
