package com.norvera.confession.ui.confession;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.databinding.FragmentExaminationentryBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ConfessionAdapter extends ListAdapter<ExaminationActiveEntry, ConfessionAdapter.ViewHolder> {
    private static final DiffUtil.ItemCallback<ExaminationActiveEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ExaminationActiveEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull ExaminationActiveEntry ExaminationActiveEntry, @NonNull ExaminationActiveEntry t1) {
                    return (ExaminationActiveEntry._id == t1._id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull ExaminationActiveEntry ExaminationActiveEntry, @NonNull ExaminationActiveEntry t1) {
                    return ExaminationActiveEntry.equals(t1);
                }
            };


    protected ConfessionAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentExaminationentryBinding binding = FragmentExaminationentryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new ConfessionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExaminationActiveEntry examinationEntry = getItem(position);
        holder.onBind(examinationEntry);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentExaminationentryBinding binding;

        public ViewHolder(@NonNull FragmentExaminationentryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(ExaminationEntry item) {
            itemView.setTag(item);
            binding.setClickListener(null);
            binding.setExaminationEntry(item);
            binding.executePendingBindings();

        }
    }
}
