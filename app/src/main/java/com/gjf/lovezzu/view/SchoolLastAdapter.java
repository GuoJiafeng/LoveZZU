package com.gjf.lovezzu.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.school.SchoolNewsResult;

import java.util.List;

/**
 * Created by zhao on 2017/3/12.
 */

public class SchoolLastAdapter extends RecyclerView.Adapter<SchoolLastAdapter.ViewHolder> {


    private Context mContext;
    private List<SchoolNewsResult> schoolNewsResultList;
    private LayoutInflater inflater;

    public SchoolLastAdapter(List<SchoolNewsResult> schoolNewsResultList, Context context) {
        this.schoolNewsResultList = schoolNewsResultList;
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_school_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.schoolLastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击事件", Toast.LENGTH_SHORT).show();
                //点击事件
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SchoolNewsResult schoolNewsResult = schoolNewsResultList.get(position);

        //  holder.imageView.setImageResource(schoolNewsResult.getImageId());
        Glide.with(mContext)
                .load(schoolNewsResult.getUrlImg())
                .centerCrop()
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.imageView);
        holder.titleText.setText(schoolNewsResult.getTitle());
    }

    @Override
    public int getItemCount() {
        return schoolNewsResultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View schoolLastView;
        ImageView imageView;
        TextView titleText;

        public ViewHolder(View itemView) {
            super(itemView);
            schoolLastView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.in_school_last_image);
            titleText = (TextView) itemView.findViewById(R.id.in_school_last_text);
        }
    }

}
