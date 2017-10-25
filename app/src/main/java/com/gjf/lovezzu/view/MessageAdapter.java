package com.gjf.lovezzu.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.Message.MessageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aotu on 2017/10/24.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageItem> mMessageItems=new ArrayList<MessageItem>();

    public MessageAdapter(List<MessageItem> messageItems){
        mMessageItems=messageItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageItem messageItem=mMessageItems.get(position);
        holder.mCheckBox.setChecked(messageItem.isBoolean());
        holder.mCheckBox.setText(messageItem.getMessageChecked());
        holder.mTime.setText(messageItem.getMessageTime());
        holder.mMessageContent.setText(messageItem.getMessageContent());
        Glide.with(MainActivity.mainActivity)
                .load(messageItem.getUserInfoResult().getImageUrl())
                .centerCrop().dontAnimate()
                .placeholder(R.drawable.def_avatar)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.mCircleImageView);
        holder.userName.setText(messageItem.getUserInfoResult().getNickname());

    }

    @Override
    public int getItemCount() {
        return mMessageItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mCircleImageView;
        TextView userName;
        TextView mTime;
        TextView mMessageContent;
        TextView mAnswer;
        RadioButton mCheckBox;
        public ViewHolder(View itemView) {
            super(itemView);
            mCircleImageView= (CircleImageView) itemView.findViewById(R.id.message_user_icon);
            userName= (TextView) itemView.findViewById(R.id.message_user_nickname);
            mTime= (TextView) itemView.findViewById(R.id.message_time);
            mMessageContent= (TextView) itemView.findViewById(R.id.message_content);
            mAnswer= (TextView) itemView.findViewById(R.id.message_answer);
            mCheckBox= (RadioButton) itemView.findViewById(R.id.message_radio_button);
        }
    }
}
