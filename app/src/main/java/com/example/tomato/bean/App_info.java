package com.example.tomato.bean;

import com.example.tomato.R;

import java.util.ArrayList;
import java.util.List;

public class App_info {
    public int image; //icon
    public String name; //name
    public String details;//detail

    public App_info(int image,String name,String details)   {
        this.image = image;
        this.name = name;
        this.details = details;

    }
    //定义储存数据的数组
    private static int[] iconArray={R.drawable.good,R.drawable.right};
    private static String[] nameArray = {"qq","微信"};
    private static String[] detailsArray = {"今日使用时长：4h","今日使用时长：6h"};

    //构建上述三个数组的集合，方便使用
    public static List<App_info> getDefaultList()   {
        List<App_info> appInfoList = new ArrayList<App_info>();
        for (int i = 0;i < iconArray.length;i++)    {
            appInfoList.add(new App_info(iconArray[i],nameArray[i],detailsArray[i] ));
        }
        return appInfoList;
    }
}
