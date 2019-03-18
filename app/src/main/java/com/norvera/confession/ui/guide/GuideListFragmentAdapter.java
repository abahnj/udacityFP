package com.norvera.confession.ui.guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.databinding.FragmentGuideEntryBinding;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class GuideListFragmentAdapter extends ListAdapter<GuideEntry, GuideListFragmentAdapter.ViewHolder> {
    GuideListFragmentAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<GuideEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<GuideEntry>() {
        @Override
        public boolean areItemsTheSame(@NonNull GuideEntry guideEntry, @NonNull GuideEntry t1) {
            return guideEntry.id == t1.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GuideEntry guideEntry, @NonNull GuideEntry t1) {
            return guideEntry.equals(t1);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        FragmentGuideEntryBinding binding = FragmentGuideEntryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuideEntry guideEntry = getItem(position);
        holder.onBind(createOnClickListener(guideEntry.id, guideEntry), guideEntry);

    }

    private View.OnClickListener createOnClickListener(long id, GuideEntry guideEntry) {
        return view -> {
            GuideListFragmentDirections.ActionGuideFragmentListToGuideFragmentDetail toGuideFragmentDetail =
                    GuideListFragmentDirections.actionGuideFragmentListToGuideFragmentDetail(guideEntry);
            toGuideFragmentDetail.setTitle(guideEntry.guideTitle);
            toGuideFragmentDetail.setGuideEntry(guideEntry);
            Navigation.findNavController(view).navigate(toGuideFragmentDetail);
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentGuideEntryBinding binding;

        ViewHolder(@NonNull FragmentGuideEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

            void onBind(View.OnClickListener clickListener, GuideEntry item) {
                itemView.setTag(item);
                binding.setClickListener(clickListener);
                binding.setGuideEntry(item);
                binding.executePendingBindings();



        }
    }
}
