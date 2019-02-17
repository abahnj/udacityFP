package com.norvera.confession.ui.guide;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.norvera.confession.databinding.FragmentGuideBinding;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {


    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentGuideBinding binding =  FragmentGuideBinding.inflate(inflater, container, false);

        binding.setClickListener(createOnClickListener());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private View.OnClickListener createOnClickListener() {

        return view -> {
            Toast.makeText(view.getContext(), Integer.toString(view.getId()), Toast.LENGTH_SHORT).show();


           /* CommandmentsFragmentDirections.CommandmentFragmentToExaminationFragment commandmentsFragmentDirections =
                    CommandmentsFragmentDirections.commandmentFragmentToExaminationFragment(commandmentId);
            Navigation.findNavController(view).navigate(commandmentsFragmentDirections);
            */
        };

    }
}
