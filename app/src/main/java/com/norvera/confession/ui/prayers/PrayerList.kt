package com.norvera.confession.ui.prayers


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.norvera.confession.MainViewModel
import com.norvera.confession.data.models.PrayersEntry
import com.norvera.confession.databinding.FragmentPrayerListBinding
import com.norvera.confession.utils.DividerDecoration

/**
 * A simple [Fragment] subclass.
 */
class PrayerList : Fragment() {


    private lateinit var binding: FragmentPrayerListBinding
    private lateinit var adapter: SectionedAdapter
    private val mViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrayerListBinding.inflate(inflater, container, false)

        adapter = SectionedAdapter()
        binding.rvPrayers.adapter = adapter
        binding.rvPrayers.addItemDecoration(DividerDecoration(context!!))

        subscribeUi(adapter)

        return binding.root
    }


    private fun subscribeUi(adapter: SectionedAdapter) {
        mViewModel.loadAllPrayers()
            .observe(this, Observer<List<PrayersEntry>> { adapter.submitList(it) })
    }


}// Required empty public constructor
