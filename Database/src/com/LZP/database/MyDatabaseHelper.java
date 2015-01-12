package com.LZP.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "Contacts.db";
	private static final String TABLE_NAME = "Contacts";
	private static final int DB_VERSION = 1;

	public MyDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String SQL_CREATE_TABLE = "create table " + TABLE_NAME
				+ " (_id integer primary key autoincrement,"+
				" ssn nchar(10),name nchar(5),_class nchar(5),phone nchar(20));";
		Log.d("Database Oncreate","OnCreate!!!!!!!!");
		db.execSQL(SQL_CREATE_TABLE);
	}

	public void insert1(String ssn, String name, String _class, String phone) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into Contacts values(ssn,name,_class,phone);");
		db.close();
		Log.d("Insert1111","Now in Insert111111");
	}

	public void insert2(String ssn, String name, String _class, String phone) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ssn", ssn);
		values.put("name", name);
		values.put("_class", _class);
		values.put("phone", phone);
		long rid = db.insert(TABLE_NAME, null, values);
		db.close();
	}

	public void update(String ssn, String name, String _class, String phone) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ssn", ssn);
		values.put("name", name);
		values.put("_class", _class);
		values.put("phone", phone);
		String whereClause = "ssn=?";
		String[] whereArgs = { ssn };
		int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
		db.close();
	}

	public void deleteById(String ssn ){
		SQLiteDatabase db=getWritableDatabase();
		String whereClause = "ssn=?";
		String[] whereArgs = {ssn};
		int row = db.delete(TABLE_NAME, whereClause, whereArgs);
		db.close();
	}
	
	public Cursor query(){
		SQLiteDatabase db = getReadableDatabase();
		//��ȡ������
		Cursor c= db.query(TABLE_NAME,null,null,null,null,null,null);
		//Cursor c2=db.rawQuery("select * from TABLE_NAME",null);
		while(c.moveToNext()){
			Log.d("query","query");
			System.out.println(c.getString(0)); //,���ر��е�һ������
			System.out.println(c.getString(1));
			System.out.println(c.getString(2));
			System.out.println(c.getString(3));
			
		}
		db.close();
		return c;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
