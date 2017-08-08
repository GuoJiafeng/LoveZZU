package com.gjf.lovezzu.activity.taoyu;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.OrderDataBridging;
import com.gjf.lovezzu.view.OrderBuyInfoAdapter;

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
    @BindView(R.id.order_buy_total)
    TextView orderBuyTotal;
    private OrderDataBridging orderDataBridging;
    private OrderBuyInfoAdapter adapter;
    public static OrderBuyInfoAcivity orderBuyInfoAcivity;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_buy_info_activity);
        ButterKnife.bind(this);
        orderBuyInfoAcivity = this;
        orderDataBridging = (OrderDataBridging) getIntent().getSerializableExtra("orderDataBridging");
        layoutManager = new LinearLayoutManager(this);
        buyGoodsList.setLayoutManager(layoutManager);
        init();
    }

    private void init() {
        buyGoodsUserName.setText(orderDataBridging.getOrderdata().getName());
        buyGoodsStatus.setText(orderDataBridging.getOrderdata().getOrder_status());
        buyGoodsUserPhone.setText(orderDataBridging.getOrderdata().getPhone());
        buyGoodsUserAddress.setText(orderDataBridging.getOrderdata().getAddress());
        buyGoodsUserTime.setText(orderDataBridging.getOrderdata().getOrder_date());
        orderBuyTotal.setText(orderDataBridging.getOrderdata().getTotal());
        adapter = new OrderBuyInfoAdapter(orderDataBridging);
        buyGoodsList.setAdapter(adapter);

    }

    @OnClick(R.id.buy_goods_pay)
    public void onViewClicked() {
        Toast.makeText(this, "付款！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    adapter.call();
                    break;
                } else {
                    Toast.makeText(OrderBuyInfoAcivity.orderBuyInfoAcivity, "没有电话权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }
}
