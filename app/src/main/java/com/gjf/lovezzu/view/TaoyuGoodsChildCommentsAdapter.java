package com.gjf.lovezzu.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.UserInfoActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuChildConmmentsActivity;
import com.gjf.lovezzu.entity.GoodsChildCommentsDateBridging;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import rx.Subscriber;

import static com.gjf.lovezzu.R.id.child_comm_user_icon;
import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/3.
 */

public class TaoyuGoodsChildCommentsAdapter extends RecyclerView.Adapter<TaoyuGoodsChildCommentsAdapter.ViewHolder> {
    private List<GoodsChildCommentsDateBridging> goodsChildCommentsDateBridgings;
    private Context mContext;
    private GoodsChildCommentsDateBridging goodsChildCommentsDateBridging;
    private  GoodsChildCommentsDateBridging goodsChildCommentsDateBridgingNew;
    private Subscriber subscriber;

    private String sonson;
    private String SeesionID;

    public TaoyuGoodsChildCommentsAdapter(List<GoodsChildCommentsDateBridging> list, Context context){
        goodsChildCommentsDateBridgings=list;
        mContext=context;
        SharedPreferences sharedPreferences = TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity.getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SeesionID= sharedPreferences.getString("SessionID", "");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_child_comm_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.child_comments_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1=View.inflate(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity,R.layout.userinfo_update_view,null);
                final EditText editText= (EditText) view1.findViewById(R.id.edituserinfo);
                new AlertDialog.Builder(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity).setMessage("评论：")
                        .setView(view1).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText()!=null) {
                            goodsChildCommentsDateBridgingNew=goodsChildCommentsDateBridgings.get(holder.getAdapterPosition());
                            publishChildComments(editText.getText().toString());
                        }else {
                            Toast.makeText(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity,"不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
        holder.do_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsChildCommentsDateBridgingNew=goodsChildCommentsDateBridgings.get(holder.getAdapterPosition());
                addThnum();
            }
        });
        holder.do_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1=View.inflate(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity,R.layout.userinfo_update_view,null);
                final EditText editText= (EditText) view1.findViewById(R.id.edituserinfo);
                new AlertDialog.Builder(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity).setMessage("评论：")
                        .setView(view1).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goodsChildCommentsDateBridgingNew=goodsChildCommentsDateBridgings.get(holder.getAdapterPosition());
                        if (editText.getText()!=null) {
                            publishChildComments(editText.getText().toString());
                        }else {
                            Toast.makeText(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity,"不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();

            }
        });
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
        ImageView do_zan;
        ImageView do_comm;
        public ViewHolder(View itemView) {
            super(itemView);
            childsUserIcon= (ImageView) itemView.findViewById(child_comm_user_icon);
            childUserNickName= (TextView) itemView.findViewById(R.id.child_comm_user_nickname);
            child_comments_content= (TextView) itemView.findViewById(R.id.child_comm_content);
            child_comments_date= (TextView) itemView.findViewById(R.id.child_comm_time);
            child_comments_reples= (TextView) itemView.findViewById(R.id.child_comm_num);
            child_comments_thumbnum= (TextView) itemView.findViewById(R.id.child_comm_zan);
            do_zan= (ImageView) itemView.findViewById(R.id.do_zan);
            do_comm= (ImageView) itemView.findViewById(R.id.do_comm);
        }
    }
    //添加子孙的评论
    private void publishChildComments(String comments) {

        RequestParams requestParams = new RequestParams(LOGIN_URL + "comments_L2Action");
        requestParams.addBodyParameter("action", "postcomments_L2");
        requestParams.addBodyParameter("L1_Cid",  "");
        requestParams.addBodyParameter("L2_Cid", goodsChildCommentsDateBridgingNew.getComments_l2().getL2_Cid().toString());
        requestParams.addBodyParameter("SessionID", SeesionID);
        requestParams.addBodyParameter("comments",comments);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Boolean res = jsonObject.getBoolean("isSuccessful");
                    if (res) {
                        Toast.makeText(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity,"评论成功！",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

    private void addThnum() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "comments_L2Action");
        requestParams.addBodyParameter("action", "postcomments_L2");
        requestParams.addBodyParameter("L1_Cid", "");
        requestParams.addBodyParameter("L2_Cid", goodsChildCommentsDateBridgingNew.getComments_l2().getL2_Cid().toString());
        requestParams.addBodyParameter("SessionID", SeesionID);
        requestParams.addBodyParameter("comments", "");
        requestParams.addBodyParameter("ThumbNum", "1");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("二级评论-孙----------------点赞", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Boolean res = jsonObject.getBoolean("isSuccessful");
                    if (res) {
                        Toast.makeText(TaoyuChildConmmentsActivity.taoyuChildConmmentsActivity,"点赞成功！",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
