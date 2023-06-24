package com.example.tomato.appUsage;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.provider.Settings;

import com.example.tomato.appUsage.AppUsageInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppUsageStatistics {
    private Context context;

    public AppUsageStatistics(Context context) {
        this.context = context;
    }
    /**
     * 获取所有应用程序的使用时长统计信息
     *
     * @return 应用程序使用时长统计信息列表
     */
    public List<AppUsageInfo> getAllAppUsageStatistics() {
        List<AppUsageInfo> appUsageInfoList = new ArrayList<>();

        // 检查是否具有获取应用使用情况的权限
        if (hasUsageAccessPermission()) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            // 获取统计的时间范围
            Calendar calendar = Calendar.getInstance();
            long endTime = calendar.getTimeInMillis();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            long startTime = calendar.getTimeInMillis();

            // 查询应用程序使用统计信息
            List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

            // 遍历查询结果，将统计信息存储在AppUsageInfo对象中
            if (usageStatsList != null) {
                for (UsageStats usageStats : usageStatsList) {
                    AppUsageInfo appUsageInfo = new AppUsageInfo();
                    appUsageInfo.setPackageName(usageStats.getPackageName());
                    appUsageInfo.setTotalTimeInForeground(usageStats.getTotalTimeInForeground());
                    appUsageInfoList.add(appUsageInfo);
                }
            }
        } else {
            // 没有权限，跳转到权限设置页面
            setPermission();
        }

        return appUsageInfoList;
    }

    /**
     * 检查是否具有获取应用使用情况的权限
     *
     * @return true表示具有权限，false表示没有权限
     */
    private boolean hasUsageAccessPermission() {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    /**
     * 跳转到权限设置页面
     */
    public void setPermission() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        context.startActivity(intent);
    }
}
