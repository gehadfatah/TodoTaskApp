package com.gehad.todotask.ui.edittask.newEdittask;

import com.gehad.todotask.ui.base.MvpView;

import org.threeten.bp.LocalDate;

interface EditTaskViewNew extends MvpView {

    void setTaskDueDate(LocalDate localDate);

    void finish();
    void showTaskDeletedMessage(String taskTitle);


}
