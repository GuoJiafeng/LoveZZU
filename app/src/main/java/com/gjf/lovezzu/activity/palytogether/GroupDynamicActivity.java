package com.gjf.lovezzu.activity.palytogether;

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

import com.gjf.lovezzu.entity.playtogether.GroupDynamicData;
import com.gjf.lovezzu.entity.playtogether.GroupDynamicResult;
import com.gjf.lovezzu.network.GroupMethods;
import com.gjf.lovezzu.view.DynamicAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by zhao on 2017/8/21.
 */

public class GroupDynamicActivity extends AppCompatActivity {

    @BindView(R.id.group_dynamic_view)
    RecyclerView groupDynamicView;
    @BindView(R.id.dynamic_refresh)
    SwipeRefreshLayout dynamicRefresh;
    @BindView(R.id.activity_dynamic_back)
    ImageView activityDynamicBack;
    @BindView(R.id.dynamic_fab)
    ImageView dynamicFab;
    private String groupId;
    public  static GroupDynamicActivity groupDynamicActivity;
    private  List<GroupDynamicResult> groupDynamicReaultList =new ArrayList<>();
    private Subscriber subscriber;
    private DynamicAdapter dynamicAdapter;
    private static int START=0;
    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        ButterKnife.bind(this);
        groupDynamicActivity=this;
        groupId = getIntent().getStringExtra("groupId");
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
        showGroupDynamic("查询群动态",START);
        initView();
        dynamicRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                START=0;
                showGroupDynamic("查询群动态",0);
            }
        });
        groupDynamicView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isDown=false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    int latItem=layoutManager.findLastCompletelyVisibleItemPosition();
                    int itemCount=layoutManager.getItemCount();
                    if (latItem==itemCount&&isDown){
                        Toast.makeText(groupDynamicActivity,"加载中",Toast.LENGTH_SHORT).show();
                        showGroupDynamic("查询群动态",START+=10);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    isDown=true;
                }else {
                    isDown=false;
                }
            }
        });
    }

    private void initView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        groupDynamicView.setLayoutManager(layoutManager);
        dynamicAdapter=new DynamicAdapter(groupDynamicReaultList);
        groupDynamicView.setAdapter(dynamicAdapter);
    }

    private void showGroupDynamic(String action, final int num) {
        subscriber = new Subscriber<GroupDynamicData>() {
            @Override
            public void onCompleted() {
                if(dynamicRefresh.isRefreshing()){
                    dynamicRefresh.setRefreshing(false);
                }

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("动态",e.getMessage()+e.toString());
            }

            @Override
            public void onNext(GroupDynamicData groupDynamicData) {
                Log.e("动态",groupDynamicData.getResult()+"");
                Log.e("动态",groupDynamicData.getValues().size()+" ");
                List<GroupDynamicResult> list = groupDynamicData.getValues();
                if (list.size()!=0){
                    if (num==0){
                        groupDynamicReaultList.clear();
                    }
                    groupDynamicReaultList.addAll(list);
                    dynamicAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(GroupDynamicActivity.groupDynamicActivity, "没有更多动态了，快发布群组动态吧！", Toast.LENGTH_SHORT).show();
                }
            }

        };

        GroupMethods.getInstance().getDynamic(subscriber,SessionID,groupId,action,num);
    }

    @OnClick({R.id.activity_dynamic_back, R.id.dynamic_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_dynamic_back:
                Intent intent2=new Intent(this,PlayTogetherActivity.class);
                startActivity(intent2);
                break;
            case R.id.dynamic_fab:
                Intent intent = new Intent(this, PublishDynamicActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                break;
        }
    }
}
