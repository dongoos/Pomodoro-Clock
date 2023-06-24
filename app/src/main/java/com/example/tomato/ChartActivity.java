package com.example.tomato;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tomato.adapter.App_usage_details;
import com.example.tomato.bean.App_info;
import com.example.tomato.util.ToastUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends Activity  {
    //柱状图
    private static BarChart barChart;
    //饼状图
    private static PieChart pieChart;
    //ListView
    private static AdapterView lv_appinfo;
    //lists
    static ArrayList<BarEntry> barEntries = new ArrayList<>();
    static ArrayList<PieEntry> pieEntries = new ArrayList<>();
    //初始化
    public static void initialize_chart(MainActivity activity){
        barChart = activity.getBc();
        pieChart = activity.getPc();
        lv_appinfo = activity.getLa();

    //use for loop
    for (int i = 1; i < 10; i++) {
        // 将值转换为浮点型
        float value = (float) (i * 10.0);
        // 初始化 BarEntry 对象
        BarEntry barEntry = new BarEntry(i, value);
        // 初始化 PieEntry 对象
        PieEntry pieEntry = new PieEntry(i, value);
        // 将对象添加到数组列表中
        barEntries.add(barEntry);
        pieEntries.add(pieEntry);
    }


    //Initialize bat date set
    BarDataSet barDataSet =new BarDataSet(barEntries,"time");
    //set colors
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
    //Hide draw value
        barDataSet.setDrawValues(false);
    //Set bar data
        barChart.setData(new BarData(barDataSet));
    //set animation
        barChart.animateY(5000);
    //Set description text and color
        barChart.getDescription().setText("time");
        barChart.getDescription().setTextColor(Color.BLUE);

    //Initialize pie data set
    PieDataSet pieDataSet = new PieDataSet(pieEntries,"day");
    //Set colors
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
    //Set pie data
        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateXY(5000,5000);
    //Hide description
        pieChart.getDescription().setEnabled(false);


    //获取默认的列表信息
        List<App_info> appInfoList = App_info.getDefaultList();
        //构建适配器
        App_usage_details adapter = new App_usage_details(activity, appInfoList);
        lv_appinfo.setAdapter(adapter);
        lv_appinfo.setOnItemClickListener(activity);
    }
}
