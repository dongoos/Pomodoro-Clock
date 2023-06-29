package com.example.tomato.tool;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class AppCheckService extends Service {
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent("com.example.app.EXITED");
                sendBroadcast(intent);

                // 重复执行定时任务
                handler.postDelayed(this, 1000); // 1秒钟执行一次，可根据需要调整时间间隔
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 启动定时任务
        handler.postDelayed(runnable, 1000); // 1秒钟后执行任务，可根据需要调整延迟时间
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止定时任务
        handler.removeCallbacks(runnable);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    private BroadcastReceiver appExitedReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals("com.example.app.EXIT")||intent.getAction().equals(com.example.app.BACKGROUND)) {
//                // 将悬浮窗口置顶的操作
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // 注册广播接收器
//        IntentFilter intentFilter = new IntentFilter();
//     intentFilter.addAction("com.example.app.BACKGROUND");
//    intentFilter.addAction("com.example.app.EXIT");
//        intentFilter.addAction("com.example.app.EXITED");
//        registerReceiver(appExitedReceiver, intentFilter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 注销广播接收器
//        unregisterReceiver(appExitedReceiver);
//    }
}