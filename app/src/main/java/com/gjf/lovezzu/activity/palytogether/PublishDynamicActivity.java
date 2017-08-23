package com.gjf.lovezzu.activity.palytogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.PhotoAdapter;
import com.gjf.lovezzu.view.RecyclerItemClickListener;

import org.json.JSONException;
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
 * Created by zhao on 2017/8/20.
 */

public class PublishDynamicActivity extends AppCompatActivity {


    @BindView(R.id.publish_dynamic_content)
    EditText publishDynamicContent;
    @BindView(R.id.publish_dynamic_textNumber)
    TextView publishDynamicTextNumber;
    @BindView(R.id.publish_dynamic_image_recycler)
    RecyclerView publishDynamicImageRecycler;
    public static PublishDynamicActivity publishDynamicActivity;
    private static ProgressDialog progressDialog;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static List<File> photoList=new ArrayList<File>();
    private static List<String> photosURL=new ArrayList<String>();
    private String dynamicId;
    private String SessionID;
    private String groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_publish);
        ButterKnife.bind(this);
        publishDynamicActivity=this;
        groupId=getIntent().getStringExtra("groupId");
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");
        publishDynamicContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = publishDynamicContent.getText().toString();
                publishDynamicTextNumber.setText(text.length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.publish_dynamic_back, R.id.publish_dynamic_commit, R.id.publish_dynamic_add_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_dynamic_back:
                Intent intent=new Intent(PublishDynamicActivity.publishDynamicActivity,PlayTogetherActivity.class);
                startActivity(intent);
                break;
            case R.id.publish_dynamic_commit:
                if (publishDynamicContent.getText().toString().trim().equals("")){
                    Toast.makeText(PublishDynamicActivity.publishDynamicActivity,"请完善动态信息",Toast.LENGTH_SHORT).show();
                }else {
                    uploadInfo();
                    progressDialog=new ProgressDialog(PublishDynamicActivity.publishDynamicActivity);
                    progressDialog.setTitle("正在上传动态信息");
                    progressDialog.setMessage("uploading....");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
                break;
            case R.id.publish_dynamic_add_image:
                getImages();
                break;
        }
    }

    private void uploadInfo(){
        RequestParams requestParams=new RequestParams(LOGIN_URL+"GroupDynamicAction");
        requestParams.addBodyParameter("talk",publishDynamicContent.getText().toString());
        requestParams.addBodyParameter("action","发表说说");
        requestParams.addBodyParameter("groupId",groupId);
        requestParams.addBodyParameter("SessionID",SessionID);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("")){
                    Toast.makeText(PublishDynamicActivity.publishDynamicActivity,"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                }else {
                    dynamicId=result;
                    uploadImages();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("发布动态",ex.getMessage());
                Toast.makeText(PublishDynamicActivity.this,"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
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

    private void uploadImages(){
        if (photosURL!=null&&photoList!=null){
            for (String photo:photosURL){
                File file=new File(photo);
                photoList.add(file);
            }
        }

        RequestParams requestParams=new RequestParams(LOGIN_URL+"imagesupload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("dynamicId",dynamicId);
        requestParams.addBodyParameter("action","上传群组动态图片");
        requestParams.addParameter("images",photoList);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"发布成功！",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(PublishDynamicActivity.publishDynamicActivity,PlayTogetherActivity.class);
                        startActivity(intent);
                        onStop();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(PublishDynamicActivity.publishDynamicActivity,"发布失败！",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("发布动态--图片",ex.getMessage());
                Toast.makeText(PublishDynamicActivity.publishDynamicActivity,"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
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

    private void getImages(){
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        publishDynamicImageRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        publishDynamicImageRecycler.setAdapter(photoAdapter);

        PhotoPicker.builder()
                .setPhotoCount(3)
                .start(PublishDynamicActivity.publishDynamicActivity);
        publishDynamicImageRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(3)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(PublishDynamicActivity.publishDynamicActivity);
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
