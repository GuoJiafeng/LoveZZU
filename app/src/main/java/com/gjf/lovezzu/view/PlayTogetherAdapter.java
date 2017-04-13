package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.PlayEnd;
import com.gjf.lovezzu.entity.PlayItems;
import com.gjf.lovezzu.entity.PlayTop;

import java.util.List;

/**
 * Created by zhaox on 2017/4/9.
 */

public class PlayTogetherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  /*  private List<PlayTop> playTops;
    private List<PlayEnd> playEnds;*/
    private List<PlayItems> playItems;
    public PlayTogetherAdapter(List<PlayItems> playItemsList) {
        this.playItems=playItemsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        RecyclerView.ViewHolder viewHolder=null;
        switch (viewType){
            case 1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.play_item_top,parent,false);
                viewHolder = new TypeOneViewViewHolder(view);
                break;
            case 2:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.play_item_end,parent,false);
                viewHolder =new TypeTwoViewViewHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)){
                case 1:
                    final TypeOneViewViewHolder typeOneViewViewHolder= (TypeOneViewViewHolder) holder;
                    PlayTop playTop= (PlayTop) playItems.get(position);
                    typeOneViewViewHolder.circleImageView.setImageResource(playTop.getUserImage());
                    typeOneViewViewHolder.userName.setText(playTop.getUserName());
                    typeOneViewViewHolder.userTime.setText(playTop.getDateTime());
                    typeOneViewViewHolder.userNice.setText(playTop.getNice());
                    typeOneViewViewHolder.userTalk.setText(playTop.getTalk());
                    typeOneViewViewHolder.userTitle.setText(playTop.getUserTitle());
                    typeOneViewViewHolder.newsTilte.setText(playTop.getNewsTitle());
                    typeOneViewViewHolder.newsImage.setImageResource(playTop.getNewsImage());
                    break;
                case 2:
                    final TypeTwoViewViewHolder typeTwoViewViewHolder= (TypeTwoViewViewHolder) holder;
                    PlayEnd playEnd= (PlayEnd) playItems.get(position);
                    typeTwoViewViewHolder.groupImage.setImageResource(playEnd.getGroupImage());
                    typeTwoViewViewHolder.groupName.setText(playEnd.getGroupName());
                    typeTwoViewViewHolder.groupInfo.setText(playEnd.getGroupInfo());
                    typeTwoViewViewHolder.tagsImage1.setImageResource(playEnd.getGroupTagesImages1());
                    typeTwoViewViewHolder.tagsImage2.setImageResource(playEnd.getGroupTagesImages2());
                    typeTwoViewViewHolder.groupTitle.setText(playEnd.getGroupNewsTitle());
                    typeTwoViewViewHolder.groupUserInfo1.setImageResource(playEnd.getInfoImages1());
                    typeTwoViewViewHolder.groupUserInfo2.setImageResource(playEnd.getInfoImages2());
                    typeTwoViewViewHolder.groupUserInfo3.setImageResource(playEnd.getInfoImages3());
                    typeTwoViewViewHolder.groupUserImage1.setImageResource(playEnd.getJoinersImage1());
                    typeTwoViewViewHolder.groupUserImage2.setImageResource(playEnd.getJoinersImage2());
                    typeTwoViewViewHolder.groupUserImage3.setImageResource(playEnd.getJoinersImage3());
                    typeTwoViewViewHolder.groupUserImage4.setImageResource(playEnd.getJoinersImage4());
                    break;
            }
    }


    @Override
    public int getItemCount() {
        return playItems.size();
    }



    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }
}
