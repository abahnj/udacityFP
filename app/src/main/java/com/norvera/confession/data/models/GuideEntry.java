package com.norvera.confession.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "guide_main")
public class GuideEntry implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public final long id;
    
    @ColumnInfo(name="h_id")
    public final int headerId;
   
    @ColumnInfo(name="g_id")
    public final int guideId;

    @ColumnInfo(name="g_title")
    public final String guideTitle;

    @ColumnInfo(name="text")
    public final String guideText;

    @ColumnInfo(name="img_name")
    public final String imgName;

    public GuideEntry(long id, int headerId, int guideId, String guideTitle, String guideText, String imgName) {
        this.id = id;
        this.headerId = headerId;
        this.guideId = guideId;
        this.guideTitle = guideTitle;
        this.guideText = guideText;
        this.imgName = imgName;
    }

    protected GuideEntry(Parcel in) {
        id = in.readLong();
        headerId = in.readInt();
        guideId = in.readInt();
        guideTitle = in.readString();
        guideText = in.readString();
        imgName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(headerId);
        dest.writeInt(guideId);
        dest.writeString(guideTitle);
        dest.writeString(guideText);
        dest.writeString(imgName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuideEntry> CREATOR = new Creator<GuideEntry>() {
        @Override
        public GuideEntry createFromParcel(Parcel in) {
            return new GuideEntry(in);
        }

        @Override
        public GuideEntry[] newArray(int size) {
            return new GuideEntry[size];
        }
    };
}
