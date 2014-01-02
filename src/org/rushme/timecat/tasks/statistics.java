package org.rushme.timecat.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.rushme.timecat.menu.menu_statistics;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class statistics extends Activity implements View.OnClickListener{
	private Button back, menu, chart;
	private TextView tags, showData;
	private List<task> wantTasksInTasktable, wantTasksInCompletedTasktable;
	private int totalNum = 0, uncompletedNum = 0, lowNum = 0, moderateNum = 0, importantNum = 0, crucialNum = 0;
	TableLayout layout;
	private EditText sDate, eDate;
	String ssDate, seDate, taskDeadline;
	SimpleDateFormat df;
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.statistics);
		back = (Button)findViewById(R.id.back);
		back.setOnClickListener(this);

		menu = (Button)findViewById(R.id.menu);
		menu.setOnClickListener(this);
		
		chart = (Button)findViewById(R.id.chart);
		chart.setOnClickListener(this);
		
		tags = (TextView)findViewById(R.id.wTag);
		wantTasksInTasktable = new ArrayList<task>();
		wantTasksInCompletedTasktable = new ArrayList<task>();
		
		showData = (TextView)findViewById(R.id.showData);
		sDate = (EditText)findViewById(R.id.sDate);
		eDate = (EditText)findViewById(R.id.eDate);
		df = new SimpleDateFormat("MM/dd/yyyy");
		
		
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
			Intent intent = new Intent(this, menu_statistics.class);
			startActivity(intent);
			break;
		case R.id.chart:
			showData.setText("");
			String wantTags = tags.getText().toString();
			layout = (TableLayout)findViewById(R.id.tableLayout1);
			wantTasksInTasktable = MainActivity.mgr.queryByTag("tags", wantTags, "tasktable");
			wantTasksInCompletedTasktable = MainActivity.mgr.queryByTag("tags", wantTags, "completedTasktable");
			wantTasksInTasktable.addAll(wantTasksInCompletedTasktable);
			ssDate = sDate.getText().toString();
			seDate = eDate.getText().toString();
			for (int i = 0; i < wantTasksInTasktable.size(); i++){
				task t = wantTasksInTasktable.get(i);
				taskDeadline = t.endTime;
				if (ssDate.equals("")&&!seDate.equals("")){
					try {
						if (df.parse(taskDeadline).after(df.parse(seDate))){
							wantTasksInTasktable.remove(t);
							i--;		
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(!ssDate.equals("")&&seDate.equals("")){
					try {
						if (df.parse(taskDeadline).before(df.parse(seDate))){
							wantTasksInTasktable.remove(t);
							i--;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}else if(!ssDate.equals("")&&!seDate.equals("")){
					try {
						if (df.parse(taskDeadline).after(df.parse(seDate)) || df.parse(taskDeadline).before(df.parse(seDate))){
							wantTasksInTasktable.remove(t);
							i--;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			totalNum = wantTasksInTasktable.size();
			for (task t : wantTasksInTasktable){
				if (t.getState.toString().equals("ACTIVE")){
					uncompletedNum ++;
				}
				if (t.getImportance.toString().equals("LOW")){
					lowNum ++;
				}else if (t.getImportance.toString().equals("MODERATE")){
					moderateNum ++;
				}else if (t.getImportance.toString().equals("IMPORTANT")){
					importantNum ++;
				}else{
					crucialNum ++;
				}
			}
			showData.append("Total = " + totalNum + "\n");
			showData.append("Uncompleted = " + uncompletedNum + "\n");
			showData.append("Low = " + lowNum + "\n");
			showData.append("Moderate = " + moderateNum + "\n");
			showData.append("Important = " + importantNum + "\n");
			showData.append("Crucial = " + crucialNum + "\n");
			
			//float uncompletAngle = uncompletedNum * 360 / totalNum;
			float lowAngle = lowNum * 360 / totalNum;
			float moderateAngle = moderateNum * 360 / totalNum;
			float importantAngle = importantNum * 360 / totalNum;
			float crucialAngle = crucialNum * 360 / totalNum;
			int[] colors =new int[]{Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN, Color.MAGENTA};
	        int[] shade_colors = new int[]{Color.rgb(180, 180, 0),Color.rgb(180, 20, 10),Color.rgb(3, 23, 163),Color.rgb(15, 165, 0), Color.rgb(56, 71, 221)};
	        float[] percent = new float[]{lowAngle, moderateAngle, importantAngle, crucialAngle};
	        PieView pieView = new PieView(this,colors,shade_colors,percent);
	        layout.addView(pieView, new LayoutParams(300,150));
			break;
			
		}

	}
}
