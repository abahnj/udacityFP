package com.norvera.confession.ui.guide


import android.os.Bundle
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.norvera.confession.viewmodels.MainViewModel
import com.norvera.confession.R
import com.norvera.confession.data.models.GuideEntry
import com.norvera.confession.databinding.FragmentGuideListBinding

/**
 * A simple [Fragment] subclass.
 */
class GuideListFragment : Fragment() {


    private var binding: FragmentGuideListBinding? = null
    private var guideId: Int = 0
    private var adapter: GuideListFragmentAdapter? = null
    private val mViewModel: MainViewModel by activityViewModels()
    private val intArray = SparseIntArray()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        guideId = GuideListFragmentArgs.fromBundle(arguments!!).guideId
        binding = FragmentGuideListBinding.inflate(inflater, container, false)

        intArray.put(R.id.cv_faq, 1)
        intArray.put(R.id.cv_asbp, 2)
        intArray.put(R.id.cv_effc, 3)
        intArray.put(R.id.cv_ccc, 4)
        intArray.put(R.id.cv_htmagc, 5)

        adapter = GuideListFragmentAdapter()
        binding!!.rvGuide.adapter = adapter
        val decoration = DividerItemDecoration(context!!, VERTICAL)
        binding!!.rvGuide.addItemDecoration(decoration)

        subscribeUi(adapter!!, guideId)

        // Inflate the layout for this fragment
        return binding!!.root
    }


    private fun subscribeUi(adapter: GuideListFragmentAdapter, guideId: Int) {
        mViewModel.allGuidesForId(intArray.get(guideId))
            .observe(activity!!, Observer<List<GuideEntry>> { adapter.submitList(it) })
    }




}// Required empty public constructor
