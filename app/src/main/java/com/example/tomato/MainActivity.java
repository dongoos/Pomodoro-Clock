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
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tomato.adapter.EventListAdapter;
import com.example.tomato.model.Model;
import com.example.tomato.tool.AppCheckService;
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

   ItemTouchHelper itemTouchHelper;

    private static boolean timeFinish = false;


    private static ImageButton ibtn_setting;
    private EventListAdapter adapter;

    SwipeDelete swipeDelete;



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

    private static int eid;
    private static String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化数据

        Intent serviceIntent = new Intent(this, AppCheckService.class);
        startService(serviceIntent);



        //对单选按钮进行监听，选中、未选中
        // lock device properties init
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

    public static String getEventName() {
        return eventName;
    }

    public static void setEventName(String eventName) {
        MainActivity.eventName = eventName;
    }

    public static int getEid() {
        return eid;
    }

    public static void setEid(int eid) {
        MainActivity.eid = eid;
    }

    public static TextView getTimer(){
        return timer;
    }
    public static ProgressBar getPB(){
        return progress;
    }

    public static void setTimeFinish(boolean timeFinish) {
        MainActivity.timeFinish = timeFinish;
    }

    public static void setTimeMili(long timeMili) {
        setTime = timeMili;
    }
    public static long getTimeMili(){
        return setTime;
    }


    public static void congrats(MainActivity activity){
        View dlgViewTime = LayoutInflater.from(activity).inflate(R.layout.dialog_congrats, null);

        Button finish = dlgViewTime.findViewById(R.id.finish);
        TextView minTotal = dlgViewTime.findViewById(R.id.minNum);
        TextView potionNum = dlgViewTime.findViewById(R.id.potionNum);
        AchievementActivity achievementActivity = new AchievementActivity();
        db = new DatabaseHandler(activity);
        db.openDatabase();
        //db.getStats(true);

        minTotal.setText(""+db.getStats(false)+" total minutes");
        potionNum.setText(""+db.getStats(true)+" potions");
        achievementActivity.getAchievement();
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgTime.dismiss();
            }
        });
        dlgTime = new AlertDialog.Builder(activity)
                .setView(dlgViewTime)
                .create();
        dlgTime.show();

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
                    db.updateEvent(eid+1,newEventTitle.getText().toString(),min,sec);
                }else{
                    Model task = new Model();
                    task.setTask(newEventTitle.getText().toString());
                    Log.i("dbTest",""+min);
                    task.setTimeMinute(min);
                    task.setTimeSec(sec);
                    task.setId(eventList.size());
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
                Log.i("dbTest","This is the local arraylist"+eventList.toString());
//                Log.i("dbTest","This is the database"+db.getAllEvents().toString());

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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.RIGHT){
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Delete Event");
                builder.setMessage("Are you sure you want to delete this event?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(context,"The positions is"+ position,Toast.LENGTH_SHORT).show();
                        db.deleteTask(position+1);
                        eventList.remove(position);
                        elAdapter.setEvent(eventList);
                        //adapter.deleteItem(position);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                     elAdapter.setEvent(eventList);
                    }
                });
                androidx.appcompat.app.AlertDialog dlg = builder.create();
                dlg.show();
            }else{
                //
            }
        }
    };




    private void initView() {
        //初始化控件
        mViewPager=findViewById(R.id.viewpager);
        mRadioGroup=findViewById(R.id.rg_tab);
        tab1=findViewById(R.id.rb_lock);
        tab2=findViewById(R.id.rb_record);
        tab3=findViewById(R.id.rb_me);
        //设置drawableTop时对图片进行比例缩放
        RadioButton[] rb = new RadioButton[3];
        rb[0] = tab1;//radiobutton对应id
        rb[1] = tab2;
        rb[2] = tab3;
        for(RadioButton r:rb){
            Drawable[] drawables = r.getCompoundDrawables();
            Rect rect = new Rect(0,0,drawables[1].getMinimumWidth()/6,drawables[1].getMinimumHeight()/6);
            drawables[1].setBounds(rect);
            r.setCompoundDrawables(null , drawables[1] , null ,null);
        }

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
        elAdapter = new EventListAdapter(db, this);
        eventRecyclerView.setAdapter(elAdapter);
        db = new DatabaseHandler(this);
        db.openDatabase();
       eventList = db.getAllEvents();
        elAdapter.setEvent(eventList);
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(eventRecyclerView);

        if(timeFinish){
            congrats(MainActivity.this);
            timeFinish = false;
        }

        //ButtonListener



        btn_wl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Toast.makeText(context,"APOLOGIES THIS IS STILL IN DEVELOPMENT \nTHANK YOU FOR USING OUR APP", Toast.LENGTH_SHORT).show();
                //startLockTask();
            }
        });


        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent(MainActivity.this, false,0);

            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createEvent(MainActivity.this, false,0);
            }

        });
        bt_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(getTimeMili()==0){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Warning: NO TIME SET")
                            .setMessage("Please set a time time before starting")
                            .setPositiveButton(android.R.string.yes, null).setNegativeButton(android.R.string.no,null)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                }else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("BEGIN TIMER")
                            .setMessage("Once you begin the timer you will not be able to exit. \nAre you sure you want to begin the timer?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (checkOverlayDisplayPermission()) {

                                        //
                                    db = new DatabaseHandler(MainActivity.this);
                                    db.openDatabase();
                                    Model temp = db.getAllEvents().get(MainActivity.getEid());
                                    db.insertStats(temp);

                                        startService(new Intent(MainActivity.this, FloatingWindow.class));

                                        finish();
                                    } else {

                                        requestOverlayDisplayPermission();
                                    }
                                }
                            }).setNegativeButton(android.R.string.no,null)
                            .setIcon(android.R.drawable.ic_dialog_alert).show();


                }
//


            }
        });
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

