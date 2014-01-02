package org.rushme.timecat.tasks;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rushme.timecat.data.DBManager;
import org.rushme.timecat.helper.SampleList;
import org.rushme.timecat.menu.menu_ActiveTask;
import org.rushme.timecat.menu.menu_taskEdit;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import org.rushme.timecat.R;
import org.rushme.timecat.R.array;
import org.rushme.timecat.R.drawable;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;
import org.rushme.timecat.R.style;

public class MainActivity extends SherlockActivity implements View.OnClickListener, ActionBar.OnNavigationListener{
	public static DBManager mgr;
	private TextView date;
	private TextView time;
	private Button now, menuBtn, backBtn;
	private ListView activeListView;
	private String[] mFrom;
	private int[] mTo;
	private Date d = new Date();
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private List<Map<String,Object>> activeTasks;
	public static MainActivity ma;
	private String[] mLocations;
	MyExit myExit;
	public static final int priority_max = 2000;


	public boolean onCreateOptionsMenu(Menu menu) {
		//Used to put dark icons on light action bar
		boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;

		//        menu.add("Save")
		//            .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
		//            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		//        menu.add("Search")
		//            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Refresh")
		.setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}



	//    protected void setContent(TextView view) {
	//        view.setText(R.string.action_items_content);
	//    }

	@Override
	public void onCreate(Bundle icicle){
		setTheme(SampleList.THEME); //Used for theme switching in samples
		super.onCreate(icicle);
		myExit = (MyExit) getApplication();
		myExit.setExit(false);
		setContentView(R.layout.activity_main); 
		mgr = new DBManager(this);				//initialize the database

		// setContentView(R.layout.text);
		// setContent((TextView)findViewById(R.id.text));

		menuBtn = (Button) findViewById(R.id.menu);
		menuBtn.setOnClickListener(this);

		backBtn = (Button)findViewById(R.id.back);
		backBtn.setOnClickListener(this);

		now = (Button)findViewById(R.id.now);
		now.setOnClickListener(this);



		date = (TextView)findViewById(R.id.date);
		date.setText(sdfDate.format(d));
		date.setOnClickListener(this);

		time = (TextView)findViewById(R.id.time);
		time.setText(sdfTime.format(d));
		time.setOnClickListener(this);

		/*menu button*/
		mLocations = getResources().getStringArray(R.array.locations);
		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);

		activeListView = (ListView) findViewById(android.R.id.list);
		mFrom = new String[]{"details","Description","time"};
		mTo = new int[]{R.id.details,R.id.description,R.id.time};

		//own defined Adapter using to associate the items with the ListView
		activeTasks = getData();
		final SelfAdapter mSelfAdapter = new SelfAdapter(this, getData(), R.layout.item_active, mFrom, mTo);

		activeListView.setAdapter(mSelfAdapter);
		activeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//get the data included in the item pressed
				@SuppressWarnings("unchecked")
				HashMap<String,Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, showTask.class);
				Bundle bundle=new Bundle();  
				bundle.putString("id", map.get("id").toString()); 
				if (map.get("table") != null){
					bundle.putString("table", "completedTasktable");
				}else{
					bundle.putString("table", "tasktable");
				}
				intent.putExtras(bundle); 
				startActivity(intent);
			}
		});

		activeListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() { 
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, 
					ContextMenuInfo menuInfo) { 
				menu.add(0, 0, 0, "Mark as Completed"); 
				menu.add(0, 1, 0, "New Task Higher"); 
				menu.add(0, 2, 0, "Delete"); 

			}
		});



		ma = this;
	}

	public boolean onContextItemSelected(android.view.MenuItem item) { 

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); 
		int position = info.position;  
		final String selectedId = activeTasks.get(position).get("id").toString();
		final String selectedTable;
		if (!activeTasks.get(position).containsKey("table")){
			selectedTable = "tasktable";
		}else{
			selectedTable = "completedTasktable";
		}
		task selectedTask = mgr.queryById(selectedId, selectedTable);
		String oldState = selectedTask.getState.toString();

		switch (item.getItemId()) { 
		case 0: 
			// Mark as Completed
			selectedTask.setState("COMPLETED");
			if (selectedTable.toString().equals("tasktable")){	//mark the task to completed and move it to the completedTasktable from tasktable
				MainActivity.mgr.deleteOneTask(selectedId, selectedTable);
				List<task> list = new ArrayList<task>();
				list.add(selectedTask);
				MainActivity.mgr.add(list, "completedTasktable");
			}
			break; 

		case 1: 
			// New Task Higher
			Toast.makeText(MainActivity.this,String.valueOf(info.position), Toast.LENGTH_LONG).show();
			String priorId;
			String priorTable;
			float score = getScore(selectedTask);
			float newScore;
			task priorTask = null;
			if (position == 0){
				newScore = score + 1;
				
			}else {
				priorId = activeTasks.get(position-1).get("id").toString();
				if (!activeTasks.get(position-1).containsKey("table")){
					priorTable = "tasktable";
				}else{
					priorTable = "completedTasktable";
				}
				priorTask = mgr.queryById(priorId, priorTable);
				float priorScore = getScore(priorTask);
			    newScore = (score + priorScore)/2;
			}
			Intent intent = new Intent();
			intent.setClass(this, taskEdit.class);
			Bundle bundle=new Bundle();  
			bundle.putString("newScore", String.valueOf(newScore)); 
			intent.putExtras(bundle); 
			startActivity(intent);
			break; 

		case 2: 
			// Delete 
				new AlertDialog.Builder(this)
				.setTitle("Delete a Task!")
				.setMessage("Are you sure you want to delete this task?")
				.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						MainActivity.mgr.deleteOneTask(selectedId, selectedTable);
						activeListView.setAdapter(new SelfAdapter(MainActivity.this, getData(), R.layout.item_active, mFrom, mTo));
					}
				})
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						
					}
				})
				.show();
			break; 

		default: 
			break; 		
		} 
		activeListView.setAdapter(new SelfAdapter(MainActivity.this, getData(), R.layout.item_active, mFrom, mTo));


		return super.onContextItemSelected(item);

	} 

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		String mode = mLocations[itemPosition];
		Intent intent = new Intent();
		if (mode.equals("Active Tasks")){
			return true;
		}else if (mode.equals("New Task")){
			intent.setClass(this, taskEdit.class);
		}else if (mode.equals("Sync")){
			return true;
		}else if (mode.equals("Statistics")){
			intent.setClass(this, statistics.class);
		}else if (mode.equals("Completed List")){
			intent.setClass(this, completedList.class);
		}else if (mode.equals("Expired List")){
			intent.setClass(this, expiredList.class);
		}else if (mode.equals("Settings")){
			intent.setClass(this, settings.class);
		}else if (mode.equals("Help")){

		}else if (mode.equals("Exit")){
			myExit.setExit(true);
			finish();
			return true;
		}

		startActivity(intent);

		return true;
	}

	protected void onStart(){
		super.onStart();
		MyExit myExit = (MyExit)getApplication();
		if (myExit.isExit()){
			finish();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getData(){
		/*
		 * store the data extracted from database into a Map and use ArrayList to organize the Maps.
		 */
		List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mMap = null;
		List<task> tasks = mgr.query("tasktable");  //only query the informations of active tasks
		System.out.println(tasks.size() + "tasks size!!!!");
		Collections.sort(tasks, new Comparator<task>(){


			public int compare(task task1, task task2) {
				Date now = new Date();
				float score1, score2;
				score1 = getScore(task1);
				score2 = getScore(task2);
				System.out.println("score1:"+score1+ task1.details+" score2:"+score2 +task2.details+"!!!!!!!!!!!");

				if (score1 > score2) return -1;
				else return 1;
			}	

		});

		for (task everyTask : tasks){
			mMap = new HashMap<String, Object>();
			mMap.put("id", everyTask.id);
			mMap.put("details", everyTask.details);
			mMap.put("Description", everyTask.description);
			mMap.put("time", getRemaining(everyTask));
			mList.add(mMap);
		}
		return mList;
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		//when close the last Activity, release DB 
		mgr.closeDB();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.menu:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, menu_ActiveTask.class);
			startActivity(intent);
			break;
		case R.id.now:
			System.out.println(d.getTime());
			d = new Date();
			date.setText(sdfDate.format(d));
			time.setText(sdfTime.format(d));
			activeListView.setAdapter(new SelfAdapter(MainActivity.this, getData(), R.layout.item_active, mFrom, mTo));
			break;
		case R.id.back:
			finish();
			break;
		case R.id.date:
			showDialog(taskEdit.DATE_DIALOG);

			break;
		case R.id.time:
			showDialog(taskEdit.TIME_DIALOG);
			break;
		default:
			break;
		}    

	}


	public String getRemaining(task everyTask){
		Date now = new Date();
		try {
			now = sdf.parse(sdf.format(now));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.util.Date dueDay = null;
		try {
			dueDay = sdf.parse(everyTask.endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l=dueDay.getTime() - now.getTime();
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		//long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		if (min<0){
			return "0 Day 0 hour 0 min";
		}else{
			return day + "day" + hour + "hour" + min + "min";
		}
	}

	protected Dialog onCreateDialog(int id){
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		switch(id){
		case taskEdit.DATE_DIALOG:
			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Map<String, Object> m = null;
					date.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year);
					activeTasks = getData();
					for (int i = 0; i < activeTasks.size(); i ++){   //only show the task that have deadline before the specific date and time
						m = activeTasks.get(i);
						try {
							if(sdf1.parse(MainActivity.mgr.queryById(m.get("id").toString(), "tasktable").addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){	
								activeTasks.remove(m);
								i--;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					List<task> tasks = MainActivity.mgr.queryCompleted();  //only query the informations of completed tasks
					for (task everyTask : tasks){
						Map<String,Object> m1 = new HashMap<String, Object>();
						try {
							if (sdf1.parse(everyTask.addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){
								m1.put("id", everyTask.id);
								m1.put("details", everyTask.details);
								m1.put("Description", everyTask.description);
								m1.put("time", everyTask.startTime);
								m1.put("table", "completedTasktable");
								activeTasks.add(m1);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					activeListView.setAdapter(new SelfAdapter(MainActivity.this, activeTasks, R.layout.item_active, mFrom, mTo));

				}
			};
			dialog = new DatePickerDialog(this, dateListener, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			break;
		case taskEdit.TIME_DIALOG:
			TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					Map<String, Object> m = null;
					time.setText(hourOfDay + ":" + minute);
					activeTasks = getData();
					for (int i = 0; i < activeTasks.size(); i ++){   //only show the task that have deadline before the specific date and time
						m = activeTasks.get(i);
						try {
							if(sdf1.parse(MainActivity.mgr.queryById(m.get("id").toString(), "tasktable").addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){
								activeTasks.remove(m);
								i--;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					List<task> tasks = MainActivity.mgr.queryCompleted();  //only query the informations of completed tasks
					for (task everyTask : tasks){
						Map<String,Object> m1 = new HashMap<String, Object>();
						try {
							if (sdf1.parse(everyTask.addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){
								m1.put("id", everyTask.id);
								m1.put("details", everyTask.details);
								m1.put("Description", everyTask.description);
								m1.put("time", everyTask.startTime);
								m1.put("table", "completedTasktable");
								activeTasks.add(m1);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					activeListView.setAdapter(new SelfAdapter(MainActivity.this, activeTasks, R.layout.item_active, mFrom, mTo));


				}
			};
			dialog = new TimePickerDialog(this, timeListener, 
					calendar.get(Calendar.HOUR_OF_DAY), 
					calendar.get(Calendar.MINUTE), true); //whether is twenty-four-hour clock
			break;
		default:
			break;
		}
		return dialog;
	}

	public float getScore(task task){
		float score = task.priority;
		Date now = new Date();
		try {
			if (task.getState.toString().equals("ACTIVE")){
				long endTime;
				endTime = sdf.parse(task.endTime).getTime();
				long startTime = sdf.parse(task.startTime).getTime();
				float priority = task.priority;
				long nowTime = sdf.parse(sdf.format(now)).getTime();
				if (endTime != startTime){
					score = priority + (priority_max - priority) * (float)(nowTime - startTime)/(float)(endTime - startTime);
					//System.out.println(nowTime + " " + startTime + " " + endTime + "!!!!!!!!!!!!getscore");
				}else{
					score = priority;
				}
			}else{
				score = task.priority;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;
	}

}