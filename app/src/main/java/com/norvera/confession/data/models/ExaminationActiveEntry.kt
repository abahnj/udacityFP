package com.norvera.confession.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "SIN_ACTIVE")
@Parcelize
data class ExaminationActiveEntry(
    @field:PrimaryKey
    @field:ColumnInfo(name = "_id")
    var id: Long,
    @field:ColumnInfo(name = "DESCRIPTION")
    var description: String
) : Parcelable
