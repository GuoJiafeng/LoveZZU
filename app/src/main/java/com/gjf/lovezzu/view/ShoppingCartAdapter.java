package com.gjf.lovezzu.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.ShopcartActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuChildConmmentsActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuDetialActivity;
import com.gjf.lovezzu.entity.Goods;
import com.gjf.lovezzu.entity.ShoppingCartDateBridging;
import com.gjf.lovezzu.entity.TaoyuGoodsResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;


/**
 * Created by zhao on 2017/8/1.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<ShoppingCartDateBridging> shoppingCartDateBridgings;
    private ShoppingCartDateBridging shoppingCartDateBridging;
    private static Goods goods=new Goods();
    private Map<Integer, Boolean> map = new HashMap<>();
    private  static String SessionID;
    public ShoppingCartAdapter(List<ShoppingCartDateBridging> shoppingCartDateBridgingList) {
        shoppingCartDateBridgings=shoppingCartDateBridgingList;
        SharedPreferences sharedPreferences = ShopcartActivity.shopcartActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
        initMap();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        final Intent intent=new Intent(ShopcartActivity.shopcartActivity, TaoyuDetialActivity.class);

        viewHolder.goods_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1=View.inflate(ShopcartActivity.shopcartActivity,R.layout.userinfo_update_view,null);
                final EditText editText= (EditText) view1.findViewById(R.id.edituserinfo);
                editText.setText("1");
                final ShoppingCartDateBridging shoppingCartnum=shoppingCartDateBridgings.get(viewHolder.getAdapterPosition());
                new android.app.AlertDialog.Builder(ShopcartActivity.shopcartActivity).setMessage("购买数量：")
                        .setView(view1).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText()!=null) {
                            shoppingCartnum.setCount(editText.getText().toString());
                        }else {
                            shoppingCartnum.setCount("1");
                            Toast.makeText(ShopcartActivity.shopcartActivity,"默认+1！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
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
                        Goods goods1=new Goods();
                        ShoppingCartDateBridging shoppingCart=shoppingCartDateBridgings.get(viewHolder.getAdapterPosition());
                        String goodsImagesURL=shoppingCart.getGoods().getGimage();
                        String imagesURL[]=goodsImagesURL.split("ZZU");
                        goods1.setGcampus(shoppingCart.getGoods().getGcampus());
                        goods1.setGoods_id(shoppingCart.getGoods().getGoods_id());
                        goods1.setGdescribe(shoppingCart.getGoods().getGdescribe());
                        goods1.setGname(shoppingCart.getGoods().getGname());
                        goods1.setImages(imagesURL);
                        goods1.setGprice(shoppingCart.getGoods().getGprice());
                        deleteGoods(goods1);
                    }
                });
               builder.show();
            }
        });

        viewHolder.goods_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartDateBridging shoppingCart=shoppingCartDateBridgings.get(viewHolder.getAdapterPosition());
                String goodsImagesURL=shoppingCart.getGoods().getGimage();
                String imagesURL[]=goodsImagesURL.split("ZZU");
                goods.setGcampus(shoppingCart.getGoods().getGcampus());
                goods.setGoods_id(shoppingCart.getGoods().getGoods_id());
                goods.setGdescribe(shoppingCart.getGoods().getGdescribe());
                goods.setGname(shoppingCart.getGoods().getGname());
                goods.setImages(imagesURL);
                goods.setGprice(shoppingCart.getGoods().getGprice());
                intent.putExtra("goods",goods);
                ShopcartActivity.shopcartActivity.startActivity(intent);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        shoppingCartDateBridging=shoppingCartDateBridgings.get(position);
        String goodsImagesURL=shoppingCartDateBridging.getGoods().getGimage();
        String imagesURL[]=goodsImagesURL.split("ZZU");
        holder.goods_type.setText(shoppingCartDateBridging.getGoods().getGtype());
        Glide.with(ShopcartActivity.shopcartActivity)
                .load(LOGIN_URL+"filedownload?action=商品&imageURL="+imagesURL[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.goods_image);
        holder.goods_name.setText(shoppingCartDateBridging.getGoods().getGname());
        holder.goods_desc.setText(shoppingCartDateBridging.getGoods().getGdescribe());
        holder.goods_price.setText(shoppingCartDateBridging.getGoods().getGprice());
        holder.goods_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position, isChecked);
            }
        });
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.goods_check.setChecked(map.get(position));
    }

    private void deleteGoods(Goods goods1){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"cartAction");
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("goods_id",goods1.getGoods_id().toString());
        requestParams.addBodyParameter("count","1");
        requestParams.addBodyParameter("action","删除");

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ShopcartActivity.shopcartActivity,"删除成功！",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void initMap() {
        for (int i = 0; i < shoppingCartDateBridgings.size(); i++) {
            map.put(i, false);
        }
    }
    public Map<Integer, Boolean> getMap() {
        return map;
    }
    @Override
    public int getItemCount() {
        return shoppingCartDateBridgings.size();
    }


    static class  ViewHolder extends RecyclerView.ViewHolder{
        CheckBox goods_check;
        TextView goods_type;
        TextView goods_delete;
        ImageView goods_image;
        TextView goods_name;
        TextView goods_desc;
        TextView goods_price;
        TextView goods_count;
        LinearLayout goods_body;
        public ViewHolder(View view) {
            super(view);
            goods_check= (CheckBox) itemView.findViewById(R.id.cart_goods_add);
            goods_body= (LinearLayout) itemView.findViewById(R.id.cart_goods_body);
            goods_type= (TextView) itemView.findViewById(R.id.shop_car_goods_type);
            goods_delete= (TextView) itemView.findViewById(R.id.shop_car_delete);
            goods_count= (TextView) itemView.findViewById(R.id.shop_car_count);
            goods_image= (ImageView) itemView.findViewById(R.id.cart_goods_image);
            goods_name= (TextView) itemView.findViewById(R.id.cart_goods_name);
            goods_desc= (TextView) itemView.findViewById(R.id.cart_goods_desc);
            goods_price= (TextView) itemView.findViewById(R.id.cart_goods_price);
        }
    }



}
