package com.gjf.lovezzu.activity.taoyu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.fragment.taoyu.OrderBuyFragment;
import com.gjf.lovezzu.fragment.taoyu.OrderSellFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhao on 2017/8/4.
 */

public class TaoyuOrderActivity extends AppCompatActivity {

    public static TaoyuOrderActivity taoyuOrderActivity;
    @BindView(R.id.order_buy)
    TextView orderBuy;
    @BindView(R.id.order_sell)
    TextView orderSell;
    @BindView(R.id.order_activity)
    FrameLayout orderActivity;
    private OrderBuyFragment orderBuyFragment;
    private OrderSellFragment orderSellFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        taoyuOrderActivity = this;
        orderBuyFragment = new OrderBuyFragment();
        orderSellFragment = new OrderSellFragment();
        orderBuy.setTextColor(Color.parseColor("#0090FD"));
        orderSell.setTextColor(Color.parseColor("#000000"));
        replaceFragment(orderBuyFragment);
    }


    @OnClick({R.id.order_buy, R.id.order_sell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_buy:
                if (orderBuyFragment==null){
                    orderBuyFragment=new OrderBuyFragment();
                }
                orderBuy.setTextColor(Color.parseColor("#0090FD"));
                orderSell.setTextColor(Color.parseColor("#000000"));
                replaceFragment(orderBuyFragment);
                break;
            case R.id.order_sell:
                if (orderSellFragment==null){
                    orderSellFragment=new OrderSellFragment();
                }
                orderBuy.setTextColor(Color.parseColor("#000000"));
                orderSell.setTextColor(Color.parseColor("#0090FD"));
                replaceFragment(orderSellFragment);
                break;
        }
    }


    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.order_activity,fragment);
        fragmentTransaction.commit();
    }
}
