package com.gjf.lovezzu.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.activity.tapictalk.topic2Activity;
import com.gjf.lovezzu.entity.TopicLast;

import java.util.List;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicLastAdapter extends RecyclerView.Adapter<TopicLastAdapter.ViewHolder> {

    private List<TopicLast> topicLasts;
    private TopicLast topicLast;
    private Context mContext;

    public TopicLastAdapter(List<TopicLast> topicLasts, Context context) {
        this.topicLasts = topicLasts;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_last_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TopicTalkActivity.topicTalkActivity,topic2Activity.class);

                TopicTalkActivity.topicTalkActivity.startActivity(intent);
            }
        });
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TopicTalkActivity.topicTalkActivity,topic2Activity.class);

                TopicTalkActivity.topicTalkActivity.startActivity(intent);
            }
        });
        holder.talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "进入评论", Toast.LENGTH_SHORT).show();
            }
        });
        holder.starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "关注", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        topicLast = topicLasts.get(position);
        holder.title.setText(topicLast.getTitle());
        holder.content.setText(topicLast.getContent());
        holder.start.setText(topicLast.getStart());
        holder.talk.setText(topicLast.getTalk());
    }

    @Override
    public int getItemCount() {
        return topicLasts.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView start;
        TextView talk;
        TextView starting;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.topic_last_title);
            content = (TextView) itemView.findViewById(R.id.topic_last_content);
            start = (TextView) itemView.findViewById(R.id.topic_last_start);
            talk = (TextView) itemView.findViewById(R.id.topic_last_talk);
            starting = (TextView) itemView.findViewById(R.id.topic_last_starting);
        }
    }

}
