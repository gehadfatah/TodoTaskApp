package com.gehad.todotask.ui.edittask.newedittaskdiolog;

import com.gehad.todotask.common.util.RxTransformers;
import com.gehad.todotask.domain.TaskController;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BasePresenter;

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
