package com.gjf.lovezzu.fragment.parttimejob;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.parttimejob.AddJopActivity;
import com.gjf.lovezzu.activity.parttimejob.PartTimeJobActivity;
import com.gjf.lovezzu.entity.parttimejob.JobData;
import com.gjf.lovezzu.entity.parttimejob.JobResult;
import com.gjf.lovezzu.network.JobMethods;
import com.gjf.lovezzu.view.JobAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by zhaox on 2017/5/24.
 */

public class InSchoolFragment extends Fragment {
    @BindView(R.id.school_job_list)
    RecyclerView schoolJobList;
    @BindView(R.id.job_refresh)
    SwipeRefreshLayout jobRefresh;
    @BindView(R.id.add_job)
    ImageView addJob;

    private View view;
    private Subscriber subscriber;
    private List<JobResult> jobResults=new ArrayList<>();
    private JobAdapter jobAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.part_time_job_list, container, false);
            ButterKnife.bind(this, view);
            jobRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getJobItem();
                }
            });
            getJobItem();
            shwowJobs();
        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }
        return view;
    }
    //获取兼职数据
    private void getJobItem(){
        subscriber=new Subscriber<JobData>() {
            @Override
            public void onCompleted() {
                if (jobRefresh.isRefreshing()){
                    jobRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(PartTimeJobActivity.partTimeJobActivity,"请检查网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JobData jobData) {

                List<JobResult> list=jobData.getValues();
                if (!list.isEmpty()){
                    jobResults.clear();
                    jobResults.addAll(list);
                    jobAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(PartTimeJobActivity.partTimeJobActivity,"还没有兼职信息！",Toast.LENGTH_SHORT).show();
                }
            }

        };
        JobMethods.getInstance().getAllJob(subscriber,"查询所有兼职","校内");

    }

    private void shwowJobs(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        schoolJobList.setLayoutManager(layoutManager);
        jobAdapter=new JobAdapter(jobResults);
        schoolJobList.setAdapter(jobAdapter);

    }


    @OnClick(R.id.add_job)
    public void onViewClicked() {
        Intent intent=new Intent(PartTimeJobActivity.partTimeJobActivity, AddJopActivity.class);
        PartTimeJobActivity.partTimeJobActivity.startActivity(intent);
    }
}
