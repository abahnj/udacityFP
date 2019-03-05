package com.norvera.confession.ui.prayers;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.databinding.FragmentPrayerListBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrayerList extends Fragment {


    private FragmentPrayerListBinding binding;
    private SectionedAdapter adapter;
    private MainViewModel mViewModel;

    public PrayerList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrayerListBinding.inflate(inflater, container, false);

        // todo refactor
        setupViewModel(requireActivity());

        adapter = new SectionedAdapter();
        binding.rvPrayers.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.rvPrayers.addItemDecoration(decoration);

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