package com.gjf.lovezzu.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.TopicInfoActivity;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.entity.topic.TopicDataBridging;

import java.util.ArrayList;
import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicTopicAdapter extends RecyclerView.Adapter<TopicTopicAdapter.ViewHolder> {

    private List<TopicDataBridging> topicDataBridgingList=new ArrayList<>();

    public TopicTopicAdapter(List<TopicDataBridging> list) {
            topicDataBridgingList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_last_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopicDataBridging bridging=topicDataBridgingList.get(position);
/*
            holder.comm_num.setText(bridging.getTopic().getTopicCommentCount()+"null");
            holder.content.setText(bridging.getTopic().getTopicText());
            holder.zan_num.setText(bridging.getTopic().getTopicThumbCount()+"");
            holder.title.setText(bridging.getTopic().getTopicTitle());
            holder.topic_date.setText(bridging.getTopic().getDate());
            String url=bridging.getTopic().getTopicImg();
            String iamges[]=url.split("ZZU");
            Glide.with(TopicTalkActivity.topicTalkActivity)
                    .load(LOGIN_URL + "filedownload?action=话题圈&imageURL=" +iamges[0])
                    .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(holder.topic_image);*/


    }

    @Override
    public int getItemCount() {
        return topicDataBridgingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView zan;
        TextView zan_num;
        ImageView comm;
        TextView comm_num;
        ImageView topic_image;
        TextView topic_date;
        public ViewHolder(View itemView) {
            super(itemView);
            topic_image= (ImageView) itemView.findViewById(R.id.topic_last_image);
            title = (TextView) itemView.findViewById(R.id.topic_last_title);
            content = (TextView) itemView.findViewById(R.id.topic_last_content);
            topic_date= (TextView) itemView.findViewById(R.id.topic_last_date);
            zan= (ImageView) itemView.findViewById(R.id.topic_last_zan);
            zan_num= (TextView) itemView.findViewById(R.id.topic_last_zan_num);
            comm= (ImageView) itemView.findViewById(R.id.topic_last_comm);
            comm_num= (TextView) itemView.findViewById(R.id.topic_last_comm_num);

        }
    }


    private void addThum(){

    }

}
