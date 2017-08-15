package com.gjf.lovezzu.activity.treehole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_hole_view);
        ButterKnife.bind(this);
        treeHoleActivity=this;

    }



    @OnClick({R.id.tree_title_back, R.id.tree_menu, R.id.tree_refresh, R.id.tree_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tree_title_back:
                finish();
                break;
            case R.id.tree_menu:
                getMenu();
                break;
            case R.id.tree_refresh:
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
                Toast.makeText(this,"发布我的树洞",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tree_comment:
                Toast.makeText(this,"我评论的",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }


}
