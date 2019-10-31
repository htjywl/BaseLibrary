package com.example.administrator.baseproject;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baseproject.fragment.Test1Fragment;
import com.example.administrator.baseproject.fragment.Test2Fragment;
import com.htjy.baselibrary.widget.imageloader.ImageLoaderUtil;
import com.lyb.besttimer.pluginwidget.utils.FragmentUtil;

import org.w3c.dom.Text;

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
        ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",R.color.transparent
        ,iv,20,getResources().getColor(R.color.red));
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg",R.color.transparent
                        ,iv,20,getResources().getColor(R.color.red));
            }
        });
    }

    private void showFragment(Class<? extends Fragment> fragmentClass) {
        FragmentUtil.replace(
                getSupportFragmentManager(),
                R.id.fl_content,
                fragmentClass,
                null,
                fragmentClass.toString());
    }
}
