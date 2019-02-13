package com.norvera.confession.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PrayersEntry {
    @PrimaryKey
    public final long _id;

    public PrayersEntry( long id) {
        _id = id;
    }
}
