package com.norvera.confession.data.models;

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
    private final int number;
    @ColumnInfo(name = "CATEGORY")
    private final String category;
    @ColumnInfo(name = "CUSTOM_ID")
    private final int customId;

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    @ColumnInfo(name = "TEXT")
    public final String text;

    public String getCommandment() {
        return commandment;
    }

    @ColumnInfo(name = "COMMANDMENT")
    public final String commandment;

    public int getCustomId() {
        return customId;
    }

}
