package com.gjf.lovezzu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.UserInfoResult;
import com.gjf.lovezzu.network.DownloadIconMethods;
import com.gjf.lovezzu.network.GetUserInfoMethods;
import com.gjf.lovezzu.network.SaveUserInfoMethods;
import com.gjf.lovezzu.view.CircleImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class UserInfoActivity extends AppCompatActivity {
    @BindView(R.id.user_info_refresh)
    SwipeRefreshLayout userInfoRefresh;
    @BindView(R.id.my_title_back)
    ImageView myTitleBack;
    private Subscriber subscriber;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    @BindView(R.id.userinfo_icon)
    ImageView userinfo_icon;
    @BindView(R.id.main_my_user_icon)
    CircleImageView circleImageView;
    @BindView(R.id.user_info_nickname_text)
    TextView user_info_nickname_text;
    @BindView(R.id.user_info_phone_text)
    TextView user_info_phone_text;
    @BindView(R.id.user_info_sex_text)
    TextView user_info_sex_text;
    @BindView(R.id.user_info_hone_text)
    TextView user_info_hone_text;
    @BindView(R.id.user_info_school_text)
    TextView user_info_school_text;
    @BindView(R.id.user_info_class_text)
    TextView user_info_class_text;
    @BindView(R.id.user_info_major_text)
    TextView user_info_major_text;
    @BindView(R.id.userinfo_icon_layout)
    RelativeLayout userinfo_icon_layout;
    @BindView(R.id.userinfo_nickname_layout)
    RelativeLayout userinfo_nickname_layout;
    @BindView(R.id.userinfo_code_layout)
    RelativeLayout userinfo_code_layout;
    @BindView(R.id.userinfo_sex_layout)
    RelativeLayout userinfo_sex_layout;
    @BindView(R.id.userinfo_home_layout)
    RelativeLayout userinfo_home_layout;
    @BindView(R.id.userinfo_school_layout)
    RelativeLayout userinfo_school_layout;
    @BindView(R.id.userinfo_class_layout)
    RelativeLayout userinfo_class_layout;
    @BindView(R.id.userinfo_major_layout)
    RelativeLayout userinfo_major_layout;

    private static String userPhone;
    private static String userName;
    private String token;
    private SharedPreferences sharedPreferences;
    private String phone;
    private String id = "";


    private static String userIcon, imageURL = null;
    private static String upIcon;
    private String SessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_APPEND);
        phone = sharedPreferences.getString("phone", "");
        dispalyUserInfo();
        onRefresh();

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @OnClick({R.id.userinfo_icon_layout, R.id.userinfo_nickname_layout, R.id.userinfo_code_layout, R.id.userinfo_sex_layout, R.id.userinfo_home_layout, R.id.userinfo_school_layout, R.id.userinfo_class_layout, R.id.userinfo_major_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userinfo_icon_layout:
                startUp();
                break;
            case R.id.userinfo_nickname_layout:
                EditNickname();
                break;
            case R.id.userinfo_code_layout:
                Toast.makeText(UserInfoActivity.this, "无效", Toast.LENGTH_SHORT).show();
                break;
            case R.id.userinfo_sex_layout:
                EditSex();
                break;
            case R.id.userinfo_home_layout:
                EditHome();
                break;
            case R.id.userinfo_school_layout:
                EditSchool();
                break;
            case R.id.userinfo_class_layout:
                EditClass();
                break;
            case R.id.userinfo_major_layout:
                EditMjaor();
                break;
        }
    }

    //刷新操作
    private void onRefresh() {
        userInfoRefresh.setColorSchemeResources(R.color.colorPrimary);
        userInfoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //更新数据
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dispalyUserInfo();
                                userInfoRefresh.setRefreshing(false);
                            }
                        });

                    }
                }).start();
            }
        });
    }

    //显示用户头像
    private void disPlayImage() {


        subscriber = new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Bitmap bitmap) {
                circleImageView.setImageBitmap(bitmap);
            }
        };

        DownloadIconMethods.getInstance().startDownloadIcon(subscriber, userIcon, "头像");
    }

    //显示用户信息
    private void dispalyUserInfo() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String phone = sharedPreferences.getString("phone", "");
        String password = sharedPreferences.getString("password", "");
        SessionID = sharedPreferences.getString("SessionID", "");
        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {
                disPlayImage();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {
                user_info_nickname_text.setText(userInfoResult.getNickname());
                userIcon = userInfoResult.getImageUrl().toString();
                user_info_phone_text.setText(userInfoResult.getPhone());
                user_info_sex_text.setText(userInfoResult.getGender());
                user_info_hone_text.setText(userInfoResult.getHometown());
                user_info_school_text.setText(userInfoResult.getAcademy());
                user_info_class_text.setText(userInfoResult.getDepartments());
                user_info_major_text.setText(userInfoResult.getProfessional());
                editor.putString("userIcon", userInfoResult.getImageUrl().toString());
                editor.putString("userNickName", userInfoResult.getNickname());
                editor.apply();
            }
        };

        String mid = "3";
        GetUserInfoMethods.getUserInfoMethods().goToGetUserInfo(subscriber, mid, phone, password);


    }

    //上传图片
    private void startUp() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    private void UploadIcon() {

        RequestParams requestParams = new RequestParams(LOGIN_URL + "upload");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("SessionID", SessionID);
        requestParams.addBodyParameter("myUpload", new File(upIcon));
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    //修改昵称
    private void EditNickname() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.userinfo_update_view, (ViewGroup) findViewById(R.id.uplayout));
        final EditText text = (EditText) layout.findViewById(R.id.edituserinfo);


        new AlertDialog.Builder(UserInfoActivity.this).setMessage("请输入您的昵称：")
                .setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveInfoNcikNmae(text.getText().toString());
            }
        }).setNegativeButton("取消", null).show();

    }
    //显示二维码


    //修改性别
    private void EditSex() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.userinfo_update_view, (ViewGroup) findViewById(R.id.uplayout));
        final EditText text = (EditText) layout.findViewById(R.id.edituserinfo);


        new AlertDialog.Builder(UserInfoActivity.this).setMessage("请输入您的性别：")
                .setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveInfoSex(text.getText().toString());
                user_info_nickname_text.setText(text.getText().toString());

            }
        }).setNegativeButton("取消", null).show();

    }

    //修改家乡
    private void EditHome() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.userinfo_update_view, (ViewGroup) findViewById(R.id.uplayout));
        final EditText text = (EditText) layout.findViewById(R.id.edituserinfo);


        new AlertDialog.Builder(UserInfoActivity.this).setMessage("请输入您的家乡：")
                .setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveInfoHome(text.getText().toString());
                user_info_hone_text.setText(text.getText().toString());

            }
        }).setNegativeButton("取消", null).show();

    }

    //修改院校
    private void EditSchool() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.userinfo_update_view, (ViewGroup) findViewById(R.id.uplayout));
        final EditText text = (EditText) layout.findViewById(R.id.edituserinfo);


        new AlertDialog.Builder(UserInfoActivity.this).setMessage("请输入您的院校：")
                .setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveInfoSChool(text.getText().toString());
                user_info_school_text.setText(text.getText().toString());
            }
        }).setNegativeButton("取消", null).show();

    }

    //修改院系
    private void EditClass() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.userinfo_update_view, (ViewGroup) findViewById(R.id.uplayout));
        final EditText text = (EditText) layout.findViewById(R.id.edituserinfo);


        new AlertDialog.Builder(UserInfoActivity.this).setMessage("请输入您的院系：")
                .setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveInfoClass(text.getText().toString());
                user_info_class_text.setText(text.getText().toString());
            }
        }).setNegativeButton("取消", null).show();

    }

    //修改专业
    private void EditMjaor() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.userinfo_update_view, (ViewGroup) findViewById(R.id.uplayout));
        final EditText text = (EditText) layout.findViewById(R.id.edituserinfo);


        new AlertDialog.Builder(UserInfoActivity.this).setMessage("请输入您的专业：")
                .setView(layout).setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveInfoMajor(text.getText().toString());
                user_info_major_text.setText(text.getText().toString());
            }
        }).setNegativeButton("取消", null).show();


    }


    //修改昵称的网络操作
    private void SaveInfoNcikNmae(String nickname) {

        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {

            }
        };

        SaveUserInfoMethods.saveUserInfoMethods().editNickname(subscriber, id, phone, nickname);

    }

    //修改性别的网络操作
    private void SaveInfoSex(String sex) {

        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {

            }
        };

        SaveUserInfoMethods.saveUserInfoMethods().editSex(subscriber, id, phone, sex);


    }

    //修改家乡的网络操作
    private void SaveInfoHome(String home) {

        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {

            }
        };

        SaveUserInfoMethods.saveUserInfoMethods().editHome(subscriber, id, phone, home);


    }

    //修改学校的网络操作
    private void SaveInfoSChool(String school) {

        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {

            }
        };


        SaveUserInfoMethods.saveUserInfoMethods().editSchool(subscriber, id, phone, school);

    }

    //修改院校的网络操作
    private void SaveInfoClass(String Class) {

        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {

            }
        };

        SaveUserInfoMethods.saveUserInfoMethods().editClass(subscriber, id, phone, Class);

    }

    //修改专业的网络操作
    private void SaveInfoMajor(String mjor) {

        subscriber = new Subscriber<UserInfoResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoResult userInfoResult) {

            }
        };

        SaveUserInfoMethods.saveUserInfoMethods().editMajor(subscriber, id, phone, mjor);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                selectedPhotos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
        }
        if (upIcon != null && !upIcon.equals("")) {
            upIcon = null;
        }
        upIcon = selectedPhotos.get(0).toString();
        Glide.with(UserInfoActivity.this).load(upIcon).into(circleImageView);
        UploadIcon();
    }


    @OnClick(R.id.my_title_back)
    public void onViewClicked() {
        Intent intent=new Intent(UserInfoActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
