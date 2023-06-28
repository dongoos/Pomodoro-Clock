package com.example.tomato;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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



    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private static boolean timerRunning;

    private long interval;
    private long ogTime;
    private static long soFar = 0;
    private long max =1000;

    private static boolean justOpened = true;

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

        // Now the Parameter of the floating-window layout is set.
        // 1) The Width of the window will be 55% of the phone width.
        // 2) The Height of the window will be 58% of the phone height.
        // 3) Layout_Type is already set.
        // 4) Next Parameter is Window_Flag. Here FLAG_NOT_FOCUSABLE is used. But
        // problem with this flag is key inputs can't be given to the EditText.
        // This problem is solved later.
        // 5) Next parameter is Layout_Format. System chooses a format that supports
        // translucency by PixelFormat.TRANSLUCENT
        floatWindowLayoutParam = new WindowManager.LayoutParams(
                (int) (width ),
                (int) (height ),
                LAYOUT_TYPE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );


        // The ViewGroup that inflates the floating_layout.xml is
        // added to the WindowManager with all the parameters
        windowManager.addView(floatView, floatWindowLayoutParam);


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
        if(soFar == 0){
            soFar = ogTime;
        }

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
                justOpened=false;
                MainActivity.setTimeMili(0);

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
