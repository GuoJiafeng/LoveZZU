package com.gjf.lovezzu.activity.tapictalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjf.lovezzu.R;
import com.gjf.lovezzu.activity.MainActivity;

/**
 * Created by Leon on 2017/7/25.
 */

public class Topic_pub extends AppCompatActivity {
    private TextView backText;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_pub);
        backText = (TextView) findViewById(R.id.topic_pub_back);


        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Topic_pub.this, TopicTalkActivity.class);
                startActivity(intent);
            }
        });
    }
}
