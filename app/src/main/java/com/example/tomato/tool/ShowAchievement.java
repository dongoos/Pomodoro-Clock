package com.example.tomato.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tomato.R;
import com.example.tomato.databinding.AchievementPopupBinding;

public class ShowAchievement {

    public static void show(Context context) {
        Log.d("show","started");
        // 创建弹出窗口
        PopupWindow achievementPopup = new PopupWindow(context);

        // 加载布局文件
        View popupView = LayoutInflater.from(context).inflate(R.layout.achievement_popup, null);

        // 找到布局中的控件
        ImageView iconImageView = popupView.findViewById(R.id.achievement_icon);
        TextView titleTextView = popupView.findViewById(R.id.achievement_title);
        TextView descriptionTextView = popupView.findViewById(R.id.achievement_description);

        // 设置成就信息
        String[] achievementTitles=new String[100];
        String[] achievementDescriptions=new String[100];
        int[] achievementIcons=new int[100];
        achievementTitles[0]="出生番茄";
        achievementDescriptions[0]="出生了！";
        achievementIcons[0] = R.drawable.good;


        int position = 0; // 要显示的成就在列表中的位置
        String title = achievementTitles[position];
        String description = achievementDescriptions[position];
        int iconResId = achievementIcons[position];

        iconImageView.setImageResource(iconResId);
        titleTextView.setText(title);
        descriptionTextView.setText(description);

        // 设置弹出窗口的宽度和高度
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        achievementPopup.setWidth(width);
        achievementPopup.setHeight(height);

        // 设置弹出窗口的内容视图
        achievementPopup.setContentView(popupView);

        // 设置弹出窗口的背景（这里使用系统默认的背景）
        achievementPopup.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置弹出窗口可点击和获取焦点（这样可以让用户点击其他区域关闭弹出窗口）
        achievementPopup.setTouchable(true);
        achievementPopup.setFocusable(true);

        // 设置弹出窗口的动画效果
        achievementPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        // 显示弹出窗口在屏幕中心
        View parentView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        achievementPopup.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }
}
