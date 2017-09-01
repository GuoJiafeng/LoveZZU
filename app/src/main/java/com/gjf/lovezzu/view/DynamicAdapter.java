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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.palytogether.DynamicInfoActivity;
import com.gjf.lovezzu.activity.palytogether.GroupDynamicActivity;
import com.gjf.lovezzu.entity.playtogether.GroupDynamicResult;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.gjf.lovezzu.R.id.url;
import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by lenovo047 on 2017/8/22.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {
    private List<GroupDynamicResult> groupDynamicResultList;
    private String  SessionId;

    public DynamicAdapter(List<GroupDynamicResult> groupDynamicResultList) {
        this.groupDynamicResultList = groupDynamicResultList;
        SharedPreferences sharedPreferences=GroupDynamicActivity.groupDynamicActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionId=sharedPreferences.getString("SessionID","");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.dynamicinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GroupDynamicActivity.groupDynamicActivity, DynamicInfoActivity.class);
                GroupDynamicResult groupDynamicResult=groupDynamicResultList.get(holder.getAdapterPosition());
                intent.putExtra("dynamicId",groupDynamicResult);
                GroupDynamicActivity.groupDynamicActivity.startActivity(intent);
            }
        });
        holder.dynamic_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GroupDynamicActivity.groupDynamicActivity, DynamicInfoActivity.class);
                GroupDynamicResult groupDynamicResult=groupDynamicResultList.get(holder.getAdapterPosition());
                intent.putExtra("dynamicId",groupDynamicResult);
                GroupDynamicActivity.groupDynamicActivity.startActivity(intent);
            }
        });
        holder.dynamic_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDynamicResult groupDynamicResult = groupDynamicResultList.get(holder.getAdapterPosition());
                if (groupDynamicResult.getGroupDynamic().getThembed()){
                    Toast.makeText(GroupDynamicActivity.groupDynamicActivity,"已经赞过了！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GroupDynamicActivity.groupDynamicActivity,"+1", Toast.LENGTH_SHORT).show();
                    Integer zan= Integer.parseInt(groupDynamicResult.getGroupDynamic().getThembCount()+"");
                    holder.dynamicZan.setText((zan +1)+"");
                    addThum(groupDynamicResult.getGroupDynamic().getDynamicId().toString()+"");
                }

            }
        });
        holder.dynamicZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDynamicResult groupDynamicResult = groupDynamicResultList.get(holder.getAdapterPosition());
                if (groupDynamicResult.getGroupDynamic().getThembed()){
                    Toast.makeText(GroupDynamicActivity.groupDynamicActivity,"已经赞过了！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GroupDynamicActivity.groupDynamicActivity,"+1", Toast.LENGTH_SHORT).show();
                    Integer zan= Integer.parseInt(groupDynamicResult.getGroupDynamic().getThembCount()+"");
                    holder.dynamicZan.setText((zan +1)+"");
                    holder.dynamic_zan.setImageResource(R.drawable.life_zan_done);
                    holder.dynamicZan.setTextColor(Color.parseColor("#F48F0B"));
                    addThum(groupDynamicResult.getGroupDynamic().getDynamicId().toString()+"");
                }
            }
        });
    return  holder;
    }

    private void addThum(String groupId) {
        RequestParams requestParams= new RequestParams(LOGIN_URL+"DynamicCommentAction");
        requestParams.addBodyParameter("dynamicId",groupId);
        requestParams.addBodyParameter("SessionID",SessionId);
        requestParams.addBodyParameter("action","动态点赞");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("动态==点赞",result);
                try {
                    JSONObject json = new JSONObject(result);
                   boolean res = json.getBoolean("isSuccessful");
                    if (!res) {
                        Toast.makeText(GroupDynamicActivity.groupDynamicActivity, "已经点过赞了！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(GroupDynamicActivity.groupDynamicActivity, "请检查网络！", Toast.LENGTH_SHORT).show();
                Log.e("动态==点赞",ex.getMessage());

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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroupDynamicResult grouodynamic= groupDynamicResultList.get(position);
        Log.e("动态===",grouodynamic.getUserinfo().getImageUrl());
        Glide.with(GroupDynamicActivity.groupDynamicActivity)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + grouodynamic.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.dynamicImg);
        holder.dynamicZan.setText(grouodynamic.getGroupDynamic().getThembCount()+"");
        holder.dynamicCommentnum.setText(grouodynamic.getGroupDynamic().getCommentCount()+"");
        holder.dynamicContent.setText(grouodynamic.getGroupDynamic().getTalk());
        holder.dynamicDate.setText(grouodynamic.getGroupDynamic().getDate());
        holder.dynamicUsername.setText(grouodynamic.getUserinfo().getNickname());
        if (grouodynamic.getGroupDynamic().getThembed()){
            holder.dynamic_zan.setImageResource(R.drawable.life_zan_done);
            holder.dynamicZan.setTextColor(Color.parseColor("#F48F0B"));
        }else {
            holder.dynamic_zan.setImageResource(R.drawable.life_zan);
            holder.dynamicZan.setTextColor(Color.parseColor("#757575"));
        }

        String images=grouodynamic.getGroupDynamic().getTalkImg();
        String url[]=images.split("ZZU");
        String imageUrl=url[0];
        Glide.with(GroupDynamicActivity.groupDynamicActivity)
                .load(LOGIN_URL + "filedownload?action=一起玩&imageURL=" +imageUrl)
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.dynamic_talkimg);

    }

    @Override
    public int getItemCount() {
        return groupDynamicResultList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView dynamicImg;
        TextView dynamicUsername;
        TextView dynamicDate;
        TextView dynamicContent;
        TextView dynamicZan;
        TextView dynamicCommentnum;
        LinearLayout dynamicinfoButton;
        ImageView dynamic_talkimg;
        ImageView dynamic_zan;
        ImageView dynamic_comm;
        public ViewHolder(View itemView) {
            super(itemView);
            dynamicImg= (CircleImageView) itemView.findViewById(R.id.dynamic_img);
            dynamicUsername= (TextView) itemView.findViewById(R.id.dynamic_username);
            dynamicDate= (TextView) itemView.findViewById(R.id.dynamic_date);
            dynamicContent= (TextView) itemView.findViewById(R.id.dynamic_content);
            dynamicZan= (TextView) itemView.findViewById(R.id.dynamic_zan);
            dynamicCommentnum= (TextView) itemView.findViewById(R.id.dynamic_commentnum);
            dynamicinfoButton= (LinearLayout) itemView.findViewById(R.id.dynamicinfo_button);
            dynamic_talkimg= (ImageView) itemView.findViewById(R.id.dynamic_talk_img);
            dynamic_zan= (ImageView) itemView.findViewById(R.id.item_dynamic_zan);
            dynamic_comm= (ImageView) itemView.findViewById(R.id.item_dynamic_comm);
        }
    }
}
