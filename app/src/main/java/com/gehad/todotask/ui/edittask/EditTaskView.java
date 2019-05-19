package com.gehad.todotask.ui.edittask;

import org.threeten.bp.LocalDate;

import java.util.List;

import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.MvpView;

interface EditTaskView extends MvpView {

    void setTaskDueDate(LocalDate localDate);

    void finish();

    void setupWithTaskToEdit(Task task);

    void setupViewToCreateNewTask();

    Task getTaskToSave();

    List<ChecklistItem> getChecklistItemsToDelete();

    List<ChecklistItem> getChecklistItemsToUpdate();

    List<ChecklistItem> getChecklistItemsToAdd();
}
