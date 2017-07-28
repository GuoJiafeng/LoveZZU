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
import android.widget.TextView;


import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.Topic2;
import com.gjf.lovezzu.entity.Topic2main;
import com.gjf.lovezzu.view.Topic2Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo047 on 2017/5/27.
 */

public class topic2Activity extends AppCompatActivity {
    private List<Topic2> topic2List = new ArrayList<>();
    private  List<Topic2main>  topic2mainList=new ArrayList<>();
    private RecyclerView top2RecyclerView;
    private ImageView backImage;
    private SwipeRefreshLayout topic2Regresh;
    private Topic2Adapter topic2Adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic2);
        backImage = (ImageView) findViewById(R.id.back1);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(topic2Activity.this, TopicTalkActivity.class);
                startActivity(intent);

            }
        });
        initItem();

        showtopic2();
        onRefresh();
    }


    //刷新操作
    private void onRefresh() {
        topic2Regresh = (SwipeRefreshLayout) findViewById(R.id.topic2_refresh);
        topic2Regresh.setColorSchemeResources(R.color.colorPrimary);
        topic2Regresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                        topic2Adapter.notifyDataSetChanged();

                        topic2Regresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    private void initItem() {

        Topic2 topic2 = new Topic2("这是昵称啊啊啊","这是评论啊啊啊", 2,2,2);
        Topic2 topic1 = new Topic2("这是昵称啊啊啊","这是评论啊啊啊", 2,2,2);
        Topic2 topic3 = new Topic2("这是昵称啊啊啊","这是评论啊啊啊", 2,2,2);

        Topic2main topic2main = new Topic2main("这是昵称啊啊啊啊","这是内容啊啊啊",2,2,2,"这是名字啊啊啊");

        topic2List.clear();
        topic2mainList.clear();

        topic2List.add(topic2);
        topic2List.add(topic1);
        topic2List.add(topic3);
        topic2List.add(topic2);
        topic2mainList.add(topic2main);
        topic2mainList.add(topic2main);
        topic2mainList.add(topic2main);
        topic2mainList.add(topic2main);





    }

    private  void showtopic2(){
        top2RecyclerView = (RecyclerView) findViewById(R.id.topic2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        top2RecyclerView.setLayoutManager(layoutManager);
        topic2Adapter = new Topic2Adapter(topic2List, this);
        top2RecyclerView.setAdapter(topic2Adapter);
    }


}