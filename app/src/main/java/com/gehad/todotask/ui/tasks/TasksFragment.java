package com.gehad.todotask.ui.tasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.common.util.ItemOffsetDecoration;
import com.gehad.todotask.data.local.converters.LocalDateConverter;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.domain.model.TaskFromFirebase;
import com.gehad.todotask.ui.base.BaseMvpFragment;
import com.gehad.todotask.ui.edittask.EditTaskActivity;
import com.gehad.todotask.ui.edittask.adapter.model.CommentlistDataCollectorForFirebase;
import com.gehad.todotask.ui.login.LoginActivity;
import com.gehad.todotask.ui.newTaskdiolog.AddTaskDialog;
import com.gehad.todotask.ui.tasks.adapter.FilterChangeListener;
import com.gehad.todotask.ui.tasks.adapter.TaskDeleteListener;
import com.gehad.todotask.ui.tasks.adapter.TaskDoneListener;
import com.gehad.todotask.ui.tasks.adapter.TaskEditListener;
import com.gehad.todotask.ui.tasks.adapter.TaskSpinnerListener;
import com.gehad.todotask.ui.tasks.adapter.TasksAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TasksFragment extends BaseMvpFragment<TasksPresenter>
        implements TasksView, TaskEditListener, TaskDeleteListener, TaskDoneListener, FilterChangeListener, TaskSpinnerListener {
    public static final String TAG = TasksFragment.class.getSimpleName();

    private static final String UserIdKey = "UserId";

    private final TasksAdapter tasksAdapter = new TasksAdapter(this, this, this, this);

    @BindView(R.id.tasks_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_tasks_text_view)
    TextView noTasksTextView;
    String UserId;
    ArrayList<Task> tasks = new ArrayList<>();
    private int numberOfTasks = 0;
    ArrayList<Long> taskIdsFromFirebase = new ArrayList<>();

    public static TasksFragment newInstance(String userId) {
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
        showLoading(true);
        getPresenter().setupTasksSubscription2(UserId);
    }

    public void getUserTasksFromFirebase() {
        if (UserId != null)
            TodoApp.newInstance().getTasksColliction().whereEqualTo("userId", UserId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    TaskFromFirebase task1 = document.toObject(TaskFromFirebase.class);
                                    Task task2 = new Task.Builder()
                                            .setTitle(task1.getTitle())
                                            .setDateTime(task1.getDateTime())
                                            .setPriority(task1.getPriority())
                                            .setId(task1.getId())
                                            .setIsDone(task1.isDone())
                                            .setDueDate(LocalDateConverter.fromLong(task1.getDueDate()))
                                            .setCommentList(task1.getCommentlistItemList())
                                            .setUserId(task1.getUserId())
                                            .build();

                                    tasks.add(task2);

                                    //  CommentlistDataCollectorForFirebase commentlistDataCollectorForFirebase=document.toObject(document.get("commentlistItemList"))
                                    //taskIdsFromFirebase.add()
                                }
                                Log.d(TAG, "success getting documents: " + tasks);
                                savetoDatabase(tasks);
                            } else {
                                hideLoading();

                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

    }

    private void savetoDatabase(ArrayList<Task> tasks) {
        if (tasks.size() == 0) hideLoading();
        else getPresenter().saveTasks(tasks);


    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(tasksAdapter);
        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected TasksPresenter createPresenter() {
        return TodoApp.getAppComponent(getContext()).tasksFragmentComponent().getPresenter();
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
        //update also in firebase
        updateTaskToFirebase(task);

    }

    @Override
    public void displayTasks(List<Task> tasks) {
        hideLoading();
        noTasksTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tasksAdapter.showTasks(tasks);
        //if (getActivity() != null && NetworkUtils.isNetworkConnected(getActivity()))
        //uploadTasksToFirebase(tasks);
    }

    @Override
    public void displayTasksDoneFirst(List<Task> tasks) {
        hideLoading();
        noTasksTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tasksAdapter.showTasksDonefirst(tasks);
    }

    @Override
    public void showAddTaskView() {
        AddTaskDialog addTaskDialog = new AddTaskDialog(getContext());
        addTaskDialog.show();

    }

    @Override
    public void showNoTasksView() {
        noTasksTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        //here i get tasks from firebase if user not have tasks for database
        getUserTasksFromFirebase();
    }

    @Override
    public void showEditTaskView(Task task) {
        EditTaskActivity.start(getContext(), task);
    }

    @Override
    public void showTaskMarkedAsDoneMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_marked_as_done, taskTitle), Snackbar.LENGTH_LONG);
    }

    @Override
    public void saveTasksFromFirebase() {
      /*  try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //saveCommentsForTasks();
    }

    private void saveCommentsForTasks() {

        for (Task task : tasks) {

            getPresenter().updateTaskwithcomments(task.getId(), task.getCommentlistItemList());

            ++numberOfTasks;
        }

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
    public void addComments() {
        if (numberOfTasks == tasks.size())
            hideLoading();
    }

    @Override
    public void onFilterChangeChange(boolean isDone) {
        if (isDone) {

            getPresenter().setupTasksSubscriptionDoneFirst(UserId);

        } else {
            getPresenter().setupTasksSubscription2(UserId);

        }

    }

    @Override
    public void onTaskSpinnerPriority(Task task) {
        getPresenter().updateTaskPriority(task);
        //update also in firebase

        updateTaskToFirebase(task);
    }

    private void updateTaskToFirebase(Task task) {
        TodoApp.newInstance().getTasksColliction().document(task.getUserId() + task.getDateTime()).update(
                "done", task.isDone(),
                "priority", task.getPriority()
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Timber.d(" onFailure saveTaskChange", e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Timber.d(" onSuccess saveTaskChange", task.toString());

            }
        });
    }
}
