package com.gjf.lovezzu.activity.parttimejob;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.parttimejob.JobResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaox on 2017/5/24.
 */

public class JobInfoActivity extends AppCompatActivity {
    @BindView(R.id.job_info_back)
    ImageView jobInfoBack;
    @BindView(R.id.job_info_title)
    TextView jobInfoTitle;
    @BindView(R.id.job_info_text)
    TextView jobInfoText;
    @BindView(R.id.job_info_status)
    TextView jobInfoStatus;
    @BindView(R.id.job_info_salary)
    TextView jobInfoSalary;
    @BindView(R.id.job_info_startDate)
    TextView jobInfoStartDate;
    @BindView(R.id.job_info_endDate)
    TextView jobInfoEndDate;
    @BindView(R.id.job_info_startTime)
    TextView jobInfoStartTime;
    @BindView(R.id.job_info_endTime)
    TextView jobInfoEndTime;
    @BindView(R.id.job_info_campus)
    TextView jobInfoCampus;
    @BindView(R.id.job_info_workPlace)
    TextView jobInfoWorkPlace;
    @BindView(R.id.job_info_contactWay)
    TextView jobInfoContactWay;
    @BindView(R.id.job_phone)
    LinearLayout jobPhone;
    private JobResult jobResult=new JobResult();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_job_info);
        ButterKnife.bind(this);
        jobResult= (JobResult) getIntent().getSerializableExtra("job");
        initView();
    }

    private void initView(){
        jobInfoTitle.setText(jobResult.getTitle());
        jobInfoText.setText(jobResult.getContent());
        jobInfoStatus.setText(jobResult.getStatus());
        jobInfoSalary.setText(jobResult.getSalary());
        jobInfoStartDate.setText(jobResult.getStartDate());
        jobInfoEndDate.setText(jobResult.getEndDate());
        jobInfoStartTime.setText(jobResult.getStartTime());
        jobInfoEndTime.setText(jobResult.getEndTime());
        jobInfoCampus.setText(jobResult.getCampus());
        jobInfoWorkPlace.setText(jobResult.getWorkPlace());
        jobInfoContactWay.setText(jobResult.getContactWay());

    }

    @OnClick({R.id.job_info_back, R.id.job_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.job_info_back:
                Intent intent=new Intent(this,PartTimeJobActivity.class);
                startActivity(intent);
                break;
            case R.id.job_phone:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
                }else {
                    call();
                }
                break;
        }
    }

    private void call(){
        try {
            Intent intent1=new Intent(Intent.ACTION_CALL);
            intent1.setData(Uri.parse("tel:"+jobInfoContactWay.getText()));
            startActivity(intent1);
        }catch (SecurityException e){
            e.printStackTrace();
            Toast.makeText(this,"不是电话号码！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }else {
                    Toast.makeText(this,"没有电话权限！",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
