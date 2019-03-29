package com.norvera.confession.ui.examination

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.norvera.confession.R
import com.norvera.confession.data.models.ExaminationEntry
import com.norvera.confession.data.models.User
import com.norvera.confession.databinding.FragmentExaminationentryListBinding
import com.norvera.confession.MainViewModel
import com.norvera.confession.utils.InjectorUtils
import com.norvera.confession.utils.SharedPreferencesHelper


/**
 * A fragment representing a list of Items.
 *
 *
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ExaminationFragment : Fragment() {

    private lateinit var mViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val commandmentId = ExaminationFragmentArgs.fromBundle(arguments!!).commandmentId
        val binding = FragmentExaminationentryListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        // todo refactor
        setupViewModel(requireActivity())

        val adapter = ExaminationEntryAdapter(mViewModel!!)
        binding.rvExamination.adapter = adapter
        val decoration = DividerItemDecoration(context!!, VERTICAL)
        binding.rvExamination.addItemDecoration(decoration)

        subscribeUi(adapter, commandmentId)

        return binding.root

    }

    private fun subscribeUi(adapter: ExaminationEntryAdapter, commandmentId: Long) {
        val vocation = SharedPreferencesHelper.getSharedPreferenceString(
            context!!,
            getString(R.string.pref_vocation_key),
            "0"
        )
        val age = SharedPreferencesHelper.getSharedPreferenceString(
            context!!,
            getString(R.string.pref_age_key),
            "0"
        )
        val gender = SharedPreferencesHelper.getSharedPreferenceString(
            context!!,
            getString(R.string.pref_gender_key),
            "0"
        )

        //todo refactor raw sql query out of user class
        val user =
            User(Integer.valueOf(vocation!!), Integer.valueOf(age!!), Integer.valueOf(gender!!))

        mViewModel.allExaminationsForCommandmentAndUser(
            user.getUserSelectionForCommandment(
                commandmentId
            )
        ).observe(this, Observer<List<ExaminationEntry>>{ examinationEntries ->
            mViewModel.examinationEntries.set(examinationEntries)
            adapter.submitList(mViewModel.examinationEntries.get())
        })
    }

    private fun setupViewModel(context: Context) {
        val factory = InjectorUtils.provideViewModelFactory(context)
        mViewModel = ViewModelProviders.of(activity!!, factory).get(MainViewModel::class.java)
    }


}
