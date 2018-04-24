package com.htjy.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : jiangwei
 *     e-mail : jiangwei_android@163.com
 *     time   : 2017/07/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class MvpFragment<V,P extends BasePresent<V>> extends BaseFragment {
    protected P presenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter = initPresenter();
        return view;
    }



    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.detach();

        super.onDestroy();
    }



    protected abstract P initPresenter();
}
