package com.gehad.todotask.app;

import javax.inject.Singleton;

import com.gehad.todotask.data.local.LocalDataModule;
import com.gehad.todotask.ui.edittask.EditTaskFragmentComponent;
import com.gehad.todotask.ui.edittask.newedittaskdiolog.EditTaskDialogComponent;
import com.gehad.todotask.ui.tasks.TasksFragmentComponent;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        LocalDataModule.class
})
public interface ApplicationComponent {

    TasksFragmentComponent tasksFragmentComponent();

    EditTaskFragmentComponent addTaskFragmentComponent();
    EditTaskDialogComponent addTaskDiaologComponent();
}
