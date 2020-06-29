package com.example.administrator.baseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.htjy.baselibrary.widget.imageloader.ImageLoaderUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView iv = findViewById(R.id.iv_head);
        TextView tv_test1 = findViewById(R.id.tv_test1);
        TextView tv_test2 = findViewById(R.id.tv_test2);
        FrameLayout frameLayout = findViewById(R.id.fl_content);
        ViewPager viewpager = findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);


        mTabLayout.addTab(mTabLayout.newTab().setText("test1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("test2"));
        viewpager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(), "test1","test2"));
        mTabLayout.setupWithViewPager(viewpager);


        /*showFragment(Test1Fragment.class);
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(Test1Fragment.class);
            }
        });

        tv_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(Test2Fragment.class);
            }
        });*/
        //ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",R.color.transparent
        //,iv,20,getResources().getColor(R.color.red));
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",R.color.transparent
              //          ,iv,20,getResources().getColor(R.color.red));
            }
        });
    }

}
