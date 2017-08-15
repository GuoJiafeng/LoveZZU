package com.gjf.lovezzu.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.fragment.UserLoginFragmen;
import com.gjf.lovezzu.fragment.UserSingUpFragment;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class UserLoginActivity extends AppCompatActivity {
    private UserLoginFragmen userLoginFragmen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setDefaultFragment();

    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        userLoginFragmen = new UserLoginFragmen();
        transaction.replace(R.id.singfragment, userLoginFragmen);
        transaction.commit();
    }
}
