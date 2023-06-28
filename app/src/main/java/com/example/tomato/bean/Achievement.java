package com.example.tomato.bean;

import com.example.tomato.R;

import java.util.ArrayList;
import java.util.List;

public class Achievement {
    public int image;
    public String name;
    public String details;

    public Achievement(int image, String name, String details)   {
        this.image = image;
        this.name = name;
        this.details = details;

    }
    //定义储存数据的数组
    private static int[] iconArray={R.drawable.achievement,R.drawable.achievement,R.drawable.achievement,R.drawable.achievement,R.drawable.achievement,R.drawable.achievement};
    private static String[] nameArray = {"初来乍到","牛刀小试","初窥门径","登堂入室","炉火纯青","心静如水"};
    private static String[] detailsArray = {"打开应用","打开应用","打开应用","打开应用","打开应用","打开应用"};

    //构建上述三个数组的集合，方便使用
    public static List<Achievement> getDefaultList()   {
        List<Achievement> achievementList = new ArrayList<Achievement>();
        for (int i = 0;i < iconArray.length;i++)    {
            achievementList.add(new Achievement(iconArray[i],nameArray[i],detailsArray[i] ));
        }
        return achievementList;
    }
}
