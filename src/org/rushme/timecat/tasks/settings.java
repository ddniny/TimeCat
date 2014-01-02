package org.rushme.timecat.tasks;

import org.rushme.timecat.menu.menu_settings;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settings extends Activity implements View.OnClickListener{
	Button back, menu;
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.settings);
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(this);

		menu = (Button)findViewById(R.id.menu);
		menu.setOnClickListener(this);
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
			Intent intent = new Intent(this, menu_settings.class);
			startActivity(intent);
			break;
		}

	}
}
