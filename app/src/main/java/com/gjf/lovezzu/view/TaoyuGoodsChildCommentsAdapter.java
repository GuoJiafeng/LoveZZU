package com.gjf.lovezzu.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.GoodsChildCommentsDateBridging;
import java.util.List;
import static com.gjf.lovezzu.R.id.child_comm_user_icon;
import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/3.
 */

public class TaoyuGoodsChildCommentsAdapter extends RecyclerView.Adapter<TaoyuGoodsChildCommentsAdapter.ViewHolder> {
    private List<GoodsChildCommentsDateBridging> goodsChildCommentsDateBridgings;
    private Context mContext;
    private GoodsChildCommentsDateBridging goodsChildCommentsDateBridging;
    public TaoyuGoodsChildCommentsAdapter(List<GoodsChildCommentsDateBridging> list, Context context){
        goodsChildCommentsDateBridgings=list;
        mContext=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_child_comm_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        goodsChildCommentsDateBridging=goodsChildCommentsDateBridgings.get(position);
        Glide.with(mContext)
                .load(LOGIN_URL+"filedownload?action=头像&imageURL="+goodsChildCommentsDateBridging.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.childsUserIcon);
        holder.childUserNickName.setText(goodsChildCommentsDateBridging.getUserinfo().getNickname());
        holder.child_comments_content.setText(goodsChildCommentsDateBridging.getComments_l2().getComments());
        holder.child_comments_date.setText(goodsChildCommentsDateBridging.getComments_l2().getCdate());
        holder.child_comments_reples.setText(goodsChildCommentsDateBridging.getComments_l2().getNum_replies()+"");
        holder.child_comments_thumbnum.setText(goodsChildCommentsDateBridging.getComments_l2().getNum_thumb()+"");
    }

    @Override
    public int getItemCount() {
        return goodsChildCommentsDateBridgings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView childsUserIcon;
        TextView childUserNickName;
        TextView child_comments_content;
        TextView child_comments_date;
        TextView child_comments_reples;
        TextView child_comments_thumbnum;
        public ViewHolder(View itemView) {
            super(itemView);
            childsUserIcon= (ImageView) itemView.findViewById(child_comm_user_icon);
            childUserNickName= (TextView) itemView.findViewById(R.id.child_comm_user_nickname);
            child_comments_content= (TextView) itemView.findViewById(R.id.child_comm_content);
            child_comments_date= (TextView) itemView.findViewById(R.id.child_comm_time);
            child_comments_reples= (TextView) itemView.findViewById(R.id.child_comm_num);
            child_comments_thumbnum= (TextView) itemView.findViewById(R.id.child_comm_zan);
        }
    }
    //添加子孙的评论
    private void addChildren(int position){


    }
}
