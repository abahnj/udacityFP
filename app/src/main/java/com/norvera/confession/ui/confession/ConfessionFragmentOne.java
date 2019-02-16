package com.norvera.confession.ui.confession;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.norvera.confession.databinding.ConfessionFragmentOneBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConfessionFragmentOne extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ConfessionFragmentOneBinding binding = ConfessionFragmentOneBinding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener( view -> {
            Toast.makeText(getContext(), ((Button) view).getText(), Toast.LENGTH_SHORT).show();
        });


        return binding.getRoot();
    }

}
