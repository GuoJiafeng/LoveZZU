package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.taoyu.GoodsChildCommentsData;
import com.gjf.lovezzu.entity.taoyu.GoodsChildCommentsDateBridging;
import com.gjf.lovezzu.entity.taoyu.GoodsCommentsDataBridging;
import com.gjf.lovezzu.network.TaoyuGoodsChildCommetnsMethods;
import com.gjf.lovezzu.view.CircleImageView;
import com.gjf.lovezzu.view.TaoyuGoodsChildCommentsAdapter;

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
 * Created by zhao on 2017/8/1.
 */

public class TaoyuChildConmmentsActivity extends AppCompatActivity {


    @BindView(R.id.parent_comm_user_icon)
    CircleImageView parentCommUserIcon;
    @BindView(R.id.parent_comm_user_nickname)
    TextView parentCommUserNickname;
    @BindView(R.id.parent_comm_content)
    TextView parentCommContent;
    @BindView(R.id.parent_comm_time)
    TextView parentCommTime;
    @BindView(R.id.comment_main)
    LinearLayout commentMain;
    @BindView(R.id.chile_comment)
    RecyclerView chileComment;
    @BindView(R.id.edit_comments)
    EditText editComments;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.parent_comm_zan)
    TextView parentCommZan;
    ImageView parent_goods_zan;
    LinearLayout zan;
    @BindView(R.id.goods_child_refresh)
    TextView goodsChildRefresh;
    private Subscriber subscriber;
    private GoodsCommentsDataBridging parentComments;
    private TaoyuGoodsChildCommentsAdapter goodsChildCommentsAdapter;
    private List<GoodsChildCommentsDateBridging> goodsChildCommentsDateBridgingList = new ArrayList<>();
    private String SessionID;
    public static TaoyuChildConmmentsActivity taoyuChildConmmentsActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_child_comments);
        zan = (LinearLayout) findViewById(R.id.child_zan);
        ButterKnife.bind(this);
        taoyuChildConmmentsActivity=this;
        parentComments = (GoodsCommentsDataBridging) getIntent().getSerializableExtra("parentComm");
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
        parent_goods_zan= (ImageView) findViewById(R.id.parent_goods_zan);
        initView();
        getChildCommetns();
        showChildComments();
    }

    private void initView() {
        Glide.with(this)
                .load(LOGIN_URL + "filedownload?action=头像&imageURL=" + parentComments.getUserinfo().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(parentCommUserIcon);
        parentCommUserNickname.setText(parentComments.getUserinfo().getNickname());
        parentCommContent.setText(parentComments.getComments_L1().getComments());
        parentCommTime.setText(parentComments.getComments_L1().getCdate());
        parentCommZan.setText(parentComments.getComments_L1().getNum_thumb() + "");
        if (parentComments.getComments_L1().getThembed()){
            parentCommZan.setTextColor(Color.parseColor("#F48F0B"));
            parent_goods_zan.setImageResource(R.drawable.life_zan_done);
        }else {
            parentCommZan.setTextColor(Color.parseColor("#757575"));
            parent_goods_zan.setImageResource(R.drawable.life_zan);
        }

    }

    private void showChildComments() {
        goodsChildCommentsAdapter = new TaoyuGoodsChildCommentsAdapter(goodsChildCommentsDateBridgingList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chileComment.setLayoutManager(layoutManager);
        chileComment.setAdapter(goodsChildCommentsAdapter);
    }

    private void getChildCommetns() {
        subscriber = new Subscriber<GoodsChildCommentsData>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GoodsChildCommentsData goodsChildCommentsData) {

                List<GoodsChildCommentsDateBridging> list = goodsChildCommentsData.getValues();
                if (list.size() == 0) {
                    Toast.makeText(getApplicationContext(), "还没有评论，快来吐槽吧！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    goodsChildCommentsDateBridgingList.addAll(list);
                    goodsChildCommentsAdapter.notifyDataSetChanged();
                }
            }
        };
        TaoyuGoodsChildCommetnsMethods.getInsance().getChileComments(subscriber, "querycomments_L2", parentComments.getComments_L1().getL1_Cid() + "");
    }

    private void publishChildComments() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "comments_L2Action");
        requestParams.addBodyParameter("action", "postcomments_L2");
        requestParams.addBodyParameter("L1_Cid", parentComments.getComments_L1().getL1_Cid() + "");
        requestParams.addBodyParameter("L2_Cid", "");
        requestParams.addBodyParameter("SessionID", SessionID);
        requestParams.addBodyParameter("comments", editComments.getText().toString());
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Boolean res = jsonObject.getBoolean("isSuccessful");
                    if (res) {
                        Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
                        goodsChildCommentsAdapter.notifyDataSetChanged();
                        editComments.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "评论失败，请重新登录或检查网络是否通畅！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(),"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
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

    private void addThnum() {

        RequestParams requestParams = new RequestParams(LOGIN_URL + "comments_L2Action");
        requestParams.addBodyParameter("action", "postcomments_L2");
        requestParams.addBodyParameter("L1_Cid", parentComments.getComments_L1().getL1_Cid() + "");
        requestParams.addBodyParameter("L2_Cid", "");
        requestParams.addBodyParameter("SessionID", SessionID);
        requestParams.addBodyParameter("comments", "");
        requestParams.addBodyParameter("ThumbNum","1");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Boolean res = jsonObject.getBoolean("isSuccessful");

                    if (res) {

                            Toast.makeText(getApplicationContext(), "+1", Toast.LENGTH_SHORT).show();
                            Integer zanNum = Integer.parseInt(parentCommZan.getText().toString());
                            parentCommZan.setText((zanNum + 1)+"");
                        parentCommZan.setTextColor(Color.parseColor("#F48F0B"));
                        parent_goods_zan.setImageResource(R.drawable.life_zan_done);
                    } else {
                        Toast.makeText(getApplicationContext(), "已经点过赞了！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "请重新登录或检查网络是否通畅！", Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.send, R.id.child_zan,R.id.goods_child_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                if (editComments.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"评论不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    publishChildComments();
                }

                break;
            case R.id.child_zan:
                if (parentComments.getComments_L1().getThembed()){
                    Toast.makeText(getApplicationContext(),"已经点过赞了！",Toast.LENGTH_SHORT).show();
                }else {
                    addThnum();
                }
                break;
            case  R.id.goods_child_refresh:
                goodsChildRefresh.setTextColor(Color.parseColor("#CDC9C9"));
                Toast.makeText(getApplicationContext(),"正在刷新，请稍候",Toast.LENGTH_SHORT).show();
                goodsChildCommentsDateBridgingList.clear();
                getChildCommetns();
                goodsChildCommentsAdapter.notifyDataSetChanged();
                goodsChildRefresh.setTextColor(Color.parseColor("#fa851e"));
                break;

        }
    }
}
