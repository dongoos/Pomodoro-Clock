package com.example.tomato;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tomato.dialogFragment.FriendDialog;

public class MeViewPager {
    private static Button btn_info, btn_friend, btn_achievement, btn_setting;
    private static ImageView ibtn_setting;

    public static void init(MainActivity activity) {

        View rootView;
        rootView = MainActivity.getView2();
        btn_info=rootView.findViewById(R.id.infoButton);
        btn_friend=rootView.findViewById(R.id.btn_friend);
        btn_achievement=rootView.findViewById(R.id.btn_achievements);
        ibtn_setting=rootView.findViewById(R.id.ib_setting);
        btn_setting=rootView.findViewById(R.id.btn_setting);

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
                FriendDialog friendDialog = new FriendDialog();
                friendDialog.show(activity.getSupportFragmentManager(), "Friend_dialog");

            }
        });

        btn_achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AchievementActivity.class);
                activity.startActivity(intent);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,SettingActivity.class);
                activity.startActivity(intent);
            }
        });
        ibtn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,SettingActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
