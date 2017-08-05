package com.gjf.lovezzu.activity.tapictalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.view.PhotoAdapter;
import com.gjf.lovezzu.view.RecyclerItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

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

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_publish_activity);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.topic_pub_back, R.id.topic_pub_commit, R.id.topic_publish_addimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.topic_pub_back:
                Intent intent = new Intent(TopicPublishActivity.this, TopicTalkActivity.class);
                startActivity(intent);
                break;
            case R.id.topic_pub_commit:

                break;
            case R.id.topic_publish_addimage:
                getImages();
                break;
        }
    }

    private void upLoadImages(){
        if (photosURL!=null&&photoList!=null){
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
