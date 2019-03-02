package com.norvera.confession.ui.prayers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.norvera.confession.data.models.PrayersEntry;
import com.norvera.confession.databinding.FragmentPrayerDetailBinding;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrayerDetail extends Fragment {


    public PrayerDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        PrayersEntry prayersEntry = PrayerDetailArgs.fromBundle(getArguments()).getPrayerEntry();
        FragmentPrayerDetailBinding binding = FragmentPrayerDetailBinding.inflate(inflater, container, false);

        binding.setPrayerEntry(prayersEntry);
        //binding.tvGuideDetail.setText(guideEntry.guideText);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
