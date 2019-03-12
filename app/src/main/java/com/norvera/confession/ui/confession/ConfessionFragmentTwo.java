package com.norvera.confession.ui.confession;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.databinding.ConfessionFragmentTwoBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfessionFragmentTwo extends Fragment {


    private MainViewModel mViewModel;

    public ConfessionFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConfessionFragmentTwoBinding binding = ConfessionFragmentTwoBinding.inflate(inflater, container, false);

        binding.setLifecycleOwner(this);

        binding.btnNext.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.confessionFragmentThree, null));

        // todo refactor
        setupViewModel(requireActivity());

        ConfessionAdapter adapter = new ConfessionAdapter();
        binding.rvConfession.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.rvConfession.addItemDecoration(decoration);

        subscribeUi(adapter);

        return binding.getRoot();

    }

    private void subscribeUi(ConfessionAdapter adapter) {
        mViewModel.allExaminationsWithCount().observe(this, adapter::submitList);
        //todo add empty view
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }


}
