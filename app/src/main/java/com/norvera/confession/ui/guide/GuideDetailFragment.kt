package com.norvera.confession.ui.guide


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.norvera.confession.databinding.FragmentGuideDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class GuideDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val guideEntry = GuideDetailFragmentArgs.fromBundle(arguments!!).guideEntry
        val binding = FragmentGuideDetailBinding.inflate(inflater, container, false)

        binding.guideEntry = guideEntry
        //binding.tvGuideDetail.setText(guideEntry.guideText);

        // Inflate the layout for this fragment
        return binding.root
    }

}// Required empty public constructor
