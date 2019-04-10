package com.norvera.confession.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.norvera.confession.data.models.CommandmentEntry

@Dao
abstract class CommandmentDao {

    @Query("SELECT * FROM COMMANDMENTS WHERE NUMBER == 0")
    abstract fun loadCommandmentsForReligious(): LiveData<List<CommandmentEntry>>

    @Query("SELECT * FROM COMMANDMENTS WHERE NUMBER > 0")
    abstract fun loadCommandmentsForLayPerson(): LiveData<List<CommandmentEntry>>

    @Query("SELECT * FROM COMMANDMENTS WHERE _id = :id")
    abstract fun selectCommandmentById(id: Long): LiveData<CommandmentEntry>

    @Insert
    abstract fun addCommandment(commandmentEntry: CommandmentEntry)
}
