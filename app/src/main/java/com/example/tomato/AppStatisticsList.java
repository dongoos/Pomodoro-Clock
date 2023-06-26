package com.example.tomato;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.tomato.appUsage.AppInformation;
import com.example.tomato.appUsage.StatisticsInfo;

public class AppStatisticsList extends AppCompatActivity {
    private int style; // 统计样式，用于表示统计是按天、周、月还是年进行
    private long totalTime; // 总运行时间
    private int totalTimes; // 总操作次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_statistics_list);
        this.style = StatisticsInfo.DAY;

        // 按钮点击事件监听器
        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        buttonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.DAY) {
                    style = StatisticsInfo.DAY;
                    onResume();
                }
            }
        });

//        Button buttonweek = (Button) findViewById(R.id.weekbuttonlist3);
//        buttonweek.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(style != StatisticsInfo.WEEK) {
//                    style = StatisticsInfo.WEEK;
//                    onResume();
//                }
//            }
//        });

        Button buttonmonth = (Button) findViewById(R.id.monthbuttonlist3);
        buttonmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.MONTH) {
                    style = StatisticsInfo.MONTH;
                    onResume();
                }
            }
        });

        Button buttonyear = (Button) findViewById(R.id.yearbuttonlist3);
        buttonyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.YEAR) {
                    style = StatisticsInfo.YEAR;
                    onResume();
                }
            }
        });
//
//        Button buttonbar = (Button) findViewById(R.id.BarButton3);
//        buttonbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AppStatisticsList.this, BarChartActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        Button buttonpie = (Button) findViewById(R.id.PieButton3);
//        buttonpie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AppStatisticsList.this, PiePolylineChartActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    // 设置按钮颜色
    private void SetButtonColor() {
        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        Button buttonmonth = (Button) findViewById(R.id.monthbuttonlist3);
        Button buttonyear = (Button) findViewById(R.id.yearbuttonlist3);
//        Button buttonweek = (Button) findViewById(R.id.weekbuttonlist3);
        Button buttonpie = (Button) findViewById(R.id.PieButton3);
        Button buttonbar = (Button) findViewById(R.id.BarButton3);
        Button buttonlist = (Button) findViewById(R.id.ListButton3);

        // 设置按钮文字颜色为白色
        buttonday.setTextColor(Color.WHITE);
        buttonmonth.setTextColor(Color.WHITE);
//        buttonweek.setTextColor(Color.WHITE);
        buttonyear.setTextColor(Color.WHITE);
        buttonbar.setTextColor(Color.WHITE);
        buttonpie.setTextColor(Color.WHITE);
        buttonlist.setTextColor(Color.WHITE);

        // 根据统计样式设置相应按钮的文字颜色
        switch (style) {
            case StatisticsInfo.DAY:
                buttonday.setTextColor(Color.GREEN);
                break;
            case StatisticsInfo.MONTH:
                buttonmonth.setTextColor(Color.GREEN);
                break;
//            case StatisticsInfo.WEEK:
//                buttonweek.setTextColor(Color.GREEN);
//                break;
            case StatisticsInfo.YEAR:
                buttonyear.setTextColor(Color.GREEN);
                break;
        }

        // 根据当前活动的界面设置相应按钮的文字颜色
        String classname = this.getClass().getName();
        if (classname.contains("BarChartActivity")) {
            buttonbar.setTextColor(Color.YELLOW);
        } else if (classname.contains("AppStatisticsList")) {
            buttonlist.setTextColor(Color.YELLOW);
        } else if (classname.contains("PiePolylineChartActivity")) {
            buttonpie.setTextColor(Color.YELLOW);
        }
    }

    // 每次重新进入界面的时候加载listView
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SetButtonColor();

        List<Map<String, Object>> datalist = null;

        // 创建 StatisticsInfo 对象，根据统计样式获取相应的统计信息
        StatisticsInfo statisticsInfo = new StatisticsInfo(this, this.style);
        totalTime = statisticsInfo.getTotalTime();
        totalTimes = statisticsInfo.getTotalTimes();
        datalist = getDataList(statisticsInfo.getShowList());

        ListView listView = (ListView) findViewById(R.id.AppStatisticsList);
        SimpleAdapter adapter = new SimpleAdapter(this, datalist, R.layout.inner_list,
                new String[]{"label", "info", "times", "icon"},
                new int[]{R.id.label, R.id.info, R.id.times, R.id.icon});
        listView.setAdapter(adapter);

        // 设置视图绑定器，用于设置图标的显示
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof Drawable) {
                    ImageView iv = (ImageView) view;
                    iv.setImageDrawable((Drawable) o);
                    return true;
                } else return false;
            }
        });
    }

    // 获取数据列表
    private List<Map<String, Object>> getDataList(ArrayList<AppInformation> ShowList) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        // 添加全部应用的统计信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("label", "全部应用");
        map.put("info", "运行时间: " + DateUtils.formatElapsedTime(totalTime / 1000));
        map.put("times", "本次开机操作次数: " + totalTimes);
//      map.put("icon", R.drawable.use);
        dataList.add(map);

        // 添加每个应用的统计信息
        for (AppInformation appInformation : ShowList) {
            map = new HashMap<String, Object>();
            map.put("label", appInformation.getLabel());
            map.put("info", "运行时间: " + DateUtils.formatElapsedTime(appInformation.getUsedTimebyDay() / 1000));
            map.put("times", "本次开机操作次数: " + appInformation.getTimes());
            map.put("icon", appInformation.getIcon());
            dataList.add(map);
        }

        return dataList;
    }

}
