package com.example.tomato;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;  //three button
    private List<View> mViews;   //存放视图
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

        mViewPager.setAdapter(new MyViewPagerAdapter());//设置一个适配器
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

