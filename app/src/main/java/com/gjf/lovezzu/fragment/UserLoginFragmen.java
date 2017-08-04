package com.gjf.lovezzu.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.activity.UserInfoActivity;
import com.gjf.lovezzu.activity.UserLoginActivity;
import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.entity.LoginResult;
import com.gjf.lovezzu.network.LoginMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import rx.Subscriber;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;


/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class UserLoginFragmen extends Fragment {
    private View view;
    private UserSingUpFragment userSingUpFragment;
    private Subscriber subscriber;
    private CheckLoginApplication checkLoginApplication;
    private static String token;

    @BindView(R.id.new_user_reg)
    TextView new_user_reg;
    @BindView(R.id.login_title_back)
    ImageView my_title_back;
    @BindView(R.id.user_reg_phone)
    EditText user_reg_phone;
    @BindView(R.id.user_reg_password)
    EditText user_reg_password;
    @BindView(R.id.user_login)
    LinearLayout user_login;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userlogin_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.login_title_back, R.id.new_user_reg, R.id.user_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_title_back:
                returnHome();
                break;
            case R.id.new_user_reg:

                goToreg();
                break;
            case R.id.user_login:

                checkInput();
                break;
        }
    }


//回到主页

    private void returnHome() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    //进入注册页面
    private void goToreg() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        userSingUpFragment = new UserSingUpFragment();
        transaction.replace(R.id.singfragment, userSingUpFragment);
        transaction.commit();
    }



    private void goTologin() {
        subscriber = new Subscriber<LoginResult>() {


            @Override
            public void onCompleted() {
                //Toast.makeText(getContext(),"连接成功！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), e.getMessage().toString() + "网络错误！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(LoginResult loginResult) {


                String SessionID = loginResult.getSessionID();
                if (SessionID != null) {
                    String phone = user_reg_phone.getText().toString();
                    connect(getToken());
                    saveUserInfo(SessionID, phone);
                } else {
                    Toast.makeText(getContext(), "账号或者密码错误！", Toast.LENGTH_LONG).show();
                    checkLoginApplication = (CheckLoginApplication) getActivity().getApplication();
                    checkLoginApplication.setIsLogin(false);
                }


            }
        };


        String phone = user_reg_phone.getText().toString().trim();
        String password = user_reg_password.getText().toString().trim();
        boolean issuccessful = false;
        String identifier = "0";
        LoginMethods.getInstance().goToLogin(subscriber, phone, password);

    }

    private void saveUserInfo(String SessionID, String phone) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userinfo", getContext().MODE_APPEND);
        SharedPreferences.Editor editUserInfo = sharedPreferences.edit();
        editUserInfo.putString("phone", phone);
        editUserInfo.putString("SessionID", SessionID);
        editUserInfo.apply();
        Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_LONG).show();

        checkLoginApplication = (CheckLoginApplication) getActivity().getApplication();
        checkLoginApplication.setIsLogin(true);
        Intent intent = new Intent(getContext(), UserInfoActivity.class);
        startActivity(intent);
    }

    private void checkInput() {
        String checkphone = user_reg_password.getText().toString();
        String checkpassword = user_reg_password.getText().toString();
        if (checkphone == null || checkpassword == null) {
            Toast.makeText(getContext(), "请输入用户名或者密码！", Toast.LENGTH_LONG).show();
        } else {
            goTologin();
        }


    }


    private void connect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.e("融云连接", "--onSuccess:" + userid);
                if (RongIM.getInstance()!=null){
                    RongIM.getInstance().setCurrentUserInfo(
                            new UserInfo(
                                    userid.equals("13283701885")?"13283701885":"18838185470",
                                    userid.equals("13283701885")?"移动二班":"传媒三班",
                                    userid.equals("13283701885")?
                                            Uri.parse("https://www.zhuangbi.info/uploads/i/2017-07-13-1dd0d8268463835bdc2b1fbcfb350439.jpeg"):
                                            Uri.parse("https://www.zhuangbi.info/uploads/i/2017-07-01-d52ad518ea2bb361183211204ee0d73f.jpg")
                            ));
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                }
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("融云",errorCode.getMessage()+errorCode.toString());
            }
        });
    }



    private String  getToken(){
        //从服务器获取token
        if (user_reg_phone.getText().toString().equals("13283701885")){
            token="dziAn/ZKSIo+8/WPYF41dU/1v4r/WO73IghY4ul0T0qqnLKdVqgbMK9xULPy8pg2lWEmrk863tzeutRR0mEwi5vJCbmUH/n6";
        }else if(user_reg_phone.getText().toString().equals("18838185470")){
            token="QyaSIMOnCzf0rluYWc+neE/1v4r/WO73IghY4ul0T0qqnLKdVqgbMLQJX6tUzdD3iEhvXlzvhu7eutRR0mEwi5vJCbmUH/n6";
        }
        return token;
    }

}