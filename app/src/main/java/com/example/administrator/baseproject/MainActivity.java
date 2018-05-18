package com.example.administrator.baseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.htjy.baselibrary.widget.imageloader.ImageLoaderUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = findViewById(R.id.iv_head);
        ImageLoaderUtil.getInstance().loadImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg"
        ,iv);
    }
}
