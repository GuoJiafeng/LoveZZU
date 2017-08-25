package com.gjf.lovezzu.activity.palytogether;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.PhotoAdapter;
import com.gjf.lovezzu.view.RecyclerItemClickListener;
import com.gjf.lovezzu.view.WheelView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
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
 * Created by zhao on 2017/8/20.
 */

public class PublishGroupActivity extends AppCompatActivity {

    @BindView(R.id.publish_group_go_back)
    ImageView publishGroupGoBack;
    @BindView(R.id.publish_group_commit)
    TextView publishGroupCommit;
    @BindView(R.id.publish_group_name)
    EditText publishGroupName;
    @BindView(R.id.publish_group_content)
    EditText publishGroupContent;
    @BindView(R.id.publish_group_label)
    EditText publishGroupLabel;
    @BindView(R.id.publish_group_add_image)
    ImageView publishGroupAddImage;
    @BindView(R.id.taoyu_publish_addimage)
    FrameLayout taoyuPublishAddimage;
    @BindView(R.id.publish_group_image_recycler)
    RecyclerView publishGroupImageRecycler;

    public static PublishGroupActivity publishGroupActivity;
    private static ProgressDialog progressDialog;
    @BindView(R.id.publish_group_camp)
    TextView publishGroupCamp;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static List<File> photoList = new ArrayList<File>();
    private static List<String> photosURL = new ArrayList<String>();
    private String groupId;
    private String SessionID;
    private String tree_school = "郑州大学（主校区）";
    private static final String[] TREE_SCHOOL = {"郑州大学（主校区）", "郑州大学（北校区）", "郑州大学（南校区）", "郑州大学（东校区）"};
    private AlertDialog goods_schoolDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_publish);
        ButterKnife.bind(this);
        publishGroupActivity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        SessionID = sharedPreferences.getString("SessionID", "");
    }

    @OnClick({R.id.publish_group_go_back, R.id.publish_group_commit, R.id.publish_group_add_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_group_go_back:
                Intent intent = new Intent(PublishGroupActivity.publishGroupActivity, PlayTogetherActivity.class);
                PublishGroupActivity.publishGroupActivity.startActivity(intent);
                break;
            case R.id.publish_group_commit:
                if (publishGroupContent.getText().toString().trim().equals("") ||
                        publishGroupName.getText().toString().trim().equals("") ||
                        publishGroupLabel.getText().toString().trim().equals("")||
                        publishGroupCamp.getText().toString().trim().equals("")) {
                    Toast.makeText(PublishGroupActivity.publishGroupActivity, "请完善群组信息", Toast.LENGTH_SHORT).show();
                } else {
                    uploadInfo();
                    progressDialog = new ProgressDialog(PublishGroupActivity.publishGroupActivity);
                    progressDialog.setTitle("正在上传群组信息");
                    progressDialog.setMessage("uploading....");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
                break;
            case R.id.publish_group_add_image:
                getImages();
                break;
        }
    }


    private void uploadInfo() {
        RequestParams requestParams = new RequestParams(LOGIN_URL + "GroupAction");
        requestParams.addBodyParameter("name", publishGroupName.getText().toString());
        requestParams.addBodyParameter("introduce", publishGroupContent.getText().toString());
        requestParams.addBodyParameter("label", publishGroupLabel.getText().toString());
        requestParams.addBodyParameter("campus", publishGroupCamp.getText().toString());
        requestParams.addBodyParameter("action", "创建群");
        requestParams.addBodyParameter("SessionID", SessionID);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("")) {
                    Toast.makeText(PublishGroupActivity.publishGroupActivity, "请检查网络是否通畅！", Toast.LENGTH_SHORT).show();
                } else {
                    groupId = result;
                    uploadImages();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(PublishGroupActivity.publishGroupActivity, "请重新登录并检查网络是否通畅！", Toast.LENGTH_SHORT).show();
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
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Boolean res = jsonObject.getBoolean("isSuccessful");
                    if (res) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_SHORT).show();
                        photosURL.clear();
                        selectedPhotos.clear();
                        photoList.clear();
                        Intent intent = new Intent(PublishGroupActivity.publishGroupActivity, PlayTogetherActivity.class);
                        startActivity(intent);
                        onStop();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "发布失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(PublishGroupActivity.publishGroupActivity, "请重新登录并检查网络是否通畅！", Toast.LENGTH_SHORT).show();
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
        publishGroupImageRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        publishGroupImageRecycler.setAdapter(photoAdapter);

        PhotoPicker.builder()
                .setPhotoCount(1)
                .start(PublishGroupActivity.publishGroupActivity);
        publishGroupImageRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(1)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(PublishGroupActivity.publishGroupActivity);
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

    @OnClick(R.id.publish_group_camp)
    public void onViewClicked() {
        View outerView1 = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wheel_view_wv);
        wv1.setOffset(1);
        wv1.setItems(Arrays.asList(TREE_SCHOOL));
        wv1.setSeletion(1);
        wv1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                tree_school = item;
            }
        });

        goods_schoolDialog = new AlertDialog.Builder(this)
                .setTitle("选择校区")
                .setView(outerView1)
                .show();
        TextView txtSure1 = (TextView) outerView1.findViewById(R.id.txt_sure);
        txtSure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishGroupCamp.setText(tree_school);
                goods_schoolDialog.dismiss();
            }
        });
        TextView txtCancle1 = (TextView) outerView1.findViewById(R.id.txt_cancel);
        txtCancle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishGroupCamp.setText("");
                goods_schoolDialog.dismiss();
            }
        });
    }

}
