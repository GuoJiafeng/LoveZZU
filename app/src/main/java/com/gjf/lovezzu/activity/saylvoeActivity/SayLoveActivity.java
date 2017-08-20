package com.gjf.lovezzu.activity.saylvoeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.gjf.lovezzu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BlackBeard丶 on 2017/03/22.
 */
public class SayLoveActivity extends AppCompatActivity {


    @BindView(R.id.sayloveWall)
    LinearLayout sayloveWall;
    @BindView(R.id.saylove_iwantsaylove)
    LinearLayout sayloveIwantsaylove;
    public static SayLoveActivity sayLoveActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biaobai);
        ButterKnife.bind(this);
        sayLoveActivity=this;

    }


    private void goTosayloveWall() {
        Intent intent = new Intent(SayLoveActivity.this, SayloveWallActivity.class);
        startActivity(intent);
    }


    private void goToSayLove() {
        Intent intent = new Intent(SayLoveActivity.this, IWantSayLoveActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.sayloveWall,R.id.sayloveWall_image, R.id.saylove_iwantsaylove,R.id.saylove_iwantsaylove_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sayloveWall:
                goTosayloveWall();
                break;
            case R.id.saylove_iwantsaylove:
                goToSayLove();
                break;
            case R.id.sayloveWall_image:
                goTosayloveWall();
                break;
            case R.id.saylove_iwantsaylove_image:
                goToSayLove();
                break;
        }
    }
}
