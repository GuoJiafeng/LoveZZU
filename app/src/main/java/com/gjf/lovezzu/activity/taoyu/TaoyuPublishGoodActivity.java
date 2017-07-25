package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.PhotoAdapter;
import com.gjf.lovezzu.view.RecyclerItemClickListener;
import com.gjf.lovezzu.view.WheelView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by BlackBeard丶 on 2017/04/20.
 */

public class TaoyuPublishGoodActivity extends AppCompatActivity {
    @BindView(R.id.go_back)
    ImageView goBack;
    @BindView(R.id.goods_commit)
    TextView goodsCommit;
    @BindView(R.id.goods_name)
    EditText goodsName;
    @BindView(R.id.goods_desc)
    EditText goodsDesc;
    @BindView(R.id.recycler_view_1)
    RecyclerView recyclerView1;
    @BindView(R.id.goods_price)
    EditText goodsPrice;
    @BindView(R.id.goods_class)
    TextView goodsClass;
    @BindView(R.id.goods_school)
    EditText goodsSchool;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    @BindView(R.id.taoyu_publish_addimage)
    FrameLayout taoyu_publish_addimage;

    private String goods_class="商品种类";
    private static final String[] GOODS_CLASS={"学校","出行","娱乐"};
    private android.app.AlertDialog goods_alertDialog;
    private static String SessionID="";
    private static String goodsId="";
    private static File[] photos;
    private static List<String> photosURL=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taoyu_fabu);
        getSessionID();
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @OnClick({R.id.taoyu_publish_addimage})
    public void OnCliCk(View view) {
        switch (view.getId()) {
            case R.id.taoyu_publish_addimage:
                upLoadImage();

                break;
        }


    }

    private void getSessionID() {
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_APPEND);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        SessionID=sharedPreferences.getString("SessionID","");

    }

    private void upGoodsInfo(){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"publishgoodsAction");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("Gtype",goodsClass.getText().toString());
        requestParams.addBodyParameter("Gname",goodsName.getText().toString());
        requestParams.addBodyParameter("Gdescribe",goodsDesc.getText().toString());
        requestParams.addBodyParameter("Gprice",goodsClass.getText().toString());
        //requestParams.addBodyParameter("Gtype",goodsClass.getText().toString());


        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Log.e("上传商品信息-----------",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    goodsId=jsonObject.getString("goods_id");
                    Log.e("上传商品信息的ID-----------",goodsId);
                    upGoodsImages();
                } catch (JSONException e) {
                    Log.e("上传商品信息返回的JSON解析-----------",e.getMessage());
                }finally {

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("上传商品信息------------错误信息",ex.getMessage().toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void upGoodsImages(){
        if (photosURL!=null&&photos!=null){
            int i=0;
            for (String photo:photosURL){
                File file=new File(photo);
                photos[i++]=file;
            }
        }


        RequestParams requestParams=new RequestParams(LOGIN_URL+"imagesupload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("goods_id",goodsId);
        requestParams.addBodyParameter("action","上传图片");


        /*
        * File[]数组不能直接设置给requestParams
        * */

        if (photos!=null){
            for (int i=0;i<photos.length;i++){
                File photo=photos[i];
                requestParams.addBodyParameter("images",photo);
            }
        }

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                Log.e("上传商品图片+++++++++++",result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("上传商品图片++++++++++++错误信息",ex.getMessage().toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void upLoadImage() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_1);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        PhotoPicker.builder()
                .setPhotoCount(5)
                .start(TaoyuPublishGoodActivity.this);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(TaoyuPublishGoodActivity.this);
                            photoAdapter.notifyDataSetChanged();
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(TaoyuPublishGoodActivity.this);
                            photoAdapter.notifyDataSetChanged();
                        }
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                photosURL=photos;
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.go_back, R.id.goods_commit, R.id.goods_class})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                onDestroy();
                break;
            case R.id.goods_commit:
                upGoodsInfo();
                break;
            case R.id.goods_class:
                View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(1);
                wv.setItems(Arrays.asList(GOODS_CLASS));
                wv.setSeletion(1);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        Log.d("选择商品", "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                        goods_class=item;
                    }
                });

                goods_alertDialog= new android.app.AlertDialog.Builder(this)
                        .setTitle("商品种类")
                        .setView(outerView)
                        .show();
                TextView txtSure= (TextView) outerView.findViewById(R.id.txt_sure);
                txtSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodsClass.setText(goods_class);
                        goods_alertDialog.dismiss();
                    }
                });
                TextView txtCancle= (TextView) outerView.findViewById(R.id.txt_cancel);
                txtCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goods_alertDialog.dismiss();
                    }
                });
                break;
        }
    }
}
