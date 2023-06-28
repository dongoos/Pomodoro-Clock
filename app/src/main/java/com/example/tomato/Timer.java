package com.example.tomato;


import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.os.CountDownTimer;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Timer extends Activity implements View.OnClickListener {

    //init variables
    private TextView timer;
    private Button btnStart;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private static boolean timerRunning;

    private Button btnInfo;

    private ProgressBar timeProgress;
    private long interval;
    private long ogTime;
    private static long soFar = 0;
    private long max =1000;

    private AlertDialog dlg;
    private View dlgView;
    Button submitEvt;


    public static void setSoFar(long sF) {
        soFar = sF;
    }

    public static boolean isTimerRunning() {
        return timerRunning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);


    }

    @Override
    public void onClick(View v) {

        btnStart = MainActivity.getBtnT();
        timer = MainActivity.getTimer();
        timeProgress = MainActivity.getPB();

        Log.i("test","Testing it button click works");

        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }



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
                timeProgress.setProgress(x);
                btnStart.setText("Start Timer");
            }
        }.start();

        btnStart.setText("Pause Timer");
    }

    private void stopTimer() {
        if(timeLeftInMillis != 0){
            soFar = timeLeftInMillis;
            btnStart.setText("Continue Timer");
        }else{
            soFar = 0;
            btnStart.setText("Start Timer");
        }
        countDownTimer.cancel();
        timerRunning = false;

    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        int progress = (int) ((ogTime-timeLeftInMillis)/interval);

        timeProgress.setProgress(progress);
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer.setText(timeLeftFormatted);
    }


}


