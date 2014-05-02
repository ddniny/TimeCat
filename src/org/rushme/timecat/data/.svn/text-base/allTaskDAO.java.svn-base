package org.rushme.timecat.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.rushme.timecat.DataBean.CustomListBean;
import org.rushme.timecat.tasks.Main;
import org.rushme.timecat.tasks.MainActivity;
import org.rushme.timecat.tasks.task;
import org.rushme.timecat.tasks.taskEdit;

import android.database.sqlite.SQLiteDatabase;

public class allTaskDAO {
	private DBHelper helper;
	private SQLiteDatabase db;
	private SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");


	public allTaskDAO(){
		helper = DBManager.getDBHelper();
		db = helper.getWritableDatabase();
	}

	public ArrayList<task> getProperList(CustomListBean bean){
		ArrayList<task> tasks = new ArrayList<task>();
		String importance = bean.getImportance();
		String orderBy = bean.getOrderBy();
		String state = bean.getState();
		String tags = bean.getTags();
		String csDate = null;
		String ceDate = null;
		String dsDate = null;
		String deDate = null;
		if (bean.getCreatedBetween() != null && bean.getCreatedBetween().length() != 0) {
			String [] cstring = bean.getCreatedBetween().split(" ");
			csDate = cstring[0];
			if (cstring.length > 1) {
				ceDate = bean.getCreatedBetween().split(" ")[1];
			}
		}
		if (bean.getDeadlineBetween() != null && bean.getDeadlineBetween().length() != 0) {
			String[] dstring = bean.getDeadlineBetween().split(" ");
			dsDate = dstring[0];
			if (dstring.length > 1) {
				deDate = dstring[1];
			}
		}
		if (tags != null && tags.length() != 0) {
			String[] tagArray = tags.trim().split(" ");
			for (String t : tagArray) {
				if (t.equals("")) continue;
				taskEdit.getAllTags();
				ArrayList<task> tmp = taskEdit.tagToTask.get(t);
				for (task task : tmp) {
					if (!tasks.contains(task)){
						tasks.add(task);
					}
				}
			}
		} else {
			tasks.addAll(Main.mgr.queryActive());
			tasks.addAll(Main.mgr.queryExpired());
			tasks.addAll(Main.mgr.queryCompleted());
		}

		if (!importance.equals("Any")) {
			for (int i = 0; i < tasks.size(); i++) {
				if (!tasks.get(i).getImportance.toString().equals(importance)) {
					tasks.remove(i);
					i--;
				}
			}
		}

		if (!state.equals("Any")) {
			String[] states = state.split(" ");
			if (states.length == 2) {
				for (int i = 0; i < tasks.size(); i++) {
					if ((!tasks.get(i).getState.toString().equals(states[0])) && (!tasks.get(i).getState.toString().equals(states[1]))) {
						tasks.remove(i);
						i--;
					}
				}
			} else {
				for (int i = 0; i < tasks.size(); i++) {
					if (!tasks.get(i).getState.toString().equals(state)) {
						tasks.remove(i);
						i--;
					}
				}
			}

		}

		if (csDate != null && csDate.length() != 0) {
			for (int i = 0; i < tasks.size(); i++) {
				try {
					if (sdf1.parse(tasks.get(i).addTime).before(sdf1.parse(csDate))) {
						tasks.remove(i);
						i--;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (ceDate != null && ceDate.length() != 0) {
			for (int i = 0; i < tasks.size(); i++) {
				try {
					if (sdf1.parse(tasks.get(i).addTime).after(sdf1.parse(ceDate))) {
						tasks.remove(i);
						i--;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (dsDate != null && dsDate.length() != 0) {
			for (int i = 0; i < tasks.size(); i++) {
				try {
					if (sdf1.parse(tasks.get(i).endTime).before(sdf1.parse(dsDate))){// || sdf1.parse(tasks.get(i).endTime).after(sdf1.parse(deDate))) {
						tasks.remove(i);
						i--;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 

		if (deDate != null && deDate.length() != 0) {
			for (int i = 0; i < tasks.size(); i++) {
				try {
					if (sdf1.parse(tasks.get(i).endTime).after(sdf1.parse(deDate))) {
						tasks.remove(i);
						i--;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (orderBy.equals("Priority")) {
			orderByPriority(tasks);
		} else if (orderBy.equals("State")) {
			orderByState(tasks);
		} else if (orderBy.equals("Task name")) {
			orderByTaskName(tasks);
		} else if (orderBy.equals("Add time")) {
			orderByAddTime(tasks);
		}
		return tasks;
	}

	public void orderByAddTime(ArrayList<task> tasks){
		Collections.sort(tasks, new Comparator<task>(){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			@Override
			public int compare(task t1, task t2) {
				// TODO Auto-generated method stub
				try {
					Date d1 = sdf.parse(t1.addTime);
					Date d2 = sdf.parse(t2.addTime);
					if (d1.before(d2)) {
						return 1;
					} if (d1.after(d2)) {
						return -1;
					} else return 0;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return 0;
			}

		});
	}

	public void orderByPriority(ArrayList<task> tasks){
		Collections.sort((List<task>)tasks, new Comparator<task>() {
			@Override
			public int compare(task task1, task task2) {
				Date now = new Date();
				float score1, score2;
				score1 = MainActivity.getScore(task1);
				score2 = MainActivity.getScore(task2);
				System.out.println("score1:"+score1+ task1.details+" score2:"+score2 +task2.details+"!!!!!!!!!!!");

				if (score1 > score2) return -1;
				else return 1;
			}
		});
	}
	public void orderByState(ArrayList<task> tasks){
		Collections.sort((List<task>)tasks, new Comparator<task>() {
			@Override
			public int compare(task task1, task task2) {
				return task1.getState.toString().compareTo(task2.getState.toString());
			}
		});
	}

	public void orderByTaskName(ArrayList<task> tasks) {
		Collections.sort((List<task>)tasks, new Comparator<task>() {
			@Override
			public int compare(task task1, task task2) {
				if (task1.description != null && task2.description!= null) {
					return task1.description.compareTo(task2.description);
				} else if (task1.description == null) {
					return 1;
				} else return -1;
			}
		});
	}

}
