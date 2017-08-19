package com.gjf.lovezzu.activity.taoyu;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.taoyu.TaoyuDataBridging;
import com.gjf.lovezzu.entity.taoyu.TaoyuGoodsData;
import com.gjf.lovezzu.network.TaoyuGoodsListMethods;
import com.gjf.lovezzu.view.TaoyuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by BlackBeard丶 on 2017/04/17.
 */

public class TaoyuSearchActivity extends AppCompatActivity {

    private Subscriber subscriber;
    private List<TaoyuDataBridging> taoyuResultList = new ArrayList<>();
    public static final  String TAG = "Fragment";
    RecyclerView taoyu_list;
    private String msg;
    private TaoyuAdapter adapter;
    private static int START = 0;
    @BindView(R.id.taoyu_search_title)
    EditText taoyu_search_title;
    @BindView(R.id.taoyu_search_button)
    TextView taoyu_search_button;
    // private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taoyu_search_activity);
                ButterKnife.bind(this);
        intList();
        taoyu_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.canScrollVertically(1) == false && recyclerView.canScrollVertically(-1) == true) {
                    Toast.makeText(getApplicationContext(), "正在加载", Toast.LENGTH_SHORT).show();
                    getTaoyuGoodsList(msg,START+=10);
                }
            }
        });
        taoyu_search_title.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (taoyu_search_title.getText().toString().trim().equals("")){
                        Toast.makeText(getApplicationContext(), "请输入关键字！", Toast.LENGTH_SHORT).show();
                    }else {
                        msg = taoyu_search_title.getText().toString();
                        getTaoyuGoodsList(msg,START);
                    }
                }
                return false;
            }
        });
    }


    @OnClick({R.id.taoyu_search_button})
    public void OnClick(View view) {
        switch (view.getId()){
            case R.id.taoyu_search_button:
                if (taoyu_search_title.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入关键字！", Toast.LENGTH_SHORT).show();
                }else {
                    msg = taoyu_search_title.getText().toString();
                    getTaoyuGoodsList(msg,START);
                }

                break;
        }

    }
    public void intList() {
        taoyu_list = (RecyclerView) findViewById(R.id.taoyu_search_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        taoyu_list.setLayoutManager(layoutManager);
        adapter = new TaoyuAdapter(taoyuResultList,this);
        taoyu_list.setAdapter(adapter);
    }

    public void getTaoyuGoodsList(String msg,int num){

        subscriber = new Subscriber<TaoyuGoodsData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TaoyuGoodsData taoyuGoodsData) {
                List<TaoyuDataBridging> list = taoyuGoodsData.getValues();
                if (list.size() == 0) {
                    Toast.makeText(getApplicationContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    taoyuResultList.clear();
                    taoyuResultList.addAll(list);
                    adapter.notifyDataSetChanged();
                }

            }


        };

        TaoyuGoodsListMethods.getInstance().getGoodsList(subscriber,msg, num);

    }



}
