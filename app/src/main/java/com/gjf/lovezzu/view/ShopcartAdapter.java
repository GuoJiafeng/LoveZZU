package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.ShopcartActivity;

import com.gjf.lovezzu.entity.Shopcart;

import java.util.List;

/**
 * Created by Leon on 2017/7/30.
 */

public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {
    private List<Shopcart> shopcartList;
    private Shopcart shopcart;

    public ShopcartAdapter(List<Shopcart> shopcartList, ShopcartActivity shopcart) {
        this.shopcartList = shopcartList;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        shopcart=shopcartList.get(position);
        holder.seller_name.setText(shopcart.getSeller_name());
        holder.good_name.setText(shopcart.getGood_name());
        holder.price.setText(shopcart.getPrice()+"");
        holder.good_Image.setImageResource(R.drawable.fragment_back);
        //holder.good_content.setText(shopcart.getGood_content()+"");
    }

    @Override
    public int getItemCount() {
        return  shopcartList.size();
    }


    static class  ViewHolder extends RecyclerView.ViewHolder{
        View shopcart_view;
        ImageView good_Image;
        TextView price;
        TextView good_name;
        TextView good_content;
        TextView seller_name;
        public ViewHolder(View view) {
            super(view);
            shopcart_view=view;
            good_content= (TextView) view.findViewById(R.id.goods_content);
            good_Image= (ImageView) view.findViewById(R.id.good_Image);
            price= (TextView) view.findViewById(R.id.goods_price);
            good_name= (TextView) view.findViewById(R.id.cart_good_name);
            seller_name= (TextView) view.findViewById(R.id.seller_name);
        }
    }
}
