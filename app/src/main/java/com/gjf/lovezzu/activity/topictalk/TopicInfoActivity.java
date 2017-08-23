package com.gjf.lovezzu.activity.topictalk;

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
import com.gjf.lovezzu.entity.taoyu.GoodsImages;
import com.gjf.lovezzu.entity.topic.TopicCommBridging;
import com.gjf.lovezzu.entity.topic.TopicCommentData;
import com.gjf.lovezzu.entity.topic.TopicDataBridging;
import com.gjf.lovezzu.network.TopicCommMethods;
import com.gjf.lovezzu.view.TopicInfoCommAdapter;
import com.gjf.lovezzu.view.TopicInfoImagesAdapter;

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
 * Created by lenovo047 on 2017/5/27.
 */

public class TopicInfoActivity extends AppCompatActivity {


    @BindView(R.id.topic_theme_name)
    TextView topicThemeName;
    @BindView(R.id.topic_info_images)
    RecyclerView topicInfoImages;
    @BindView(R.id.topic_info_title)
    TextView topicInfoTitle;
    @BindView(R.id.topic_info_time)
    TextView topicInfoTime;
    @BindView(R.id.topic_info_user_name)
    TextView topicInfoUserName;
    @BindView(R.id.topic_info_text)
    TextView topicInfoText;
    @BindView(R.id.topic_info_comm_image)
    ImageView topicInfoCommImage;
    @BindView(R.id.topic_info_comm)
    TextView topicInfoComm;
    @BindView(R.id.topic_info_zan_image)
    ImageView topicInfoZanImage;
    @BindView(R.id.topic_info_zan)
    TextView topicInfoZan;
    @BindView(R.id.topic_info_comments)
    RecyclerView topicInfoComments;
    @BindView(R.id.topic_info_refresh)
    SwipeRefreshLayout topicInfoRefresh;
    @BindView(R.id.edit_comments)
    EditText editComments;
    @BindView(R.id.send)
    TextView send;

    public static TopicInfoActivity topicInfoActivity;
    private Subscriber subscriber;
    private List<TopicCommBridging> commBridgings=new ArrayList<>();
    private TopicInfoCommAdapter topicInfoCommAdapter;
    private String SessionID;
    private TopicDataBridging topicDataBridging;
    private TopicInfoImagesAdapter topicInfoImagesAdapter;
    private List<GoodsImages> goodsImages=new ArrayList<>();
    private String typeZan="1";
    private String topicId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_info_activity);
        ButterKnife.bind(this);
        topicInfoActivity=this;
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
        topicDataBridging= (TopicDataBridging) getIntent().getSerializableExtra("topicInfo");
        topicId=topicDataBridging.getTopic().getTopicId().toString();
        initView();
        getComm();
        showComm();
        topicInfoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getComm();
            }
        });
    }




    @OnClick({R.id.topic_info_comm_image, R.id.topic_info_zan_image, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topic_info_comm_image:
                editComments.requestFocus();
                break;
            case R.id.topic_info_zan_image:
                addThum();
                break;
            case R.id.send:
                if (editComments.getText().toString().trim().equals("")){
                    Toast.makeText(this,"评论不能为空!",Toast.LENGTH_SHORT).show();
                }else {
                    addComm();
                }
                break;
        }
    }

    private void initView(){
        String images=topicDataBridging.getTopic().getTopicImg();
        String url[]=images.split("ZZU");
        goodsImages.clear();
        for (int i = 0; i < url.length; i++) {
            String imagesName = url[i];
            GoodsImages goodsImage = new GoodsImages(LOGIN_URL + "filedownload?action=话题圈&imageURL=" + imagesName);
            goodsImages.add(goodsImage);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topicInfoImages.setLayoutManager(layoutManager);
        topicInfoImagesAdapter=new TopicInfoImagesAdapter(goodsImages);
        topicInfoImages.setAdapter(topicInfoImagesAdapter);

        topicThemeName.setText("话题");
        topicInfoTitle.setText(topicDataBridging.getTopic().getTopicTitle());
        topicInfoTime.setText(topicDataBridging.getTopic().getDate());
        topicInfoUserName.setText(topicDataBridging.getUserinfo().getNickname());
        topicInfoText.setText(topicDataBridging.getTopic().getTopicText());
        topicInfoComm.setText(topicDataBridging.getTopic().getTopicCommentCount()+"");
        topicInfoZan.setText(topicDataBridging.getTopic().getTopicThumbCount()+"");




    }
    private void showComm(){
        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        topicInfoComments.setLayoutManager(layoutManager1);
        topicInfoCommAdapter=new TopicInfoCommAdapter(commBridgings);
        topicInfoComments.setAdapter(topicInfoCommAdapter);
    }

    private void getComm(){
        subscriber=new Subscriber<TopicCommentData>() {
            @Override
            public void onCompleted() {
                if (topicInfoRefresh.isRefreshing()){
                    topicInfoRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("评论==",e.getMessage());
            }

            @Override
            public void onNext(TopicCommentData topicCommentData) {
                List<TopicCommBridging> list=topicCommentData.getValues();
                if (list.size()!=0){
                    commBridgings.clear();
                    commBridgings.addAll(list);
                    topicInfoCommAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TopicInfoActivity.topicInfoActivity,"还没有评论，快来吐槽吧！",Toast.LENGTH_SHORT).show();
                }
            }
        };
        TopicCommMethods.getInstance().getComm(subscriber,"查询话题评论",topicDataBridging.getTopic().getTopicId()+"");
    }

    private void addComm(){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"TopicCommentAction");
        requestParams.addBodyParameter("TopicId",topicId);
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("action","发布话题评论");
        requestParams.addBodyParameter("TopicComment",editComments.getText().toString());

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("话题评论",result);
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(TopicInfoActivity.topicInfoActivity,"评论成功!",Toast.LENGTH_SHORT).show();
                        int comm=Integer.parseInt(topicInfoComm.getText().toString());
                        topicInfoComm.setText((comm+1)+"");
                        editComments.setText("");
                    }else {
                        Toast.makeText(TopicInfoActivity.topicInfoActivity,"请重新登录并检查网络是否通畅!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("话题评论",ex.getMessage());
                Toast.makeText(TopicInfoActivity.topicInfoActivity,"请重新登录并检查网络是否通畅!",Toast.LENGTH_SHORT).show();
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
    private void addThum(){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"TopicCommentAction");
        requestParams.addBodyParameter("ThumbNum",typeZan);
        requestParams.addBodyParameter("TopicId",topicId);
        requestParams.addBodyParameter("action","话题点赞");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        int zan=Integer.parseInt(topicInfoZan.getText().toString());
                        if (typeZan.equals("1")){
                            Toast.makeText(TopicInfoActivity.topicInfoActivity,"+1 再点一次取消点赞！",Toast.LENGTH_SHORT).show();
                            topicInfoZan.setText((zan+1)+"");
                        }else {
                            Toast.makeText(TopicInfoActivity.topicInfoActivity,"-1",Toast.LENGTH_SHORT).show();
                            topicInfoZan.setText((zan-1)+"");
                        }
                    }else {
                        Toast.makeText(TopicInfoActivity.topicInfoActivity,"请重新登录并检查网络是否通畅!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("话题点赞",ex.getMessage());
                Toast.makeText(TopicInfoActivity.topicInfoActivity,"请重新登录并检查网络是否通畅!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (typeZan.equals("1")){
                    typeZan="0";
                }else {
                    typeZan="1";
                }
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}