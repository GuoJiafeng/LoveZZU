package com.gjf.lovezzu.activity.palytogether;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
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
 * Created by zhao on 2017/8/25.
 */

public class UpdateGroupImage extends AppCompatActivity {
    @BindView(R.id.update_group_image)
    ImageView updateGroupImage;
    @BindView(R.id.update_group_image_recycler)
    RecyclerView updateGroupImageRecycler;
    @BindView(R.id.update_group_commit)
    TextView updateGroupCommit;
    private String groupId;
    private static ProgressDialog progressDialog;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static List<File> photoList = new ArrayList<File>();
    private static List<String> photosURL = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group);
        ButterKnife.bind(this);
        groupId = getIntent().getStringExtra("groupId");
    }

    @OnClick({R.id.update_group_image,R.id.update_group_commit})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.update_group_image:
                getImages();
                break;
            case R.id.update_group_commit:
                progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("正在上传图片");
                progressDialog.setMessage("uploading....");
                progressDialog.setCancelable(true);
                progressDialog.show();
                uploadImages();
        }

    }


    private void uploadImages() {
        if (photosURL != null && photoList != null) {
            for (String photo : photosURL) {
                File file = new File(photo);
                photoList.add(file);
            }
        }

        RequestParams requestParams = new RequestParams(LOGIN_URL + "imagesupload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("groupId", groupId);
        requestParams.addBodyParameter("action", "上传群组图片");
        requestParams.addParameter("images", photoList);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("修改动态--图片", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Boolean res = jsonObject.getBoolean("isSuccessful");
                    if (res) {
                        progressDialog.dismiss();
                        photosURL.clear();
                        selectedPhotos.clear();
                        photoList.clear();
                        Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateGroupImage.this, PlayTogetherActivity.class);
                        startActivity(intent);
                        onStop();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateGroupImage.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("修改动态--图片", ex.getMessage());
                Toast.makeText(PublishDynamicActivity.publishDynamicActivity, "请重新登录并检查网络是否通畅！", Toast.LENGTH_SHORT).show();
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

    private void getImages() {
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        updateGroupImageRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        updateGroupImageRecycler.setAdapter(photoAdapter);

        PhotoPicker.builder()
                .setPhotoCount(1)
                .start(this);
        updateGroupImageRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(1)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(UpdateGroupImage.this);
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
                photosURL = photos;
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }


}
