package com.gehad.todotask.ui.base;

import android.os.Bundle;

public abstract class BaseMvpActivity<T extends Presenter> extends BaseActivity implements MvpView {

    private T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        attachViewToPresenter();
    }

    private void attachViewToPresenter() {
        getPresenter().onAttach(this);
    }

    public T getPresenter() {
        return presenter;
    }

    public abstract T createPresenter();
}
