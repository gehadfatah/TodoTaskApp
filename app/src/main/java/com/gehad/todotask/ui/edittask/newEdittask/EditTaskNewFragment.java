package com.gehad.todotask.ui.edittask.newEdittask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.common.KeyboardUtils;
import com.gehad.todotask.common.LocalDateFormatterUtil;
import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.CommentlistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseMvpFragment;
import com.gehad.todotask.ui.edittask.DatePickerDialogFragment;
import com.gehad.todotask.ui.edittask.adapter.model.ChecklistDataCollector;
import com.gehad.todotask.ui.edittask.adapter.model.CommentlistDataCollector;
import com.gehad.todotask.ui.login.LoginActivity;
import com.gehad.todotask.ui.tasks.adapter.TasksAdapter;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditTaskNewFragment extends BaseMvpFragment<EditTaskPresenterNew>
        implements EditTaskViewNew, DatePickerDialog.OnDateSetListener {
    public static final String TAG = EditTaskNewFragment.class.getSimpleName();
    private static final String BUNDLE_TASK_TO_EDIT = "BUNDLE_TASK_TO_EDIT";
    @BindView(R.id.dateTv)
    TextView dueDateTextView;
    @BindView(R.id.cbItemCheck)
    AppCompatCheckBox doneCheckbox;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.comment_list_recycler)
    RecyclerView comment_list_recycler;
    @BindView(R.id.send_button)
    ImageButton send_button;
    @BindView(R.id.edit_text_message)
    EditText edit_text_message;
    private static final String[] Priority = {"Law", "Medium", "High"};
    Task taskToEdit;
    ArrayList<CommentlistItem> commentlistItems = new ArrayList<>();
    ArrayList<CommentlistItem> newCommentlistItems = new ArrayList<>();
    ArrayList<CommentlistDataCollector> commentlistDataCollectors = new ArrayList<>();
    ArrayList<CommentlistDataCollector> newCommentlistDataCollectors = new ArrayList<>();
    CommentAdapter commentAdapter;

    public static EditTaskNewFragment newAddTaskInstance() {
        return new EditTaskNewFragment();
    }

    public static EditTaskNewFragment newEditTaskInstance(Task taskToEdit) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_TASK_TO_EDIT, taskToEdit);
        EditTaskNewFragment fragment = new EditTaskNewFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskToEdit = null;
        if (getArguments() != null) {
            taskToEdit = getArguments().getParcelable(BUNDLE_TASK_TO_EDIT);
        }
        setupWithTaskToEdit(taskToEdit);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_add_task2;
    }

    @Override
    protected EditTaskPresenterNew createPresenter() {
        return TodoApp.getAppComponent(getContext()).addTaskNewFragmentComponent().getPresenter();
    }

    public void setcomment_list_recycler() {

        comment_list_recycler.setHasFixedSize(true);
        for (CommentlistItem commentlistItem :
                taskToEdit.getCommentlistItemList()) {
            CommentlistDataCollector commentlistDataCollector = new CommentlistDataCollector(commentlistItem.getId(), commentlistItem.getdescription(), commentlistItem.getDueDate());
            commentlistDataCollectors.add(commentlistDataCollector);
            commentlistItems.add(commentlistItem);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        comment_list_recycler.setLayoutManager(linearLayoutManager);

        commentAdapter = new CommentAdapter(getActivity(), commentlistItems);
        comment_list_recycler.setAdapter(commentAdapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthIndexedFromZero, int dayOfMonth) {
        getPresenter().handleDateSetByUser(year, monthIndexedFromZero, dayOfMonth);
    }

    @OnClick(R.id.dateTv)
    public void dateTvClick() {
        onTaskRequestDate();
    }

    @OnClick(R.id.send_button)
    public void send_button_Click() {
        //  commentlistItems.add(new CommentlistItem(null,edit_text_message.getText().toString(),LocalDate.now()));
        CommentlistDataCollector commentlistDataCollector = new CommentlistDataCollector();
        commentlistDataCollector.setDescription(edit_text_message.getText().toString());
        commentlistDataCollector.setDueDate(LocalDate.now());
        newCommentlistItems.add(new CommentlistItem.Builder()
                .setDescription(commentlistDataCollector.getDescription())
                .setDueDate(commentlistDataCollector.getDueDate())
                .build());
        newCommentlistDataCollectors.add(commentlistDataCollector);
        commentlistDataCollectors.add(commentlistDataCollector);
        commentlistItems.add(new CommentlistItem.Builder()
                .setDescription(commentlistDataCollector.getDescription())
                .setDueDate(commentlistDataCollector.getDueDate())
                .build());
        if (getActivity() != null)
            KeyboardUtils.hideSoftInput(getActivity());
        edit_text_message.setText("");
        commentAdapter.notifyItemInserted(commentAdapter.getItemCount() - 1);

        comment_list_recycler.smoothScrollToPosition(commentAdapter.getItemCount() - 1);


    }

    public void setupWithTaskToEdit(Task task) {
        //checkListAdapter = new CheckListAdapter(this, task);
        // checkListRecyclerView.setAdapter(checkListAdapter);
        doneCheckbox.setChecked(task.isDone());
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Priority));
        spinner.setSelection(task.getPriority());
        if (task.getDueDate() != null) {
            //dueDateTextView.setText(task.getDueDate().toString());
            dueDateTextView.setText(LocalDateFormatterUtil.getShortMonthDayAndYearFormattedDate(task.getDueDate()));

        }
        setcomment_list_recycler();

    }


    @Override
    public void setTaskDueDate(LocalDate localDate) {
        dueDateTextView.setText(localDate.toString());

    }

    public void TaskDelete(Task task) {
        getPresenter().deleteTask(task);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void showTaskDeletedMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_deleted, taskTitle), Snackbar.LENGTH_LONG);

    }

    public void onTaskRequestDate() {
        DatePickerDialogFragment.newInstance(this).show(getFragmentManager(), DatePickerDialogFragment.TAG);
    }

    public void saveTaskChange() {
       /* getPresenter().updateTask(new Task.Builder()
                .setId(taskToEdit.getId())
                .setTitle(taskToEdit.getTitle())
                .setIsDone(doneCheckbox.isChecked())
                .setPriority(spinner.getSelectedItemPosition())
                .setCommentList(taskToEdit.getCommentlistItemList())
                .setDueDate(dueDateTextView.getText().toString().contains("Date") ? null : LocalDate.parse(dueDateTextView.getText().toString()))
                .setUserId(LoginActivity.user_name)
                .build());*/
//here i send just newcomment to save it in comment table
        getPresenter().updateTaskwithcomments(new Task.Builder()
                .setId(taskToEdit.getId())
                .setTitle(taskToEdit.getTitle())
                .setIsDone(doneCheckbox.isChecked())
                .setPriority(spinner.getSelectedItemPosition())
                .setCommentList(/*getlistComment()*/getCommentListItemsFromCollectors(newCommentlistDataCollectors))
                .setDueDate(dueDateTextView.getText().toString().contains("Date") ? null : LocalDate.parse(dueDateTextView.getText().toString()))
                .setUserId(LoginActivity.user_name)
                .build());
    }

    private List<CommentlistItem> getlistComment() {
        ArrayList<CommentlistItem> commentlistItems = (ArrayList<CommentlistItem>) taskToEdit.getCommentlistItemList();

        return commentlistItems;
    }

    public List<CommentlistItem> getCommentListItemsFromCollectors(List<CommentlistDataCollector> commentlistDataCollectors) {
        List<CommentlistItem> commentlistItems = new ArrayList<>(commentlistDataCollectors.size());
        for (CommentlistDataCollector commentlistDataCollector : commentlistDataCollectors) {
            commentlistItems.add(
                    new CommentlistItem.Builder()
                            .setId(commentlistDataCollector.getId())
                            .setDescription(commentlistDataCollector.getDescription())
                            .setDueDate(commentlistDataCollector.getDueDate())
                            .build());
        }
        return commentlistItems;
    }
}
