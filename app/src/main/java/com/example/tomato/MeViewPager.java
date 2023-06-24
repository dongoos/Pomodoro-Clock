package com.example.tomato;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
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
                Intent intent = new Intent(activity, ViewPagerInfo.class);
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

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_achievement


    }
}

