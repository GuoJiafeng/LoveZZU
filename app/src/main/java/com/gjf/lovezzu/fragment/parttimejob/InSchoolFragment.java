package com.gjf.lovezzu.fragment.parttimejob;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.parttimejob.AddJopActivity;
import com.gjf.lovezzu.activity.parttimejob.PartTimeJobActivity;
import com.gjf.lovezzu.entity.JobItem;
import com.gjf.lovezzu.entity.parttimejob.JobData;
import com.gjf.lovezzu.view.SchoolJobAdapter;

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
    private List<JobItem> JobItemList = new ArrayList<>();
    private SchoolJobAdapter inSchoolJobAdapter;
    private Subscriber subscriber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.part_time_job_list, container, false);
            ButterKnife.bind(this, view);
            getJobItem();
            shwowJobs();
            refreshJob();
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

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JobData jobData) {

            }


        };

    }
    //显示数据
    private void shwowJobs(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        schoolJobList.setLayoutManager(layoutManager);
        inSchoolJobAdapter=new SchoolJobAdapter(JobItemList);
        schoolJobList.setAdapter(inSchoolJobAdapter);

    }
    private void refreshJob(){
        jobRefresh.setColorSchemeResources(R.color.colorPrimary);
        jobRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              getJobItem();
            }
        });
    }


    @OnClick(R.id.add_job)
    public void onViewClicked() {
        Intent intent=new Intent(PartTimeJobActivity.partTimeJobActivity, AddJopActivity.class);
        PartTimeJobActivity.partTimeJobActivity.startActivity(intent);
    }
}
