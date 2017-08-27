package com.gjf.lovezzu.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.entity.friend.DemoApplication;
import com.gjf.lovezzu.entity.friend.InviteMessage;
import com.gjf.lovezzu.entity.friend.InviteMessgeDao;
import com.gjf.lovezzu.entity.friend.UserDao;
import com.gjf.lovezzu.fragment.friends.FriendFragment;
import com.gjf.lovezzu.fragment.LifeFragment;
import com.gjf.lovezzu.fragment.MessageFragment;
import com.gjf.lovezzu.fragment.PersonFragment;
import com.gjf.lovezzu.fragment.school.SchoolFragment;
import com.gjf.lovezzu.service.CheckLogin;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;
    LinearLayout mOne;

    LinearLayout mTwo;

    LinearLayout mThree;

    LinearLayout mFour;

    LinearLayout mFive;
    private CheckLoginApplication app;
    private SchoolFragment schoolFragment;
    private LifeFragment lifeFragment;
    private FriendFragment friendFragment;
    private MessageFragment messageFragment;
    private PersonFragment personFragment;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        checkLoin();
        // 创建EventHandler对象

        inviteMessgeDao = new InviteMessgeDao(MainActivity.this);
        userDao = new UserDao(MainActivity.this);
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        mOne = (LinearLayout) findViewById(R.id.onefragment);
        mTwo = (LinearLayout) findViewById(R.id.twofragment);
        mThree = (LinearLayout) findViewById(R.id.threefragment);
        mFour = (LinearLayout) findViewById(R.id.fourfragment);
        mFive = (LinearLayout) findViewById(R.id.fivefragment);


        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mFive.setOnClickListener(this);

        //设置初始的Fragment
        setDefaultFragment();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.onefragment:
                if (schoolFragment == null) {
                    schoolFragment = new SchoolFragment();

                }
                mOne.setBackgroundResource(R.drawable.tab_icon_school_selected);
                mTwo.setBackgroundResource(R.drawable.tab_icon_life);
                mThree.setBackgroundResource(R.drawable.tab_icon_friend);
                mFour.setBackgroundResource(R.drawable.tab_icon_message);
                mFive.setBackgroundResource(R.drawable.tab_icon_preson);
                //layout.setBackgroundColor(Color.BLUE);
                transaction.replace(R.id.id_content, schoolFragment);
                break;
            case R.id.twofragment:
                if (lifeFragment == null) {
                    lifeFragment = new LifeFragment();
                }
                // layout.setBackgroundColor(Color.YELLOW);
                mOne.setBackgroundResource(R.drawable.tab_icon_school);
                mTwo.setBackgroundResource(R.drawable.tab_icon_life_selected);
                mThree.setBackgroundResource(R.drawable.tab_icon_friend);
                mFour.setBackgroundResource(R.drawable.tab_icon_message);
                mFive.setBackgroundResource(R.drawable.tab_icon_preson);
                transaction.replace(R.id.id_content, lifeFragment);
                break;
            case R.id.threefragment:
                if (friendFragment == null) {
                    friendFragment = new FriendFragment();
                }

                mOne.setBackgroundResource(R.drawable.tab_icon_school);
                mTwo.setBackgroundResource(R.drawable.tab_icon_life);
                mThree.setBackgroundResource(R.drawable.tab_icon_friend_selected);
                mFour.setBackgroundResource(R.drawable.tab_icon_message);
                mFive.setBackgroundResource(R.drawable.tab_icon_preson);
                //layout.setBackgroundColor(Color.BLACK);
                transaction.replace(R.id.id_content, friendFragment);
                break;
            case R.id.fourfragment:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                }
                // layout.setBackgroundColor(Color.WHITE);
                mOne.setBackgroundResource(R.drawable.tab_icon_school);
                mTwo.setBackgroundResource(R.drawable.tab_icon_life);
                mThree.setBackgroundResource(R.drawable.tab_icon_friend);
                mFour.setBackgroundResource(R.drawable.tab_icon_message_selected);
                mFive.setBackgroundResource(R.drawable.tab_icon_preson);
                transaction.replace(R.id.id_content, messageFragment);
                break;
            case R.id.fivefragment:
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                }
                // layout.setBackgroundColor(Color.WHITE);
                mOne.setBackgroundResource(R.drawable.tab_icon_school);
                mTwo.setBackgroundResource(R.drawable.tab_icon_life);
                mThree.setBackgroundResource(R.drawable.tab_icon_friend);
                mFour.setBackgroundResource(R.drawable.tab_icon_message);
                mFive.setBackgroundResource(R.drawable.tab_icon_person_selected);
                transaction.replace(R.id.id_content, personFragment);
                break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();

    }



    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        schoolFragment = new SchoolFragment();
        transaction.replace(R.id.id_content, schoolFragment);
        transaction.commit();

    }

    private void checkLoin() {

       app = (CheckLoginApplication) getApplication();
        if (app.isLogin() == false) {
            Intent startintent = new Intent(MainActivity.this, CheckLogin.class);
            startService(startintent);
        }
    }

    /**
     * 保存并提示消息的邀请消息
     * @param msg
     */
    private void notifyNewIviteMessage(InviteMessage msg){
        if(inviteMessgeDao == null){
            inviteMessgeDao = new InviteMessgeDao(MainActivity.this);
        }
        inviteMessgeDao.saveMessage(msg);
        //保存未读数，这里没有精确计算
        inviteMessgeDao.saveUnreadMessageCount(1);
        // 提示有新消息
        //响铃或其他操作
    }


    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(final String username) {
            // 保存增加的联系人
            Map<String, EaseUser> localUsers = DemoApplication.getInstance().getContactList();
            Map<String, EaseUser> toAddUsers = new HashMap<String, EaseUser>();
            EaseUser user = new EaseUser(username);
            // 添加好友时可能会回调added方法两次
            if (!localUsers.containsKey(username)) {
                userDao.saveContact(user);
            }
            toAddUsers.put(username, user);
            localUsers.putAll(toAddUsers);
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "增加联系人：+"+username, Toast.LENGTH_SHORT).show();
                }


            });

        }

        @Override
        public void onContactDeleted(final String username) {
            // 被删除
            Map<String, EaseUser> localUsers = DemoApplication.getInstance().getContactList();
            localUsers.remove(username);
            userDao.deleteContact(username);
            inviteMessgeDao.deleteMessage(username);

            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "删除联系人：+"+username, Toast.LENGTH_SHORT).show();
                }


            });
        }

        @Override
        public void onContactInvited(final String username, String reason) {
            // 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不需要重复提醒
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals(username)) {
                    inviteMessgeDao.deleteMessage(username);
                }
            }
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            msg.setReason(reason);

            // 设置相应status
            msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
            notifyNewIviteMessage(msg);
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "收到好友申请：+"+username, Toast.LENGTH_SHORT).show();
                }


            });

        }

        @Override
        public void onFriendRequestAccepted(final String username) {
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getFrom().equals(username)) {
                    return;
                }
            }
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());

            msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
            notifyNewIviteMessage(msg);
            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "好友申请同意：+"+username, Toast.LENGTH_SHORT).show();
                }


            });
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            Log.d(s, s + "拒绝了你的好友请求");
        }
    }
}
