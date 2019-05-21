package com.gehad.todotask.data.local;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.gehad.todotask.data.local.model.CommentlistItemDb;
import com.gehad.todotask.data.local.model.TaskDb;
import com.gehad.todotask.domain.mapper.CommentlistItemMapper;
import com.gehad.todotask.domain.mapper.TaskMapper;
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
                        appDatabase.setTransactionSuccessful();
                    } finally {
                        appDatabase.endTransaction();
                    }
                }
        );
    }

    public Completable updateTaskWithComments(Task task) {
        return Completable.fromAction(() -> {
                    TaskDb taskDb = TaskMapper.toTaskDb(task);
                    appDatabase.beginTransaction();
                    try {
                        appDatabase.tasksDao().updateTask(taskDb);

                        List<CommentlistItemDb> toCommentItemDbsToAdd =
                                CommentlistItemMapper.toCommentlistItemDbList(task.getId(), task.getCommentlistItemList());
                        appDatabase.commentlistDao().insertAll(toCommentItemDbsToAdd);

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
                .flatMapSingle(this::getTasksWithCommentlistFromTaskDbs);
    }

    public Flowable<List<Task>> getDoneTasks() {
        return appDatabase.tasksDao().getDoneTasks()
                .flatMapSingle(this::getTasksWithCommentlistFromTaskDbs);
    }

    public Flowable<List<Task>> getAllTasks(String  userid) {
        return appDatabase.tasksDao().getAllTasks(userid)
                .flatMapSingle(this::getTasksWithCommentlistFromTaskDbs);
    }
    public Flowable<List<Task>> getAllTasks(  ) {
        return appDatabase.tasksDao().getAllTasks()
                .flatMapSingle(this::getTasksWithCommentlistFromTaskDbs);
    }
    public Flowable<List<Task>> getAllTasksWithComments(String  userid) {
        return appDatabase.tasksDao().getAllTasks(userid)
                .flatMapSingle(this::getTasksWithCommentlistFromTaskDbs);
    }
    public Flowable<List<Task>> getTasksWithDueDateBefore(LocalDate date) {
        return appDatabase.tasksDao().getTaskWithDueDateBefore(date)
                .flatMapSingle(this::getTasksWithCommentlistFromTaskDbs);
    }


    private Single<List<CommentlistItemDb>> getSingleCommentListItems(TaskDb taskDb) {
        return appDatabase.commentlistDao().getCommentlistItems(taskDb.getId()).firstOrError();
    }

    private Single<List<Task>> getTasksWithCommentlistFromTaskDbs(List<TaskDb> taskDbs) {
        return Flowable.fromIterable(taskDbs)
                .flatMapSingle(new Function<TaskDb, SingleSource<? extends Task>>() {
                    @Override
                    public SingleSource<? extends Task> apply(TaskDb taskDb) throws Exception {
                        return TasksRepository.this.getSingleCommentListItems(taskDb)
                                .map(commentlistItemDbs -> TaskMapper.fromTaskDbAndCommentlistDbList(taskDb, commentlistItemDbs));
                    }
                }, false, 1)
                .toList();
    }
}
