package com.example.tomato;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tomato.dialogFragment.FriendDialog;
import com.example.tomato.tool.ShowAchievement;

public class MeViewPager {
    private static Button btn_info, btn_friend, btn_achievement, btn_setting;
    private static ImageButton ibtn_setting;

    public static void init(MainActivity activity) {
<<<<<<< HEAD
        btn_info = activity.getBtnInfo();
        btn_friend = activity.getBtnFriend();
        btn_achievement = activity.getBtnAchievement();
        btn_setting = activity.getBtnSetting();
        ibtn_setting = activity.getiBtnSetting();
=======
        View rootView;
        rootView = MainActivity.getView2();
        btn_info=rootView.findViewById(R.id.infoButton);
        btn_friend=rootView.findViewById(R.id.btn_friend);
        btn_achievement=rootView.findViewById(R.id.btn_achievements);
        btn_feedback=rootView.findViewById(R.id.btn_feedback);
        btn_setting=rootView.findViewById(R.id.btn_setting);

>>>>>>> d3c63e3e806cd946ba63d520e8eaa500193c9e1c
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
                ShowAchievement.show(activity);
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
