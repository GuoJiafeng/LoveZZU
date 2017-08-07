package com.gjf.lovezzu.view;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.OrderSellInfoActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuOrderActivity;
import com.gjf.lovezzu.entity.OrderSellDataBridging;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderSellAdapter extends RecyclerView.Adapter<OrderSellAdapter.ViewHolder>{


    private List<OrderSellDataBridging> orderSellDataBridgings;

    public OrderSellAdapter(List<OrderSellDataBridging> orderSellDataBridgingList){
        orderSellDataBridgings=orderSellDataBridgingList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler_sell_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.sell_goods_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSellDataBridging orderSellDataBridging1=orderSellDataBridgings.get(holder.getAdapterPosition());
                Intent intent=new Intent(TaoyuOrderActivity.taoyuOrderActivity, OrderSellInfoActivity.class);
                intent.putExtra("orderSellDataBridging",orderSellDataBridging1);
                TaoyuOrderActivity.taoyuOrderActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderSellDataBridging orderSellDataBridging=orderSellDataBridgings.get(position);
        holder.sell_user_name.setText(orderSellDataBridging.getOrder().getName());
        holder.sell_order_status.setText(orderSellDataBridging.getOrder().getOrder_status());
        holder.sell_goods_name.setText(orderSellDataBridging.getGoods().getGname());
        holder.sell_goods_price.setText(orderSellDataBridging.getGoods().getGprice());
        holder.sell_order_time.setText(orderSellDataBridging.getOrder().getOrder_date());
        String images=orderSellDataBridging.getGoods().getGimage();
        String image[]=images.split("ZZU");
        Glide.with(TaoyuOrderActivity.taoyuOrderActivity)
                .load(LOGIN_URL + "filedownload?action=商品&imageURL=" + image[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.sell_goods_image);

    }

    @Override
    public int getItemCount() {
        return orderSellDataBridgings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sell_user_name;
        TextView sell_order_status;
        LinearLayout sell_goods_body;
        ImageView sell_goods_image;
        TextView sell_goods_name;
        TextView sell_goods_price;
        TextView sell_order_time;

        public ViewHolder(View itemView) {
            super(itemView);
            sell_user_name= (TextView) itemView.findViewById(R.id.order_sell_user_name);
            sell_order_status= (TextView) itemView.findViewById(R.id.order_sell_status);
            sell_goods_body= (LinearLayout) itemView.findViewById(R.id.order_sell_goods_body);
            sell_goods_image= (ImageView) itemView.findViewById(R.id.order_sell_goods_image);
            sell_goods_name= (TextView) itemView.findViewById(R.id.order_sell_goods_name);
            sell_goods_price= (TextView) itemView.findViewById(R.id.order_sell_goods_price);
            sell_order_time= (TextView) itemView.findViewById(R.id.order_sell_time);
        }
    }
}
