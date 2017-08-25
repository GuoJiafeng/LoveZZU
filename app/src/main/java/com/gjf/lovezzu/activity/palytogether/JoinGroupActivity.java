package com.gjf.lovezzu.activity.palytogether;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.playtogether.GroupData;
import com.gjf.lovezzu.entity.playtogether.GroupDataBridging;
import com.gjf.lovezzu.network.GroupMethods;
import com.gjf.lovezzu.view.PlayTogetherAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by zhao on 2017/8/20.
 */

public class JoinGroupActivity extends AppCompatActivity {


    @BindView(R.id.joined_group_recycler)
    RecyclerView joinedGroupRecycler;

    public static  JoinGroupActivity joinGroupActivity;
    private Subscriber subscriber;
    private List<GroupDataBridging> groupDataBridgingList=new ArrayList<>();
    private PlayTogetherAdapter playTogetherAdapter;
    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_group);
        ButterKnife.bind(this);
        joinGroupActivity=this;
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
        getJoinedGroup();
        showGroup();
    }

    @OnClick(R.id.activity_joined_back)
    public void onViewClicked() {
        Intent intent = new Intent(this, PlayTogetherActivity.class);
        startActivity(intent);
    }

    private void showGroup(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        joinedGroupRecycler.setLayoutManager(layoutManager);
        playTogetherAdapter=new PlayTogetherAdapter(groupDataBridgingList,"退出群组",this);
        joinedGroupRecycler.setAdapter(playTogetherAdapter);
    }

    private void getJoinedGroup(){
        subscriber=new Subscriber<GroupData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("一起玩--我加入的",e.getMessage());
            }

            @Override
            public void onNext(GroupData groupData) {
                Log.e("一起玩--我加入的",groupData.getResult());
                Log.e("一起玩--我加入的",groupData.getValues().size()+"num");
                List<GroupDataBridging> list=groupData.getValues();
                if (!list.isEmpty()){
                    groupDataBridgingList.clear();
                    groupDataBridgingList.addAll(list);
                    playTogetherAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(PlayTogetherActivity.playTogetherActivity,"没有更多数据了",Toast.LENGTH_SHORT).show();
                }
            }
        };
        GroupMethods.getInstance().getMyGroup(subscriber,"查询我加入的群组",SessionID);
    }
}
