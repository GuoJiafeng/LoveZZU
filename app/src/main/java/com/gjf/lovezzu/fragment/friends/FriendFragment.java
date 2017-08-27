package com.gjf.lovezzu.fragment.friends;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.activity.friend.AddFriendActivity;
import com.gjf.lovezzu.activity.friend.ChatActivity;
import com.gjf.lovezzu.entity.friend.Constant;
import com.gjf.lovezzu.entity.friend.DemoApplication;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BlackBeard丶 on 2017/03/01.
 */
public class FriendFragment extends Fragment {
    @BindView(R.id.friends_messages)
    TextView friendsMessages;
    @BindView(R.id.friends_people)
    TextView friendsPeople;
    @BindView(R.id.add_friends)
    ImageView addFriends;
    @BindView(R.id.friend_content)
    FrameLayout friendContent;
    @Nullable
    private View view;
    private EaseContactListFragment contactListFragment;
    private EaseConversationListFragment conversationListFragment;
    private EaseChatFragment chatFragment;
    private Map<String, EaseUser> contactsMap = new Hashtable<String, EaseUser>();
    protected List<EaseUser> contactList = new ArrayList<EaseUser>();
    private EMConversation conversation;
    private int chatType = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friend_fragment, container, false);
        ButterKnife.bind(this, view);
        friendsMessages.setTextColor(Color.parseColor("#0090FD"));
        friendsPeople.setTextColor(Color.parseColor("#000000"));
        chatFragment=new EaseChatFragment();
        contactListFragment=new EaseContactListFragment();
        if (!contactsMap.isEmpty()){
            contactsMap.clear();
        }
        contactListFragment.setContactsMap(getContactList());
        contactListFragment.hideTitleBar();
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                conversation = EMClient.getInstance().chatManager().getConversation(user.toString(),
                            com.gjf.lovezzu.entity.friend.EaseCommonUtils.getConversationType(chatType),true);
                Intent intent=new Intent(MainActivity.mainActivity, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.toString());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,conversation.getType());
                startActivity(intent);
            }
        });
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent=new Intent(MainActivity.mainActivity, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,conversation.getType());
                startActivity(intent);
            }
        });
        conversationListFragment.hideTitleBar();
        replceFragment(conversationListFragment);
        return view;

    }

    @OnClick({R.id.friends_messages, R.id.friends_people, R.id.add_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friends_messages:
                friendsMessages.setTextColor(Color.parseColor("#0090FD"));
                friendsPeople.setTextColor(Color.parseColor("#000000"));
                if (conversationListFragment == null) {
                    conversationListFragment = new EaseConversationListFragment();
                    conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
                        @Override
                        public void onListItemClicked(EMConversation conversation) {
                            Intent intent=new Intent(MainActivity.mainActivity, ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,conversation.getType());
                            startActivity(intent);
                        }
                    });
                    conversationListFragment.hideTitleBar();
                }
                replceFragment(conversationListFragment);
                break;
            case R.id.friends_people:
                friendsMessages.setTextColor(Color.parseColor("#000000"));
                friendsPeople.setTextColor(Color.parseColor("#0090FD"));
                if (contactListFragment == null) {
                    contactListFragment = new EaseContactListFragment();
                    contactListFragment.setContactsMap(getContactList());
                    contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
                        @Override
                        public void onListItemClicked(EaseUser user) {

                            conversation = EMClient.getInstance().chatManager().getConversation(user.toString(),
                                    com.gjf.lovezzu.entity.friend.EaseCommonUtils.getConversationType(chatType),true);
                            Intent intent=new Intent(MainActivity.mainActivity, ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, user.toString());
                            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,conversation.getType());
                            startActivity(intent);
                        }
                    });
                    contactListFragment.hideTitleBar();
                }
                replceFragment(contactListFragment);
                break;
            case R.id.add_friends:
                startActivity(new Intent(MainActivity.mainActivity, AddFriendActivity.class));
                break;
        }
    }

    private void replceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.friend_content, fragment);
        transaction.commit();
    }

    /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    protected Map<String, EaseUser> getContactList() {
        contactList.clear();
        // 获取联系人列表
        contactsMap = DemoApplication.getInstance().getContactList();
        if (contactsMap == null) {
            return new Hashtable<String, EaseUser>();
        }
        synchronized (this.contactsMap) {
            Iterator<Map.Entry<String, EaseUser>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = EMClient.getInstance().contactManager().getBlackListUsernames();
            while (iterator.hasNext()) {
                Map.Entry<String, EaseUser> entry = iterator.next();
                // 兼容以前的通讯录里的已有的数据显示，加上此判断，如果是新集成的可以去掉此判断
                if (!entry.getKey().equals("item_new_friends") && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom") && !entry.getKey().equals("item_robots")) {
                    if (!blackList.contains(entry.getKey())) {
                        // 不显示黑名单中的用户
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        contactList.add(user);
                    }
                }
            }
        }
        // 排序
        Collections.sort(contactList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });
        return contactsMap;
    }

}
