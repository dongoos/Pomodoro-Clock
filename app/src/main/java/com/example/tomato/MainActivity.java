package com.example.tomato;


import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tomato.adapter.EventListAdapter;
import com.example.tomato.model.Model;
import com.example.tomato.util.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
   //<test
    static final int RESULT_ENABLE = 1 ;
    DevicePolicyManager deviceManger ;
    ComponentName compName ;


    private AlertDialog dialog;

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3;





    private static Button bt_time,btn_wl;
    private static ImageButton btn_event;
    //Widgets from Activity_lock

    private static TextView timer;
    private static ProgressBar progress;
    private static AlertDialog dlgTime;
    private static long setTime;
    private RecyclerView eventRecyclerView;
    private static EventListAdapter elAdapter;
    private static List<Model> eventList;


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
    private static DatabaseHandler db;
    static int min = 0;
    static int sec = 0;

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

    public static void setTimeMili(long timeMili) {
        setTime = timeMili;
    }
    public static long getTimeMili(){
        return setTime;
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

    public static void createEvent(MainActivity activity, boolean update, int eid){
        View dlgViewTime = LayoutInflater.from(activity).inflate(R.layout.dialog_create_times, null);

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
        db = new DatabaseHandler(activity);
        db.openDatabase();
        if(update){
            newEventTitle.setText(eventList.get(eid).getTask());
            minPicker.setValue(eventList.get(eid).getTimeMinute()/5);
            secPicker.setValue(eventList.get(eid).getTimeSec());
        }
        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                min = i1*5;
            }
        });
        secPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                sec = i1;
            }
        });





        btn_evtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update){
                    eventList.get(eid).setTask(newEventTitle.getText().toString());
                    eventList.get(eid).setTimeMinute(min);
                    eventList.get(eid).setTimeSec(sec);
                    Log.i("db",newEventTitle.getText().toString()+" time in mins"+min);
                    //db.deleteTask(eid);
                    db.updateEvent(eid,newEventTitle.getText().toString(),min,sec);
                }else{
                    Model task = new Model();
                    task.setTask(newEventTitle.getText().toString());
                    Log.i("dbTest",""+min);
                    task.setTimeMinute(min);
                    task.setTimeSec(sec);
                    eventList.add(task);
                    db.insertEvent(task);
                }
                long milliseconds = (min*60000)+(sec*1000);
                if(Timer.isTimerRunning()){
                    Toast.makeText(activity,"Timer is still running",Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.setTimeMili(milliseconds);
                    String timeLeftFormatted = String.format("%02d:%02d", min, sec);
                    timer.setText(timeLeftFormatted);
                    Timer.setSoFar(0);
                    progress.setProgress(0);
                    bt_time.setText("Start Timer");
                }

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
        dlgTime = new AlertDialog.Builder(activity)
                .setView(dlgViewTime)
                .create();
        dlgTime.show();
        Log.i("Testing","Ok so were here at least");
    }


  //Permissions...will put somewhere else later ig
    private void requestOverlayDisplayPermission() {
        // An AlertDialog is created
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // This dialog can be closed, just by
        // taping outside the dialog-box
        builder.setCancelable(true);

        // The title of the Dialog-box is set
        builder.setTitle("Screen Overlay Permission Needed");

        // The message of the Dialog-box is set
        builder.setMessage("Enable 'Display over other apps' from System Settings.");

        // The event of the Positive-Button is set
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The app will redirect to the 'Display over other apps' in Settings.
                // This is an Implicit Intent. This is needed when any Action is needed
                // to perform, here it is
                // redirecting to an other app(Settings).
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));

                // This method will start the intent. It takes two parameter,
                // one is the Intent and the other is
                // an requestCode Integer. Here it is -1.
                startActivityForResult(intent, RESULT_OK);
            }
        });
        dialog = builder.create();
        // The Dialog will show in the screen
        dialog.show();
    }
    //check for permissions
    private boolean checkOverlayDisplayPermission() {
        // Android Version is lesser than Marshmallow
        // or the API is lesser than 23
        // doesn't need 'Display over other apps' permission enabling.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // If 'Display over other apps' is not enabled it
            // will return false or else true
            if (!Settings.canDrawOverlays(this)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
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

                if (checkOverlayDisplayPermission()) {

                    Log.i("TESTING____________________________________","DID I EVEN GET HERE?????");
                    // FloatingWindowGFG service is started
                    startService(new Intent(MainActivity.this, FloatingWindow.class));
                    // The MainActivity closes here
                    Log.i("TESTING____________________________________","DID I EVEN GET HERE?????");
                    finish();
                } else {
                    // If permission is not given,
                    // it shows the AlertDialog box and
                    // redirects to the Settings
                    requestOverlayDisplayPermission();
                }
                //startLockTask();
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

                createEvent(MainActivity.this, false,0);
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

