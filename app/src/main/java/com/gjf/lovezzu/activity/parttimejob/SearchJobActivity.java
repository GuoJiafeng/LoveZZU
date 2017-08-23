package com.gjf.lovezzu.activity.parttimejob;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
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
 * Created by zhao on 2017/8/19.
 */

public class SearchJobActivity extends AppCompatActivity {


    @BindView(R.id.taoyu_search_title)
    EditText taoyuSearchTitle;
    @BindView(R.id.taoyu_search_button)
    TextView taoyuSearchButton;
    @BindView(R.id.taoyu_search_list)
    RecyclerView taoyuSearchList;

    private Subscriber subscriber;
    private JobAdapter jobAdapter;
    private List<JobResult> jobResults=new ArrayList<>();
    public static SearchJobActivity searchJobActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taoyu_search_activity);
        ButterKnife.bind(this);
        searchJobActivity=this;
        taoyuSearchTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getJob();
                }
                return false;
            }
        });
        showJob();
    }
    @OnClick(R.id.taoyu_search_button)
    public void onViewClicked() {
            getJob();
    }
    private void showJob(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        taoyuSearchList.setLayoutManager(layoutManager);
        jobAdapter=new JobAdapter(jobResults);
        taoyuSearchList.setAdapter(jobAdapter);
    }

    private void getJob(){

        subscriber=new Subscriber<JobData>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(searchJobActivity,"请检查网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(JobData jobData) {

                List<JobResult> list=jobData.getValues();
                if (!list.isEmpty()){
                    jobResults.clear();
                    jobResults.addAll(list);
                    jobAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(PartTimeJobActivity.partTimeJobActivity,"没有相关兼职信息！",Toast.LENGTH_SHORT).show();
                }
            }
        };
        if (taoyuSearchTitle.getText().toString().trim().equals("")){
            Toast.makeText(searchJobActivity,"请输入关键字",Toast.LENGTH_SHORT).show();
        }else {
            JobMethods.getInstance().getAllJob(subscriber,"搜索兼职",taoyuSearchTitle.getText().toString());
        }

    }
}
