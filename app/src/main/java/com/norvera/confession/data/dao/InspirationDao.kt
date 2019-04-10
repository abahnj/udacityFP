package com.norvera.confession.data.dao

import com.norvera.confession.data.models.InspirationEntry

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class InspirationDao {
    @Query("SELECT * FROM INSPIRATION WHERE _id = :id")
    abstract fun getInspirationForId(id: Int): LiveData<InspirationEntry>
}
