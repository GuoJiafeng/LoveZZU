package com.gjf.lovezzu.view;

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
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.entity.topic.TopicThemeBridging;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicThemeAdapter extends RecyclerView.Adapter<TopicThemeAdapter.ViewHolder> {

    private List<TopicThemeBridging> topicThemeBridgingList;
    private Integer themeId;
    public TopicThemeAdapter(List<TopicThemeBridging> list) {
        topicThemeBridgingList=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_first_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.theme_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeId=topicThemeBridgingList.get(holder.getAdapterPosition()).getTheme().getThemeId();
                Log.e("话题=========",themeId+"?id");
                TopicTalkActivity.topicTalkActivity.getTopics();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopicThemeBridging bridging=topicThemeBridgingList.get(position);
        holder.theme_title.setText(bridging.getTheme().getThemeTitle());
        holder.theme_count.setText(bridging.getTheme().getTopicCount()+"");
        holder.user_name.setText(bridging.getUserinfo().getNickname());
        Glide.with(TopicTalkActivity.topicTalkActivity)
                .load(LOGIN_URL + "filedownload?action=话题圈&imageURL=" +bridging.getTheme().getThemeImg())
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.theme_image);

    }

    @Override
    public int getItemCount() {
        return topicThemeBridgingList.size();
    }

    public Integer getTheme(){
        return  themeId;
    }
    public void setTheme(Integer id){
        themeId=id;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView theme_title;
        ImageView theme_image;
        TextView user_name;
        TextView theme_count;

        public ViewHolder(View itemView) {
            super(itemView);
            theme_title= (TextView) itemView.findViewById(R.id.topic_theme_title);
            theme_image= (ImageView) itemView.findViewById(R.id.topic_theme_image);
            user_name= (TextView) itemView.findViewById(R.id.topic_theme_user_name);
            theme_count= (TextView) itemView.findViewById(R.id.topic_theme_count);
        }
    }

}
