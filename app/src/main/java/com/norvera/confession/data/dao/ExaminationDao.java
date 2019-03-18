package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

@Dao
public abstract class ExaminationDao {

    @RawQuery(observedEntities = ExaminationEntry.class)
    public abstract LiveData<List<ExaminationEntry>> loadAllExaminationsForCommandmentAndUser(SupportSQLiteQuery query);

    @Update
    public abstract void updateEntry(ExaminationEntry examinationEntry);

    @Query("SELECT _id, DESCRIPTION FROM SIN_ACTIVE WHERE _id IN (SELECT _id FROM SIN WHERE COUNT > 0)")
    public abstract LiveData<List<ExaminationActiveEntry>> loadAllExaminationsWithCount();

    @Update
    public abstract void decrementCount(ExaminationEntry examinationEntry);
}
