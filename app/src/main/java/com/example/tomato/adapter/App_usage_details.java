package com.example.tomato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomato.R;
import com.example.tomato.bean.App_info;

import java.util.List;

public class App_usage_details extends BaseAdapter {

    private Context mContext;

    private List<App_info> mAppinfoList;
    private View view;
    private View viewById;

    public App_usage_details(Context mContext, List<App_info> mAppinfoList) {
        this.mContext = mContext;
        this.mAppinfoList = mAppinfoList;
    }

    @Override
    public int getCount() {
        return mAppinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppinfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //根据布局文件item_list.xml生成转换视图对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_details = view.findViewById(R.id.tv_details);
        //给控件设置数据
        App_info app_info = mAppinfoList.get(position);
        iv_icon.setImageResource(app_info.image);
        tv_name.setText(app_info.name);
        tv_details.setText(app_info.details);
        return view;
    }
}
