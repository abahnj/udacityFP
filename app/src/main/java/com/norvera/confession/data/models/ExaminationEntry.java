package com.norvera.confession.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "SIN")
public class ExaminationEntry implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public final long id;

    public ExaminationEntry(long id,
                            int commandmentId,
                            int adult,
                            int single,
                            int married,
                            int religious,
                            int priest,
                            int teen,
                            int female,
                            int male,
                            int child,
                            int customId,
                            String description, int count) {
        this.id = id;
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
        this.count = count;
    }

    @ColumnInfo(name="COMMANDMENT_ID")
    public final int commandmentId;
    
    @ColumnInfo(name="ADULT")
    public final int adult;

    @ColumnInfo(name="SINGLE")
    public final int single;
    
    @ColumnInfo(name="MARRIED")
    public final int married;

    @ColumnInfo(name="RELIGIOUS")
    public final int religious;

    @ColumnInfo(name="PRIEST")
    public final int priest;

    @ColumnInfo(name="TEEN")
    public final int teen;

    @ColumnInfo(name="FEMALE")
    public final int female;

    @ColumnInfo(name="MALE")
    public final int male;

    @ColumnInfo(name="CHILD")
    public final int child;

    @ColumnInfo(name="CUSTOM_ID")
    public final int customId;

    @ColumnInfo(name="DESCRIPTION")
    public final String description;

    @ColumnInfo(name = "COUNT")
    public int count;


    @Ignore
    protected ExaminationEntry(Parcel in) {
        id = in.readLong();
        commandmentId = in.readInt();
        adult = in.readInt();
        single = in.readInt();
        married = in.readInt();
        religious = in.readInt();
        priest = in.readInt();
        teen = in.readInt();
        female = in.readInt();
        male = in.readInt();
        child = in.readInt();
        customId = in.readInt();
        description = in.readString();
        count = in.readInt();
    }

    public static final Creator<ExaminationEntry> CREATOR = new Creator<ExaminationEntry>() {
        @Override
        public ExaminationEntry createFromParcel(Parcel in) {
            return new ExaminationEntry(in);
        }

        @Override
        public ExaminationEntry[] newArray(int size) {
            return new ExaminationEntry[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(commandmentId);
        dest.writeInt(adult);
        dest.writeInt(single);
        dest.writeInt(married);
        dest.writeInt(religious);
        dest.writeInt(priest);
        dest.writeInt(teen);
        dest.writeInt(female);
        dest.writeInt(male);
        dest.writeInt(child);
        dest.writeInt(customId);
        dest.writeString(description);
        dest.writeInt(count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExaminationEntry that = (ExaminationEntry) o;

        if (id != that.id) return false;
        if (commandmentId != that.commandmentId) return false;
        if (adult != that.adult) return false;
        if (single != that.single) return false;
        if (married != that.married) return false;
        if (religious != that.religious) return false;
        if (priest != that.priest) return false;
        if (teen != that.teen) return false;
        if (female != that.female) return false;
        if (male != that.male) return false;
        if (child != that.child) return false;
        if (customId != that.customId) return false;
        if (count != that.count) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + commandmentId;
        result = 31 * result + adult;
        result = 31 * result + single;
        result = 31 * result + married;
        result = 31 * result + religious;
        result = 31 * result + priest;
        result = 31 * result + teen;
        result = 31 * result + female;
        result = 31 * result + male;
        result = 31 * result + child;
        result = 31 * result + customId;
        result = 31 * result + description.hashCode();
        result = 31 * result + count;
        return result;
    }
}
