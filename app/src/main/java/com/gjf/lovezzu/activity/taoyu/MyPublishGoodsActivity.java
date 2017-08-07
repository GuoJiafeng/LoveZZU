package com.gjf.lovezzu.activity.taoyu;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.MyPublishGoodsData;
import com.gjf.lovezzu.entity.TaoyuGoodsResult;
import com.gjf.lovezzu.network.MyGoodsMethods;
import com.gjf.lovezzu.view.MyPublishOGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by zhao on 2017/8/7.
 */

public class MyPublishGoodsActivity extends AppCompatActivity {

    public static MyPublishGoodsActivity myPublishGoodsActivity;
    @BindView(R.id.my_publish_godds_back)
    ImageView myPublishGoddsBack;
    @BindView(R.id.my_publish_goods_RecyclerView)
    RecyclerView myPublishGoodsRecyclerView;
    @BindView(R.id.my_publish_refresh)
    SwipeRefreshLayout myPublishRefresh;

    private List<TaoyuGoodsResult> taoyuGoodsResultList=new ArrayList<>();
    private MyPublishOGoodsAdapter myPublishOGoodsAdapter;
    private Subscriber subscriber;
    private String sessoinID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish_goods);
        ButterKnife.bind(this);
        myPublishGoodsActivity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        sessoinID = sharedPreferences.getString("SessionID", "");
        getGoods();
        init();
    }

    private void init(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myPublishGoodsRecyclerView.setLayoutManager(layoutManager);
        myPublishOGoodsAdapter=new MyPublishOGoodsAdapter(taoyuGoodsResultList);
        myPublishGoodsRecyclerView.setAdapter(myPublishOGoodsAdapter);

        myPublishRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taoyuGoodsResultList.clear();
                getGoods();
            }
        });
    }

    private void getGoods(){
        subscriber=new Subscriber<MyPublishGoodsData>() {
            @Override
            public void onCompleted() {
                if (myPublishRefresh.isRefreshing()){
                    myPublishRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("我的发布========",e.getMessage());
            }

            @Override
            public void onNext(MyPublishGoodsData myPublishGoodsData) {
                Log.e("我的发布========",myPublishGoodsData.getResult());
                List<TaoyuGoodsResult> list=myPublishGoodsData.getValues();
                if (list!=null){
                    taoyuGoodsResultList.addAll(list);
                    myPublishOGoodsAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MyPublishGoodsActivity.myPublishGoodsActivity,"你还没有发布任何商品！",Toast.LENGTH_SHORT).show();
                }
            }

        };

        MyGoodsMethods.getInstance().getMyGoods(subscriber,sessoinID,"查询已发布商品");
    }

    @OnClick(R.id.my_publish_godds_back)
    public void onViewClicked() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
