package com.norvera.confession.ui.commandment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.norvera.confession.MainViewModel
import com.norvera.confession.R
import com.norvera.confession.data.models.CommandmentEntry
import com.norvera.confession.databinding.FragmentCommandmentsListBinding
import com.norvera.confession.utils.SharedPreferencesHelper

/**
 * A fragment representing a list of Items.
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class CommandmentsFragment : Fragment() {
    private lateinit var commandmentsAdapter: CommandmentsAdapter
    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentCommandmentsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommandmentsListBinding.inflate(inflater, container, false)
        val context = context

        // Set the adapter
        commandmentsAdapter = CommandmentsAdapter()
        binding.rvCommandments.adapter = commandmentsAdapter
        subscribeUi(commandmentsAdapter)

        val decoration = DividerItemDecoration(context!!, VERTICAL)
        binding.rvCommandments.addItemDecoration(decoration)

        return binding.root
    }

    private fun subscribeUi(adapter: CommandmentsAdapter?) {
        val vocation = SharedPreferencesHelper.getSharedPreferenceString(
            context!!,
            getString(R.string.pref_vocation_key),
            "1"
        )

        when (vocation) {
            "0", "1" -> {
                mViewModel.allCommandmentsForLay()
                    .observe(this, Observer<List<CommandmentEntry>> { adapter!!.submitList(it) })
            }
            else -> {
                mViewModel.allCommandmentsForReligious()
                    .observe(viewLifecycleOwner, Observer { adapter!!.submitList(it) })
            }
        }
    }


}
