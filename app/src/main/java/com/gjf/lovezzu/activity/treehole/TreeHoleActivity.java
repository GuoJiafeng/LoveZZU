package com.gjf.lovezzu.activity.treehole;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.gjf.lovezzu.entity.treehole.TreeHole;
import com.gjf.lovezzu.entity.treehole.TreeHoleData;
import com.gjf.lovezzu.network.TreeHoleMethods;
import com.gjf.lovezzu.view.TreeHoleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by zhao on 2017/5/4.
 */

public class TreeHoleActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    public static TreeHoleActivity treeHoleActivity;

    @BindView(R.id.tree_title_back)
    ImageView treeTitleBack;
    @BindView(R.id.tree_menu)
    ImageView treeMenu;
    @BindView(R.id.tree_item_view)
    RecyclerView treeItemView;
    @BindView(R.id.tree_refresh)
    SwipeRefreshLayout treeRefresh;
    @BindView(R.id.tree_fab)
    ImageView treeFab;

    private Subscriber subscriber;
    private String SessionID;
    private List<TreeHole> treeHoleList=new ArrayList<>();
    private TreeHoleAdapter treeHoleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_hole_view);
        ButterKnife.bind(this);
        treeHoleActivity=this;
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SessionID=sharedPreferences.getString("SessionID","");
        treeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTreeHole("查询树洞");
            }
        });
        getTreeHole("查询树洞");
        showTree();
    }

    private void showTree(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        treeItemView.setLayoutManager(layoutManager);
        treeHoleAdapter=new TreeHoleAdapter(treeHoleList,SessionID);
        treeItemView.setAdapter(treeHoleAdapter);
    }

    @OnClick({R.id.tree_title_back, R.id.tree_menu, R.id.tree_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tree_title_back:
                finish();
                break;
            case R.id.tree_menu:
                getMenu();
                break;
            case R.id.tree_fab:
                Intent intent=new Intent(TreeHoleActivity.this,AddTreeHoleActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void getMenu(){
        PopupMenu popupMenu=new PopupMenu(this,treeMenu);
        MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.tree_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tree_publish:
                getTreeHole("查询我发布的树洞");
                break;
            case R.id.tree_comment:
                getTreeHole("查询我评论过的树洞");
                break;
            default:
                break;
        }
        return false;
    }

    private void getTreeHole(String action){
        subscriber=new Subscriber<TreeHoleData>() {
            @Override
            public void onCompleted() {
                if (treeRefresh.isRefreshing()){
                    treeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TreeHoleData treeHoleData) {

                List<TreeHole> list=treeHoleData.getValues();
                if (list.size()!=0){
                    treeHoleList.clear();
                    treeHoleList.addAll(list);
                    treeHoleAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TreeHoleActivity.treeHoleActivity,"还没有最新发言，快来吐槽吧！",Toast.LENGTH_SHORT).show();
                }
            }
        };
        TreeHoleMethods.getInstance().getTreeHole(subscriber,action,SessionID);
    }



}
