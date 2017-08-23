package com.gjf.lovezzu.view;

import android.content.Intent;
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
    private String typeZan="1";
    public TopicTopicAdapter(List<TopicDataBridging> list) {
            topicDataBridgingList=list;
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
                TopicTalkActivity.topicTalkActivity.startActivity(intent);
            }
        });
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TopicDataBridging bridging=topicDataBridgingList.get(holder.getAdapterPosition());
                String topicId=bridging.getTopic().getTopicId().toString();
                int zan=Integer.parseInt(holder.zan_num.getText().toString());
                    if (typeZan.equals("1")){
                        holder.zan_num.setText((zan+1)+"");
                        addThum(topicId);
                    }else {
                        holder.zan_num.setText((zan-1)+"");
                        addThum(topicId);
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


    private boolean addThum(String topicId){
        boolean res=false;
            RequestParams requestParams=new RequestParams(LOGIN_URL+"TopicCommentAction");
            requestParams.addBodyParameter("ThumbNum",typeZan);
            requestParams.addBodyParameter("TopicId",topicId);
            requestParams.addBodyParameter("action","话题点赞");
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("话题点赞",result);
                    try{
                        JSONObject jsonObject=new JSONObject(result);
                        Boolean res=jsonObject.getBoolean("isSuccessful");
                        if (res){
                            res=true;
                            if (typeZan.equals("1")){
                                Toast.makeText(TopicTalkActivity.topicTalkActivity,"+1 再点一次取消点赞！",Toast.LENGTH_SHORT).show();
                                typeZan="0";
                            }else {
                                Toast.makeText(TopicTalkActivity.topicTalkActivity,"-1",Toast.LENGTH_SHORT).show();
                                typeZan="1";
                            }
                        }else {
                            res=false;
                            Toast.makeText(TopicTalkActivity.topicTalkActivity,"请重新登录!",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("话题点赞",ex.getMessage());
                    Toast.makeText(TopicTalkActivity.topicTalkActivity,"请重新登录并检查网络是否通畅!",Toast.LENGTH_SHORT).show();
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
        return res;
    }


}
