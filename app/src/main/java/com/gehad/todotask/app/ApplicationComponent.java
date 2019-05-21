package com.gehad.todotask.app;

import javax.inject.Singleton;

import com.gehad.todotask.data.local.LocalDataModule;
import com.gehad.todotask.ui.edittask.newEdittask.EditTaskNewFragmentComponent;
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

    EditTaskNewFragmentComponent addTaskNewFragmentComponent();
    EditTaskDialogComponent addTaskDiaologComponent();
}
