package com.norvera.confession.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PRAYERS")
public class PrayersEntry implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public final long id;

    public PrayersEntry(long id, String prayerName, String prayerText, String groupName, String custom) {
        this.id = id;
        this.prayerName = prayerName;
        this.prayerText = prayerText;
        this.groupName = groupName;
        this.custom = custom;
    }

    @ColumnInfo(name = "PRAYERNAME")
    public final String prayerName;

    @ColumnInfo(name = "PRAYERTEXT")
    private final String prayerText;
    @ColumnInfo(name = "GROUPNAME")
    private final String groupName;
    @ColumnInfo(name = "CUSTOM")
    private final String custom;

    public static Creator<PrayersEntry> getCREATOR() {
        return CREATOR;
    }

    public long getId() {
        return id;
    }

    public String getPrayerName() {
        return prayerName;
    }

    public String getPrayerText() {
        return prayerText;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getCustom() {
        return custom;
    }

    protected PrayersEntry(Parcel in) {
        id = in.readLong();
        prayerName = in.readString();
        prayerText = in.readString();
        groupName = in.readString();
        custom = in.readString();
    }

    public static final Creator<PrayersEntry> CREATOR = new Creator<PrayersEntry>() {
        @Override
        public PrayersEntry createFromParcel(Parcel in) {
            return new PrayersEntry(in);
        }

        @Override
        public PrayersEntry[] newArray(int size) {
            return new PrayersEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(prayerName);
        dest.writeString(prayerText);
        dest.writeString(groupName);
        dest.writeString(custom);
    }
}
