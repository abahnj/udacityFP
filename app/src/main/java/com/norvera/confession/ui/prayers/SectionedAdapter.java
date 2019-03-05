package com.norvera.confession.ui.prayers;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.norvera.confession.ListAdapterWithHeader;
import com.norvera.confession.R;
import com.norvera.confession.data.models.PrayersEntry;
import com.norvera.confession.databinding.FragmentPrayerItemBinding;
import com.norvera.confession.databinding.ListHeaderBinding;
import com.norvera.confession.ui.guide.GuideListFragmentDirections;

import java.util.Arrays;
import java.util.stream.IntStream;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SectionedAdapter extends ListAdapterWithHeader<PrayersEntry, RecyclerView.ViewHolder> {

    private static final int[] HEADER_POSITION = new int[]{0, 7};
    private HeaderDataItem headerItem = new HeaderDataItem("Title");

    // A class holding header data
    private class HeaderDataItem {
        private String title;

        private HeaderDataItem(String title) {
            this.title = title;
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
        if (viewType == R.layout.list_header) {
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
            case R.layout.list_header: {
                ((HeaderViewHolder)holder).onBind();
                break;
            }
            case R.layout.fragment_prayer_item: {
                ((ViewHolder)holder).onBind(createOnClickListener(getItem(position)), getItem(position));
                break;
            }
            default:{

            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (IntStream.of(HEADER_POSITION).anyMatch(x -> x == position)) {
                return R.layout.list_header;
            } else {
                return R.layout.fragment_prayer_item;
            }
        } else {
            if (Arrays.asList(HEADER_POSITION).contains(position)) {
                return R.layout.list_header;
            } else {
                return R.layout.fragment_prayer_item;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
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

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ListHeaderBinding binding;

        public HeaderViewHolder(@NonNull ListHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind() {
            binding.sectionHeader.setText(headerItem.title);
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