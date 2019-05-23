package com.gehad.todotask.ui.edittask;

import com.gehad.todotask.common.di.FragmentScope;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface EditTaskNewFragmentComponent {

    void inject(EditTaskNewFragment addTaskNewFragment);

    EditTaskPresenterNew getPresenter();
}
