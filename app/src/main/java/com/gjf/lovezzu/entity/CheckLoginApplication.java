package com.gjf.lovezzu.entity;


import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.gjf.lovezzu.entity.friend.Constant;
import com.gjf.lovezzu.entity.friend.DemoApplication;
import com.gjf.lovezzu.entity.friend.Myinfo;
import com.gjf.lovezzu.entity.friend.UserDao;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.mob.MobApplication;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.hyphenate.easeui.ui.EaseBaiduMapActivity.instance;


/**
 * zhao
 * Created by BlackBeard丶 on 2017/03/08.
 */
public class CheckLoginApplication extends MobApplication {
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
        /*EaseUI.getInstance().init(this,initChatOptions());
        EMClient.getInstance().setDebugMode(true);*/

        x.Ext.init(this);
    }

    /*private EMOptions initChatOptions() {

        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        return options;
    }*/

}

