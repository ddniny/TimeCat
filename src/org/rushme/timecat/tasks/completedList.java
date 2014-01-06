package org.rushme.timecat.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rushme.timecat.menu.menu_completedList;
import org.rushme.timecat.tasks.SlideDelListview.SlideDeleteListener;



import org.rushme.timecat.R;
import org.rushme.timecat.R.array;
import org.rushme.timecat.R.drawable;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;
import org.rushme.timecat.R.style;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class completedList extends Fragment{ //extends ActionBarActivity implements View.OnClickListener{
	Button back, menu;
	private String[] mLocations;
	MyExit myExit;
	private SlideDelListview activeListView;
	private String[] mFrom;
	private int[] mTo;
	private static boolean longClick = false;
	private Button curDel_btn;
	private List<Map<String,Object>> activeTasks;
	public static final String ARG_MODE_NUMBER = "mode_number";
	
	public completedList() {
        // Empty constructor required for fragment subclasses
    } 

//	@Override
//	public void onCreate(Bundle icicle){
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.completed_list, container, false);
		int i = getArguments().getInt(ARG_MODE_NUMBER);
        String planet = getResources().getStringArray(R.array.Mode_array)[i];
        getActivity().setTitle(planet);
//		super.onCreate(icicle);
//		setContentView(R.layout.completed_list);
		//setTheme(SampleList.THEME); //Used for theme switching in samples

		myExit = (MyExit) getActivity().getApplication();
		myExit.setExit(false);
		/*menu button*/
//		mLocations = getResources().getStringArray(R.array.locations);
//		Context context = getSupportActionBar().getThemedContext();
//		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
//		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
//
//		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		getSupportActionBar().setListNavigationCallbacks(list, this);

		activeListView = (SlideDelListview) rootView.findViewById(android.R.id.list);
		mFrom = new String[]{"details","Description","time"};
		mTo = new int[]{R.id.details,R.id.description,R.id.time};
		activeTasks = getData();
		//own-defined Adapter
		final SelfAdapter mSelfAdapter = new SelfAdapter(getActivity(), getData(), R.layout.item_completed, mFrom, mTo);
		activeListView.setAdapter(mSelfAdapter);
		//		activeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		//
		//			@Override
		//			public void onItemClick(AdapterView<?> parent, View view, int position,
		//					long id) {
		//				//get the data included in the item pressed 
		//				@SuppressWarnings("unchecked")
		//				HashMap<String,Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
		//				Intent intent = new Intent();
		//				intent.setClass(completedList.this, showTask.class);
		//				Bundle bundle=new Bundle();  
		//				bundle.putString("id", map.get("id").toString());
		//				bundle.putString("table", "completedTasktable");
		//				intent.putExtras(bundle); 
		//				startActivity(intent);
		//			}
		//		});

		activeListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() { 
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, 
					ContextMenuInfo menuInfo) { 
				longClick = true;
				menu.add(0, 0, 0, "Mark as Uncompleted"); 
				menu.add(0, 1, 0, "Duplicate"); 
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
				final int position = activeListView.pointToPosition((int) xPosition,
						(int) yPosition);
				final String selectedId = activeTasks.get(position).get("id").toString();
				final String selectedTable;
				selectedTable = "completedTasktable";

				task selectedTask = MainActivity.mgr.queryById(selectedId, selectedTable);
				Intent intent = new Intent();
				intent.setClass(completedList.this.getActivity(), showTask.class);
				Bundle bundle=new Bundle();  
				bundle.putString("id", selectedId); 
				bundle.putString("table", selectedTable);
				intent.putExtras(bundle); 
				startActivity(intent);
			}

			public void filpperDelete(float xPosition, float yPosition) {
				if (activeListView.getChildCount() == 0)
					return;
				final int index = activeListView.pointToPosition((int) xPosition,
						(int) yPosition);
				// 一下两步是获得该条目在屏幕显示中的相对位置，直接根据index删除会空指針异常。因为listview中的child只有当前在屏幕中显示的才不会为空
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
							selectedTable = "completedTasktable";
							MainActivity.mgr.deleteOneTask(selectedId, selectedTable);
							activeListView.setAdapter(new SelfAdapter(completedList.this.getActivity(), getData(), R.layout.item_active, mFrom, mTo));

						}

					});

				}
			}
		});


//		back = (Button)findViewById(R.id.back);
//		back.setOnClickListener(this);
//
//		menu = (Button)findViewById(R.id.menu);
//		menu.setOnClickListener(this);
		return rootView;
	}

	public boolean onContextItemSelected(android.view.MenuItem item) { 

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); 
		int position = info.position;  
		final String selectedId = getData().get(position).get("id").toString();
		final String selectedTable = "completedTasktable";
		task selectedTask = MainActivity.mgr.queryById(selectedId, selectedTable);
		String oldState = selectedTask.getState.toString();
		switch (item.getItemId()) { 
		case 0: 
			// Mark as Completed
			selectedTask.setState("ACTIVE");
			if (selectedTable.toString().equals("completedTasktable")){	//mark the task to completed and move it to the completedTasktable from tasktable
				MainActivity.mgr.deleteOneTask(selectedId, selectedTable);
				List<task> list = new ArrayList<task>();
				list.add(selectedTask);
				MainActivity.mgr.add(list, "tasktable");
			}
			break; 

		case 1: 
			// New Task Higher
			Toast.makeText(getActivity(),String.valueOf(info.position), Toast.LENGTH_LONG).show();

			break; 

		case 2: 
			// Delete 
			new AlertDialog.Builder(getActivity())
			.setTitle("Delete a Task!")
			.setMessage("Are you sure you want to delete this task?")
			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					MainActivity.mgr.deleteOneTask(selectedId, selectedTable);
					activeListView.setAdapter(new SelfAdapter(completedList.this.getActivity(), getData(), R.layout.item_active, mFrom, mTo));
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
		activeListView.setAdapter(new SelfAdapter(completedList.this.getActivity(), getData(), R.layout.item_active, mFrom, mTo));


		return super.onContextItemSelected(item);

	} 


	private List<Map<String, Object>> getData(){
		/*
		 * store the data extracted from database into a Map and use ArrayList to organize the Maps.
		 */
		List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mMap = null;
		List<task> tasks = MainActivity.mgr.queryCompleted();  //only query the informations of completed tasks
		for (task everyTask : tasks){
			mMap = new HashMap<String, Object>();
			mMap.put("id", everyTask.id);
			mMap.put("details", everyTask.details);
			mMap.put("Description", everyTask.description);
			mMap.put("time", everyTask.startTime);
			mList.add(mMap);
		}
		return mList;
	}

//	@Override
//	public void onClick(View v) {
//		switch(v.getId()){
//		case R.id.back:
//			finish();
//			break;
//		case R.id.menu:
//			Intent intent = new Intent(this, menu_completedList.class);
//			startActivity(intent);
//			break;
//		}
//
//	}


}
