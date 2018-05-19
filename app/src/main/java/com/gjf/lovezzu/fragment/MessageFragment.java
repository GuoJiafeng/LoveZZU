package com.gjf.lovezzu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.Message.MessageItem;
import com.gjf.lovezzu.entity.UserInfoResult;
import com.gjf.lovezzu.view.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class MessageFragment extends Fragment {
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    LinearLayoutManager layoutManager;
    MessageAdapter mMessageAdapter;

    @BindView(R.id.message_recycler_view)
    RecyclerView mMessageRecyclerView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        layoutManager = new LinearLayoutManager(view.getContext());
        unbinder = ButterKnife.bind(this, view);
        showMessage();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showMessage() {
        if (mMessageItems.size()<=0){
            getMessage();
        }
        mMessageRecyclerView.setLayoutManager(layoutManager);
        mMessageAdapter = new MessageAdapter(mMessageItems);
        mMessageRecyclerView.setAdapter(mMessageAdapter);
    }

    private void getMessage() {
        //从服务器获取
        /*模拟数据*/
        MessageItem messageItem = new MessageItem();
        UserInfoResult userInfoResult = new UserInfoResult();
        userInfoResult.setImageUrl("https://www.zhuangbi.info/uploads/i/2017-10-18-c0643696b92d92ba3f32f54befb07cce.png");
        userInfoResult.setNickname("小红花");
        messageItem.setUserInfoResult(userInfoResult);
        messageItem.setBoolean(false);
        messageItem.setMessageAnswer("暂无回复");
        messageItem.setMessageChecked("1人未确定");
        messageItem.setMessageTime("23:02");
        messageItem.setMessageContent("暂无内容");
        for (int i = 0; i < 2; i++) {
            mMessageItems.add(messageItem);
        }
    }
}