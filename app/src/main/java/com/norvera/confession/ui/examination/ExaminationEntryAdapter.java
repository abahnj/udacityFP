package com.norvera.confession.ui.examination;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.databinding.FragmentExaminationentryBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link ListAdapter} that can display a {@link ExaminationEntry} and makes a call to the
 * specified {@link View.OnClickListener}.
 */
public class ExaminationEntryAdapter extends ListAdapter<ExaminationEntry, ExaminationEntryAdapter.ViewHolder> {


    private static final DiffUtil.ItemCallback<ExaminationEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ExaminationEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull ExaminationEntry examinationEntry, @NonNull ExaminationEntry t1) {
                    return (examinationEntry._id == t1._id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull ExaminationEntry examinationEntry, @NonNull ExaminationEntry t1) {
                    return examinationEntry.equals(t1);
                }
            };

    ExaminationEntryAdapter(){
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentExaminationentryBinding binding = FragmentExaminationentryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new ExaminationEntryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExaminationEntryAdapter.ViewHolder holder, int position) {
        ExaminationEntry examinationEntry = getItem(position);
        holder.onBind(createOnClickListener(examinationEntry._id), examinationEntry);
    }

    private View.OnClickListener createOnClickListener(long examinationId) {

        return view -> {
            Toast.makeText(view.getContext(), (Long.toString(examinationId)), Toast.LENGTH_SHORT).show();

           /* CommandmentsFragmentDirections.CommandmentFragmentToExaminationFragment commandmentsFragmentDirections =
                    CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(commandmentId);
            Navigation.findNavController(view).navigate(commandmentsFragmentDirections);*/
        };

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FragmentExaminationentryBinding binding;

        ViewHolder(FragmentExaminationentryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(View.OnClickListener clickListener, ExaminationEntry item) {
            itemView.setTag(item);
            binding.setClickListener(clickListener);
            binding.setExaminationEntry(item);
            binding.executePendingBindings();

            /*if (commandmentEntry.commandment != null ) {
                if (commandmentEntry._id < 11)
                    mTvCommandmentDescription.setText(commandmentEntry.text);
                else
                    mTvCommandmentDescription.setText(commandmentEntry.commandment);
            }

            mTvCommandmentTitle.setText(getNumberOrdinal((commandmentEntry._id)));

*/
        }
    }

}
