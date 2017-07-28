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
    private TaoyuAdapter adapter;

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
                String msg = taoyu_search_title.getText().toString();
                getTaoyuGoodsList(msg);
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

    public void getTaoyuGoodsList(String msg){

        subscriber = new Subscriber<TaoyuGoodsData>() {
            @Override
            public void onCompleted() {
                Log.d("ggggg","yiwancheng taoyu");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ggggg",e.getMessage().toString()+"hgggggggggggggg");
            }

            @Override
            public void onNext(TaoyuGoodsData taoyuGoodsData) {
                List<TaoyuDataBridging> list = taoyuGoodsData.getValues();
                Toast.makeText(getApplicationContext(),taoyuGoodsData.getResult(),Toast.LENGTH_LONG).show();
                taoyuResultList.clear();
                taoyuResultList.addAll(list);

                adapter.notifyDataSetChanged();


            }


        };

        int i=0;
        TaoyuGoodsListMethods.getInstance().getGoodsList(subscriber,msg,i+=10);

    }



}
