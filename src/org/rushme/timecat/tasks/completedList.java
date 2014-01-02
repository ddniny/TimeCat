package org.rushme.timecat.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rushme.timecat.helper.SampleList;
import org.rushme.timecat.menu.menu_completedList;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class completedList extends SherlockActivity implements View.OnClickListener, ActionBar.OnNavigationListener{
	Button back, menu;
	private String[] mLocations;
    MyExit myExit;
    private ListView activeListView;
	private String[] mFrom;
	private int[] mTo;
	
	@Override
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
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.completed_list);
		setTheme(SampleList.THEME); //Used for theme switching in samples
		
		myExit = (MyExit) getApplication();
		myExit.setExit(false);
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
		//own-defined Adapter
		final SelfAdapter mSelfAdapter = new SelfAdapter(this, getData(), R.layout.item_completed, mFrom, mTo);
		activeListView.setAdapter(mSelfAdapter);
		activeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//get the data included in the item pressed 
				@SuppressWarnings("unchecked")
				HashMap<String,Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.setClass(completedList.this, showTask.class);
				Bundle bundle=new Bundle();  
				bundle.putString("id", map.get("id").toString());
				bundle.putString("table", "completedTasktable");
				intent.putExtras(bundle); 
				startActivity(intent);
			}
		});
		
		activeListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() { 
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, 
					ContextMenuInfo menuInfo) { 
				menu.add(0, 0, 0, "Mark as Uncompleted"); 
				menu.add(0, 1, 0, "Duplicate"); 
				menu.add(0, 2, 0, "Delete"); 

			}
		});


		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(this);

		menu = (Button)findViewById(R.id.menu);
		menu.setOnClickListener(this);
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
			Toast.makeText(this,String.valueOf(info.position), Toast.LENGTH_LONG).show();
			
			break; 

		case 2: 
			// Delete 
				new AlertDialog.Builder(this)
				.setTitle("Delete a Task!")
				.setMessage("Are you sure you want to delete this task?")
				.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						MainActivity.mgr.deleteOneTask(selectedId, selectedTable);
						activeListView.setAdapter(new SelfAdapter(completedList.this, getData(), R.layout.item_active, mFrom, mTo));
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
		activeListView.setAdapter(new SelfAdapter(completedList.this, getData(), R.layout.item_active, mFrom, mTo));


		return super.onContextItemSelected(item);

	} 
	
	protected void onStart(){
		super.onStart();
		MyExit myExit = (MyExit)getApplication();
		if (myExit.isExit()){
			finish();
		}
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

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.menu:
			Intent intent = new Intent(this, menu_completedList.class);
			startActivity(intent);
			break;
		}

	}

	@Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		String mode = mLocations[itemPosition];
		Intent intent = new Intent();
		if (mode.equals("Active Tasks")){
			intent.setClass(this, MainActivity.class);
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

}