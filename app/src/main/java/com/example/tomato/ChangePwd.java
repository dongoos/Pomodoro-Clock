package com.example.tomato;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.yalantis.phoenix.PullToRefreshView;

public class ChangePwd extends Activity {
    private EditText et_email;
    private static final int REFRESH_DELAY = 2000; // 延迟时间为2秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);
        PullToRefreshView mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里执行刷新操作
                        // 当刷新完成后，调用以下代码来停止刷新动画
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });



    }


}
