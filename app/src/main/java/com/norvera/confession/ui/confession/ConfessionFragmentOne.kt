package com.norvera.confession.ui.confession

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.norvera.confession.R
import com.norvera.confession.databinding.ConfessionFragmentOneBinding

class ConfessionFragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ConfessionFragmentOneBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.confessionFragmentTwo,
                null
            )
        )

        return binding.root
    }

}
