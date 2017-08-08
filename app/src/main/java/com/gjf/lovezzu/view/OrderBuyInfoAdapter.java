package com.gjf.lovezzu.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.OrderBuyInfoAcivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuDetialActivity;
import com.gjf.lovezzu.entity.Goods;
import com.gjf.lovezzu.entity.OrderDataBridging;
import com.gjf.lovezzu.entity.OrderDataResult;

import java.util.ArrayList;
import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/7.
 */

public class OrderBuyInfoAdapter extends RecyclerView.Adapter<OrderBuyInfoAdapter.ViewHolder> {

    private List<OrderDataResult> orderDataResults=new ArrayList<>();
    private String phone;
    public OrderBuyInfoAdapter (OrderDataBridging orderDataBridging){
        orderDataResults=orderDataBridging.getUgoods();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_buy_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.buy_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDataResult dataResult=orderDataResults.get(holder.getAdapterPosition());
                phone=dataResult.getUserinfo().getPhone();
                if (ContextCompat.checkSelfPermission(OrderBuyInfoAcivity.orderBuyInfoAcivity, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(OrderBuyInfoAcivity.orderBuyInfoAcivity,new String[]{Manifest.permission.CALL_PHONE},1);
                }else {
                    call();
                }


            }
        });
        holder.goods_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDataResult result=orderDataResults.get(holder.getAdapterPosition());
                String url=result.getGoods().getGimage();
                String images[]=url.split("ZZU");
                Goods goods=new Goods();
                goods.setGprice(result.getGoods().getGprice());
                goods.setImages(images);
                goods.setGname(result.getGoods().getGname());
                goods.setGdescribe(result.getGoods().getGdescribe());
                goods.setGoods_id(result.getGoods().getGoods_id());
                goods.setGcampus(result.getGoods().getGcampus());
                Intent intent=new Intent(OrderBuyInfoAcivity.orderBuyInfoAcivity, TaoyuDetialActivity.class);
                intent.putExtra("goods",goods);
                OrderBuyInfoAcivity.orderBuyInfoAcivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            OrderDataResult orderDataResult=orderDataResults.get(position);
            String url=orderDataResult.getGoods().getGimage();
            String images[]=url.split("ZZU");
        Glide.with(OrderBuyInfoAcivity.orderBuyInfoAcivity)
                .load(LOGIN_URL + "filedownload?action=商品&imageURL=" + images[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.goods_image);
        holder.goods_name.setText(orderDataResult.getGoods().getGname());
        holder.goods_price.setText(orderDataResult.getGoods().getGprice());
        holder.buy_count.setText(orderDataResult.getCount()+"");
        holder.buy_total.setText(orderDataResult.getTotal()+"");
    }

    @Override
    public int getItemCount() {
        return orderDataResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout goods_body;
        ImageView goods_image;
        TextView goods_name;
        TextView goods_price;
        TextView buy_count;
        TextView buy_total;
        CardView buy_call;
        public ViewHolder(View itemView) {
            super(itemView);
            goods_body= (LinearLayout) itemView.findViewById(R.id.buy_item_goods_body);
            goods_image= (ImageView) itemView.findViewById(R.id.buy_item_goods_image);
            goods_name= (TextView) itemView.findViewById(R.id.buy_item_goods_name);
            goods_price= (TextView) itemView.findViewById(R.id.buy_item_goods_price);
            buy_count= (TextView) itemView.findViewById(R.id.buy_item_count);
            buy_total= (TextView) itemView.findViewById(R.id.buy_item_total);
            buy_call= (CardView) itemView.findViewById(R.id.buy_item_call);
        }
    }

    public void call(){
        try {
            Intent intent1=new Intent(Intent.ACTION_CALL);
            intent1.setData(Uri.parse("tel:"+phone));
            OrderBuyInfoAcivity.orderBuyInfoAcivity.startActivity(intent1);
        }catch (SecurityException e){
            e.printStackTrace();
            Toast.makeText(OrderBuyInfoAcivity.orderBuyInfoAcivity,"号码格式不正确！",Toast.LENGTH_SHORT).show();
        }
    }



}
