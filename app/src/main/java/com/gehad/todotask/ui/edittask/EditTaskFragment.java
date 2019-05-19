package com.gehad.todotask.ui.edittask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;

import org.threeten.bp.LocalDate;

import java.util.List;

import butterknife.BindView;
import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseMvpFragment;
import com.gehad.todotask.ui.edittask.adapter.CheckListAdapter;
import com.gehad.todotask.ui.edittask.adapter.DueDateRequestListener;

public class EditTaskFragment extends BaseMvpFragment<EditTaskPresenter>
        implements EditTaskView, DatePickerDialog.OnDateSetListener, DueDateRequestListener {

    public static final String TAG = EditTaskFragment.class.getSimpleName();

    private static final String BUNDLE_TASK_TO_EDIT = "BUNDLE_TASK_TO_EDIT";

    private CheckListAdapter checkListAdapter;

    @BindView(R.id.check_list_recycler) RecyclerView checkListRecyclerView;

    public static EditTaskFragment newAddTaskInstance() {
        return new EditTaskFragment();
    }

    public static EditTaskFragment newEditTaskInstance(Task taskToEdit) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_TASK_TO_EDIT, taskToEdit);
        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Task taskToEdit = null;
        if (getArguments() != null) {
            taskToEdit = getArguments().getParcelable(BUNDLE_TASK_TO_EDIT);
        }
        getPresenter().setupWithTask(taskToEdit);
        checkListRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_task;
    }

    @Override
    protected EditTaskPresenter createPresenter() {
        return TodoApp.getAppComponent(getContext()).addTaskFragmentComponent().getPresenter();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthIndexedFromZero, int dayOfMonth) {
        getPresenter().handleDateSetByUser(year, monthIndexedFromZero, dayOfMonth);
    }

    public void onAcceptClick() {
        getPresenter().saveTask();
    }

    @Override
    public void setupWithTaskToEdit(Task task) {
        checkListAdapter = new CheckListAdapter(this, task);
        checkListRecyclerView.setAdapter(checkListAdapter);
    }

    @Override
    public void setupViewToCreateNewTask() {
        checkListAdapter = new CheckListAdapter(this);
        checkListRecyclerView.setAdapter(checkListAdapter);
    }

    @Override
    public Task getTaskToSave() {
        return checkListAdapter.getTask();
    }

    @Override
    public List<ChecklistItem> getChecklistItemsToDelete() {
        return checkListAdapter.getRemovedChecklistItemsWithIds();
    }

    @Override
    public List<ChecklistItem> getChecklistItemsToUpdate() {
        return checkListAdapter.getUpdatedCheckListDataItems();
    }

    @Override
    public List<ChecklistItem> getChecklistItemsToAdd() {
        return checkListAdapter.getNewChecklistItems();
    }

    @Override
    public void setTaskDueDate(LocalDate localDate) {
        checkListAdapter.setTaskDate(localDate);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onTaskRequestDate() {
        DatePickerDialogFragment.newInstance(this).show(getFragmentManager(), DatePickerDialogFragment.TAG);
    }
}
