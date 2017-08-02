package com.gjf.lovezzu.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.ShopcartActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuDetialActivity;
import com.gjf.lovezzu.entity.Goods;
import com.gjf.lovezzu.entity.ShoppingCartDateBridging;
import com.gjf.lovezzu.entity.TaoyuGoodsResult;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;


/**
 * Created by Leon on 2017/7/30.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<ShoppingCartDateBridging> shoppingCartDateBridgings;
    private ShoppingCartDateBridging shoppingCartDateBridging;
    private Goods goods;
    public ShoppingCartAdapter(List<ShoppingCartDateBridging> shoppingCartDateBridgingList) {
        shoppingCartDateBridgings=shoppingCartDateBridgingList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
         final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.goods_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(ShopcartActivity.shopcartActivity);
                builder.setTitle("删除商品");
                builder.setMessage("确定要删除"+viewHolder.goods_name.getText()+"吗?");
                builder.setCancelable(true);
                builder.setPositiveButton("狠心删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除购物车商品
                        deleteGoods();
                    }
                });
               builder.show();
            }
        });

        viewHolder.goods_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopcartActivity.shopcartActivity, TaoyuDetialActivity.class);
                ShoppingCartDateBridging shoppingCart=shoppingCartDateBridgings.get(viewHolder.getAdapterPosition());
                String goodsImagesURL=shoppingCart.getGoodsResult().getGimage();
                String imagesURL[]=goodsImagesURL.split("ZZU");
                goods.setGcampus(shoppingCart.getGoodsResult().getGcampus());
                goods.setGoods_id(shoppingCart.getGoodsResult().getGoods_id());
                goods.setGdescribe(shoppingCart.getGoodsResult().getGdescribe());
                goods.setGname(shoppingCart.getGoodsResult().getGname());
                goods.setImages(imagesURL);
                goods.setGprice(shoppingCart.getGoodsResult().getGprice());
                intent.putExtra("goods",goods);
                ShopcartActivity.shopcartActivity.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String goodsImagesURL=shoppingCartDateBridging.getGoodsResult().getGimage();
        String imagesURL[]=goodsImagesURL.split("ZZU");
        shoppingCartDateBridging=shoppingCartDateBridgings.get(position);
        holder.goods_type.setText(shoppingCartDateBridging.getGoodsResult().getGtype());
        Glide.with(ShopcartActivity.shopcartActivity)
                .load(LOGIN_URL+"filedownload?action=商品&imageURL="+imagesURL[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.goods_image);
        holder.goods_name.setText(shoppingCartDateBridging.getGoodsResult().getGname());
        holder.goods_desc.setText(shoppingCartDateBridging.getGoodsResult().getGdescribe());
        holder.goods_price.setText(shoppingCartDateBridging.getGoodsResult().getGprice());

    }

    private void deleteGoods(){

    }


    @Override
    public int getItemCount() {
        return shoppingCartDateBridgings.size();
    }


    static class  ViewHolder extends RecyclerView.ViewHolder{
        TextView goods_type;
        TextView goods_delete;
        ImageView goods_image;
        TextView goods_name;
        TextView goods_desc;
        TextView goods_price;
        LinearLayout goods_body;
        public ViewHolder(View view) {
            super(view);
            goods_body= (LinearLayout) itemView.findViewById(R.id.cart_goods_body);
            goods_type= (TextView) itemView.findViewById(R.id.shop_car_goods_type);
            goods_delete= (TextView) itemView.findViewById(R.id.shop_car_delete);
            goods_image= (ImageView) itemView.findViewById(R.id.cart_goods_image);
            goods_name= (TextView) itemView.findViewById(R.id.cart_goods_name);
            goods_desc= (TextView) itemView.findViewById(R.id.cart_goods_desc);
            goods_price= (TextView) itemView.findViewById(R.id.cart_goods_price);
        }
    }



}
