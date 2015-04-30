package com.loyal.one.activities.datalayer;

public class DBScripts {

	public static String STANDARD_CARD_TABLE = "StandardCard";
	public static String MY_CARD_TABLE = "MyCard";
	public static String cardId= "cardId";
	public static String cardName="cardName";
	public static String region="region";
	public static String cardImageName="cardImageName";
	public static String mycardId="mycardId";
	public static String fronImage="fronImage";
	public static String qrCode="qrCode";
	public static String dt="dt";
	public static String note="extranote";
	
	
	
	public static enum IDS {
		// ID_LOGIN_TABLE,

		ID_STANDARD_CARD_TABLE, ID_MY_CARD_TABLE 

	};

	public static String CREATE_STANDARD_CARD_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ STANDARD_CARD_TABLE
			+ "("+cardId+" integer primary key NOT NULL,"
			+ cardName+" varchar2 NOT NULL,"
			+ region+" varchar2  NULL,"
			+ cardImageName+ " varchar2 NULL);";

	public static String CREATE_MY_CARD_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ MY_CARD_TABLE + "("+mycardId+" integer primary key NOT NULL,"
			+ cardId+" varchar2 NOT NULL," + "cardName varchar2 NOT NULL,"
			+ fronImage+" varchar2 NOT NULL," + "backImage varchar2 NOT NULL,"
			+ qrCode+" varchar2 NOT NULL,"
			+ dt+" timestamp DATETIME DEFAULT (datetime('now','localtime')),  "
			+ note+" varchar2 NULL);";
}
