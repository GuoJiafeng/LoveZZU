package com.gjf.lovezzu.activity.palytogether;

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

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.playtogether.DynamicComment;
import com.gjf.lovezzu.entity.playtogether.DynamicCommentData;
import com.gjf.lovezzu.entity.playtogether.DynamicImages;
import com.gjf.lovezzu.entity.playtogether.GroupDynamicResult;
import com.gjf.lovezzu.network.GroupMethods;
import com.gjf.lovezzu.view.CircleImageView;
import com.gjf.lovezzu.view.DynamicCommentAdapter;
import com.gjf.lovezzu.view.DynamicImagesAdapter;

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
 * Created by lenovo047 on 2017/8/22.
 */

public class DynamicInfoActivity extends AppCompatActivity {
    @BindView(R.id.dynamicinfo_username)
    TextView dynamicinfoUsername;
    @BindView(R.id.dynamicinfo_time)
    TextView dynamicinfoTime;
    @BindView(R.id.edit_comments)
    EditText editComments;
    GroupDynamicResult dynamicReault = new GroupDynamicResult();
    public static DynamicInfoActivity dynamicInfoActivity;
    @BindView(R.id.dynamicinfo_content)
    TextView dynamicinfoContent;
    @BindView(R.id.dynamicinfo_zan)
    TextView dynamicinfoZan;
    @BindView(R.id.dynamicinfo_commentnum)
    TextView dynamicinfoCommentnum;
    @BindView(R.id.dynamiccommentRefresh)
    SwipeRefreshLayout dynamiccommentRefresh;
    @BindView(R.id.dynamicinfo_userimg)
    CircleImageView dynamicinfoUserimg;
    @BindView(R.id.dynamiccommentRecyclerView)
    RecyclerView dynamiccommentRecyclerView;
    @BindView(R.id.send_comment)
    TextView sendComment;
    @BindView(R.id.dynamicinfo_talkimg)
    RecyclerView dynamicinfoTalkimg;
    @BindView(R.id.dynamic_info_zan)
    ImageView dynamicInfoZan;
    @BindView(R.id.dynamic_info_comm)
    ImageView dynamicInfoComm;
    private String SessionID;
    private Subscriber subscriber;
    private List<DynamicComment> dynamicCommentList = new ArrayList<>();
    private DynamicCommentAdapter dynamicCommentAdapter;
    private List<DynamicImages> dynamicImagesList = new ArrayList<>();
    private DynamicImagesAdapter dynamicImagesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_info);
        ButterKnife.bind(this);
        dynamicInfoActivity = this;
        dynamicReault = (GroupDynamicResult) getIntent().getSerializableExtra("dynamicId");
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SessionID = sharedPreferences.getString("SessionID", "");
        dynamiccommentRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDynamicComment();
            }
        });
        initView();
    }

    private void initView() {
        dynamicinfoTime.setText(dynamicReault.getGroupDynamic().getDate());
        dynamicinfoUsername.setText(dynamicReault.getUserinfo().getNickname());
        dynamicinfoContent.setText(dynamicReault.getGroupDynamic().getTalk());
        dynamicinfoZan.setText(dynamicReault.getGroupDynamic().getThembCount() + "");
        dynamicinfoCommentnum.setText(dynamicReault.getGroupDynamic().getCommentCount() + "");
        String images = dynamicReault.getGroupDynamic().getTalkImg();
        String url[] = images.split("ZZU");
        dynamicImagesList.clear();
        for (int i = 0; i < url.length; i++) {
            DynamicImages dynamicImages = new DynamicImages();
            dynamicImages.setDynamicImages(LOGIN_URL + "filedownload?action=一起玩&imageURL=" + url[i]);
            dynamicImagesList.add(dynamicImages);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dynamicinfoTalkimg.setLayoutManager(layoutManager);
        dynamicImagesAdapter = new DynamicImagesAdapter(dynamicImagesList);
        dynamicinfoTalkimg.setAdapter(dynamicImagesAdapter);
        Glide.with(DynamicInfoActivity.dynamicInfoActivity)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + dynamicReault.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(dynamicinfoUserimg);
        showDynamicComment();


    }

    private void showDynamicComment() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dynamiccommentRecyclerView.setLayoutManager(layoutManager);
        dynamicCommentAdapter = new DynamicCommentAdapter(dynamicCommentList, this);
        dynamiccommentRecyclerView.setAdapter(dynamicCommentAdapter);
        dynamicCommentAdapter.notifyDataSetChanged();
        getDynamicComment();
    }

    private void getDynamicComment() {
        subscriber = new Subscriber<DynamicCommentData>() {
            @Override
            public void onCompleted() {
                if (dynamiccommentRefresh.isRefreshing()) {
                    dynamiccommentRefresh.setRefreshing(false);
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DynamicCommentData dynamicCommentData) {
                List<DynamicComment> list = dynamicCommentData.getValues();
                if (list.size() != 0) {
                    dynamicCommentList.clear();
                    dynamicCommentList.addAll(list);
                    dynamicCommentAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "还没有评论，快来支持吧", Toast.LENGTH_SHORT).show();
                }
            }


        };
        GroupMethods.getInstance().getDynamicComment(subscriber, dynamicReault.getGroupDynamic().getDynamicId().toString(), "查询动态评论");
    }

    @OnClick({R.id.send_comment, R.id.dynamic_info_zan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_comment:

                if (editComments.getText().toString().trim() != null) {
                    addsayloveReply();
                } else {
                    Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "评论不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dynamic_info_zan:
                addThum();
                break;
        }

    }

    private void addsayloveReply() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "DynamicCommentAction");
        requestParams.addBodyParameter("SessionID", SessionID);
        requestParams.addBodyParameter("dynamicId", dynamicReault.getGroupDynamic().getDynamicId().toString());
        requestParams.addBodyParameter("comment", editComments.getText().toString());
        requestParams.addBodyParameter("action", "发表动态评论");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    Boolean res = json.getBoolean("isSuccessful");
                    if (res) {
                        editComments.setText("");
                        Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "发布成功", Toast.LENGTH_SHORT).show();
                        Integer zan = Integer.parseInt(dynamicinfoCommentnum.getText().toString() + "");
                        dynamicinfoCommentnum.setText((zan + 1) + "");
                        getDynamicComment();
                    } else {
                        Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "请检查网络！", Toast.LENGTH_SHORT).show();

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

    private void addThum() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "DynamicCommentAction");
        requestParams.addBodyParameter("SessionID", SessionID);
        requestParams.addBodyParameter("dynamicId", dynamicReault.getGroupDynamic().getDynamicId() + "");
        requestParams.addBodyParameter("action", "动态点赞");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("动态==点赞", result);
                try {
                    JSONObject json = new JSONObject(result);
                    boolean res = json.getBoolean("isSuccessful");
                    if (res) {
                        Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "+1", Toast.LENGTH_SHORT).show();
                        Integer zan = Integer.parseInt(dynamicinfoZan.getText().toString() + "");
                        dynamicinfoZan.setText((zan + 1) + "");
                    } else {
                        Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "请重新登录！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(DynamicInfoActivity.dynamicInfoActivity, "请检查网络！", Toast.LENGTH_SHORT).show();
                Log.e("动态==点赞", ex.getMessage());
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
