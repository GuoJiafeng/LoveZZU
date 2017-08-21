package com.gjf.lovezzu.activity.palytogether;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
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

public class SearchGroupActivity extends AppCompatActivity {


    @BindView(R.id.taoyu_search_title)
    EditText taoyuSearchTitle;
    @BindView(R.id.group_search_list)
    RecyclerView groupSearchList;
    private Subscriber subscriber;
    private List<GroupDataBridging> groupDataBridgingList=new ArrayList<>();
    private PlayTogetherAdapter playTogetherAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        ButterKnife.bind(this);
        showGroup();
        taoyuSearchTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    if (!taoyuSearchTitle.getText().toString().trim().equals("")){
                        searchGroup();
                    }else {
                        Toast.makeText(SearchGroupActivity.this,"请输入关键字",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    @OnClick(R.id.taoyu_search_button)
    public void onViewClicked() {
        if (!taoyuSearchTitle.getText().toString().trim().equals("")){
            searchGroup();
        }else {
            Toast.makeText(this,"请输入关键字",Toast.LENGTH_SHORT).show();
        }
    }

    private void showGroup(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        groupSearchList.setLayoutManager(layoutManager);
        playTogetherAdapter=new PlayTogetherAdapter(groupDataBridgingList);
        groupSearchList.setAdapter(playTogetherAdapter);
    }

    private void searchGroup(){
        subscriber=new Subscriber<GroupData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("一起玩--搜索",e.getMessage());
            }

            @Override
            public void onNext(GroupData groupData) {
                Log.e("一起玩--搜索",groupData.getResult());
                Log.e("一起玩--搜索",groupData.getValues().size()+"num");
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

        GroupMethods.getInstance().getSearchGroup(subscriber,taoyuSearchTitle.getText().toString().trim(),"搜索群组");
    }

}
