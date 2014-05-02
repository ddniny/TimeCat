package org.rushme.timecat.data;

import java.util.ArrayList;
import java.util.List;

import org.rushme.timecat.tasks.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBHelper helper = null;
	private SQLiteDatabase db;

	public DBManager(Context context){   //??context
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public static DBHelper getDBHelper(){
		return helper;
	}

	/**
	 * add task
	 */
	public void add(List<task> tasks, String table){ //we have two tables tasktable and completedTasktable
		StringBuilder tagBuilder = new StringBuilder();
		db.beginTransaction(); //begin
		try{
			for (task everyTask : tasks){   //convert tags from String[] to String
				if (everyTask.tags != null) {
				for(String s:everyTask.tags){
					tagBuilder.append(" " + s);
				}
				tagBuilder.append(" ");
				}else tagBuilder.append("");
				//db.execSQL("INSERT INTO " + table + " VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{everyTask.details, everyTask.startTime, everyTask.endTime, everyTask.description, everyTask.getState, everyTask.getImportance, everyTask.priority, tagBuilder.toString()});
				ContentValues values = new ContentValues();
				values.put("details", everyTask.details);
				values.put("startTime", everyTask.startTime);
				values.put("deadline", everyTask.endTime);
				values.put("description", everyTask.description);
				values.put("state", everyTask.getState.toString());
				values.put("importance", everyTask.getImportance.toString());
				values.put("priority", everyTask.priority);
				values.put("tags", tagBuilder.toString());
				db.insert(table, null, values);
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}

	/**
	 * Update the information of one task.
	 * Searching the task from database by using its name
	 */
	public void updateById(task everyTask, String id, String table){
		StringBuilder tagBuilder = new StringBuilder();
		//		task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
		ContentValues cv = new ContentValues();
		cv.put("details", everyTask.details);
		cv.put("startTime", everyTask.startTime);
		cv.put("deadline", everyTask.endTime);
		cv.put("description", everyTask.description);
		cv.put("state", everyTask.getState.toString());
		cv.put("importance", everyTask.getImportance.toString());
		cv.put("priority", everyTask.priority);
		if (everyTask.tags != null) {
		for(String s:everyTask.tags){
			tagBuilder.append(s + " ");
		}
		}else tagBuilder.append("");
		cv.put("tags", tagBuilder.toString().trim());

		db.update(table, cv, "_id = ?", new String[]{id});
	}



	/**
	 * delete expired task
	 */
	public void deleteExpiredTask(task everyTask){
		//	db.delete("Tasks", whereClause, whereArgs)
	}

	/**
	 * query all tasks, return list
	 */
	public List<task> query(String table){
		ArrayList<task> tasks = new ArrayList<task>();
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		Cursor c = queryTheCursor(table);
		
		while(c.moveToNext()){
			id = c.getString(c.getColumnIndex("_id"));
			details = c.getString(c.getColumnIndex("details"));
			startTime = c.getString(c.getColumnIndex("startTime"));
			endTime = c.getString(c.getColumnIndex("deadline"));
			description = c.getString(c.getColumnIndex("description"));
			state = c.getString(c.getColumnIndex("state"));
			importance = c.getString(c.getColumnIndex("importance"));
			priority = c.getInt(c.getColumnIndex("priority"));
			tags = c.getString(c.getColumnIndex("tags"));
			addTime = c.getString(c.getColumnIndex("addTime"));
			System.out.println(id + details + description);
			//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
			task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
			tasks.add(everyTask);
		}
		c.close();
		return tasks;
	}

	/**
	 * query all all of the active tasks, return list
	 */
	public List<task> queryActive(){
		ArrayList<task> tasks = new ArrayList<task>();
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		Cursor c = db.query("tasktable", null, "state=?", new String[]{"ACTIVE"}, null, null, "priority desc");  
		while(c.moveToNext()){  
			id = c.getString(c.getColumnIndex("_id"));
			details = c.getString(c.getColumnIndex("details"));
			startTime = c.getString(c.getColumnIndex("startTime"));
			endTime = c.getString(c.getColumnIndex("deadline"));
			description = c.getString(c.getColumnIndex("description"));
			state = c.getString(c.getColumnIndex("state"));
			importance = c.getString(c.getColumnIndex("importance"));
			priority = c.getInt(c.getColumnIndex("priority"));
			tags = c.getString(c.getColumnIndex("tags"));
			addTime = c.getString(c.getColumnIndex("addTime"));
			//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
			task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
			tasks.add(everyTask); 
		}  
		c.close();
		return tasks;
	}

	/**
	 * query all all of the expired tasks, return list
	 */
	public List<task> queryExpired(){
		ArrayList<task> tasks = new ArrayList<task>();
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		Cursor c = db.query("tasktable", null, "state=?", new String[]{"EXPIRED"}, null, null, "deadline desc");    //order by不太对可能。。？？？？？？
		while(c.moveToNext()){  
			id = c.getString(c.getColumnIndex("_id"));
			details = c.getString(c.getColumnIndex("details"));
			startTime = c.getString(c.getColumnIndex("startTime"));
			endTime = c.getString(c.getColumnIndex("deadline"));
			description = c.getString(c.getColumnIndex("description"));
			state = c.getString(c.getColumnIndex("state"));
			importance = c.getString(c.getColumnIndex("importance"));
			priority = c.getInt(c.getColumnIndex("priority"));
			tags = c.getString(c.getColumnIndex("tags"));
			addTime = c.getString(c.getColumnIndex("addTime"));
			//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
			task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
			tasks.add(everyTask); 
		}  
		c.close();
		return tasks;
	}

	/**
	 * query all all of the completed tasks, return list
	 */
	public List<task> queryCompleted(){
		ArrayList<task> tasks = new ArrayList<task>();
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		Cursor c = db.query("tasktable", null, "state=?", new String[]{"COMPLETED"}, null, null, "deadline desc");    //order by不太对可能。。？？？？？？
		while(c.moveToNext()){  
			id = c.getString(c.getColumnIndex("_id"));
			details = c.getString(c.getColumnIndex("details"));
			startTime = c.getString(c.getColumnIndex("startTime"));
			endTime = c.getString(c.getColumnIndex("deadline"));
			description = c.getString(c.getColumnIndex("description"));
			state = c.getString(c.getColumnIndex("state"));
			importance = c.getString(c.getColumnIndex("importance"));
			priority = c.getInt(c.getColumnIndex("priority"));
			tags = c.getString(c.getColumnIndex("tags"));
			addTime = c.getString(c.getColumnIndex("addTime"));
			//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
			task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
			tasks.add(everyTask); 
		}  
		c.close();
		return tasks;
	}

	/**
	 * query a specific task by using its name, return a task
	 */
	public task queryByName(String name, String table){
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		Cursor c = db.query(table, null, "details=?", new String[]{name}, null, null, null);    //order by不太对可能。。？？？？？？
		if (c.getCount() != 0){
			c.moveToNext();
			id = c.getString(c.getColumnIndex("_id"));
			details = c.getString(c.getColumnIndex("details"));
			startTime = c.getString(c.getColumnIndex("startTime"));
			endTime = c.getString(c.getColumnIndex("deadline"));
			description = c.getString(c.getColumnIndex("description"));
			state = c.getString(c.getColumnIndex("state"));
			importance = c.getString(c.getColumnIndex("importance"));
			priority = c.getInt(c.getColumnIndex("priority"));
			tags = c.getString(c.getColumnIndex("tags"));
			addTime = c.getString(c.getColumnIndex("addTime"));
			//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
			task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
			c.close();
			return everyTask;
		}else{
			return null;
		}


	}

	public task queryById(String needId, String table){
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		Cursor c = db.query(table, null, "_id=?", new String[]{needId}, null, null, null);    //order by不太对可能。。？？？？？？
		if (c.getCount() != 0){
			c.moveToNext();
			id = c.getString(c.getColumnIndex("_id"));
			details = c.getString(c.getColumnIndex("details"));
			startTime = c.getString(c.getColumnIndex("startTime"));
			endTime = c.getString(c.getColumnIndex("deadline"));
			description = c.getString(c.getColumnIndex("description"));
			state = c.getString(c.getColumnIndex("state"));
			importance = c.getString(c.getColumnIndex("importance"));
			priority = c.getInt(c.getColumnIndex("priority"));
			tags = c.getString(c.getColumnIndex("tags"));
			addTime = c.getString(c.getColumnIndex("addTime"));
			//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
			task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
			c.close();
			return everyTask;
		}else{
			return null;
		}
	}

	/**
	 * query tasks including the tag return a task
	 */
	public List<task> queryByTag(String colum, String wantTags, String table){
		ArrayList<task> tasks = new ArrayList<task>(); 
		String details, startTime, endTime, description, state, importance, tags, id, addTime;
		int priority;
		int have = 0;
		String[] tag;
		/*
		 * public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
		 */
		tag = wantTags.trim().split(" ");
		for(String s: tag){
			if (s.equals("")) continue;
			//s = " " + s + " ";
			System.out.println("wanttedTage~~~~~~~~~~~~~" + s + "aaaaaaaaaaaaa");
//			Cursor c = db.query(table, null, colum + " LIKE ? ", new String[]{"%" + s +"%"}, null, null, null);    //order by不太对可能。。？？？？？？
			Cursor c = db.query(table, null, colum + " LIKE '%" + s + "%'", null, null, null, null); 
			System.out.println(c.getCount());
			while(c.moveToNext()){  
				id = c.getString(c.getColumnIndex("_id"));
				details = c.getString(c.getColumnIndex("details"));
				startTime = c.getString(c.getColumnIndex("startTime"));
				endTime = c.getString(c.getColumnIndex("deadline"));
				description = c.getString(c.getColumnIndex("description"));
				state = c.getString(c.getColumnIndex("state"));
				importance = c.getString(c.getColumnIndex("importance"));
				System.out.println(description);//////////////////
				priority = c.getInt(c.getColumnIndex("priority"));
				tags = c.getString(c.getColumnIndex("tags"));
				addTime = c.getString(c.getColumnIndex("addTime"));
				//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
				task everyTask = new task(id, details, startTime, endTime, description, state, importance, priority, tags, addTime);
				for(task t : tasks){	//if the task have already in the tasks list, then don't add it again.
					if (everyTask.id.equals(t.id))
						have = 1;
				}
				if (have == 0){
					tasks.add(everyTask);
				}else{
					have = 0;
				}
			}  
			c.close();
		}
		return tasks;

	}

	/**
	 * query all tasks, return cursor
	 */
	public Cursor queryTheCursor(String table){
		Cursor c = db.rawQuery("SELECT * FROM " + table, null);
		return c;
	}

	public void deleteOneTask(String id, String table){
		db.delete(table, "_id == ?", new String[]{id});
	}

	/**
	 * close database
	 */
	public void closeDB(){
		db.close();
	}
}
