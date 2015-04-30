package com.loyal.one.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxingr.client.android.CaptureActivity;
import com.loyal.one.R;
import com.loyal.one.activities.POJO.StandardCardItemPOJO;
import com.loyal.one.activities.adapter.StdCardBaseAdapter;
import com.loyal.one.activities.datalayer.DBScripts;
import com.loyal.one.activities.datalayer.DBScripts.IDS;
import com.loyal.one.activities.datalayer.LOYAL_DBAdapter;

public class PendingVisitsActivityNew extends FragmentActivity {

	public static TabHost tabHost = null;
	private ViewPager viewPager = null;
	private TabsAdapter mTabsAdapter = null;
	private ActionBar actionBar;
	private ListView stdCardListView;
	private ArrayList<StandardCardItemPOJO> stdCardItems;
	private StdCardBaseAdapter stdCardBaseAdapter;
	private String TAG = "PendingVisitsActivityNew";
	private LOYAL_DBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub

		super.onCreate(arg0);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		actionBar = getActionBar();
		actionBar.show();
		// actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		// actionBar.setDisplayShowHomeEnabled(false);
		// actionBar.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.black_bar_top));
		actionBar.setTitle(getString(R.string.scheduledVisits));
		setContentView(R.layout.activity_scheduled_visits_tab_widget);

		Holder.QR_DATA = "";
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		if (tabHost != null) {
			Log.i("", "tabHost.getChildCount(): " + tabHost.getChildCount());
			if (tabHost.getChildCount() != 2) {
				tabHost.setup();
				tabHost.getTabWidget().setShowDividers(
						TabWidget.SHOW_DIVIDER_MIDDLE);
				tabHost.getTabWidget().setDividerDrawable(
						R.drawable.tab_divider_1);
				viewPager = (ViewPager) findViewById(R.id.pager);
				mTabsAdapter = new TabsAdapter(this, tabHost, viewPager);
				Bundle unscheduledBundle = new Bundle();
				setupTab(0, mTabsAdapter, tabHost, new TextView(this),
						"MY CARDS", CardFragment.class, unscheduledBundle);
				Bundle scheduledBundle = new Bundle();
				setupTab(1, mTabsAdapter, tabHost, new TextView(this),
						"OFFERS", CardFragment.class, scheduledBundle);
			}
		}

//		insertStandardCards();
	}

	private void insertStandardCards() {
		// TODO Auto-generated method stub
		dbAdapter = new LOYAL_DBAdapter(this);
		SQLiteDatabase db = dbAdapter.openTable();

		ContentValues sdt_values = new ContentValues();
		sdt_values.put(DBScripts.cardId, "1");
		sdt_values.put(DBScripts.cardName, "DB Bigbazr std card");
		sdt_values.put(DBScripts.region, "IND");
		sdt_values.put(DBScripts.cardImageName, "bigbazar");

		ContentValues sdt_values2 = new ContentValues();
		sdt_values2.put(DBScripts.cardId, "2");
		sdt_values2.put(DBScripts.cardName, "DB Star bazar std card");
		sdt_values2.put(DBScripts.region, "IND");
		sdt_values2.put(DBScripts.cardImageName, "starbazar");

		ContentValues sdt_values3 = new ContentValues();
		sdt_values3.put(DBScripts.cardId, "3");
		sdt_values3.put(DBScripts.cardName, "DB Payback std card");
		sdt_values3.put(DBScripts.region, "IND");
		sdt_values3.put(DBScripts.cardImageName, "payback");

		ContentValues sdt_values4 = new ContentValues();
		sdt_values4.put(DBScripts.cardId, "4");
		sdt_values4.put(DBScripts.cardName, "DB AirIndia std card");
		sdt_values4.put(DBScripts.region, "IND");
		sdt_values4.put(DBScripts.cardImageName, "airindia");

		dbAdapter.insertDetails(db, IDS.ID_STANDARD_CARD_TABLE, sdt_values);
		dbAdapter.insertDetails(db, IDS.ID_STANDARD_CARD_TABLE, sdt_values2);
		dbAdapter.insertDetails(db, IDS.ID_STANDARD_CARD_TABLE, sdt_values3);
		dbAdapter.insertDetails(db, IDS.ID_STANDARD_CARD_TABLE, sdt_values4);

		dbAdapter.closeTable();

	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
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
		// Intent intent = new Intent(this, StandardCardsActivity.class);
		// startActivity(intent);
		// Intent captureIntent= new Intent(this, CaptureActivity.class);
		// startActivityForResult(captureIntent,0);
		showStdCardDialog("Select Card Type");
		/*
		 * Holder.CardHolder.setQR_DATA("Ranjeet_QRCODE");
		 * Holder.CardHolder.setCardImageName("bigbazar"); Intent intentN = new
		 * Intent(this, CreateCardActivity.class);
		 * 
		 * startActivity(intentN);
		 */
	}

	private void showStdCardDialog(String title) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				PendingVisitsActivityNew.this);
		LayoutInflater li = LayoutInflater.from(PendingVisitsActivityNew.this);
		View alertView = li.inflate(R.layout.standard_card_dialog, null);
		builder.setView(alertView);
		builder.setCancelable(true);
		final AlertDialog dialog = builder.create();		
		dialog.show();
		dialog.setCancelable(true);
		// Remove padding from parent
		ViewGroup parent = (ViewGroup) alertView.getParent();
		parent.setPadding(0, 0, 0, 0);

		TextView titleText = (TextView) alertView.findViewById(R.id.titleMsg);
		titleText.setText(title);

		stdCardListView = (ListView) alertView.findViewById(R.id.list_cards);
		// cardItems=new ArrayList<CardItem>() ;

		stdCardItems = getStdCardDataFromDB();
		stdCardBaseAdapter = new StdCardBaseAdapter(getApplicationContext(),
				stdCardItems);

		stdCardListView.setAdapter(stdCardBaseAdapter);

		stdCardListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						StandardCardItemPOJO cardItemPOJO = (StandardCardItemPOJO) parent
								.getItemAtPosition(position);
						Toast.makeText(PendingVisitsActivityNew.this,
								"CardItemPOJO : " + cardItemPOJO.toString(),
								Toast.LENGTH_LONG).show();
						/*
						 * Intent intent = new Intent(MainActivity.this,
						 * CreateCardActivity.class);
						 * intent.putExtra("cardName",
						 * cardItemPOJO.getCardName());
						 * intent.putExtra("imageName",
						 * cardItemPOJO.getImage()); startActivity(intent);
						 */
						Intent captureIntent = new Intent(
								PendingVisitsActivityNew.this,
								CaptureActivity.class);
						startActivityForResult(captureIntent, 0);
						//
						// Intent intentN= new
						// Intent(PendingVisitsActivityNew.this,CreateCardActivity.class);
						// startActivity(intentN);

					}

				});

	}

	private ArrayList<StandardCardItemPOJO> getStdCardDataFromDB() {

		ArrayList<StandardCardItemPOJO> cardItemPOJOs = new ArrayList<StandardCardItemPOJO>();
		LOYAL_DBAdapter dbHandler = new LOYAL_DBAdapter(this);
		SQLiteDatabase db = dbHandler.openTable();

		Cursor cursor = dbHandler.readFromDBWithQuery(db,
				IDS.ID_STANDARD_CARD_TABLE, "select * from "
						+ DBScripts.STANDARD_CARD_TABLE);

		
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					StandardCardItemPOJO standardCardItemPOJO = new StandardCardItemPOJO();
					standardCardItemPOJO.setCardName(cursor.getString(cursor
							.getColumnIndex(DBScripts.cardName)));
					standardCardItemPOJO.setImage(cursor.getString(cursor
							.getColumnIndex(DBScripts.cardImageName)));
					cardItemPOJOs.add(standardCardItemPOJO);
				}
				
			}
		}
		cursor.close();
		dbHandler.closeTable();
		return cardItemPOJOs;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		System.out.println(TAG + " @@@@@@@onActivityResult");
		System.out.println(TAG + " @@@@@@@@@@requestCode" + requestCode
				+ ", RESULT_OK " + resultCode);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				System.out.println(TAG + " 1");
				String contents = intent.getStringExtra("SCAN_RESULT");
				System.out.println(TAG + " 2");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				System.out.println(TAG + " 3");
				// showMyDialog("QRCode", contents);
				System.out.println(TAG + "contents : " + contents
						+ "\n format : " + format);
				String qr_data = intent.getExtras().getString("QR_DATA");
				System.out.println("QR_DATA : " + qr_data);
				// showMyDialog("QRCode", qr_data,"mESSAGE");
				Holder.CardHolder.setQR_DATA(qr_data);
				Intent intentN = new Intent(this, CreateCardActivity.class);
				startActivity(intentN);
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}

	}

	/*
	 * @Override public void onWindowFocusChanged(boolean hasFocus) { // TODO
	 * Auto-generated method stub super.onWindowFocusChanged(hasFocus);
	 * if(hasFocus){ System.out.println("onWindowFocusChanged : " + TAG);
	 * 
	 * if (!Holder.QR_DATA.trim().equals("")) {
	 * System.out.println("!!!!!QR_DATA6546 : " + Holder.QR_DATA); } //
	 * if(!Holder.QR_DATA.trim().equals("")){ //
	 * System.out.println("!!!!!QR_DATA6546 : " + Holder.QR_DATA); //
	 * showMyDialog("QR- CODE", Holder.QR_DATA, "Message"); } } }
	 */

	private ArrayList<StandardCardItemPOJO> getStdCardData() {
		// TODO Auto-generated method stub
		ArrayList<StandardCardItemPOJO> aa = new ArrayList<StandardCardItemPOJO>();

		StandardCardItemPOJO c1 = new StandardCardItemPOJO("Bigbazr std card",
				"bigbazar");
		StandardCardItemPOJO c2 = new StandardCardItemPOJO(
				"Star bazar std card", "starbazar");
		StandardCardItemPOJO c3 = new StandardCardItemPOJO("Payback std card",
				"payback");
		StandardCardItemPOJO c4 = new StandardCardItemPOJO("AirIndia std card",
				"airindia");

		aa.add(c1);
		aa.add(c2);
		aa.add(c3);
		aa.add(c4);

		return aa;
	}

	private View createTabView(int tabIndex, final Context context,
			final String text) {
		Log.i("", "##############3: " + text);
		int id = R.layout.left_tab_bg;

		switch (tabIndex) {
		case 0:
			id = R.layout.left_tab_bg;
			break;
		case 1:
			id = R.layout.middle_tab_bg;
			break;
		// case 2:
		// id = R.layout.right_tab_bg;
		// break;
		}
		View view = LayoutInflater.from(context).inflate(id, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	private void setupTab(int tabIndex, TabsAdapter mTabsAdapter,
			TabHost mTabHost, final View view, final String tabName,
			Class<?> clss, Bundle bundle) {
		View tabview = createTabView(tabIndex, mTabHost.getContext(), tabName);

		TabSpec setContent = mTabHost.newTabSpec(tabName).setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		mTabsAdapter.addTab(setContent, clss, bundle);
	}

	public class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private Context mContext;
		private TabHost mTabHost;
		private ViewPager mViewPager;
		private ArrayList<TabInfo> tabList = new ArrayList<TabInfo>();

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tabName = tabSpec.getTag();
			Log.i("", "################ addTab tabName: " + tabName);

			TabInfo info = new TabInfo(tabName, clss, args);

			info.fragment = ((FragmentActivity) mContext)
					.getSupportFragmentManager().findFragmentByTag(tabName);
			if (info.fragment != null && !info.fragment.isDetached()) {
				FragmentTransaction ft = ((FragmentActivity) mContext)
						.getSupportFragmentManager().beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}
			tabList.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return tabList.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = tabList.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {

			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		final class TabInfo {
			// private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			public Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				// tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}
	}
}