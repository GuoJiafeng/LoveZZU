package com.gjf.lovezzu.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.tapictalk.topic2Activity;
import com.gjf.lovezzu.entity.Topic2;

import java.util.List;



/**
 * Created by lenovo047 on 2017/5/27.
 */

public class Topic2Adapter extends  RecyclerView.Adapter<Topic2Adapter.ViewHolder>{
    private List<Topic2>  topic2List;
    private Topic2 topic2;
    private  Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.topic2_dis,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.name.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                topic2=topic2List.get(position);
                Toast.makeText(v.getContext(), "进入个人资料", Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        topic2=topic2List.get(position);
        holder.name.setText(topic2.getName());
        holder.content.setText(topic2.getContent());
        holder.zan.setText(topic2.getZan()+"");
        holder.floor.setText(topic2.getFloor()+"");
        holder.time.setText(topic2.getTime()+"");
    }

    @Override
    public int getItemCount() {
        return topic2List.size();
    }

    static  class  ViewHolder extends  RecyclerView.ViewHolder{
      View topic2View;
      TextView name;
      TextView content;
      TextView zan;
      TextView floor;
      TextView time;
        ImageView touxiang;
      public ViewHolder(View view) {
          super(view);
          topic2View=view;
          name= (TextView) view.findViewById(R.id.topic2_dis_name);
          content= (TextView) view.findViewById(R.id.topic2_dis_content);
          zan= (TextView) view.findViewById(R.id.topic2_dis_zan);
          time= (TextView) view.findViewById(R.id.topic2_dis_time);
          floor= (TextView) view.findViewById(R.id.topic2_dis_floor);
          touxiang = (ImageView) view.findViewById(R.id.topic2_dis_touxiang);


      }
  }

    public Topic2Adapter(List<Topic2> topic2List, topic2Activity topic2Activity) {
        this.topic2List = topic2List;
    }
}
