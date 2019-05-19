package com.gehad.todotask.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public abstract class BaseMvpFragment<T extends Presenter> extends BaseFragment implements MvpView {

    private T presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = createPresenter();
        attachViewToPresenter();
    }

    @SuppressWarnings("unchecked")
    private void attachViewToPresenter() {
        getPresenter().onAttach(this);
    }

    @Override
    public void onDestroyView() {
        getPresenter().onDetach();
        super.onDestroyView();
    }

    protected T getPresenter() {
        return presenter;
    }

    protected abstract T createPresenter();
}
