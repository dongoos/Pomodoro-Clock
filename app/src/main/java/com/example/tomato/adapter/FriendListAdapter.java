package com.example.tomato.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomato.R;
import com.example.tomato.firend.FriendInfo;

import java.util.List;


public class FriendListAdapter extends ArrayAdapter<FriendInfo> {

    private int resourceId;
    public FriendListAdapter( Context context, int resource,  List<FriendInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        FriendInfo friendInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView ivFriend = (ImageView) view.findViewById(R.id.icon);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_email = (TextView) view.findViewById(R.id.tv_email);
        TextView tv_score = (TextView) view.findViewById(R.id.tv_score);
        ivFriend.setImageResource(friendInfo.getIcon());
        tv_name.setText(friendInfo.getName());
        tv_email.setText(friendInfo.getName());
        tv_score.setText(friendInfo.getName());
        return view;
    }
}
