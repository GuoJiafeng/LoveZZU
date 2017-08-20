package com.gjf.lovezzu.activity.saylvoeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gjf.lovezzu.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by BlackBeard丶 on 2017/03/23.
 */
public class IWantSayLoveActivity extends AppCompatActivity {
    @BindView(R.id.biaobai)
    EditText biaobai;
    @BindView(R.id.beibiaobai)
    EditText beibiaobai;

    @BindView(R.id.biaobai_submmit)
    ImageButton biaobaiSubmmit;
    private static ProgressDialog progressDialog;
    @BindView(R.id.biaobai_content)
    EditText biaobaiContent;


    private String SessionID;
    private String lovecardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biaobai_submit);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
    }

    @OnClick(R.id.biaobai_submmit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.biaobai_submmit:
                progressDialog=new ProgressDialog(IWantSayLoveActivity.this);
                progressDialog.setTitle("正在发布表白");
                progressDialog.setMessage("uploading....");
                progressDialog.setCancelable(true);
                progressDialog.show();
                publishBiaobai();
                break;
        }
    }

    private void publishBiaobai() {
        if (beibiaobai != null && biaobaiContent!=null){
            RequestParams requestParams=new RequestParams(LOGIN_URL+"LoveCardAction");
            if (biaobai.getText().toString().trim()!=null&&!biaobai.getText().toString().trim().equals("")) {

                requestParams.addBodyParameter("senderName", biaobai.getText().toString());
            }else{

                requestParams.addBodyParameter("senderName", "匿名");
            }
            requestParams.addBodyParameter("lovedName",beibiaobai.getText().toString());
            requestParams.addBodyParameter("loveContent",biaobaiContent.getText().toString());
            requestParams.addBodyParameter("action","发布表白");
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {

                    if (!result.isEmpty()){
                        lovecardID=result;
                        progressDialog.dismiss();
                        Intent intent=new Intent(SayLoveActivity.sayLoveActivity,SayloveWallActivity.class);
                        SayLoveActivity.sayLoveActivity.startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                    }

              }


                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(getApplicationContext(),"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();

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

