package com.norvera.confession.ui.confession


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.norvera.confession.R
import com.norvera.confession.data.models.ExaminationActiveEntry
import com.norvera.confession.databinding.ConfessionFragmentTwoBinding
import com.norvera.confession.MainViewModel
import com.norvera.confession.utils.InjectorUtils

/**
 * A simple [Fragment] subclass.
 */
class ConfessionFragmentTwo : Fragment() {


    private lateinit var mViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = ConfessionFragmentTwoBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.btnNext.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.confessionFragmentThree,
                null
            )
        )

        // todo refactor
        setupViewModel(requireActivity())

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

    private fun setupViewModel(context: Context) {
        val factory = InjectorUtils.provideViewModelFactory(context)
        mViewModel = ViewModelProviders.of(activity!!, factory).get(MainViewModel::class.java)
    }


}// Required empty public constructor
