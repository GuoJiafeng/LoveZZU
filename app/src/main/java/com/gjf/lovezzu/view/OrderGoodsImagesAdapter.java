package com.gjf.lovezzu.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.OrderBuyInfoAcivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuOrderActivity;
import com.gjf.lovezzu.entity.OrderDataBridging;
import com.gjf.lovezzu.entity.OrderGoodsImagesItem;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderGoodsImagesAdapter extends RecyclerView.Adapter<OrderGoodsImagesAdapter.ViewHolder>{

    private List<OrderGoodsImagesItem> orderGoodsImagesItemList;
    private OrderDataBridging orderDataBridging;
    public OrderGoodsImagesAdapter(List<OrderGoodsImagesItem> orderGoodsImagesItems, OrderDataBridging orderdataBridging1){
        orderGoodsImagesItemList=orderGoodsImagesItems;
        orderDataBridging=orderdataBridging1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_goods_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaoyuOrderActivity.taoyuOrderActivity,orderDataBridging.getOrderdata().getAddress(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(TaoyuOrderActivity.taoyuOrderActivity, OrderBuyInfoAcivity.class);
                intent.putExtra("orderDataBridging",orderDataBridging);
                TaoyuOrderActivity.taoyuOrderActivity.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderGoodsImagesItem orderGoodsImagesItem=orderGoodsImagesItemList.get(position);
        Glide.with(TaoyuOrderActivity.taoyuOrderActivity)
                .load(LOGIN_URL + "filedownload?action=商品&imageURL=" +orderGoodsImagesItem.getImages())
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.imageView);
        holder.textView.setText(orderGoodsImagesItem.getName());
    }

    @Override
    public int getItemCount() {
        return orderGoodsImagesItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.order_goods_images_image);
            textView= (TextView) itemView.findViewById(R.id.order_goods_images_name);
        }
    }
}
