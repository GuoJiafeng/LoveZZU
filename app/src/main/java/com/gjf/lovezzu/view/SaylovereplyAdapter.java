package com.gjf.lovezzu.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.saylvoeActivity.SayloveReplyActivity;
import com.gjf.lovezzu.entity.saylove.SayloveReply;


import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by Leon on 2017/8/16.
 */

public class SaylovereplyAdapter   extends RecyclerView.Adapter<SaylovereplyAdapter.ViewHolder> {
    private List<SayloveReply>  sayloveReplyList;
    private Context context;

    public SaylovereplyAdapter(List<SayloveReply> sayloveReplyList, SayloveReplyActivity sayloveReply) {
        this.sayloveReplyList = sayloveReplyList;

    }

    private SayloveReply sayloveReply;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.saylovereply_item,parent,false);
        final  ViewHolder holder= new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sayloveReply=sayloveReplyList.get(position);
        holder.context.setText(sayloveReply.getLoveCardComment().getCommentContent());
        holder.date.setText(sayloveReply.getLoveCardComment().getDate());

        Glide.with(SayloveReplyActivity.saylovereplyActivity)
                .load(LOGIN_URL+"filedownload?action=头像&imageURL="+sayloveReply.getUserImg())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return sayloveReplyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
                View  Saylovereply_view;
        ImageView imageView;
        TextView context;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            Saylovereply_view=itemView;
            context= (TextView) itemView.findViewById(R.id.saylove_reply);
            imageView= (ImageView) itemView.findViewById(R.id.saylove_touxiang);
            date= (TextView) itemView.findViewById(R.id.biaobaicomment_time);

        }
    }
}
