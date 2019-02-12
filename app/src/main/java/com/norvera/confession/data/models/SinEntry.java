package com.norvera.confession.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SIN")
public class SinEntry implements Parcelable {
    @PrimaryKey @NonNull
    public final String _id;

    public SinEntry(@NonNull String _id,
                    String commandmentId,
                    String adult,
                    String single,
                    String married,
                    String religious,
                    String priest,
                    String teen,
                    String female,
                    String male,
                    String child,
                    String customId,
                    String description) {
        this._id = _id;
        this.commandmentId = commandmentId;
        this.adult = adult;
        this.single = single;
        this.married = married;
        this.religious = religious;
        this.priest = priest;
        this.teen = teen;
        this.female = female;
        this.male = male;
        this.child = child;
        this.customId = customId;
        this.description = description;
    }

    @ColumnInfo(name="COMMANDMENT_ID")
    public final String commandmentId;
    
    @ColumnInfo(name="ADULT")
    public final String adult;

    @ColumnInfo(name="SINGLE")
    public final String single;
    
    @ColumnInfo(name="MARRIED")
    public final String married;

    @ColumnInfo(name="RELIGIOUS")
    public final String religious;

    @ColumnInfo(name="PRIEST")
    public final String priest;

    @ColumnInfo(name="TEEN")
    public final String teen;

    @ColumnInfo(name="FEMALE")
    public final String female;

    @ColumnInfo(name="MALE")
    public final String male;

    @ColumnInfo(name="CHILD")
    public final String child;

    @ColumnInfo(name="CUSTOM_ID")
    public final String customId;

    @ColumnInfo(name="DESCRIPTION")
    public final String description;


    protected SinEntry(Parcel in) {
        _id = Objects.requireNonNull(in.readString());
        commandmentId = in.readString();
        adult = in.readString();
        single = in.readString();
        married = in.readString();
        religious = in.readString();
        priest = in.readString();
        teen = in.readString();
        female = in.readString();
        male = in.readString();
        child = in.readString();
        customId = in.readString();
        description = in.readString();
    }

    public static final Creator<SinEntry> CREATOR = new Creator<SinEntry>() {
        @Override
        public SinEntry createFromParcel(Parcel in) {
            return new SinEntry(in);
        }

        @Override
        public SinEntry[] newArray(int size) {
            return new SinEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(commandmentId);
        dest.writeString(adult);
        dest.writeString(single);
        dest.writeString(married);
        dest.writeString(religious);
        dest.writeString(priest);
        dest.writeString(teen);
        dest.writeString(female);
        dest.writeString(male);
        dest.writeString(child);
        dest.writeString(customId);
        dest.writeString(description);
    }
}
