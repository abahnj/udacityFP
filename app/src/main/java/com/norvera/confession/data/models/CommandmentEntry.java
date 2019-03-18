package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "COMMANDMENTS")
public class CommandmentEntry {
    // column names commandment table's columns

    public CommandmentEntry(long id, int number, String text, String category, String commandment,
                            int customId) {
        this.id = id;
        this.number = number;
        this.text = text;
        this.category = category;
        this.commandment = commandment;
        this.customId = customId;
    }

    @PrimaryKey
    @ColumnInfo(name = "_id")
    public final long id;


    @ColumnInfo(name = "NUMBER")
    public final int number;

    @ColumnInfo(name = "TEXT")
    public final String text;

    @ColumnInfo(name = "CATEGORY")
    public final String category;

    @ColumnInfo(name = "COMMANDMENT")
    public final String commandment;

    @ColumnInfo(name = "CUSTOM_ID")
    public final int customId;

}
