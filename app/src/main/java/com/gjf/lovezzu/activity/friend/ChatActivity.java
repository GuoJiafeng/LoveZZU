package com.gjf.lovezzu.activity.friend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.friend.Constant;
import com.gjf.lovezzu.entity.friend.EaseCommonUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import java.util.List;

/**
 * Created by zhao on 2017/8/26.
 */

public class ChatActivity extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getPremission();
        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.chat_content,chatFragment).commit();

    }

    private void getPremission(){
        if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ChatActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }else {
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    break;
                }else {
                    Toast.makeText(this,"无存储权限将不能正常发送图片！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
