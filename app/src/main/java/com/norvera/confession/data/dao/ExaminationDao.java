package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.ExaminationEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public abstract class ExaminationDao {

    @Query("SELECT * FROM SIN WHERE COMMANDMENT_ID = :commandmentId")
    public abstract LiveData<List<ExaminationEntry>> loadAllExaminationsForCommandment(long commandmentId);

}
