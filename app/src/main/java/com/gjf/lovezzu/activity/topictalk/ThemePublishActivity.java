package com.gjf.lovezzu.activity.topictalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.PhotoAdapter;
import com.gjf.lovezzu.view.RecyclerItemClickListener;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/11.
 */

public class ThemePublishActivity extends AppCompatActivity {
    @BindView(R.id.theme_pub_back)
    TextView themePubBack;
    @BindView(R.id.theme_pub_commit)
    TextView themePubCommit;
    @BindView(R.id.theme_name)
    EditText themeName;
    @BindView(R.id.theme_publish_addimage)
    FrameLayout themePublishAddimage;
    @BindView(R.id.theme_publish_images)
    RecyclerView themePublishImages;
    private static ProgressDialog progressDialog;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static List<File>  photoList=new ArrayList<File>();
    private static List<String> photosURL=new ArrayList<String>();
    private String SessionID;
    private String themeID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_publish_activity);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");

    }

    @OnClick({R.id.theme_pub_back, R.id.theme_pub_commit, R.id.theme_name, R.id.theme_publish_addimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.theme_pub_back:
                Intent intent=new Intent(this,TopicTalkActivity.class);
                startActivity(intent);
                break;
            case R.id.theme_pub_commit:
                publishTheme();
                break;
            case R.id.theme_publish_addimage:
                getImages();
                Toast.makeText(getApplicationContext(),"只可上传一张主题图片！",Toast.LENGTH_LONG).show();
                break;
        }
    }



    private void publishTheme(){
        if (themeName.getText()!=null){
            RequestParams requestParams=new RequestParams(LOGIN_URL+"ThemeAction");
            requestParams.addBodyParameter("ThemeTitle",themeName.getText().toString());
            requestParams.addBodyParameter("SessionID",SessionID);
            requestParams.addBodyParameter("action","发布主题");

            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("话题圈============主题信息发布result",result);
                    if (!result.isEmpty()){
                        themeID=result;
                        progressDialog=new ProgressDialog(ThemePublishActivity.this);
                        progressDialog.setTitle("正在上传信息");
                        progressDialog.setMessage("uploading....");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        upLoadImages(themeID);
                    }else {
                        Toast.makeText(getApplicationContext(),"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(getApplicationContext(),"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                    Log.e("话题圈============主题发布error",ex.getMessage());
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
        }else {
            Toast.makeText(this,"请完善信息！",Toast.LENGTH_SHORT).show();
        }
    }

    private void upLoadImages(String theme){
        Log.e("话题圈===========主题","执行上传");
        if (photosURL!=null&&photoList!=null){
            photoList.clear();
            for (String photo:photosURL){
                File file=new File(photo);
                photoList.add(file);
            }
        }
        RequestParams requestParams=new RequestParams(LOGIN_URL+"imagesupload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("ThemeId",theme);
        requestParams.addBodyParameter("action","上传主题图片");
        requestParams.addParameter("images",photoList);
        if (photoList.isEmpty()){
            Log.e("话题圈===========主题","图片为空");
        }
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("话题圈===========主题result",result);
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ThemePublishActivity.this,TopicTalkActivity.class);
                        startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("话题圈============主题发布error图片",ex.getMessage()+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                photosURL.clear();
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    private void getImages(){
        photoAdapter=new PhotoAdapter(this,selectedPhotos);

        themePublishImages.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.HORIZONTAL));
        themePublishImages.setAdapter(photoAdapter);

        PhotoPicker.builder()
                .setShowGif(true)
                .setPhotoCount(1)
                .start(ThemePublishActivity.this);
        themePublishImages.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(1)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(ThemePublishActivity.this);
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

}
