package com.gjf.lovezzu.fragment.taoyu;

import android.support.v4.app.Fragment;
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
import com.gjf.lovezzu.entity.taoyu.TaoyuDataBridging;
import com.gjf.lovezzu.entity.taoyu.TaoyuGoodsData;
import com.gjf.lovezzu.network.TaoyuGoodsListMethods;
import com.gjf.lovezzu.view.TaoyuAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by BlackBeard丶 on 2017/04/17.
 */

public class TaoyuGoodsStudyTypeFragment extends Fragment {


    private Subscriber subscriber;
    private View view;
    private List<TaoyuDataBridging> taoyuResultList = new ArrayList<>();
    RecyclerView taoyu_list;
    private SwipeRefreshLayout refreshLayout;
    private TaoyuAdapter adapter;
    private static int START=0;
    private LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            START=0;
            view = inflater.inflate(R.layout.taoyu_goods_study_type_fragment, container, false);
            //初始化所需数据
            intList();
            getTaoyuGoodsList(0);
            refreshStudy();

        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            refreshStudy();
        }
        return view;
    }

    private void refreshStudy(){
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.taoyu_study_list_refresh);
        refreshLayout.setColorSchemeColors(Color.RED);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTaoyuGoodsList(START+=10);
            }
        });
        taoyu_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部，并且是向下滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        Toast.makeText(getContext(), "加载中", Toast.LENGTH_SHORT).show();
                        getTaoyuGoodsList(START += 10);
                    }
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }

            }
        });



    }


    public void intList() {
        taoyu_list = (RecyclerView) view.findViewById(R.id.taoyu_study_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        taoyu_list.setLayoutManager(layoutManager);
        adapter = new TaoyuAdapter(taoyuResultList, getContext());
        taoyu_list.setAdapter(adapter);
    }

    public void getTaoyuGoodsList(int num){
        subscriber = new Subscriber<TaoyuGoodsData>() {
            @Override
            public void onCompleted() {
                if (refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TaoyuGoodsData taoyuGoodsData) {
                List<TaoyuDataBridging> list = taoyuGoodsData.getValues();
                if (list.size()==0){
                    Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    taoyuResultList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        TaoyuGoodsListMethods.getInstance().getGoodsList(subscriber,"学习",num);

    }

}
