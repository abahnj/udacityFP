package com.norvera.confession.ui.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.databinding.FragmentGuideBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {


    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentGuideBinding binding =  FragmentGuideBinding.inflate(inflater, container, false);

        binding.setClickListener(createOnClickListener());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private View.OnClickListener createOnClickListener() {

        return view -> {
            GuideFragmentDirections.ActionGuideFragmentToGuideFragmentList toGuideFragmentList = GuideFragmentDirections.actionGuideFragmentToGuideFragmentList(view.getId());

            Navigation.findNavController(view).navigate(toGuideFragmentList);
           /* CommandmentsFragmentDirections.CommandmentFragmentToExaminationFragment commandmentsFragmentDirections =
                    CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(commandmentId);
            Navigation.findNavController(view).navigate(commandmentsFragmentDirections);
            */
        };

    }
}
