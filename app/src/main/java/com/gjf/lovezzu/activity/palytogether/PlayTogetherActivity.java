package com.gjf.lovezzu.activity.palytogether;

import android.os.Bundle;
import android.support.annotation.Nullable;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_teg_view);
        ButterKnife.bind(this);
        playTogetherActivity=this;

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
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.play_create_group:
                Toast.makeText(this, "创建群组", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }


}
