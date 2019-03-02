package com.norvera.confession.ui.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.databinding.FragmentGuideDetailBinding;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideDetailFragment extends Fragment {


    public GuideDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GuideEntry guideEntry = GuideDetailFragmentArgs.fromBundle(getArguments()).getGuideEntry();
        FragmentGuideDetailBinding binding = FragmentGuideDetailBinding.inflate(inflater, container, false);

        binding.setGuideEntry(guideEntry);
        //binding.tvGuideDetail.setText(guideEntry.guideText);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
