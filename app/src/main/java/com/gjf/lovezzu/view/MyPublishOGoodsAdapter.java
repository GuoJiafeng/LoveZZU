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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.MyPublishGoodsActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuDetialActivity;
import com.gjf.lovezzu.entity.taoyu.Goods;
import com.gjf.lovezzu.entity.taoyu.TaoyuGoodsResult;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/7.
 */

public class MyPublishOGoodsAdapter extends RecyclerView.Adapter<MyPublishOGoodsAdapter.ViewHolder> {

    private List<TaoyuGoodsResult> taoyuGoodsResultList;

    public MyPublishOGoodsAdapter(List<TaoyuGoodsResult> taoyuGoodsResults) {
        taoyuGoodsResultList=taoyuGoodsResults;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_publish_goods_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.goods_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaoyuGoodsResult goodsResult=taoyuGoodsResultList.get(holder.getAdapterPosition());
                Intent intent=new Intent(MyPublishGoodsActivity.myPublishGoodsActivity, TaoyuDetialActivity.class);
                Goods goods=new Goods();
                String goodsImagesURL = goodsResult.getGimage();
                String imagesURL[] = goodsImagesURL.split("ZZU");
                goods.setGcampus(goodsResult.getGcampus());
                goods.setGoods_id(goodsResult.getGoods_id());
                goods.setGdescribe(goodsResult.getGdescribe());
                goods.setGname(goodsResult.getGname());
                goods.setImages(imagesURL);
                goods.setGprice(goodsResult.getGprice());
                intent.putExtra("goods", goods);
                MyPublishGoodsActivity.myPublishGoodsActivity.startActivity(intent);
            }
        });
        holder.goods_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(MyPublishGoodsActivity.myPublishGoodsActivity);
                builder.setTitle("删除发布商品");
                builder.setMessage("将从用户购物车和购买的订单中移除该商品！");
                builder.setCancelable(true);
                builder.setPositiveButton("狠心删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaoyuGoodsResult result=taoyuGoodsResultList.get(holder.getAdapterPosition());
                        String id=result.getGoods_id()+"";
                        deleteGoods(id);
                    }
                });
                builder.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaoyuGoodsResult taoyuGoodsResult=taoyuGoodsResultList.get(position);
        holder.goods_type.setText(taoyuGoodsResult.getGtype());
        holder.goods_name.setText(taoyuGoodsResult.getGname());
        holder.goods_price.setText(taoyuGoodsResult.getGprice());
        holder.goods_desc.setText(taoyuGoodsResult.getGdescribe());
        String url=taoyuGoodsResult.getGimage();
        String images[]=url.split("ZZU");
        Glide.with(MyPublishGoodsActivity.myPublishGoodsActivity)
                .load(LOGIN_URL+"filedownload?action=商品&imageURL="+images[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.goods_image);
    }

    @Override
    public int getItemCount() {
        return taoyuGoodsResultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView goods_type;
        TextView goods_delete;
        LinearLayout goods_body;
        ImageView goods_image;
        TextView goods_name;
        TextView goods_desc;
        TextView goods_price;

        public ViewHolder(View itemView) {
            super(itemView);
            goods_type= (TextView) itemView.findViewById(R.id.my_publish_goods_type);
            goods_delete= (TextView) itemView.findViewById(R.id.my_publish_delete);
            goods_body= (LinearLayout) itemView.findViewById(R.id.my_publish_goods_body);
            goods_image= (ImageView) itemView.findViewById(R.id.my_publish_goods_image);
            goods_name= (TextView) itemView.findViewById(R.id.my_publish_goods_name);
            goods_desc= (TextView) itemView.findViewById(R.id.my_publish_goods_desc);
            goods_price= (TextView) itemView.findViewById(R.id.my_publish_goods_price);

        }
    }

    private void deleteGoods(String goods_id){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"querygoodsAction");

        requestParams.addBodyParameter("Goods_id",goods_id);
        requestParams.addBodyParameter("action","删除商品");

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(MyPublishGoodsActivity.myPublishGoodsActivity,"删除成功!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MyPublishGoodsActivity.myPublishGoodsActivity,"删除失败!请保持网络通畅",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

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

}
