package com.gjf.lovezzu.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.taoyu.ShopcartActivity;
import com.gjf.lovezzu.activity.taoyu.TaoyuOrderActivity;
import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.OrderDataBridging;
import com.gjf.lovezzu.entity.OrderDataResult;
import com.gjf.lovezzu.entity.OrderGoodsImagesItem;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderActivityAdapter extends RecyclerView.Adapter<OrderActivityAdapter.ViewHolder>{

    private List<OrderDataBridging> orderDataBridgingList;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderGoodsImagesItem> orderGoodsImagesItemList=new ArrayList<>();
    private OrderGoodsImagesAdapter orderGoodsImagesAdapter;
    public OrderActivityAdapter ( List<OrderDataBridging> orderDataBridgings){
        orderDataBridgingList=orderDataBridgings;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(TaoyuOrderActivity.taoyuOrderActivity);
                builder.setTitle("删除订单");
                builder.setMessage("确定要删除该订单吗？该订单包含的所用商品都将清除！");
                builder.setCancelable(true);
                builder.setPositiveButton("狠心删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderDataBridging orderDataBridgingl=orderDataBridgingList.get(viewHolder.getAdapterPosition());
                        deleteOder(orderDataBridgingl.getOrderdata().getOrder_id()+"");
                    }
                });
                builder.show();
            }
        });

        linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.order_goods_imaes.setLayoutManager(linearLayoutManager);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDataBridging orderDataBridging=orderDataBridgingList.get(position);
        holder.order_name.setText(orderDataBridging.getOrderdata().getName());
        holder.order_price.setText(orderDataBridging.getOrderdata().getTotal());
        holder.order_status.setText(orderDataBridging.getOrderdata().getOrder_status());
        if (!orderGoodsImagesItemList.isEmpty()){
            orderGoodsImagesItemList.clear();
        }
        OrderDataBridging orderDataBridging1=orderDataBridgingList.get(holder.getAdapterPosition());
        List<OrderDataResult> orderDataResultList=orderDataBridging1.getUgoods();
        for (OrderDataResult orderDataResult:orderDataResultList){

            String goodsImagesURL =orderDataResult.getGoods().getGimage();
            String imagesURL[] = goodsImagesURL.split("ZZU");

            OrderGoodsImagesItem orderGoodsImagesItem=new OrderGoodsImagesItem();
            orderGoodsImagesItem.setName(orderDataResult.getGoods().getGname());
            orderGoodsImagesItem.setImages(imagesURL[0]);
            orderGoodsImagesItemList.add(orderGoodsImagesItem);
        }

        orderGoodsImagesAdapter=new OrderGoodsImagesAdapter(orderGoodsImagesItemList,orderDataBridging1);
        holder.order_goods_imaes.setAdapter(orderGoodsImagesAdapter);
    }

    @Override
    public int getItemCount() {
        return orderDataBridgingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_status;
        TextView order_delete;
        RecyclerView order_goods_imaes;
        TextView order_price;
        public ViewHolder(View itemView) {
            super(itemView);
            order_name= (TextView) itemView.findViewById(R.id.order_user_name);
            order_status= (TextView) itemView.findViewById(R.id.order_status);
            order_delete= (TextView) itemView.findViewById(R.id.order_delete);
            order_goods_imaes= (RecyclerView) itemView.findViewById(R.id.order_goods_images);
            order_price= (TextView) itemView.findViewById(R.id.order_price);
        }
    }


    private void deleteOder(String orderID){
        RequestParams requestParams=new RequestParams(Url.LOGIN_URL+"OrderAction");
        requestParams.addBodyParameter("action","删除订单");
        requestParams.addBodyParameter("OrderID",orderID);

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean res=jsonObject.getBoolean("isSuccessful");
                    if (res){
                        Toast.makeText(TaoyuOrderActivity.taoyuOrderActivity,"已删除",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TaoyuOrderActivity.taoyuOrderActivity,"请保持网络通畅",Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });


    }
}
