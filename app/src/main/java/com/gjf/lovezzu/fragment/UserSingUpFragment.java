package com.gjf.lovezzu.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.LoginResult;
import com.gjf.lovezzu.entity.friend.DemoApplication;
import com.gjf.lovezzu.network.SingInMethods;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import rx.Subscriber;

import static com.thefinestartist.utils.content.ContextUtil.getApplicationContext;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class UserSingUpFragment extends Fragment {

    @BindView(R.id.login_now)
    TextView login_now;
    @BindView(R.id.user_singup_button)
    TextView user_singuo_button;
    @BindView(R.id.user_reg_phone)
    TextView getuser_reg_phone;
    @BindView(R.id.user_reg_password)
    EditText getUser_reg_password;
    @BindView(R.id.reg_title_back)
    ImageView reg_title_back;
    private UserLoginFragmen userLoginFragmen;
    private View view;
    private Subscriber subscriber;

    String phoneNum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.usersingup_fragment, container, false);
        ButterKnife.bind(this, view);


        getuser_reg_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPage registerPage = new RegisterPage();
                //回调函数
                registerPage.setRegisterCallback(new EventHandler()
                {
                    public void afterEvent(int event, int result, Object data)
                    {
                        // 解析结果
                        if (result == SMSSDK.RESULT_COMPLETE)
                        {
                            //提交验证码成功
                            if (data instanceof Throwable) {
                                Throwable throwable = (Throwable)data;
                                String msg = throwable.getMessage();
                                Toast.makeText(MainActivity.mainActivity, msg, Toast.LENGTH_SHORT).show();
                            }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                            {
                                Toast.makeText(MainActivity.mainActivity,"短信验证成功",Toast.LENGTH_SHORT).show();
                                HashMap<String, Object> dataMaps = (HashMap<String, Object>) data;
                                phoneNum= (String) dataMaps.get("phone");
                                getuser_reg_phone.setText(phoneNum);
                            }/*else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){

                            }*/
                        }
                    }
                });
                registerPage.show(MainActivity.mainActivity);
            }
        });
        return view;
    }

    private void goTologin() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        userLoginFragmen = new UserLoginFragmen();
        transaction.replace(R.id.singfragment, userLoginFragmen);
        transaction.commit();
    }

   /* private void singUpIM(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(getuser_reg_phone.getText().toString().trim(),getUser_reg_password.getText().toString().trim());
                    Log.e("环信--注册成功","");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("环信--注册失败",e.getErrorCode()+", "+e.getMessage());
                }
            }
        }).start();
    }*/

   private void singUpIM(){

       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   EMClient.getInstance().createAccount(getuser_reg_phone.getText().toString().trim(),getUser_reg_password.getText().toString().trim());
                   Log.e("环信--注册成功","");
                   // 保存用户名
                   DemoApplication.getInstance().setCurrentUserName(getuser_reg_phone.getText().toString().trim());

               } catch (HyphenateException e) {
                   e.printStackTrace();
                   int errorCode=e.getErrorCode();
                   if(errorCode== EMError.NETWORK_ERROR){
                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                   }else if(errorCode == EMError.USER_ALREADY_EXIST){
                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                   }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
                   }else
                   Log.e("环信--注册失败",e.getErrorCode()+", "+e.getMessage());
               }
           }
       }).start();

   }

    private void goTosingup() {
        subscriber = new Subscriber<LoginResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginResult loginResult) {
                if (loginResult.isSuccessful()) {
                    Toast.makeText(getContext(), "注册成功", Toast.LENGTH_LONG).show();
                    goTologin();
                } else {
                    Toast.makeText(getContext(), "注册失败！", Toast.LENGTH_LONG).show();
                }
            }


        };
        String phone = getuser_reg_phone.getText().toString();
        String password = getUser_reg_password.getText().toString();

        boolean issuccessful = false;
        String identifier = "1";
        SingInMethods.getInstance().goToSingup(subscriber, identifier, issuccessful, phone, password);

    }

    @OnClick({R.id.login_now, R.id.user_singup_button, R.id.reg_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_now:
                goTologin();
                break;
            case R.id.user_singup_button:
                goTosingup();
                singUpIM();
                break;
            case R.id.reg_title_back:
                returnHome();
                break;
        }
    }
    //回到主页


    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onResume() {
        super.onResume();
        getuser_reg_phone.setText(phoneNum);
    }

    private void returnHome() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
