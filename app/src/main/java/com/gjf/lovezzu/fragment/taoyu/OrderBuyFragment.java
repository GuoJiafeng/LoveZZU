package com.gjf.lovezzu.fragment.taoyu;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.gjf.lovezzu.entity.taoyu.OrderData;
import com.gjf.lovezzu.entity.taoyu.OrderDataBridging;
import com.gjf.lovezzu.network.OrderDataMethods;
import com.gjf.lovezzu.view.OrderActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.thefinestartist.utils.content.ContextUtil.getApplicationContext;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderBuyFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Subscriber subscriber;
    private String SessionID;
    private List<OrderDataBridging> orderDataBridgingList=new ArrayList<>();
    private OrderActivityAdapter orderActivityAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = TaoyuOrderActivity.taoyuOrderActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
            if (view==null){
                view=inflater.inflate(R.layout.order_buy_fragment,container,false);
                linearLayoutManager=new LinearLayoutManager(view.getContext());
                SessionID = sharedPreferences.getString("SessionID", "");
                getBuyOrder();
                recyclerView= (RecyclerView) view.findViewById(R.id.order_buy_recycler);
                refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.buy_order_refresh);
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (orderDataBridgingList.size()!=0){
                            orderDataBridgingList.clear();
                        }
                        getBuyOrder();

                    }
                });
                orderActivityAdapter=new OrderActivityAdapter(orderDataBridgingList);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(orderActivityAdapter);
            }else {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(view);
                }

            }
            return view;

    }




    private void getBuyOrder(){
        subscriber=new Subscriber<OrderData>() {
            @Override
            public void onCompleted() {
                if (refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("订单-------------error",e.getMessage());
                Toast.makeText(getApplicationContext(),"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(OrderData orderData) {

                if (orderData.getValues()!=null){
                    List<OrderDataBridging> list=orderData.getValues();
                    if (list.size()!=0){
                        orderDataBridgingList.addAll(list);
                        orderActivityAdapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(TaoyuOrderActivity.taoyuOrderActivity,"没有订单！",Toast.LENGTH_SHORT).show();
                }
            }

        };

        OrderDataMethods.getInstance().getOrderData(subscriber,"查询买家订单",SessionID);

    }
}
