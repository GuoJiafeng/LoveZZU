package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.taoyu.Order;
import com.gjf.lovezzu.entity.taoyu.OrderItems;
import com.gjf.lovezzu.entity.taoyu.ShoppingCartDate;
import com.gjf.lovezzu.entity.taoyu.ShoppingCartDateBridging;
import com.gjf.lovezzu.network.ShoppingCartMethods;
import com.gjf.lovezzu.view.ShoppingCartAdapter;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

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
    private  Order orderdata=new Order();
    private List<OrderItems> orderItemsList=new ArrayList<>();
    private List<String> StrOrderItemsData=new ArrayList<>();
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
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, false);
                }
                shoppingCartAdapter.notifyDataSetChanged();
                cartAll.setChecked(false);
                orderItemsList.clear();
                StrOrderItemsData.clear();
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
                orderItemsList.clear();
                break;
            case R.id.cart_go_order:
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {
                        ShoppingCartDateBridging shoppingCartDateBridging=shoppingCartDateBridgingList.get(i);
                        OrderItems orderItems=new OrderItems();
                        orderItems.setCount(Integer.parseInt(shoppingCartDateBridging.getCount()));
                        orderItems.setGoods_id(shoppingCartDateBridging.getGoods().getGoods_id());
                        orderItemsList.add(orderItems);
                    }
                }
                View view1=View.inflate(ShopcartActivity.shopcartActivity,R.layout.taoyu_orderdata,null);
                final EditText name= (EditText) view1.findViewById(R.id.order_name);
                final EditText address= (EditText) view1.findViewById(R.id.order_address);
                final EditText phone= (EditText) view1.findViewById(R.id.order_phone);
                new android.app.AlertDialog.Builder(ShopcartActivity.shopcartActivity).setMessage("请准确填写信息，以便卖家尽快发货：")
                        .setView(view1).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.getText().toString()!=null&&address.getText().toString()!=null&&phone.getText().toString()!=null) {
                            orderdata.setName(name.getText().toString());
                            orderdata.setAddress(address.getText().toString());
                            orderdata.setPhone(phone.getText().toString());
                            createOrder();
                        }else {
                            Toast.makeText(ShopcartActivity.shopcartActivity,"请准确填写信息",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < map.size(); i++) {
                            map.put(i, false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                        cartAll.setChecked(false);
                        orderItemsList.clear();
                        StrOrderItemsData.clear();
                    }
                }).show();
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
        if (orderItemsList.size()!=0){
            orderItemsList.clear();
        }
        StrOrderItemsData.clear();
        orderItemsList.clear();
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
        RequestParams requestParams=new RequestParams(LOGIN_URL+"OrderAction");
        requestParams.setMultipart(true);

        Gson gson = new Gson();
        String orderData=gson.toJson(orderdata);
        for (OrderItems orderItems1:orderItemsList){
            String orderItems=gson.toJson(orderItems1);
            StrOrderItemsData.add(orderItems);
        }

        requestParams.addBodyParameter("action","生成订单");
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("StrOrderData",orderData);
        requestParams.addParameter("StrOrderItemsData",StrOrderItemsData);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(ShopcartActivity.shopcartActivity,"订单已生成，请前往订单查看并尽快完成付款！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ShopcartActivity.shopcartActivity,"订单生成失败！请重新登录或者检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("订单---------生成错误",ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, false);
                }
                StrOrderItemsData.clear();
                shoppingCartAdapter.notifyDataSetChanged();
                cartAll.setChecked(false);
                orderItemsList.clear();
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < map.size(); i++) {
            map.put(i, false);
        }
        StrOrderItemsData.clear();
        shoppingCartAdapter.notifyDataSetChanged();
        cartAll.setChecked(false);
        orderItemsList.clear();
    }
}

