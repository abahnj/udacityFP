package com.norvera.confession.ui.confession

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.norvera.confession.R
import com.norvera.confession.databinding.ConfessionFragmentOneBinding
import com.norvera.confession.utils.Constants
import com.norvera.confession.utils.SharedPreferencesHelper
import com.norvera.confession.utils.TimeAgo

class ConfessionFragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ConfessionFragmentOneBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.confessionFragmentTwo)
        )

        val lastConfession = SharedPreferencesHelper.getSharedPreferenceLong(
            context!!,
            Constants.LAST_CONFESSION, 0L
        )

        binding.blessMe.text = when (lastConfession) {
            0L -> getString(R.string.null_time_since_last)
            else -> getString(R.string.bless_me, TimeAgo.getTimeAgo(lastConfession!!))
        }

        return binding.root
    }

}
