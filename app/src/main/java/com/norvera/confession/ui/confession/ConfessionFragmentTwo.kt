package com.norvera.confession.ui.confession


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.norvera.confession.viewmodels.MainViewModel
import com.norvera.confession.R
import com.norvera.confession.data.models.ExaminationActiveEntry
import com.norvera.confession.databinding.ConfessionFragmentTwoBinding

/**
 * A simple [Fragment] subclass.
 */
class ConfessionFragmentTwo : Fragment() {

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = ConfessionFragmentTwoBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.btnNext.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.confessionFragmentThree)
        )


        val adapter = ConfessionAdapter()
        binding.rvConfession.adapter = adapter
        val decoration = DividerItemDecoration(context!!, VERTICAL)
        binding.rvConfession.addItemDecoration(decoration)

        subscribeUi(adapter)

        return binding.root

    }

    private fun subscribeUi(adapter: ConfessionAdapter) {
        mViewModel.allExaminationsWithCount()
            .observe(this, Observer<List<ExaminationActiveEntry>> { adapter.submitList(it) })
        //todo add empty view
    }

}
