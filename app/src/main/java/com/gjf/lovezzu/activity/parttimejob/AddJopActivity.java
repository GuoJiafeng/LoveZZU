package com.gjf.lovezzu.activity.parttimejob;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.WheelView;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhaox on 2017/5/24.
 */

public class AddJopActivity extends AppCompatActivity {

    @BindView(R.id.job_add_back)
    ImageView jobAddBack;
    @BindView(R.id.add_part_job)
    EditText addPartJob;
    @BindView(R.id.add_job_textNumber)
    TextView addJobTextNumber;
    @BindView(R.id.add_job_title)
    EditText addJobTitle;
    @BindView(R.id.add_job_salary)
    EditText addJobSalary;
    @BindView(R.id.add_job_startDate)
    TextView addJobStartDate;
    @BindView(R.id.add_job_endDate)
    TextView addJobEndDate;
    @BindView(R.id.add_job_startTime)
    TextView addJobStartTime;
    @BindView(R.id.add_job_endTime)
    TextView addJobEndTime;
    @BindView(R.id.add_job_campus)
    TextView addJobCampus;
    @BindView(R.id.add_job_workPlace)
    EditText addJobWorkPlace;
    @BindView(R.id.add_job_contactWay)
    EditText addJobContactWay;

    public  static AddJopActivity addJopActivity;
    private String job_type="校内";
    private static final String[] JOB_CLASS={"校内","校外"};
    private android.app.AlertDialog job_Dialog;
    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_add_job);
        ButterKnife.bind(this);
        addJopActivity=this;
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SessionID=sharedPreferences.getString("SessionID","");
        addPartJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = addPartJob.getText().toString();
                addJobTextNumber.setText(text.length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.job_add_back, R.id.send_job,R.id.add_job_startDate,R.id.add_job_endDate,R.id.add_job_startTime,R.id.add_job_endTime,R.id.add_job_campus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.job_add_back:
                finish();
                break;
            case R.id.send_job:
                addJobs();
                break;
            case R.id.add_job_startDate:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        addJobStartDate.setText(String.format("%d-%d-%d",year,month+1,dayOfMonth));
                    }
                },2017,10,10).show();

                break;
            case R.id.add_job_endDate:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        addJobEndDate.setText(String.format("%d-%d-%d",year,month+1,dayOfMonth));
                    }
                },2017,10,10).show();
                break;
            case R.id.add_job_startTime:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        addJobStartTime.setText(String.format("%d:%d",hourOfDay,minute));
                    }
                },0,0,true).show();

                break;
            case R.id.add_job_endTime:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        addJobEndTime.setText(String.format("%d:%d",hourOfDay,minute));
                    }
                },0,0,true).show();
                break;
            case R.id.add_job_campus:
                View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(1);
                wv.setItems(Arrays.asList(JOB_CLASS));
                wv.setSeletion(1);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        job_type=item;
                    }
                });

                job_Dialog= new android.app.AlertDialog.Builder(this)
                        .setTitle("商品种类")
                        .setView(outerView)
                        .show();
                TextView txtSure= (TextView) outerView.findViewById(R.id.txt_sure);
                txtSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addJobCampus.setText(job_type);
                        job_Dialog.dismiss();
                    }
                });
                TextView txtCancle= (TextView) outerView.findViewById(R.id.txt_cancel);
                txtCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        job_Dialog.dismiss();
                    }
                });
                break;
        }
    }

    //插入兼职信息
    private void addJobs() {
        if (addJobTitle.getText().toString().trim().equals("")||addJobSalary.getText().toString().trim().equals("")||
                addJobCampus.getText().toString().trim().equals("")||addJobWorkPlace.getText().toString().trim().equals("")||
                addJobContactWay.getText().toString().trim().equals("")){
            Toast.makeText(this,"请完善信息！",Toast.LENGTH_SHORT).show();
        }else {
            RequestParams requestParams=new RequestParams(LOGIN_URL + "PartTimeAction");
            requestParams.addBodyParameter("action","发布兼职");
            requestParams.addBodyParameter("SessionID",SessionID);
            requestParams.addBodyParameter("title",addJobTitle.getText().toString());
            requestParams.addBodyParameter("content",addPartJob.getText().toString());
            requestParams.addBodyParameter("salary",addJobSalary.getText().toString());
            requestParams.addBodyParameter("startDate",addJobStartDate.getText().toString());
            requestParams.addBodyParameter("endDate",addJobEndDate.getText().toString());
            requestParams.addBodyParameter("startTime",addJobStartTime.getText().toString());
            requestParams.addBodyParameter("endTime",addJobEndTime.getText().toString());
            requestParams.addBodyParameter("workPlace",addJobWorkPlace.getText().toString());
            requestParams.addBodyParameter("contactWay",addJobContactWay.getText().toString());
            requestParams.addBodyParameter("campus",addJobCampus.getText().toString());

            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        Boolean res=jsonObject.getBoolean("isSuccessful");
                        if (res){
                            Toast.makeText(AddJopActivity.addJopActivity,"发布成功，等待审核！",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddJopActivity.addJopActivity,PartTimeJobActivity.class);
                            AddJopActivity.addJopActivity.startActivity(intent);
                        }else {
                            Toast.makeText(AddJopActivity.addJopActivity,"请重新登录！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    Toast.makeText(AddJopActivity.addJopActivity,"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
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

}
