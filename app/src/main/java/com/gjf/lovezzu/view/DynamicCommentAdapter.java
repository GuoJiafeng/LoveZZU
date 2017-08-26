package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.palytogether.DynamicInfoActivity;
import com.gjf.lovezzu.entity.playtogether.DynamicComment;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;
import static java.lang.System.load;

/**
 * Created by lenovo047 on 2017/8/22.
 */

public class DynamicCommentAdapter extends  RecyclerView.Adapter<DynamicCommentAdapter.ViewHolder> {

    private List<DynamicComment> dynamicCommentList;
    private  DynamicComment dynamicComment;
    public DynamicCommentAdapter(List<DynamicComment> dynamicCommentList, DynamicInfoActivity dynamicInfoActivity) {
        this.dynamicCommentList = dynamicCommentList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_comment_item,parent,false);
        final  ViewHolder  holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dynamicComment=dynamicCommentList.get(position);
        holder.dynamicCommentname.setText(dynamicComment.getUserinfo().getNickname());
        holder.dynamicCommentdate.setText(dynamicComment.getDynamicComment().getDate());
        holder.dynamicCommentcontent.setText(dynamicComment.getDynamicComment().getComment());
        Glide.with(DynamicInfoActivity.dynamicInfoActivity)
                .load(LOGIN_URL+"filedownload?action=头像&imageURL="+dynamicComment.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.userImg);
    }

    @Override
    public int getItemCount() {
        return dynamicCommentList.size();
    }

    static class  ViewHolder extends  RecyclerView.ViewHolder{
      TextView dynamicCommentname;
        TextView dynamicCommentdate;
        TextView dynamicCommentcontent;
        ImageView userImg;

        public ViewHolder(View itemView) {
            super(itemView);
            dynamicCommentname= (TextView) itemView.findViewById(R.id.dynamiccomment_username);
            dynamicCommentcontent= (TextView) itemView.findViewById(R.id.dynamiccomment_content);
            dynamicCommentdate= (TextView) itemView.findViewById(R.id.dynamiccomment_date);
            userImg= (ImageView) itemView.findViewById(R.id.dynamiccomment_userImg);
        }
    }
}
