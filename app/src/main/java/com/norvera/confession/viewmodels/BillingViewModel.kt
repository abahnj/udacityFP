package com.norvera.confession


import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.billingclient.api.BillingClient
import com.norvera.confession.data.billingrepo.BillingRepository
import com.norvera.confession.data.billingrepo.localdb.AugmentedSkuDetails
import com.norvera.confession.data.billingrepo.localdb.GasTank
import com.norvera.confession.data.billingrepo.localdb.GoldStatus
import com.norvera.confession.data.billingrepo.localdb.PremiumCar
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Notice just how small and simple this BillingViewModel is!!
 *
 * This beautiful simplicity is the result of keeping all the hard work buried
 * inside the [BillingRepository] and only inside the [BillingRepository]. The
 * rest of your app is now free from [BillingClient] tentacles!! And this
 * [BillingViewModel] is the one and only object the rest of your Android team
 * need to know about billing.
 *
 */
class BillingViewModel(application: Application) : AndroidViewModel(application) {

    val gasTankLiveData: LiveData<GasTank>
    val premiumCarLiveData: LiveData<PremiumCar>
    val goldStatusLiveData: LiveData<GoldStatus>
    val subsSkuDetailsListLiveData: LiveData<List<AugmentedSkuDetails>>
    val inAppSkuDetailsListLiveData: LiveData<List<AugmentedSkuDetails>>

    private val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)
    private val repository: BillingRepository = BillingRepository.getInstance(application)

    init {
        repository.startDataSourceConnections()
        gasTankLiveData = repository.gasTankLiveData
        premiumCarLiveData = repository.premiumCarLiveData
        goldStatusLiveData = repository.goldStatusLiveData
        subsSkuDetailsListLiveData = repository.subsSkuDetailsListLiveData
        inAppSkuDetailsListLiveData = repository.inAppSkuDetailsListLiveData
    }

    /**
     * Not used in this sample app. But you may want to force refresh in your
     * own app (e.g. pull-to-refresh)
     */
    fun queryPurchases() = repository.queryPurchasesAsync()

    override fun onCleared() {
        super.onCleared()
        Timber.d( "onCleared")
        repository.endDataSourceConnections()
        viewModelScope.coroutineContext.cancel()
    }

    fun makePurchase(activity: Activity, augmentedSkuDetails: AugmentedSkuDetails) {
        repository.launchBillingFlow(activity, augmentedSkuDetails)
    }

    /**
     * It's important to save after decrementing since gas can be updated by
     * both clients and the data sources.
     *
     * Note that even the ViewModel does not need to worry about thread safety
     * because the repo has already taken care it. So definitely the clients
     * also don't need to worry about thread safety.
     */
    fun decrementAndSaveGas() {
        val gas = gasTankLiveData.value
        gas?.apply {
            decrement()
            viewModelScope.launch {
                repository.updateGasTank(this@apply)
            }
        }
    }
}