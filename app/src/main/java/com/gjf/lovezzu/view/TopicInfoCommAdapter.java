package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjf.lovezzu.R;


/**
 * Created by lenovo047 on 2017/5/27.
 */

public class TopicInfoCommAdapter extends  RecyclerView.Adapter<TopicInfoCommAdapter.ViewHolder>{


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_info_comm,parent,false);
        final ViewHolder holder=new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static  class  ViewHolder extends  RecyclerView.ViewHolder{

      public ViewHolder(View view) {
          super(view);



      }
  }


}
