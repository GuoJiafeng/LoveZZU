package com.gjf.lovezzu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.palytogether.PlayGroupActivity;
import com.gjf.lovezzu.activity.palytogether.PlayTogetherActivity;
import com.gjf.lovezzu.entity.CommResult;
import com.gjf.lovezzu.entity.UserInfoResult;
import com.gjf.lovezzu.entity.playtogether.GroupDataBridging;
import com.gjf.lovezzu.network.GroupMethods;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhaox on 2017/4/9.
 */

public class PlayTogetherAdapter extends RecyclerView.Adapter<PlayTogetherAdapter.ViewHolder> {

    private List<GroupDataBridging> groupDataBridgingList;
    private  String SessionID;
    private  Subscriber subscriber;
    private String type;
    private  LinearLayoutManager layoutManager;
    private PlayGroupDynamicAdapter playGroupDynamicAdapter;
    private Context mContext;
    private  LinearLayoutManager layoutManager1;
    private List<UserInfoResult> userInfoResults=new ArrayList<>();
    private PlayGroupUserAdapter playGroupUserAdapter;
    public PlayTogetherAdapter(List<GroupDataBridging> list,String ty,Context context) {
        type=ty;
        groupDataBridgingList=list;
        mContext=context;
        SharedPreferences sharedPreferences=PlayTogetherActivity.playTogetherActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.play_group_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.group_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDataBridging groupDataBridging1=groupDataBridgingList.get(holder.getAdapterPosition());
                Intent intent=new Intent(PlayTogetherActivity.playTogetherActivity, PlayGroupActivity.class);
                intent.putExtra("groupData",groupDataBridging1);
                PlayTogetherActivity.playTogetherActivity.startActivity(intent);
            }
        });

        holder.join_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDataBridging groupDataBridging=groupDataBridgingList.get(holder.getAdapterPosition());
                if (type.equals("加入群组")){
                    joinGroup(groupDataBridging.getGroup().getGroupId()+"");
                }else {
                    quitGroup(groupDataBridging.getGroup().getGroupId()+"");
                }

            }
        });
        layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.group_dynamic.setLayoutManager(layoutManager);

        layoutManager1=new LinearLayoutManager(view.getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.group_users.setLayoutManager(layoutManager1);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroupDataBridging groupDataBridging=groupDataBridgingList.get(position);
        Glide.with(mContext)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + groupDataBridging.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.user_image);
        holder.user_name.setText(groupDataBridging.getUserinfo().getNickname());
        holder.group_name.setText(groupDataBridging.getGroup().getName());
        holder.group_lable.setText(groupDataBridging.getGroup().getLabel());
        holder.group_indec.setText(groupDataBridging.getGroup().getIntroduce());
        holder.group_school.setText(groupDataBridging.getGroup().getCampus());
        holder.join_group.setText(type);

        GroupDataBridging groupDataBridging2=groupDataBridgingList.get(holder.getAdapterPosition());

        playGroupDynamicAdapter=new PlayGroupDynamicAdapter(groupDataBridging2,mContext);
        holder.group_dynamic.setAdapter(playGroupDynamicAdapter);

        playGroupUserAdapter=new PlayGroupUserAdapter(groupDataBridging2,mContext);
        holder.group_users.setAdapter(playGroupUserAdapter);

    }

    @Override
    public int getItemCount() {
        return groupDataBridgingList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout group_content;
        CircleImageView user_image;
        TextView user_name;
        TextView group_name;
        TextView join_group;
        TextView group_indec;
        TextView group_lable;
        TextView group_school;
        RecyclerView group_dynamic;
        RecyclerView group_users;
        public ViewHolder(View itemView) {
            super(itemView);
            group_content= (LinearLayout) itemView.findViewById(R.id.play_item_content);
            user_image= (CircleImageView) itemView.findViewById(R.id.play_item_user_image);
            user_name= (TextView) itemView.findViewById(R.id.play_item_user_name);
            group_name= (TextView) itemView.findViewById(R.id.play_item_name);
            join_group= (TextView) itemView.findViewById(R.id.play_item_join);
            group_indec= (TextView) itemView.findViewById(R.id.play_item_info);
            group_lable= (TextView) itemView.findViewById(R.id.play_item_type);
            group_school= (TextView) itemView.findViewById(R.id.play_item_campus);
            group_dynamic= (RecyclerView) itemView.findViewById(R.id.play_item_dynamic);
            group_users= (RecyclerView) itemView.findViewById(R.id.play_item_users);
        }
    }

    public  void joinGroup(String id){
        subscriber=new Subscriber<CommResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("加入群组",e.getMessage());
            }

            @Override
            public void onNext(CommResult commResult) {
                Log.e("加入群组",commResult.getSuccessful()+"");
                if (commResult.getSuccessful()){
                    Log.e("加入群组","成功");
                    Toast.makeText(PlayTogetherActivity.playTogetherActivity,"加入成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PlayTogetherActivity.playTogetherActivity,"已经加入过了！",Toast.LENGTH_SHORT).show();
                    Log.e("加入群组","已经加入群组");
                }
            }


        };
        GroupMethods.getInstance().joinGroup(subscriber,SessionID,"加入群组",id);
    }

    public void quitGroup(String id){
        subscriber=new Subscriber<CommResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("加入群组",e.getMessage());
            }

            @Override
            public void onNext(CommResult commResult) {
                Log.e("退出群组",commResult.getSuccessful()+"");
                if (commResult.getSuccessful()){
                    Log.e("退出群组","成功");
                    Toast.makeText(PlayTogetherActivity.playTogetherActivity,"退出成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PlayTogetherActivity.playTogetherActivity,"未加入！",Toast.LENGTH_SHORT).show();
                    Log.e("退出群组","已经退出群组");
                }
            }


        };
        GroupMethods.getInstance().joinGroup(subscriber,SessionID,"退出群组",id);
    }
}
