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

public class ShopcartActivity extends AppCompatActivity {

    private RecyclerView shopcartRecyvlerView;
    private ImageView backImage;
    private SwipeRefreshLayout shopcartRefresh;


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


    }

    private void initItem() {




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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initItem();


                        shopcartRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}

