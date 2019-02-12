package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InspirationEntry {
    @PrimaryKey
    @NonNull
    public final String _id;

    public InspirationEntry(@NonNull String id) {
        _id = id;
    }
}
