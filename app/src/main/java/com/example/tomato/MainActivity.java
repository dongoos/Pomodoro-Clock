package com.example.tomato;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;
    //Your buttons
    private Button bt_info;
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
        bt_info=mViews.get(2).findViewById(R.id.infoButton);
        bt_time=mViews.get(0).findViewById(R.id.btnStart);
        //TextViews
        timer=mViews.get(0).findViewById(R.id.timer);
        progress=mViews.get(0).findViewById(R.id.progressBar);



        //ButtonListener
        bt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,ViewPagerInfo.class);
                startActivity(intent);



            }
        });


        bt_time.setOnClickListener(timerButton);

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
                        break;
                    case 2:
                        tab1.setChecked(false);
                        tab2.setChecked(false);
                        tab3.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面滚动状态变化时的逻辑处理（可选）
            }
        });
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

