package com.gjf.lovezzu.activity.treehole;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.treehole.TreeHole;
import com.gjf.lovezzu.entity.treehole.TreeHoleComm;
import com.gjf.lovezzu.entity.treehole.TreeHoleCommData;
import com.gjf.lovezzu.network.TreeHoleCommMethods;
import com.gjf.lovezzu.view.TreeHoleCommAdapter;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/16.
 */

public class TreeInfoActivity extends AppCompatActivity {


    @BindView(R.id.tree_info_content)
    TextView treeInfoContent;
    @BindView(R.id.tree_info_school)
    TextView treeInfoSchool;
    @BindView(R.id.tree_info_zan_image)
    ImageView treeInfoZanImage;
    @BindView(R.id.tree_info_zan)
    TextView treeInfoZan;
    @BindView(R.id.tree_info_talk_image)
    ImageView treeInfoTalkImage;
    @BindView(R.id.tree_info_talk)
    TextView treeInfoTalk;
    @BindView(R.id.tree_info_refresh)
    SwipeRefreshLayout treeInfoRefresh;
    @BindView(R.id.tree_info_comm)
    RecyclerView treeInfoComm;
    @BindView(R.id.edit_comments)
    EditText editComments;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.tree_info_date)
    TextView treeInfoDate;

    private Subscriber subscriber;
    private TreeHole treeHole=new TreeHole();
    private List<TreeHoleComm> treeHoleCommList=new ArrayList<>();
    private TreeHoleCommAdapter treeHoleCommAdapter;
    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_hole_info);
        ButterKnife.bind(this);
        treeHole= (TreeHole) getIntent().getSerializableExtra("treeHole");
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SessionID=sharedPreferences.getString("SessionID","");
        treeInfoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getComm();
            }
        });
        initView();
        showComm();
    }

    @OnClick({R.id.tree_info_zan_image, R.id.tree_info_talk_image,R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tree_info_zan_image:
                addThum();
                break;
            case R.id.tree_info_talk_image:
                editComments.requestFocus();
                break;
            case R.id.send:
                if (!editComments.getText().toString().trim().equals("")){
                    addComm();
                }else {
                    Toast.makeText(TreeHoleActivity.treeHoleActivity,"评论不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initView(){
        treeInfoContent.setText(treeHole.getTreeHoleContent());
        treeInfoSchool.setText(treeHole.getCampus());
        treeInfoZan.setText(treeHole.getThembCount()+"");
        treeInfoTalk.setText(treeHole.getCommentCount()+"");
        treeInfoDate.setText(treeHole.getDate());
        getComm();
    }

    private void showComm(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        treeInfoComm.setLayoutManager(layoutManager);
        treeHoleCommAdapter=new TreeHoleCommAdapter(treeHoleCommList);
        treeInfoComm.setAdapter(treeHoleCommAdapter);
    }

    private void getComm(){
        subscriber=new Subscriber<TreeHoleCommData>() {
            @Override
            public void onCompleted() {
                if (treeInfoRefresh.isRefreshing()){
                    treeInfoRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TreeHoleCommData treeHoleCommData) {

                List<TreeHoleComm> list=treeHoleCommData.getValues();
                if (!list.isEmpty()){
                    treeHoleCommList.clear();
                    treeHoleCommList.addAll(list);
                    treeHoleCommAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TreeHoleActivity.treeHoleActivity,"还没有评论，快来吐槽吧！",Toast.LENGTH_SHORT).show();
                }
            }

        };
        TreeHoleCommMethods.getInstance().getTreeHoleComm(subscriber,"查询树洞评论",treeHole.getTreeHoleId().toString());
    }

    private void addThum(){

        RequestParams requestParams=new RequestParams(LOGIN_URL + "TreeHoleCommentAction");
        requestParams.addBodyParameter("action","树洞点赞");
        requestParams.addBodyParameter("treeHoleId",treeHole.getTreeHoleId().toString());
        requestParams.addBodyParameter("SessionID",SessionID);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject json=new JSONObject(result);
                    Boolean res=json.getBoolean("isSuccessful");
                    if (res){
                        Integer zan=Integer.parseInt(treeInfoZan.getText().toString());
                        treeInfoZan.setText((zan+1)+"");
                        Toast.makeText(TreeHoleActivity.treeHoleActivity,"+1",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TreeHoleActivity.treeHoleActivity,"请重新登录！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(TreeHoleActivity.treeHoleActivity,"请检查网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void addComm(){
        RequestParams requestParams=new RequestParams(LOGIN_URL + "TreeHoleCommentAction");
        requestParams.addBodyParameter("action","发布树洞评论");
        requestParams.addBodyParameter("commentContent",editComments.getText().toString());
        requestParams.addBodyParameter("treeHoleId",treeHole.getTreeHoleId().toString());
        requestParams.addBodyParameter("SessionID",SessionID);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject json=new JSONObject(result);
                    Boolean res=json.getBoolean("isSuccessful");
                    if (res){
                        Integer comm=Integer.parseInt(treeInfoTalk.getText().toString());
                        treeInfoTalk.setText((comm+1)+"");
                        editComments.setText("");
                        Toast.makeText(TreeHoleActivity.treeHoleActivity,"+1",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TreeHoleActivity.treeHoleActivity,"请重新登录！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(TreeHoleActivity.treeHoleActivity,"请检查网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}
