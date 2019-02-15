package com.norvera.confession.ui.examination;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.databinding.FragmentExaminationentryListBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;


/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ExaminationFragment extends Fragment {

    private MainViewModel mViewModel;
    private FragmentExaminationentryListBinding binding;
    private long plantId;
    private ExaminationEntryAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExaminationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        plantId = ExaminationFragmentArgs.fromBundle(getArguments()).getCommandmentId();
        binding = FragmentExaminationentryListBinding.inflate(inflater, container, false);

        adapter = new ExaminationEntryAdapter();
        binding.rvExamination.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.rvExamination.addItemDecoration(decoration);

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel(requireActivity());
        subscribeUi(adapter, plantId);
    }

    private void subscribeUi(ExaminationEntryAdapter adapter, long plantId) {
        mViewModel.allExaminationsForCommandment(plantId).observe(this, adapter::submitList);
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }


}
