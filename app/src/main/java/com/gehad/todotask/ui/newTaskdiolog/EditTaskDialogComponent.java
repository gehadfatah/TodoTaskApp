package com.gehad.todotask.ui.newTaskdiolog;

import com.gehad.todotask.common.di.FragmentScope;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface EditTaskDialogComponent {
    void inject(AddTaskDialog addTaskDialog);

    EditTaskDiologPresenter getPresenter();
}
