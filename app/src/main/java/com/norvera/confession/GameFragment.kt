package com.norvera.confession

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.norvera.confession.data.billingrepo.localdb.GasTank
import com.norvera.confession.viewmodels.BillingViewModel
import kotlinx.android.synthetic.main.fragment_game.*


/**
 * This Fragment represents the game world. Hence it contains logic to display the items the user
 * owns -- gas, premium car, and gold status--,and logic for what it means to drive
 * the car; this is a driving game after all. When the user wants to buy, the app navigates to a
 * different Fragment.
 *
 * As you can see there is really nothing about Billing here. That's on purpose, all the billing
 * code reside in the [repo][BillingRepository] layer and below.
 */
class GameFragment : Fragment() {
    private val LOG_TAG = "GameFragment"

    private var gasLevel: GasTank? = null
    private lateinit var billingViewModel: BillingViewModel

    override fun onCreateView(inflater: LayoutInflater, containter: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, containter, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_drive.setOnClickListener { onDrive() }
        btn_purchase.setOnClickListener { onPurchase(it) }

        billingViewModel = ViewModelProviders.of(this).get(BillingViewModel::class.java)
        billingViewModel.gasTankLiveData.observe(this, Observer {
            gasLevel = it
            showGasLevel()
        })
        billingViewModel.premiumCarLiveData.observe(this, Observer {
            it?.apply { showPremiumCar(entitled) }
        })
        billingViewModel.goldStatusLiveData.observe(this, Observer {
            it?.apply { showGoldStatus(entitled) }
        })
    }

    private fun onDrive() {
        gasLevel?.apply {
            if (!needGas()) {
                billingViewModel.decrementAndSaveGas()
                showGasLevel()
                Toast.makeText(context, "asdfg", Toast.LENGTH_LONG).show()
            }
        }
        if (gasLevel?.needGas() != false) {
            Toast.makeText(context, "fghjk", Toast.LENGTH_LONG).show()
        }
    }

    private fun onPurchase(view: View) {
        view.findNavController().navigate(R.id.action_makePurchase)
    }

    private fun showGasLevel() {
        gasLevel?.apply {
            val drawableName = "gas_level_${getLevel()}"
            val drawableId = resources.getIdentifier(drawableName, "drawable", requireActivity().packageName)
            gas_gauge.setImageResource(drawableId)
        }
        if (gasLevel == null) {
            gas_gauge.setImageResource(R.drawable.ic_confession)
        }
    }

    private fun showPremiumCar(entitled: Boolean) {
        if (entitled) {
            free_or_premium_car.setImageResource(R.drawable.ic_confession)
        } else {
            free_or_premium_car.setImageResource(R.drawable.ic_confession)
        }
    }

    private fun showGoldStatus(entitled: Boolean) {
        if (entitled) {
            gold_status.setBackgroundResource(R.drawable.ic_confession)
        } else {
            gold_status.setBackgroundResource(0)
        }
    }

}
