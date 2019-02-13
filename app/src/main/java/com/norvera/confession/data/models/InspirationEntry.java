package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InspirationEntry {
    @PrimaryKey
    public final long _id;

    public InspirationEntry(@NonNull long id) {
        _id = id;
    }
}
