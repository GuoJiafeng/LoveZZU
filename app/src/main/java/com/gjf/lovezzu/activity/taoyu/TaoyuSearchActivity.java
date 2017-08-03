package com.gjf.lovezzu.activity.taoyu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.TaoyuDataBridging;
import com.gjf.lovezzu.entity.TaoyuGoodsData;
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
        setChenjinshitongzhilan();
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
    }

    private void setChenjinshitongzhilan(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }
    @OnClick({R.id.taoyu_search_button})
    public void OnClick(View view) {
        switch (view.getId()){
            case R.id.taoyu_search_button:
                msg = taoyu_search_title.getText().toString();
                getTaoyuGoodsList(msg,START);
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
