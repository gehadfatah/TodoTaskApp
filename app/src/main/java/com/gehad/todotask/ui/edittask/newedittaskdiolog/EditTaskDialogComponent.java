package com.gehad.todotask.ui.edittask.newedittaskdiolog;

import com.gehad.todotask.common.di.FragmentScope;
import com.gehad.todotask.ui.edittask.EditTaskDiologPresenter;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface EditTaskDialogComponent {
    void inject(AddTaskDialog addTaskDialog);

    EditTaskDiologPresenter getPresenter();
}
