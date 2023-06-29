package com.example.tomato.tool;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.tomato.R;

public class AppCheckService extends Service {
    private Handler handler;
    private BroadcastReceiver appExitedReceiver;
    private WindowManager windowManager;
    private View floatingView;
    private String packageName;
    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        appExitedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(packageName+".EXITED")) {
                    // 处理应用退出的逻辑
                    // 启动悬浮窗口
                    showFloatingWindow();
                }
            }
        };

        // 设置悬浮窗口
//        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_window_layout, null);
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_layout,null);

        // 配置窗口参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0; // 设置窗口的初始X位置
        params.y = 0; // 设置窗口的初始Y位置

        // 将悬浮窗口添加到窗口管理器中
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 注册appExitedReceiver以监听应用退出事件
        IntentFilter intentFilter = new IntentFilter(packageName+".EXITED");
        registerReceiver(appExitedReceiver, intentFilter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appExitedReceiver);

        // 从窗口管理器中移除悬浮窗口
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//   public IBinder onBind(Intent intent) {
//        return null;
//    }

    // 显示悬浮窗口
    private void showFloatingWindow() {
        // 在此处理显示悬浮窗口的逻辑
    }
}
