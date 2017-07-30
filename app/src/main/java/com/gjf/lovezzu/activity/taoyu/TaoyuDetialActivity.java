package com.gjf.lovezzu.activity.taoyu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjf.lovezzu.R;

/**
 * Created by BlackBeard丶 on 2017/04/18.
 */

public class TaoyuDetialActivity extends AppCompatActivity {
    private RecyclerView goods_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        showphoto();
    }

    private void showphoto() {
        goods_photo = (RecyclerView) findViewById(R.id.goods_photo_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        goods_photo.setLayoutManager(layoutManager);

    }
}
