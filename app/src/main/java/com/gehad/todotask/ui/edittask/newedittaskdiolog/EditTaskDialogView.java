package com.gehad.todotask.ui.edittask.newedittaskdiolog;

import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.MvpView;

public interface EditTaskDialogView  extends MvpView {
    void setupViewToCreateNewTask();
    Task getTaskToSave();
    void finish();


}
