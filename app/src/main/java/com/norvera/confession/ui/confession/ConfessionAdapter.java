package com.norvera.confession.ui.confession;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.databinding.FragmentConfessionEntryBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ConfessionAdapter extends ListAdapter<ExaminationActiveEntry, ConfessionAdapter.ViewHolder> {
    private static final DiffUtil.ItemCallback<ExaminationActiveEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ExaminationActiveEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull ExaminationActiveEntry ExaminationActiveEntry, @NonNull ExaminationActiveEntry t1) {
                    return (ExaminationActiveEntry.getId() == t1.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ExaminationActiveEntry ExaminationActiveEntry, @NonNull ExaminationActiveEntry t1) {
                    return ExaminationActiveEntry.equals(t1);
                }
            };


    ConfessionAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentConfessionEntryBinding binding = FragmentConfessionEntryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new ConfessionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExaminationActiveEntry examinationEntry = getItem(position);
        holder.onBind(examinationEntry);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentConfessionEntryBinding binding;

        ViewHolder(@NonNull FragmentConfessionEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(ExaminationActiveEntry item) {
            itemView.setTag(item);
            binding.setExaminationEntry(item);
            binding.executePendingBindings();

        }
    }
}
