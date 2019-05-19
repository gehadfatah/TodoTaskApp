package com.gehad.todotask.ui.tasks;

import com.gehad.todotask.common.di.FragmentScope;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface TasksFragmentComponent {

    void inject(TasksFragment tasksFragment);

    TasksPresenter getPresenter();
}
