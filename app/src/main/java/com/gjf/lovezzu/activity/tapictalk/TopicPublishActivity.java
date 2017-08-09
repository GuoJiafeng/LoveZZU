package com.gjf.lovezzu.activity.tapictalk;

import android.app.Activity;
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
 * Created by Leon on 2017/7/25.
 * finished by zhoa
 */

public class TopicPublishActivity extends AppCompatActivity {

    @BindView(R.id.topic_pub_back)
    TextView topicPubBack;
    @BindView(R.id.topic_pub_commit)
    TextView topicPubCommit;
    @BindView(R.id.topic_name)
    EditText topicName;
    @BindView(R.id.topic_content)
    EditText topicContent;
    @BindView(R.id.topic_publish_addimage)
    FrameLayout topicPublishAddimage;
    @BindView(R.id.topic_publish_images)
    RecyclerView topicPublishImages;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static List<File>  photoList=new ArrayList<File>();
    private static List<String> photosURL=new ArrayList<String>();
    private Integer parentId;
    private String type;
    private String phone;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_publish_activity);
        ButterKnife.bind(this);
        type=getIntent().getStringExtra("type");
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        phone=sharedPreferences.getString("phone","");
    }

    @OnClick({R.id.topic_pub_back, R.id.topic_pub_commit, R.id.topic_publish_addimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topic_pub_back:
                Intent intent = new Intent(TopicPublishActivity.this, TopicTalkActivity.class);
                startActivity(intent);
                break;
            case R.id.topic_pub_commit:
                if (type.equals("一级话题")){
                    upLoadImages();
                    publishFirstTopic();
                }else if (type.equals("二级话题")){
                    upLoadImages();
                    publishChildTopic();
                }
                break;
            case R.id.topic_publish_addimage:
                getImages();
                break;
        }
    }


    private void publishFirstTopic(){


            if (topicName.getText().toString()!=null&&topicContent.getText().toString()!=null){
            RequestParams requestParams=new RequestParams(LOGIN_URL+"addSubjectAction");
            requestParams.addBodyParameter("title",topicName.getText().toString());
            requestParams.addBodyParameter("content",topicContent.getText().toString());
            requestParams.addBodyParameter("phone",phone);
            requestParams.addParameter("myUpload",photoList);
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("话题圈============一级发布result",result);
                    try{
                        JSONObject jsonObject=new JSONObject(result);
                        Boolean res=jsonObject.getBoolean("isSuccessful");
                        if (res){
                            Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("话题圈===============以及发布error",ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    photoList.clear();
                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }


    }

    private void publishChildTopic(){
        if (topicName.getText().toString()!=null&&topicContent.getText().toString()!=null){
            RequestParams requestParams=new RequestParams(LOGIN_URL+"addSubjectAction");
            requestParams.addBodyParameter("title",topicName.getText().toString());
            requestParams.addBodyParameter("content",topicContent.getText().toString());
            requestParams.addParameter("subjectid",parentId);
            requestParams.addParameter("myUpload",photoList);
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("话题圈============二级发布result",result);
                    try{
                        JSONObject jsonObject=new JSONObject(result);
                        Boolean res=jsonObject.getBoolean("isSuccessful");
                        if (res){
                            Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("话题圈===============二级发布error",ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    photoList.clear();
                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }
    }

    private void upLoadImages(){
        if (photosURL!=null&&photoList!=null){
            photoList.clear();
            for (String photo:photosURL){
                File file=new File(photo);
                photoList.add(file);
            }
        }

    }

    private void getImages(){
        photoAdapter=new PhotoAdapter(this,selectedPhotos);
        topicPublishImages.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        topicPublishImages.setAdapter(photoAdapter);
        PhotoPicker.builder()
                .setPhotoCount(5)
                .start(TopicPublishActivity.this);
        topicPublishImages.addOnItemTouchListener(new  RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(TopicPublishActivity.this);
                            photoAdapter.notifyDataSetChanged();
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(TopicPublishActivity.this);
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
