package com.example.tomato.dialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.tomato.R;
import com.example.tomato.User;
import com.example.tomato.adapter.FriendListAdapter;
import com.example.tomato.friend.FriendInfo;
import com.example.tomato.tool.ServerHelper;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class FriendDialog extends DialogFragment {
    private List<FriendInfo> list_friend = new ArrayList<FriendInfo>();
    private int[] images = {R.drawable.good, R.drawable.good, R.drawable.good, R.drawable.good, R.drawable.good};
    private String[] names = {"1", "1", "1", "1", "1"};
    private String[] emails = {"1", "1", "1", "1", "1"};
    private int[] scores = {6, 4, 5, 3, 4};
    private FriendListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend, container, false);
        ListView lv_friend = view.findViewById(R.id.friendListView);
        ImageButton btn_addFriend = view.findViewById(R.id.addFriendButton);
        ImageButton btn_findFriend = view.findViewById(R.id.findFriendButton);

        adapter = new FriendListAdapter(getActivity(), R.layout.frienditem, list_friend);
        lv_friend.setAdapter(adapter);

        btn_addFriend.setOnClickListener(view1 -> {
            dismiss();
            FriendRequestDialog friendRequestDialog = new FriendRequestDialog();
            friendRequestDialog.show(requireActivity().getSupportFragmentManager(), "FriendRequest_dialog");
        });

        btn_findFriend.setOnClickListener(view12 -> {
            ServerHelper serverHelper = new ServerHelper();
            Log.i("RequestSend-uid", String.valueOf(User.getUid()));
        });

        initFriends(); // 初始化默认好友列表
        return view;
    }

    private void initFriends() {
        for (int i = 0; i < names.length; i++) {
            FriendInfo friendInfo = new FriendInfo(images[i], names[i], emails[i], scores[i]);
            list_friend.add(friendInfo);
        }

        // 数据加载完成后通知适配器更新
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        // 设置弹窗的宽度和高度
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        layoutParams.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.66);
        // 设置弹窗的位置
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().getWindow().setAttributes(layoutParams);
    }

}
