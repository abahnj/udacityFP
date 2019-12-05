package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "SIN")
@Parcelize
data class ExaminationEntry(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id: Long?,
    @ColumnInfo(name = "COMMANDMENT_ID") val commandmentId: Int,
    @ColumnInfo(name = "ADULT") val adult: Int,
    @ColumnInfo(name = "SINGLE") val single: Int,
    @ColumnInfo(name = "MARRIED") val married: Int,
    @ColumnInfo(name = "RELIGIOUS") val religious: Int,
    @ColumnInfo(name = "PRIEST") val priest: Int,
    @ColumnInfo(name = "TEEN") val teen: Int,
    @ColumnInfo(name = "FEMALE") val female: Int,
    @ColumnInfo(name = "MALE") val male: Int,
    @ColumnInfo(name = "CHILD") val child: Int,
    @ColumnInfo(name = "CUSTOM_ID") val customId: Int?,
    @ColumnInfo(name = "DESCRIPTION") var description: String,
    @ColumnInfo(name = "COUNT", defaultValue = "0") var count: Int
) : Parcelable
