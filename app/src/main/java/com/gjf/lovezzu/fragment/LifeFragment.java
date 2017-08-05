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
import com.gjf.lovezzu.activity.schoolnewsActivity.SchoolNewsWebView;
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
        imageUrl.add("http://202.196.64.199/zzupic/p025.jpg");
        imageUrl.add("http://202.196.64.199/zzupic/p038.jpg");
        imageUrl.add("http://202.196.64.199/zzupic/p010.jpg");

    }

    //显示轮播
    private void showTopView() {
        flashView = (FlashView) view.findViewById(R.id.life_flush);
        flashView.setImageUris(imageUrl);
        flashView.setEffect(EffectConstants.DEFAULT_EFFECT);
        flashView.setOnPageClickListener(new FlashViewListener() {
            @Override
            public void onClick(int position) {
                String mUrl;
                Intent mintent=new Intent();
                mintent.setClass(getActivity(),SchoolNewsWebView.class);
                switch (position){
                    case 0:
                        Toast.makeText(view.getContext(), "北校区",
                                Toast.LENGTH_SHORT).show();
                        mUrl="http://www5.zzu.edu.cn/soft/";
                        mintent.putExtra("url",mUrl);
                        startActivity(mintent);
                        break;
                    case 1:
                        Toast.makeText(view.getContext(), "南校区",
                                Toast.LENGTH_SHORT).show();
                        mUrl="http://www5.zzu.edu.cn/gjxy/";
                        mintent.putExtra("url",mUrl);
                        startActivity(mintent);
                        break;
                    case 2:
                        Toast.makeText(view.getContext(), "新闻中心",
                                Toast.LENGTH_SHORT).show();
                        mUrl="http://news.zzu.edu.cn/";
                        mintent.putExtra("url",mUrl);
                        startActivity(mintent);
                        break;
                }

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