package com.gjf.lovezzu.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.parttimejob.JobInfoActivity;
import com.gjf.lovezzu.activity.parttimejob.PartTimeJobActivity;
import com.gjf.lovezzu.entity.parttimejob.JobResult;

import java.util.List;


/**
 * Created by zhao on 2017/8/18.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private List<JobResult> jobResults;

    public JobAdapter (List<JobResult> list){
        jobResults=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.part_time_job_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.job_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobResult jobResult=jobResults.get(holder.getAdapterPosition());
                Intent intent=new Intent(PartTimeJobActivity.partTimeJobActivity, JobInfoActivity.class);
                intent.putExtra("job",jobResult);
                PartTimeJobActivity.partTimeJobActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JobResult jobResult=jobResults.get(position);
        holder.job_title.setText(jobResult.getTitle());
        holder.job_text.setText(jobResult.getContent());
        holder.job_date.setText(jobResult.getPublishDate());
    }

    @Override
    public int getItemCount() {
        return jobResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout job_item;
        TextView job_title;
        TextView job_text;
        TextView job_date;
        public ViewHolder(View itemView) {
            super(itemView);
            job_item= (LinearLayout) itemView.findViewById(R.id.job_item);
            job_title= (TextView) itemView.findViewById(R.id.job_title);
            job_text= (TextView) itemView.findViewById(R.id.job_content);
            job_date= (TextView) itemView.findViewById(R.id.job_date);
        }
    }
}
