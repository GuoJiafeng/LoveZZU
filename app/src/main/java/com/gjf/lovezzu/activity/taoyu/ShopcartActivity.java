package com.gjf.lovezzu.activity.taoyu;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.Shopcart;
import com.gjf.lovezzu.view.ShopcartAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopcartActivity extends AppCompatActivity {
    private List<Shopcart>  shopcartList=new ArrayList<>();
    private RecyclerView shopcartRecyvlerView;
    private ImageView backImage;
    private SwipeRefreshLayout shopcartRefresh;
    private ShopcartAdapter shopcartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart);
        backImage = (ImageView) findViewById(R.id.shopcart_back);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopcartActivity.this, TaoyuActivity.class);
                startActivity(intent);

            }
        });
        initItem();

        showshopcart();
        onRefresh();
    }

    private void showshopcart() {
        shopcartRecyvlerView= (RecyclerView) findViewById(R.id.shopcartRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopcartRecyvlerView.setLayoutManager(layoutManager);
        shopcartAdapter=new ShopcartAdapter(shopcartList,this);
        shopcartRecyvlerView.setAdapter(shopcartAdapter);

    }

    private void initItem() {
        Shopcart shopcart1=new Shopcart("啦啦啦",123,"哈哈哈","嘻嘻嘻嘻");

        shopcartList.clear();
        shopcartList.add(shopcart1);
        shopcartList.add(shopcart1);
        shopcartList.add(shopcart1);

    }
    private void onRefresh() {
        shopcartRefresh = (SwipeRefreshLayout) findViewById(R.id.shopcart_refresh);
        shopcartRefresh.setColorSchemeResources(R.color.colorPrimary);
        shopcartRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshshopcart();
            }
        });
    }

    //刷新数据
    private void refreshshopcart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据并更新
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initItem();
                        shopcartAdapter.notifyDataSetChanged();

                        shopcartRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}

