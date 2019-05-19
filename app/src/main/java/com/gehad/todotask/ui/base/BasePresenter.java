package com.gehad.todotask.ui.base;

public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void onAttach(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    protected T getMvpView() {
        return mMvpView;
    }
}
