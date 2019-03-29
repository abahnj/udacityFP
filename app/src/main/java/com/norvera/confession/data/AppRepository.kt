package com.norvera.confession.data

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.norvera.confession.data.models.*
import com.norvera.confession.utils.AppExecutor
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class AppRepository(private val appDatabase: AppDatabase, private val executor: AppExecutor) {

    fun loadCommandmentsForReligious(): LiveData<List<CommandmentEntry>> {
        return appDatabase.commandmentDao().loadCommandmentsForReligious()
    }

    fun loadCommandmentsForLayPerson(): LiveData<List<CommandmentEntry>> {
        return appDatabase.commandmentDao().loadCommandmentsForLayPerson()
    }

    fun loadCommandmentById(id: Long): LiveData<CommandmentEntry> {
        return appDatabase.commandmentDao().selectCommandmentById(id)
    }

    fun addCommandment(commandmentEntry: CommandmentEntry) {
        executor.diskIO().execute { appDatabase.commandmentDao().addCommandment(commandmentEntry) }
    }


    fun loadAllExaminationsForCommandmentAndUser(query: SimpleSQLiteQuery): LiveData<List<ExaminationEntry>> {
        return appDatabase.examinationDao().loadAllExaminationsForCommandmentAndUser(query)
    }

    suspend fun updateCountForEntry(examinationEntry: ExaminationEntry) {
        withContext(IO) {
            appDatabase.examinationDao().updateEntry(examinationEntry)
        }
    }

    fun allGuidesForId(guideId: Int): LiveData<List<GuideEntry>> {
        return appDatabase.guideDao().allGuidesForId(guideId)
    }

    fun loadAllPrayers(): LiveData<List<PrayersEntry>> {
        return appDatabase.prayersDao().loadAllPrayers()
    }

    fun loadAllExaminationsWithCount(): LiveData<List<ExaminationActiveEntry>> {
        return appDatabase.examinationDao().loadAllExaminationsWithCount()
    }

    fun getInspirationForId(id: Int): LiveData<InspirationEntry> {
        return appDatabase.inspirationDao().getInspirationForId(id)
    }

    suspend fun decrementCountForEntry(examinationEntry: ExaminationEntry) {
        withContext(IO) {
            appDatabase.examinationDao().decrementCount(examinationEntry)
        }
    }
}
