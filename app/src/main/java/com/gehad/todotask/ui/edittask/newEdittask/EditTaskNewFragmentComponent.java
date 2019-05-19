package com.gehad.todotask.ui.edittask.newEdittask;

import com.gehad.todotask.common.di.FragmentScope;
import com.gehad.todotask.ui.edittask.EditTaskFragment;
import com.gehad.todotask.ui.edittask.EditTaskPresenter;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface EditTaskNewFragmentComponent {

    void inject(EditTaskNewFragment addTaskNewFragment);

    EditTaskPresenterNew getPresenter();
}
