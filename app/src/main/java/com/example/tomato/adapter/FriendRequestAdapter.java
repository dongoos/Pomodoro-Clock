package com.example.tomato.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomato.FUser;
import com.example.tomato.R;
import com.example.tomato.friend.FriendInfo;
import com.example.tomato.tool.ServerHelper;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends ArrayAdapter<FriendInfo> {
    private ArrayList<FUser> friendList;
    private int resourceId;

    public FriendRequestAdapter(Context context, int resource, List<FriendInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
        friendList = new ArrayList<>();
        loadFriendRequestListData();
    }

    private void loadFriendRequestListData() {
        ServerHelper serverHelper = new ServerHelper();
        serverHelper.getFriendRequest().thenAccept(complete -> {
            if (complete != null) {
                friendList = (ArrayList<FUser>) complete;
                notifyDataSetChanged();
            } else {
                Log.i("showFriend", "失败");
            }
        });
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.ivFriend = convertView.findViewById(R.id.icon);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvEmail = convertView.findViewById(R.id.tv_email);
            holder.btnAdd=convertView.findViewById(R.id.btn_add);
            holder.btnReject=convertView.findViewById(R.id.btn_reject);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position >= 0 && position < friendList.size()) {
            FUser user = friendList.get(position);
            if (user != null) {
                FriendInfo friendInfo = getItem(position);
                holder.ivFriend.setImageResource(friendInfo.getIcon());
                holder.tvName.setText(user.getName());
                holder.tvEmail.setText(user.getEmail());
            }
        }

        return convertView;
    }

    static class ViewHolder {
        Button btnAdd,btnReject;
        ImageView ivFriend;
        TextView tvName;
        TextView tvEmail;
    }
}