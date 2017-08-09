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


import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.activity.parttimejob.PartTimeJobActivity;
import com.gjf.lovezzu.activity.palytogether.PlayTogetherActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuActivity;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.activity.treehole.TreeHoleActivity;
import com.gjf.lovezzu.entity.TopNewsResult;
import com.gjf.lovezzu.view.ImageViewHolder;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class LifeFragment extends Fragment {


    @BindView(R.id.life_taoyu)
    LinearLayout lifeTaoyu;
    @BindView(R.id.life_play)
    LinearLayout lifePlay;
    @BindView(R.id.life_talk)
    LinearLayout lifeTalk;
    @BindView(R.id.life_shudong)
    LinearLayout lifeShudong;
    @BindView(R.id.life_jianzhi)
    LinearLayout lifeJianzhi;
    private List<TopNewsResult> topNewsResults=new ArrayList<>();
   private ConvenientBanner convenientBanner;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.life_fragment, container, false);
            convenientBanner= (ConvenientBanner) view.findViewById(R.id.life_flush);
            getTopNews();
            showTopView();
        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }
        ButterKnife.bind(this, view);
        return view;

    }

    //加载轮播图片
    private void getTopNews() {
        if (topNewsResults!=null){
            topNewsResults.clear();
        }
        /*=========================*/
    }

    //显示轮播
    private void showTopView() {
        convenientBanner.setPages(new CBViewHolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        },topNewsResults).setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                webView(topNewsResults.get(position).getNewsUrl());
            }
        });
    }
    private void webView(String url){
        new  FinestWebView.Builder(MainActivity.mainActivity)
                .webViewSupportZoom(true)
                .webViewBuiltInZoomControls(true)
                .show(url);
    }

    @OnClick({R.id.life_taoyu, R.id.life_play, R.id.life_talk, R.id.life_shudong, R.id.life_jianzhi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.life_taoyu:
                Toast.makeText(getActivity().getApplicationContext(), "淘鱼", Toast.LENGTH_SHORT).show();
                goToTaoYu();
                break;
            case R.id.life_play:
                Intent play_intent = new Intent(getActivity().getApplicationContext(), PlayTogetherActivity.class);
                startActivity(play_intent);
                break;
            case R.id.life_talk:
                Intent intent = new Intent(getActivity().getApplicationContext(), TopicTalkActivity.class);
                startActivity(intent);
                break;
            case R.id.life_shudong:
                Intent treeHole_intent = new Intent(getActivity().getApplicationContext(), TreeHoleActivity.class);
                startActivity(treeHole_intent);
                break;
            case R.id.life_jianzhi:
                Intent partTimeJob_intent = new Intent(getActivity().getApplicationContext(), PartTimeJobActivity.class);
                startActivity(partTimeJob_intent);
                break;
        }
    }

    private void goToTaoYu() {
        Intent intent = new Intent(getContext(), TaoyuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (convenientBanner!=null){
            convenientBanner.stopTurning();
        }
    }
}