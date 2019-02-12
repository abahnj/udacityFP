package com.norvera.confession.data.dao;

import com.norvera.confession.data.models.CommandmentEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public abstract class CommandmentDao {

    @Query("SELECT * FROM commandments")
    public abstract LiveData<List<CommandmentEntry>> selectAll();

    @Query("SELECT * FROM COMMANDMENTS WHERE _id = :id")
    public abstract LiveData<CommandmentEntry> selectCommandmentById(long id);

    @Insert
    public abstract Integer addCommandment(CommandmentEntry commandmentEntry);
}
