package com.gjf.lovezzu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.PlayEnd;
import com.gjf.lovezzu.entity.PlayItems;
import com.gjf.lovezzu.entity.PlayTop;
import com.gjf.lovezzu.view.PlayTogetherAdapter;
import com.gjf.lovezzu.view.TypeTwoViewViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaox on 2017/4/8.
 */

public class PlayTogetherActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //数据源
    /*List<PlayTop> playTopList = new ArrayList<>();
    List<PlayEnd> playEndList = new ArrayList<>();*/
    List<PlayItems> playItemsList=new ArrayList<>();
    PlayTogetherAdapter adapter;

    @BindView(R.id.play_title_back)
    ImageView playTitleBack;
    @BindView(R.id.play_menu)
    ImageView playMenu;
    @BindView(R.id.play_together_view)
    RecyclerView playRecyvlerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_teg_view);
        ButterKnife.bind(this);
        initDate();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        playRecyvlerView.setLayoutManager(layoutManager);
        adapter=new PlayTogetherAdapter(playItemsList);
        playRecyvlerView.setAdapter(adapter);
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

    //初始化加载数据
    private void initDate() {

        for (int i = 1; i <=4 ; i++) {


            PlayTop playTop = new PlayTop(R.drawable.test_person_01, "凹凸", "4-11 23:01", "123", "110",
                    "新闻的大标题111", "新闻的小标题", R.drawable.life_beautiful_girl);
            PlayEnd playEnd = new PlayEnd(R.drawable.test_person_01, "PlayBoy", "Android小组", R.drawable.new_group,
                    R.drawable.new_add, "小漠国服第一系列的第一螳螂丶第一大眼,国服最高排名第八,曾任CC战队教练击败OMG战队,现为国服第一金牌讲师长驻斗鱼..",
                    R.drawable.test_person_03, R.drawable.test_person_03, R.drawable.test_person_03,
                    R.drawable.test_person_03, R.drawable.test_person_03, R.drawable.test_person_03, R.drawable.test_person_03);
            playItemsList.add(playTop);
            playItemsList.add(playEnd);
          /*  playTopList.add(playTop);
            playEndList.add(playEnd);*/
        }

    }
}
