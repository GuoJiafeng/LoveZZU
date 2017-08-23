package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.palytogether.PlayTogetherActivity;

import java.util.List;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by zhao on 2017/8/21.
 */

public class PlayGroupDynamicAdapter extends RecyclerView.Adapter<PlayGroupDynamicAdapter.ViewHolder>{

    private List<String> stringList;
    public PlayGroupDynamicAdapter(List<String> list){
        stringList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.play_group_item_dynamic,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url=stringList.get(position);
        Glide.with(PlayTogetherActivity.playTogetherActivity)
                .load(LOGIN_URL + "filedownload?action=一起玩&imageURL=" + url)
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.dynamicImage);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dynamicImage;
        public ViewHolder(View itemView) {
            super(itemView);
            dynamicImage= (ImageView) itemView.findViewById(R.id.play_group_dynamic_image);
        }
    }
}
