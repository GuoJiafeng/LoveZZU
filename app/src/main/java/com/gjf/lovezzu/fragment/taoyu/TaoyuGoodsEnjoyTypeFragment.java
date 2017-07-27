package com.gjf.lovezzu.fragment.taoyu;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.TaoyuDataBridging;
import com.gjf.lovezzu.entity.TaoyuGoodsData;
import com.gjf.lovezzu.network.TaoyuGoodsListMethods;
import com.gjf.lovezzu.view.TaoyuAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by BlackBeard丶 on 2017/04/17.
 */

public class TaoyuGoodsEnjoyTypeFragment extends Fragment{


    private Subscriber subscriber;
    private View view;
    private List<TaoyuDataBridging> taoyuResultList = new ArrayList<>();
    public static final  String TAG = "Fragment";
    RecyclerView taoyu_list;
    private TaoyuAdapter adapter;
    // private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.taoyu_goods_enjoy_type_fragment, container, false);

            //初始化所需数据
            intList();
            //inittaoyuList();
            getTaoyuGoodsList();
            //onRefresh();
        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            // onRefresh();
        }
        return view;
    }

    public void intList() {
        taoyu_list = (RecyclerView) view.findViewById(R.id.taoyu_enjoy_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        taoyu_list.setLayoutManager(layoutManager);
        adapter = new TaoyuAdapter(taoyuResultList, getContext());
        taoyu_list.setAdapter(adapter);
    }


    public void getTaoyuGoodsList(){

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
                Toast.makeText(getContext(),taoyuGoodsData.getResult(),Toast.LENGTH_LONG).show();
                taoyuResultList.addAll(list);
                adapter.notifyDataSetChanged();


            }


        };

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userinfo", Activity.MODE_APPEND);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String SessionID = sharedPreferences.getString("SessionID", "");
        int i=1;
        TaoyuGoodsListMethods.getInstance().getGoodsList(subscriber,"娱乐",i+=5);

    }

}
