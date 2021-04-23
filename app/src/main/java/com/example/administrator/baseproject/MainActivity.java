package com.example.administrator.baseproject;

import android.os.Bundle;
import android.view.View;

import com.example.administrator.baseproject.bean.TestBean;
import com.example.administrator.baseproject.databinding.ActivityMainBinding;
import com.htjy.baselibrary.base.BaseAcitvity;
import com.htjy.baselibrary.bean.JavaBaseBean;
import com.htjy.baselibrary.http.base.JsonDialogCallback;
import com.htjy.baselibrary.utils.DialogUtils;
import com.htjy.baselibrary.utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class MainActivity extends BaseAcitvity {

    private ActivityMainBinding mainBinding;

    @Override
    protected boolean isBinding() {
        return true;
    }

    @Override
    protected void setContentViewByBinding(int layoutId) {
        mainBinding = getContentViewByBinding(layoutId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mainBinding.tabLayout.addTab(mainBinding.tabLayout.newTab().setText("test1"));
        mainBinding.tabLayout.addTab(mainBinding.tabLayout.newTab().setText("test2"));
        mainBinding.viewpager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager(), "test1", "test2"));
        mainBinding.tabLayout.setupWithViewPager(mainBinding.viewpager);

        mainBinding.tvTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showSimpleDialog(activity, "000", null);
                DialogUtils.showConfirmDialog(activity, "123", "456", null, null);
            }
        });
        mainBinding.tvTest2.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initStateLayout() {

    }

    @Override
    public void showNullLayout() {

    }

    @Override
    public void showErrorLayout() {

    }

    @Override
    public void showSuccessLayout() {

    }

    @Override
    public void showNetWorkErrorLayout() {

    }
}
