package com.gehad.todotask.ui.base;

import android.os.Bundle;

import com.gehad.todotask.common.util.CommonUtils;

public abstract class BaseMvpActivity<T extends Presenter> extends BaseActivity implements MvpView {
    private ProgressDialog mProgressDialog;
    private T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        attachViewToPresenter();
    }

    public void showLoading(boolean cancelable) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this, cancelable);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void attachViewToPresenter() {
        getPresenter().onAttach(this);
    }

    public T getPresenter() {
        return presenter;
    }

    public abstract T createPresenter();
}
