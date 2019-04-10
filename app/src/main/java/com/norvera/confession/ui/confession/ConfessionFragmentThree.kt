package com.norvera.confession.ui.confession


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.norvera.confession.viewmodels.MainViewModel
import com.norvera.confession.R
import com.norvera.confession.databinding.FragmentConfessionThreeBinding
import com.norvera.confession.utils.Constants
import com.norvera.confession.utils.InjectorUtils
import com.norvera.confession.utils.SharedPreferencesHelper
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class ConfessionFragmentThree : Fragment() {

    private var mViewModel: MainViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel(context)

        // Inflate the layout for this fragment
        val binding = FragmentConfessionThreeBinding.inflate(inflater, container, false)

        binding.btnFinish.setOnClickListener { displayDialog() }
        return binding.root
    }

    private fun setupViewModel(context: Context?) {
        val factory = InjectorUtils.provideViewModelFactory(context!!)
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }


    private fun displayDialog() {
        mViewModel!!.getInspirationForId(Random.nextInt(1, 20))
            .observe(this, Observer { inspirationEntry ->
                val newFragment = InspirationsDialogFragment()
                val bundle = Bundle()
                bundle.putString(KEY_TITLE, inspirationEntry.author)
                bundle.putString(KEY_INSPIRATION, inspirationEntry.text)
                newFragment.arguments = bundle
                newFragment.show(fragmentManager!!, "inspiration")
            })

    }

    class InspirationsDialogFragment : DialogFragment() {


        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(context!!)

            val bundle = arguments
            val title = bundle!!.getString(ConfessionFragmentThree.KEY_TITLE)
            val inspiration = bundle.getString(ConfessionFragmentThree.KEY_INSPIRATION)

            builder.setTitle(title).setMessage(inspiration)
                .setPositiveButton(R.string.finish_confession) { _, _ -> goHome() }

            // Create the AlertDialog object and return it
            return builder.create()
        }

        override fun onDismiss(dialog: DialogInterface) {
            super.onDismiss(dialog)
            setLastConfession()
            goHome()
        }

        private fun setLastConfession() {
            SharedPreferencesHelper.setSharedPreferenceLong(
                context!!,
                Constants.LAST_CONFESSION,
                System.currentTimeMillis()
            )
        }

        private fun goHome() {
            NavHostFragment.findNavController(parentFragment!!).navigate(R.id.commandment_fragment)
        }
    }

    companion object {

        private const val KEY_TITLE = "title"
        private const val KEY_INSPIRATION = "inspiration"
    }


}
