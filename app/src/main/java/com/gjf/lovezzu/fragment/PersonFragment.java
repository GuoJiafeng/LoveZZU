package com.gjf.lovezzu.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.activity.UserInfoActivity;
import com.gjf.lovezzu.activity.UserLoginActivity;
import com.gjf.lovezzu.activity.UserSettingActivity;
import com.gjf.lovezzu.activity.friend.NewFriendsMsgActivity;
import com.gjf.lovezzu.activity.saylvoeActivity.SayLoveActivity;
import com.gjf.lovezzu.activity.taoyu.MyPublishGoodsActivity;
import com.gjf.lovezzu.activity.taoyu.ShopcartActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuOrderActivity;
import com.gjf.lovezzu.entity.CheckLoginApplication;
import com.gjf.lovezzu.view.CircleImageView;
import com.gjf.lovezzu.view.WheelView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class PersonFragment extends Fragment {
    @Nullable
    @BindView(R.id.user_image)
    LinearLayout linearLayout;
    @BindView(R.id.my_info)
    LinearLayout my_info;
    @BindView(R.id.person_usersetting)
    LinearLayout person_usersetting;
    @BindView(R.id.person_saylove)
    LinearLayout person_saylove;
    @BindView(R.id.user_shop_car)
    LinearLayout person_shop_car;
    @BindView(R.id.user_shop_list)
    LinearLayout person_shop_list;
    @BindView(R.id.main_my_user_icon)
    CircleImageView mainMyUserIcon;
    @BindView(R.id.user_nick_name)
    TextView userNickName;
    @BindView(R.id.user_state)
    TextView userState;
    @BindView(R.id.user_publish_goods_list)
    LinearLayout publish_goods_list;
    @BindView(R.id.user_mark)
    LinearLayout user_mark;
    private View view;
    private static final String[] MARK={"期末成绩","四六级"};
    private String type="四六级";
    private String markUrl="";
    private android.app.AlertDialog markDialog;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.person_fragment, container, false);
        ButterKnife.bind(this, view);
        userState.setText("未登录");
        initView();
        return view;

    }

    private void initView() {
        CheckLoginApplication checkLoginApplication = (CheckLoginApplication) getActivity().getApplication();
        if (checkLoginApplication.isLogin()) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo", Activity.MODE_APPEND);
            String userIcon = sharedPreferences.getString("userIcon", "");
            String userName = sharedPreferences.getString("userNickName", "");
            Glide.with(this).load(LOGIN_URL+"filedownload?action=头像&imageURL="+userIcon).into(mainMyUserIcon);
            userNickName.setText(userName);
            userState.setText("已登录");
        }
    }



    private void goTomyinfo() {
        Intent intent = new Intent(getContext(), UserInfoActivity.class);
        startActivity(intent);
    }

    public void goTologin() {
        Intent intent = new Intent(getContext(), UserLoginActivity.class);
        startActivity(intent);
    }

    public void goToSetting() {
        Intent intent = new Intent(getContext(), UserSettingActivity.class);
        startActivity(intent);
    }

    public void goToSayLove() {
        Intent intent = new Intent(getContext(), SayLoveActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.user_image,R.id.my_info, R.id.person_usersetting, R.id.person_saylove, R.id.user_shop_car, R.id.user_shop_list,R.id.user_publish_goods_list,R.id.user_mark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                CheckLoginApplication checkLoginApplication = (CheckLoginApplication) getActivity().getApplication();
                if (checkLoginApplication.isLogin()) {
                    goTomyinfo();
                } else {
                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_LONG).show();
                    goTologin();
                }
                break;
            case R.id.my_info:
                Intent intentF = new Intent(getContext(),NewFriendsMsgActivity.class);
                startActivity(intentF);
                break;
            case R.id.person_usersetting:
                goToSetting();
                break;
            case R.id.person_saylove:
                goToSayLove();
                break;
            case R.id.user_shop_car:
                Intent intent = new Intent(getContext(), ShopcartActivity.class);
                startActivity(intent);
                break;
            case R.id.user_shop_list:
                Intent intent2 = new Intent(getContext(), TaoyuOrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.user_publish_goods_list:
                Intent intent1=new Intent(getContext(), MyPublishGoodsActivity.class);
                startActivity(intent1);
                break;
            case R.id.user_mark:
                View outerView = LayoutInflater.from(MainActivity.mainActivity).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(1);
                wv.setItems(Arrays.asList(MARK));
                wv.setSeletion(1);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        type=item;
                    }
                });

                markDialog=new android.app.AlertDialog.Builder(MainActivity.mainActivity)
                    .setTitle("查询成绩")
                    .setView(outerView)
                    .show();
                TextView txtSure= (TextView) outerView.findViewById(R.id.txt_sure);
                txtSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (type.equals("四六级")){
                           markUrl="http://cet.superdaxue.com/";
                       }else if (type.equals("期末成绩")){
                           markUrl="http://jw.zzu.edu.cn/";
                       }
                        markDialog.dismiss();
                        new  FinestWebView.Builder(MainActivity.mainActivity)
                                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                                .stringResRefresh(R.string.refresh)
                                .stringResShareVia(R.string.share_via)
                                .stringResCopyLink(R.string.copy_link)
                                .stringResOpenWith(R.string.open_with)
                                .webViewSupportZoom(true)
                                .webViewBuiltInZoomControls(true)
                                .show(markUrl);

                    }
                });
                TextView txtCancle= (TextView) outerView.findViewById(R.id.txt_cancel);
                txtCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        markDialog.dismiss();
                    }
                });


                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        type="四六级";
    }
}