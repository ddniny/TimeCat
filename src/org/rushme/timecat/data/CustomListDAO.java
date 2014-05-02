package org.rushme.timecat.data;

import java.util.ArrayList;

import org.rushme.timecat.DataBean.CustomListBean;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CustomListDAO {
	private DBHelper helper;
	private SQLiteDatabase db;
	private final String tableName = "customList";

	public CustomListDAO(){
		helper = DBManager.getDBHelper();
		db = helper.getWritableDatabase();
	}

	public void insert(CustomListBean bean){
		db.beginTransaction(); //begin
		try{
	//	"(_id INTEGER PRIMARY KEY, listName VARCHAR, orderBy VARCHAR, state VARCHAR, importance VARCHAR, tags VARCHAR, createdBetween VARCHAR, deadlineBetween VARCHAR)");
			ContentValues values = new ContentValues();
			values.put("listName", bean.getListName());
			values.put("orderBy", bean.getOrderBy());
			values.put("state", bean.getState());
			values.put("importance", bean.getImportance());
			values.put("tags", bean.getTags());
			values.put("createdBetween", bean.getCreatedBetween());
			values.put("deadlineBetween", bean.getDeadlineBetween());
			db.insert(tableName, null, values);
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	/**
	 * query all customList, return list
	 */
	public ArrayList<CustomListBean> query(){
		ArrayList<CustomListBean> beans = new ArrayList<CustomListBean>();
		Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);
		while(c.moveToNext()){
			CustomListBean bean = new CustomListBean();
			bean.setListId(c.getInt(c.getColumnIndex("_id")));
			bean.setImportance(c.getString(c.getColumnIndex("importance")));
			bean.setListName(c.getString(c.getColumnIndex("listName")));
			bean.setOrderBy(c.getString(c.getColumnIndex("orderBy")));
			bean.setState(c.getString(c.getColumnIndex("state")));
			bean.setTags(c.getString(c.getColumnIndex("tags")));
			bean.setCreatedBetween(c.getString(c.getColumnIndex("createdBetween")));
			bean.setDeadlineBetween(c.getString(c.getColumnIndex("deadlineBetween")));
			beans.add(bean);
		}
		c.close();
		return beans;
	}
	
	public CustomListBean queryByName(String name){
		CustomListBean bean = new CustomListBean();
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		Cursor c = db.query(tableName, null, "listName=?", new String[]{name}, null, null, null);    
		if (c.getCount() != 0){
			c.moveToNext();
			bean.setListId(c.getInt(c.getColumnIndex("_id")));
			bean.setImportance(c.getString(c.getColumnIndex("importance")));
			bean.setListName(c.getString(c.getColumnIndex("listName")));
			bean.setOrderBy(c.getString(c.getColumnIndex("orderBy")));
			bean.setState(c.getString(c.getColumnIndex("state")));
			bean.setTags(c.getString(c.getColumnIndex("tags")));
			bean.setCreatedBetween(c.getString(c.getColumnIndex("createdBetween")));
			bean.setDeadlineBetween(c.getString(c.getColumnIndex("deadlineBetween")));
			c.close();
			return bean;
		}else{
			return null;
		}
	}
	
	public void deleteOneList(){
		db.execSQL("delete from " + tableName + " where _id = (SELECT MIN(rowid) FROM " + tableName + ");");
	}
	
	public int getCount(){
		Cursor c = db.rawQuery("Select count(*) as num from " + tableName + ";", null);
		c.moveToNext();
		return c.getInt(c.getColumnIndex("num"));
	}



}
