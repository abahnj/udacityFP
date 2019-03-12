package com.norvera.confession.ui.confession;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.databinding.ConfessionFragmentOneBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class ConfessionFragmentOne extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ConfessionFragmentOneBinding binding = ConfessionFragmentOneBinding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.confessionFragmentTwo, null));

        return binding.getRoot();
    }

}
