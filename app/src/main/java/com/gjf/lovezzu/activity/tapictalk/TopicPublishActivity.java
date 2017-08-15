package com.gjf.lovezzu.activity.tapictalk;

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
import com.gjf.lovezzu.entity.topic.TopicThemeBridging;
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

    private static ProgressDialog progressDialog;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static List<File>  photoList=new ArrayList<File>();
    private static List<String> photosURL=new ArrayList<String>();
    private Integer theme;
    private String SessionID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_publish_activity);
        ButterKnife.bind(this);
        theme= getIntent().getIntExtra("theme",0);
        Log.e("话题==============话题发布ThemeID",theme+"?null");
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID=sharedPreferences.getString("SessionID","");

    }

    @OnClick({R.id.topic_pub_back, R.id.topic_pub_commit, R.id.topic_publish_addimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topic_pub_back:
                Intent intent = new Intent(TopicPublishActivity.this, TopicTalkActivity.class);
                startActivity(intent);
                break;
            case R.id.topic_pub_commit:
                publishChildTopic();
                break;
            case R.id.topic_publish_addimage:
                getImages();
                break;
        }
    }




    private void publishChildTopic(){
        if (topicName.getText().toString()!=null&&topicContent.getText().toString()!=null){
            RequestParams requestParams=new RequestParams(LOGIN_URL+"TopicAction");
            requestParams.addBodyParameter(" TopicTitle",topicName.getText().toString());
            requestParams.addBodyParameter("TopicText",topicContent.getText().toString());
            requestParams.addBodyParameter("ThemeId",theme+"");
            requestParams.addBodyParameter("SessionID",SessionID);
            requestParams.addBodyParameter("action","发布话题");
            x.http().post(requestParams, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!result.isEmpty()){
                            Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                            progressDialog=new ProgressDialog(TopicPublishActivity.this);
                            progressDialog.setTitle("正在上传信息");
                            progressDialog.setMessage("uploading....");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                            upLoadImages(result);
                        }else {
                            Toast.makeText(getApplicationContext(),"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
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
                    photoList.clear();
                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }
    }

    private void upLoadImages(String topicID){
        if (photosURL!=null&&photoList!=null){
            photoList.clear();
            for (String photo:photosURL){
                File file=new File(photo);
                photoList.add(file);
            }
        }

        RequestParams requestParams=new RequestParams(LOGIN_URL+"imagesupload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("TopicId",topicID);
        requestParams.addBodyParameter("action","上传话题图片");
        requestParams.addParameter("images",photoList);
        if (photoList.isEmpty()){
            Log.e("话题圈===========话题","图片为空");
        }
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("话题圈===========话题result",result);
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(TopicPublishActivity.this,TopicTalkActivity.class);
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
                Toast.makeText(getApplicationContext(),"请重新登录并检查网络是否通畅！",Toast.LENGTH_SHORT).show();
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
