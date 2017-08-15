package com.gjf.lovezzu.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjf.lovezzu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class SchoolFragment extends Fragment {
    @BindView(R.id.in_school)
    TextView inSchool;
    @BindView(R.id.in_society)
    TextView inSociety;
    private School_shoolfragment school_shoolfragment;
    private Shool_societyfragment shool_societyfragment;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.school_fragment, container, false);
        ButterKnife.bind(this, view);
        school_shoolfragment = new School_shoolfragment();
        shool_societyfragment = new Shool_societyfragment();
        inSchool.setTextColor(Color.parseColor("#0090FD"));
        replaceFragment(school_shoolfragment);
        return view;
    }


    @OnClick({R.id.in_school, R.id.in_society})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_school:
                if (school_shoolfragment == null) {
                    school_shoolfragment = new School_shoolfragment();
                }
                inSchool.setTextColor(Color.parseColor("#0090FD"));
                inSociety.setTextColor(Color.parseColor("#000000"));

                replaceFragment(school_shoolfragment);
                break;
            case R.id.in_society:
                if (shool_societyfragment == null) {
                    shool_societyfragment = new Shool_societyfragment();
                }
                inSociety.setTextColor(Color.parseColor("#0090FD"));
                inSchool.setTextColor(Color.parseColor("#000000"));
                replaceFragment(shool_societyfragment);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.in_school_content, fragment);
        transaction.commit();
    }


}