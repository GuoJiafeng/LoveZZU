package com.gjf.lovezzu.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.CheckLoginApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BlackBeard丶 on 2017/03/15.
 */
public class UserSettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_exitLogin)
    RelativeLayout setting_exitLogin;

    private CheckLoginApplication checkLoginApplication;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersetting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.setting_exitLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_exitLogin:
                cleanUserLoinInfo();
                break;
        }

    }

    private void cleanUserLoinInfo() {
        checkLoginApplication = (CheckLoginApplication) getApplication();
        if (checkLoginApplication.isLogin()) {
            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("SessionID");
            editor.remove("phone");
            editor.clear().apply();
            checkLoginApplication.setIsLogin(false);
            Toast.makeText(getApplicationContext(), "已退出登录！", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "您还未登录！", Toast.LENGTH_LONG).show();
        }
    }


}
