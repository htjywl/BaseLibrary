package com.htjy.baselibrary.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

/**
 * Created by hankkin on 2017/3/29.
 */

public abstract class MvpActivity<V, P extends BasePresent<V>> extends BaseActivity {

    protected P presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        if (presenter != null)
            presenter.attach((V) this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
        }

        super.onDestroy();
    }

    public abstract P initPresenter();
}
