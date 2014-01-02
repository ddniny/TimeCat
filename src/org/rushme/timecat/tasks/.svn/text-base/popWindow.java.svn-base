package org.rushme.timecat.tasks;

import org.rushme.timecat.R;
import org.rushme.timecat.R.id;
import org.rushme.timecat.R.layout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

public class popWindow implements View.OnClickListener {
	public popWindow(int index){
		LayoutInflater popLayoutInflater = (LayoutInflater) MainActivity.ma.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		//		popLayoutInflater = LayoutInflater.from(this);
		View popWindowView = popLayoutInflater.inflate(R.layout.popup_active, null, false);
		PopupWindow pw = new PopupWindow(popLayoutInflater.inflate(R.layout.popup_active, null, false),600,600, true);
		pw.setFocusable(true);    //enable dismiss() function to exit from the popup window
		pw.setBackgroundDrawable(new BitmapDrawable());
		//pw.setOutsideTouchable(true);
		pw.setTouchable(true);
		pw.showAtLocation(MainActivity.ma.findViewById(R.id.edit), Gravity.CENTER, 0, 0);
		Button moveUp = (Button)popWindowView.findViewById(R.id.moveUp);
		Button moveDown = (Button)popWindowView.findViewById(R.id.moveDown);
		Button markCompleted = (Button)popWindowView.findViewById(R.id.markCompleted);
		Button editAndView = (Button)popWindowView.findViewById(R.id.editAndView);
		Button newTaskHigher = (Button)popWindowView.findViewById(R.id.newTaskHigher); //?????????????
		Button markExpired = (Button)popWindowView.findViewById(R.id.markExpired);
		Button delete = (Button)popWindowView.findViewById(R.id.delete);
		//		System.out.println(getItem(index));

		moveUp.setOnClickListener(this);
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
		//
		//	@Override
		//	public void onClick(View v) {
		//		// TODO Auto-generated method stub
		//		
		//	}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.moveUp){
			System.out.println("shouldbebebebebeb!");
		}

	}
}
