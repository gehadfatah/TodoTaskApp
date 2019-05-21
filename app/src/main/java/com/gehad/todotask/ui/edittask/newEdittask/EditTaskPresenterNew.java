package com.gehad.todotask.ui.edittask.newEdittask;

import com.gehad.todotask.common.util.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;

import org.threeten.bp.LocalDate;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class EditTaskPresenterNew extends BasePresenter<EditTaskViewNew> {

    private final TaskController taskController;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public EditTaskPresenterNew(TaskController taskController) {
        this.taskController = taskController;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposables.dispose();
    }

    public void handleDateSetByUser(int year, int monthIndexedFromZero, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, monthIndexedFromZero + 1, dayOfMonth);
        getMvpView().setTaskDueDate(localDate);
    }



    public void updateTask(Task task) {
        disposables.add(taskController.updateTaskOnly(task)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::finish,
                        throwable -> Timber.e(throwable, "Error while updating task")
                )
        );
    }

    public void deleteTask(Task task) {
        disposables.add(
                taskController.deleteTask(task)
                        .compose(RxTransformers.applyCompletableIoSchedulers())
                        .subscribe(() -> getMvpView().showTaskDeletedMessage(task.getTitle()),
                                throwable -> Timber.e(throwable, "Error while deleting task")));
    }
    public void updateTaskwithcomments(Task task ) {
        disposables.add(taskController.updateTaskWithCommentlist(task)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::finish,
                        throwable -> Timber.e(throwable, "Error while updating task")
                )
        );
    }
}
