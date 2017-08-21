package com.gjf.lovezzu.activity.palytogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.gjf.lovezzu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhao on 2017/8/20.
 */

public class JoinGroupActivity extends AppCompatActivity {


    @BindView(R.id.joined_group_recycler)
    RecyclerView joinedGroupRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_group);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_joined_back)
    public void onViewClicked() {
        Intent intent = new Intent(this, PlayTogetherActivity.class);
        startActivity(intent);
    }
}
