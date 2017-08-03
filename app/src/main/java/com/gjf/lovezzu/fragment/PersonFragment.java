package com.gjf.lovezzu.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.saylvoeActivity.SayLoveActivity;
import com.gjf.lovezzu.activity.UserInfoActivity;
import com.gjf.lovezzu.activity.UserLoginActivity;
import com.gjf.lovezzu.activity.UserSettingActivity;
import com.gjf.lovezzu.activity.taoyu.ShopcartActivity;
import com.gjf.lovezzu.entity.CheckLoginApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class PersonFragment extends Fragment {
    @Nullable
    @BindView(R.id.user_image)
    LinearLayout linearLayout;
    @BindView(R.id.my_info)
    LinearLayout my_info;
    @BindView(R.id.person_usersetting)
    LinearLayout person_usersetting;
    @BindView(R.id.person_saylove)
    LinearLayout person_saylove;
    @BindView(R.id.user_shop_car)
    LinearLayout person_shop_car;
    @BindView(R.id.user_shop_list)
    LinearLayout person_shop_list;

    private View view;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.person_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;

    }

    private void goTomyinfo() {
        Intent intent = new Intent(getContext(), UserInfoActivity.class);
        startActivity(intent);
    }

    public void goTologin() {
        Intent intent = new Intent(getContext(), UserLoginActivity.class);
        startActivity(intent);
    }

    public void goToSetting() {
        Intent intent = new Intent(getContext(), UserSettingActivity.class);
        startActivity(intent);
    }

    public void goToSayLove() {
        Intent intent = new Intent(getContext(), SayLoveActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.user_image, R.id.person_usersetting, R.id.person_saylove,R.id.user_shop_car,R.id.user_shop_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                CheckLoginApplication checkLoginApplication = (CheckLoginApplication) getActivity().getApplication();
                if (checkLoginApplication.isLogin()) {
                    goTomyinfo();
                } else {
                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_LONG).show();
                    goTologin();
                }
                break;
            case R.id.person_usersetting:
                goToSetting();
                break;
            case R.id.person_saylove:
                goToSayLove();
                break;
            case R.id.user_shop_car:
                Intent intent=new Intent(getContext(),ShopcartActivity.class);
                startActivity(intent);
                break;
            case R.id.user_shop_list:
                break;
        }
    }
}