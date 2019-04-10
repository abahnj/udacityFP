package com.norvera.confession.ui.purchases

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norvera.confession.R
import com.norvera.confession.viewmodels.BillingViewModel
import com.norvera.confession.data.billingrepo.BillingRepository
import com.norvera.confession.data.billingrepo.localdb.AugmentedSkuDetails
import kotlinx.android.synthetic.main.fragment_make_purchase.view.*
import timber.log.Timber

/**
* This Fragment is simply a wrapper for the inventory (i.e. items for sale). It contains two
* [lists][RecyclerView], one for subscriptions and one for in-app products. Here again there is
* no complicated billing logic. All the billing logic reside inside the [BillingRepository].
* The [BillingRepository] provides a [AugmentedSkuDetails] object that shows what
* is for sale and whether the user is allowed to buy the item at this moment. E.g. if the user
* already has a full tank of gas, then they cannot buy gas at this moment.
*/
class MakePurchases : Fragment() {

    val LOG_TAG = "MakePurchaseFragment"
    private lateinit var billingViewModel: BillingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_make_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d( "onViewCreated")

        val inAppAdapter = object : InventoryAdapter() {
            override fun onSkuDetailsClicked(item: AugmentedSkuDetails) {
                onPurchase(view, item)
            }
        }

        val subsAdapter = object : InventoryAdapter() {
            override fun onSkuDetailsClicked(item: AugmentedSkuDetails) {
                onPurchase(view, item)
            }
        }
        attachAdapterToRecyclerView(view.inapp_inventory, inAppAdapter)
        attachAdapterToRecyclerView(view.subs_inventory, subsAdapter)

        billingViewModel = ViewModelProviders.of(this).get(BillingViewModel::class.java)
        billingViewModel.inAppSkuDetailsListLiveData.observe(this, Observer {
            it?.let {
                inAppAdapter.setSkuDetailsList(it)
            }
        })
        billingViewModel.subsSkuDetailsListLiveData.observe(this, Observer {
            it?.let { subsAdapter.setSkuDetailsList(it) }
        })

    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, skuAdapter: InventoryAdapter) {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = skuAdapter
        }
    }

    private fun onPurchase(view: View, item: AugmentedSkuDetails) {
        billingViewModel.makePurchase(activity as Activity, item)
        view.findNavController().navigate(R.id.commandment_fragment)
        Timber.d( "starting purchase flow for SkuDetail:\n $item")
    }
}
