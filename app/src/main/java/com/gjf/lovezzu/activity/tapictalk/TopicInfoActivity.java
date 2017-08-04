package com.gjf.lovezzu.activity.tapictalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.gjf.lovezzu.R;

/**
 * Created by lenovo047 on 2017/5/27.
 */

public class TopicInfoActivity extends AppCompatActivity {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_info_activity);


    }


    //刷新操作
    private void onRefresh() {

    }

    //刷新数据
    private void refreshTopic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据并更新

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }
    private void initItem() {



    }


}