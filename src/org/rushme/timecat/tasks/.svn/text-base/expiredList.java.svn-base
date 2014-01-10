package org.rushme.timecat.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rushme.timecat.menu.menu_expiredList;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class expiredList extends Activity implements View.OnClickListener{
	Button back, menu;
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.expired_list);
		ListView activeListView = (ListView) findViewById(android.R.id.list);
		String[] mFrom = new String[]{"details","Description","time"};
		int[] mTo = new int[]{R.id.details,R.id.description,R.id.time};

		//own-defined Adapter
		final SelfAdapter mSelfAdapter = new SelfAdapter(this, getData(), R.layout.item_expired, mFrom, mTo);
		activeListView.setAdapter(mSelfAdapter);
		activeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//get the data included in the item pressed
				@SuppressWarnings("unchecked")
				HashMap<String,Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.setClass(expiredList.this, showTask.class);
				Bundle bundle=new Bundle();  
				bundle.putString("details", map.get("details").toString());  
				intent.putExtras(bundle); 
				startActivity(intent);
			}
		});

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

	private List<Map<String, Object>> getData(){
		/*
		 * store the data extracted from database into a Map and use ArrayList to organize the Maps.
		 */
		List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mMap = null;
		List<task> tasks = Main.mgr.queryExpired();  //only query the informations of expired tasks
		for (task everyTask : tasks){
			mMap = new HashMap<String, Object>();
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
			Intent intent = new Intent(this, menu_expiredList.class);
			startActivity(intent);
			break;

		}

	}

}
