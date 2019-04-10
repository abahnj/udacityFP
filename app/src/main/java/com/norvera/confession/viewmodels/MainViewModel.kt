package com.norvera.confession.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.beautycoder.pflockscreen.security.PFResult
import com.beautycoder.pflockscreen.security.PFSecurityManager
import com.norvera.confession.data.AppRepository
import com.norvera.confession.data.models.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel internal constructor(private val appRepository: AppRepository) : ViewModel() {


    var examinationEntries = ObservableField<List<ExaminationEntry>>()
    private val liveData = MutableLiveData<PFResult<Boolean>>()//PFLiveData<PFResult<Boolean>>()

    init {
        Timber.d("Actively retrieving the movies from the DataBase")
    }
    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [PlantDetailViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    //private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */

    fun allCommandmentsForLay(): LiveData<List<CommandmentEntry>> {
        return appRepository.loadCommandmentsForLayPerson()
    }

    fun allCommandmentsForReligious(): LiveData<List<CommandmentEntry>> {
        return appRepository.loadCommandmentsForReligious()
    }

    fun allExaminationsForCommandmentAndUser(query: SimpleSQLiteQuery): LiveData<List<ExaminationEntry>> {
        return appRepository.loadAllExaminationsForCommandmentAndUser(query)
    }

    fun updateCountForEntry(examinationEntry: ExaminationEntry) {
        viewModelScope.launch {
            appRepository.updateCountForEntry(examinationEntry)
        }
    }

    fun allGuidesForId(guideId: Int): LiveData<List<GuideEntry>> {
        return appRepository.allGuidesForId(guideId)
    }

    fun loadAllPrayers(): LiveData<List<PrayersEntry>> {
        return appRepository.loadAllPrayers()
    }

    fun allExaminationsWithCount(): LiveData<List<ExaminationActiveEntry>> {
        return appRepository.loadAllExaminationsWithCount()
    }

    fun getInspirationForId(id: Int): LiveData<InspirationEntry> {
        return appRepository.getInspirationForId(id)
    }

    fun decrementCount(examinationEntry: ExaminationEntry) {
        viewModelScope.launch {
            appRepository.decrementCountForEntry(examinationEntry)
        }
    }

    fun isPinCodeEncryptionKeyExist(): LiveData<PFResult<Boolean>> {
        PFSecurityManager.getInstance().pinCodeHelper.isPinCodeEncryptionKeyExist { liveData.value = it }
        return liveData
    }


    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}
