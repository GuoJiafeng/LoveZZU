package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Leon on 2017/7/30.
 */

public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {



    public ShopcartAdapter() {


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.good_content.setText(shopcart.getGood_content()+"");
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    static class  ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View view) {
            super(view);

        }
    }
}
