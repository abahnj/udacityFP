package com.norvera.confession;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListAdapterWithHeader<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private DiffUtil.ItemCallback<T> diffCallback;
    private int headerOffset = 1;

    private AsyncListDiffer<T> mHelper = new AsyncListDiffer<>(new OffsetListUpdateCallback(this, headerOffset),
            new AsyncDifferConfig.Builder<>(diffCallback).build());

    protected ListAdapterWithHeader(DiffUtil.ItemCallback<T> diffCallback) {
        this.diffCallback = diffCallback;
    }

    /**
     * Submits a new list to be diffed, and displayed.
     *
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param list The new list to be displayed.
     */
    public void submitList(List<T> list) {
        mHelper.submitList(list);
    }

    /**
     * Accounts for header offset
     */
    public T getItem(int position) {
        return mHelper.getCurrentList().get(position - headerOffset);
    }

    /**
     * Returns the total number of items in the data set held by the adapter (includes header offset)
     *
     * @return The total number of items in this adapter (plus the header offset)
     */
    @Override
    public int getItemCount() {
        return mHelper.getCurrentList().size() + headerOffset;
    }


    /**
     * ListUpdateCallback that dispatches update events to the given adapter with a position offset to
     * allow for the number of header items.
     *
     *  @see DiffUtil.DiffResult#dispatchUpdatesTo(RecyclerView.Adapter)
     */
    private class OffsetListUpdateCallback implements ListUpdateCallback {
        private RecyclerView.Adapter adapter;
        private int offset;

        private OffsetListUpdateCallback(RecyclerView.Adapter adapter, int offset) {
            this.adapter = adapter;
            this.offset = offset;
        }

        private int offsetPosition(int originalPosition) {
            return originalPosition + offset;
        }

        @Override
        public void onInserted(int position, int count) {
            adapter.notifyItemRangeInserted(offsetPosition(position), count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapter.notifyItemRangeRemoved(offsetPosition(position), count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapter.notifyItemMoved(offsetPosition(fromPosition), offsetPosition(toPosition));
        }

        @Override
        public void onChanged(int position, int count, @Nullable Object payload) {
            adapter.notifyItemRangeChanged(offsetPosition(position), count, payload);
        }
    }
}
