package com.example.tomato;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomato.dialogFragment.FriendDialog;


import java.io.File;

public class MeViewPager {
    private static Button btn_info, btn_friend, btn_achievement, btn_setting,btn_changeAvatar;
    private static ImageView ibtn_setting;
    private static TextView tv_name,tv_email,tv_score;

    public static void init(MainActivity activity) {
        View rootView;
        rootView = MainActivity.getView2();
        btn_info=rootView.findViewById(R.id.infoButton);
        btn_friend=rootView.findViewById(R.id.btn_friend);
        btn_achievement=rootView.findViewById(R.id.btn_achievements);
        ibtn_setting=rootView.findViewById(R.id.ib_setting);
        btn_setting=rootView.findViewById(R.id.btn_setting);
        tv_name=rootView.findViewById(R.id.user_name);
        tv_email=rootView.findViewById(R.id.user_email);
        tv_score=rootView.findViewById(R.id.user_score);
        tv_name.setText("用户");
        tv_email.setText(User.getEmail());
        tv_score.setText("");
        btn_changeAvatar=rootView.findViewById(R.id.btn_changeAvatar);

        MainActivity.iv_avatar=rootView.findViewById(R.id.imageView);
        btn_info.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);

        });

        btn_friend.setOnClickListener(view -> {
            FriendDialog friendDialog = new FriendDialog();
            friendDialog.show(activity.getSupportFragmentManager(), "Friend_dialog");

        });

        btn_achievement.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AchievementActivity.class);
            activity.startActivity(intent);
        });
        btn_setting.setOnClickListener(view -> {
            Intent intent = new Intent(activity,SettingActivity.class);
            activity.startActivity(intent);
        });
        ibtn_setting.setOnClickListener(view -> {
            Intent intent = new Intent(activity,SettingActivity.class);
            activity.startActivity(intent);
        });
        btn_changeAvatar.setOnClickListener(view -> {

        });
    }



}

