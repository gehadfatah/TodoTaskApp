package com.gehad.todotask.ui.tasks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gehad.todotask.domain.model.Task;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private final List<Task> tasks = new ArrayList<>();
    private final TaskEditListener taskEditListener;
    private final TaskDeleteListener taskDeleteListener;
    private final TaskDoneListener taskDoneListener;

    public TasksAdapter(TaskEditListener taskEditListener, TaskDeleteListener taskDeleteListener, TaskDoneListener taskDoneListener) {
        this.taskEditListener = taskEditListener;
        this.taskDeleteListener = taskDeleteListener;
        this.taskDoneListener = taskDoneListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(parent, taskEditListener, taskDeleteListener, taskDoneListener);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void showTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void showTasksDonefirst(List<Task> tasks) {
        this.tasks.clear();
        ArrayList<Task> tasks1 = new ArrayList<>();
        ArrayList<Task> tasksNotDone = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDone())
                tasks1.add(task);
            else tasksNotDone.add(task);
        }
        this.tasks.addAll(tasks1);
        this.tasks.addAll(tasksNotDone);
        notifyDataSetChanged();
    }
}
