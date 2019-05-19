package com.gehad.todotask.ui.edittask;

import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.MvpView;

import org.threeten.bp.LocalDate;

import java.util.List;

interface EditTaskViewNew extends MvpView {

    void setTaskDueDate(LocalDate localDate);

    void finish();

    void setupWithTaskToEdit(Task task);


    Task getTaskToSave();

}
