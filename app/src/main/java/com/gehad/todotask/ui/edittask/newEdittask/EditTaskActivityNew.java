package com.gehad.todotask.ui.edittask.newEdittask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTaskActivityNew extends BaseActivity {

    public static final String TASK_TO_EDIT_EXTRA = "TASK_TO_EDIT_EXTRA";
    EditTaskNewFragment fragmentToReplace;
    Task taskToEdit;

    @BindView(R.id.taskName)
    TextView taskName;

    public static void start(Context context, Task task) {
        Intent starter = new Intent(context, EditTaskActivityNew.class);
        starter.putExtra(TASK_TO_EDIT_EXTRA, task);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_task2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            taskToEdit = getIntent().getParcelableExtra(TASK_TO_EDIT_EXTRA);
            taskName.setText(String.format("%s", taskToEdit == null ? "" : taskToEdit.getTitle()));

            fragmentToReplace = taskToEdit == null ? EditTaskNewFragment.newAddTaskInstance() : EditTaskNewFragment.newEditTaskInstance(taskToEdit);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragmentToReplace, EditTaskNewFragment.TAG)
                    .commit();
        }
    }

    @OnClick(R.id.backImage)
    public void backImageClick() {
        //fragmentToReplace = (EditTaskNewFragment) getSupportFragmentManager().findFragmentByTag(EditTaskNewFragment.TAG);
        fragmentToReplace.saveTaskChange();

       // Intent intent = new Intent(this, TaskActivity.class);
        //startActivity(intent);
       // finish();
    }


    @OnClick(R.id.deleteImg)
    public void deleteImgClick() {
        if (taskToEdit != null)
            fragmentToReplace.TaskDelete(taskToEdit);
        //Intent intent = new Intent(this, TaskActivity.class);
       // startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
