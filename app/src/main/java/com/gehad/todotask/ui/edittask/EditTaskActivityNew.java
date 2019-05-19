package com.gehad.todotask.ui.edittask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseActivity;
import com.gehad.todotask.ui.base.BaseMvpActivity;
import com.gehad.todotask.ui.base.BaseMvpFragment;
import com.gehad.todotask.ui.edittask.adapter.DueDateRequestListener;
import com.gehad.todotask.ui.edittask.newedittaskdiolog.EditTaskDialogView;

import org.threeten.bp.LocalDate;

import butterknife.BindView;

public class EditTaskActivityNew extends BaseMvpActivity<EditTaskPresenterNew>
        implements EditTaskViewNew, DatePickerDialog.OnDateSetListener, DueDateRequestListener {

    public static final String TASK_TO_EDIT_EXTRA = "TASK_TO_EDIT_EXTRA";
    @BindView(R.id.dateTv)
    EditText dueDateTextView;
    @BindView(R.id.cbItemCheck)
    CheckBox doneCheckbox;
    @BindView(R.id.tvItemPriority)
    TextView tvItemPriority;
    @BindView(R.id.spinner)
    Spinner spinner;
    private static final String[] Priority = {"law", "medium","high"};

    public static void start(Context context, Task task) {
        Intent starter = new Intent(context, EditTaskActivityNew.class);
        starter.putExtra(TASK_TO_EDIT_EXTRA, task);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_task2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Task taskToEdit = getIntent().getParcelableExtra(TASK_TO_EDIT_EXTRA);
            // spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Priority));

        }
    }

    @Override
    public EditTaskPresenterNew createPresenter() {
        return null;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public void onTaskRequestDate() {
        DatePickerDialogFragment.newInstance(null).show(getFragmentManager(), DatePickerDialogFragment.TAG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //getPresenter().handleDateSetByUser(year, monthIndexedFromZero, dayOfMonth);
        LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);

    }

    @Override
    public void setTaskDueDate(LocalDate localDate) {

    }

    @Override
    public void setupWithTaskToEdit(Task task) {

    }

    @Override
    public Task getTaskToSave() {
        return null;
    }
}
