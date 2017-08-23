package com.gjf.lovezzu.activity.palytogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
 * Created by zhaox on 2017/4/8.
 */

public class PlayTogetherActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static PlayTogetherActivity playTogetherActivity;

    @BindView(R.id.play_title_back)
    ImageView playTitleBack;
    @BindView(R.id.play_menu)
    ImageView playMenu;
    @BindView(R.id.play_together_view)
    RecyclerView playRecyvlerView;
    @BindView(R.id.play_refresh)
    SwipeRefreshLayout playSwipeRefresh;
    private static int START=0;
    private Subscriber subscriber;
    private List<GroupDataBridging> groupDataBridgingList=new ArrayList<>();
    private PlayTogetherAdapter playTogetherAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_teg_view);
        ButterKnife.bind(this);
        playTogetherActivity=this;
        START=0;
        getGroup(0);
        playSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGroup(0);
            }
        });
        playRecyvlerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部，并且是向下滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        Toast.makeText(PlayTogetherActivity.playTogetherActivity, "加载中", Toast.LENGTH_SHORT).show();
                        getGroup(START += 10);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }
            }
        });
        showGroup();
    }

    @OnClick({R.id.play_title_back, R.id.play_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_title_back:
                finish();
                break;
            case R.id.play_menu:
                getMenu();
                break;
        }
    }

    private void getMenu() {
        //创建弹出式菜单（API>11）
        PopupMenu popupMenu = new PopupMenu(this, playMenu);
        //获取菜单填充器
        MenuInflater inflater = popupMenu.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.play_menu, popupMenu.getMenu());
        //绑定菜单的点击事件
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.play_search:
                startActivity(new Intent(this,SearchGroupActivity.class));
                break;
            case R.id.play_create_group:
                startActivity(new Intent(this,PublishGroupActivity.class));
                break;
            case R.id.play_publish_group:
                startActivity(new Intent(this,MyGroupActivity.class));
                break;
            case R.id.play_join_group:
                startActivity(new Intent(this,JoinGroupActivity.class));
                break;
        }
        return false;
    }

    private void showGroup(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        playRecyvlerView.setLayoutManager(layoutManager);
        playTogetherAdapter=new PlayTogetherAdapter(groupDataBridgingList);
        playRecyvlerView.setAdapter(playTogetherAdapter);
    }

    private void getGroup(int num){
        subscriber=new Subscriber<GroupData>() {
            @Override
            public void onCompleted() {
                if (playSwipeRefresh.isRefreshing()){
                    playSwipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("一起玩--查询",e.getMessage());
            }

            @Override
            public void onNext(GroupData groupData) {
                Log.e("一起玩--查询",groupData.getResult());
                Log.e("一起玩--查询",groupData.getValues().size()+"num");
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

        GroupMethods.getInstance().getGroup(subscriber,"查询群组",num);
    }

    @Override
    protected void onResume() {
        super.onResume();
        START=0;
    }
}
