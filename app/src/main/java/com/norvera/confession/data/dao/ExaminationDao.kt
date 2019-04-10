package com.norvera.confession.data.dao

import com.norvera.confession.data.models.ExaminationActiveEntry
import com.norvera.confession.data.models.ExaminationEntry

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class ExaminationDao {

    @RawQuery(observedEntities = [ExaminationEntry::class])
    abstract fun loadAllExaminationsForCommandmentAndUser(query: SupportSQLiteQuery): LiveData<List<ExaminationEntry>>

    @Update
    abstract fun updateEntry(examinationEntry: ExaminationEntry)

    @Query("SELECT _id, DESCRIPTION FROM SIN_ACTIVE WHERE _id IN (SELECT _id FROM SIN WHERE COUNT > 0)")
    abstract fun loadAllExaminationsWithCount(): LiveData<List<ExaminationActiveEntry>>

    //todo implement using id and direct edit
    @Update
    abstract fun decrementCount(examinationEntry: ExaminationEntry)
}
