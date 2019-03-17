package com.norvera.confession.ui.commandment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.databinding.FragmentCommandmentsBinding;
import com.norvera.confession.interfaces.ClickListeners;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CommandmentEntry} and makes a call to the
 * specified {@link ClickListeners.CommandmentClickListener}.
 */
public class CommandmentsAdapter extends ListAdapter<CommandmentEntry, CommandmentsAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<CommandmentEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CommandmentEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull CommandmentEntry commandmentEntry, @NonNull CommandmentEntry t1) {
                    return (commandmentEntry.id == t1.id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommandmentEntry commandmentEntry, @NonNull CommandmentEntry t1) {
                    return commandmentEntry.equals(t1);
                }
            };


    CommandmentsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentCommandmentsBinding binding = FragmentCommandmentsBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        CommandmentEntry commandmentEntry = getItem(position);
        holder.onBind(createOnClickListener(commandmentEntry.id), commandmentEntry);
    }


    private View.OnClickListener createOnClickListener(long commandmentId) {

        return view -> {
            NavDirections commandmentsFragmentDirections =
                    CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(commandmentId);
            Navigation.findNavController(view).navigate(commandmentsFragmentDirections);
        };

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FragmentCommandmentsBinding binding;

        ViewHolder(FragmentCommandmentsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(View.OnClickListener clickListener, CommandmentEntry item) {
            itemView.setTag(item);
            binding.setClickListener(clickListener);
            binding.setCommandmentEntry(item);
            binding.executePendingBindings();
        }
    }

}
