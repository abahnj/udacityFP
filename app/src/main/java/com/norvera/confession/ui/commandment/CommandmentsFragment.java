package com.norvera.confession.ui.commandment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.databinding.FragmentCommandmentsListBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;
import com.norvera.confession.utils.SharedPreferencesHelper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class CommandmentsFragment extends Fragment {

    private static final String KEY_RECYCLER_STATE = "RECYCLER-STATE";
    private CommandmentsAdapter commandmentsAdapter;
    private MainViewModel mViewModel;

    private enum Vocation {SINGLE, MARRIED, PRIEST, RELIGIOUS}

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommandmentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCommandmentsListBinding binding = FragmentCommandmentsListBinding.inflate(inflater, container, false);
        Context context = getContext();

        // Set the adapter
        commandmentsAdapter = new CommandmentsAdapter();
        binding.rvCommandments.setAdapter(commandmentsAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(context, VERTICAL);
        binding.rvCommandments.addItemDecoration(decoration);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel(getActivity());
        subscribeUi(commandmentsAdapter);
    }

    private void subscribeUi(CommandmentsAdapter adapter) {
        String vocation = SharedPreferencesHelper.getSharedPreferenceString(getContext(), getString(R.string.pref_vocation_key), "1");

        switch (vocation){
            case "0": case "1": {
                mViewModel.allCommandmentsForLay().observe(this, adapter::submitList);
                break;
            }
            default: {
                mViewModel.allCommandmentsForReligious().observe(this, adapter::submitList);
                break;
            }
        }
    }

    private void setupViewModel(Context context) {
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(context);
        mViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);
    }

}
