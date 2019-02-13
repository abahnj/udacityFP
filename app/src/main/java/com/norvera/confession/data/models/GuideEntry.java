package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GuideEntry {
    @PrimaryKey
    public final long _id;

    public GuideEntry(@NonNull long id) {
        _id = id;
    }
}
