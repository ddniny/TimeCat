package org.rushme.timecat.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.achartengine.model.CategorySeries;
import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class statistics extends Fragment{//extends Activity implements View.OnClickListener{
	private static statistics statistics = null;
	public static boolean saveData = false;
	public static boolean lastByState = true;
	public static boolean lastByImportance = false;
	public static boolean lastByTag = false;
	public static int maxNum;
	private TextView tags, showData;
	private List<task> wantTasksInTasktable, wantTasksInCompletedTasktable;
	private int totalNum = 0, completedNum = 0, activeNum = 0, expiredNum = 0;
	private int lowNum = 0, moderateNum = 0, importantNum = 0, crucialNum = 0;
	TableLayout layout;
	static EditText sDate;
	static EditText eDate;
	String ssDate, seDate, taskDeadline;
	SimpleDateFormat df;
	public static final String ARG_MODE_NUMBER = "mode_number";
	View rootView;
	private ListView areaCheckListView;
	static int dOrs = -1;
	private Button byState, byImportance, byTag;
	private settings setting = settings.getSettings();
	private CategorySeries mSeries = new CategorySeries("");
	private LinearLayout bottomBar;
	private String preWantTags = null;

	public static statistics getStatistics(){ //singleton
		if (statistics == null) {
			statistics = new statistics();
		}
		return statistics;
	}

	public static void setStatistics(){
		statistics = null;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("tags", tags.getText().toString());
		System.out.println("savveveveveeveve");

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			tags.setText(savedInstanceState.getString("tags"));
			System.out.println("heeeeeeeeeeeeeeerrrrrrrrrr" + tags.getText().toString());
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		rootView = inflater.inflate(R.layout.statistics, container, false);
		int i = getArguments().getInt(ARG_MODE_NUMBER);
		String mode = getResources().getStringArray(R.array.Mode_array)[i];
		getActivity().setTitle(mode);

		setHasOptionsMenu(true);

		tags = (TextView)rootView.findViewById(R.id.wTag);
		wantTasksInCompletedTasktable = new ArrayList<task>();

		showData = (TextView)rootView.findViewById(R.id.showData);
		sDate = (EditText)rootView.findViewById(R.id.sDate);
		eDate = (EditText)rootView.findViewById(R.id.eDate);
		layout = (TableLayout)rootView.findViewById(R.id.tableLayout1);
		df = new SimpleDateFormat("MM/dd/yyyy");
		byState = (Button)rootView.findViewById(R.id.byState);
		byImportance = (Button)rootView.findViewById(R.id.byImportance);
		byTag = (Button)rootView.findViewById(R.id.byTag);
		maxNum = setting.getSeekBarValue();

		eDate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dOrs = 0;
				getActivity().showDialog(taskEdit.DATE_DIALOG);
			}

		});

		sDate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dOrs = 1;
				getActivity().showDialog(taskEdit.DATE_DIALOG);
			}

		});

		tags.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog ad = new AlertDialog.Builder(getActivity())
				.setTitle("Choose tags")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMultiChoiceItems(taskEdit.getAllTags(), null, null)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						StringBuilder s = new StringBuilder();
						for (int i = 0; i < areaCheckListView.getCount(); i++){  
							if (areaCheckListView.getCheckedItemPositions().get(i)){  
								s.append(areaCheckListView.getAdapter().getItem(i)+ "  ");  
							}else{  
								areaCheckListView.getCheckedItemPositions().get(i,false);  
							}  
						}  
						tags.setText(s);
						dialog.dismiss();  
					}

				})
				.setNegativeButton("Cancel", null)
				.show();
				areaCheckListView = ad.getListView();
			}

		});


		if (saveData) {
			chart();	
			//			if(lastByTag) {
			//				//mSeries = chartByTag();
			//				byTag.performClick();
			//				drawPie(maxNum, "Chart By Tag");
			//			}
			//			else if(lastByImportance) {
			//				//mSeries = chartByImportance();
			//				byImportance.performClick();
			//				drawPie(maxNum, "Chart By Importance");
			//			}
			//			else {
			//				//mSeries = chartByState();
			//				byState.performClick();
			//				drawPie(maxNum, "Chart By State");
			//			}
		} else {
			tags.setText("");

		}

		return rootView;


	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		//currentMenu = menu;
		return super.getActivity().onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		//boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//boolean drawerOpen = Main.getInstance().mDrawerLayout.isDrawerOpen(Main.getInstance().expListView);
		//if (drawerOpen){
		menu.findItem(R.id.action_refresh).setVisible(!true);
		menu.findItem(R.id.action_settings).setVisible(true);
		menu.findItem(R.id.action_group).setVisible(!true);
		menu.findItem(R.id.action_new).setVisible(!true);
		menu.findItem(R.id.action_accept).setVisible(true);
		//}
	}


	public void chart(){
		bottomBar = (LinearLayout) rootView.findViewById(R.id.selectBy);
		if (bottomBar.getVisibility() != View.VISIBLE) {
			bottomBar.setVisibility(View.VISIBLE);
		} else bottomBar.setVisibility(View.GONE);

		//		if (byState.getVisibility() != View.VISIBLE) {
		//			byState.setVisibility(View.VISIBLE);
		//			showData.setText("");
		//			
		//			if (layout.getChildCount() > 4){
		//				layout.removeViews(4, 1);
		//			}

		//			if(lastByTag) {
		//			mSeries = chartByState();
		//			//chartByImportance();
		//			//chartByTag();
		//			drawPie(maxNum, "Chart By State");
		//			}
		//			if(lastByTag) {
		//				mSeries = chartByState();
		//				drawPie(maxNum, "Chart By State");
		//				}
		//			
		//		} else {
		//			byState.setVisibility(View.GONE);
		//		}
		//		
		//
		//		if (byImportance.getVisibility() != View.VISIBLE) {
		//			byImportance.setVisibility(View.VISIBLE);
		//			if(lastByImportance){
		//				mSeries = chartByImportance();
		//				drawPie(maxNum, "Chart By Importance");
		//			}
		//		} else {
		//			byImportance.setVisibility(View.GONE);
		//		}
		//
		//		if (byTag.getVisibility() != View.VISIBLE) {
		//			byTag.setVisibility(View.VISIBLE);
		//			if (lastByTag) {
		//				mSeries = chartByTag();
		//				drawPie(maxNum, "Chart By Tag");
		//			}
		//		} else {
		//			byTag.setVisibility(View.GONE);
		//		}

		byState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showData.setText("");

				if (layout.getChildCount() > 4){
					layout.removeViews(4, 1);
				}
				mSeries = chartByState();
				//chartByImportance();
				//chartByTag();
				drawPie(maxNum, "Chart By State");
			}

		});

		byImportance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showData.setText("");

				if (layout.getChildCount() > 4){
					layout.removeViews(4, 1);
				}

				//chartByState();
				mSeries = chartByImportance();
				//chartByTag();
				drawPie(maxNum, "Chart By Importance");
			}

		});

		byTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showData.setText("");

				if (layout.getChildCount() > 4){
					layout.removeViews(4, 1);
				}
				//chartByState();
				//chartByImportance();
				mSeries = chartByTag();
				drawPie(maxNum, "Chart By Tag");			
			}

		});

		if(lastByTag){
			byTag.performClick();
		}else if (lastByState){
			byState.performClick();
		}else if (lastByImportance){
			byImportance.performClick();
		}


	}

	public void drawPie(int maxNum, String title){

		PieChartBuilder pieBuilder = new PieChartBuilder(this.getActivity(), mSeries, title);

		layout.addView(pieBuilder.getPieChart(), new LayoutParams(700,500));
	}

	public CategorySeries chartByTag(){
		lastByTag = true;
		lastByState = false;
		lastByImportance = false;
		CategorySeries result = new CategorySeries("");
		totalNum = 0;
		//showData.setText("");
		wantTasksInTasktable = new ArrayList<task>();
		//		if (layout.getChildCount() > 4){
		//			layout.removeViews(4, 1);
		//		}

		String wantTags = tags.getText().toString();


		//		wantTasksInTasktable = Main.mgr.queryByTag("tags", wantTags, "tasktable");
		//		wantTasksInCompletedTasktable = Main.mgr.queryByTag("tags", wantTags, "completedTasktable");
		String[] tags = wantTags.trim().split(" ");
		if (tags.length == 0 || wantTags.equals("")) {
			tags = taskEdit.getAllTags();
		}
		HashMap<String, ArrayList<task>> availableTask = new HashMap<String, ArrayList<task>>();
		for (String t : tags) {
			if (t.equals("")) continue;
			availableTask.put(t, new ArrayList<task>(taskEdit.tagToTask.get(t)));
			for (task task : taskEdit.tagToTask.get(t)) {
				if (!wantTasksInTasktable.contains(task)){
					wantTasksInTasktable.add(task);
				}
			}
		}

		ssDate = sDate.getText().toString();
		seDate = eDate.getText().toString();
		for(String key : availableTask.keySet()){
			ArrayList<task> tasks = new ArrayList<task>();
			tasks = availableTask.get(key);
			for (int i = 0; i < tasks.size(); i++) {
				task t = tasks.get(i); 
				taskDeadline = t.endTime;
				if (ssDate.equals("")&&!seDate.equals("")){
					try {
						if (df.parse(taskDeadline).after(df.parse(seDate))){
							tasks.remove(t);
							i--;		
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(!ssDate.equals("")&&seDate.equals("")){
					try {
						if (df.parse(taskDeadline).before(df.parse(seDate))){
							tasks.remove(t);
							i--;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}else if(!ssDate.equals("")&&!seDate.equals("")){
					try {
						if (df.parse(taskDeadline).after(df.parse(seDate)) || df.parse(taskDeadline).before(df.parse(seDate))){
							tasks.remove(t);
							i--;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		totalNum = wantTasksInTasktable.size();
		//showData.append("Total = " + totalNum + "\n");
		if (totalNum == 0) return result;// null;
		float[] angle = new float[availableTask.keySet().size()];
		int i = 0;
		if(availableTask.keySet().size() == 0) return result; //mSeries;
		if (availableTask.keySet().size() <= maxNum) {
			for (String key : availableTask.keySet()) {
				showData.append(key + " = " + availableTask.get(key).size() + "\n");
				result.add(key, (float) (availableTask.get(key).size() / totalNum));
				//angle[i++] = (float) (availableTask.get(key).size()*360 / totalNum);
			}
		} else {
			HashMap<String, Float> map = new HashMap<String, Float>();
			for(String key:availableTask.keySet()) {
				map.put(key, (float) (availableTask.get(key).size() *100/ totalNum));
			}

			ArrayList<Entry<String,Float>> l = new ArrayList<Entry<String,Float>>(map.entrySet());  

			Collections.sort(l, new Comparator<Map.Entry<String, Float>>() {  
				public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {  
					return (int) (o2.getValue() - o1.getValue());  
				}  
			});

			for(Entry<String,Float> e : l) {
				System.out.println(e.getKey() + "::::" + e.getValue());
			}
			for(int k = 0; k<maxNum; k++) {
				showData.append(l.get(k).getKey() + " = " + availableTask.get(l.get(k).getKey()).size() + "\n");
				result.add(l.get(k).getKey(), l.get(k).getValue()/100);
			}

		}

		return result;

		//		float[] percent = null;
		//		if (angle.length > maxNum-1) {
		//			Arrays.sort(angle);
		//			percent = new float[maxNum];
		//			System.arraycopy(angle, 0, percent, 0, maxNum-1);
		//			int add = 0;
		//			for (int j = maxNum-1; j<angle.length; j++) {
		//				add += angle[j];
		//			}
		//			percent[maxNum-1] = add;
		//		} else {
		//			percent = angle;
		//		}
		//		System.out.println(Arrays.toString(percent) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//		return percent;
		//		int[] colors =new int[]{Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN, Color.MAGENTA};
		//		int[] shade_colors = new int[]{Color.rgb(180, 180, 0),Color.rgb(180, 20, 10),Color.rgb(3, 23, 163),Color.rgb(15, 165, 0), Color.rgb(56, 71, 221)};
		//		//float[] percent = angle; 
		//		PieView pieView = new PieView(getActivity(),Arrays.copyOf(colors, maxNum), Arrays.copyOf(shade_colors, maxNum),percent);
		//		layout.addView(pieView, new LayoutParams(300,150));
	}

	public CategorySeries chartByState(){
		lastByTag = false;
		lastByState = true;
		lastByImportance = false;
		CategorySeries result = new CategorySeries("");
		activeNum = 0;
		expiredNum = 0;
		completedNum = 0;
		//showData.setText("");
		wantTasksInTasktable = new ArrayList<task>();
		//		if (layout.getChildCount() > 4){
		//			layout.removeViews(4, 1);
		//		}
		String wantTags = tags.getText().toString();


		//		wantTasksInTasktable = Main.mgr.queryByTag("tags", wantTags, "tasktable");
		//		wantTasksInCompletedTasktable = Main.mgr.queryByTag("tags", wantTags, "completedTasktable");
		String[] tags = wantTags.trim().split(" ");
		if (tags.length == 0 || wantTags.equals("")) {
			tags = taskEdit.getAllTags();
		}
		for (String t : tags) {
			if (t.equals("")) continue;
			for (task task : taskEdit.tagToTask.get(t)) {
				if (!wantTasksInTasktable.contains(task)){
					wantTasksInTasktable.add(task);
				}
			}
		}

		//wantTasksInTasktable.addAll(wantTasksInCompletedTasktable);
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
				activeNum ++;
			}else if (t.getState.toString().equals("EXPIRED")) {
				expiredNum ++;
			}else {
				completedNum ++;
			}
		}
		showData.append("Total = " + totalNum + "\n");
		showData.append("Active = " + activeNum + "\n");
		showData.append("Expired = " + expiredNum + "\n");
		showData.append("Completed = " + completedNum + "\n");

		//float uncompletAngle = uncompletedNum * 360 / totalNum;
		if (totalNum == 0) return result;
		float activeAngle = activeNum*100/ totalNum;
		float expiredAngle = expiredNum*100/ totalNum;
		float completedAngle = completedNum*100/ totalNum;

		float[] percent = new float[]{activeAngle, expiredAngle, completedAngle};
		result.clear();
		result.add("Active", activeAngle/100);
		result.add("Expired", expiredAngle/100);
		result.add("Completed", completedAngle/100);
		return result;
	}



	public CategorySeries chartByImportance(){
		lastByTag = false;
		lastByState = false;
		lastByImportance = true;
		CategorySeries result = new CategorySeries("");
		lowNum = 0;
		moderateNum = 0;
		importantNum = 0;
		crucialNum = 0;
		//showData.setText("");
		wantTasksInTasktable = new ArrayList<task>();
		//		if (layout.getChildCount() > 4){
		//			layout.removeViews(4, 1);
		//		}
		String wantTags = tags.getText().toString();


		//		wantTasksInTasktable = Main.mgr.queryByTag("tags", wantTags, "tasktable");
		//		wantTasksInCompletedTasktable = Main.mgr.queryByTag("tags", wantTags, "completedTasktable");
		String[] tags = wantTags.trim().split(" ");
		if (tags.length == 0 || wantTags.equals("")) {
			tags = taskEdit.getAllTags();
		}
		for (String t : tags) {
			if (t.equals("")) continue;
			for (task task : taskEdit.tagToTask.get(t)) {
				if (!wantTasksInTasktable.contains(task)){
					wantTasksInTasktable.add(task);
				}
			}
		}

		//wantTasksInTasktable.addAll(wantTasksInCompletedTasktable);
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
		//showData.append("Total = " + totalNum + "\n");
		showData.append("Total = " + totalNum + "\n");
		showData.append("Low = " + lowNum + "\n");
		showData.append("Moderate = " + moderateNum + "\n");
		showData.append("Important = " + importantNum + "\n");
		showData.append("Crucial = " + crucialNum + "\n");

		//float uncompletAngle = uncompletedNum * 360 / totalNum;
		if (totalNum == 0) return result;
		float lowAngle = lowNum*100/totalNum;
		float moderateAngle = moderateNum*100/totalNum;
		float importantAngle = importantNum*100/ totalNum;
		float crucialAngle = crucialNum*100/totalNum;

		result.add("Low", lowAngle/100);
		result.add("Moderate", moderateAngle/100);
		result.add("Important", importantAngle/100);
		result.add("Crucial", crucialAngle/100);
		//		int[] colors =new int[]{Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN, Color.MAGENTA};
		//		int[] shade_colors = new int[]{Color.rgb(180, 180, 0),Color.rgb(180, 20, 10),Color.rgb(3, 23, 163),Color.rgb(15, 165, 0), Color.rgb(56, 71, 221)};
		float[] percent = new float[]{lowAngle, moderateAngle, importantAngle, crucialAngle};
		return result;
	}


}
