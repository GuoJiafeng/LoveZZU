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

import com.gc.flashview.FlashView;
import com.gc.flashview.constants.EffectConstants;
import com.gc.flashview.listener.FlashViewListener;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.parttimejob.PartTimeJobActivity;
import com.gjf.lovezzu.activity.palytogether.PlayTogetherActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuActivity;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.activity.treehole.TreeHoleActivity;

import java.util.ArrayList;

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
    private FlashView flashView;
    private ArrayList<String> imageUrl = new ArrayList<String>();
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.life_fragment, container, false);
            initImage();
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
    private void initImage() {
        imageUrl.add("http://www.bz55.com/uploads1/allimg/120312/1_120312100435_8.jpg");
        imageUrl.add("http://www.237.cc/uploads/pcline/712_0_1680x1050.jpg");
        imageUrl.add("http://img2.pconline.com.cn/pconline/0706/19/1038447_34.jpg");

    }

    //显示轮播
    private void showTopView() {
        flashView = (FlashView) view.findViewById(R.id.life_flush);
        flashView.setImageUris(imageUrl);
        flashView.setEffect(EffectConstants.DEFAULT_EFFECT);
        flashView.setOnPageClickListener(new FlashViewListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(view.getContext(), "你的点击的是第" + (position + 1) + "张图片！",
                        Toast.LENGTH_SHORT).show();

            }
        });
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
}