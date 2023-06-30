package com.example.tomato;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tomato.dialogFragment.FriendDialog;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MeViewPager {
    private static Button btn_info, btn_friend, btn_achievement, btn_setting,btn_changeAvatar;
    private static ImageView ibtn_setting,avatar;
    private static TextView tv_name,tv_email,tv_score;
   private static Context context;

    public static void init(MainActivity activity) {
        View rootView;
        rootView = MainActivity.getView2();
        btn_info=rootView.findViewById(R.id.infoButton);
        btn_friend=rootView.findViewById(R.id.btn_friend);
        btn_achievement=rootView.findViewById(R.id.btn_achievements);
        ibtn_setting=rootView.findViewById(R.id.ib_setting);
        btn_setting=rootView.findViewById(R.id.btn_setting);
        avatar=rootView.findViewById(R.id.imageView);
        tv_name=rootView.findViewById(R.id.user_name);
        tv_email=rootView.findViewById(R.id.user_email);
        tv_score=rootView.findViewById(R.id.user_score);
        tv_name.setText("用户");
        tv_email.setText(User.getEmail());
        tv_score.setText("");
        btn_changeAvatar=rootView.findViewById(R.id.btn_changeAvatar);
        context =activity;
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
            int[] scoreThresholds = {0, 50, 100, 150};//分数阈值
             String[] avatarList = {"avatar1", "avatar2", "avatar3"}; // 头像图片的文件名列表
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("选择头像");

                // 根据分数确定可供选择的头像
                int availableAvatars = 0;
                final List<String> availableAvatarList = new ArrayList<>();

                for (int i = 0; i < avatarList.length; i++) {
                    if (User.getScore() >= scoreThresholds[i]) {
                        availableAvatarList.add(avatarList[i]);
                        availableAvatars++;
                    }
                }

                if (availableAvatars > 0) {
                    builder.setItems(availableAvatarList.toArray(new String[0]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedAvatar = availableAvatarList.get(which);
                            // 在这里处理选择头像后的逻辑
                            Toast.makeText(context, "您选择了：" + selectedAvatar, Toast.LENGTH_SHORT).show();
                            avatar.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(selectedAvatar, "drawable", context.getPackageName())));
                        }
                    });
                } else {
                    builder.setMessage("您的分数不足，无法选择头像！");
                }

                builder.show();

        });
    }



}

