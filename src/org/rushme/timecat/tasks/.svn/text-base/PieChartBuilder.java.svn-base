package org.rushme.timecat.tasks;


import java.text.NumberFormat;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PieChartBuilder{

	private static int[] COLORS = new int[] { Color.RED, Color.GREEN,
			Color.BLUE, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.DKGRAY };
	double data[] = new double[] { 20, 30, 40, 50, 60, 70, 80, 90, 100 };

	private CategorySeries mSeries = new CategorySeries("");// PieChart的DataSet
															// 其实就是一些键值对，跟Map使用方法差不多

	private DefaultRenderer mRenderer = new DefaultRenderer();// PieChart的主要描绘器

	private GraphicalView mChartView;// 用来显示PieChart 需要在配置文件Manifest中添加
										// <activity
										// android:name="org.achartengine.GraphicalActivity"
										// />

	private LinearLayout mLinear;

	private static double VALUE = 0;// 总数

	private Context context;
	private String title;
	
	public PieChartBuilder(Context context, CategorySeries mSeries, String title) {
		this.context = context;
		this.mSeries = mSeries;
		this.title = title;
	}
	
	public GraphicalView getPieChart(){
		mRenderer.setZoomButtonsVisible(true);// 显示放大缩小功能按钮
		mRenderer.setStartAngle(180);// 设置为水平开始
		mRenderer.setDisplayValues(true);// 显示数据
		mRenderer.setFitLegend(true);// 设置是否显示图例
		mRenderer.setLegendTextSize(20);// 设置图例字体大小
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLegendHeight(20);// 设置图例高度
		mRenderer.setChartTitle(title);// 设置饼图标题

		for (int i = 0; i < data.length; i++)
			VALUE += data[i];
		for (int i = 0; i < mSeries.getItemCount(); i++) {
			//mSeries.add("示例 " + (i + 1), data[i] / VALUE);// 设置种类名称和对应的数值，前面是（key,value）键值对
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			if (i < COLORS.length) {
				renderer.setColor(COLORS[i]);// 设置描绘器的颜色
			} else {
				renderer.setColor(getRandomColor());// 设置描绘器的颜色
			}
			renderer.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
			mRenderer.setChartTitleTextSize(20);// 设置饼图标题大小
			mRenderer.addSeriesRenderer(renderer);// 将最新的描绘器添加到DefaultRenderer中
		}

		if (mChartView == null) {// 为空需要从ChartFactory获取PieChartView
			mChartView = ChartFactory.getPieChartView(context,
					mSeries, mRenderer);// 构建mChartView
			mRenderer.setClickEnabled(true);// 允许点击事件
			mChartView.setOnClickListener(new View.OnClickListener() {// 具体内容
						@Override
						public void onClick(View v) {
							SeriesSelection seriesSelection = mChartView
									.getCurrentSeriesAndPoint();// 获取当前的类别和指针
							if (seriesSelection == null) {
								Toast.makeText(context,
										"您未选择数据", Toast.LENGTH_SHORT).show();
							} else {
								for (int i = 0; i < mSeries.getItemCount(); i++) {
									mRenderer.getSeriesRendererAt(i)
											.setHighlighted(
													i == seriesSelection
															.getPointIndex());
								}
								mChartView.repaint();
								Toast.makeText(
										context,
										"The percentage is "
												+ NumberFormat
														.getPercentInstance()
														.format(seriesSelection
																.getValue()),
										Toast.LENGTH_SHORT).show();
							}
						}
					});
			return mChartView;
		} else {
			mChartView.repaint();
		}
		return mChartView;
	}

	private int getRandomColor() {// 分别产生RBG数值
		Random random = new Random();
		int R = random.nextInt(255);
		int G = random.nextInt(255);
		int B = random.nextInt(255);
		return Color.rgb(R, G, B);
	}
}
