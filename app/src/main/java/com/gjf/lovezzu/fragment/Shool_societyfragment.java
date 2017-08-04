package com.gjf.lovezzu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.SocietyNewsData;
import com.gjf.lovezzu.entity.SocietyNewsResult;
import com.gjf.lovezzu.network.HttpClientUtils;
import com.gjf.lovezzu.network.NewsMethods;
import com.gjf.lovezzu.view.SocietyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import rx.Subscriber;

/**
 * Created by lenovo047 on 2017/3/9.
 */

public class Shool_societyfragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return null;
    }

    private void onRegresh() {


    }

    //下拉刷新
    private void refreshView() {
    }

    //展示新闻
    private void showNews() {
    }







}







