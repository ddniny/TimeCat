package org.rushme.timecat.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SelfAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private int mResource;
	private List<? extends Map<String, ?>> mSelfData;
	private String[] from;
	private int[] to;
	private LayoutInflater popLayoutInflater;
	private LinearLayout layMenu;
	private View view;
	private PopupWindow pw;

	public SelfAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
		this.mSelfData = data;
		this.mResource = resource;
		this.from = from;
		this.to = to;
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mSelfData.size();
	}

	@Override
	public Object getItem(int position) {
		return mSelfData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = mLayoutInflater.inflate(mResource, null);
		}
		Map<String, ?> item = mSelfData.get(position);

		int count = to.length; 
		for (int i = 0; i < count; i++){
			View v = convertView.findViewById(to[i]);
			bindView(v, item, from[i]);
		}
//		Button setBtn = (Button)convertView.findViewById(R.id.edit);
//		setBtn.setTag(position);
		convertView.setTag(position);
//		addListener(convertView); //?????
		return convertView;
	}

	/*
	 * set listener to the button in listView
	 */
//	public void addListener(final View convertView){
//		((Button)convertView.findViewById(R.id.edit)).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {	
//				int index = Integer.parseInt(v.getTag().toString());
//				initPopupWindow(index, convertView.getContext(), convertView);
//
//				//				new popWindow(index);
//
//
//
//			}
//		});
//
//
//	}

//	@SuppressWarnings("deprecation")
//	public void initPopupWindow(final int index, Context convertContext, View convertView){
//		popLayoutInflater = (LayoutInflater) convertContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
//		popLayoutInflater = LayoutInflater.from(convertContext);
//		View popWindowView = popLayoutInflater.inflate(R.layout.popup_active, null,false);
//		pw = new PopupWindow(popLayoutInflater.inflate(R.layout.popup_active, null, false),600,600, true);
//		//pw.setFocusable(true);    //enable dismiss() function to exit from the popup window
//		pw.setBackgroundDrawable(new BitmapDrawable());
//		pw.setOutsideTouchable(true);
//		pw.showAtLocation(convertView.findViewById(R.id.edit), Gravity.CENTER, 0, 0);
//		Button moveUp = (Button)popWindowView.findViewById(R.id.moveUp);
//		Button moveDown = (Button)popWindowView.findViewById(R.id.moveDown);
//		Button markCompleted = (Button)popWindowView.findViewById(R.id.markCompleted);
//		Button editAndView = (Button)popWindowView.findViewById(R.id.editAndView);
//		Button newTaskHigher = (Button)popWindowView.findViewById(R.id.newTaskHigher); //?????????????
//		Button markExpired = (Button)popWindowView.findViewById(R.id.markExpired);
//		Button delete = (Button)popWindowView.findViewById(R.id.delete);
//		System.out.println(getItem(index));
//
//		System.out.println("whywhywhy do not comming here????????");
//		moveUp.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				System.out.println(index + "!!!!!!index");
//				System.out.println("shouldbebebebebeb!!!");
//
//			}
//		});
//
//		moveDown.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				System.out.println("shouldbebebebebeb!!!");
//
//			}
//
//		});
//
//		markCompleted.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		editAndView.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		newTaskHigher.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		markExpired.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		delete.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//
//	}



	private void bindView(View view, Map<String, ?> item, String from){
		Object data = item.get(from);
		if (view instanceof TextView){
			((TextView) view).setText(data == null? "": data.toString());
		}
	}

}