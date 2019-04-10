package com.norvera.confession.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.norvera.confession.data.models.PrayersEntry

@Dao
abstract class PrayersDao {
    @Query("SELECT * FROM  PRAYERS")
    abstract fun loadAllPrayers(): LiveData<List<PrayersEntry>>
}
