package com.example.tomato.appUsage;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;


public class AppInformation {
    private UsageStats usageStats;
    private String packageName;
    private String label;
    private Drawable Icon;
    private long UsedTimebyDay;  //milliseconds
    private Context context;
    private int times;

    //  构造函数同时对异常信息进行处理，在控制台打印输出
    public AppInformation(UsageStats usageStats, Context context) {
        this.usageStats = usageStats;
        this.context = context;

        try {
            GenerateInfo();
        } catch (PackageManager.NameNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void GenerateInfo() throws PackageManager.NameNotFoundException, NoSuchFieldException, IllegalAccessException {
        //获取包名
        PackageManager packageManager = context.getPackageManager();
        this.packageName = usageStats.getPackageName();
        if (this.packageName != null && !this.packageName.equals("")) {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.packageName, 0);
            //获取应用信息
            this.label = (String) packageManager.getApplicationLabel(applicationInfo);
            //前台使用总时长
            this.UsedTimebyDay = usageStats.getTotalTimeInForeground();
            //使用次数
            this.times = (Integer) usageStats.getClass().getDeclaredField("mLaunchCount").get(usageStats);

            if (this.UsedTimebyDay > 0) {
                this.Icon = applicationInfo.loadIcon(packageManager);
            }
        }
    }

    public UsageStats getUsageStats() {
        return usageStats;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setUsedTimebyDay(long usedTimebyDay) {
        this.UsedTimebyDay = usedTimebyDay;
    }

    public Drawable getIcon() {
        return Icon;
    }

    public long getUsedTimebyDay() {
        return UsedTimebyDay;
    }

    public String getLabel() {
        return label;
    }

    public String getPackageName() {
        return packageName;
    }

    private long timeStampMoveToForeground = -1;

    private long timeStampMoveToBackGround = -1;


    public void setTimeStampMoveToForeground(long timeStampMoveToForeground) {
        this.timeStampMoveToForeground = timeStampMoveToForeground;
    }

    public void timesPlusPlus(){
        times++;
    }

    public void setTimeStampMoveToBackGround(long timeStampMoveToBackGround) {
        this.timeStampMoveToBackGround = timeStampMoveToBackGround;
    }

    public long getTimeStampMoveToBackGround() {
        return timeStampMoveToBackGround;
    }

    public long getTimeStampMoveToForeground() {
        return timeStampMoveToForeground;
    }
    //用来计算程序的运行时间，设计巧妙
    public void calculateRunningTime() {

        if (timeStampMoveToForeground < 0 || timeStampMoveToBackGround < 0) {
            return;
        }

        if (timeStampMoveToBackGround > timeStampMoveToForeground) {
            UsedTimebyDay += (timeStampMoveToBackGround - timeStampMoveToForeground);
            timeStampMoveToForeground = -1;
            timeStampMoveToBackGround = -1;
        }

    }


    // 返回开机时间，单位微妙
    public static long bootTime() {
        return System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }

}
