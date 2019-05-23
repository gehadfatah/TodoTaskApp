package com.gehad.todotask.ui.edittask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTaskActivity extends BaseActivity {

    public static final String TASK_TO_EDIT_EXTRA = "TASK_TO_EDIT_EXTRA";
    EditTaskNewFragment fragmentToReplace;
    Task taskToEdit;
    public static final String TAG = EditTaskActivity.class.getSimpleName();

    @BindView(R.id.taskName)
    TextView taskName;

    public static void start(Context context, Task task) {
        Intent starter = new Intent(context, EditTaskActivity.class);
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
        fragmentToReplace.saveTaskChange();

    }


    @OnClick(R.id.deleteImg)
    public void deleteImgClick() {
        if (taskToEdit != null) {
            fragmentToReplace.TaskDelete(taskToEdit);
            deletFromFirebase(taskToEdit);
        }
        finish();
    }

    private void deletFromFirebase(Task task) {
        TodoApp.newInstance().getTasksColliction().document(task.getUserId() + task.getDateTime())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
