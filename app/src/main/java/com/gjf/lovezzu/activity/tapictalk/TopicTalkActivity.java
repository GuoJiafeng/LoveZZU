package com.gjf.lovezzu.activity.tapictalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gjf.lovezzu.R;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicTalkActivity extends AppCompatActivity {



public  static TopicTalkActivity topicTalkActivity;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicTalkActivity=this;
        setContentView(R.layout.topic_activity);


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

    //初始化加载数据
    private void initItem() {




    }

    //显示一级话题圈
    private void showMid() {

    }
    //显示二级话题圈
    private void showLast() {

    }


}
