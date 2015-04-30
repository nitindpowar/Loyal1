package com.loyal.one.activities.POJO;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class StandardCardItemPOJO {

	private String cardName;
	private String imageName;
	
	
	
	
	@Override
	public String toString() {
		return "StandardCardItemPOJO [cardName=" + cardName + ", Image="
				+ imageName + "]";
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getImage() {
		return imageName;
	}
	public void setImage(String image) {
		imageName = image;
	}
	public StandardCardItemPOJO(String cardName, String image) {
		super();
		this.cardName = cardName;
		imageName = image;
	}
	public StandardCardItemPOJO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
