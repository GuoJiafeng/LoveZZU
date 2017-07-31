package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.gjf.lovezzu.entity.GoodsImages;
import com.gjf.lovezzu.view.TaoyuGoodsImagesAdapter;

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
 * Created by BlackBeard丶 on 2017/04/18.
 * finished by zhao
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
    private Goods goods;
    private List<GoodsImages> goodsImagesList = new ArrayList<>();
    private TaoyuGoodsImagesAdapter taoyuGoodsImagesAdapter;
    public static TaoyuDetialActivity taoyuDetialActivity;
    private Subscriber subscriber;
    private static String SessionID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        taoyuDetialActivity = this;
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
        goods = (Goods) getIntent().getSerializableExtra("goods");
        Log.e("商品详情---------------", goods.getGoods_id() + "");
        initView();


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

    }


    private void getGoodsComments() {
        subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };
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
                Log.e("商品评论---------------------------success", result.toString());
               if (result!=null){
                   Toast.makeText(TaoyuDetialActivity.taoyuDetialActivity,"评论成功！",Toast.LENGTH_SHORT).show();
                   editComments.setText("");
               }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("商品评论---------------------------error", ex.getMessage().toString());
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

    @OnClick({R.id.goods_talks, R.id.buybutton, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goods_talks:

                break;
            case R.id.buybutton:
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

}
