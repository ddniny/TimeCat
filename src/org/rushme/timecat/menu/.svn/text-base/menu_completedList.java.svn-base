package org.rushme.timecat.menu;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_completedList extends menu implements View.OnClickListener{

	Button activeTask, statistics, expiredList, settings;
	Button help, exit;

	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.menu_completed_list);

		activeTask = (Button)findViewById(R.id.activeTasks);
		activeTask.setOnClickListener(this);

		statistics = (Button) findViewById(R.id.statistics);
		statistics.setOnClickListener(this);

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
		case R.id.activeTasks:
			activeTasks();
			break;
		case R.id.statistics:
			statistics();
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
