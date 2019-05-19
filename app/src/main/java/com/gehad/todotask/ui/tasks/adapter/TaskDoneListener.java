package com.gehad.todotask.ui.tasks.adapter;

import com.gehad.todotask.domain.model.Task;

public interface TaskDoneListener {

    void onTaskDoneStateChange(Task task);
}
