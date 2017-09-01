package com.gjf.lovezzu.activity.friend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.friend.InviteMessage;
import com.gjf.lovezzu.entity.friend.InviteMessgeDao;
import com.gjf.lovezzu.view.NewFriendsMsgAdapter;

import java.util.List;

/**
 * Created by zhao on 2017/8/26.
 */

public class NewFriendsMsgActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }

}
