package com.gehad.todotask.ui.edittask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import org.threeten.bp.ZonedDateTime;

import com.gehad.todotask.common.InterfaceNotImplementedException;

public class DatePickerDialogFragment extends DialogFragment {

    public static final String TAG = DatePickerDialogFragment.class.getSimpleName();

    private static final int FRAGMENT_REQUEST_CODE = 101;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    public static DatePickerDialogFragment newInstance(Fragment targetFragment) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setTargetFragment(targetFragment, FRAGMENT_REQUEST_CODE);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment targetFragment = getTargetFragment();
        try {
            dateSetListener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new InterfaceNotImplementedException(e,
                    targetFragment.getClass().getSimpleName(),
                    DatePickerDialog.OnDateSetListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        final int year = zonedDateTime.getYear();
        final int monthIndexedFromZero = zonedDateTime.getMonthValue() - 1;
        final int dayOfMonth = zonedDateTime.getDayOfMonth();
        return new DatePickerDialog(getContext(), STYLE_NORMAL, dateSetListener, year, monthIndexedFromZero, dayOfMonth);
    }
}