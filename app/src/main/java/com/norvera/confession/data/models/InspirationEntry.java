package com.norvera.confession.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "INSPIRATION" )
public class InspirationEntry {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public final long id;

    @ColumnInfo(name="QUOTE")
    public final String text;

    @ColumnInfo(name="AUTHOR")
    public final String author;
    public InspirationEntry(long id, String text, String author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }
}
