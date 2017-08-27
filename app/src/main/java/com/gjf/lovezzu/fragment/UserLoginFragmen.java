package com.gjf.lovezzu.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.activity.UserInfoActivity;
import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.entity.LoginResult;
import com.gjf.lovezzu.entity.friend.DemoApplication;
import com.gjf.lovezzu.entity.friend.DemoDBManager;
import com.gjf.lovezzu.network.LoginMethods;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import rx.Subscriber;

import static com.thefinestartist.utils.content.ContextUtil.getApplicationContext;


/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class UserLoginFragmen extends Fragment {
    private View view;
    private UserSingUpFragment userSingUpFragment;
    private Subscriber subscriber;
    private CheckLoginApplication checkLoginApplication;
    private static final String TAG = "环信登录";
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

    private String currentUsername;
    private String currentPassword;
    private boolean progressShow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userlogin_fragment, container, false);
        ButterKnife.bind(this, view);
        user_reg_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_NEXT){
                    user_reg_password.requestFocus();
                }
                return false;
            }
        });
        user_reg_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId==EditorInfo.IME_ACTION_GO){
                    checkInput();
                }
                return false;
            }
        });
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

        LoginMethods.getInstance().goToLogin(subscriber, phone, password);

    }



    public void login() {
        if (!EaseCommonUtils.isNetWorkConnected(MainActivity.mainActivity)) {
            Toast.makeText(MainActivity.mainActivity, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        currentUsername = user_reg_phone.getText().toString().trim();
        currentPassword = user_reg_password.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(MainActivity.mainActivity,R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(MainActivity.mainActivity, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(MainActivity.mainActivity);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();
        // reset current user name before login
        DemoApplication.getInstance().setCurrentUserName(currentUsername);
        // 调用sdk登陆方法登陆聊天服务器
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");
                if ( pd.isShowing()) {
                    pd.dismiss();
                }
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                getFriends();

                // 进入主页面
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);

            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }

                pd.dismiss();
                Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private  void  getFriends(){
        try {
            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            Map<String ,EaseUser> users=new HashMap<String ,EaseUser>();
            for(String username:usernames){
                EaseUser user=new EaseUser(username);
                users.put(username, user);
            }

            DemoApplication.getInstance().setContactList(users);

        } catch (HyphenateException e) {
            e.printStackTrace();
        }

    }




   /* private void loginIM(){
        EMClient.getInstance().login(user_reg_phone.getText().toString().trim(), user_reg_password.getText().toString().trim(), new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("环信--登录","登录成功");
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("环信--登陆失败",i +", "+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });


    }*/
    private void saveUserInfo(String SessionID, String phone) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userinfo", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editUserInfo = sharedPreferences.edit();
        editUserInfo.putString("phone", phone);
        editUserInfo.putString("SessionID", SessionID);
        editUserInfo.putBoolean("firstOpen",true);
        editUserInfo.apply();
        Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_LONG).show();
        checkLoginApplication = (CheckLoginApplication) getActivity().getApplication();
        checkLoginApplication.setIsLogin(true);
        login();
    }
    private void checkInput() {
        String checkphone = user_reg_password.getText().toString().trim();
        String checkpassword = user_reg_password.getText().toString();
        if (checkphone == null || checkpassword == null) {
            Toast.makeText(getContext(), "请输入用户名或者密码！", Toast.LENGTH_LONG).show();
        } else {
            goTologin();
        }


    }




}