package com.gjf.lovezzu.fragment.taoyu;

import android.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class TaoyuGoodsEnjoyTypeFragment extends Fragment {
    private Subscriber subscriber;
    private View view;
    private List<TaoyuDataBridging> taoyuResultList = new ArrayList<>();
    RecyclerView taoyu_list;
    private SwipeRefreshLayout refreshLayout;
    private TaoyuAdapter adapter;
    private static int START = 0;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            START = 0;
            view = inflater.inflate(R.layout.taoyu_goods_enjoy_type_fragment, container, false);
            //初始化所需数据
            intList();
            getTaoyuGoodsList(0);
            refreshEnjoy();
        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            refreshEnjoy();
        }
        return view;
    }

    private void refreshEnjoy() {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.taoyu_enjoy_list_refresh);
        refreshLayout.setColorSchemeColors(Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTaoyuGoodsList(START += 10);

            }
        });
        taoyu_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.canScrollVertically(1) == false && recyclerView.canScrollVertically(-1) == true) {
                    Toast.makeText(getContext(), "正在加载", Toast.LENGTH_SHORT).show();
                    getTaoyuGoodsList(START += 10);
                }
            }

        });

    }


    public void intList() {
        taoyu_list = (RecyclerView) view.findViewById(R.id.taoyu_enjoy_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        taoyu_list.setLayoutManager(layoutManager);
        adapter = new TaoyuAdapter(taoyuResultList, getContext());
        taoyu_list.setAdapter(adapter);
    }





    public void getTaoyuGoodsList(int num) {
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
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    taoyuResultList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        TaoyuGoodsListMethods.getInstance().getGoodsList(subscriber, "娱乐", num);
        if (num > 0) {
            refreshLayout.setRefreshing(false);
        }
    }

}
