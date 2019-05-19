package com.gehad.todotask.ui.edittask.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.OnClick;
import com.gehad.todotask.R;
import com.gehad.todotask.ui.base.BaseViewHolder;

class AddChecklistItemViewHolder extends BaseViewHolder {

    private final AddChecklistItemListener addChecklistItemListener;

    AddChecklistItemViewHolder(ViewGroup parent, AddChecklistItemListener addChecklistItemListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_task_button, parent, false));

        this.addChecklistItemListener = addChecklistItemListener;
    }

    @Override
    public void bind(Object item) {
        //no-op
    }

    @OnClick(R.id.add_list_item_button)
    void onAddListItemButtonClick() {
        addChecklistItemListener.addChecklistItem();
    }
}
