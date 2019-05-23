package com.gehad.todotask.common.Services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;


import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.gehad.todotask.common.util.CommonUtils.generateRandomIntIntRange;


public class UploadFirebaseTasksService extends IntentService {
    public static final String TAG = UploadFirebaseTasksService.class.getSimpleName();
    private ArrayList<Task> tasks;
    private ArrayList<Task> failedUploadtasks;
    private String userId;

    public UploadFirebaseTasksService() {
        super("");

    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadFirebaseTasksService(String name) {
        super(name);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForeground(41245678, getNotification());
        }
        super.onCreate();


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            tasks = intent.getParcelableArrayListExtra("tasks");
            userId = intent.getParcelableExtra("userId");

            uploadTasks(tasks, userId);
        }
    }

    private void uploadTasks(List<Task> tasks, String UserId) {
        for (Task task : tasks) {

            TodoApp.newInstance().getTasksColliction().document(userId + String.valueOf(System.currentTimeMillis())).set(new Task.Builder()
                    .setId(task.getId())
                    .setTitle(task.getTitle())
                    .setDateTime(task.getDateTime())
                    .setIsDone(task.isDone())
                    .setPriority(task.getPriority())
                    .setCommentList(task.getCommentlistItemList())
                    .setUserId(LoginActivity.user_name)
                    .build()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Timber.d(" onFailure uploadTasksToFirebase", e + " " + task.toString());
                    failedUploadtasks.add(task);
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Timber.d(" onSuccess uploadTasksToFirebase", task.toString());

                }
            });
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification getNotification() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return null;
        }

        NotificationChannel channel = new NotificationChannel("todo_channel_01", "Todo Channel", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(this, "todo_channel_01").setAutoCancel(true);

        return builder.build();
    }
}
