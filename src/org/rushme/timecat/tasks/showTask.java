package org.rushme.timecat.tasks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.rushme.timecat.menu.menu_taskEdit;
import org.rushme.timecat.R;
import org.rushme.timecat.R.id;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class showTask extends taskEdit implements View.OnClickListener {
	private task checkedTask;
	private String oldName, details, table, id;
	private Menu currentMenu;


	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		
		wDescription.setEnabled(false);
		filename.setEnabled(false);
		dDate.setEnabled(false);
		dTime.setEnabled(false);
		sDate.setEnabled(false);
		sTime.setEnabled(false);
		rDate.setEnabled(false);
		rTime.setEnabled(false);
		rTime.setVisibility(View.INVISIBLE);  //?
		sb.setEnabled(false);
		wPriority.setEnabled(false);
		wTags.setEnabled(false);
		active.setEnabled(false);
		completed.setEnabled(false);
		expired.setEnabled(false);
		low.setEnabled(false);
		moderate.setEnabled(false);
		important.setEnabled(false);
		crucial.setEnabled(false);
		save.setText("Update");



		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		table = intent.getStringExtra("table");
		//oldName = details;
		//checkedTask = MainActivity.mgr.queryByName(details, table);
		checkedTask = Main.mgr.queryById(id, table);
		//System.out.println(checkedTask.details);
		wDescription.setText(checkedTask.description);
		filename.setText(checkedTask.details);
		String[] dateAndTime = checkedTask.endTime.split(" ");
		dDate.setText(dateAndTime[0]);
		dTime.setText(dateAndTime[1]);
		dateAndTime = checkedTask.startTime.split(" ");
		sDate.setText(dateAndTime[0]);
		sTime.setText(dateAndTime[1]);
		rDate.setText(MainActivity.ma.getRemaining(checkedTask));

		System.out.println(checkedTask.addTime + "addTime!!!!!!!!!!!!!!!!!!!!");
		/*
		 * set the value of slider
		 */
		try {
			now = df.parse(df.format(today));
			dueDay = df.parse(dDate.getText().toString() + " " + dTime.getText().toString());
			startDay = df.parse(sDate.getText().toString() + " " + sTime.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float percentage = ((float)(now.getTime() - startDay.getTime())/ (float)(dueDay.getTime() - startDay.getTime())) * 100f;
		sb.setProgress((int)percentage);
		
		if (checkedTask.getState.toString().equalsIgnoreCase("COMPLETED")){
			completed.setChecked(true);
			sb.setProgress(100);
		}else if (percentage >= 100){
			expired.setChecked(true);
			checkedTask.setState("EXPIRED"); 
			Main.mgr.updateById(checkedTask, id, table);
		}else if(checkedTask.getState.toString().equalsIgnoreCase("ACTIVE")){
			active.setChecked(true);
		}
		
		if(checkedTask.getImportance.toString().equalsIgnoreCase("LOW")){
			low.setChecked(true);
		}else if (checkedTask.getImportance.toString().equalsIgnoreCase("MODERATE")){
			moderate.setChecked(true);
		}else if (checkedTask.getImportance.toString().equalsIgnoreCase("IMPORTANT")){
			important.setChecked(true);
		}else{
			crucial.setChecked(true);
		}

		wPriority.setText(checkedTask.priority +"");

		for(String s: checkedTask.tags){
			wTags.append(s + " "); 
		}
		
		
		
	}

	protected void onStart(){
		super.onStart();
		MyExit myExit = (MyExit)getApplication();
		if (myExit.isExit()){
			finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.show_task, menu);
		currentMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	if(currentMenu.findItem(R.id.action_save).isVisible()){
				new AlertDialog.Builder(this)
				.setTitle("Have not saved!")
				.setMessage("Are you sure you want to leave without saving?")
				.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue without save
						finish();

					}
				})
				.setNegativeButton("Save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						if (changeData(table) != 0){

							if (!state.equals("COMPLETED")){
								NavUtils.navigateUpFromSameTask(showTask.this);
							}else{
								Bundle bundle = new Bundle();
								bundle.putString("table", "COMPLETED");
								intent.putExtras(bundle);
								intent.setClass(showTask.this, Main.class);
								startActivity(intent);
							}
						}
					}
				})
				.show();
			}else{
				finish();
			}
	        return true;
	    case R.id.action_edit:
	    	MenuItem item1 = (MenuItem) currentMenu.findItem(R.id.action_save);
			item1.setVisible(true);
	    	item.setVisible(false);
	    	update();
	    	return true;
	    case R.id.action_save:
	    	if(changeData(table) != 0){
				if (!state.equals("COMPLETED")){
					intent.setClass(this, Main.class);
				}else{
					Bundle bundle = new Bundle();
					bundle.putString("table", "COMPLETED");
					intent.putExtras(bundle);
					intent.setClass(showTask.this, Main.class);
				}
				startActivity(intent);
				finish();   //will not back to the edit page by pressing the back button
			}
			return true;
	    default:
	    	break;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.menu:
			if(save.getText().equals("Save")){
				new AlertDialog.Builder(this)
				.setTitle("Have not saved!")
				.setMessage("Are you sure you want to leave without saving?")
				.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue without save
						intent.setClass(showTask.this, menu_taskEdit.class);
						startActivity(intent);

					}
				})
				.setNegativeButton("Save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						if (changeData(table) != 0){

							if (!state.equals("COMPLETED")){
								intent.setClass(showTask.this, MainActivity.class);
							}else{
								intent.setClass(showTask.this, completedList.class);
							}
							startActivity(intent);  
						}
					}
				})
				.show();
			}else{
				intent.setClass(showTask.this, menu_taskEdit.class);
				startActivity(intent);
			}
			break;
		case R.id.save:
			if (save.getText().equals("Update")){
				update();
			}else{
				if(changeData(table) != 0){
					if (!state.equals("COMPLETED")){
						intent.setClass(this, MainActivity.class);
					}else{
						intent.setClass(this, completedList.class);
					}

					startActivity(intent);
					finish();   //will not back to the edit page by pressing the back button
				}
			}
			break;
		case R.id.dDate:
			dOrs = 0;
			showDialog(DATE_DIALOG);
			break;
		case R.id.dTime:
			dOrs = 0;
			showDialog(TIME_DIALOG);
			break;
		case R.id.sDate:
			dOrs = 1;
			showDialog(DATE_DIALOG);
			break;
		case R.id.sTime:
			dOrs = 1;
			showDialog(TIME_DIALOG);
			break;
		default:
			break;

		}

	}

	/*
	 * set all of the field enable.
	 */
	public void update(){
		save.setText("Save");
		wDescription.setEnabled(true);
		filename.setEnabled(true);
		dDate.setEnabled(true);
		dDate.setOnClickListener(this);
		dTime.setEnabled(true);
		dTime.setOnClickListener(this);
		sDate.setEnabled(true);
		sDate.setOnClickListener(this);
		sTime.setEnabled(true);
		sTime.setOnClickListener(this);
		rDate.setEnabled(true);
		rTime.setEnabled(true);
		rTime.setVisibility(View.VISIBLE);
		sb.setEnabled(true);
		wPriority.setEnabled(true);
		wTags.setEnabled(true);
		active.setEnabled(true);
		completed.setEnabled(true);
		expired.setEnabled(true);
		low.setEnabled(true);
		moderate.setEnabled(true);
		important.setEnabled(true);
		crucial.setEnabled(true);
	}

	public int changeData(String table){ //1-success, 0-failed
		details = filename.getText().toString();
//		task taskInActiveTable = MainActivity.mgr.queryByName(details, "tasktable");   //have to make sure that the task name is unique in the whole database including two tables.
//		task taskInCompleteTable = MainActivity.mgr.queryByName(details, "completedTasktable");
//		if ((taskInActiveTable != null && !taskInActiveTable.details.equals(oldName)) || (taskInCompleteTable != null && !taskInCompleteTable.details.equals(oldName))){       //check whether the task name is duplicate
//			new AlertDialog.Builder(this)
//			.setTitle("Duplicate file name��")
//			.setMessage("Please change the file name.")
//			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) { 
//
//				}
//			})
//			.show();
//			return 0;
//		}
//		System.out.println("afterduplicatename!!!!!!!!!!!!!!!!");
		startTime = sDate.getText().toString() + " " + sTime.getText().toString();
		if (startTime.trim().equals("")){
			startTime = df.format(today);
		}else if (sDate.getText().toString().equals("")) {
			startTime = sdf_date.format(today) + " " + sTime.getText().toString();
		}else if(sTime.getText().toString().equals("")){
			startTime = sDate.getText().toString() + " " + sdf_time.format(today);
		}

		endTime = dDate.getText().toString() + " " + dTime.getText().toString();
		if (endTime.trim().equals("")){
			endTime = df.format(today);
		}else if (dDate.getText().toString().equals("")) {
			endTime = sdf_date.format(today) + " " + dTime.getText().toString();
		}else if (dTime.getText().toString().equals("")){
			endTime = dDate.getText().toString() + " " + sdf_time.format(today);
		}

		try {
			if ((df.parse(endTime).before(today)||df.parse(endTime).equals(today))&&!completed.isChecked()){
				expired.setChecked(true);
			}else if (df.parse(endTime).after(today)&&expired.isChecked()){
				active.setChecked(true);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		description = wDescription.getText().toString();
		System.out.println(description+"!!!!!!!!!!!!!!!!");
		RadioButton checkedState = (RadioButton)findViewById(stateGroup.getCheckedRadioButtonId());
		state = checkedState.getText().toString();
		RadioButton checkedImportance = (RadioButton)findViewById(importanceGroup.getCheckedRadioButtonId());
		importance = checkedImportance.getText().toString();
		String priority_string = wPriority.getText().toString();
		priority = Float.parseFloat(priority_string);
		tags = wTags.getText().toString();
		checkedTask = new task(null, details, startTime, endTime, description, state, importance, priority, tags, null);
		if (table.toString().equals("tasktable")&&state.equals("COMPLETED")){
			Main.mgr.deleteOneTask(id, table);
			List<task> list = new ArrayList<task>();
			list.add(checkedTask);
			Main.mgr.add(list, "completedTasktable");
			table = "completedTasktable";
		}else if (table.toString().equals("completedTasktable")&&(state.equals("ACTIVE")||state.equals("EXPIRED"))){
			Main.mgr.deleteOneTask(id, table);
			List<task> list = new ArrayList<task>();
			list.add(checkedTask);
			Main.mgr.add(list, "tasktable");
			table = "tasktable";
		}
		else {
			Main.mgr.updateById(checkedTask, id, table);
		}

		return 1;
	}


}
