package com.gehad.todotask.ui.edittask.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gehad.todotask.domain.model.ChecklistItem;
import com.gehad.todotask.domain.model.Task;
import com.gehad.todotask.ui.base.BaseViewHolder;
import com.gehad.todotask.ui.edittask.adapter.model.ChecklistDataCollector;
import com.gehad.todotask.ui.edittask.adapter.model.TaskDataCollector;

public class CheckListAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements AddChecklistItemListener, GetIsLastObserver, RemoveChecklistItemListener {

    private static final int HEADER_VIEW_TYPE = 1;
    private static final int CHECKLIST_ITEM_VIEW_TYPE = 2;
    private static final int ADD_CHECKLIST_ITEM_BUTTON_VIEW_HOLDER = 3;

    private static final int HEADER_ITEMS_COUNT = 1;
    private static final int FOOTER_ITEMS_COUNT = 1;

    private static final int HEADER_POSITION = 0;

    private final List<ChecklistDataCollector> checklistDataCollectors = new ArrayList<>();
    private final List<ChecklistDataCollector> removedChecklistDataCollectorsWithId = new ArrayList<>();
    private final List<ChecklistDataCollector> newChecklistDataCollectors = new ArrayList<>();
    private final TaskDataCollector taskDataCollector;
    private final DueDateRequestListener dueDateRequestListener;

    private final Set<RecyclerView> attachedRecyclers = new HashSet<>();

    public CheckListAdapter(DueDateRequestListener dueDateRequestListener) {
        this.dueDateRequestListener = dueDateRequestListener;
        taskDataCollector = new TaskDataCollector();
    }

    public CheckListAdapter(DueDateRequestListener dueDateRequestListener, Task task) {
        this.dueDateRequestListener = dueDateRequestListener;
        taskDataCollector = new TaskDataCollector(task.getId(), task.getTitle(),task.getUserId(), task.getDescription(), task.isDone(), task.getDueDate());
        for (ChecklistItem checklistItem : task.getChecklistItemList()) {
            checklistDataCollectors.add(new ChecklistDataCollector(checklistItem.getId(), checklistItem.getName()));
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                return new TaskHeaderItemViewHolder(parent, dueDateRequestListener);
            case CHECKLIST_ITEM_VIEW_TYPE:
                return new CheckListItemViewHolder(parent, this, this, this);
            case ADD_CHECKLIST_ITEM_BUTTON_VIEW_HOLDER:
                return new AddChecklistItemViewHolder(parent, this);
            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                holder.bind(taskDataCollector);
                break;
            case CHECKLIST_ITEM_VIEW_TYPE:
                final int checklistListItemPosition = position - HEADER_ITEMS_COUNT;
                holder.bind(checklistDataCollectors.get(checklistListItemPosition));
                break;
            case ADD_CHECKLIST_ITEM_BUTTON_VIEW_HOLDER:
                break;
            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public int getItemCount() {
        return checklistDataCollectors.size() + HEADER_ITEMS_COUNT + FOOTER_ITEMS_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_POSITION) {
            return HEADER_VIEW_TYPE;
        } else if (position < checklistDataCollectors.size() + HEADER_ITEMS_COUNT) {
            return CHECKLIST_ITEM_VIEW_TYPE;
        } else {
            return ADD_CHECKLIST_ITEM_BUTTON_VIEW_HOLDER;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        attachedRecyclers.add(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        attachedRecyclers.remove(recyclerView);
    }

    @Override
    public void removeChecklistItemBasedOnLayoutPosition(int layoutPosition) {
        final int checklistItemPosition = layoutPosition - HEADER_ITEMS_COUNT;
        if (checklistDataCollectors.size() > checklistItemPosition) {
            ChecklistDataCollector removedChecklistDataCollector =
                    checklistDataCollectors.remove(checklistItemPosition);
            if (removedChecklistDataCollector.isIdSet()) {
                removedChecklistDataCollectorsWithId.add(removedChecklistDataCollector);
            }
            notifyItemRemoved(layoutPosition);
        }
    }

    @Override
    public void addChecklistItem() {
        int sizeBeforeInsertion = checklistDataCollectors.size();
        ChecklistDataCollector checklistDataCollector = new ChecklistDataCollector();
        checklistDataCollectors.add(checklistDataCollector);
        newChecklistDataCollectors.add(checklistDataCollector);
        notifyItemInserted(sizeBeforeInsertion + HEADER_ITEMS_COUNT);
        scrollBottomIfPossible();
    }

    @Override
    public boolean isLastChecklistItem(int layoutPosition) {
        return layoutPosition == getItemCount() - HEADER_ITEMS_COUNT - 1 && !checklistDataCollectors.isEmpty();
    }

    public void setTaskDate(LocalDate taskDate) {
        taskDataCollector.setDueDate(taskDate);
        notifyItemChanged(HEADER_POSITION);
    }

    private void scrollBottomIfPossible() {
        int itemCount = getItemCount();
        if (itemCount > 0) {
            for (RecyclerView attachedRecycler : attachedRecyclers) {
                attachedRecycler.scrollToPosition(itemCount - 1);
            }
        }
    }

    public Task getTask() {
        return new Task.Builder()
                .setId(taskDataCollector.getId())
                .setTitle(taskDataCollector.getTitle())
                .setUserId(taskDataCollector.getuserId())
                .setDescription(taskDataCollector.getDescription())
                .setDueDate(taskDataCollector.getDueDate())
                .setChecklistItemList(getCheckListItemsFromCollectors(checklistDataCollectors))
                .build();
    }

    public List<ChecklistItem> getRemovedChecklistItemsWithIds() {
        return getCheckListItemsFromCollectors(removedChecklistDataCollectorsWithId);
    }

    public List<ChecklistItem> getNewChecklistItems() {
        return getCheckListItemsFromCollectors(newChecklistDataCollectors);
    }

    public List<ChecklistItem> getUpdatedCheckListDataItems() {
        return getCheckListItemsFromCollectors(getUpdatedCheckListDataCollectors());
    }

    private List<ChecklistDataCollector> getUpdatedCheckListDataCollectors() {
        List<ChecklistDataCollector> result = new ArrayList<>(checklistDataCollectors);
        result.removeAll(newChecklistDataCollectors);
        return result;
    }

    private List<ChecklistItem> getCheckListItemsFromCollectors(List<ChecklistDataCollector> checklistDataCollectors) {
        List<ChecklistItem> checklistItems = new ArrayList<>(checklistDataCollectors.size());
        for (ChecklistDataCollector checklistDataCollector : checklistDataCollectors) {
            checklistItems.add(
                    new ChecklistItem.Builder()
                            .setId(checklistDataCollector.getId())
                            .setName(checklistDataCollector.getName())
                            .build());
        }
        return checklistItems;
    }
}
