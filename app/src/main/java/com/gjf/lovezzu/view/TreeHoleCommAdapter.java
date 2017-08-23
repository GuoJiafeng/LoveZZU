package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.treehole.TreeHoleComm;

import java.util.List;

/**
 * Created by zhao on 2017/8/17.
 */

public class TreeHoleCommAdapter extends RecyclerView.Adapter<TreeHoleCommAdapter.ViewHolder> {

    private List<TreeHoleComm> treeHoleCommList;

    public TreeHoleCommAdapter (List<TreeHoleComm> list){
            treeHoleCommList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_hole_comm,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeHoleComm treeHoleComm=treeHoleCommList.get(position);
        holder.commText.setText(treeHoleComm.getCommentContent());
        holder.commTime.setText(treeHoleComm.getDate());
        holder.commDate.setText((position+1)+" 楼");
    }

    @Override
    public int getItemCount() {
        return treeHoleCommList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView commText;
        TextView commDate;
        TextView commTime;
        public ViewHolder(View itemView) {
            super(itemView);
            commText= (TextView) itemView.findViewById(R.id.tree_info_comm_text);
            commDate= (TextView) itemView.findViewById(R.id.tree_info_comm_time);
            commTime= (TextView) itemView.findViewById(R.id.tree_info_comm_date);
        }
    }
}
