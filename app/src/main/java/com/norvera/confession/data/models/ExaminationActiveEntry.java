package com.norvera.confession.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SIN_ACTIVE")
public class ExaminationActiveEntry {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private long id;

    @ColumnInfo(name = "DESCRIPTION")
    private String description;

    public ExaminationActiveEntry(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
