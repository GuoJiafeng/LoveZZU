package com.gjf.lovezzu.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.parttimejob.JobInfoActivity;
import com.gjf.lovezzu.activity.parttimejob.MyJobActivity;
import com.gjf.lovezzu.activity.parttimejob.PartTimeJobActivity;
import com.gjf.lovezzu.entity.parttimejob.JobResult;


import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/19.
 */

public class JobMyAdapter extends RecyclerView.Adapter<JobMyAdapter.ViewHolder>{

    private List<JobResult> jobResults;
    public JobMyAdapter(List<JobResult> list){
        jobResults=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.part__my_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.job_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(MyJobActivity.myJobActivity);
                builder.setTitle("删除兼职");
                builder.setMessage("确定要删除该兼职吗？");
                builder.setCancelable(true);
                builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JobResult jobResult=jobResults.get(holder.getAdapterPosition());
                        deleteJob(jobResult.getPartTimeId()+"");
                    }
                });
                builder.show();
            }
        });
        holder.job_text.setOnClickListener(new View.OnClickListener() {
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
        holder.job_status.setText(jobResult.getStatus());
    }

    @Override
    public int getItemCount() {
        return jobResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView job_delete;
        TextView job_title;
        TextView job_text;
        TextView job_date;
        TextView job_status;
        public ViewHolder(View itemView) {
            super(itemView);
            job_title= (TextView) itemView.findViewById(R.id.my_job_title);
            job_text= (TextView) itemView.findViewById(R.id.my_job_content);
            job_date= (TextView) itemView.findViewById(R.id.my_job_date);
            job_delete= (TextView) itemView.findViewById(R.id.my_job_delete);
            job_status= (TextView) itemView.findViewById(R.id.my_job_status);
        }
    }

    private void deleteJob(String id){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"PartTimeAction");
        requestParams.addBodyParameter("action","删除我的兼职");
        requestParams.addBodyParameter("Id",id);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(MyJobActivity.myJobActivity,"删除成功！",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }else {
                        Toast.makeText(MyJobActivity.myJobActivity,"请重新登录！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Toast.makeText(MyJobActivity.myJobActivity,"请保持网络通畅！",Toast.LENGTH_SHORT).show();
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
