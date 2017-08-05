package com.gjf.lovezzu.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.activity.tapictalk.TopicInfoActivity;
import com.gjf.lovezzu.entity.TopicMid;

import java.util.List;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicMidAdapter extends RecyclerView.Adapter<TopicMidAdapter.ViewHolder> {


    public TopicMidAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_first_item, parent, false);
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


        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

}
