package com.example.tomato;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
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
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FloatingWindow extends Service {

    // The reference variables for the
    // ViewGroup, WindowManager.LayoutParams,
    // WindowManager, Button, EditText classes are created
    private ViewGroup floatView;
    private int LAYOUT_TYPE;
    private WindowManager.LayoutParams floatWindowLayoutParam;
    private WindowManager windowManager;
    private Button maximizeBtn;

    // As FloatingWindowGFG inherits Service class,
    // it actually overrides the onBind method
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // The screen height and width are calculated, cause
        // the height and width of the floating window is set depending on this
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        floatView = (ViewGroup) inflater.inflate(R.layout.floating_layout, null);

        maximizeBtn = floatView.findViewById(R.id.buttonMaximize);
        Log.i("TESTING____________________________________","DID I EVEN GET HERE????? - sdk if statements");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("TESTING____________________________________","SO SDK IS GOOD?????");
            LAYOUT_TYPE = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            Log.i("TESTING____________________________________","I THOUGHT I PHONE WAS NEW...................");
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
                (int) (width * (0.55f)),
                (int) (height * (0.58f)),
                LAYOUT_TYPE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        // The Gravity of the Floating Window is set.
        // The Window will appear in the center of the screen
        floatWindowLayoutParam.gravity = Gravity.CENTER;

        // X and Y value of the window is set
        floatWindowLayoutParam.x = 0;
        floatWindowLayoutParam.y = 0;

        // The ViewGroup that inflates the floating_layout.xml is
        // added to the WindowManager with all the parameters
        windowManager.addView(floatView, floatWindowLayoutParam);

        // The button that helps to maximize the app
        maximizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // stopSelf() method is used to stop the service if
                // it was previously started
                stopSelf();

                // The window is removed from the screen
                windowManager.removeView(floatView);

                // The app will maximize again. So the MainActivity
                // class will be called again.
                Intent backToHome = new Intent(FloatingWindow.this, MainActivity.class);

                // 1) FLAG_ACTIVITY_NEW_TASK flag helps activity to start a new task on the history stack.
                // If a task is already running like the floating window service, a new activity will not be started.
                // Instead the task will be brought back to the front just like the MainActivity here
                // 2) FLAG_ACTIVITY_CLEAR_TASK can be used in the conjunction with FLAG_ACTIVITY_NEW_TASK. This flag will
                // kill the existing task first and then new activity is started.
                backToHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backToHome);
            }
        });

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
