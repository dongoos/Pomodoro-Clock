package com.example.tomato;


import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tomato.adapter.EventListAdapter;
import com.example.tomato.model.Model;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
   //<test
    static final int RESULT_ENABLE = 1 ;
    DevicePolicyManager deviceManger ;
    ComponentName compName ;
    //>
    //<test2// Allowlist two apps.
    private static final String KIOSK_PACKAGE = "com.example.tomato";
    private static final String PLAYER_PACKAGE = "com.example.player";
    private static final String[] APP_PACKAGES = {KIOSK_PACKAGE, PLAYER_PACKAGE};
    //
    //// ...
    //

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;





    private static Button bt_time,btn_wl;
    private static ImageButton btn_event;
    //Widgets from Activity_lock

    private static TextView timer;
    private static ProgressBar progress;
    private AlertDialog dlgTime;
    private static int setTime;
    private RecyclerView eventRecyclerView;
    private EventListAdapter elAdapter;
    private List<Model> eventList;


    private static ImageButton ibtn_setting;



    private static List<View> mViews;   //存放视图


    //create instance of timer to allow for the clicklistener to be elsewhere
    Timer timerButton = new Timer();

    //lock inits
    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName componentName;
    private ActivityOptions options;
    private PackageManager packageManager;
    private Context context;
    private Intent launchIntent;
    private DatabaseHandler db;
    int min = 0;
    int sec = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化数据

        //对单选按钮进行监听，选中、未选中
        //lock device properties init
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        options = ActivityOptions.makeBasic();
        context = MainActivity.this;
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE) ;
        componentName = new ComponentName(this, MyAdmin.class);
        //mRefSetActiveAdmin(componentName, false);


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



//    @Override
//    public void onResume(){
//        super.onResume();
//        if(devicePolicyManager.isLockTaskPermitted(context.getPackageName())){
//          MainActivity.this.startLockTask();
//            Log.i("testing","somethings working ig?");
//        }else{
//            Log.i("testing","so uhh idk what this does but u know");
//        }
//    }


    protected void OnResume(){
        super.onResume();
        boolean isActive = devicePolicyManager.isAdminActive(componentName);
    }

    public void createEvent(){
        View dlgViewTime = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_create_times, null);
        Button btn_evtSubmit = dlgViewTime.findViewById(R.id.submitEvent);
        Button btn_cancel = dlgViewTime.findViewById(R.id.cancelEvent);
        EditText newEventTitle = dlgViewTime.findViewById(R.id.eventTitle);



        NumberPicker minPicker =dlgViewTime.findViewById(R.id.minutePicker);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(36);

        minPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d",i*5);
            }
        });

        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                min = i;
            }
        });

        NumberPicker secPicker =dlgViewTime.findViewById(R.id.secPicker);
        secPicker.setMinValue(0);
        secPicker.setMaxValue(59);

        secPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d",i);
            }
        });

        secPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                sec = i;
            }
        });
        //minPicker.setOn
        //ValueChangedListener( this);

        //@Override
//        public onValueChange(NumberPicker numberPicker, int i , int j){
//
//        }
        db = new DatabaseHandler(this);
        db.openDatabase();
        boolean isUpdate = false;





        btn_evtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model task = new Model();
                task.setTask(newEventTitle.getText().toString());
                eventList.add(task);
                db.insertEvent(task);
                elAdapter.setEvent(eventList);
                Log.i("dbTest",eventList.toString());
                Log.i("dbTest",db.getAllEvents().toString());

                dlgTime.dismiss();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgTime.dismiss();
            }
        });
        dlgTime = new AlertDialog.Builder(MainActivity.this)
                .setView(dlgViewTime)
                .create();
        dlgTime.show();
        Log.i("Testing","Ok so were here at least");
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

        bt_time=mViews.get(0).findViewById(R.id.btnStart);
        btn_wl=mViews.get(0).findViewById(R.id.whiteListBtn);
        btn_event=mViews.get(0).findViewById(R.id.addEventBtn);
        timer=mViews.get(0).findViewById(R.id.timer);
        progress=mViews.get(0).findViewById(R.id.progressBar);



        eventList = new ArrayList<>();
        eventRecyclerView = mViews.get(0).findViewById(R.id.eventList);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        elAdapter = new EventListAdapter(this);
        eventRecyclerView.setAdapter(elAdapter);
        db = new DatabaseHandler(this);
        db.openDatabase();
        eventList = db.getAllEvents();
        elAdapter.setEvent(eventList);

        //ButtonListener



        btn_wl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //devicePolicyManager.lockNow();
                startLockTask();
//                if(devicePolicyManager.isAdminActive(componentName)){
//                 startLockTask();
//                }else{
//                    Toast.makeText(context, "Device Admin aint enabled :(", Toast.LENGTH_SHORT).show();
//                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    Log.i("Testing","SDK VERSION RIGHT!!");
                    if(options.getLockTaskMode()==true){
                        options.setLockTaskEnabled(false);
                    }else{
                        options.setLockTaskEnabled(true);
                    }

                }else{
                    Log.i("Testing","Uhhh do some research my guy");
                }


//                startLockTask();
            }
        });

        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceManger = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                compName = new ComponentName(MainActivity.this, DeviceAdmin.class);
                boolean active = deviceManger.isAdminActive(compName);
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "You should enable the app!");
                startActivityForResult(intent, RESULT_ENABLE);

            }


        });

//        btn_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createEvent();
//
//            }
//        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createEvent();
            }

        });
        bt_time.setOnClickListener(timerButton);
        //Record_Button



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
    public  static  View getView0(){
        return mViews.get(0);
    } ;public  static  View getView1(){
        return mViews.get(1);
    } ;
    public  static  View getView2(){
        return mViews.get(2);
    } ;
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

