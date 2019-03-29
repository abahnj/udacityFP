package com.norvera.confession.ui.guide


import android.os.Bundle
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.norvera.confession.R
import com.norvera.confession.databinding.FragmentGuideBinding

import org.json.JSONObject

import java.util.HashMap
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 */
class GuideFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentGuideBinding.inflate(inflater, container, false)

        binding.clickListener = createOnClickListener()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            val intArray = SparseArrayCompat<String>(5)
            intArray.put(R.id.cv_faq, getString(R.string.faq))
            intArray.put(R.id.cv_asbp, getString(R.string.as_said_by_popes))
            intArray.put(R.id.cv_effc, getString(R.string.extracts_fulton_sheen))
            intArray.put(R.id.cv_ccc, getString(R.string.catechism_of_the_catholic_church))
            intArray.put(R.id.cv_htmagc, getString(R.string.how_to_make_a_good_confession))

            val toGuideFragmentList =
                GuideFragmentDirections.actionGuideFragmentToGuideFragmentList(it.id, intArray.get(it.id)!!)
            findNavController().navigate(toGuideFragmentList)

        }

    }
}// Required empty public constructor
