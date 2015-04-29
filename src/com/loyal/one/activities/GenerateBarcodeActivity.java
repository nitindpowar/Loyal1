package com.loyal.one.activities;

import java.util.EnumMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loyal.one.R;

public class GenerateBarcodeActivity extends Activity {

	private String TAG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String barcode_data = getIntent().getExtras().getString("ENCODE_DATA");
		Log.i(TAG, "barcode_data : "+barcode_data);
		setContentView(R.layout.activity_generate_barcode);
		ImageView i1 = (ImageView) findViewById(R.id.imageView1);
		ImageView i2 = (ImageView) findViewById(R.id.imageView2);
		ImageView i3 = (ImageView) findViewById(R.id.imageView3);
		TextView t1 = (TextView) findViewById(R.id.textView1);
		

//		LinearLayout l = new LinearLayout(this);
//		l.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT));
//		l.setOrientation(LinearLayout.VERTICAL);
//		l.setGravity(Gravity.CENTER);
//		setContentView(l);

		// barcode data
//		String barcode_data = "12345123126";

		// barcode image
		Bitmap bitmap = null;
//		ImageView iv = new ImageView(this);
		try {

			bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128, 600,
					300);
			i1.setImageBitmap(bitmap);
			
			
			bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.PDF_417, 600,
					300);
			i2.setImageBitmap(bitmap);
			
			bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.QR_CODE, 600,
					300);
			i3.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		

//		l.addView(iv);

		
		
		// barcode text
//		TextView tv = new TextView(this);
//		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		t1.setText(barcode_data);

//		l.addView(tv);

	}

	/**************************************************************
	 * getting from com.google.zxing.client.android.encode.QRCodeEncoder
	 * 
	 * See the sites below http://code.google.com/p/zxing/
	 * http://code.google.com
	 * /p/zxing/source/browse/trunk/android/src/com/google/
	 * zxing/client/android/encode/EncodeActivity.java
	 * http://code.google.com/p/zxing
	 * /source/browse/trunk/android/src/com/google/
	 * zxing/client/android/encode/QRCodeEncoder.java
	 */

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