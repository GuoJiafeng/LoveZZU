package com.gjf.lovezzu.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.GoodsCommentsDataBridging;
import com.gjf.lovezzu.entity.GoodsCommentsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2017/7/31.
 */

public class TaoyuGoodsCommentsAdapter extends RecyclerView.Adapter<TaoyuGoodsCommentsAdapter.ViewHolder> {

    private List<GoodsCommentsDataBridging> goodsCommentses;
    private Context mContext;
    public TaoyuGoodsCommentsAdapter(List<GoodsCommentsDataBridging> list,Context context){
        goodsCommentses=list;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return goodsCommentses.size();
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
        }
    }
}
