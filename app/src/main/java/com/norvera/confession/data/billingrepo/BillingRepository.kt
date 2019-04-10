package com.norvera.confession.data.billingrepo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.*
import com.norvera.confession.data.billingrepo.BillingRepository.ConfessionSku.CONSUMABLE_SKUS
import com.norvera.confession.data.billingrepo.BillingRepository.ConfessionSku.GOLD_STATUS_SKUS
import com.norvera.confession.data.billingrepo.BillingRepository.ConfessionSku.INAPP_SKUS
import com.norvera.confession.data.billingrepo.BillingRepository.ConfessionSku.SUBS_SKUS
import com.norvera.confession.data.billingrepo.BillingRepository.RetryPolicies.connectionRetryPolicy
import com.norvera.confession.data.billingrepo.BillingRepository.RetryPolicies.resetConnectionRetryPolicyCounter
import com.norvera.confession.data.billingrepo.BillingRepository.RetryPolicies.taskExecutionRetryPolicy
import com.norvera.confession.data.billingrepo.BillingRepository.Throttle.isLastInvocationTimeStale
import com.norvera.confession.data.billingrepo.BillingRepository.Throttle.refreshLastInvocationTime
import com.norvera.confession.data.billingrepo.localdb.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

class BillingRepository private constructor(private val application: Application) :
    PurchasesUpdatedListener, BillingClientStateListener,
    ConsumeResponseListener, SkuDetailsResponseListener {

    companion object {
        private const val LOG_TAG = "BillingRepository"

        @Volatile
        private var INSTANCE: BillingRepository? = null

        fun getInstance(application: Application): BillingRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: BillingRepository(application)
                        .also { INSTANCE = it }
            }
    }

    private lateinit var playStoreBillingClient: BillingClient

    private lateinit var secureServerBillingClient: BillingWebservice

    private lateinit var localCacheBillingClient: LocalBillingDb

    val subsSkuDetailsListLiveData: LiveData<List<AugmentedSkuDetails>> by lazy {
        if (!::localCacheBillingClient.isInitialized) {
            localCacheBillingClient = LocalBillingDb.getInstance(application)
        }
        localCacheBillingClient.skuDetailsDao().getSubscriptionSkuDetails()
    }

    val inAppSkuDetailsListLiveData: LiveData<List<AugmentedSkuDetails>> by lazy {
        if (!::localCacheBillingClient.isInitialized) {
            localCacheBillingClient = LocalBillingDb.getInstance(application)
        }
        localCacheBillingClient.skuDetailsDao().getInappSkuDetails()
    }

    val gasTankLiveData: LiveData<GasTank> by lazy {
        if (!::localCacheBillingClient.isInitialized) {
            localCacheBillingClient = LocalBillingDb.getInstance(application)
        }
        localCacheBillingClient.entitlementsDao().getGasTank()
    }

    val premiumCarLiveData: LiveData<PremiumCar> by lazy {
        if (!::localCacheBillingClient.isInitialized) {
            localCacheBillingClient = LocalBillingDb.getInstance(application)
        }
        localCacheBillingClient.entitlementsDao().getPremiumCar()
    }

    val goldStatusLiveData: LiveData<GoldStatus> by lazy {
        if (!::localCacheBillingClient.isInitialized) {
            localCacheBillingClient = LocalBillingDb.getInstance(application)
        }
        localCacheBillingClient.entitlementsDao().getGoldStatus()
    }

    fun startDataSourceConnections() {
        Timber.d("startDataSourceConnections")
        instantiateAndConnectToPlayBillingService()
        secureServerBillingClient = BillingWebservice.create()
        localCacheBillingClient = LocalBillingDb.getInstance(application)
    }

    fun endDataSourceConnections() {
        playStoreBillingClient.endConnection()
        // normally you don't worry about closing a DB connection unless you have
        //more than one open. so no need to call 'localCacheBillingClient.close()'
        Timber.d("endDataSourceConnections")
    }


    private fun instantiateAndConnectToPlayBillingService() {
        playStoreBillingClient = newBuilder(application.applicationContext)
            .setListener(this).build()
        connectToPlayBillingService()
    }

    private fun connectToPlayBillingService(): Boolean {
        Timber.d("connectToPlayBillingService")
        if (!playStoreBillingClient.isReady) {
            playStoreBillingClient.startConnection(this)
            return true
        }
        return false
    }

    /**
     * Implement this method to get notifications for purchases updates. Both
     * purchases initiated by your app and the ones initiated by Play Store
     * will be reported here.
     *
     * @param responseCode Response code of the update.
     * @param purchases List of updated purchases if present.
     */
    @SuppressLint("SwitchIntDef")
    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        when (responseCode) {
            BillingResponse.OK -> {
                // will handle server verification, consumables, and updating the local cache
                purchases?.apply { processPurchases(toSet()) }
            }
            BillingResponse.ITEM_ALREADY_OWNED -> {
                //item already owned? call queryPurchasesAsync to verify and process all such items
                Timber.d("already owned items")
                queryPurchasesAsync()
            }
            BillingResponse.DEVELOPER_ERROR -> {
                Timber.e("Your app's configuration is incorrect. Review in the Google PlayConsole. Possible causes of this error include: APK is not signed with release key; SKU productId mismatch.")
            }
            BillingResponse.SERVICE_DISCONNECTED -> {
                connectionRetryPolicy { connectToPlayBillingService() }
            }
            else -> {
                Timber.i("BillingClient.BillingResponse error code: $responseCode")
            }
        }
    }

    /**
     * Called to notify that connection to billing service was lost
     *
     *
     * Note: This does not remove billing service connection itself - this
     * binding to the service
     * will remain active, and you will receive a call to
     * [.onBillingSetupFinished] when billing
     * service is next running and setup is complete.
     */
    override fun onBillingServiceDisconnected() {
        Timber.d("onBillingServiceDisconnected")
        connectionRetryPolicy { connectToPlayBillingService() }
    }

    /**
     * Called to notify that setup is complete.
     *
     * @param responseCode The response code from [BillingResponse] which returns the status of
     * the setup process.
     */
    @SuppressLint("SwitchIntDef")
    override fun onBillingSetupFinished(responseCode: Int) {
        when (responseCode) {
            BillingResponse.OK -> {
                Timber.d("onBillingSetupFinished successfully")
                resetConnectionRetryPolicyCounter()//for retry policy
                querySkuDetailsAsync(SkuType.INAPP, ConfessionSku.INAPP_SKUS)
                querySkuDetailsAsync(SkuType.SUBS, ConfessionSku.SUBS_SKUS)
                queryPurchasesAsync()
            }
            BillingResponse.BILLING_UNAVAILABLE -> {
                //Some apps may choose to make decisions based on this knowledge.
                Timber.d("onBillingSetupFinished but billing is not available on this device")
            }
            else -> {
                //do nothing. Someone else will connect it through retry policy.
                //May choose to send to server though
                Timber.d("onBillingSetupFinished with failure response code: $responseCode")
            }
        }
    }


    private fun querySkuDetailsAsync(@BillingClient.SkuType skuType: String, skuList: List<String>) {
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(skuType)
        taskExecutionRetryPolicy(playStoreBillingClient, this) {
            Timber.d("querySkuDetailsAsync for $skuType")
            playStoreBillingClient.querySkuDetailsAsync(params.build(), this)
        }
    }

    /**
     * Called to notify that a fetch SKU details operation has finished.
     *
     * @param responseCode Response code of the update.
     * @param skuDetailsList List of SKU details.
     */
    override fun onSkuDetailsResponse(responseCode: Int, skuDetailsList: MutableList<SkuDetails>?) {
        if (responseCode != BillingResponse.OK) {
            Timber.w("SkuDetails query failed with response: $responseCode")
        } else {
            Timber.d("SkuDetails query responded with success. List: $skuDetailsList")
        }

        if (skuDetailsList.orEmpty().isNotEmpty()) {
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                skuDetailsList?.forEach {
                    localCacheBillingClient.skuDetailsDao().insertOrUpdate(it)
                }
            }
        }
    }

    fun queryPurchasesAsync() {
        fun task() {
            Timber.d("queryPurchasesAsync called")
            val purchasesResult = HashSet<Purchase>()
            var result = playStoreBillingClient.queryPurchases(SkuType.INAPP)
            Timber.d("queryPurchasesAsync in app results: ${result?.purchasesList}")
            result?.purchasesList?.apply { purchasesResult.addAll(this) }
            if (isSubscriptionSupported()) {
                result = playStoreBillingClient.queryPurchases(SkuType.SUBS)
                result?.purchasesList?.apply { purchasesResult.addAll(this) }
                Timber.d( "queryPurchasesAsync SUBS results: ${result?.purchasesList}")
            }
            processPurchases(purchasesResult)
        }
        taskExecutionRetryPolicy(playStoreBillingClient, this) { task() }
    }

    private fun processPurchases(purchasesResult: Set<Purchase>) =
        CoroutineScope(Job() + Dispatchers.IO).launch {
            val cachedPurchases = localCacheBillingClient.purchaseDao().getPurchases()
            val newBatch = HashSet<Purchase>(purchasesResult.size)
            purchasesResult.forEach { purchase ->
                if (isSignatureValid(purchase) && !cachedPurchases.any { it.data == purchase }) {
                    //todo !cachedPurchases.contains(purchase)
                    newBatch.add(purchase)
                }
            }

            if (newBatch.isNotEmpty()) {
                sendPurchasesToServer(newBatch)
                // We still care about purchasesResult in case a old purchase has not
                // yet been consumed.
                saveToLocalDatabase(newBatch, purchasesResult)
                //consumeAsync(purchasesResult): do this inside saveToLocalDatabase to
                // avoid race condition
            } else if (isLastInvocationTimeStale(application)) {
                handleConsumablePurchasesAsync(purchasesResult)
                queryPurchasesFromSecureServer()
            }
        }

    private fun isSubscriptionSupported(): Boolean {
        val responseCode = playStoreBillingClient.isFeatureSupported(FeatureType.SUBSCRIPTIONS)
        if (responseCode != BillingResponse.OK) {
            Timber.w("isSubscriptionSupported() got an error response: $responseCode")
        }
        return responseCode == BillingResponse.OK
    }

    private fun isSignatureValid(purchase: Purchase): Boolean {
        return Security.verifyPurchase(
            Security.BASE_64_ENCODED_PUBLIC_KEY,
            purchase.originalJson,
            purchase.signature
        )
    }

    private fun saveToLocalDatabase(newBatch: Set<Purchase>, allPurchases: Set<Purchase>) {
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            newBatch.forEach { purchase ->
                when (purchase.sku) {
                    ConfessionSku.PREMIUM -> {
                        val premiumCar = PremiumCar(true)
                        insert(premiumCar)
                        localCacheBillingClient.skuDetailsDao()
                            .insertOrUpdate(purchase.sku, premiumCar.mayPurchase())
                    }
                    ConfessionSku.GOLD_MONTHLY, ConfessionSku.GOLD_YEARLY -> {
                        val goldStatus = GoldStatus(true)
                        insert(goldStatus)
                        localCacheBillingClient.skuDetailsDao()
                            .insertOrUpdate(purchase.sku, goldStatus.mayPurchase())
                        GOLD_STATUS_SKUS.forEach { otherSku ->
                            if (otherSku != purchase.sku) {
                                localCacheBillingClient.skuDetailsDao()
                                    .insertOrUpdate(otherSku, !goldStatus.mayPurchase())
                            }
                        }
                    }
                }
            }
            localCacheBillingClient.purchaseDao().insert(*newBatch.toTypedArray())
            handleConsumablePurchasesAsync(allPurchases)
        }
    }

    private fun saveToLocalDatabase(purchaseToken: String) {
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            val cachedPurchases = localCacheBillingClient.purchaseDao().getPurchases()
            val match = cachedPurchases.find { it.purchaseToken == purchaseToken }
            if (match?.sku == ConfessionSku.GAS) {
                updateGasTank(GasTank(GAS_PURCHASE))
                localCacheBillingClient.purchaseDao().delete(match)
            }
        }
    }

    private fun handleConsumablePurchasesAsync(purchases: Set<Purchase>) {
        purchases.forEach {
            if (ConfessionSku.CONSUMABLE_SKUS.contains(it.sku)) {
                playStoreBillingClient.consumeAsync(it.purchaseToken, this@BillingRepository)
                //tell your server:
                Timber.i("handleConsumablePurchasesAsync: asked Play Billing to consume sku = ${it.sku}")
            }
        }
    }

    @WorkerThread
    suspend fun updateGasTank(gas: GasTank) = withContext(Dispatchers.IO) {
        Timber.d("updateGasTank")
        var update: GasTank = gas
        gasTankLiveData.value?.apply {
            synchronized(this) {
                if (this != gas) {//new purchase
                    update = GasTank(getLevel() + gas.getLevel())
                }
                Timber.d("New purchase level is ${gas.getLevel()}; existing level is ${getLevel()}; so the final result is ${update.getLevel()}")
                localCacheBillingClient.entitlementsDao().update(update)
            }
        }
        if (gasTankLiveData.value == null) {
            localCacheBillingClient.entitlementsDao().insert(update)
            Timber.d("No we just added from null gas with level: ${gas.getLevel()}")
        }
        localCacheBillingClient.skuDetailsDao()
            .insertOrUpdate(ConfessionSku.GAS, update.mayPurchase())
        Timber.d("updated AugmentedSkuDetails as well")
    }

    @WorkerThread
    private suspend fun insert(entitlement: Entitlement) = withContext(Dispatchers.IO) {
        localCacheBillingClient.entitlementsDao().insert(entitlement)
    }

    private fun sendPurchasesToServer(purchases: Set<Purchase>) {
        //not implemented here
    }

    private fun queryPurchasesFromSecureServer() {
        fun getPurchasesFromSecureServerToLocalDB() {//closure
            //do the actual work of getting the purchases from server
        }
        getPurchasesFromSecureServerToLocalDB()

        refreshLastInvocationTime(application)
    }

    /**
     * Called to notify that a consume operation has finished.
     *
     * @param responseCode The response code from [BillingResponse] set to report the result of
     * consume operation.
     * @param purchaseToken The purchase token that was (or was to be) consumed.
     */
    /**
     * This is the callback for [BillingClient.consumeAsync]. It's called by [playStoreBillingClient] to notify
     *  that a consume operation has finished.
     * Appropriate action should be taken in the app, such as add fuel to user's
     * car. This information should also be saved on the secure server in case user
     * accesses the app through another device.
     */
    @SuppressLint("SwitchIntDef")
    override fun onConsumeResponse(responseCode: Int, purchaseToken: String?) {
        Timber.d("onConsumeResponse")
        when (responseCode) {
            BillingResponse.OK -> {
                //give user the items s/he just bought by updating the appropriate tables/databases
                purchaseToken?.apply { saveToLocalDatabase(this) }
                secureServerBillingClient.onComsumeResponse(purchaseToken, responseCode)
            }
            else -> {
                Timber.w("Error consuming purchase with token ($purchaseToken). Response code: $responseCode")
            }
        }
    }


    fun launchBillingFlow(activity: Activity, augmentedSkuDetails: AugmentedSkuDetails) =
        launchBillingFlow(activity, SkuDetails(augmentedSkuDetails.originalJson))

    fun launchBillingFlow(activity: Activity, skuDetails: SkuDetails) {
        val oldSku: String? = getOldSku(skuDetails.sku)
        val purchaseParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails)
            .setOldSku(oldSku).build()

        taskExecutionRetryPolicy(playStoreBillingClient, this) {
            playStoreBillingClient.launchBillingFlow(activity, purchaseParams)
        }
    }

    /**
     * This sample app only offers one item for subscription: GoldStatus. And there are two
     * ways a user can subscribe to GoldStatus: monthly or yearly. Hence it's easy for
     * the [BillingRepository] to get the old sku if one exists. You too should have no problem
     * knowing this fact about your app.
     *
     * We must here again reiterate. We don't want to make this sample app overwhelming. And so we
     * are introducing ideas piecewise. This sample focuses more on overall architecture.
     * So although we mention subscriptions, it is not about subscription per se. Classy Taxi is
     * the sample app that focuses on subscriptions.
     *
     */
    private fun getOldSku(sku: String?): String? {
        var result: String? = null
        if (ConfessionSku.SUBS_SKUS.contains(sku)) {
            goldStatusLiveData.value?.apply {
                result = when (sku) {
                    ConfessionSku.GOLD_MONTHLY -> ConfessionSku.GOLD_YEARLY
                    else -> ConfessionSku.GOLD_YEARLY
                }
            }
        }
        return result
    }

    /**
     * This private object class shows an example retry policies. You may choose to replace it with
     * your own policies.
     */
    private object RetryPolicies {
        private const val maxRetry = 5
        private var retryCounter = AtomicInteger(1)
        private const val baseDelayMillis = 500
        private const val taskDelay = 2000L

        fun resetConnectionRetryPolicyCounter() {
            retryCounter.set(1)
        }

        /**
         * This works because it actually only makes one call. Then it waits for success or failure.
         * onSuccess it makes no more calls and resets the retryCounter to 1. onFailure another
         * call is made, until too many failures cause retryCounter to reach maxRetry and the
         * policy stops trying. This is a safe algorithm: the initial calls to
         * connectToPlayBillingService from instantiateAndConnectToPlayBillingService is always
         * independent of the RetryPolicies. And so the Retry Policy exists only to help and never
         * to hurt.
         */

        fun connectionRetryPolicy(block: () -> Unit) {
            Timber.d("connectionRetryPolicy")
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                val counter = retryCounter.getAndIncrement()
                if (counter < maxRetry) {
                    val waitTime: Long = (2f.pow(counter) * baseDelayMillis).toLong()
                    delay(waitTime)
                    block()
                }
            }
        }

        /**
         * All this is doing is check that billingClient is connected and if it's
         * not, request connection, wait x number of seconds and then proceed with
         * the actual task.
         */
        fun taskExecutionRetryPolicy(
            billingClient: BillingClient,
            listener: BillingRepository,
            task: () -> Unit
        ) {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                if (!billingClient.isReady) {
                    Timber.d("taskExecutionRetryPolicy billing not ready")
                    billingClient.startConnection(listener)
                    delay(taskDelay)
                }
                task()
            }
        }
    }

    /**
     * This is the throttling valve. It is used to modulate how often calls are
     * made to the secure server in order to save money.
     */
    private object Throttle {
        private const val DEAD_BAND = 7200000//2*60*60*1000: two hours wait
        private const val PREFS_NAME = "BillingRepository.Throttle"
        private const val KEY = "lastInvocationTime"

        fun isLastInvocationTimeStale(context: Context): Boolean {
            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val lastInvocationTime = sharedPrefs.getLong(KEY, 0)
            return lastInvocationTime + DEAD_BAND < Date().time
        }

        fun refreshLastInvocationTime(context: Context) {
            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putLong(KEY, Date().time)
                apply()
            }
        }
    }

    /**
     * [INAPP_SKUS], [SUBS_SKUS], [CONSUMABLE_SKUS]:
     *
     * Where you define these lists is quite truly up to you. If you don't need
     * customization, then it makes since to define and hardcode them here, as
     * I am doing. Keep simple things simple. But there are use cases where you may
     * need customization:
     *
     * - If you don't want to update your APK (or Bundle) each time you change your
     *   SKUs, then you may want to load these lists from your secure server.
     *
     * - If your design is such that users can buy different items from different
     *   Activities or Fragments, then you may want to define a list for each of
     *   those subsets. I only have two subsets: INAPP_SKUS and SUBS_SKUS
     */

    private object ConfessionSku {
        const val GAS = "remove_ads"
        const val PREMIUM = "premium"
        const val GOLD_MONTHLY = "premium_month"
        const val GOLD_YEARLY = "premium_year"

        val INAPP_SKUS = listOf(GAS, PREMIUM)
        val SUBS_SKUS = listOf(GOLD_MONTHLY, GOLD_YEARLY)
        val CONSUMABLE_SKUS = listOf(GAS)
        //coincidence that there only gold_status is a sub
        val GOLD_STATUS_SKUS = SUBS_SKUS
    }

}