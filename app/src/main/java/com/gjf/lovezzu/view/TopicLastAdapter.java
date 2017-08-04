package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjf.lovezzu.R;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicLastAdapter extends RecyclerView.Adapter<TopicLastAdapter.ViewHolder> {



    public TopicLastAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_last_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        ImageView zan;
        TextView zan_num;
        ImageView comm;
        TextView comm_num;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.topic_last_title);
            content = (TextView) itemView.findViewById(R.id.topic_last_content);
            zan= (ImageView) itemView.findViewById(R.id.topic_last_zan);
            zan_num= (TextView) itemView.findViewById(R.id.topic_last_zan_num);
            comm= (ImageView) itemView.findViewById(R.id.topic_last_comm);
            comm_num= (TextView) itemView.findViewById(R.id.topic_last_comm_num);

        }
    }

}
