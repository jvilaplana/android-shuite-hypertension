
package com.android.bpcontrol.model;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.ForteTextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


public class LineChartItem extends ChartItem {

    private Context context;
    private int tag;

    public LineChartItem(ChartData<?> cd, Context c,int tag) {
        super(cd);
        this.tag = tag;
        this.context = c;

    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_linechart, null);
            holder.chart = (LineChart) convertView.findViewById(R.id.chart);
            holder.forte = (ForteTextView) convertView.findViewById(R.id.chartTitle);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (tag) {
            case 0:
                holder.forte.setText(context.getResources().getString(R.string.graphdescriptionheader1));
                break;
            case 1:
                holder.forte.setText(context.getResources().getString(R.string.graphdescriptionheader2));
                break;
            default:
                holder.forte.setText(context.getResources().getString(R.string.graphdescriptionheader3));
                break;
        }
        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
         holder.chart.setDrawGridBackground(false);
        xAxis.setDrawGridLines(false);
       xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setLabelCount(5);
        
        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setLabelCount(5);
       rightAxis.setDrawGridLines(false);



        holder.chart.setData((LineData) mChartData);
        LineDataSet set= holder.chart.getLineData().getDataSetByIndex(0);
        set.setCircleSize(8);
        set.setLineWidth(5);

        int height = getScreenHeightResolution(context);
        holder.chart.getLayoutParams().height = height-(int)height/5;

        holder.chart.animateX(1500);

        return convertView;
    }

    private static class ViewHolder {
        LineChart chart;
        ForteTextView forte;
    }


    private int getScreenHeightResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }


}
