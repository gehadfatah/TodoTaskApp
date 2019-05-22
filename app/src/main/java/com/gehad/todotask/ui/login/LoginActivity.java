package com.gehad.todotask.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.app.TodoApp;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.TasksMain.TaskActivity;
import com.gehad.todotask.ui.base.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.loginStatus)
    TextView loginStatus;
    public static String user_name = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveAllUser();
        userName.setOnEditorActionListener(editorListener);

    }
    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {

                case EditorInfo.IME_ACTION_DONE:
                    loginClick();
                    break;
            }
            return false;
        }
    };
    private void retrieveAllUser() {

    }

    @OnClick(R.id.login_btn)
    public void loginClick() {
        if (!userName.getText().toString().equals("")|| TextUtils.isEmpty(userName.getText())) {
            loginStatus.setVisibility(View.INVISIBLE);

            user_name = userName.getText().toString();
            Intent intent = new Intent(this, TaskActivity.class);
            intent.putExtra("userId", userName.getText().toString());

            startActivity(intent);
        } else {
            loginStatus.setText("Please insert username");
            loginStatus.setVisibility(View.VISIBLE);
        }
    }
}
