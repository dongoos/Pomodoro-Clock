package com.example.tomato;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tomato.adapter.AchievementAdapter;
import com.example.tomato.bean.Achievement;

import java.util.List;

public class AchievementActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private GridView gv_achievement;
    private List<Achievement>   achievementList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        gv_achievement = findViewById(R.id.gv_achievement);
        achievementList = Achievement.getDefaultList();
        AchievementAdapter adapter = new AchievementAdapter(this,achievementList);
        gv_achievement.setAdapter(adapter);
        gv_achievement.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}