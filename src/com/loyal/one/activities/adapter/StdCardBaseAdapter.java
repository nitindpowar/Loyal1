package com.loyal.one.activities.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loyal.one.R;
import com.loyal.one.activities.POJO.StandardCardItemPOJO;

public class StdCardBaseAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<StandardCardItemPOJO> cardArrayList;
	private String TAG="StdCardBaseAdapter";
	
	
		
	
	public StdCardBaseAdapter(Context context,ArrayList<StandardCardItemPOJO> cardArrayList) {
		super();		
		this.context = context;
		this.cardArrayList = cardArrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cardArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cardArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.mylist, null);
		}
		
		StandardCardItemPOJO standardCardItemPOJO =(StandardCardItemPOJO) getItem(arg0);
		
//		Log.i(TAG,"AAAAAAA - "+standardCardItemPOJO.toString() );
		TextView cardNameTextView=(TextView) v.findViewById(R.id.Itemname);		
		cardNameTextView.setText(standardCardItemPOJO.getCardName());
		
	    int resID =context.getResources().getIdentifier("@drawable/"+standardCardItemPOJO.getImage() , "drawable",context.getPackageName());
//	    prodImg.setImageResource(resID);
		
		ImageView frontImageTextView=(ImageView)v.findViewById(R.id.icon);
		frontImageTextView.setImageResource(resID);
		
		return v;
	}

}
