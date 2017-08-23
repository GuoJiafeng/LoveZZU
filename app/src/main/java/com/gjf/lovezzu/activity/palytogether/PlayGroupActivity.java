package com.gjf.lovezzu.activity.palytogether;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.CommResult;
import com.gjf.lovezzu.entity.UserInfoResult;
import com.gjf.lovezzu.entity.playtogether.GroupDataBridging;
import com.gjf.lovezzu.network.GroupMethods;
import com.gjf.lovezzu.view.CircleImageView;
import com.gjf.lovezzu.view.PlayGroupDynamicAdapter;
import com.gjf.lovezzu.view.PlayGroupUserAdapter;
import com.gjf.lovezzu.view.PlayTogetherAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhaox on 2017/4/18.
 */

public class PlayGroupActivity extends AppCompatActivity {


    @BindView(R.id.play_info_image)
    ImageView playInfoImage;
    @BindView(R.id.play_info_toolbar)
    Toolbar playInfoToolbar;
    @BindView(R.id.play_info_introduce)
    TextView playInfoIntroduce;
    @BindView(R.id.play_info_users_num)
    TextView playInfoUsersNum;
    @BindView(R.id.play_info_users_recycler)
    RecyclerView playInfoUsersRecycler;
    @BindView(R.id.play_info_news_recycler)
    RecyclerView playInfoNewsRecycler;
    @BindView(R.id.play_info_tag)
    TextView playInfoTag;
    @BindView(R.id.play_info_master_image)
    CircleImageView playInfoMasterImage;
    @BindView(R.id.play_info_master_name)
    TextView playInfoMasterName;
    @BindView(R.id.group_location)
    TextView groupLocation;
    @BindView(R.id.play_info_time)
    TextView playInfoTime;
    @BindView(R.id.play_info_collapsing_toolbar)
    CollapsingToolbarLayout playInfoCollapsingToolbar;
    private GroupDataBridging groupDataBridging;
    private PlayGroupUserAdapter playGroupUserAdapter;
    private PlayGroupDynamicAdapter playGroupDynamicAdapter;
    private Subscriber subscriber;
    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_info);
        ButterKnife.bind(this);
        groupDataBridging= (GroupDataBridging) getIntent().getSerializableExtra("groupData");
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
        setSupportActionBar(playInfoToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        playInfoCollapsingToolbar.setTitle(groupDataBridging.getGroup().getName());
        playInfoCollapsingToolbar.setExpandedTitleColor(Color.parseColor("#2574e4"));
        initView();
    }

    private void initView(){
        String images=groupDataBridging.getGroup().getPicture();
        String url[]=images.split("ZZU");
        Glide.with(this)
                .load(LOGIN_URL + "filedownload?action=一起玩&imageURL=" + url[0])
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(playInfoImage);
        playInfoIntroduce.setText(groupDataBridging.getGroup().getIntroduce());
        playInfoUsersNum.setText(groupDataBridging.getMemberInfo().size()+"");
        playInfoTag.setText(groupDataBridging.getGroup().getLabel());
        Glide.with(this)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + groupDataBridging.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(playInfoMasterImage);
        playInfoMasterName.setText(groupDataBridging.getUserinfo().getNickname());
        groupLocation.setText(groupDataBridging.getGroup().getCampus());
        playInfoTime.setText(groupDataBridging.getGroup().getDate());



        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        String dynamicImg=groupDataBridging.getTalkImg();
        if (!dynamicImg.trim().equals("")){
            String dynamicUrl[]=dynamicImg.split("ZZU");
            List<String> dynamicImages=new ArrayList<>();
            for (int i=0;i<dynamicUrl.length;i++){
                if (i<10){
                    dynamicImages.add(dynamicUrl[i]);
                }else {
                    break;
                }
            }
            playInfoNewsRecycler.setLayoutManager(layoutManager);
            playGroupDynamicAdapter=new PlayGroupDynamicAdapter(dynamicImages);
            playInfoNewsRecycler.setAdapter(playGroupDynamicAdapter);
        }

        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        List<UserInfoResult> userInfoResultList=groupDataBridging.getMemberInfo();
        playGroupUserAdapter=new PlayGroupUserAdapter(userInfoResultList,this);
        playInfoUsersRecycler.setLayoutManager(layoutManager1);
        playInfoUsersRecycler.setAdapter(playGroupUserAdapter);
    }

    @OnClick({R.id.play_info_news_layout, R.id.play_info_master_info, R.id.play_info_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_info_news_layout:
                Intent intent=new Intent(this,GroupDynamicActivity.class);
                intent.putExtra("groupId",groupDataBridging.getGroup().getGroupId()+"");
                startActivity(intent);
                break;
            case R.id.play_info_master_info:
                break;
            case R.id.play_info_join:
               joinGroup();
                break;
        }
    }

    private void joinGroup(){
            subscriber=new Subscriber<CommResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e("加入群组",e.getMessage());
                }

                @Override
                public void onNext(CommResult commResult) {
                    Log.e("加入群组",commResult.getSuccessful()+"");
                    if (commResult.getSuccessful()){
                        Log.e("加入群组","成功");
                        Toast.makeText(PlayTogetherActivity.playTogetherActivity,"加入成功！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PlayTogetherActivity.playTogetherActivity,"已经加入过了！",Toast.LENGTH_SHORT).show();
                        Log.e("加入群组","已经加入群组");
                    }
                }


            };
            GroupMethods.getInstance().joinGroup(subscriber,SessionID,"加入群组",groupDataBridging.getGroup().getGroupId()+"");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
