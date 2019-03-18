package com.norvera.confession.ui.prayers;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.databinding.FragmentPrayerListBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.DividerDecoration;
import com.norvera.confession.utils.InjectorUtils;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrayerList extends Fragment {


    private MainViewModel mViewModel;

    public PrayerList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPrayerListBinding binding = FragmentPrayerListBinding.inflate(inflater, container, false);

        // todo refactor
        setupViewModel(requireActivity());

        SectionedAdapter adapter = new SectionedAdapter();
        binding.rvPrayers.setAdapter(adapter);
        binding.rvPrayers.addItemDecoration(new DividerDecoration(getContext()));

        subscribeUi(adapter);

        return binding.getRoot();
    }


    private void subscribeUi(SectionedAdapter adapter) {
        mViewModel.loadAllPrayers().observe(this, adapter::submitList);
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }
}
