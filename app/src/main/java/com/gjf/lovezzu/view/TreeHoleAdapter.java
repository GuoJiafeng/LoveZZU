package com.gjf.lovezzu.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.treehole.TreeHoleActivity;
import com.gjf.lovezzu.activity.treehole.TreeInfoActivity;
import com.gjf.lovezzu.entity.treehole.TreeHole;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.gjf.lovezzu.R.id.home;
import static com.gjf.lovezzu.R.id.tree_item_view;
import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/5/4.
 */

    public class TreeHoleAdapter extends RecyclerView.Adapter<TreeHoleAdapter.ViewHolder> {

    private List<TreeHole> treeHoleList;
    private String SessionID;
    private   Boolean res=false;
    public TreeHoleAdapter(List<TreeHole> list,String sessionID) {
        treeHoleList=list;
        SessionID=sessionID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_hole_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.talkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TreeHoleActivity.treeHoleActivity, TreeInfoActivity.class);
                TreeHole treeHole=treeHoleList.get(viewHolder.getAdapterPosition());
                intent.putExtra("treeHole",treeHole);
                TreeHoleActivity.treeHoleActivity.startActivity(intent);
            }
        });
        viewHolder.zanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreeHole treeHole=treeHoleList.get(viewHolder.getAdapterPosition());
                if (treeHole.getThembed()){
                    Toast.makeText(TreeHoleActivity.treeHoleActivity,"已经点过赞了！",Toast.LENGTH_SHORT).show();
                }else {
                    if (addThum(treeHole.getTreeHoleId().toString())){
                        Integer zan=Integer.parseInt(viewHolder.zanView.getText().toString());
                        viewHolder.zanView.setText((zan+1)+"");
                        viewHolder.zanView.setTextColor(Color.parseColor("#F48F0B"));
                        viewHolder.zanImage.setImageResource(R.drawable.life_zan_done);
                    }
                }

            }
        });
        viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TreeHoleActivity.treeHoleActivity, TreeInfoActivity.class);
                TreeHole treeHole=treeHoleList.get(viewHolder.getAdapterPosition());
                intent.putExtra("treeHole",treeHole);
                TreeHoleActivity.treeHoleActivity.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeHole treeHole=treeHoleList.get(position);
        holder.contentView.setText(treeHole.getTreeHoleContent());
        holder.talkView.setText(treeHole.getCommentCount()+"");
        holder.zanView.setText(treeHole.getThembCount()+"");
        if (treeHole.getThembed()){
            holder.zanView.setTextColor(Color.parseColor("#F48F0B"));
            holder.zanImage.setImageResource(R.drawable.life_zan_done);
        }else {
            holder.zanImage.setImageResource(R.drawable.zan_white);
        }
        holder.testView.setText(treeHole.getCampus());
    }

    @Override
    public int getItemCount() {
        return treeHoleList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentView;
        TextView testView;
        TextView zanView;
        TextView talkView;
        ImageView zanImage;
        ImageView talkImage;
        public ViewHolder(View itemView) {
            super(itemView);
            contentView = (TextView) itemView.findViewById(R.id.tree_content);
            testView = (TextView) itemView.findViewById(R.id.tree_item_author);
            zanView = (TextView) itemView.findViewById(R.id.tree_nice);
            talkView = (TextView) itemView.findViewById(R.id.tree_talk);
            zanImage= (ImageView) itemView.findViewById(R.id.tree_zan_image);
            talkImage= (ImageView) itemView.findViewById(R.id.tree_talk_image);
        }
    }


    private boolean addThum(String treeId){

        RequestParams requestParams=new RequestParams(LOGIN_URL + "TreeHoleCommentAction");
        requestParams.addBodyParameter("action","树洞点赞");
        requestParams.addBodyParameter("treeHoleId",treeId);
        requestParams.addBodyParameter("SessionID",SessionID);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try{
                    JSONObject json=new JSONObject(result);
                    res=json.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(TreeHoleActivity.treeHoleActivity,"+1",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TreeHoleActivity.treeHoleActivity,"已经点过赞了！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(TreeHoleActivity.treeHoleActivity,"请检查网络！",Toast.LENGTH_SHORT).show();
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
