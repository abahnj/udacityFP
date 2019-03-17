package com.norvera.confession.ui.examination;


import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.norvera.confession.R;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.databinding.FragmentExaminationentryBinding;
import com.norvera.confession.ui.main.MainViewModel;

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
                    return (examinationEntry.id == t1.id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull ExaminationEntry examinationEntry, @NonNull ExaminationEntry t1) {
                    return examinationEntry.equals(t1);
                }
            };
    private final MainViewModel viewModel;

    ExaminationEntryAdapter(MainViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.viewModel = viewModel;
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
        holder.onBind(createOnClickListener(examinationEntry.id, examinationEntry), examinationEntry);
    }

    private View.OnClickListener createOnClickListener(long examinationId, ExaminationEntry examinationEntry) {

        return view -> {
            examinationEntry.count += 1;
            viewModel.updateCountForEntry(examinationEntry);
           /* CommandmentsFragmentDirections.CommandmentFragmentToExaminationFragment commandmentsFragmentDirections =
                    CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(commandmentId);
            Navigation.findNavController(view).navigate(commandmentsFragmentDirections);
            */
        };

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private FragmentExaminationentryBinding binding;
        private ExaminationEntry examinationEntry;


        ViewHolder(FragmentExaminationentryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnCreateContextMenuListener(this); //Register OnCreate Menu Listener

        }

        void onBind(View.OnClickListener clickListener, ExaminationEntry item) {
            this.examinationEntry = item;
            itemView.setTag(item);
            binding.executePendingBindings();
            binding.setClickListener(clickListener);
            binding.setExaminationEntry(item);
            binding.executePendingBindings();

            /*if (commandmentEntry.commandment != null ) {
                if (commandmentEntry.id < 11)
                    mTvCommandmentDescription.setText(commandmentEntry.text);
                else
                    mTvCommandmentDescription.setText(commandmentEntry.commandment);
            }

            mTvCommandmentTitle.setText(getNumberOrdinal((commandmentEntry.id)));

*/
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem Decrement = menu.add(Menu.NONE, 1, 0, R.string.context_menu_count);
            MenuItem Edit = menu.add(Menu.NONE, 2, 1, R.string.context_menu_edit);
            MenuItem Delete = menu.add(Menu.NONE, 3, 2, R.string.context_menu_delete);
            MenuItem ResetCount = menu.add(Menu.NONE, 4, 3, R.string.context_menu_restore);


            Edit.setOnMenuItemClickListener(onMenuClick);
            Delete.setOnMenuItemClickListener(onMenuClick);
            Decrement.setOnMenuItemClickListener(onMenuClick);
            ResetCount.setOnMenuItemClickListener(onMenuClick);

        }

        //Add an OnMenuItem Listener to execute commands OnClick of context menu task
        private final MenuItem.OnMenuItemClickListener onMenuClick = item -> {

            switch (item.getItemId()) {
                case 1:
                    if (examinationEntry.count > 0){
                        examinationEntry.count--;
                        viewModel.decrementCount(examinationEntry);
                    }
                    //Do stuff
                    break;

                case 2:
                    //Do stuff

                    break;
            }
            return true;
        };


    }

}


