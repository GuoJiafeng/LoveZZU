package com.gjf.lovezzu.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.gjf.lovezzu.activity.palytogether.GroupDynamicActivity;
import com.gjf.lovezzu.activity.topictalk.TopicInfoActivity;
import com.gjf.lovezzu.activity.topictalk.TopicTalkActivity;
import com.gjf.lovezzu.entity.topic.TopicDataBridging;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicTopicAdapter extends RecyclerView.Adapter<TopicTopicAdapter.ViewHolder> {

    private List<TopicDataBridging> topicDataBridgingList=new ArrayList<>();
    private String SessionID;

    public TopicTopicAdapter(List<TopicDataBridging> list) {
            topicDataBridgingList=list;
        SharedPreferences sharedPreferences= TopicTalkActivity.topicTalkActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_last_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.topic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TopicTalkActivity.topicTalkActivity,TopicInfoActivity.class);
                TopicDataBridging bridging=topicDataBridgingList.get(holder.getAdapterPosition());
                intent.putExtra("topicInfo",bridging);
                intent.putExtra("zanNum",holder.zan_num.getText().toString());
                TopicTalkActivity.topicTalkActivity.startActivity(intent);
            }
        });
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopicDataBridging bridging=topicDataBridgingList.get(holder.getAdapterPosition());
                Log.e("话题点赞",bridging.getTopic().getThembed()+"");
                if (!bridging.getTopic().getThembed()){
                        String topicId=bridging.getTopic().getTopicId().toString();
                        int zan=Integer.parseInt(holder.zan_num.getText().toString());
                        holder.zan_num.setText((zan+1)+"");
                        holder.zan.setImageResource(R.drawable.life_zan_done);
                        holder.zan_num.setTextColor(Color.parseColor("#F48F0B"));
                        addThum(topicId);
                }else {
                    Toast.makeText(TopicTalkActivity.topicTalkActivity,"已经点过赞了",Toast.LENGTH_SHORT).show();

                }

            }
        });
        holder.comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TopicTalkActivity.topicTalkActivity,TopicInfoActivity.class);
                TopicDataBridging bridging=topicDataBridgingList.get(holder.getAdapterPosition());
                intent.putExtra("topicInfo",bridging);
                TopicTalkActivity.topicTalkActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            TopicDataBridging bridging=topicDataBridgingList.get(position);
            holder.comm_num.setText(bridging.getTopic().getTopicCommentCount()+"");
            holder.content.setText(bridging.getTopic().getTopicText());
            holder.zan_num.setText(bridging.getTopic().getTopicThumbCount()+"");
            if (bridging.getTopic().getThembed()){
                holder.zan.setImageResource(R.drawable.life_zan_done);
                holder.zan_num.setTextColor(Color.parseColor("#F48F0B"));
            }else {
                holder.zan.setImageResource(R.drawable.life_zan);
                holder.zan_num.setTextColor(Color.parseColor("#757575"));
            }
            holder.title.setText(bridging.getTopic().getTopicTitle());
            holder.topic_date.setText(bridging.getTopic().getDate());
            String url=bridging.getTopic().getTopicImg();
            String iamges[]=url.split("ZZU");
            Glide.with(TopicTalkActivity.topicTalkActivity)
                    .load(LOGIN_URL + "filedownload?action=话题圈&imageURL=" +iamges[0])
                    .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(holder.topic_image);
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


    private void addThum(String topicId){

            RequestParams requestParams=new RequestParams(LOGIN_URL+"TopicCommentAction");
            requestParams.addBodyParameter("ThumbNum","1");
            requestParams.addBodyParameter("TopicId",topicId);
            requestParams.addBodyParameter("SessionID",SessionID);
            requestParams.addBodyParameter("action","话题点赞");
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("话题点赞",result);
                    try{
                        JSONObject jsonObject=new JSONObject(result);
                        Boolean res=jsonObject.getBoolean("isSuccessful");
                        if (res){
                            Toast.makeText(TopicTalkActivity.topicTalkActivity,"+1",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TopicTalkActivity.topicTalkActivity,"已经点过赞了！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("话题点赞",ex.getMessage());
                    Toast.makeText(TopicTalkActivity.topicTalkActivity,"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });

    }


}
