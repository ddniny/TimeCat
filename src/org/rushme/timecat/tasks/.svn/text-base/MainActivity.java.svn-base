package org.rushme.timecat.tasks;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
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

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;
import org.rushme.timecat.data.DBManager;
import org.rushme.timecat.menu.menu_ActiveTask;
import org.rushme.timecat.menu.menu_taskEdit;
import org.rushme.timecat.tasks.SlideDelListview.SlideDeleteListener;



public class MainActivity extends Fragment{// implements View.OnClickListener{
	
	private TextView date;
	private TextView time;
	private Button now, menuBtn, backBtn;
	static SlideDelListview activeListView;
	static String[] mFrom;
	static int[] mTo;
	private Date d = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	
	
	static List<Map<String,Object>> activeTasks;
	public static MainActivity ma;
	private String[] mLocations;
	private int pointX, pointY, endX,endY;
	private int position,newpos;
	private Button curDel_btn;
	MyExit myExit;
	public static final int priority_max = 2000;
	public static boolean longClick = false;
	public static final String ARG_MODE_NUMBER = "mode_number";
	static SelfAdapter mSelfAdapter;


	public MainActivity() {
        // Empty constructor required for fragment subclasses
    } 
	
	@Override
	//public void onCreate(Bundle icicle){
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activelist, container, false);
		int i = getArguments().getInt(ARG_MODE_NUMBER);
        String planet = getResources().getStringArray(R.array.Mode_array)[i];
        getActivity().setTitle(planet);
		//super.onCreate(icicle);
		longClick = false;
		myExit = (MyExit) getActivity().getApplication();
		myExit.setExit(false);
		//setContentView(R.layout.activity_main); 
		
		

//		menuBtn = (Button) rootView.findViewById(R.id.menu);
//		menuBtn.setOnClickListener(getActivity());
//
//		backBtn = (Button)findViewById(R.id.back);
//		backBtn.setOnClickListener(getActivity());
//
//		now = (Button)findViewById(R.id.now);
//		now.setOnClickListener(getActivity());



//		date = (TextView)findViewById(R.id.date);
//		date.setText(sdfDate.format(d));
//		date.setOnClickListener(getActivity());
//
//		time = (TextView)findViewById(R.id.time);
//		time.setText(sdfTime.format(d));
//		time.setOnClickListener(getActivity());

		activeListView = (SlideDelListview)rootView.findViewById(android.R.id.list);
		mFrom = new String[]{"details","Description","time"};
		mTo = new int[]{R.id.details,R.id.description,R.id.time};

		//own defined Adapter using to associate the items with the ListView
		activeTasks = getData();
		mSelfAdapter = new SelfAdapter(getActivity(), getData(), R.layout.item_active, mFrom, mTo);

		activeListView.setAdapter(mSelfAdapter);
		//		activeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		//			@Override
		//			public void onItemClick(AdapterView<?> parent, View view, int position,
		//					long id) {
		//				//get the data included in the item pressed
		//				@SuppressWarnings("unchecked")
		//				HashMap<String,Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
		//				Intent intent = new Intent();
		//				intent.setClass(MainActivity.this, showTask.class);
		//				Bundle bundle=new Bundle();  
		//				bundle.putString("id", map.get("id").toString()); 
		//				if (map.get("table") != null){
		//					bundle.putString("table", "completedTasktable");
		//				}else{
		//					bundle.putString("table", "tasktable");
		//				}
		//				intent.putExtras(bundle); 
		//				startActivity(intent);
		//			}
		//		});

		activeListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() { 
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, 
					ContextMenuInfo menuInfo) { 
				longClick = true;
				menu.add(0, 0, 0, "Mark as Completed"); 
				menu.add(0, 1, 0, "New Task Higher"); 
				menu.add(0, 2, 0, "Delete"); 
			}
		});

		activeListView.setFlipperDeleteListener(new SlideDeleteListener() {

			public void filpperOnclick(float xPosition, float yPosition) {
				if (longClick) {
					longClick = false;
					return;
				}
				if (activeListView.getChildCount() == 0)
					return;
				// get the position of the item according to the coordinates
				final int position = activeListView.pointToPosition((int) xPosition, (int) yPosition);
				if (position == -1){
					return;
				}
				final String selectedId = activeTasks.get(position).get("id").toString();
				final String selectedTable;
				if (!activeTasks.get(position).containsKey("table")){
					selectedTable = "tasktable";
				}else{
					selectedTable = "completedTasktable";
				}
				task selectedTask = Main.mgr.queryById(selectedId, selectedTable);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this.getActivity(), showTask.class);
				Bundle bundle=new Bundle();  
				bundle.putString("id", selectedId); 
				bundle.putString("table", selectedTable);
				intent.putExtras(bundle); 
				startActivity(intent);
			}

			public void filpperDelete(float xPosition, float yPosition) {
				if (activeListView.getChildCount() == 0)
					return;
				final int index = activeListView.pointToPosition((int) xPosition, (int) yPosition);
				// 一下两步是获得该条目在屏幕显示中的相对位置，直接根据index删除会空指針异常。因为listview中的child只有当前在屏幕中显示的才不会为空
				if (index == -1){
					return;
				}
				int firstVisiblePostion = activeListView.getFirstVisiblePosition();
				View view = activeListView.getChildAt(index - firstVisiblePostion);
				if (curDel_btn!=null) {
					curDel_btn.setVisibility(View.GONE);
					curDel_btn = null;
				}else{

					curDel_btn = (Button)view.findViewById(R.id.del);
					curDel_btn.setVisibility(View.VISIBLE);
					curDel_btn.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							final String selectedId = activeTasks.get(index).get("id").toString();
							final String selectedTable;
							if (!activeTasks.get(index).containsKey("table")){
								selectedTable = "tasktable";
							}else{
								selectedTable = "completedTasktable";
							}
							Main.mgr.deleteOneTask(selectedId, selectedTable);
							activeListView.setAdapter(new SelfAdapter(MainActivity.this.getActivity(), getData(), R.layout.item_active, mFrom, mTo));

						}

					});

				}


			}
		});
        
		ma = this;
		return rootView;
		
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
		task selectedTask = Main.mgr.queryById(selectedId, selectedTable);
		String oldState = selectedTask.getState.toString();
		longClick = false;
		switch (item.getItemId()) { 
		case 0: 
			// Mark as Completed
			selectedTask.setState("COMPLETED");
			if (selectedTable.toString().equals("tasktable")){	//mark the task to completed and move it to the completedTasktable from tasktable
				Main.mgr.deleteOneTask(selectedId, selectedTable);
				List<task> list = new ArrayList<task>();
				list.add(selectedTask);
				Main.mgr.add(list, "completedTasktable");
			}
			break; 

		case 1: 
			// New Task Higher
			//Toast.makeText(MainActivity.this,String.valueOf(info.position), Toast.LENGTH_LONG).show();
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
				priorTask = Main.mgr.queryById(priorId, priorTable);
				float priorScore = getScore(priorTask);
				newScore = (score + priorScore)/2;
			}
			Intent intent = new Intent();
			intent.setClass(MainActivity.this.getActivity(), taskEdit.class);
			Bundle bundle=new Bundle();  
			bundle.putString("newScore", String.valueOf(newScore)); 
			intent.putExtras(bundle); 
			startActivity(intent);
			break; 

		case 2: 
			// Delete 
			new AlertDialog.Builder(MainActivity.this.getActivity())
			.setTitle("Delete a Task!")
			.setMessage("Are you sure you want to delete this task?")
			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					Main.mgr.deleteOneTask(selectedId, selectedTable);
					activeListView.setAdapter(new SelfAdapter(MainActivity.this.getActivity(), getData(), R.layout.item_active, mFrom, mTo));
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
		activeListView.setAdapter(new SelfAdapter(MainActivity.this.getActivity(), getData(), R.layout.item_active, mFrom, mTo));


		return super.onContextItemSelected(item);

	} 



	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getData(){
		/*
		 * store the data extracted from database into a Map and use ArrayList to organize the Maps.
		 */
		List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mMap = null;
		List<task> tasks = Main.mgr.query("tasktable");  //only query the informations of active tasks
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


//	@Override
//	public void onClick(View v) {
//		switch(v.getId()){
//		case R.id.menu:
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.getActivity(), menu_ActiveTask.class);
//			startActivity(intent);
//			break;
//		case R.id.now:
//			d = new Date();
//			date.setText(sdfDate.format(d));
//			time.setText(sdfTime.format(d));
//			activeListView.setAdapter(new SelfAdapter(MainActivity.getActivity(), getData(), R.layout.item_active, mFrom, mTo));
//			break;
//		case R.id.back:
//			finish();
//			break;
//		case R.id.date:
//			showDialog(taskEdit.DATE_DIALOG);
//
//			break;
//		case R.id.time:
//			showDialog(taskEdit.TIME_DIALOG);
//			break;
//		default:
//			break;
//		}    
//
//	}


	public static String getRemaining(task everyTask){
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

//	protected Dialog onCreateDialog(int id){
//		Calendar calendar = Calendar.getInstance();
//		Dialog dialog = null;
//		switch(id){
//		case taskEdit.DATE_DIALOG:
//			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
//
//				@Override
//				public void onDateSet(DatePicker view, int year, int monthOfYear,
//						int dayOfMonth) {
//					Map<String, Object> m = null;
//					date.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year);
//					activeTasks = getData();
//					for (int i = 0; i < activeTasks.size(); i ++){   //only show the task that have deadline before the specific date and time
//						m = activeTasks.get(i);
//						try {
//							if(sdf1.parse(MainActivity.mgr.queryById(m.get("id").toString(), "tasktable").addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){	
//								activeTasks.remove(m);
//								i--;
//							}
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					List<task> tasks = MainActivity.mgr.queryCompleted();  //only query the informations of completed tasks
//					for (task everyTask : tasks){
//						Map<String,Object> m1 = new HashMap<String, Object>();
//						try {
//							if (sdf1.parse(everyTask.addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){
//								m1.put("id", everyTask.id);
//								m1.put("details", everyTask.details);
//								m1.put("Description", everyTask.description);
//								m1.put("time", everyTask.startTime);
//								m1.put("table", "completedTasktable");
//								activeTasks.add(m1);
//							}
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//					activeListView.setAdapter(new SelfAdapter(MainActivity.this.getActivity(), activeTasks, R.layout.item_active, mFrom, mTo));
//
//				}
//			};
//			dialog = new DatePickerDialog(MainActivity.this.getActivity(), dateListener, calendar.get(Calendar.YEAR),
//					calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//			break;
//		case taskEdit.TIME_DIALOG:
//			TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
//
//				@Override
//				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//					Map<String, Object> m = null;
//					time.setText(hourOfDay + ":" + minute);
//					activeTasks = getData();
//					for (int i = 0; i < activeTasks.size(); i ++){   //only show the task that have deadline before the specific date and time
//						m = activeTasks.get(i);
//						try {
//							if(sdf1.parse(MainActivity.mgr.queryById(m.get("id").toString(), "tasktable").addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){
//								activeTasks.remove(m);
//								i--;
//							}
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//					List<task> tasks = MainActivity.mgr.queryCompleted();  //only query the informations of completed tasks
//					for (task everyTask : tasks){
//						Map<String,Object> m1 = new HashMap<String, Object>();
//						try {
//							if (sdf1.parse(everyTask.addTime).getTime() > sdf.parse(date.getText().toString() + " " + time.getText().toString()).getTime()){
//								m1.put("id", everyTask.id);
//								m1.put("details", everyTask.details);
//								m1.put("Description", everyTask.description);
//								m1.put("time", everyTask.startTime);
//								m1.put("table", "completedTasktable");
//								activeTasks.add(m1);
//							}
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//					}
//					activeListView.setAdapter(new SelfAdapter(MainActivity.this.getActivity(), activeTasks, R.layout.item_active, mFrom, mTo));
//
//
//				}
//			};
//			dialog = new TimePickerDialog(MainActivity.this.getActivity(), timeListener, 
//					calendar.get(Calendar.HOUR_OF_DAY), 
//					calendar.get(Calendar.MINUTE), true); //whether is twenty-four-hour clock
//			break;
//		default:
//			break;
//		}
//		return dialog;
//	}

	public static float getScore(task task){
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
