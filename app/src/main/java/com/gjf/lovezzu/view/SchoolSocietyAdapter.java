package com.gjf.lovezzu.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.activity.schoolnewsActivity.SchoolNewsWebView;
import com.gjf.lovezzu.entity.SocietyNewsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2017/3/15.
 */

public class SchoolSocietyAdapter extends RecyclerView.Adapter<SchoolSocietyAdapter.ViewHolder> {

    private List<SocietyNewsResult> societyNewsResultList = new ArrayList<>();


    public SchoolSocietyAdapter(List<SocietyNewsResult> societyNewsResults) {
        societyNewsResultList = societyNewsResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_society_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.news_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocietyNewsResult societyNewsResult1=societyNewsResultList.get(holder.getAdapterPosition());
                String url=societyNewsResult1.getUrl();
                Intent intent=new Intent();
                intent.setClass(MainActivity.mainActivity,SchoolNewsWebView.class);
                intent.putExtra("url",url);
                MainActivity.mainActivity.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SocietyNewsResult societyNewsResult = societyNewsResultList.get(position);
        holder.news_title.setText(societyNewsResult.getTitle());
        Glide.with(MainActivity.mainActivity)
                .load(societyNewsResult.getThumbnail_pic_s())
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.news_images);
        holder.news_time.setText(societyNewsResult.getDate());
        holder.news_author.setText(societyNewsResult.getAuthor_name());

    }

    @Override
    public int getItemCount() {
        return societyNewsResultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout news_body;
        ImageView news_images;
        TextView news_title;
        TextView news_time;
        TextView news_author;

        public ViewHolder(View itemView) {
            super(itemView);
            news_body = (LinearLayout) itemView.findViewById(R.id.news_body);
            news_images = (ImageView) itemView.findViewById(R.id.news_image);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_time = (TextView) itemView.findViewById(R.id.news_time);
            news_author = (TextView) itemView.findViewById(R.id.news_author);

        }
    }


}
