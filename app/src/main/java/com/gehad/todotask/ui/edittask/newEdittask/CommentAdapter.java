package com.gehad.todotask.ui.edittask.newEdittask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.domain.model.CommentTodo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<CommentTodo> commentTodosList;
    private Context context;

    public CommentAdapter(Context context, ArrayList<CommentTodo> commentTodosList
    ) {
        super();
        this.commentTodosList = commentTodosList;
        this.context = context;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);


        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CommentTodo commentTodo = commentTodosList.get(position);
        holder.commentTv.setText(commentTodo.getDescription());
        holder.timeTv.setText(commentTodo.getDate());

    }

    @Override
    public int getItemCount() {
        if (commentTodosList == null)
            return 0;
        return commentTodosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentTv)
        TextView commentTv;
        @BindView(R.id.timeTv)
        TextView timeTv;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;

        }
    }
}