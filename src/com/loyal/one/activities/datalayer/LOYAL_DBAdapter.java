package com.loyal.one.activities.datalayer;


import static com.loyal.one.activities.datalayer.DBScripts.CREATE_MY_CARD_TABLE;
import static com.loyal.one.activities.datalayer.DBScripts.CREATE_STANDARD_CARD_TABLE;
import static com.loyal.one.activities.datalayer.DBScripts.MY_CARD_TABLE;
import static com.loyal.one.activities.datalayer.DBScripts.STANDARD_CARD_TABLE;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.loyal.one.activities.datalayer.DBScripts.IDS;

public class LOYAL_DBAdapter {

	private static final String TAG = "Loyal1_DBAdapter";
	private static final String DATABASE_NAME = "apaDB";
	private static final int DATABASE_VERSION = 1;

	private Context context = null;

	private DBHelperFSTable dbHelperFSTableObj;
	

	public LOYAL_DBAdapter(Context ctx) {

		this.context = ctx;

		dbHelperFSTableObj = new DBHelperFSTable(context);

	}

	private static class DBHelperFSTable extends SQLiteOpenHelper {

		DBHelperFSTable(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
//			db.execSQL(CREATE_LOGIN_TABLE);
			db.execSQL(CREATE_STANDARD_CARD_TABLE);
			db.execSQL(CREATE_MY_CARD_TABLE);			
			
			Log.i(LOYAL_DBAdapter.class.toString(), "DBHelperFSTable onCreate executed(db created)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS users");
			onCreate(db);
			Log.i(TAG, "onUpgrade executed(db upgraded)");
		}
	}

	public SQLiteDatabase openTable() {
		
		SQLiteDatabase db = dbHelperFSTableObj.getWritableDatabase();
		Log.i(TAG,"openloginTable executed(db now open)" );
		return db;
		/*
		 * db.execSQL(DATABASE_CREATE);
		 * System.out.println(" openSalaatTable query executed");
		 */
	}

	public void closeTable() {
		dbHelperFSTableObj.close();
		Log.i(TAG, "closeloginTable executed(db now closed)");
	}

	public void beginTransactionForDB(SQLiteDatabase db) {
		db.beginTransaction();
		Log.i(TAG, "beginTransactionForDB executed");
	}

	public void endTransactionForDB(SQLiteDatabase db) {
		db.endTransaction();
		Log.i(TAG, "endTransactionForDB executed");
	}

	public void setTransactionSuccessfulForDB(SQLiteDatabase db) {
		db.setTransactionSuccessful();
		Log.i(TAG, "setTransactionSuccessfulForDB executed");
	}

	public long insertDetails(SQLiteDatabase db,IDS tableId, ContentValues values) {

		
		Log.i(TAG, "insert " + tableId + " Details executed"+values.getAsInteger("storeId"));
		
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			return db.insert(STANDARD_CARD_TABLE, null, values);

		case ID_MY_CARD_TABLE:
			return db.insert(MY_CARD_TABLE, null, values);
			
			default:
				break;
		}
		return -100;
	}
	

	public int updateTable(SQLiteDatabase db,IDS tableId, ContentValues values, String[] whereArgs) throws Exception {

		
		Log.i(TAG, "update " + tableId + " executed");
		
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			return db.update(STANDARD_CARD_TABLE, values, "cardId=?", whereArgs);

		case ID_MY_CARD_TABLE:
			return db.update(MY_CARD_TABLE, values, "myCardId=?", whereArgs);
						
		
			
		default:
			break;
		}


		return -100;

	}

	public int deleteTableRow(SQLiteDatabase db,IDS tableId , String[] whereArgs) throws Exception {

		Log.i(TAG, "delete " + tableId + " executed");
		
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			return db.delete(STANDARD_CARD_TABLE, "cardId=?", whereArgs);

		case ID_MY_CARD_TABLE:
			return db.delete(MY_CARD_TABLE, "myCardId=?", whereArgs);
			
			
		}

		return -100;
	}
	
	public int deleteTable(SQLiteDatabase db,IDS tableId ) throws Exception {

		Log.i(TAG, "delete " + tableId + " executed");
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			return db.delete(STANDARD_CARD_TABLE, null, null);

		case ID_MY_CARD_TABLE:
			return db.delete(MY_CARD_TABLE, null, null);
				
		}

		return -100;
	}

	public Cursor readFromDB(SQLiteDatabase db,IDS tableId) {
		Cursor c = null;
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			c = db.rawQuery("SELECT * FROM " + STANDARD_CARD_TABLE, null);
			return c;
			
		case ID_MY_CARD_TABLE:
			c = db.rawQuery("SELECT * FROM " + MY_CARD_TABLE, null);
			return c;					
		
		default:
			break;
		}
		return c;
	}

	public Cursor readFromDBWithQuery(SQLiteDatabase db,IDS tableId,String query) {
		Cursor c = null;
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			c = db.rawQuery(query, null);
			return c;
		case ID_MY_CARD_TABLE:
			c = db.rawQuery(query, null);
			return c;
				
		
						
		default:
			break;
		}
		return c;
	}

	
	public Cursor getId(SQLiteDatabase db,IDS tableId)
	{
		
		Cursor c = null;
		switch (tableId) {
		case ID_STANDARD_CARD_TABLE:
			c = db.rawQuery("SELECT * FROM SQLITE_SEQUENCE WHERE name = "+"\'"
					  +STANDARD_CARD_TABLE+"\'", null);
			return c;
		case ID_MY_CARD_TABLE:
			c = db.rawQuery("SELECT * FROM SQLITE_SEQUENCE WHERE name = "+"\'"
					  +MY_CARD_TABLE+"\'", null);
			return c;
									
		
						
		default:
			break;
		}
		return c;
		
	}
	
	public long insertorUpdate(SQLiteDatabase db, IDS tableId,
			ContentValues values) {

		switch (tableId) {

		case ID_STANDARD_CARD_TABLE:
			return db.insertWithOnConflict(
					STANDARD_CARD_TABLE, null, values,
					SQLiteDatabase.CONFLICT_REPLACE);
		case ID_MY_CARD_TABLE:
			return db.insertWithOnConflict(MY_CARD_TABLE, null,
					values, SQLiteDatabase.CONFLICT_REPLACE);		
			
		default:
			break;

		}

		return -100;
	}
	
	

}
/*****************************************END*******************************************/

