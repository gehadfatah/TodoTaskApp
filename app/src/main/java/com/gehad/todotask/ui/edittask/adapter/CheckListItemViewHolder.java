package com.gehad.todotask.ui.edittask.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import com.gehad.todotask.R;
import com.gehad.todotask.ui.edittask.adapter.model.ChecklistDataCollector;
import com.gehad.todotask.ui.base.BaseViewHolder;

class CheckListItemViewHolder extends BaseViewHolder<ChecklistDataCollector> {

    private final AddChecklistItemListener addChecklistItemListener;
    private final GetIsLastObserver getIsLastObserver;
    private final RemoveChecklistItemListener removeChecklistItemListener;

    @BindView(R.id.sub_task_name_edit_text) EditText subTaskNameEditText;

    @Nullable
    private ChecklistDataCollector currentItem;

    CheckListItemViewHolder(ViewGroup parent, AddChecklistItemListener addChecklistItemListener,
                            GetIsLastObserver getIsLastObserver, RemoveChecklistItemListener removeChecklistItemListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_list_item, parent, false));
        this.addChecklistItemListener = addChecklistItemListener;
        this.getIsLastObserver = getIsLastObserver;
        this.removeChecklistItemListener = removeChecklistItemListener;
    }

    @Override
    public void bind(ChecklistDataCollector item) {
        currentItem = item;
        subTaskNameEditText.setText(item.getName());
    }

    @OnTextChanged(R.id.sub_task_name_edit_text)
    void onTextChanged(CharSequence charSequence) {
        if (currentItem != null) {
            currentItem.setName(charSequence.toString());
        }
    }

    @OnClick(R.id.remove_button)
    void onRemoveClick() {
        removeChecklistItemListener.removeChecklistItemBasedOnLayoutPosition(getLayoutPosition());
    }

    @OnEditorAction(R.id.sub_task_name_edit_text)
    boolean onSubTaskNameEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT && getIsLastObserver.isLastChecklistItem(getLayoutPosition())) {
            addChecklistItemListener.addChecklistItem();
        }
        //return false on purpose so we get next item focused
        return false;
    }
}
