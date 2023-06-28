package com.example.tomato;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
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

public class Timer extends Activity {

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
    private AlertDialog dialog;

    private View dlgView;
    Button submitEvt;
    MainActivity activity;


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

    //request permissions
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

//    @Override
    public void test(View v) {

        btnStart = MainActivity.getBtnT();
//        timer =MainActivity.getTimer();
//        timeProgress = MainActivity.getPB();
//       timer = FloatingWindow.getTimer();
//      timeProgress =FloatingWindow.getProgressBar();

//        if (checkOverlayDisplayPermission()) {
//
//            Log.i("TESTING____________________________________","DID I EVEN GET HERE?????");
//            // FloatingWindowGFG service is started
//            startService(new Intent(Timer.this, FloatingWindow.class));
//            // The MainActivity closes here
//            Log.i("TESTING____________________________________","DID I EVEN GET HERE?????");
//            finish();
//        } else {
//            // If permission is not given,
//            // it shows the AlertDialog box and
//            // redirects to the Settings
//            requestOverlayDisplayPermission();
//        }

//        if (timerRunning) {
//            stopTimer();
//        } else {
//            startTimer();
//        }



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


