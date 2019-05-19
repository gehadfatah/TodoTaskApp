package com.gehad.todotask.ui.edittask.newedittaskdiolog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseMvpDialog;
import com.gehad.todotask.ui.edittask.EditTaskDiologPresenter;
import com.gehad.todotask.ui.edittask.adapter.CheckListAdapter;
import com.gehad.todotask.ui.edittask.adapter.DueDateRequestListener;
import com.gehad.todotask.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTaskDialog extends BaseMvpDialog<EditTaskDiologPresenter> implements DueDateRequestListener, EditTaskDialogView {
    Context context;
    private CheckListAdapter checkListAdapter;

    @BindView(R.id.taskTitleEd)
    TextInputEditText taskTitleEd;
    @BindView(R.id.createBtn)
    Button createBtn;

    public AddTaskDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_newtask);
        ButterKnife.bind(this);
        this.setCancelable(true);
        if (this.getWindow() != null)
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    @Override
    public EditTaskDiologPresenter createPresenter() {
        return TodoApp.getAppComponent(getContext()).addTaskDiaologComponent().getPresenter();
    }

    @OnClick(R.id.createBtn)
    public void createBtnClick() {

        if (!taskTitleEd.getText().toString().equals("")) {
           // checkListRecyclerView.setAdapter(checkListAdapter);
           // checkListAdapter.getTask();
            //getPresenter().setupWithTask(null);

           // getPresenter().saveTask();
            getPresenter().saveNewTask(new Task.Builder()
                    .setTitle(taskTitleEd.getText().toString())
                    .setUserId(LoginActivity.user_name)
                    .build()) ;

        }
        dismiss();
    }


    @Override
    public void onTaskRequestDate() {

    }

    @Override
    public void setupViewToCreateNewTask() {
        checkListAdapter = new CheckListAdapter(this);

    }

    @Override
    public Task getTaskToSave() {
        return checkListAdapter.getTask();
    }

    @Override
    public void finish() {
        dismiss();
    }
}

