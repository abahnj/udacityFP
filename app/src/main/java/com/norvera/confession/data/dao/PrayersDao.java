package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.PrayersEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class PrayersDao {
    @Query("SELECT * FROM  PRAYERS")
    public abstract LiveData<List<PrayersEntry>> loadAllPrayers();
}
