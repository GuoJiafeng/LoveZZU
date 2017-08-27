package com.gjf.lovezzu.activity.friend;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhao on 2017/8/26.
 */

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.add_friend_username)
    EditText addFriendUsername;
    @BindView(R.id.add_friend_add)
    Button addFriendAdd;
    @BindView(R.id.add_friend_reason)
    EditText addFriendReason;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_friend_add)
    public void onViewClicked() {
        if (addFriendUsername.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
        } else {
            sendRequest();
        }
    }

    private void sendRequest() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在发送请求...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(addFriendUsername.getText().toString().trim(),addFriendReason.getText().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(AddFriendActivity.this, "发送请求成功,等待对方验证", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            String msg="请求添加好友失败:"+e.getMessage();
                            Toast.makeText(AddFriendActivity.this,msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }
}
