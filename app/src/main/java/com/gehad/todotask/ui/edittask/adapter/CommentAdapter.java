package com.gehad.todotask.ui.edittask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gehad.todotask.R;
import com.gehad.todotask.common.util.DateUtils;
import com.gehad.todotask.domain.model.CommentlistItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<CommentlistItem> commentTodosList;
    private Context context;

    public CommentAdapter(Context context, List<CommentlistItem> commentTodosList
    ) {
        super();
        this.commentTodosList =(ArrayList<CommentlistItem>) commentTodosList;
        this.context = context;


    }

    public void addItem(CommentlistItem commentlistItem) {
        commentTodosList.add(commentlistItem);
        notifyItemInserted(commentTodosList.size());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);


        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CommentlistItem commentTodo = commentTodosList.get(position);
        holder.commentTv.setText(commentTodo.getdescription());
        //holder.timeTv.setText(commentTodo.getDueDate().toString());

        if (commentTodo.getDueDate() != null) {
            //holder.timeTv.setText(LocalDateFormatterUtil.getShortMonthDayAndYearFormattedDate(commentTodo.getDueDate()));
            holder.timeTv.setText(DateUtils.dateTimeToshow(commentTodo.getDueDate()));
        }

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
