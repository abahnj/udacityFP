package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ExaminationDao {

    @Query("SELECT * FROM SIN WHERE COMMANDMENT_ID = :commandmentId")
    public abstract LiveData<List<ExaminationEntry>> loadAllExaminationsForCommandment(long commandmentId);

    @Update
    public abstract void updateEntry(ExaminationEntry examinationEntry);

    @Query("SELECT _id, DESCRIPTION FROM SIN_ACTIVE WHERE _id IN (SELECT _id FROM SIN WHERE COUNT > 0)")
    public abstract LiveData<List<ExaminationActiveEntry>> loadAllExaminationsWithCount();

}
