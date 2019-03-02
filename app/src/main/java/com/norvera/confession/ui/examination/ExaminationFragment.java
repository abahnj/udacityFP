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
    private long commandmentId;
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
        commandmentId = ExaminationFragmentArgs.fromBundle(getArguments()).getCommandmentId();
        binding = FragmentExaminationentryListBinding.inflate(inflater, container, false);

        binding.setLifecycleOwner(this);
        // todo refactor
        setupViewModel(requireActivity());

        adapter = new ExaminationEntryAdapter(mViewModel);
        binding.rvExamination.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.rvExamination.addItemDecoration(decoration);

        subscribeUi(adapter, commandmentId);

        return binding.getRoot();

    }

    private void subscribeUi(ExaminationEntryAdapter adapter, long commandmentId) {
        mViewModel.allExaminationsForCommandment(commandmentId).observe(this, examinationEntries -> {
            mViewModel.examinationEntries.set(examinationEntries);
            adapter.submitList(mViewModel.examinationEntries.get());
        });
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }


}
