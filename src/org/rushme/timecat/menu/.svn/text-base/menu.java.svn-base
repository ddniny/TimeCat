package org.rushme.timecat.menu;

import org.rushme.timecat.tasks.MainActivity;
import org.rushme.timecat.tasks.MyExit;
import org.rushme.timecat.tasks.completedList;
import org.rushme.timecat.tasks.expiredList;
import org.rushme.timecat.tasks.settings;
import org.rushme.timecat.tasks.statistics;
import org.rushme.timecat.tasks.taskEdit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class menu extends Activity{

	public void onCreat(Bundle icicle){
		super.onCreate(icicle);
	}

	protected void onStart(){
		super.onStart();
		MyExit myExit = (MyExit)getApplication();
		if (myExit.isExit()){
			finish();
		}
	}

	public void newTask(){
		Intent intent = new Intent();
		intent.setClass(this, taskEdit.class);
		startActivity(intent);
	}
	public void sync(){

	}
	public void statistics(){
		Intent intent = new Intent();
		intent.setClass(this, statistics.class);
		startActivity(intent);
	}
	public void completedList(){
		Intent intent = new Intent();
		intent.setClass(this, completedList.class);
		startActivity(intent);
	}
	public void expiredList(){
		Intent intent = new Intent();
		intent.setClass(this, expiredList.class);
		startActivity(intent);		
	}
	public void settings(){
		Intent intent = new Intent();
		intent.setClass(this, settings.class);
		startActivity(intent);	

	}
	public void help(){

	}
	public void save(){

	}
	public void exit(){
		MyExit myExit = (MyExit)getApplication();
		myExit.setExit(true);
		finish();

	}
	public void cancel(){

	}
	public void activeTasks(){
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);

	}


}
