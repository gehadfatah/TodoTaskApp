package com.gehad.todotask.ui.edittask;

import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import com.gehad.todotask.common.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class EditTaskPresenter extends BasePresenter<EditTaskView> {

    private boolean isEditing;
    private final TaskController taskController;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public EditTaskPresenter(TaskController taskController) {
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
        if (task == null) {
            isEditing = false;
            getMvpView().setupViewToCreateNewTask();
        } else {
            isEditing = true;
            getMvpView().setupWithTaskToEdit(task);
        }
    }

    public void saveTask() {
        Task task = getMvpView().getTaskToSave();
        if (isEditing) {
            List<ChecklistItem> checklistItemsToDelete = getMvpView().getChecklistItemsToDelete();
            List<ChecklistItem> checklistItemsToUpdate = getMvpView().getChecklistItemsToUpdate();
            List<ChecklistItem> checklistItemsToAdd = getMvpView().getChecklistItemsToAdd();
            updateTask(task, checklistItemsToDelete, checklistItemsToUpdate, checklistItemsToAdd);
        } else {
            saveNewTask(task);
        }
    }

    private void updateTask(Task task, List<ChecklistItem> checklistItemsToDelete,
                            List<ChecklistItem> checklistItemsToUpdate, List<ChecklistItem> checklistItemsToAdd) {
        disposables.add(taskController.updateTaskWithChecklist(task, checklistItemsToDelete, checklistItemsToUpdate, checklistItemsToAdd)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::finish,
                        throwable -> Timber.e(throwable, "Error while updating task")
                )
        );
    }

    private void saveNewTask(Task task) {
        disposables.add(taskController.saveNewTask(task)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::finish,
                        throwable -> Timber.e(throwable, "Error while saving task")
                )
        );
    }
}
