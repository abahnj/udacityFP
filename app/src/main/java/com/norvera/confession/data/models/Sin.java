package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SIN")
public class Sin {
    @PrimaryKey
    @NonNull
    public final String _id;

    public Sin(@NonNull String _id) {
        this._id = _id;
    }
}
