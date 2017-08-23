package com.gjf.lovezzu.fragment.school;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.school.SocietyNewsData;
import com.gjf.lovezzu.entity.school.SocietyNewsResult;
import com.gjf.lovezzu.network.SchoolSocietyMehods;
import com.gjf.lovezzu.view.SchoolSocietyAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by lenovo047 on 2017/3/9.
 */

public class Shool_societyfragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private List<SocietyNewsResult> societyNewsResultList = new ArrayList<>();
    private SchoolSocietyAdapter schoolSocietyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Subscriber subscriber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.inschool_society_view, container, false);
            onRegresh();
            getSocietyNews();
            showNews();
        }else{
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }

        return view;
    }

    private void onRegresh() {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.society_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });
    }

    //下拉刷新
    private void refreshView() {
        societyNewsResultList.clear();
        getSocietyNews();
        showNews();
    }
    //上拉加载
    private void loadMore(){
        Toast.makeText(MainActivity.mainActivity,"看的太快了，休息一下吧！",Toast.LENGTH_SHORT).show();
        getSocietyNews();
        schoolSocietyAdapter.notifyDataSetChanged();
    }
    //展示新闻
    private void showNews() {
        recyclerView = (RecyclerView) view.findViewById(R.id.school_society_content);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        schoolSocietyAdapter=new SchoolSocietyAdapter(societyNewsResultList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(schoolSocietyAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isLast=false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isLast) {
                        Toast.makeText(getContext(), "加载中", Toast.LENGTH_SHORT).show();
                        loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    isLast=true;
                }else {
                    isLast=false;
                }
            }
        });
    }

    private void getSocietyNews() {
        subscriber = new Subscriber<SocietyNewsData>() {
            @Override
            public void onCompleted() {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SocietyNewsData societyNewsData) {
               if (societyNewsData.getError_code()==0){
                   List<SocietyNewsResult> list1 = societyNewsData.getResult().getData();
                   societyNewsResultList.addAll(list1);
                   schoolSocietyAdapter.notifyDataSetChanged();
               }else{
                   Toast.makeText(MainActivity.mainActivity,"请检查网络是否通畅！",Toast.LENGTH_SHORT).show();
               }
            }

        };
        SchoolSocietyMehods.getInstance().getSocietyNews(subscriber, "d55dc1e1040e269ecebe5e826b70504c", "top");
    }


}







