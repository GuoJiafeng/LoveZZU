package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.TopicTalkActivity;
import com.gjf.lovezzu.fragment.taoyu.TaoyuGoodsStudyTypeFragment;
import com.gjf.lovezzu.view.PhotoAdapter;
import com.gjf.lovezzu.view.RecyclerItemClickListener;
import com.gjf.lovezzu.view.WheelView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
    TextView goodsSchool;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    @BindView(R.id.taoyu_publish_addimage)
    FrameLayout taoyu_publish_addimage;

    private String goods_class="出行";
    private static final String[] GOODS_CLASS={"学习","出行","娱乐"};
    private String goods_school="郑州大学（主校区）";
    private static final String[] GOODS_SCHOOL={"郑州大学（主校区）","郑州大学（北校区）","郑州大学（南校区）","郑州大学（东校区）"};
    private android.app.AlertDialog goods_alertDialog;
    private android.app.AlertDialog goods_schoolDialog;
    private static ProgressDialog progressDialog;
    private static String SessionID="";
    private static String goodsId="";
    private static List<File>  photoList=new ArrayList<File>();
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
        requestParams.addBodyParameter("Gprice",goodsPrice.getText().toString());
        requestParams.addBodyParameter("Gcampus",goodsSchool.getText().toString());
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject=new JSONObject(result);
                    goodsId=jsonObject.getString("goods_id");
                    upGoodsImages();
                } catch (JSONException e) {

                }finally {

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
        });

    }

    private void upGoodsImages(){
        if (photosURL!=null&&photoList!=null){
            for (String photo:photosURL){
                File file=new File(photo);
                photoList.add(file);
            }
        }


        RequestParams requestParams=new RequestParams(LOGIN_URL+"imagesupload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("SessionID",SessionID);
        requestParams.addBodyParameter("goods_id",goodsId);
        requestParams.addBodyParameter("action","上传商品图片");
        requestParams.addParameter("images",photoList);



        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"发布成功！",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(TaoyuPublishGoodActivity.this,TaoyuActivity.class);
                        startActivity(intent);
                        onStop();

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"发布失败！",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                }finally {

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
                photosURL.clear();
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
                                    .setPhotoCount(5)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
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
            photosURL.clear();
            if (photos != null) {
                photosURL=photos;
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.go_back, R.id.goods_commit, R.id.goods_class,R.id.goods_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                startActivity(new Intent(TaoyuPublishGoodActivity.this, TaoyuActivity.class));
                break;
            case R.id.goods_commit:
                if(goodsName.equals("")||goodsPrice.equals("")||photosURL==null){
                    Toast.makeText(getApplicationContext(),"请完善商品信息",Toast.LENGTH_SHORT).show();
                }else {
                    upGoodsInfo();
                    progressDialog=new ProgressDialog(TaoyuPublishGoodActivity.this);
                    progressDialog.setTitle("正在上传商品信息");
                    progressDialog.setMessage("uploading....");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
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
            case R.id.goods_school:
                View outerView1 = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wheel_view_wv);
                wv1.setOffset(1);
                wv1.setItems(Arrays.asList(GOODS_SCHOOL));
                wv1.setSeletion(1);
                wv1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        goods_school=item;
                    }
                });

                goods_schoolDialog= new android.app.AlertDialog.Builder(this)
                        .setTitle("选择校区")
                        .setView(outerView1)
                        .show();
                TextView txtSure1= (TextView) outerView1.findViewById(R.id.txt_sure);
                txtSure1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodsSchool.setText(goods_school);
                        goods_schoolDialog.dismiss();
                    }
                });
                TextView txtCancle1= (TextView) outerView1.findViewById(R.id.txt_cancel);
                txtCancle1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goods_schoolDialog.dismiss();
                    }
                });
                break;

        }
    }
}
