package org.rushme.timecat.tasks;

import org.rushme.timecat.helper.ViewBase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class PieView extends ViewBase {
	int areaX = 1;
	int areaY = 22;
	int areaWidth;
	int areaHight;
	int colors[];
	int shade_colors[];
	float percent[];
	private int thickness=20;

	public PieView(Context context,int[] colors,int[] shade_colors,float[] percent) {
		super(context);
		this.colors = colors;
		this.shade_colors = shade_colors;
		this.percent = percent;
	}
	
	public void setThickness(int thickness) {
		this.thickness = thickness;
		areaY=thickness+2;
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		areaWidth=width-2;
		areaHight=height-2;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		for(int i=0;i<=thickness;i++){
			float tempAngle=0;
			for(int j=0;j<percent.length;j++){
				paint.setColor(shade_colors[j]);
				canvas.drawArc(new RectF(areaX, areaY-i, areaX+areaWidth, areaHight-i), tempAngle,percent[j], true, paint);
				tempAngle+=percent[j];
			}
			if(i==thickness){
				for(int j=0;j<percent.length;j++){
					paint.setColor(colors[j]);
					canvas.drawArc(new RectF(areaX, areaY-i, areaX+areaWidth, areaHight-i), tempAngle,percent[j], true, paint);
					tempAngle+=percent[j];
				}
			}
		}
	}
}
