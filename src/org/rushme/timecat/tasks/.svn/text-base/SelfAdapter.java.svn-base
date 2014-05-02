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
import android.graphics.Color;
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
import android.widget.CheckBox;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = mLayoutInflater.inflate(mResource, null);
		}
		Map<String, ?> item = mSelfData.get(position);

		int count = to.length; 
		for (int i = 0; i < count; i++){
			View v = convertView.findViewById(to[i]);
			bindView(v, item, from[i]);
		}
		Button delBtn = (Button)convertView.findViewById(R.id.del);
		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
		convertView.setTag(position);
		checkBox.setOnClickListener(new OnClickListener() { 
			@Override         
			public void onClick(View v) {   
				if(Main.isSelected.get(position)){ 
					Main.isSelected.put(position, false);          
				}else{           
					Main.isSelected.put(position, true);          
				}         
				notifyDataSetChanged();        
			}        
		});

		return convertView;
	}

	private void bindView(View view, Map<String, ?> item, String from){
		Object data = item.get(from);
		if (view instanceof TextView){
			((TextView) view).setText(data == null? "": data.toString());
			if (item.get("time").toString().contains("Expired")) {
				((TextView) view).setTextColor(Color.GRAY);
			}else {
				((TextView) view).setTextColor(Color.BLACK);
			}
		}
	}

}
