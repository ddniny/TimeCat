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
	
	private float centerX;
	private float centerY;
	private Paint legendPaint; 
	private Paint paint;

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
		
		String[] info = {"1", "2", "3", "4"};
		legendPaint = new Paint();
		legendPaint.setColor(Color.BLACK);
		legendPaint.setStrokeWidth(1f);
		
		paint = new Paint();
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
			
			if(i  == thickness ){
				
				RectF rectF = new RectF(areaX, areaY- thickness, areaX + areaWidth, areaHight- thickness);
				centerX = rectF.centerX();
				centerY = rectF.centerY();

				int temp = areaHight + 20/* height-320*/;				
				
	            for(int j=0;j<percent.length;j++){
					
					
					paint.setColor(colors[j]);
					canvas.drawArc(rectF , tempAngle,percent[j], true, paint);
					tempAngle+=percent[j];
					
					
					RectF rect = new RectF(areaX, temp, areaX+40, temp-10);
					canvas.drawText(info[j], areaX+60, temp, legendPaint);
					canvas.drawRect(rect, paint);
					temp += 25;
				}
			}
		}
		
		
	}
}
