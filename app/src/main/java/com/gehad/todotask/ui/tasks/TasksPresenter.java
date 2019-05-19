package com.gehad.todotask.ui.tasks;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;

import javax.inject.Inject;

import com.gehad.todotask.common.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
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

    public void setupTasksSubscription(TasksType taskType) {
        compositeDisposable.add(
                getTaskFlowableBasedOnType(taskType)
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

    private Flowable<List<Task>> getTaskFlowableBasedOnType(TasksType tasksType) {
        switch (tasksType) {
            case TODO:
                return taskController.getToDoTasks();
            case DONE:
                return taskController.getDoneTasks();
            case FINISHING:
                return taskController.getTasksWithDueDateBefore(LocalDate.now().plus(FINISHING_TASKS_PERIOD, ChronoUnit.DAYS));

            default:
                throw new IllegalArgumentException("Unknown task type");
        }
    }

    private Flowable<List<Task>> getTaskFlowable(String userId) {
        return taskController.getAllTasks(userId);

    }

    private Flowable<List<Task>> getTaskFlowable2(String userId) {
        return taskController.getDoneTasks().concatWith(
                taskController.getToDoTasks());
        /*return Single.merge(taskController.getDoneTasksSingle(),
                taskController.getToDoTasksSingle());*/

    }

    private Single<List<Task>> getTaskFlowableTodo(String userId) {
    /*    return taskController.getDoneTasks().zipWith(
                taskController.getToDoTasks());
*/
        return
                taskController.getToDoTasksSingle();

    }

    private Flowable<List<Task>> getTaskFlowableDone(String userId) {
        return taskController.getDoneTasks();


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
                        }, throwable -> Timber.e(throwable, "Error while deleting task")));
    }
}
