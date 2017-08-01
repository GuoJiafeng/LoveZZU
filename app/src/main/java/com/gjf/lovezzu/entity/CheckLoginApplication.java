package com.gjf.lovezzu.entity;

import android.app.Application;



import org.xutils.x;

import io.rong.imkit.RongIM;


/**
 * zhao
 * Created by BlackBeard丶 on 2017/03/08.
 */
public class CheckLoginApplication extends Application {
    private boolean isLogin;

    public boolean isLogin() {
        return this.isLogin;
    }

    public void setIsLogin(boolean isLogining) {
        this.isLogin = isLogining;
    }

    @Override
    public void onCreate() {
        isLogin = false;

        super.onCreate();
        RongIM.init(this);
        x.Ext.init(this);
    }




}
