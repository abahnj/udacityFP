package com.norvera.confession.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PersonToSinEntry {
    @PrimaryKey
    private final long _id;
    @ColumnInfo(name="PERSON_ID")
    private final String PERSON_ID;
    @ColumnInfo(name="SINS_ID")
    private final String SINS_ID;
    @ColumnInfo(name="COUNT")
    private final String COUNT;
    @ColumnInfo(name="EDITED")
    private final String EDITED;
    @ColumnInfo(name="DELETED")
    private final String DELETED;

    public PersonToSinEntry(long id, String PERSON_ID,
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

    public long get_id() {
        return _id;
    }

    public String getPERSON_ID() {
        return PERSON_ID;
    }

    public String getSINS_ID() {
        return SINS_ID;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public String getEDITED() {
        return EDITED;
    }

    public String getDELETED() {
        return DELETED;
    }
}
