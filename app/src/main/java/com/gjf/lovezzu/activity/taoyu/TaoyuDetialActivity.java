package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.gjf.lovezzu.entity.Goods;
import com.gjf.lovezzu.entity.GoodsCommentsData;
import com.gjf.lovezzu.entity.GoodsCommentsDataBridging;
import com.gjf.lovezzu.entity.GoodsImages;
import com.gjf.lovezzu.network.TaoyuGoodsCommentsMethods;
import com.gjf.lovezzu.view.TaoyuGoodsCommentsAdapter;
import com.gjf.lovezzu.view.TaoyuGoodsImagesAdapter;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 *
 * finished by zhao on 2017/08/1
 */

public class TaoyuDetialActivity extends AppCompatActivity {

    @BindView(R.id.goods_photo_recycler)
    RecyclerView goodsPhotoRecycler;
    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    @BindView(R.id.goodsPosition)
    TextView goodsPosition;
    @BindView(R.id.goods_desc)
    TextView goodsDesc;
    @BindView(R.id.goods_talks)
    RecyclerView goodsTalks;
    @BindView(R.id.buybutton)
    ImageView buybutton;
    @BindView(R.id.edit_comments)
    EditText editComments;
    @BindView(R.id.send)
    TextView send;
    Unbinder unbinder;
    private SwipeRefreshLayout refreshLayout;

    private Goods goods;
    private List<GoodsImages> goodsImagesList = new ArrayList<>();
    private TaoyuGoodsImagesAdapter taoyuGoodsImagesAdapter;
    private List<GoodsCommentsDataBridging> goodsCommentsDataBridgingList=new ArrayList<>();
    private TaoyuGoodsCommentsAdapter taoyuGoodsCommentsAdapter;
    public static TaoyuDetialActivity taoyuDetialActivity;
    private Subscriber subscriber;
    private static String SessionID = "";
    private static String goods_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        taoyuDetialActivity = this;
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.goods_datail_refresh);
        unbinder=ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
        goods = (Goods) getIntent().getSerializableExtra("goods");
        goods_id=goods.getGoods_id().toString();

        initView();
        getGoodsComments();
        showComments();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                goodsCommentsDataBridgingList.clear();
                getGoodsComments();
                showComments();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    @OnClick({R.id.buybutton, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buybutton:

                addShopCar();
                break;
            case R.id.send:
                if (editComments.getText().toString().equals("")){
                    Toast.makeText(this,"评论不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    publishGoodsComments();
                }
                break;
        }
    }


    private void initView() {
        goodsName.setText(goods.getGname());
        goodsDesc.setText(goods.getGdescribe());
        goodsPosition.setText(goods.getGcampus());
        goodsPrice.setText(goods.getGprice());
        String images[] = goods.getImages();
        if (!goodsImagesList.isEmpty()) {
            goodsImagesList.clear();
        }
        for (int i = 0; i < images.length; i++) {
            String imagesName = images[i];
            GoodsImages goodsImages = new GoodsImages(LOGIN_URL + "filedownload?action=商品&imageURL=" + imagesName);
            goodsImagesList.add(goodsImages);
        }
        showphoto();

    }

    private void showphoto() {
        taoyuGoodsImagesAdapter = new TaoyuGoodsImagesAdapter(goodsImagesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        goodsPhotoRecycler.setLayoutManager(layoutManager);
        goodsPhotoRecycler.setAdapter(taoyuGoodsImagesAdapter);


    }

    private void showComments() {
        taoyuGoodsCommentsAdapter=new TaoyuGoodsCommentsAdapter(goodsCommentsDataBridgingList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        goodsTalks.setLayoutManager(layoutManager);
        goodsTalks.setAdapter(taoyuGoodsCommentsAdapter);
    }


    private void getGoodsComments() {
        subscriber = new Subscriber<GoodsCommentsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GoodsCommentsData goodsCommentsData) {
                List<GoodsCommentsDataBridging> list=goodsCommentsData.getValues();
                if (list.size()==0){
                    Toast.makeText(getApplicationContext(), "该商品还没有评论，快来吐槽吧！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    goodsCommentsDataBridgingList.addAll(list);
                    taoyuGoodsCommentsAdapter.notifyDataSetChanged();
                }
            }

        };

        TaoyuGoodsCommentsMethods.getInstance().getTaoyuGoodsComments(subscriber,"querycomments_L1",goods.getGoods_id().toString());
    }

    private void publishGoodsComments() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "comments_L1Action");
        requestParams.addBodyParameter("action", "postcomments_L1");
        requestParams.addBodyParameter("goods_id", goods.getGoods_id().toString());
        requestParams.addBodyParameter("comments", editComments.getText().toString());
        requestParams.addBodyParameter("SessionID", SessionID);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

               if (result!=null){
                   Toast.makeText(TaoyuDetialActivity.taoyuDetialActivity,"评论成功！",Toast.LENGTH_SHORT).show();
                   editComments.setText("");
               }else {
                   Toast.makeText(TaoyuDetialActivity.taoyuDetialActivity,"评论失败，请检查网络!",Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

    private void addShopCar(){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"cartAction");
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("goods_id",goods_id);
        requestParams.addBodyParameter("count","1");
        requestParams.addBodyParameter("action","添加");

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getApplicationContext(),"已加入购物车 +1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
