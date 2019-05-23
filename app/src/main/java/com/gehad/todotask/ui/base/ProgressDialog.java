package com.gehad.todotask.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;


import com.gehad.todotask.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressDialog extends Dialog {

    Context context;
    @BindView(R.id.progress_loader)
    ProgressBar loaderprogressbar;

    public ProgressDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        ButterKnife.bind(this);
        if (this.getWindow() != null) {

            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }


}
