package com.example.tomato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;
    //柱状图
    private static BarChart barChart;
    //饼状图
    private static PieChart pieChart;
    //Record_Button
    private Button  buttonDay,buttonMonth,buttonYear,OpenButton;

    //list
    private ListView listView;

    //文本框
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;

    private static Button btn_info,btn_friend,btn_achievement,btn_feedback,btn_setting;
    private static Button bt_time;
    private static TextView timer;
    private static ProgressBar progress;


    private List<View> mViews;   //存放视图

    //create instance of timer to allow for the clicklistener to be elsewhere
    Timer timerButton = new Timer();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化数据

        //对单选按钮进行监听，选中、未选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rb_lock) {
                    mViewPager.setCurrentItem(0);
                } else if (id == R.id.rb_record) {
                    mViewPager.setCurrentItem(1);
                } else if (id == R.id.rb_me) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });

    }
@TestOnly
public static void c(){
    System.out.println(1);
}

    //get function to allow for the button to be used outside
    public static Button getBtnT(){
        return bt_time;
    }
    public static TextView getTimer(){
        return timer;
    }

    public static ProgressBar getPB(){
        return progress;
    }



    //初始化图表
    public static BarChart getBc(){
        return barChart;
    }
    public static PieChart getPc(){
        return pieChart;
    }
    //初始化列表
    public Button getOBtn(){
        return OpenButton;
    }
    public static Button getBtnInfo(){return btn_info;}
    public static Button getBtnFriend(){return btn_friend;}
    public static Button getBtnAchievement(){return btn_achievement;}
    public static Button getBtnFeedback(){return btn_feedback;}
    public static Button getBtnSetting(){return btn_setting;}
    public Button getBtnD(){return buttonDay;}
    public Button getBtnM(){return buttonMonth;}
    public Button getBtnY(){return buttonYear;}

    public ListView getLv(){return listView;}
    private void initView() {
        //初始化控件
        mViewPager=findViewById(R.id.viewpager);
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_lock);
        tab2=findViewById(R.id.rb_record);
        tab3=findViewById(R.id.rb_me);

        mViews=new ArrayList<View>();//加载，添加视图
        mViews.add(LayoutInflater.from(this).inflate(R.layout.activity_lock,null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.activity_record,null));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.activity_me,null));

        //Your Views‘ buttons

        btn_info=mViews.get(2).findViewById(R.id.infoButton);
        bt_time=mViews.get(0).findViewById(R.id.btnStart);
        //TextViews
        timer=mViews.get(0).findViewById(R.id.timer);
        progress=mViews.get(0).findViewById(R.id.progressBar);
        btn_friend=mViews.get(2).findViewById(R.id.btn_friend);
        btn_achievement=mViews.get(2).findViewById(R.id.btn_achievements);
        btn_feedback=mViews.get(2).findViewById(R.id.btn_feedback);
        btn_setting=mViews.get(2).findViewById(R.id.btn_setting);
        //柱状图和饼状图
        barChart = mViews.get(1).findViewById(R.id.bar_chart);
        pieChart = mViews.get(1).findViewById(R.id.pie_chart);
        //record_background
        tv5 = mViews.get(1).findViewById(R.id.tv5);
        tv6 = mViews.get(1).findViewById(R.id.tv6);
        tv7 = mViews.get(1).findViewById(R.id.tv7);
        tv5.setBackgroundResource(R.drawable.shape_rect);
        tv6.setBackgroundResource(R.drawable.shape_rect);
        tv7.setBackgroundResource(R.drawable.shape_rect);

        //ButtonListener
        bt_time.setOnClickListener(timerButton);
        //Record_Button
        OpenButton = mViews.get(1).findViewById(R.id.OpenButton);
        buttonDay = mViews.get(1).findViewById(R.id.daybuttonlist3);
        buttonMonth = mViews.get(1).findViewById(R.id.monthbuttonlist3);
        buttonYear = mViews.get(1).findViewById(R.id.yearbuttonlist3);
        listView = mViews.get(1).findViewById(R.id.AppStatisticsList);

        //设置一个适配器
        mViewPager.setAdapter(new MyViewPagerAdapter());
        //对viewpager监听，让分页和底部图标保持一起滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 页面滚动时的逻辑处理（可选）
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tab1.setChecked(true);
                        tab2.setChecked(false);
                        tab3.setChecked(false);
                        break;
                    case 1:
                        tab1.setChecked(false);
                        tab2.setChecked(true);
                        tab3.setChecked(false);
                        OpenAccess openAccess = new OpenAccess();
                        openAccess.initialize_button(MainActivity.this);
                        RecordPageInfo recordPageInfo = new RecordPageInfo(MainActivity.this);
                        recordPageInfo.init(MainActivity.this);
                        ChartActivity.initialize_chart(MainActivity.this);
                        break;
                    case 2:
                        tab1.setChecked(false);
                        tab2.setChecked(false);
                        tab3.setChecked(true);
                        MeViewPager.init(MainActivity.this);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面滚动状态变化时的逻辑处理（可选）
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    //ViewPager适配器
    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject( View view,  Object object) {
            return view==object;
        }

        @Override
        public void destroyItem( ViewGroup container, int position,  Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem( ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }
}

