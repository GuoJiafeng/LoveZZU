package com.gjf.lovezzu.activity.taoyu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.OrderDataBridging;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhao on 2017/8/7.
 */

public class OrderBuyInfoAcivity extends AppCompatActivity {


    @BindView(R.id.buy_goods_user_name)
    TextView buyGoodsUserName;
    @BindView(R.id.buy_goods_status)
    TextView buyGoodsStatus;
    @BindView(R.id.buy_goods_user_phone)
    TextView buyGoodsUserPhone;
    @BindView(R.id.buy_goods_user_address)
    TextView buyGoodsUserAddress;
    @BindView(R.id.buy_goods_user_time)
    TextView buyGoodsUserTime;
    @BindView(R.id.buy_goods_list)
    RecyclerView buyGoodsList;
    @BindView(R.id.order_info_goods_body)
    LinearLayout orderInfoGoodsBody;
    @BindView(R.id.buy_goods_pay)
    CardView buyGoodsPay;
    private OrderDataBridging orderDataBridging;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_buy_info_activity);
        ButterKnife.bind(this);
        orderDataBridging = (OrderDataBridging) getIntent().getSerializableExtra("orderDataBridging");
    }

    @OnClick(R.id.buy_goods_pay)
    public void onViewClicked() {

    }
}
