package com.gjf.lovezzu.activity.tapictalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.TopicLast;
import com.gjf.lovezzu.entity.TopicMid;
import com.gjf.lovezzu.view.TopicLastAdapter;
import com.gjf.lovezzu.view.TopicMidAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicTalkActivity extends AppCompatActivity {

    private List<TopicMid> topicMidList = new ArrayList<>();
    private List<TopicLast> topicLastList = new ArrayList<>();

public  static TopicTalkActivity topicTalkActivity;
    private RecyclerView midRecyclerView;
    private RecyclerView lastRecyerView;
    private ImageView backImage;
    private SwipeRefreshLayout topicRegresh;
    private TopicMidAdapter topicMidAdapter;
    private TopicLastAdapter topicLastAdapter;

    private ImageView  Topicpub;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicTalkActivity=this;
        setContentView(R.layout.talk_view);
        Topicpub= (ImageView) findViewById(R.id.Topicpub);
        Topicpub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TopicTalkActivity.this,Topic_pub.class);
                startActivity(intent);
            }
        });
        backImage = (ImageView) findViewById(R.id.back);


        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicTalkActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initItem();
        showMid();
        showLast();
        onRefresh();
    }

    //刷新操作
    private void onRefresh() {
        topicRegresh = (SwipeRefreshLayout) findViewById(R.id.talk_refresh);
        topicRegresh.setColorSchemeResources(R.color.colorPrimary);
        topicRegresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTopic();
            }
        });
    }

    //刷新数据
    private void refreshTopic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据并更新
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initItem();
                        topicMidAdapter.notifyDataSetChanged();
                        topicLastAdapter.notifyDataSetChanged();
                        topicRegresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //初始化加载数据
    private void initItem() {

        TopicMid topicMid1 = new TopicMid("新闻标题", R.drawable.life_beautiful_girl, "从服务器获得链接");

        TopicLast topicLast = new TopicLast("测试测试", "中国队赢了，里皮是真的皮", "1001", "1996");
        topicMidList.clear();
        topicLastList.clear();

        topicMidList.add(topicMid1);
        topicMidList.add(topicMid1);
        topicMidList.add(topicMid1);
        topicMidList.add(topicMid1);
        topicMidList.add(topicMid1);


        topicLastList.add(topicLast);
        topicLastList.add(topicLast);
        topicLastList.add(topicLast);
        topicLastList.add(topicLast);
        topicLastList.add(topicLast);


    }

    //显示中部
    private void showMid() {
        midRecyclerView = (RecyclerView) findViewById(R.id.talk_mid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        midRecyclerView.setLayoutManager(layoutManager);
        topicMidAdapter = new TopicMidAdapter(topicMidList, this);
        midRecyclerView.setAdapter(topicMidAdapter);
    }

    //显示底部
    private void showLast() {
        lastRecyerView = (RecyclerView) findViewById(R.id.talk_last);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lastRecyerView.setLayoutManager(layoutManager);
        topicLastAdapter = new TopicLastAdapter(topicLastList, this);
        lastRecyerView.setAdapter(topicLastAdapter);

    }

}
