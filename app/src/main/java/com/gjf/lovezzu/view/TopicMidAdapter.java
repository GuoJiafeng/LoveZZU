package com.gjf.lovezzu.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.activity.tapictalk.topic2Activity;
import com.gjf.lovezzu.entity.TopicMid;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicMidAdapter extends RecyclerView.Adapter<TopicMidAdapter.ViewHolder> {

    private List<TopicMid> topicMidList;
    private TopicMid topicMid;
    private Context mContext;

    public TopicMidAdapter(List<TopicMid> topicMidList, Context mContext) {
        this.topicMidList = topicMidList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_mid_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TopicTalkActivity.topicTalkActivity,topic2Activity.class);

                 TopicTalkActivity.topicTalkActivity.startActivity(intent);
                //点击事件
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        topicMid = topicMidList.get(position);
        holder.imageView.setImageResource(topicMid.getImageId());
       /* Glide.with(mContext)
                .load(topicMid.getUrl())
                .centerCrop().thumbnail(0.1f)
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.imageView);*/
        holder.textView.setText(topicMid.getTitle());
    }

    @Override
    public int getItemCount() {
        return topicMidList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.topic_mid_image);
            textView = (TextView) itemView.findViewById(R.id.topic_mid_title);
        }
    }

}
