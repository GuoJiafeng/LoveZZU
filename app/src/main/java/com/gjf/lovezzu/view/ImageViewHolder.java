package com.gjf.lovezzu.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.entity.TopNewsResult;

/**
 * Created by zhao on 2017/8/9.
 */

public class ImageViewHolder implements Holder<TopNewsResult> {
    private ImageView imageView;
    private Context mContent;
    @Override
    public View createView(Context context) {
        mContent=context;
        imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return null;
    }

    @Override
    public void UpdateUI(Context context, int position, TopNewsResult data) {
        Glide.with(mContent)
                .load(data.getImgesUrl())
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(imageView);
    }
}
