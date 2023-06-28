package com.example.tomato.appUsage;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.databinding.library.BuildConfig;

import com.example.tomato.BaseApplication;
import com.example.tomato.MainActivity;
import com.example.tomato.util.JDateKit;
import com.example.tomato.util.JListKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LoadAppUsageTask  {
    private static final String TAG = "LoadAppUsageTask";
    private Callback mCallback;
    private long beginTime, endTime;
    private PackageManager mPackageManager;
    private  MainActivity activity;
private  Context   context;
    public LoadAppUsageTask(long beginTime, long endTime, PackageManager packageManager, Context context) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        Log.i("caseapp","do");
        this.context=context;
        mPackageManager = packageManager;
    }


    public ArrayList<AppUsageBean> readAppUsageList() {
        ArrayList<AppUsageBean> mItems = JListKit.newArrayList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsManager usage = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (usage == null) return mItems;

            // 查询并按包名进行聚合操作
            Map<String, UsageStats> statsMap = usage.queryAndAggregateUsageStats(beginTime, endTime);
            Set<String> keySet = statsMap.keySet();
            for (String packageName : keySet) {
                UsageStats usageStats = statsMap.get(packageName);
                if (usageStats == null) continue;
                long totalTimeInForeground = usageStats.getTotalTimeInForeground();
                if (totalTimeInForeground <= 0) continue;// 小于1秒的都按照没有打开过处理

                AppUsageBean appUsageBean = new AppUsageBean(packageName, usageStats);
                ApplicationInfo info = getAppInfo(packageName);
                appUsageBean.setAppInfo(info);
                if (info != null) {
                   //  获取应用名称
                    String label = (String) info.loadLabel(mPackageManager);
                    Drawable icon = info.loadIcon(mPackageManager);
                    appUsageBean.setAppName(label);
                    appUsageBean.setAppIcon(icon);
                    if(icon!=null)
                    mItems.add(appUsageBean);

                } else {
                    appUsageBean.setAppName("应用已卸载");
                     Log.e(TAG, "已经找不到包名为[" + packageName + "]的应用");
                }

                // 打印日志

                    Log.d("UsageStats", "**********************************************");
                    Log.d("UsageStats", packageName);
                    // Log.d("UsageStats", "运行时长:" + JDateKit.timeToStringChineChinese(totalTimeInForeground));
                    Log.d("UsageStats", String.format("运行时长:%s (%sms)", JDateKit.timeToStringChineChinese(totalTimeInForeground), totalTimeInForeground));
                    String fmt = "yyyy-MM-dd HH:mm:ss.SSS";
                    Log.d("UsageStats", "开始启动:" + JDateKit.timeToDate(fmt, usageStats.getFirstTimeStamp()));
                    Log.d("UsageStats", "最后启动:" + JDateKit.timeToDate(fmt, usageStats.getLastTimeStamp()));
                    Log.d("UsageStats", "最近使用:" + JDateKit.timeToDate(fmt, usageStats.getLastTimeUsed()));

            }
        }


        return mItems;
    }


    private ApplicationInfo getAppInfo(String pkgName) {
        try {
            // ApplicationInfo info = mPackageManager.getApplicationInfo(pkgName, PackageManager.GET_ACTIVITIES);
            return mPackageManager.getApplicationInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
            Log.e(TAG, "已经找不到包名为[" + pkgName + "]的应用");
        }
        return null;
    }

    //onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    public void onPostExecute(List<AppUsageBean> mItems) {
        Log.d(TAG, "共获取到[" + readAppUsageList().size() + "]个系统应用。");

            mItems= readAppUsageList();
    }

    public interface Callback {
        void onPostExecute(ArrayList<AppUsageBean> list);
    }
}
