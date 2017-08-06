package com.gjf.lovezzu.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.fragment.friends.FriendFragment;
import com.gjf.lovezzu.fragment.LifeFragment;
import com.gjf.lovezzu.fragment.MessageFragment;
import com.gjf.lovezzu.fragment.PersonFragment;
import com.gjf.lovezzu.fragment.SchoolFragment;
import com.gjf.lovezzu.service.CheckLogin;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


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
        FragmentManager fm = getFragmentManager();
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        schoolFragment = new SchoolFragment();
        transaction.replace(R.id.id_content, schoolFragment);
        transaction.commit();

    }

    private void checkLoin() {

        CheckLoginApplication checkLoginApplication = (CheckLoginApplication) getApplication();
        if (checkLoginApplication.isLogin() == false) {
            Intent startintent = new Intent(MainActivity.this, CheckLogin.class);
            startService(startintent);
        }
    }





}
