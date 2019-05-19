package com.gehad.todotask.ui.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.gehad.todotask.R;
import com.gehad.todotask.app.App;
import com.gehad.todotask.common.ItemOffsetDecoration;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseMvpFragment;
import com.gehad.todotask.ui.edittask.EditTaskActivity;
import com.gehad.todotask.ui.edittask.EditTaskActivityNew;
import com.gehad.todotask.ui.edittask.newedittaskdiolog.AddTaskDialog;
import com.gehad.todotask.ui.edittask.newedittaskdiolog.EditTaskDialogView;
import com.gehad.todotask.ui.tasks.adapter.FilterChangeListener;
import com.gehad.todotask.ui.tasks.adapter.TaskDeleteListener;
import com.gehad.todotask.ui.tasks.adapter.TaskDoneListener;
import com.gehad.todotask.ui.tasks.adapter.TaskEditListener;
import com.gehad.todotask.ui.tasks.adapter.TasksAdapter;

public class TasksFragment extends BaseMvpFragment<TasksPresenter>
        implements TasksView, TaskEditListener, TaskDeleteListener, TaskDoneListener, FilterChangeListener {

    private static final String UserIdKey = "UserId";

    private final TasksAdapter tasksAdapter = new TasksAdapter(this, this, this);

    @BindView(R.id.tasks_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_tasks_text_view)
    TextView noTasksTextView;
    String UserId;

    public static TasksFragment newInstance(String userId/*TasksType tasksType*/) {
        Bundle args = new Bundle();
        args.putString(UserIdKey, userId);

        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        UserId = getArguments().getString(UserIdKey);
        getPresenter().setupTasksSubscription2(UserId);
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(tasksAdapter);
        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected TasksPresenter createPresenter() {
        return App.getAppComponent(getContext()).tasksFragmentComponent().getPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tasks;
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        getPresenter().handleAddTaskClick();
    }

    @Override
    public void onTaskEdit(Task task) {
        getPresenter().editTask(task);
    }

    @Override
    public void onTaskDelete(Task task) {
        getPresenter().deleteTask(task);
    }

    @Override
    public void onTaskDoneStateChange(Task task) {
        getPresenter().updateTaskDoneState(task);
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        noTasksTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tasksAdapter.showTasks(tasks);
    }

    @Override
    public void displayTasksDoneFirst(List<Task> tasks) {
        noTasksTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tasksAdapter.showTasksDonefirst(tasks);
    }

    @Override
    public void showAddTaskView() {
       // EditTaskActivity.start(getContext());
        AddTaskDialog addTaskDialog=new AddTaskDialog(getContext());
        addTaskDialog.show();

    }

    @Override
    public void showNoTasksView() {
        noTasksTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showEditTaskView(Task task) {
       // EditTaskActivity.start(getContext(), task);
        EditTaskActivityNew.start(getContext(), task);
    }

    @Override
    public void showTaskMarkedAsDoneMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_marked_as_done, taskTitle), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showTaskUnmarkedAsDoneMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_unmarked_as_done, taskTitle), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showTaskDeletedMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_deleted, taskTitle), Snackbar.LENGTH_LONG);
    }

    @Override
    public void onFilterChangeChange(boolean isDone) {
        if (isDone) {

            getPresenter().setupTasksSubscriptionDoneFirst(UserId);

        } else {
            getPresenter().setupTasksSubscription2(UserId);

        }

    }
}
