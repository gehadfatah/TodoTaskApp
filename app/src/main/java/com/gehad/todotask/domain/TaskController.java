package com.gehad.todotask.domain;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import com.gehad.todotask.data.local.TasksRepository;
import com.gehad.todotask.domain.model.Task;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TaskController {

    private final TasksRepository tasksRepository;

    @Inject
    public TaskController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public Completable saveNewTask(Task task) {
        return tasksRepository.saveNewTask(task);
    }


    public Completable updateTaskWithCommentlist(Task task    ) {
        return tasksRepository.updateTaskWithComments(task);
    }

    public Completable updateTaskOnly(Task task) {
        return tasksRepository.updateTaskOnly(task);
    }

    public Flowable<List<Task>> getToDoTasks() {
        return tasksRepository.getToDoTasks();
    }

    public Flowable<List<Task>> getDoneTasks() {
        return tasksRepository.getDoneTasks();
    }
    public Flowable<List<Task>> getAllTasks(String userId) {
        return tasksRepository.getAllTasks(userId);
    }
    public Flowable<List<Task>> getAllTasks( ) {
        return tasksRepository.getAllTasks();
    }
    public Flowable<List<Task>> getAllTasksWithComments(String userId) {
        return tasksRepository.getAllTasksWithComments(userId);
    }

    public Flowable<List<Task>> getTasksWithDueDateBefore(LocalDate localDate) {
        return tasksRepository.getTasksWithDueDateBefore(localDate);
    }

    public Completable deleteTask(Task task) {
        return tasksRepository.deleteTask(task);
    }
}
