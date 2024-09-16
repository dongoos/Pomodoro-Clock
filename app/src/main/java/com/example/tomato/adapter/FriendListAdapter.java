package com.example.tomato.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomato.FUser;
import com.example.tomato.R;
import com.example.tomato.friend.FriendInfo;
import com.example.tomato.tool.ServerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FriendListAdapter extends ArrayAdapter<FriendInfo> {
    private ArrayList<FUser> friendList;
    private int resourceId;

    public FriendListAdapter(Context context, int resource, List<FriendInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
        friendList = new ArrayList<>();
        loadFriendListData();
    }

    private void loadFriendListData() {
        ServerHelper serverHelper = new ServerHelper();
        serverHelper.showFriendList().thenAccept(complete -> {
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
    private static String[] avatarList = {"avatar1", "avatar2", "avatar3"};
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.ivFriend = convertView.findViewById(R.id.icon);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvEmail = convertView.findViewById(R.id.tv_email);
            holder.tvScore=convertView.findViewById(R.id.tv_score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
int count=0;
        if (position >= 0 && position < friendList.size()) {
            FUser user = friendList.get(position);
            if (user != null) {


                Random r = new Random();
                int irand = r.nextInt(10);
                FriendInfo friendInfo = getItem(position);

                holder.ivFriend.setImageResource(R.drawable.avatar2);


                holder.tvName.setText(user.getName());
                holder.tvEmail.setText(user.getEmail());
               Log.i("scorrr", String.valueOf(holder.tvScore));
                holder.tvScore.setText(String.valueOf(irand+count));
                count=12;

            }
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView ivFriend;
        TextView tvName;
        TextView tvEmail,tvScore;
    }
}