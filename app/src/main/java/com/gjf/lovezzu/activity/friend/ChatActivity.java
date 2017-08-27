package com.gjf.lovezzu.activity.friend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

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
        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.chat_content,chatFragment).commit();
    }



}
