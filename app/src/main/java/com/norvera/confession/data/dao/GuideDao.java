package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.GuideEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class GuideDao {

    @Query("SELECT * FROM  GUIDE_MAIN WHERE h_id = :guideId")
    public abstract LiveData<List<GuideEntry>> allGuidesForId(int guideId);

}
