package com.gehad.todotask.ui.tasks;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.ui.base.BaseActivity;
import com.gehad.todotask.ui.login.LoginActivity;
import com.gehad.todotask.ui.tasks.TasksFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskActivity extends BaseActivity {


    private TasksFragment fragment;
    String userId = "";
    boolean doneFilter = false;

    @BindView(R.id.filterDoneImg)
    ImageView filterDoneImg;
    @BindView(R.id.userName)
    TextView userName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        userName.setText(String.format("%s's Tasks", userId == null ? "" : userId));
        setFragment();

    }

    private void setFragment() {

        fragment = TasksFragment.newInstance(userId);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    @OnClick(R.id.backImage)
    public void backImageClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.filterDoneImg)
    public void filterDoneImgClick() {
        if (doneFilter) {
            filterDoneImg.setImageResource(R.drawable.ic_filter_list_black_24dp);

            fragment.onFilterChangeChange(false);
            doneFilter = false;

        } else {
            filterDoneImg.setImageResource(R.drawable.ic_filter_list_done);

            fragment.onFilterChangeChange(true);
            doneFilter = true;
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tasks;
    }
}
