package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "PRAYERS")
@Parcelize
data class PrayersEntry(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id: Long,
    @ColumnInfo(name = "PRAYERNAME") val prayerName: String,
    @ColumnInfo(name = "PRAYERTEXT") val prayerText: String,
    @ColumnInfo(name = "GROUPNAME") val groupName: String
) : Parcelable