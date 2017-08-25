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
import com.gjf.lovezzu.activity.palytogether.DynamicInfoActivity;
import com.gjf.lovezzu.activity.palytogether.GroupDynamicActivity;
import com.gjf.lovezzu.entity.playtogether.DynamicImages;

import java.util.List;

/**
 * Created by lenovo047 on 2017/8/24.
 */

public class DynamicImagesAdapter extends RecyclerView.Adapter<DynamicImagesAdapter.ViewHolder> {
    private List<DynamicImages> dynamicImagesList;

    public DynamicImagesAdapter(List<DynamicImages> dynamicImagesList) {
        this.dynamicImagesList = dynamicImagesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final  View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamicimage_item,parent,false);
        final  ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicImages dynamicImages= dynamicImagesList.get(viewHolder.getAdapterPosition());
                final Dialog dialog = new Dialog(DynamicInfoActivity.dynamicInfoActivity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                ImageView imageView=new ImageView(DynamicInfoActivity.dynamicInfoActivity);
                imageView.setLayoutParams( new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT));
                Glide.with(DynamicInfoActivity.dynamicInfoActivity)
                         .load(dynamicImages.getDynamicImages())
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
        DynamicImages dynamicImages = dynamicImagesList.get(position);
        Glide.with(DynamicInfoActivity.dynamicInfoActivity)
                .load(dynamicImages.getDynamicImages())
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.images);


    }

    @Override
    public int getItemCount() {
        return dynamicImagesList.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
                  ImageView images;
        public ViewHolder(View itemView) {
            super(itemView);
            images= (ImageView) itemView.findViewById(R.id.dynamic_talkimage);
        }
    }
}
