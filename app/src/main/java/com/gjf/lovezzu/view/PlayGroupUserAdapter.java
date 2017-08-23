package com.gjf.lovezzu.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.palytogether.PlayTogetherActivity;
import com.gjf.lovezzu.entity.UserInfoResult;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/21.
 */

public class PlayGroupUserAdapter extends RecyclerView.Adapter<PlayGroupUserAdapter.ViewHolder> {

    private List<UserInfoResult> userInfoResultList;
    private Context mContext;
    public PlayGroupUserAdapter(List<UserInfoResult> list,Context context){
        userInfoResultList=list;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.play_group_item_user,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"用户信息",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfoResult userInfoResult=userInfoResultList.get(position);
        Glide.with(mContext)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + userInfoResult.getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return userInfoResultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        public ViewHolder(View itemView) {
            super(itemView);
            userImage= (CircleImageView) itemView.findViewById(R.id.play_group_user_image);
        }
    }
}
