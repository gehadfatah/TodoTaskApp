package com.gehad.todotask.ui.tasks.adapter;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;
import timber.log.Timber;

import com.gehad.todotask.R;
import com.gehad.todotask.common.LocalDateFormatterUtil;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseViewHolder;

public class TaskViewHolder extends BaseViewHolder<Task> implements Toolbar.OnMenuItemClickListener {

    private final TaskEditListener taskEditListener;
    private final TaskSpinnerListener taskSpinnerListener;
    private final TaskDeleteListener taskDeleteListener;
    private final TaskDoneListener taskDoneListener;
    @BindView(R.id.dummy_id)
    LinearLayout dummy;
    @BindView(R.id.tvItem)
    TextView titleTextView;
    @BindView(R.id.task_view_Rel)
    RelativeLayout task_view_Rel;
    /*  @BindView(R.id.tvItemPriority)
      TextView tvItemPriority;*/
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.tvDueDate)
    TextView dueDateTextView;
    @BindView(R.id.cbItemCheck)
    CheckBox doneCheckbox;
    @BindView(R.id.delete_layout)
    FrameLayout deleteLayout;
    Context context;
    private Task currentTask;
    private static final String[] Priority = {"Law", "Medium", "High"};

    public TaskViewHolder(ViewGroup parent, TaskEditListener taskEditListener,
                          TaskDeleteListener taskDeleteListener, TaskDoneListener taskDoneListener, TaskSpinnerListener taskSpinnerListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task2, parent, false));
        this.taskEditListener = taskEditListener;
        this.taskDeleteListener = taskDeleteListener;
        this.taskDoneListener = taskDoneListener;
        this.taskSpinnerListener = taskSpinnerListener;
        context = parent.getContext();
   /*     toolbar.inflateMenu(R.menu.task_card_menu);
        toolbar.setOnMenuItemClickListener(this);*/
    }

    @Override
    public void bind(Task item) {
        this.currentTask = item;
        //this for spinner call setOnItemSelectedListener when it intailized or user select item
        final boolean[] taskspinnerSelect = {false};
        spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, Priority));
        spinner.setSelection(item.getPriority());
        dummy.requestFocus();
        doneCheckbox.setChecked(item.isDone());
        titleTextView.setText(item.getTitle());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (taskspinnerSelect[0]) {
                    taskSpinnerListener.onTaskSpinnerPriority(
                            new Task.Builder()
                                    .setId(currentTask.getId())
                                    .setTitle(currentTask.getTitle())
                                    .setUserId(currentTask.getUserId())
                                    .setDescription(currentTask.getDescription())
                                    .setDueDate(currentTask.getDueDate())
                                    .setPriority(position)
                                    .setIsDone(currentTask.isDone())
                                    .setChecklistItemList(currentTask.getChecklistItemList())
                                    .setCommentList(currentTask.getCommentlistItemList())
                                    .build());
                    Timber.d("", "");
                }
                taskspinnerSelect[0] = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Timber.d("", "");

            }
        });

       /* switch (item.getPriority()) {
            case 0:
                tvItemPriority.setText(Priority[0]);

                break;
            case 1:
                tvItemPriority.setText(Priority[1]);

                break;
            case 2:
                tvItemPriority.setText(Priority[2]);

                break;
        }*/
        task_view_Rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskEditListener.onTaskEdit(currentTask);

            }
        });
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDeleteListener.onTaskDelete(currentTask);


            }
        });
        if (item.getDueDate() != null)
            dueDateTextView.setText(LocalDateFormatterUtil.getShortMonthDayAndYearFormattedDate(item.getDueDate()));

    }

    private String getFormattedChecklistItems(List<ChecklistItem> checklistItems) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ChecklistItem checklistItem : checklistItems) {
            stringBuilder.append("- ").append(checklistItem.getName()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                taskEditListener.onTaskEdit(currentTask);
                return true;
            case R.id.action_delete:
                taskDeleteListener.onTaskDelete(currentTask);
                return true;
            default:
                throw new IllegalArgumentException("Unknown item menu item");
        }
    }

    @OnCheckedChanged(R.id.cbItemCheck)
    void onTaskDoneStateChange(boolean isDone) {
        if (currentTask.isDone() != isDone) {
            taskDoneListener.onTaskDoneStateChange(
                    new Task.Builder()
                            .setId(currentTask.getId())
                            .setTitle(currentTask.getTitle())
                            .setUserId(currentTask.getUserId())
                            .setDescription(currentTask.getDescription())
                            .setDueDate(currentTask.getDueDate())
                            .setPriority(currentTask.getPriority())
                            .setIsDone(isDone)
                            .setChecklistItemList(currentTask.getChecklistItemList())
                            .setCommentList(currentTask.getCommentlistItemList())
                            .build());
        }
    }

  /*  @OnItemSelected(R.id.spinner)
    void onTaskSpinnerPriority(int postion) {
       *//* taskSpinnerListener.onTaskSpinnerPriority(
                new Task.Builder()
                        .setId(currentTask.getId())
                        .setTitle(currentTask.getTitle())
                        .setUserId(currentTask.getUserId())
                        .setDescription(currentTask.getDescription())
                        .setDueDate(currentTask.getDueDate())
                        .setPriority(postion)
                        .setIsDone(currentTask.isDone())
                        .setChecklistItemList(currentTask.getChecklistItemList())
                        .setCommentList(currentTask.getCommentlistItemList())
                        .build());*//*
    }*/
}
