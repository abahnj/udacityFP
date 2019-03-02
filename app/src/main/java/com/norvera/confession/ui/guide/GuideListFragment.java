package com.norvera.confession.ui.guide;


import android.content.Context;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.databinding.FragmentGuideListBinding;
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
public class GuideListFragment extends Fragment {


    private FragmentGuideListBinding binding;
    private int guideId;
    private GuideListFragmentAdapter adapter;
    private MainViewModel mViewModel;
    private SparseIntArray intArray = new SparseIntArray();

    public GuideListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        guideId = GuideListFragmentArgs.fromBundle(getArguments()).getGuideId();
        binding = FragmentGuideListBinding.inflate(inflater, container, false);

        intArray.put(R.id.cv_faq, 1);
        intArray.put(R.id.cv_asbp, 2);
        intArray.put(R.id.cv_effc, 3);
        intArray.put(R.id.cv_ccc, 4);
        intArray.put(R.id.cv_htmagc, 5);

        setupViewModel(requireActivity());

        adapter = new GuideListFragmentAdapter();
        binding.rvGuide.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        binding.rvGuide.addItemDecoration(decoration);

        subscribeUi(adapter, guideId);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    private void subscribeUi(GuideListFragmentAdapter adapter, int guideId) {
        guideId = intArray.get(guideId);
        mViewModel.allGuidesForId(guideId).observe(getActivity(), adapter::submitList);
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }


}
