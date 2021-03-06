package com.gehad.todotask.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gehad.todotask.common.util.CommonUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void showLoading(boolean cancelable) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this.getContext(),cancelable);
    }
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @LayoutRes
    protected abstract int getLayout();

    public void showTextOnSnackbar(@NonNull String text, int duration) {
        View rootView = getView();
        if (rootView != null) {
            Snackbar.make(rootView, text, duration).show();
        }
    }
}
