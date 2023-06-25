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
    private boolean timerRunning;

    private Button btnInfo;

    private ProgressBar timeProgress;
    private long interval;
    private long ogTime;
    private int soFar;

    private AlertDialog dlg;
    private View dlgView;
    Button submitEvt;

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


        if(v.getId() == btnStart.getId()){
            if (timerRunning) {
                stopTimer();
            } else {
                startTimer();
            }

        }else if(v.getId() == timer.getId()){




        }


    }



    private void startTimer() {

        ogTime = 60000;
        timeLeftInMillis = 60000; // 1 minute
        timerRunning = true;
        interval = ogTime/100;
        interval = (ogTime+interval)/100;


        countDownTimer = new CountDownTimer(timeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStart.setText("Start Timer");
            }
        }.start();

        btnStart.setText("Pause Timer");
    }

    private void stopTimer() {

        countDownTimer.cancel();
        timerRunning = false;
        btnStart.setText("Start Timer");
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


