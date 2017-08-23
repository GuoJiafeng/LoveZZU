package com.gjf.lovezzu.view;

import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.topictalk.TopicInfoActivity;
import com.gjf.lovezzu.entity.taoyu.GoodsImages;

import java.util.List;

/**
 * Created by zhao on 2017/8/15.
 */

public class TopicInfoImagesAdapter extends RecyclerView.Adapter<TopicInfoImagesAdapter.ViewHolder>{
    private List<GoodsImages> photos;

    public TopicInfoImagesAdapter(List<GoodsImages> images){
        photos=images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.taoyu_goods_list_item1,parent,false);
        final TopicInfoImagesAdapter.ViewHolder viewHolder=new TopicInfoImagesAdapter.ViewHolder(view);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsImages goodsImages=photos.get(viewHolder.getAdapterPosition());
                final Dialog dialog = new Dialog(TopicInfoActivity.topicInfoActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                ImageView imageView= new ImageView(TopicInfoActivity.topicInfoActivity);
                imageView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                Glide.with(TopicInfoActivity.topicInfoActivity)
                        .load(goodsImages.getName())
                        .error(R.drawable.__picker_ic_broken_image_black_48dp)
                        .into(imageView);
                dialog.setContentView(imageView);
                dialog.show();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsImages goodsImages=photos.get(position);

        Glide.with(TopicInfoActivity.topicInfoActivity)
                .load(goodsImages.getName())
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.goods_images_item);
        }
    }
}
