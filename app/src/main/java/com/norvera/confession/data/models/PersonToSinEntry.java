package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PersonToSinEntry {

    public PersonToSinEntry(@NonNull String id, String PERSON_ID,
                            String SINS_ID,
                            String COUNT,
                            String EDITED,
                            String DELETED) {
        _id = id;
        this.PERSON_ID = PERSON_ID;
        this.SINS_ID = SINS_ID;
        this.COUNT = COUNT;
        this.EDITED = EDITED;
        this.DELETED = DELETED;
    }
    @PrimaryKey
    @NonNull
    public final String _id;

    @ColumnInfo(name="PERSON_ID")
    public final String PERSON_ID;

    @ColumnInfo(name="SINS_ID")
    public final String SINS_ID;

    @ColumnInfo(name="COUNT")
    public final String COUNT;

    @ColumnInfo(name="EDITED")
    public final String EDITED;

    @ColumnInfo(name="DELETED")
    public final String DELETED;
}
