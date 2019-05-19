package com.gehad.todotask.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

public abstract class BaseMvpDialog<T extends Presenter> extends BaseDialog implements MvpView {

    private T presenter;

    public BaseMvpDialog(@NonNull Context context) {
        super(context);
    }

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
