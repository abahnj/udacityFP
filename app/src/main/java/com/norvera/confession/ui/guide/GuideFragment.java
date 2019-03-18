package com.norvera.confession.ui.guide;


import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norvera.confession.R;
import com.norvera.confession.databinding.FragmentGuideBinding;

import org.json.JSONObject;

import java.util.HashMap;

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
            SparseArray<String> intArray = new SparseArray<>(5);
            intArray.put(R.id.cv_faq, getString(R.string.faq));
            intArray.put(R.id.cv_asbp, getString(R.string.as_said_by_popes));
            intArray.put(R.id.cv_effc, getString(R.string.extracts_fulton_sheen));
            intArray.put(R.id.cv_ccc, getString(R.string.catechism_of_the_catholic_church));
            intArray.put(R.id.cv_htmagc, getString(R.string.how_to_make_a_good_confession));

            toGuideFragmentList.setTitle(intArray.get(view.getId()));
            Navigation.findNavController(view).navigate(toGuideFragmentList);
        };

    }
}
