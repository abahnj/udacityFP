package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PrayersEntry {
    @PrimaryKey
    @NonNull
    public final String _id;

    public PrayersEntry(@NonNull String id) {
        _id = id;
    }
}
