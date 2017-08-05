package com.gjf.lovezzu.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.TaoyuActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuDetialActivity;
import com.gjf.lovezzu.entity.Goods;
import com.gjf.lovezzu.entity.TaoyuDataBridging;
import com.gjf.lovezzu.network.DownloadIconMethods;

import java.util.List;

import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by lenovo047 on 2017/3/24.
 */

public class TaoyuAdapter extends RecyclerView.Adapter<TaoyuAdapter.ViewHolder> {
    private List<TaoyuDataBridging> taoyuResultList;
    private Context context;
    private TaoyuDataBridging taoyuResult;
    private LayoutInflater inflater;
    private Goods goods=new Goods();
    public TaoyuAdapter(List<TaoyuDataBridging> taoyuResultList, Context context) {
        this.taoyuResultList = taoyuResultList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taoyu, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.taoyuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TaoyuActivity.taoyuActivity, TaoyuDetialActivity.class);
                TaoyuDataBridging taoyuResultNew=taoyuResultList.get(holder.getAdapterPosition());
                String goodsImagesURL=taoyuResultNew.getGoods().getGimage();
                String imagesURL[]=goodsImagesURL.split("ZZU");
                goods.setGcampus(taoyuResultNew.getGoods().getGcampus());
                goods.setGoods_id(taoyuResultNew.getGoods().getGoods_id());
                goods.setGdescribe(taoyuResultNew.getGoods().getGdescribe());
                goods.setGname(taoyuResultNew.getGoods().getGname());
                goods.setImages(imagesURL);
                goods.setGprice(taoyuResultNew.getGoods().getGprice());
                intent.putExtra("goods",goods);
                TaoyuActivity.taoyuActivity.startActivity(intent);

            }
        });

        holder.comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TaoyuActivity.taoyuActivity, TaoyuDetialActivity.class);
                TaoyuDataBridging taoyuResultNew=taoyuResultList.get(holder.getAdapterPosition());
                String goodsImagesURL=taoyuResultNew.getGoods().getGimage();
                String imagesURL[]=goodsImagesURL.split("ZZU");
                goods.setGcampus(taoyuResultNew.getGoods().getGcampus());
                goods.setGoods_id(taoyuResultNew.getGoods().getGoods_id());
                goods.setGdescribe(taoyuResultNew.getGoods().getGdescribe());
                goods.setGname(taoyuResultNew.getGoods().getGname());
                goods.setImages(imagesURL);
                goods.setGprice(taoyuResultNew.getGoods().getGprice());
                intent.putExtra("goods",goods);
                Log.e("商品详情--------onCreateViewHolder",goods.getGoods_id()+"  点击位置"+holder.getAdapterPosition());
                TaoyuActivity.taoyuActivity.startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        taoyuResult = taoyuResultList.get(position);
        String goodsImagesURL=taoyuResult.getGoods().getGimage();
        String imagesURL[]=goodsImagesURL.split("ZZU");

        Glide.with(context)
                .load(LOGIN_URL+"filedownload?action=头像&imageURL="+taoyuResult.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.user_icon);

        Glide.with(context)
                .load(LOGIN_URL+"filedownload?action=商品&imageURL="+imagesURL[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.goods_img);
        holder.goods_comment.setText(taoyuResult.getGoods().getGcomments());
        holder.goods_position.setText(taoyuResult.getGoods().getGcampus());
        holder.good_price.setText(taoyuResult.getGoods().getGprice());
        holder.goods_name.setText(taoyuResult.getGoods().getGname());
        holder.user_nickname.setText(taoyuResult.getUserinfo().getNickname());



    }

    @Override
    public int getItemCount() {
        return taoyuResultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View taoyuView;
        ImageView user_icon;
        ImageView goods_img;
        ImageView comm;
        TextView user_nickname;
        TextView goods_name;
        TextView good_price;
        TextView goods_position;
        TextView goods_comment;


        public ViewHolder(View itemView) {
            super(itemView);
            taoyuView = itemView.findViewById(R.id.goods_info);
            user_icon = (ImageView) itemView.findViewById(R.id.goods_user_icon);
            goods_img = (ImageView) itemView.findViewById(R.id.goodsImg);
            user_nickname = (TextView) itemView.findViewById(R.id.goods_user_nickname);
            goods_name= (TextView) itemView.findViewById(R.id.goodsName);
            good_price = (TextView) itemView.findViewById(R.id.goodsPrice);
            goods_position = (TextView) itemView.findViewById(R.id.goodsPosition);
            goods_comment = (TextView) itemView.findViewById(R.id.goodsComment);
            comm= (ImageView) itemView.findViewById(R.id.comment_num);


        }
    }

}
