package com.gehad.todotask.ui.tasks;

import android.content.Intent;
import android.os.Build;
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
import com.gehad.todotask.common.Services.UploadFirebaseTasksService;
import com.gehad.todotask.common.util.ItemOffsetDecoration;
import com.gehad.todotask.common.util.NetworkUtils;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseMvpFragment;
import com.gehad.todotask.ui.edittask.newEdittask.EditTaskActivityNew;
import com.gehad.todotask.ui.login.LoginActivity;
import com.gehad.todotask.ui.newedittaskdiolog.AddTaskDialog;
import com.gehad.todotask.ui.tasks.adapter.FilterChangeListener;
import com.gehad.todotask.ui.tasks.adapter.TaskDeleteListener;
import com.gehad.todotask.ui.tasks.adapter.TaskDoneListener;
import com.gehad.todotask.ui.tasks.adapter.TaskEditListener;
import com.gehad.todotask.ui.tasks.adapter.TaskSpinnerListener;
import com.gehad.todotask.ui.tasks.adapter.TasksAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    ArrayList<Task> taskArrayList;

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
        getUserTasksFromFirebase();
    }

    public void getUserTasksFromFirebase() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (UserId != null)
            TodoApp.newInstance().getTasksColliction().whereEqualTo("userId", UserId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Task task1 = document.toObject(Task.class);
                                    tasks.add(task1);
                                }
                                Log.d(TAG, "success getting documents: " +tasks);
                                //savetoDatabase(tasks);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

    }

    private void savetoDatabase(ArrayList<Task> tasks) {
        for (Task task :
                tasks) {
            getPresenter().saveNewTask(task);
        }

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
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        noTasksTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tasksAdapter.showTasks(tasks);
        //if (getActivity() != null && NetworkUtils.isNetworkConnected(getActivity()))
        //uploadTasksToFirebase(tasks);
    }

    private void uploadTasksToFirebase(List<Task> tasks) {
        if (getActivity() == null) return;
        Intent Intent = new Intent();
        Intent.putParcelableArrayListExtra("tasks", (ArrayList<Task>) tasks);
        Intent.putExtra("userId", UserId);
        Intent.setClass(getActivity(), UploadFirebaseTasksService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(Intent);
        } else {
            getActivity().startService(Intent);
        }
        /*for (Task task : tasks) {

            TodoApp.newInstance().getTasksColliction().document(UserId + String.valueOf(System.currentTimeMillis())).set(new Task.Builder()
                    .setId(task.getId())
                    .setTitle(task.getTitle())
                    .setIsDone(task.isDone())
                    .setPriority(task.getPriority())
                    .setCommentList(task.getCommentlistItemList())
                    .setUserId(LoginActivity.user_name)
                    .build()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Timber.d(" onFailure uploadTasksToFirebase", e + " " + task.toString());
                    //failedUploadtasks.add(task);
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Timber.d(" onSuccess uploadTasksToFirebase", task.toString());

                }
            });
        }*/
    }

    @Override
    public void displayTasksDoneFirst(List<Task> tasks) {
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
    }

    @Override
    public void showEditTaskView(Task task) {
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

    @Override
    public void onTaskSpinnerPriority(Task task) {
        getPresenter().updateTaskPriority(task);

    }
}
