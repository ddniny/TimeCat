package org.rushme.timecat.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.rushme.timecat.menu.menu_taskEdit;
import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

/*
 * can improve: time restriction; calendar, clock;
 */
public class taskEdit extends ActionBarActivity implements View.OnClickListener{
	Button save, back, menu;
	EditText wDescription, filename, dDate, dTime, sDate, sTime, rDate, rTime, wPriority, wTags;
	RadioGroup stateGroup, importanceGroup;
	RadioButton active, expired, completed, low, moderate, important, crucial;
    SeekBar sb;
	private TextView sbResult;
	String details, startTime, endTime, description, state, importance, tags;
	float priority;
	Date today = new Date();
	Intent intent = new Intent();
	private String table;
	protected static final int DATE_DIALOG = 1;
	protected static final int TIME_DIALOG = 2;
	protected int dOrs = 0; //if 0, set deadline, if 1 set start date and time
	protected SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	protected SimpleDateFormat sdf_date = new SimpleDateFormat("MM/dd/yyyy");
	protected SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
	Date dueDay = null;
	Date now = null;
	Date startDay = null;
	Menu currentMenu;
	//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)

	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.task_edit);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		float newScore = -1;
		if (intent.getStringExtra("newScore") != null){
			newScore = Float.parseFloat(intent.getStringExtra("newScore"));
		}
		menu = (Button)findViewById(R.id.menu);
		menu.setOnClickListener(this);

		save = (Button)findViewById(R.id.save);
		save.setOnClickListener(this);

		menu = (Button)findViewById(R.id.menu);
		menu.setOnClickListener(this);

		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(this);

		wDescription = (EditText)findViewById(R.id.wDescription);
		wDescription.requestFocus();
		filename = (EditText)findViewById(R.id.filename);
		dDate = (EditText)findViewById(R.id.dDate);
		dTime = (EditText)findViewById(R.id.dTime);
		sDate = (EditText)findViewById(R.id.sDate);
		sTime = (EditText)findViewById(R.id.sTime);
		rDate = (EditText)findViewById(R.id.rDate);
		rTime = (EditText)findViewById(R.id.rTime);
		wPriority = (EditText)findViewById(R.id.wPriority);
		if (newScore>0){
			System.out.println(newScore + "newScore!!!!!!!!!!!!!!!");
			wPriority.setText(String.valueOf(newScore));
		}
		wTags = (EditText)findViewById(R.id.wTags);
		sb =(SeekBar)findViewById(R.id.sb);
		sbResult=(TextView)findViewById(R.id.sbresult);

		stateGroup = (RadioGroup)findViewById(R.id.stateGroup);
		importanceGroup = (RadioGroup)findViewById(R.id.importanceGroup);
		active = (RadioButton)findViewById(R.id.active);
		expired = (RadioButton)findViewById(R.id.expired);
		completed = (RadioButton)findViewById(R.id.completed);
		low = (RadioButton)findViewById(R.id.low);
		moderate = (RadioButton)findViewById(R.id.moderate);
		important = (RadioButton)findViewById(R.id.important);
		crucial = (RadioButton)findViewById(R.id.crucial);

		dDate.setOnClickListener(this);
		dTime.setOnClickListener(this);
		sDate.setOnClickListener(this);
		sTime.setOnClickListener(this);

		dDate.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus){
					if (dDate.getText().toString().equals("")) return;

					int remainingDays = 0; 	  
					Calendar beginCalendar = Calendar.getInstance();  
					Calendar endCalendar = Calendar.getInstance();  
					beginCalendar.setTime(today);  
					try {
						endCalendar.setTime(sdf_date.parse(dDate.getText().toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}  
					//calculate the remaining days. 
					while(beginCalendar.before(endCalendar)){  
						remainingDays++;  
						beginCalendar.add(Calendar.DAY_OF_MONTH, 1);  
					}  
					rDate.setText(Integer.toString(remainingDays) + "Days");

				}
			}

		} );

		dTime.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					if (dDate.getText().toString().equals("")||dTime.getText().toString().equals("")) return;

					try {
						now = df.parse(df.format(today));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					try {
						dueDay = df.parse(dDate.getText().toString() + " " + dTime.getText().toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					long l=dueDay.getTime() - now.getTime();
					long day=l/(24*60*60*1000);
					long hour=(l/(60*60*1000)-day*24);
					long min=((l/(60*1000))-day*24*60-hour*60);
					//long s=(l/1000-day*24*60*60-hour*60*60-min*60);
					if(min > 0){
						rTime.setText(hour + "hour" + min + "min");
					}else{
						rTime.setText("0 hour 0 min"); 
					}
				}
			}

		});
		
			sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					//Toast.makeText(MainActivity.this, "滑动结束", Toast.LENGTH_LONG).show();
				}
				
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					//Toast.makeText(MainActivity.this, "滑动开始", Toast.LENGTH_LONG).show();		
				}
				
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					sbResult.setText(progress+"%");
					// TODO Auto-generated method stub
						
				}
			});


	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.task_edit, menu);
		menu.findItem(R.id.action_save).setVisible(true);
		currentMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	
				new AlertDialog.Builder(this)
				.setTitle("Have not saved!")
				.setMessage("Are you sure you want to leave without saving?")
				.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue without save
						//NavUtils.navigateUpFromSameTask(taskEdit.this);
						finish();
					}
				})
				.setNegativeButton("Save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						if (add()!=0){
							if (!state.equals("COMPLETED")){
								NavUtils.navigateUpFromSameTask(taskEdit.this);
							}else{
								Bundle bundle = new Bundle();
								bundle.putString("table", "COMPLETED");
								intent.putExtras(bundle);
								intent.setClass(taskEdit.this, Main.class);
								startActivity(intent);
							}
						}
					}
				})
				.show();
			
	    	return true;
	    case R.id.action_save:
			if (add()!=0){
				if (!state.equals("COMPLETED")){
					intent.setClass(this, Main.class);
				}else{
					Bundle bundle = new Bundle();
					bundle.putString("table", "COMPLETED");
					intent.putExtras(bundle);
					intent.setClass(this, Main.class);
				}
				startActivity(intent);
			}
			return true;
	    default:
	    	break;
	    }
	    return super.onOptionsItemSelected(item);
	}
	


	protected Dialog onCreateDialog(int id){
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		switch(id){
		case DATE_DIALOG:
			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					if (dOrs == 0){
						dDate.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year);
					}else{
						sDate.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year);
					}
				}
			};
			dialog = new DatePickerDialog(this, dateListener, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			break;
		case TIME_DIALOG:
			TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					if (dOrs == 0){
						dTime.setText(hourOfDay + ":" + minute);
					}else{
						sTime.setText(hourOfDay + ":" + minute);
					}
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


	protected void onStart(){
		super.onStart();
		MyExit myExit = (MyExit)getApplication();
		if (myExit.isExit()){
			finish();
		}
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
						intent.setClass(taskEdit.this, Main.class);
						startActivity(intent);

					}
				})
				.setNegativeButton("Save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						if (add()!=0){
							if (!state.equals("COMPLETED")){
								intent.setClass(taskEdit.this, Main.class);
							}else{
								intent.setClass(taskEdit.this, Main.class);
							}
							startActivity(intent);
						} 
					}
				})
				.show();
			}else{
				intent.setClass(taskEdit.this, menu_taskEdit.class);
				startActivity(intent);
			}

			break;
		case R.id.save:
			if (add()!=0){
				if (!state.equals("COMPLETED")){
					intent.setClass(this, Main.class);
				}else{
//					Bundle bundle = new Bundle();
//					bundle.putString("table", "COMPLETED");
//					intent.putExtras(bundle);
					intent.setClass(this, Main.class);
				}
				startActivity(intent);
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
	 * return 1 - success, 0 - failed.
	 */
	public int add(){   
		ArrayList<task> tasks = new ArrayList<task>();
		//task(String details, String startTime, String endTime, String description, String state, String importance, int priority, String tags)
		details = filename.getText().toString();
//		task taskInActiveTable = MainActivity.mgr.queryByName(details, "tasktable");   //have to make sure that the task name is unique in the whole database including two tables.
//		task taskInCompleteTable = MainActivity.mgr.queryByName(details, "completedTasktable");
//		if (taskInActiveTable != null || taskInCompleteTable != null){     //Primary key constraint,  details should be unique.
//			new AlertDialog.Builder(this)
//			.setTitle("Duplicate file name！")
//			.setMessage("Please change the file name.")
//			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) { 
//
//				}
//			})
//			.show();
//			return 0;
//		}
		if (details.equals("")){   //task name cannot be null
//			new AlertDialog.Builder(this)
//			.setTitle("Need task name.")
//			.setMessage("Please enter the task name.")
//			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) { 
//
//				}
//			})
//			.show();
//			return 0;
			details = null;
		}
		startTime = sDate.getText().toString() + " " + sTime.getText().toString();
		if (startTime.trim().equals("")){   //if the user did not enter the start time our deadline, set it default to current date our time
			startTime = df.format(today);
		}else if (sDate.getText().toString().equals("")) {
			startTime = sdf_date.format(today) + " " + sTime.getText().toString();
		}else{
			startTime = sDate.getText().toString() + " " + sdf_time.format(today);
		}

		endTime = dDate.getText().toString() + " " + dTime.getText().toString();
		if (endTime.trim().equals("")){
			endTime = df.format(today);
		}else if (dDate.getText().toString().equals("")) {
			endTime = sdf_date.format(today) + " " + dTime.getText().toString();
		}else{
			endTime = dDate.getText().toString() + " " + sdf_time.format(today);
		}

		try {   //check whether the state and the relationship between deadline and current is corresponding with each other
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
		RadioButton checkedState = (RadioButton)findViewById(stateGroup.getCheckedRadioButtonId());
		state = checkedState.getText().toString();
		RadioButton checkedImportance = (RadioButton)findViewById(importanceGroup.getCheckedRadioButtonId());
		importance = checkedImportance.getText().toString();
		String priority_string = wPriority.getText().toString();
		if (priority_string.equals("")){
			priority = 1000;
		}else{
			priority = Float.parseFloat(priority_string);
		}
		tags = wTags.getText().toString();


		task task1 = new task(null, details, startTime, endTime, description, state, importance, priority, tags, null);
		
		tasks.add(task1);
		if (state.equals("COMPLETED")){
			Main.mgr.add(tasks, "completedTasktable");
			table = "completedTasktable";
		}else{
			Main.mgr.add(tasks, "tasktable");
			table = "tasktable";
		}
		return 1;
	}


}
