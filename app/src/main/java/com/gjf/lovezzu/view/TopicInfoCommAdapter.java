package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.TopicInfoActivity;
import com.gjf.lovezzu.entity.topic.TopicCommBridging;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;


/**
 * Created by lenovo047 on 2017/5/27.
 */

public class TopicInfoCommAdapter extends  RecyclerView.Adapter<TopicInfoCommAdapter.ViewHolder>{

    private List<TopicCommBridging> commBridgings;

    public TopicInfoCommAdapter(List<TopicCommBridging> list){
        commBridgings=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_info_comm,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopicCommBridging topicCommBridging=commBridgings.get(position);
        Glide.with(TopicInfoActivity.topicInfoActivity)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + topicCommBridging.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.userImage);
        holder.commTime.setText(topicCommBridging.getTopiccomment().getDate());
        holder.commText.setText(topicCommBridging.getTopiccomment().getTopicComment());
        holder.userName.setText(topicCommBridging.getUserinfo().getNickname());
    }

    @Override
    public int getItemCount() {
        return commBridgings.size();
    }

    static  class  ViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView userImage;
        TextView userName;
        TextView commText;
        TextView commTime;
      public ViewHolder(View view) {
          super(view);
          userImage= (CircleImageView) view.findViewById(R.id.topic_info_comm_user_image);
          userName= (TextView) view.findViewById(R.id.topic_info_comm_user_name);
          commText= (TextView) view.findViewById(R.id.topic_info_comm_text);
          commTime= (TextView) view.findViewById(R.id.topic_info_comm_time);
      }
  }


}
