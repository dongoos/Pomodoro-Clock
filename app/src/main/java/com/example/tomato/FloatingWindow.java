package com.example.tomato;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tomato.util.DatabaseHandler;

public class FloatingWindow extends Service {

    // The reference variables for the
    // ViewGroup, WindowManager.LayoutParams,
    // WindowManager, Button, EditText classes are created
    private ViewGroup floatView;
    private AlertDialog dialog;
    private int LAYOUT_TYPE;
    private WindowManager.LayoutParams floatWindowLayoutParam;
    private WindowManager windowManager;
    Context context;
    Button stop;
    private static ProgressBar timeProgress;
    private static TextView timer;
    private TextView eventName;



    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private static boolean timerRunning;

    private long interval;
    private long ogTime;
    private static long soFar = 0;
    private long max =1000;

    private static boolean justOpened = true;
    MainActivity activity;
    private static DatabaseHandler db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void closeFloat(){
        stopSelf();
        // The window is removed from the screen
        windowManager.removeView(floatView);

        Intent backToHome = new Intent(FloatingWindow.this, MainActivity.class);

        backToHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.setTimeFinish(true);
        startActivity(backToHome);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        floatView = (ViewGroup) inflater.inflate(R.layout.floating_layout, null);

        //stop = floatView.findViewById(R.id.btnStop);
        timer = floatView.findViewById(R.id.timer);
        timeProgress =  floatView.findViewById(R.id.progressBar);
        eventName = floatView.findViewById(R.id.floatingName);

//        maximizeBtn = floatView.findViewById(R.id.buttonMaximize);
//        toAPP = floatView.findViewById(R.id.buttonApp2);

        if(justOpened){
            startTimer();
            justOpened = false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //sdk 23 above only
            LAYOUT_TYPE = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_TYPE = WindowManager.LayoutParams.TYPE_TOAST;
        }

        floatWindowLayoutParam = new WindowManager.LayoutParams(
                (int) (width ),
                (int) (height ),
                LAYOUT_TYPE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        windowManager.addView(floatView, floatWindowLayoutParam);
        eventName.setText(MainActivity.getEventName());



//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeFloat();
//            }
//        });

    }

    private void startTimer() {


        timeProgress.setMax((int)max);





        ogTime = MainActivity.getTimeMili();
        Log.i("Timing work","THe ogTime: "+ ogTime);
       soFar = ogTime;
        timeLeftInMillis = soFar; // 1 minute
        timerRunning = true;
        interval = ogTime/max;
        interval = (ogTime+interval)/max;


        countDownTimer = new CountDownTimer(timeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                int x = (int)max*2;
                justOpened=true;

                closeFloat();
            }
        }.start();


    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        int progress = (int) ((ogTime-timeLeftInMillis)/interval);

        timeProgress.setProgress(progress);
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted);
    }

    // It is called when stopService()
    // method is called in MainActivity
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        // Window is removed from the screen
        windowManager.removeView(floatView);
    }
}
