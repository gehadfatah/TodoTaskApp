package com.gehad.todotask.ui.newedittaskdiolog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseMvpDialog;
import com.gehad.todotask.ui.edittask.newEdittask.EditTaskNewFragment;
import com.gehad.todotask.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.gehad.todotask.ui.edittask.newEdittask.EditTaskNewFragment.TAG;

public class AddTaskDialog extends BaseMvpDialog<EditTaskDiologPresenter> implements EditTaskDialogView {
    Context context;
    public static final String TAG = AddTaskDialog.class.getSimpleName();

    @BindView(R.id.taskTitleEd)
    TextInputEditText taskTitleEd;
    @BindView(R.id.createBtn)
    Button createBtn;

    public AddTaskDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_newtask);
        ButterKnife.bind(this);
        this.setCancelable(true);
        if (this.getWindow() != null)
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        taskTitleEd.setOnEditorActionListener(editorListener);

    }

    @Override
    public EditTaskDiologPresenter createPresenter() {
        return TodoApp.getAppComponent(getContext()).addTaskDiaologComponent().getPresenter();
    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {

                case EditorInfo.IME_ACTION_DONE:
                    createBtnClick();
                    break;
            }
            return false;
        }
    };

    @OnClick(R.id.createBtn)
    public void createBtnClick() {

        if (!taskTitleEd.getText().toString().equals("")) {
            getPresenter().saveNewTask(new Task.Builder()
                    .setTitle(taskTitleEd.getText().toString())
                    .setUserId(LoginActivity.user_name)
                    .build());
          /*  TodoApp.newInstance().getTasksColliction().document(taskTitleEd.getText().toString()).set(new Task.Builder()
                    .setTitle(taskTitleEd.getText().toString())
                    .setUserId(LoginActivity.user_name)
                    .build()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Timber.d(" onSuccess createBtnClick");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Timber.d(" onFailure createBtnClick ", e);

                }
            });*/

        }


        dismiss();
    }


    @Override
    public void finish() {
        dismiss();

    }
}

