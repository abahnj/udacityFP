package com.norvera.confession.data.dao

import com.norvera.confession.data.models.GuideEntry

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class GuideDao {

    @Query("SELECT * FROM  GUIDE_MAIN WHERE h_id = :guideId")
    abstract fun allGuidesForId(guideId: Int): LiveData<List<GuideEntry>>

}
