package com.gjf.lovezzu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.entity.friend.DemoApplication;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by BlackBeard丶 on 2017/03/15.
 */
public class UserSettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_exitLogin)
    RelativeLayout setting_exitLogin;

    private CheckLoginApplication checkLoginApplication;
    ImageView share;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersetting);
        ButterKnife.bind(this);
        share= (ImageView) findViewById(R.id.user_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    @OnClick({R.id.setting_exitLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_exitLogin:
                cleanUserLoinInfo();
                break;
        }

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    private void logout(){
       /* EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                finish();
                Log.e("环信--注册","退出成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("环信--退出登录",i+", "+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });*/
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoApplication.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                        pd.dismiss();
                Log.e("环信--注册","退出成功");
                        finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                        pd.dismiss();
                        Log.e("环信--退出登录",code+", "+message);

            }
        });
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
            logout();
        } else {
            Toast.makeText(getApplicationContext(), "您还未登录！", Toast.LENGTH_LONG).show();
        }
    }


}
