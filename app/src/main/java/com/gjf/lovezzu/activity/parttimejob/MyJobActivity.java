package com.gjf.lovezzu.activity.parttimejob;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.parttimejob.JobData;
import com.gjf.lovezzu.entity.parttimejob.JobResult;
import com.gjf.lovezzu.network.JobMethods;
import com.gjf.lovezzu.view.JobMyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by zhao on 2017/8/19.
 */

public class MyJobActivity extends AppCompatActivity {


    public static MyJobActivity myJobActivity;
    @BindView(R.id.my_job_back)
    ImageView myJobBack;
    @BindView(R.id.my_job_RecyclerView)
    RecyclerView myJobRecyclerView;
    @BindView(R.id.my_job_refresh)
    SwipeRefreshLayout myJobRefresh;

    private String SessionID;
    private Subscriber subscriber;
    private List<JobResult> jobResults=new ArrayList<>();
    private JobMyAdapter jobMyAdapter;
    private  SharedPreferences.Editor editor;
    private Boolean firstOpen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_my_job);
        ButterKnife.bind(this);
        myJobActivity = this;
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_APPEND);
        editor = sharedPreferences.edit();
        SessionID=sharedPreferences.getString("SessionID","");
        firstOpen=sharedPreferences.getBoolean("firstOpen",true);
        if (firstOpen){
            showMessage();
            editor.putBoolean("firstOpen",false);
            editor.apply();
        }
        myJobRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyJOb();
            }
        });
        getMyJOb();
        showJob();
    }

    @OnClick(R.id.my_job_back)
    public void onViewClicked() {
        Intent intent=new Intent(this,PartTimeJobActivity.class);
        startActivity(intent);
    }

    private void showMessage(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(MyJobActivity.myJobActivity);
        builder.setTitle("提示");
        builder.setMessage("正在审核或审核未通过的兼职信息，只用发布人可见！");
        builder.setCancelable(true);
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showJob(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        myJobRecyclerView.setLayoutManager(layoutManager);
        jobMyAdapter=new JobMyAdapter(jobResults);
        myJobRecyclerView.setAdapter(jobMyAdapter);
    }

    private void getMyJOb(){
        subscriber=new Subscriber<JobData>() {
            @Override
            public void onCompleted() {
                if (myJobRefresh.isRefreshing()){
                    myJobRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(PartTimeJobActivity.partTimeJobActivity,"请重新登录或检查网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JobData jobData) {

                List<JobResult> list=jobData.getValues();
                if (!list.isEmpty()){
                    jobResults.clear();
                    jobResults.addAll(list);
                    jobMyAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(PartTimeJobActivity.partTimeJobActivity,"还没有兼职信息！",Toast.LENGTH_SHORT).show();
                }
            }

        };
        JobMethods.getInstance().getMyJob(subscriber,"查询我发布的兼职",SessionID);
    }

}
