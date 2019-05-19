package com.gehad.todotask.ui.edittask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import com.gehad.todotask.R;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseActivity;

public class EditTaskActivity extends BaseActivity {

    public static final String TASK_TO_EDIT_EXTRA = "TASK_TO_EDIT_EXTRA";

    public static void start(Context context) {
        Intent starter = new Intent(context, EditTaskActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, Task task) {
        Intent starter = new Intent(context, EditTaskActivity.class);
        starter.putExtra(TASK_TO_EDIT_EXTRA, task);
        context.startActivity(starter);
    }

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_task;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        if (savedInstanceState == null) {
            Task taskToEdit = getIntent().getParcelableExtra(TASK_TO_EDIT_EXTRA);
            Fragment fragmentToReplace = taskToEdit == null ? EditTaskFragment.newAddTaskInstance() : EditTaskFragment.newEditTaskInstance(taskToEdit);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragmentToReplace, EditTaskFragment.TAG)
                    .commit();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_accept) {
            EditTaskFragment addTaskFragment = (EditTaskFragment) getSupportFragmentManager().findFragmentByTag(EditTaskFragment.TAG);
            addTaskFragment.onAcceptClick();
        }
        return super.onOptionsItemSelected(item);
    }
}
