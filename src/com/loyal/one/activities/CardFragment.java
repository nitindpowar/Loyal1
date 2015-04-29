package com.loyal.one.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.loyal.one.R;
import com.loyal.one.activities.POJO.CardItemPOJO;
import com.loyal.one.activities.POJO.StandardCardItemPOJO;
import com.loyal.one.activities.adapter.CardBaseAdapter;

public class CardFragment extends Fragment  {
	
	private ViewFlipper vf_overdue_visits;
	private TextView tv_selected_agency_name_for_overdue;
	private ListView overdueVisitsListView;
	private CardBaseAdapter CardBaseAdapter;
	private ArrayList<CardItemPOJO> cardItems;
	private ListView cardListView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.card_list_layout, container,
				false);
		
		vf_overdue_visits = (ViewFlipper) rootView
				.findViewById(R.id.vf_overdue_visits);
		cardItems=getCardData();
		CardBaseAdapter = new CardBaseAdapter(getActivity(), cardItems);
		cardListView = (ListView)rootView.findViewById(R.id.list1);		
		cardListView.setAdapter(CardBaseAdapter);		
		
		cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CardItemPOJO cardItemPOJO=(CardItemPOJO) parent.getItemAtPosition(position);
				Toast.makeText(getActivity(),"CardItemPOJO : "+ cardItemPOJO.toString(),Toast.LENGTH_LONG).show();
				

				
			}
			
		});

		
		return rootView;
	}
	
	private ArrayList<CardItemPOJO> getCardData() {
		// TODO Auto-generated method stub
		ArrayList<CardItemPOJO> aa =new ArrayList<CardItemPOJO>();
		
		CardItemPOJO c1= new CardItemPOJO("Bigbazr card","bigbazar");
		CardItemPOJO c2= new CardItemPOJO("Star bazar card","starbazar");
		CardItemPOJO c3= new CardItemPOJO("food shop card","payback");
		CardItemPOJO c4= new CardItemPOJO("benifite card","airindia");
			
		aa.add(c1);
		aa.add(c2);
		aa.add(c3);
		aa.add(c4);
		
		return aa;	
	}
	

}
