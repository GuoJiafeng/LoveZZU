package com.gjf.lovezzu.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;
import com.gjf.lovezzu.entity.TopNewsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2017/8/9.
 */

public class ImageViewHolder implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {

        Glide.with(MainActivity.mainActivity)
                .load(data)
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(imageView);
    }
}
