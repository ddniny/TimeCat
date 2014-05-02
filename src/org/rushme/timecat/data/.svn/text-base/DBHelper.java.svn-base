package org.rushme.timecat.data;

import java.util.ArrayList;
import java.util.List;

import org.rushme.timecat.tasks.Main;
import org.rushme.timecat.tasks.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "Tasks.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//deleteDatabase(context);
	}

	/*
	 * when the first time to create the database, execute the onCreate().
	 * create a table named tasktable to store the information of tasks.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS tasktable");   
		//db.execSQL("DROP TABLE IF EXISTS completedTasktable");
		db.execSQL("DROP TABLE IF EXISTS customList");
		db.execSQL("CREATE TABLE IF NOT EXISTS tasktable" +    //one table to store the active and expired tasks
				"(_id INTEGER PRIMARY KEY, details VARCHAR, startTime VARCHAR, deadline VARCHAR, description VARCHAR, state VARCHAR, importance VARCHAR, priority INTEGER, tags VARCHAR, addTime TimeStamp NOT NULL DEFAULT (datetime('now', 'localtime')))");
//		db.execSQL("CREATE TABLE IF NOT EXISTS completedTasktable" +	   //one table to store the completed tasks
//				"(_id INTEGER PRIMARY KEY, details VARCHAR, startTime VARCHAR, deadline VARCHAR, description VARCHAR, state VARCHAR, importance VARCHAR, priority INTEGER, tags VARCHAR, addTime TimeStamp NOT NULL DEFAULT (datetime('now', 'localtime')))");
		db.execSQL("CREATE TABLE IF NOT EXISTS customList" +
				"(_id INTEGER PRIMARY KEY, listName VARCHAR, orderBy VARCHAR, state VARCHAR, importance VARCHAR, tags VARCHAR, createdBetween VARCHAR, deadlineBetween VARCHAR)");
//		ContentValues values = new ContentValues();
//		values.put("details", "secret");
//		values.put("startTime", "02/21/1991 06:06");
//		values.put("deadline", "02/21/3333 06:06");
//		values.put("description", "secret");
//		values.put("state", "ACTIVE");
//		values.put("importance", "IMPORTANT");
//		values.put("priority", 0);
//		values.put("tags", "");
//		db.insert("tasktable", null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE Tables ADD COLUMN other STRING");	
	}

	/** 
	 * delete database
	 */  
	public boolean deleteDatabase(Context context) {  
		return context.deleteDatabase(DATABASE_NAME);  
	}  





}
