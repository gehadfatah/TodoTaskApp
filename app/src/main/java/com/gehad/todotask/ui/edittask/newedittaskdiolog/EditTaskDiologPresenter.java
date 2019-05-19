package com.gehad.todotask.ui.edittask.newedittaskdiolog;

import android.support.annotation.Nullable;

import com.gehad.todotask.common.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;
import com.gehad.todotask.ui.edittask.newedittaskdiolog.EditTaskDialogView;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class EditTaskDiologPresenter extends BasePresenter<EditTaskDialogView> {

    private final TaskController taskController;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public EditTaskDiologPresenter(TaskController taskController) {
        this.taskController = taskController;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposables.dispose();
    }

    public void saveNewTask(Task task) {
        disposables.add(taskController.saveNewTask(task)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribe(
                        getMvpView()::finish,
                        throwable -> Timber.e(throwable, "Error while saving task")
                )
        );
    }
}
