package com.norvera.confession.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.data.models.CommandmentEntry;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public List<CommandmentEntry> commandmentEntries;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModelFactory factory = MainViewModelFactory.getInstance(getActivity());
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), factory).get(MainViewModel.class);
        mViewModel.allCommandments().observe( this, commandmentEntries -> this.commandmentEntries = commandmentEntries);
        // TODO: Use the ViewModel
    }

}
