package org.rushme.timecat.tasks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class SlideDelListview extends ListView {
	private float myLastX = -1;
	private float myLastY = -1;
	private boolean delete = false;
	private boolean markAs = false;
	// self defined listener
	private SlideDeleteListener slideDeleteListener;

	public SlideDelListview(Context context) {
		super(context);
	}

	public SlideDelListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// get the touch down coordinates
			myLastX = ev.getX(0);
			myLastY = ev.getY(0);
			break;

		case MotionEvent.ACTION_MOVE:
			float deltaX = ev.getX(ev.getPointerCount() - 1) - myLastX;
			float deltaY = Math.abs(ev.getY(ev.getPointerCount() - 1) - myLastY);
			if (deltaX < -200.0) {
				markAs = false;
				delete = true;
			} else if (deltaX > 200){
				markAs = true;
				delete = false;
			}else {
				markAs = false;
				delete = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (delete && slideDeleteListener != null) {
				slideDeleteListener.filpperDelete(myLastX, myLastY);
			} else if (markAs && slideDeleteListener != null) {
				if (MainActivity.curDel_btn != null || completedList.curDel_btn != null) {
					slideDeleteListener.filpperDelete(myLastX, myLastY);
				}else {
				slideDeleteListener.filpperMarkAs(myLastX, myLastY);
				}
			} else {
				slideDeleteListener.filpperOnclick(myLastX, myLastY);
			}
			reset();
			break;
		}

		return super.onTouchEvent(ev);
}

	public void reset() {
		delete = false;
		myLastX = -1;
		myLastY = -1;
	}

	public void setFlipperDeleteListener(SlideDeleteListener f) {
		slideDeleteListener = f;
	}
	
	public interface SlideDeleteListener {
		public void filpperDelete(float xPosition, float yPosition);
		public void filpperMarkAs(float myLastX, float myLastY);
		public void filpperOnclick(float xPosition, float yPosition);
	}
}