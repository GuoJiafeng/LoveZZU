package com.gjf.lovezzu.activity.palytogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjf.lovezzu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhao on 2017/8/21.
 */

public class GroupDynamicActivity extends AppCompatActivity {

    @BindView(R.id.group_dynamic_view)
    RecyclerView groupDynamicView;
    @BindView(R.id.dynamic_refresh)
    SwipeRefreshLayout dynamicRefresh;
    @BindView(R.id.activity_dynamic_back)
    ImageView activityDynamicBack;
    @BindView(R.id.dynamic_fab)
    ImageView dynamicFab;
    private String groupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        ButterKnife.bind(this);
        groupId = getIntent().getStringExtra("groupId");
    }

    @OnClick({R.id.activity_dynamic_back, R.id.dynamic_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_dynamic_back:
                break;
            case R.id.dynamic_fab:
                Intent intent = new Intent(this, PublishDynamicActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
                break;
        }
    }
}
