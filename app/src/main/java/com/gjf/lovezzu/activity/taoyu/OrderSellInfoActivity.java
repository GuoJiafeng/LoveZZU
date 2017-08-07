package com.gjf.lovezzu.activity.taoyu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.Goods;
import com.gjf.lovezzu.entity.OrderSellDataBridging;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/7.
 */

public class OrderSellInfoActivity extends AppCompatActivity {
    @BindView(R.id.order_info_user_name)
    TextView orderInfoUserName;
    @BindView(R.id.order_info_status)
    TextView orderInfoStatus;
    @BindView(R.id.order_info_user_phone)
    TextView orderInfoUserPhone;
    @BindView(R.id.order_info_user_address)
    TextView orderInfoUserAddress;
    @BindView(R.id.order_info_goods_image)
    ImageView orderInfoGoodsImage;
    @BindView(R.id.order_info_goods_name)
    TextView orderInfoGoodsName;
    @BindView(R.id.order_info_goods_desc)
    TextView orderInfoGoodsDesc;
    @BindView(R.id.order_info_goods_price)
    TextView orderInfoGoodsPrice;
    @BindView(R.id.order_info_goods_body)
    LinearLayout orderInfoGoodsBody;
    @BindView(R.id.order_info_count)
    TextView orderInfoCount;
    @BindView(R.id.order_info_total)
    TextView orderInfoTotal;
    @BindView(R.id.order_info_call)
    CardView orderInfoCall;
    @BindView(R.id.order_info_user_time)
    TextView orderInfoUserTime;

    private OrderSellDataBridging orderSellDataBridging;
    private Goods goods=new Goods();
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_activity);
        ButterKnife.bind(this);
        orderSellDataBridging = (OrderSellDataBridging) getIntent().getSerializableExtra("orderSellDataBridging");
        init();
    }

    private void init() {
        orderInfoUserName.setText(orderSellDataBridging.getOrder().getName());
        orderInfoUserPhone.setText(orderSellDataBridging.getOrder().getPhone());
        phone=orderSellDataBridging.getOrder().getPhone();
        orderInfoUserAddress.setText(orderSellDataBridging.getOrder().getAddress());
        orderInfoStatus.setText(orderSellDataBridging.getOrder().getOrder_status());
        orderInfoUserTime.setText(orderSellDataBridging.getOrder().getOrder_date());
        orderInfoGoodsName.setText(orderSellDataBridging.getGoods().getGname());
        orderInfoGoodsDesc.setText(orderSellDataBridging.getGoods().getGdescribe());
        orderInfoGoodsPrice.setText(orderSellDataBridging.getGoods().getGprice());
        orderInfoCount.setText(orderSellDataBridging.getCount()+"");
        orderInfoTotal.setText(orderSellDataBridging.getOrder().getTotal());
        String imagesUrl=orderSellDataBridging.getGoods().getGimage();
        String images[]=imagesUrl.split("ZZU");
        Glide.with(this).load(LOGIN_URL + "filedownload?action=商品&imageURL=" + images[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(orderInfoGoodsImage);
        goods.setGprice(orderSellDataBridging.getGoods().getGprice());
        goods.setImages(images);
        goods.setGname(orderSellDataBridging.getGoods().getGname());
        goods.setGdescribe(orderSellDataBridging.getGoods().getGdescribe());
        goods.setGoods_id(orderSellDataBridging.getGoods().getGoods_id());
        goods.setGcampus(orderSellDataBridging.getGoods().getGcampus());

    }
    private void call(){
        try {
            Intent intent1=new Intent(Intent.ACTION_CALL);
            intent1.setData(Uri.parse("tel:"+phone));
            startActivity(intent1);
        }catch (SecurityException e){
            e.printStackTrace();
            Toast.makeText(this,"号码格式不正确！",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.order_info_goods_body, R.id.order_info_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_info_goods_body:
                Intent intent=new Intent(this,TaoyuDetialActivity.class);
                intent.putExtra("goods",goods);
                startActivity(intent);
                break;
            case R.id.order_info_call:
               if (ContextCompat.checkSelfPermission(OrderSellInfoActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(OrderSellInfoActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
               }else {
                   call();
               }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }else {
                    Toast.makeText(this,"没有电话权限！",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
