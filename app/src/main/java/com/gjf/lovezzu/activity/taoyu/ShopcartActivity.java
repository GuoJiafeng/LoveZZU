package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.ShoppingCartDate;
import com.gjf.lovezzu.entity.ShoppingCartDateBridging;
import com.gjf.lovezzu.network.ShoppingCartMethods;
import com.gjf.lovezzu.view.ShoppingCartAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class ShopcartActivity extends AppCompatActivity {


    public static ShopcartActivity shopcartActivity;
    @BindView(R.id.shopcart_back)
    ImageView shopcartBack;
    @BindView(R.id.cart_alter_message)
    TextView cartAlterMessage;
    @BindView(R.id.shopcartRecyclerView)
    RecyclerView shopcartRecyclerView;
    @BindView(R.id.shopcart_refresh)
    SwipeRefreshLayout shopcartRefresh;
    @BindView(R.id.cart_all)
    CheckBox cartAll;
    @BindView(R.id.cart_cancel)
    TextView cartCancel;
    @BindView(R.id.cart_go_order)
    TextView cartGoOrder;


    private Subscriber subscriber;
    private Map<Integer, Boolean> map=new HashMap<>();
    private ShoppingCartAdapter shoppingCartAdapter;
    private List<ShoppingCartDateBridging> shoppingCartDateBridgingList=new ArrayList<>();
    private String SessionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart);
        ButterKnife.bind(this);
        shopcartActivity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
        getShopCart();
        initView();
        shopcartRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shoppingCartDateBridgingList.clear();
                getShopCart();
            }
        });
    }


    @OnClick({R.id.shopcart_back, R.id.cart_alter_message, R.id.cart_all, R.id.cart_cancel, R.id.cart_go_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopcart_back:
                Intent intent=new Intent(ShopcartActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.cart_alter_message:
                Toast.makeText(ShopcartActivity.shopcartActivity,"选择商品-->生成订单-->完成付款",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cart_all:
                if (cartAll.isChecked()){
                    for (int i = 0; i < map.size(); i++) {
                        map.put(i, true);
                    }
                }else {
                    for (int i = 0; i < map.size(); i++) {
                        map.put(i, false);
                    }
                }
                shoppingCartAdapter.notifyDataSetChanged();
                break;
            case R.id.cart_cancel:
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, false);
                }
                shoppingCartAdapter.notifyDataSetChanged();
                cartAll.setChecked(false);
                break;
            case R.id.cart_go_order:
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {

                    }
                }
                break;
        }
    }

    private void initView(){
        shoppingCartAdapter=new ShoppingCartAdapter(shoppingCartDateBridgingList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopcartRecyclerView.setLayoutManager(layoutManager);
        shopcartRecyclerView.setAdapter(shoppingCartAdapter);
        map=shoppingCartAdapter.getMap();
    }


    private void getShopCart(){
        subscriber=new Subscriber<ShoppingCartDate>() {

            @Override
            public void onCompleted() {
                if (shopcartRefresh.isRefreshing()){
                    shopcartRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("购物车-----------------",e.getMessage());
            }

            @Override
            public void onNext(ShoppingCartDate shoppingCartDate) {
                List<ShoppingCartDateBridging> list=shoppingCartDate.getValues();
                if (list.size()==0){
                    Toast.makeText(ShopcartActivity.shopcartActivity,"您还没有添加商品",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    shoppingCartDateBridgingList.addAll(list);
                    shoppingCartAdapter.notifyDataSetChanged();
                }

            }
        };
        ShoppingCartMethods.getInstance().getShoppingCart(subscriber,SessionID,"查询");

    }

    private void createOrder(){


    }
}

