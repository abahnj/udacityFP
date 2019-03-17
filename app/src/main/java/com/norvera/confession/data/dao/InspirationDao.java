package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.InspirationEntry;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class InspirationDao {
    @Query("SELECT * FROM INSPIRATION WHERE _id = :id")
    public abstract LiveData<InspirationEntry> getInspirationForId(int id);
}
