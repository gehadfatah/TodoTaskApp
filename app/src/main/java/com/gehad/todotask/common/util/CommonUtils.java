package com.gehad.todotask.common.util;

import android.content.Context;

import com.gehad.todotask.ui.base.ProgressDialog;

import java.util.Random;

public class CommonUtils {
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static ProgressDialog showLoadingDialog(Context context, boolean canelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(canelable);
        return progressDialog;
    }
}
