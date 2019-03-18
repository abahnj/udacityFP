package com.norvera.confession.ui.prayers;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.ListAdapterWithHeader;
import com.norvera.confession.R;
import com.norvera.confession.data.models.PrayersEntry;
import com.norvera.confession.databinding.FragmentPrayerItemBinding;
import com.norvera.confession.databinding.ListHeaderBinding;

import java.util.Arrays;
import java.util.stream.IntStream;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SectionedAdapter extends ListAdapterWithHeader<PrayersEntry, RecyclerView.ViewHolder> {

    private static final int[] HEADER_POSITION = new int[]{0, 7};
    private static final int LIST_VIEW_TYPE = R.layout.fragment_prayer_item;
    public static final int HEADER_VIEW_TYPE = R.layout.list_header;
    private HeaderDataItem headerItem = new HeaderDataItem();

    // A class holding header data
    private class HeaderDataItem {
        private final String title;

        private HeaderDataItem() {
            this.title = "Title";
        }
    }

    private static final DiffUtil.ItemCallback<PrayersEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PrayersEntry>() {
                @Override
                public boolean areItemsTheSame(@NonNull PrayersEntry PrayersEntry, @NonNull PrayersEntry t1) {
                    return (PrayersEntry.id == t1.id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull PrayersEntry PrayersEntry, @NonNull PrayersEntry t1) {
                    return PrayersEntry.equals(t1);
                }
            };

    SectionedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW_TYPE) {
            ListHeaderBinding binding = ListHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HeaderViewHolder(binding);
        } else {
            FragmentPrayerItemBinding binding = FragmentPrayerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_VIEW_TYPE: {
                ((HeaderViewHolder) holder).onBind(position);
                break;
            }
            case LIST_VIEW_TYPE: {
                ((ViewHolder) holder).onBind(createOnClickListener(getItem(position)), getItem(position));
                break;
            }
            default: {

            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (IntStream.of(HEADER_POSITION).anyMatch(x -> x == position)) {
                return HEADER_VIEW_TYPE;
            } else {
                return LIST_VIEW_TYPE;
            }
        } else {
            if (Arrays.asList(HEADER_POSITION).contains(position)) {
                return HEADER_VIEW_TYPE;
            } else {
                return LIST_VIEW_TYPE;
            }
        }
    }

    private View.OnClickListener createOnClickListener(PrayersEntry prayerEntry) {

        return view -> {
            PrayerListDirections.ActionPrayerFragmentListToPrayerFragmentDetail toPrayerFragmentDetail =
                    PrayerListDirections.actionPrayerFragmentListToPrayerFragmentDetail(prayerEntry);
            toPrayerFragmentDetail.setTitle(prayerEntry.prayerName);
            toPrayerFragmentDetail.setPrayerEntry(prayerEntry);
            Navigation.findNavController(view).navigate(toPrayerFragmentDetail);
        };

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentPrayerItemBinding binding;

        ViewHolder(FragmentPrayerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(View.OnClickListener clickListener, PrayersEntry item) {
            binding.setPrayerEntry(item);
            binding.setClickListener(clickListener);
            binding.executePendingBindings();
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ListHeaderBinding binding;

        HeaderViewHolder(@NonNull ListHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(int position) {
            //todo move to string resource
            binding.sectionHeader.setText(position == 0 ? "Act of Contrition" : "Traditional Prayers");
            binding.executePendingBindings();
        }
    }

    // Updates the header item view
    void setHeaderItem(HeaderDataItem newHeaderItem) {
        HeaderDataItem previousItem = this.headerItem;
        this.headerItem = newHeaderItem;
        if (previousItem != newHeaderItem) {
            notifyDataSetChanged();
        }
    }

}
