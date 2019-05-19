package com.gehad.todotask.data.local;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gehad.todotask.data.local.model.ChecklistItemDb;
import com.gehad.todotask.data.local.model.TaskDb;
import com.gehad.todotask.domain.mapper.ChecklistItemMapper;
import com.gehad.todotask.domain.mapper.TaskMapper;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import timber.log.Timber;

@Singleton
public class TasksRepository {

    private final AppDatabase appDatabase;

    @Inject
    public TasksRepository(AppDatabase appDatabase) {

        this.appDatabase = appDatabase;
    }

    public Completable saveNewTask(Task task) {
        return Completable.fromAction(() -> {
                    TaskDb taskDb = TaskMapper.toTaskDb(task);
                    appDatabase.beginTransaction();
                    try {
                        long taskId = appDatabase.tasksDao().insertTask(taskDb);
                        //List<ChecklistItemDb> checklistItemDbs = ChecklistItemMapper.toChecklistItemDbList(taskId, task.getChecklistItemList());
                       // appDatabase.checklistDao().insertAll(checklistItemDbs);
                        appDatabase.setTransactionSuccessful();
                    } finally {
                        appDatabase.endTransaction();
                    }
                }
        );
    }

    public Completable updateTask(Task task, List<ChecklistItem> checklistItemsToDelete,
                                  List<ChecklistItem> checklistItemsToUpdate, List<ChecklistItem> checklistItemsToAdd) {
        return Completable.fromAction(() -> {
                    TaskDb taskDb = TaskMapper.toTaskDb(task);
                    appDatabase.beginTransaction();
                    try {
                        appDatabase.tasksDao().updateTask(taskDb);

                        List<ChecklistItemDb> checklistItemDbsToDelete =
                                ChecklistItemMapper.toChecklistItemDbList(task.getId(), checklistItemsToDelete);
                        appDatabase.checklistDao().deleteAll(checklistItemDbsToDelete);

                        List<ChecklistItemDb> checklistItemDbsToUpdate =
                                ChecklistItemMapper.toChecklistItemDbList(task.getId(), checklistItemsToUpdate);
                        appDatabase.checklistDao().updateAll(checklistItemDbsToUpdate);

                        List<ChecklistItemDb> checklistItemDbsToAdd =
                                ChecklistItemMapper.toChecklistItemDbList(task.getId(), checklistItemsToAdd);
                        appDatabase.checklistDao().insertAll(checklistItemDbsToAdd);

                        appDatabase.setTransactionSuccessful();
                    } finally {
                        appDatabase.endTransaction();
                    }
                }
        );
    }

    public Completable updateTaskOnly(Task task) {
        return Completable.fromAction(() -> {
            TaskDb taskDb = TaskMapper.toTaskDb(task);
            long updatedRow = appDatabase.tasksDao().updateTask(taskDb);
            Timber.d("Updated row " + updatedRow);
        });
    }

    public Completable deleteTask(Task task) {
        return Completable.fromAction(() ->
                appDatabase.tasksDao().deleteTask(TaskMapper.toTaskDb(task))
        );
    }

    /**
     * Flowable will emit new list of tasks whenever tasks table is modified
     */
    public Flowable<List<Task>> getToDoTasks() {
        return appDatabase.tasksDao().getToDoTasks()
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }

    public Flowable<List<Task>> getDoneTasks() {
        return appDatabase.tasksDao().getDoneTasks()
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }
    public Single<List<Task>> getToDoTasksSingle() {
        return appDatabase.tasksDao().getToDoTasksSingle()
                .flatMap(this::getTasksWithChecklistFromTaskDbs);
    }

    public Single<List<Task>> getDoneTasksSingle() {
        return appDatabase.tasksDao().getDoneTasksSingle()
                .flatMap(this::getTasksWithChecklistFromTaskDbs);
    }
    public Flowable<List<Task>> getAllTasks(String  userid) {
        return appDatabase.tasksDao().getAllTasks(userid)
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }

    public Flowable<List<Task>> getTasksWithDueDateBefore(LocalDate date) {
        return appDatabase.tasksDao().getTaskWithDueDateBefore(date)
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }

    private Single<List<ChecklistItemDb>> getSingleCheckListItems(TaskDb taskDb) {
        return appDatabase.checklistDao().getChecklistItems(taskDb.getId()).firstOrError();
    }

    private Single<List<Task>> getTasksWithChecklistFromTaskDbs(List<TaskDb> taskDbs) {
        return Flowable.fromIterable(taskDbs)
                .flatMapSingle(new Function<TaskDb, SingleSource<? extends Task>>() {
                    @Override
                    public SingleSource<? extends Task> apply(TaskDb taskDb) throws Exception {
                        return TasksRepository.this.getSingleCheckListItems(taskDb)
                                .map(checklistItemDbs -> TaskMapper.fromTaskDbAndChecklistDbList(taskDb, checklistItemDbs));
                    }
                }, false, 1)
                .toList();
    }
}