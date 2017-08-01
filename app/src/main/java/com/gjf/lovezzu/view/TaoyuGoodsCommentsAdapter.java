package com.gjf.lovezzu.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.TaoyuChildConmmentsActivity;
import com.gjf.lovezzu.entity.GoodsCommentsDataBridging;
import com.gjf.lovezzu.entity.GoodsCommentsResult;

import java.util.ArrayList;
import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/7/31.
 */

public class TaoyuGoodsCommentsAdapter extends RecyclerView.Adapter<TaoyuGoodsCommentsAdapter.ViewHolder> {

    private List<GoodsCommentsDataBridging> goodsCommentsesList;
    private Context mContext;
    private GoodsCommentsDataBridging goodsCommentsDataBridging;
    private int l1_Cid;
    public TaoyuGoodsCommentsAdapter(List<GoodsCommentsDataBridging> list,Context context){
        goodsCommentsesList=list;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.taoyu_goods_list_item2,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, TaoyuChildConmmentsActivity.class);

                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        goodsCommentsDataBridging=goodsCommentsesList.get(position);
        Glide.with(mContext)
                .load(LOGIN_URL+"filedownload?action=头像&imageURL="+goodsCommentsDataBridging.getUserinfo().getImageUrl())
                .into(holder.goodsUserIcon);
        holder.goodsUserNickName.setText(goodsCommentsDataBridging.getUserinfo().getNickname());
        holder.comments_content.setText(goodsCommentsDataBridging.getComments_L1().getComments());
        holder.comments_date.setText(goodsCommentsDataBridging.getComments_L1().getCdate());
        holder.comments_reples.setText(goodsCommentsDataBridging.getComments_L1().getNum_replies()+"");
        l1_Cid=goodsCommentsDataBridging.getComments_L1().getL1_Cid();


    }

    @Override
    public int getItemCount() {
        return goodsCommentsesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout commentView;
        ImageView goodsUserIcon;
        TextView goodsUserNickName;
        TextView comments_content;
        TextView comments_date;
        TextView comments_reples;

        public ViewHolder(View itemView) {
            super(itemView);
            commentView= (LinearLayout) itemView.findViewById(R.id.comment_main);
            goodsUserIcon= (ImageView) itemView.findViewById(R.id.goods_comm_user_icon);
            goodsUserNickName= (TextView) itemView.findViewById(R.id.goods_comm_user_nickname);
            comments_content= (TextView) itemView.findViewById(R.id.goods_comm_content);
            comments_date= (TextView) itemView.findViewById(R.id.goods_comm_time);
            comments_reples= (TextView) itemView.findViewById(R.id.goods_comm_num);
        }
    }
}
