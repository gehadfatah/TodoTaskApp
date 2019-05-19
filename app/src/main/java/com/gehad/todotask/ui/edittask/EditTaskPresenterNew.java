package com.gehad.todotask.ui.edittask;

import android.support.annotation.Nullable;

import com.gehad.todotask.common.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;

import org.threeten.bp.LocalDate;

import java.util.List;

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

    public void setupWithTask(@Nullable Task task) {

            getMvpView().setupWithTaskToEdit(task);

    }

    public void saveTask() {
        Task task = getMvpView().getTaskToSave();
        updateTask(task);

    }

    private void updateTask(Task task) {
        disposables.add(taskController.updateTaskOnly(task)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::finish,
                        throwable -> Timber.e(throwable, "Error while updating task")
                )
        );
    }


}
