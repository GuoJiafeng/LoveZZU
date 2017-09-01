package com.gjf.lovezzu.activity.topictalk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.topic.TopicData;
import com.gjf.lovezzu.entity.topic.TopicDataBridging;
import com.gjf.lovezzu.entity.topic.TopicThemeBridging;
import com.gjf.lovezzu.entity.topic.TopicThemeData;
import com.gjf.lovezzu.network.TopicMethods;
import com.gjf.lovezzu.network.TopicThemeMethods;
import com.gjf.lovezzu.view.TopicThemeAdapter;
import com.gjf.lovezzu.view.TopicTopicAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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

    private Subscriber subscriber;
    private List<TopicThemeBridging> topicThemeBridgingList=new ArrayList<>();
    private TopicThemeAdapter topicThemeAdapter;

    private List<TopicDataBridging> topicDataBridgingList=new ArrayList<>();
    private TopicTopicAdapter topicTopicAdapter;

    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicTalkActivity = this;
        setContentView(R.layout.topic_activity);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences= TopicTalkActivity.topicTalkActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
        onRefresh();
        getTopicTheme();
        showTheme();

    }
    private void onRefresh() {
            topicActivityRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getTopicTheme();
                    getTopics();
                }
            });
    }

    private void getTopicTheme(){
        subscriber=new Subscriber<TopicThemeData>() {
            @Override
            public void onCompleted() {
                if (topicActivityRefresh.isRefreshing()){
                    topicActivityRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TopicThemeData topicThemeData) {
                List<TopicThemeBridging> list=topicThemeData.getValues();
                if (!list.isEmpty()){
                    topicThemeBridgingList.clear();
                    topicThemeBridgingList.addAll(list);
                    TopicThemeBridging themeBridging=topicThemeBridgingList.get(0);
                    topicThemeAdapter.setTheme(themeBridging.getTheme().getThemeId());
                    topicThemeAdapter.notifyDataSetChanged();
                    getTopics();
                    showTopic();
                }else {
                    Toast.makeText(TopicTalkActivity.topicTalkActivity,"还没有圈子，快来吐槽吧!",Toast.LENGTH_SHORT).show();
                }
            }
        };
        TopicThemeMethods.getInstance().getTheme(subscriber,"查询所有主题");
    }
    public  void getTopics(){
        subscriber=new Subscriber<TopicData>() {
            @Override
            public void onCompleted() {
                if (topicActivityRefresh.isRefreshing()){
                    topicActivityRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("话题",e.getMessage());
            }

            @Override
            public void onNext(TopicData topicData) {

                List<TopicDataBridging> list=topicData.getValues();
                if (!list.isEmpty()){
                    topicDataBridgingList.clear();
                    topicDataBridgingList.addAll(list);
                    topicTopicAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(TopicTalkActivity.topicTalkActivity,"还没有话题，快来吐槽吧!",Toast.LENGTH_SHORT).show();
                }
            }
        };
        TopicMethods.getInstance().getTopic(subscriber,topicThemeAdapter.getTheme()+"","查询话题",SessionID);
    }

    private void showTheme() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(topicTalkActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topicFirstRecycler.setLayoutManager(layoutManager);
        topicThemeAdapter=new TopicThemeAdapter(topicThemeBridgingList);
        topicFirstRecycler.setAdapter(topicThemeAdapter);

    }


    private void showTopic() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(topicTalkActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topicChildRecycler.setLayoutManager(layoutManager);
        topicTopicAdapter=new TopicTopicAdapter(topicDataBridgingList);
        topicChildRecycler.setAdapter(topicTopicAdapter);
    }


    @OnClick({R.id.topic_back, R.id.topic_first_add, R.id.topic_child_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topic_back:
                Intent intent2=new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.topic_first_add:
                Intent intent=new Intent(this, ThemePublishActivity.class);
                startActivity(intent);
                break;
            case R.id.topic_child_add:
                Intent intent1=new Intent(this,TopicPublishActivity.class);
                intent1.putExtra("theme",topicThemeAdapter.getTheme());
                startActivity(intent1);
                break;
        }
    }
}
