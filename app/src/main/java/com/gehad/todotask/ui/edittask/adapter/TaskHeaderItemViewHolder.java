package com.gehad.todotask.ui.edittask.adapter;

import android.renderscript.RenderScript;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import com.gehad.todotask.R;
import com.gehad.todotask.common.LocalDateFormatterUtil;
import com.gehad.todotask.ui.edittask.adapter.model.TaskDataCollector;
import com.gehad.todotask.ui.base.BaseViewHolder;
import com.gehad.todotask.ui.login.LoginActivity;

public class TaskHeaderItemViewHolder extends BaseViewHolder<TaskDataCollector> {

/*    @BindView(R.id.title_edit_text)
    TextInputEditText titleEditText;*/
  /*  @BindView(R.id.description_edit_text)
    TextInputEditText descriptionEditText;*/
    @BindView(R.id.dateTv)
    EditText dueDateTextView;
    @BindView(R.id.cbItemCheck)
    CheckBox doneCheckbox;
    @BindView(R.id.tvItemPriority)
    TextView tvItemPriority;
    @BindView(R.id.spinner)
     Spinner spinner;
    private static final String[] Priority = {"law", "medium","high"};


    private final DueDateRequestListener dueDateRequestListener;

    @Nullable
    private TaskDataCollector currentTask;

    TaskHeaderItemViewHolder(ViewGroup parent, DueDateRequestListener dueDateRequestListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_add_task2, parent, false));
        this.dueDateRequestListener = dueDateRequestListener;

        disableDueDateEditTextEditAbility();
    }

    private void disableDueDateEditTextEditAbility() {
        dueDateTextView.setKeyListener(null);
    }

    @Override
    public void bind(TaskDataCollector item) {
        currentTask = item;
        //titleEditText.setText(item.getTitle());
        //descriptionEditText.setText(item.getDescription());
       // spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Priority));


        final LocalDate dueDate = item.getDueDate();
        if (dueDate != null) {
            String formattedDate = LocalDateFormatterUtil.getShortMonthDayAndYearFormattedDate(item.getDueDate());
            dueDateTextView.setText(formattedDate);
        }
    }

    /*@OnTextChanged(R.id.title_edit_text)
    void onTitleTextChange(CharSequence charSequence) {
        if (currentTask != null) {
            currentTask.setTitle(charSequence.toString());
            currentTask.setuserId(LoginActivity.user_name);
        }
    }*/

    /*@OnTextChanged(R.id.description_edit_text)
    void onDescriptionChange(CharSequence charSequence) {
        if (currentTask != null) {
            currentTask.setDescription(charSequence.toString());
        }
    }*/

    @OnClick(R.id.due_date_edit_text)
    void onDueDateClick() {
        dueDateRequestListener.onTaskRequestDate();
    }
}
