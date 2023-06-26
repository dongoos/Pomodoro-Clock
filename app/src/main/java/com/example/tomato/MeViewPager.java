package com.example.tomato;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tomato.tool.ShowAchievement;

public class MeViewPager {
    private static Button btn_info, btn_friend, btn_achievement, btn_feedback, btn_setting;

    public static void init(MainActivity activity) {
        btn_info = activity.getBtnInfo();
        btn_friend = activity.getBtnFriend();
        btn_achievement = activity.getBtnAchievement();
        btn_feedback = activity.getBtnFeedback();
        btn_setting = activity.getBtnSetting();

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ifo","start");
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        });

        btn_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ViewPagerInfo.class);
                activity.startActivity(intent);
            }
        });

        btn_achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAchievement.show(activity);
            }
        });
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent intent =new Intent(activity,RecordPageInfo.class);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
