package com.gjf.lovezzu.activity.saylvoeActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;

import com.gjf.lovezzu.entity.saylove.Saylove;
import com.gjf.lovezzu.entity.saylove.SayloveData;
import com.gjf.lovezzu.network.SayloveWallMethods;
import com.gjf.lovezzu.network.SearchlovecardMethods;
import com.gjf.lovezzu.view.SayloveAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


public class SayloveWallActivity extends AppCompatActivity {
    public static SayloveWallActivity sayloveWallActivity;

    ImageView saylove_back;
    @BindView(R.id.searchlovecard)
    EditText searchlovecard;
    private List<Saylove> sayloveList = new ArrayList<>();
    private RecyclerView sayloveRecyclerView;
    private SwipeRefreshLayout sayloveRefresh;
    private SayloveAdapter sayloveAdapter;
    private String SessionID;
    private Subscriber subscriber;
    private  Saylove saylove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saylove);
        ButterKnife.bind(this);
        sayloveWallActivity = this;

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SessionID = sharedPreferences.getString("SessionID", "");
        sayloveRefresh = (SwipeRefreshLayout) findViewById(R.id.saylove_Refresh);
        saylove_back = (ImageView) findViewById(R.id.saylove_back);
        saylove_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SayloveWallActivity.this, SayLoveActivity.class);
                startActivity(intent);

            }
        });
        searchlovecard.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchlovecard.getText().toString().trim().equals("")){
                        Toast.makeText(getApplicationContext(), "请输入关键字！", Toast.LENGTH_SHORT).show();
                    }else {
                        getSearchlovecard("搜索表白卡",searchlovecard.getText().toString());
                    }
                }
                return false;
            }
        });
        sayloveRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSayloveWall("查询所有表白卡");
            }
        });
        getSayloveWall("查询所有表白卡");
        showsaylove();

    }

    private void showsaylove() {
        sayloveRecyclerView = (RecyclerView) findViewById(R.id.sayloveRecyclerView);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        sayloveRecyclerView.setLayoutManager(layoutmanager);
        sayloveAdapter = new SayloveAdapter(sayloveList,SessionID);
        sayloveRecyclerView.setAdapter(sayloveAdapter);
    }


    public void getSayloveWall(String action) {
        subscriber = new Subscriber<SayloveData>() {
            @Override
            public void onCompleted() {
                if (sayloveRefresh.isRefreshing()) {
                    sayloveRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SayloveData sayloveData) {
                List<Saylove> list = sayloveData.getValues();
                if (list.size() != 0) {
                    sayloveList.clear();
                    sayloveList.addAll(list);
                    sayloveAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SayloveWallActivity.sayloveWallActivity, "还没有人表白，快给TA表白吧！", Toast.LENGTH_SHORT).show();
                }
            }

        };
        SayloveWallMethods.getInstance().getSaylove(subscriber, action);
    }


    private void getSearchlovecard(String action,String search) {
        subscriber=new Subscriber<SayloveData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SayloveData sayloveData) {
           List<Saylove> list=sayloveData.getValues();
                if (list.size()==0){
                    Toast.makeText(getApplicationContext(), "没有您要搜索的表白QAQ", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    sayloveList.clear();
                    sayloveList.addAll(list);
                    sayloveAdapter.notifyDataSetChanged();
                }

            }
        };
        SearchlovecardMethods.getInstance().getSearchlovecard(subscriber,action,search);
    }
}
