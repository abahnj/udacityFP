package com.norvera.confession.ui.prayers


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.norvera.confession.databinding.FragmentPrayerDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class PrayerDetail : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val prayersEntry = PrayerDetailArgs.fromBundle(arguments!!).prayerEntry
        val binding = FragmentPrayerDetailBinding.inflate(inflater, container, false)

        binding.prayerEntry = prayersEntry
        //binding.tvGuideDetail.setText(guideEntry.guideText);

        // Inflate the layout for this fragment
        return binding.root
    }

}// Required empty public constructor
