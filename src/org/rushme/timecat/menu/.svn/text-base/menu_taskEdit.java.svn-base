package org.rushme.timecat.menu;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_taskEdit extends menu implements View.OnClickListener{
	Button newTask, statistics, activeList, completedList, expiredList, settings;
	Button save, cancel, help, exit;

	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.menu_task_edit);
		save = (Button)findViewById(R.id.save);
		save.setOnClickListener(this);

		cancel = (Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(this);

		newTask = (Button)findViewById(R.id.newTask);
		newTask.setOnClickListener(this);

		statistics = (Button) findViewById(R.id.statistics);
		statistics.setOnClickListener(this);

		activeList = (Button) findViewById(R.id.activeTasks);
		activeList.setOnClickListener(this);

		completedList = (Button) findViewById(R.id.completedList);
		completedList.setOnClickListener(this);

		expiredList = (Button) findViewById(R.id.expiredList);
		expiredList.setOnClickListener(this);

		settings = (Button) findViewById(R.id.settings);
		settings.setOnClickListener(this);

		help = (Button) findViewById(R.id.help);
		help.setOnClickListener(this);

		exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.save:
			break;
		case R.id.cancel:
			break;
		case R.id.newTask:
			newTask();          //clear??????
					break;
		case R.id.statistics:
			statistics();
			break;
		case R.id.activeTasks:
			activeTasks();
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
		case R.id.help:
			//help();
			break;
		case R.id.exit:
			exit();
			break;

		}

	}

}
