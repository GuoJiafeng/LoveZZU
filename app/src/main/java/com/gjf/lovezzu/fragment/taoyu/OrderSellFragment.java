package com.gjf.lovezzu.fragment.taoyu;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.TaoyuOrderActivity;
import com.gjf.lovezzu.entity.taoyu.OrderSellData;
import com.gjf.lovezzu.entity.taoyu.OrderSellDataBridging;

import com.gjf.lovezzu.network.OrderSellMethods;

import com.gjf.lovezzu.view.OrderSellAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.thefinestartist.utils.content.ContextUtil.getApplicationContext;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderSellFragment extends Fragment{
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Subscriber subscriber;
    private String SessionID;

    private List<OrderSellDataBridging> sellDataBridgings=new ArrayList<>();
    private OrderSellAdapter orderSellAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = TaoyuOrderActivity.taoyuOrderActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        if (view==null){
            view=inflater.inflate(R.layout.order_sell_fragment,container,false);
            linearLayoutManager=new LinearLayoutManager(view.getContext());
            SessionID = sharedPreferences.getString("SessionID", "");
            getSellOrder();
            recyclerView= (RecyclerView) view.findViewById(R.id.order_sell_recycler);
            refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.sell_order_refresh);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sellDataBridgings.clear();
                    getSellOrder();
                }
            });
            orderSellAdapter=new OrderSellAdapter(sellDataBridgings);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(orderSellAdapter);

        }else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }

        }
        return view;

    }


    private void getSellOrder(){
        subscriber=new Subscriber<OrderSellData>() {
            @Override
            public void onCompleted() {
                if (refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("订单-------error",e.getMessage());
                Toast.makeText(getApplicationContext(),"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(OrderSellData orderSellData) {
                List<OrderSellDataBridging> list=orderSellData.getValues();
                if (list!=null){
                    sellDataBridgings.addAll(list);
                    orderSellAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TaoyuOrderActivity.taoyuOrderActivity,"没有订单！",Toast.LENGTH_SHORT).show();
                }
            }
        };

        OrderSellMethods.getInstance().getOrderSell(subscriber,"查询卖家订单",SessionID);

    }
}

