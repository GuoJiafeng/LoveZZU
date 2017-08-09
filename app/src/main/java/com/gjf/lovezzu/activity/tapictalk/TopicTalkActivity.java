package com.gjf.lovezzu.activity.tapictalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gjf.lovezzu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicTalkActivity extends AppCompatActivity {


    public static TopicTalkActivity topicTalkActivity;
    @BindView(R.id.topic_back)
    ImageView topicBack;
    @BindView(R.id.topic_first_add)
    ImageView topicFirstAdd;
    @BindView(R.id.topic_first_recycler)
    RecyclerView topicFirstRecycler;
    @BindView(R.id.topic_child_recycler)
    RecyclerView topicChildRecycler;
    @BindView(R.id.topic_activity_refresh)
    SwipeRefreshLayout topicActivityRefresh;
    @BindView(R.id.topic_child_add)
    ImageView topicChildAdd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicTalkActivity = this;
        setContentView(R.layout.topic_activity);
        ButterKnife.bind(this);


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


    @OnClick({R.id.topic_back, R.id.topic_first_add, R.id.topic_child_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topic_back:
                break;
            case R.id.topic_first_add:
                Intent intent=new Intent(this,TopicPublishActivity.class);
                intent.putExtra("type","一级话题");
                startActivity(intent);
                break;
            case R.id.topic_child_add:
                break;
        }
    }
}
