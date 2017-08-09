package com.gjf.lovezzu.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.entity.LoginResult;
import com.gjf.lovezzu.network.LoginMethods;

import rx.Subscriber;

/**
 * Created by BlackBeard丶 on 2017/03/08.
 */
public class CheckLogin extends Service {
    private Subscriber subscriber;
    private CheckLoginApplication checkLoginApplication;

    public CheckLogin() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("11111111111");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkLogin();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String SessionID = sharedPreferences.getString("SessionID", "");
        if (!SessionID.equals("")) {

            subscriber = new Subscriber<LoginResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(LoginResult loginResult) {
                    String SessionID = loginResult.getSessionID();
                    if (!SessionID.equals("")) {
                        checkLoginApplication = (CheckLoginApplication) getApplication();
                        checkLoginApplication.setIsLogin(true);
                    } else {
                        checkLoginApplication = (CheckLoginApplication) getApplication();
                        checkLoginApplication.setIsLogin(false);
                        editor.remove("SessionID");
                        editor.remove("phone");
                        editor.apply();

                    }
                }
            };
            LoginMethods.getInstance().checkLogin(subscriber, SessionID);
        } else {
            checkLoginApplication = (CheckLoginApplication) getApplication();
            checkLoginApplication.setIsLogin(false);
            editor.clear().apply();

        }

    }

}
