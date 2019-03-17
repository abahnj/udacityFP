package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.CommandmentEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public abstract class CommandmentDao {

    @Query("SELECT * FROM COMMANDMENTS WHERE NUMBER == 0")
    public abstract LiveData<List<CommandmentEntry>> loadCommandmentsForReligious();

    @Query("SELECT * FROM COMMANDMENTS WHERE NUMBER > 0")
    public abstract LiveData<List<CommandmentEntry>> loadCommandmentsForLayPerson();

    @Query("SELECT * FROM COMMANDMENTS WHERE _id = :id")
    public abstract LiveData<CommandmentEntry> selectCommandmentById(long id);

    @Insert
    public abstract void addCommandment(CommandmentEntry commandmentEntry);
}
