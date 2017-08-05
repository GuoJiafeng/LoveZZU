package com.gjf.lovezzu.view;

import android.app.Activity;
import android.content.Context;
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
import com.gjf.lovezzu.entity.SocietyNewsResult;
import com.gjf.lovezzu.network.HttpClientUtils;

import java.util.List;

/**
 * Created by zhao on 2017/3/15.
 */

public class SocietyAdapter extends RecyclerView.Adapter<SocietyAdapter.ViewHolder> {




    public SocietyAdapter() {

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.society_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout news_body;
        ImageView news_images;
        TextView news_title;
        TextView news_content;
        TextView news_time;

        public ViewHolder(View itemView) {
            super(itemView);
            news_body= (LinearLayout) itemView.findViewById(R.id.news_body);
            news_images= (ImageView) itemView.findViewById(R.id.news_image);
            news_title= (TextView) itemView.findViewById(R.id.news_title);
            news_content= (TextView) itemView.findViewById(R.id.news_desc);
            news_time= (TextView) itemView.findViewById(R.id.news_time);


        }
    }



}
