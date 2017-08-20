package com.gjf.lovezzu.activity.saylvoeActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;


import com.gjf.lovezzu.entity.saylove.Saylove;
import com.gjf.lovezzu.entity.saylove.SayloveReply;
import com.gjf.lovezzu.entity.saylove.SayloveReplyData;
import com.gjf.lovezzu.network.SayloveReplyMethods;
import com.gjf.lovezzu.view.SaylovereplyAdapter;

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

public class SayloveReplyActivity extends AppCompatActivity {
    public static SayloveReplyActivity saylovereplyActivity;
    @BindView(R.id.saylover)
    TextView saylover;
    @BindView(R.id.Besaidlover)
    TextView Besaidlover;

    @BindView(R.id.edit_comments)
    EditText editComments;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.saylovereplyRecyclerView)
    RecyclerView saylovereplyRecyclerView;
    @BindView(R.id.saylovereply_refresh)
    SwipeRefreshLayout saylovereplyRefresh;
    @BindView(R.id.saoylove_content)
    TextView saoyloveContent;
    private SaylovereplyAdapter saylovereplyAdapter;
    private List<SayloveReply> saylovereplylist = new ArrayList<>();
    private String SessionID;
    private Subscriber subscriber;
    private SayloveReply sayloveReply;
    private List<Saylove> saylovelist;
    private Saylove saylove = new Saylove();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saylove_reply);
        ButterKnife.bind(this);
        saylovereplyActivity = this;
        saylove = (Saylove) getIntent().getSerializableExtra("loveCard");
        saylovereplyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         getSayloveReply();
                                                     }
                                                 }
        );
        Log.e("表白", saylove.toString());
        Log.e("表白", saylove.getLoveCardId() + "");
        Log.e("表白", saylove.getLovedName());
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SessionID = sharedPreferences.getString("SessionID", "");
        ;
        initView();
        showSayloveReply();

    }

    private void initView() {
        saylover.setText(saylove.getSenderName());
        Besaidlover.setText(saylove.getLovedName());
        saoyloveContent.setText(saylove.getLoveContent());
        getSayloveReply();
    }


    private void showSayloveReply() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        saylovereplyRecyclerView.setLayoutManager(layoutManager);
        saylovereplyAdapter = new SaylovereplyAdapter(saylovereplylist, this);
        saylovereplyRecyclerView.setAdapter(saylovereplyAdapter);
        saylovereplyAdapter.notifyDataSetChanged();


    }

    @OnClick(R.id.send)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:

                if (editComments.getText().toString().trim() != null) {
                    addsayloveReply();
                } else {
                    Toast.makeText(SayloveReplyActivity.saylovereplyActivity, "评论不能为空", Toast.LENGTH_SHORT).show();


                }
                break;
        }

    }

    private void addsayloveReply() {

        RequestParams requestParams = new RequestParams(LOGIN_URL + "LoveCardCommentAction");
        requestParams.addBodyParameter("SessionID", SessionID);
        requestParams.addBodyParameter("loveCardId", saylove.getLoveCardId().toString());
        requestParams.addBodyParameter("commentContent", editComments.getText().toString());
        requestParams.addBodyParameter("action", "发布表白评论");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    Boolean res = json.getBoolean("isSuccessful");
                    if (res) {
                        editComments.setText("");
                        Toast.makeText(SayloveReplyActivity.saylovereplyActivity, "发布成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SayloveReplyActivity.saylovereplyActivity, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(SayloveReplyActivity.saylovereplyActivity, "请检查网络！", Toast.LENGTH_SHORT).show();
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


    public void getSayloveReply() {
        subscriber = new Subscriber<SayloveReplyData>() {
            @Override
            public void onCompleted() {
                if (saylovereplyRefresh.isRefreshing()) {
                    saylovereplyRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SayloveReplyData sayloveReplyData) {

                List<SayloveReply> list = sayloveReplyData.getValues();
                if (list.size() != 0) {
                    saylovereplylist.clear();
                    saylovereplylist.addAll(list);
                    saylovereplyAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SayloveReplyActivity.saylovereplyActivity, "还没有评论，快来支持吧", Toast.LENGTH_SHORT).show();

                }
            }
        };
        SayloveReplyMethods.getInstance().getSayloveReply(subscriber, "查询表白评论", saylove.getLoveCardId().toString());
    }


}
