package org.rushme.timecat.tasks;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.rushme.timecat.R;
import org.rushme.timecat.R.array;
import org.rushme.timecat.R.drawable;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;
import org.rushme.timecat.R.style;
import org.rushme.timecat.data.DBManager;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class Main extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mModeTitles;

	MainActivity fragment = new MainActivity();
	completedList fragment1 = new completedList();
	statistics fragment2 = new statistics();
	settings fragment3 = new settings();
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

	private String date = null, time = null;
	private Menu currentMenu = null;
	public static DBManager mgr;

	MyExit myExit;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myExit = (MyExit)getApplication();
		myExit.setExit(false);
		
		mgr = new DBManager(this);				//initialize the database

		mTitle = mDrawerTitle = getTitle();
		mModeTitles = getResources().getStringArray(R.array.Mode_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mModeTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);    //????????

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
		Intent intent = getIntent();

		if (intent.getStringExtra("table") != null){ 

			selectItem(1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		currentMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		if (drawerOpen){
			menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
			menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
			menu.findItem(R.id.action_timeChoose).setVisible(!drawerOpen);
			menu.findItem(R.id.action_new).setVisible(!drawerOpen);
			menu.findItem(R.id.action_accept).setVisible(!drawerOpen);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_refresh:
			MainActivity.activeTasks = MainActivity.getData();
			MainActivity.activeListView.setAdapter(new SelfAdapter(Main.this, MainActivity.activeTasks, R.layout.item_active, MainActivity.mFrom, MainActivity.mTo));
			return true;
		case R.id.action_settings:
			System.out.println("settings!!!!!!!");
			return true;
		case R.id.action_timeChoose:
			showDialog(taskEdit.TIME_DIALOG);
			showDialog(taskEdit.DATE_DIALOG);
			return true;
		case R.id.action_new:
			intent.setClass(this, taskEdit.class);
			startActivity(intent);
			return true;
		case R.id.action_accept:
			
			fragment2.chart();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments      
		Bundle args = new Bundle();
		MainActivity fragment = new MainActivity();
		completedList fragment1 = new completedList();
		statistics nfragment2 = new statistics();
		settings fragment3 = new settings();
		
		FragmentManager fragmentManager = getFragmentManager();
		switch(position)
		{
		case 0:
			args.putInt(MainActivity.ARG_MODE_NUMBER, position);
			fragment.setArguments(args);
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			if (currentMenu != null){
				currentMenu.findItem(R.id.action_refresh).setVisible(true);
				currentMenu.findItem(R.id.action_timeChoose).setVisible(true);
				currentMenu.findItem(R.id.action_settings).setVisible(true);
				currentMenu.findItem(R.id.action_new).setVisible(true);
				currentMenu.findItem(R.id.action_accept).setVisible(false);
			}
			break;
		case 1:
			args.putInt(completedList.ARG_MODE_NUMBER, position);
			fragment1.setArguments(args);
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment1).commit();
			if (currentMenu != null){
				currentMenu.findItem(R.id.action_refresh).setVisible(false);
				currentMenu.findItem(R.id.action_timeChoose).setVisible(false);
				currentMenu.findItem(R.id.action_settings).setVisible(true);
				currentMenu.findItem(R.id.action_new).setVisible(true);
				currentMenu.findItem(R.id.action_accept).setVisible(false);
			}
			break;
		case 2:
			fragment2 = nfragment2;
			args.putInt(statistics.ARG_MODE_NUMBER, position);
			nfragment2.setArguments(args);
			fragmentManager.beginTransaction().replace(R.id.content_frame, nfragment2).commit();
			currentMenu.findItem(R.id.action_refresh).setVisible(false);
			currentMenu.findItem(R.id.action_timeChoose).setVisible(false);
			currentMenu.findItem(R.id.action_settings).setVisible(true);
			currentMenu.findItem(R.id.action_new).setVisible(true);
			currentMenu.findItem(R.id.action_accept).setVisible(true);
			break;
		case 3:
			args.putInt(settings.ARG_MODE_NUMBER, position);
			fragment3.setArguments(args);
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment3).commit();
			currentMenu.findItem(R.id.action_refresh).setVisible(false);
			currentMenu.findItem(R.id.action_timeChoose).setVisible(false);
			currentMenu.findItem(R.id.action_settings).setVisible(true);
			currentMenu.findItem(R.id.action_new).setVisible(true);
			currentMenu.findItem(R.id.action_accept).setVisible(false);
			break;    
		case 5: //exit
			myExit.setExit(true);
			finish();
			break;
		default:
			break;

		}


		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mModeTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onStart(){
		super.onStart();
		MyExit myExit = (MyExit)getApplication();
		if (myExit.isExit()){
			finish();
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		//when close the last Activity, release DB 
		Main.mgr.closeDB();
	}

	protected Dialog onCreateDialog(int id){
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		switch(id){
		case taskEdit.DATE_DIALOG:
			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Map<String, Object> m = null;
					date = monthOfYear+1 + "/" + dayOfMonth + "/" + year;
					//date.setText((monthOfYear+1) + "/" + dayOfMonth + "/" + year);
					MainActivity.activeTasks = MainActivity.getData();
					for (int i = 0; i < MainActivity.activeTasks.size(); i ++){   //only show the task that have deadline before the specific date and time
						m = MainActivity.activeTasks.get(i);
						try {
							if(sdf1.parse(Main.mgr.queryById(m.get("id").toString(), "tasktable").addTime).getTime() > sdfDate.parse(date).getTime()){	
								MainActivity.activeTasks.remove(m);
								i--;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					List<task> tasks = Main.mgr.queryCompleted();  //only query the informations of completed tasks
					for (task everyTask : tasks){
						Map<String,Object> m1 = new HashMap<String, Object>();
						try {
							if ((sdf1.parse(everyTask.addTime).getTime() > sdfDate.parse(date).getTime()) && (MainActivity.sdf.parse(everyTask.startTime).getTime() < sdfDate.parse(date).getTime())){
								m1.put("id", everyTask.id);
								m1.put("details", everyTask.details);
								m1.put("Description", everyTask.description);
								m1.put("time", everyTask.startTime);
								m1.put("table", "completedTasktable");
								MainActivity.activeTasks.add(m1);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					MainActivity.activeListView.setAdapter(new SelfAdapter(Main.this, MainActivity.activeTasks, R.layout.item_active, MainActivity.mFrom, MainActivity.mTo));

				}
			};
			dialog = new DatePickerDialog(Main.this, dateListener, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			break;
		case taskEdit.TIME_DIALOG:
			TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					Map<String, Object> m = null;
					//time.setText(hourOfDay + ":" + minute);
					time = hourOfDay + ":" + minute;
					if (date == null){
						Date today = new Date();
						date = sdfDate.format(today);
					}
					MainActivity.activeTasks = MainActivity.getData();
					for (int i = 0; i < MainActivity.activeTasks.size(); i ++){   //only show the task that have deadline before the specific date and time
						m = MainActivity.activeTasks.get(i);
						try {
							if(sdf1.parse(Main.mgr.queryById(m.get("id").toString(), "tasktable").addTime).getTime() > MainActivity.sdf.parse(date + " " + time).getTime()){
								MainActivity.activeTasks.remove(m);
								i--;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					List<task> tasks = Main.mgr.queryCompleted();  //only query the informations of completed tasks
					for (task everyTask : tasks){
						Map<String,Object> m1 = new HashMap<String, Object>();
						try {
							if ((sdf1.parse(everyTask.addTime).getTime() > MainActivity.sdf.parse(date + " " + time).getTime())&&(MainActivity.sdf.parse(everyTask.startTime).getTime() < sdfDate.parse(date + " " + time).getTime())){
								m1.put("id", everyTask.id);
								m1.put("details", everyTask.details);
								m1.put("Description", everyTask.description);
								m1.put("time", everyTask.startTime);
								m1.put("table", "completedTasktable");
								MainActivity.activeTasks.add(m1);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					MainActivity.activeListView.setAdapter(new SelfAdapter(Main.this, MainActivity.activeTasks, R.layout.item_active, MainActivity.mFrom, MainActivity.mTo));


				}
			};
			dialog = new TimePickerDialog(Main.this, timeListener, 
					calendar.get(Calendar.HOUR_OF_DAY), 
					calendar.get(Calendar.MINUTE), true); //whether is twenty-four-hour clock
			break;
		default:
			break;
		}
		return dialog;
	}


}