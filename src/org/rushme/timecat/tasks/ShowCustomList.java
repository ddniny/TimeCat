package org.rushme.timecat.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rushme.timecat.R;
import org.rushme.timecat.DataBean.CustomListBean;
import org.rushme.timecat.data.allTaskDAO;
import org.rushme.timecat.tasks.SlideDelListview.SlideDeleteListener;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ShowCustomList extends Fragment{
	static SlideDelListview activeListView;
	static String[] mFrom;
	static int[] mTo;
	private Date d = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");


	static List<Map<String,Object>> activeTasks;
	public static MainActivity ma;
	public static Button curDel_btn;
	public static CheckBox checkBox;
	MyExit myExit;
	public static final int priority_max = 2000;
	public static boolean longClick = false;
	public static final String ARG_MODE_NUMBER = "mode_number";
	static SelfAdapter mSelfAdapter;
	private static Button delMulti, cancel, setComplete;
	private static View rootView;
	private allTaskDAO taskDAO = new allTaskDAO();
	private CustomListBean bean = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_activelist, container, false);
		int i = getArguments().getInt(ARG_MODE_NUMBER);
		bean = (CustomListBean) getArguments().getSerializable("customListBean");
		
		String planet = getArguments().getString("title");//getResources().getStringArray(R.array.Mode_array)[i];
		getActivity().setTitle(planet);
		longClick = false;
		myExit = (MyExit) getActivity().getApplication();
		myExit.setExit(false);
		delMulti = (Button)rootView.findViewById(R.id.delMulti);

//		delMulti.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				deletMulti();
//			}
//
//		});
//
//		cancel = (Button)rootView.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				batchProcessing();
//			}
//
//		});
//
//		setComplete = (Button)rootView.findViewById(R.id.setComplete);
//		setComplete.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				setMulti();
//			}
//
//		});


		activeListView = (SlideDelListview)rootView.findViewById(android.R.id.list);
		mFrom = new String[]{"details","Description","time"};
		mTo = new int[]{R.id.details,R.id.description,R.id.time};

		//own defined Adapter using to associate the items with the ListView
		activeTasks = new ArrayList<Map<String, Object>> ();
		activeTasks.clear();
		activeTasks.addAll(getData(taskDAO.getProperList(bean)));

		mSelfAdapter = new SelfAdapter(getActivity(), activeTasks, R.layout.item_active, mFrom, mTo);

		activeListView.setAdapter(mSelfAdapter);
		

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
				if (curDel_btn != null) {  //delete button disappear when press other place
					curDel_btn.setVisibility(View.GONE);
				}
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
				intent.setClass(ShowCustomList.this.getActivity(), taskEdit.class);
				Bundle bundle=new Bundle();  
				bundle.putString("id", selectedId); 
				bundle.putString("table", selectedTable);
				intent.putExtras(bundle); 
				startActivity(intent);
			}

			public void filpperMarkAs(float xPosition, float yPosition) {
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
				//markAsCompleted (selectedTask, selectedId, selectedTable);
				activeTasks.clear();
				activeTasks.addAll(getData(taskDAO.getProperList(bean)));
				mSelfAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "Mark as completed.", Toast.LENGTH_LONG).show();
			}

			public void filpperDelete(float xPosition, float yPosition) {
				if (activeListView.getChildCount() == 0)
					return;
				final int index = activeListView.pointToPosition((int) xPosition, (int) yPosition);
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
							new AlertDialog.Builder(ShowCustomList.this.getActivity())
							.setTitle("Delete a Task!")
							.setMessage("Are you sure you want to delete this task?")
							.setPositiveButton("YES", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) { 
									final String selectedId = activeTasks.get(index).get("id").toString();
									final String selectedTable;
									if (!activeTasks.get(index).containsKey("table")){
										selectedTable = "tasktable";
									}else{
										selectedTable = "completedTasktable";
									}
									Main.mgr.deleteOneTask(selectedId, selectedTable);
									activeTasks.clear();
									activeTasks.addAll(getData(taskDAO.getProperList(bean)));
									mSelfAdapter.notifyDataSetChanged();
									curDel_btn.setVisibility(View.GONE);
								}
							})
							.setNegativeButton("NO", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) { 

								}
							})
							.show();

						}

					});

				}


			}
		});

		//ma = this;
		
		return rootView;

	}
	
	public static List<Map<String, Object>> getData(ArrayList<task> tasks){
		/*
		 * store the data extracted from database into a Map and use ArrayList to organize the Maps.
		 */
		
		Map<String,Object> mMap = null;
		List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
		for (task everyTask : tasks){
			mMap = new HashMap<String, Object>();
			mMap.put("id", everyTask.id);
			mMap.put("details", everyTask.details);
			mMap.put("Description", everyTask.description);
			if (everyTask.getState.toString().equals("EXPIRED")) {
				mMap.put("time", "Expired at: " + everyTask.endTime);
			} else {
				mMap.put("time", getRemaining(everyTask));
			}
			mList.add(mMap);
		}
		return mList;
	}
	
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
		if (l<=0){
			return "0 Day 0 Hour 0 Min";
		}else{
			return day + " Day " + hour + " Hour " + min + " Min";
		}
	}

}
