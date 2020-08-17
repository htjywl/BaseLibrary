package com.example.administrator.baseproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baseproject.bean.TestBean;
import com.htjy.baselibrary.bean.JavaBaseBean;
import com.htjy.baselibrary.http.base.JsonDialogCallback;
import com.htjy.baselibrary.utils.LogUtils;
import com.htjy.baselibrary.widget.imageloader.ImageLoaderUtil;
import com.lyb.besttimer.pluginwidget.utils.FragmentUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

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

        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderUtil.getInstance().loadCircleBorderImage("http://img.taopic.com/uploads/allimg/140729/240450-140HZP45790.jpg", R.color.transparent
                        , iv, 20, getResources().getColor(R.color.red));
            }
        });
        tv_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<JavaBaseBean<TestBean>>get("http://www.baokaodaxue.com/yd/Bkdxsecond_Startload/getConfig")
                        .tag(this)
                        .params("app", "1")
                        .params("type", "1")
                        .execute(new JsonDialogCallback<JavaBaseBean<TestBean>>(MainActivity.this) {
                            @Override
                            public void onSimpleSuccess(Response<JavaBaseBean<TestBean>> response) {
                                super.onSimpleSuccess(response);
                                LogUtils.d(response.body().getMessage().toString());
                            }
                        });
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
