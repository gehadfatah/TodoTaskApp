package com.gehad.todotask.ui.tasks;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;

import javax.inject.Inject;

import com.gehad.todotask.common.util.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.CommentlistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class TasksPresenter extends BasePresenter<TasksView> {

    private static final int FINISHING_TASKS_PERIOD = 7;

    private final TaskController taskController;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
    }

    @Inject
    public TasksPresenter(TaskController taskController) {
        this.taskController = taskController;
    }

    public void handleAddTaskClick() {
        getMvpView().showAddTaskView();
    }



    public void setupTasksSubscription2(String userId) {
        compositeDisposable.add(
                getTaskFlowable(userId)
                        .compose(RxTransformers.applyFlowableIoSchedulers())
                        .subscribe(
                                tasks -> {
                                    if (tasks.isEmpty()) {
                                        getMvpView().showNoTasksView();
                                    } else {
                                        getMvpView().displayTasks(tasks);
                                    }
                                },
                                throwable -> Timber.d("Error occurred while loading tasks", throwable)
                        ));


    }

    public void setupTasksSubscriptionDoneFirst(String userId) {
        compositeDisposable.add(
                getTaskFlowable(userId)
                        .compose(RxTransformers.applyFlowableIoSchedulers())
                        .subscribe(
                                tasks -> {
                                    if (tasks.isEmpty()) {
                                        getMvpView().showNoTasksView();
                                    } else {
                                        getMvpView().displayTasksDoneFirst(tasks);
                                    }
                                },
                                throwable -> Timber.d("Error occurred while loading tasks", throwable)
                        ));
    }



    private Flowable<List<Task>> getTaskFlowable(String userId) {
        return taskController.getAllTasksWithComments(userId);

    }

    public void deleteTask(Task task) {
        compositeDisposable.add(
                taskController.deleteTask(task)
                        .compose(RxTransformers.applyCompletableIoSchedulers())
                        .subscribe(() -> getMvpView().showTaskDeletedMessage(task.getTitle()),
                                throwable -> Timber.e(throwable, "Error while deleting task")));
    }

    public void editTask(Task task) {
        getMvpView().showEditTaskView(task);
    }

    public void updateTaskDoneState(Task task) {
        compositeDisposable.add(
                taskController.updateTaskOnly(task)
                        .compose(RxTransformers.applyCompletableIoSchedulers())
                        .subscribe(() -> {
                            if (task.isDone()) {
                                getMvpView().showTaskMarkedAsDoneMessage(task.getTitle());
                            } else {
                                getMvpView().showTaskUnmarkedAsDoneMessage(task.getTitle());
                            }
                        }, throwable ->
                                Timber.e(throwable, "Error while deleting task")));
    }

    public void updateTaskPriority(Task task) {
        compositeDisposable.add(
                taskController.updateTaskOnly(task)
                        .compose(RxTransformers.applyCompletableIoSchedulers())
                        .subscribe(() -> {
                            if (task.isDone()) {
                                // getMvpView().showTaskMarkedAsDoneMessage(task.getTitle());
                                Timber.d("", "");
                            } else {
                                //  getMvpView().showTaskUnmarkedAsDoneMessage(task.getTitle());
                                Timber.d("", "");
                            }
                        }, throwable ->
                                Timber.e(throwable, "Error while deleting task")));
    }

    public void saveTasks(List<Task> tasks) {
        compositeDisposable.add(taskController.saveTasks(tasks)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        () -> getMvpView().saveTasksFromFirebase()
                        /* Timber.d("", "")*/,
                        throwable -> Timber.e(throwable, "Error while saving task")
                )
        );
    }
    public void updateTaskwithcomments(long taskid, List<CommentlistItem> commentlistItemList) {
        compositeDisposable.add(taskController.updateTaskWithCommentsFromFirebase(taskid,commentlistItemList)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::addComments,
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Timber.e(throwable, "Error while updating task");
                            }
                        }
                )
        );
    }
}
