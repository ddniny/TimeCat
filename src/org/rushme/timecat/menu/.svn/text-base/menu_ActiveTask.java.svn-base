package org.rushme.timecat.menu;

import org.rushme.timecat.tasks.MyExit;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_ActiveTask extends menu implements View.OnClickListener{
	Button newTask, statistics, completedList, expiredList, settings;
	Button sync, help, exit;

	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.menu_active_task);

		newTask = (Button)findViewById(R.id.newTask);
		newTask.setOnClickListener(this);

		statistics = (Button) findViewById(R.id.statistics);
		statistics.setOnClickListener(this);

		completedList = (Button) findViewById(R.id.completedList);
		completedList.setOnClickListener(this);

		expiredList = (Button) findViewById(R.id.expiredList);
		expiredList.setOnClickListener(this);

		settings = (Button) findViewById(R.id.settings);
		settings.setOnClickListener(this);

		sync = (Button) findViewById(R.id.sync);
		sync.setOnClickListener(this);

		help = (Button) findViewById(R.id.help);
		help.setOnClickListener(this);

		exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(this);
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
		switch (v.getId()){
		case R.id.newTask:
			newTask();
			break;
		case R.id.statistics:
			statistics();
			break;
		case R.id.completedList:
			completedList();
			break;
		case R.id.expiredList:
			expiredList();
			break;
		case R.id.settings:
			settings();
			break;
		case R.id.sync:
			//sync();
			break;
		case R.id.help:
			//help();
			break;
		case R.id.exit:
			exit();
			break;
		}



	}



}
