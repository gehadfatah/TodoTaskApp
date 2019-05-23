package com.gehad.todotask.ui.tasks;

import java.util.List;

import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.MvpView;

public interface TasksView extends MvpView {

    void displayTasks(List<Task> tasks);
    void displayTasksDoneFirst(List<Task> tasks);

    void showAddTaskView();

    void showNoTasksView();

    void showEditTaskView(Task task);

    void showTaskMarkedAsDoneMessage(String taskTitle);
    void saveTasksFromFirebase();

    void showTaskUnmarkedAsDoneMessage(String taskTitle);

    void showTaskDeletedMessage(String taskTitle);

    void addComments();
}
